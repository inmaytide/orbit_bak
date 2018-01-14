package model

import (
	"attachment/config"
	"attachment/redis"
	"encoding/json"
	"errors"
	"fmt"
	"github.com/dgrijalva/jwt-go"
	"io/ioutil"
	"log"
	"net/http"
	"strings"
)

const (
	VISITOR_CACHE_NAME_PATTERN = "visitor-json::%s"
	secret_key                 = "59a84cbf83227a35"
)

type User struct {
	ID       int64  `json:"id,string"`
	Username string `json:"username"`
	Name     string `json:"name"`
}

func keyfunc(token *jwt.Token) (interface{}, error) {
	return []byte(secret_key), nil
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
		return user, nil
	}

	if user.ID == 0 {
		println(config.GetApi().GetUserByUsername)
		var url = config.GetApi().Base + config.GetApi().GetUserByUsername
		url = strings.Replace(url, "{username}", username, -1)
		request, err := http.NewRequest("GET", url, nil)
		if err != nil {
			return User{}, err
		}
		request.Header.Add("Authorization", authorization)
		response, err := http.DefaultClient.Do(request)
		if err != nil {
			return user, err
		}
		if response.StatusCode != http.StatusOK {
			return user, errors.New("user service is not available")
		}
		defer response.Body.Close()
		body, err := ioutil.ReadAll(response.Body)
		if err != nil {
			return user, err
		}

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
	if len(data) < 8 {
		log.Println("There is no cache for visitor")
		return user, nil
	}

	err = json.Unmarshal(data[7:], &user)
	if err != nil {
		return user, err
	}
	return user, nil
}

func getCacheName(username string) string {
	return fmt.Sprintf(VISITOR_CACHE_NAME_PATTERN, username)
}
