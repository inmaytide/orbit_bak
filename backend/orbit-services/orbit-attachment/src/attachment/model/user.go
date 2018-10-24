package model

import (
	"attachment/config"
	"encoding/json"
	"errors"
	"fmt"
	"github.com/dgrijalva/jwt-go"
	"log"
	"net/http"
	"attachment/util"
	"crypto/rsa"
	"math/big"
	"encoding/base64"
	"attachment/cache"
	"strings"
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
var env *config.Configuration
var client *cache.RedisClient

func InitializePermission(configuration *config.Configuration, client_ *cache.RedisClient) {
	env = configuration
	client = client_
	var err error
	var body, e, n []byte
	var jwks = jwks{}

	request, _ := http.NewRequest(http.MethodGet, env.Jwt.JwkSetUri, nil)

	if body, err = util.DoRequest(request); err != nil {
		log.Fatal(err)
	}

	if err = json.Unmarshal(body, &jwks); err != nil {
		log.Fatal(err)
	}

	if e, err = base64.RawURLEncoding.DecodeString(jwks.Keys[0].E); err != nil {
		log.Fatal(err)
	}

	if n, err = base64.RawURLEncoding.DecodeString(jwks.Keys[0].N); err != nil {
		log.Fatal(err)
	}

	verifyKey = &rsa.PublicKey{
		E: int(new(big.Int).SetBytes(e).Int64()),
		N: new(big.Int).SetBytes(n),
	}
}

func keyfunc(token *jwt.Token) (interface{}, error) {
	return verifyKey, nil
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
		url := env.Api.Base + strings.Replace(env.Api.GetUserByUsername, "{username}", username, -1)
		request, err := http.NewRequest(http.MethodGet, url, nil)
		if err != nil {
			return User{}, err
		}
		request.Header.Add("Authorization", authorization)
		body, err := util.DoRequest(request)
		err = json.Unmarshal(body, &user)
		if err != nil {
			return user, err
		}
		client.ESet(getCacheName(username), user, 86400)
		return user, nil
	}
	return user, nil
}

func getCacheUser(username string) (User, error) {
	var user = User{}
	data, err := client.Get(getCacheName(username))
	if err != nil {
		return user, err
	}
	if len(data) == 0 {
		return user, errors.New("There is no cache for visitor")
	}

	if data[0] != byte('{') {
		if len(data) < 8 {
			return user, errors.New("Incorrect format of cache for visitor")
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
