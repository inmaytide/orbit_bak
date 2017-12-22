package redis

import (
	"attachment/config"
	"attachment/util"
	"github.com/garyburd/redigo/redis"
	"log"
)

func getConn() redis.Conn {
	conn, err := redis.Dial("tcp", config.Apps.Redis.Addr)
	util.CheckError(err)
	return conn
}

func Set(key string, value interface{}) {
	conn := getConn()
	defer conn.Close()
	_, err := conn.Do("set", key, value)
	util.CheckError(err)
}

func ESet(key string, value interface{}, expire uint64) {
	conn := getConn()
	defer conn.Close()
	_, err := conn.Do("set", key, value)
	util.CheckError(err)
	_, err = conn.Do("expire", key, expire)
	util.CheckError(err)
}

func Get(key string) interface{} {
	conn := getConn()
	defer conn.Close()
	value, err := conn.Do("get", key)
	util.CheckError(err)
	log.Println(value)
	return value;
}
