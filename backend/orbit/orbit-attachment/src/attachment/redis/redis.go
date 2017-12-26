package redis

import (
	"attachment/config"
	"attachment/util"
	"github.com/garyburd/redigo/redis"
	"log"
	"encoding/json"
)

func getConn() redis.Conn {
	conn, err := redis.Dial("tcp", config.Apps.Redis.Addr)
	if err != nil {
		log.Fatal("Failed to connect the redis, err =>", err.Error())
		panic(err)
	}
	return conn
}

func Set(key string, value interface{}) {
	data, err := json.Marshal(value)
	util.CheckError(err)
	conn := getConn()
	defer conn.Close()
	_, err = conn.Do("set", key, data)
	util.CheckError(err)
}

func ESet(key string, value interface{}, expire uint64) {
	data, err := json.Marshal(value)
	util.CheckError(err)

	conn := getConn()
	defer conn.Close()

	_, err = conn.Do("set", key, data)
	util.CheckError(err)
	_, err = conn.Do("expire", key, expire)
	util.CheckError(err)
}

func Get(key string) []byte {
	conn := getConn()
	defer conn.Close()
	data, err := redis.Bytes(conn.Do("get", key))
	util.CheckError(err)
	return data;
}