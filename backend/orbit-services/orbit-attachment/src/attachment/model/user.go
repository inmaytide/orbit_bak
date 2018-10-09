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
)

const (
	VISITOR_CACHE_NAME_PATTERN = "visitor-json::%s"
	E = 65537
	N = "92032540060540856130699856218033980227909420161154272440856424391337831083081633223257042754076930142688883599931819080863310751260975563655931896404073841267772979089007431866988369523052660671922204391891685962100812539714008553578527942925270385160702076827458753644833661843321703086079889898175420563511"
)

type User struct {
	ID       int64  `json:"id,string"`
	Username string `json:"username"`
	Name     string `json:"name"`
}


func keyfunc(token *jwt.Token) (interface{}, error) {
	module, b := new(big.Int).SetString(N, 0)
	if b {
		return &rsa.PublicKey{
			E: E,
			N: module,
		}, nil
	}
	return nil, errors.New("Wrong module value");
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
