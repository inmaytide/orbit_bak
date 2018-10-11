package model

import (
	"attachment/config"
	"attachment/redis"
	"encoding/json"
	"errors"
	"fmt"
	"github.com/dgrijalva/jwt-go"
	"log"
	"net/http"
	"attachment/util"
	"crypto/rsa"
	"math/big"
	"sync"
	"encoding/base64"
	"attachment/errorhandler"
)

const (
	VISITOR_CACHE_NAME_PATTERN = "visitor-json::%s"
)

type User struct {
	ID       int64  `json:"id,string"`
	Username string `json:"username"`
	Name     string `json:"name"`
}

type jwks struct {
	Keys []key `json:"keys"`
}

type key struct {
	Kty string `json:"kty"`
	E   string `json:"e"`
	N   string `json:"n"`
}

var verifyKey *rsa.PublicKey
var once sync.Once

func keyfunc(token *jwt.Token) (interface{}, error) {
	once.Do(func() {
		request, _ := http.NewRequest(http.MethodGet, config.GetApplication().Jwt.JwkSetUri, nil)
		body, err := util.DoRequest(request);
		errorhandler.Terminate(err, "The authorization service is unavailable right now")

		var jwks = jwks{}
		err = json.Unmarshal(body, &jwks)
		errorhandler.Terminate(err, "The authorization service is unavailable right now")

		e, err := base64.RawURLEncoding.DecodeString(jwks.Keys[0].E)
		E := new(big.Int).SetBytes(e).Int64();
		errorhandler.Terminate(err, "The authorization service is unavailable right now")

		n, err := base64.RawURLEncoding.DecodeString(jwks.Keys[0].N)
		N := new(big.Int).SetBytes(n);
		errorhandler.Terminate(err, "The authorization service is unavailable right now")

		verifyKey = &rsa.PublicKey{
			E: int(E),
			N: N,
		}
	})
	return verifyKey, nil;
}

func getAuthorization(r *http.Request) (string, error) {
	authorization := r.Header.Get("Authorization")
	if authorization == "" || len(authorization) < 7 || authorization[0:6] != "Bearer" {
		return "", errors.New("no authorization")
	}
	return authorization, nil
}

func parseAuthorization(authorization string) (*jwt.Token, error) {
	token, err := jwt.Parse(authorization[7:], keyfunc)
	if err != nil {
		return nil, err
	}
	return token, nil
}

func VisitorResolver(r *http.Request) (User, error) {
	var user User
	authorization, err := getAuthorization(r)
	if err != nil {
		return user, err
	}
	return getUser(authorization)
}

func getUsername(token *jwt.Token) (string, error) {
	claims := token.Claims.(jwt.MapClaims)
	username := claims["user_name"]
	if username == nil || len(username.(string)) == 0 {
		return "", errors.New("invalid token")
	}
	return username.(string), nil
}

func getUser(authorization string) (User, error) {
	var user User
	token, err := parseAuthorization(authorization)
	if err != nil {
		return user, err
	}

	username, err := getUsername(token)
	if err != nil {
		return user, err
	}

	user, err = getCacheUser(username)

	if err != nil {
		log.Println(err.Error())
		request, err := http.NewRequest(http.MethodGet, config.GetApi().GetUserByUsername(username), nil)
		if err != nil {
			return User{}, err
		}
		request.Header.Add("Authorization", authorization)
		body, err := util.DoRequest(request)
		err = json.Unmarshal(body, &user)
		if err != nil {
			return user, err
		}
		redis.GetClient().ESet(getCacheName(username), user, 86400)
		return user, nil
	}
	return user, nil
}

func getCacheUser(username string) (User, error) {
	var user = User{}
	data, err := redis.GetClient().Get(getCacheName(username))
	if err != nil {
		return user, err
	}
	if len(data) == 0 {
		return user, errors.New("There is no cache for visitor");
	}

	if data[0] != byte('{') {
		if len(data) < 8 {
			return user, errors.New("Incorrect format of cache for visitor");
		}
		data = data[7:]
	}
	err = json.Unmarshal(data, &user)
	if err != nil {
		return user, err
	}
	return user, nil
}

func getCacheName(username string) string {
	return fmt.Sprintf(VISITOR_CACHE_NAME_PATTERN, username)
}
