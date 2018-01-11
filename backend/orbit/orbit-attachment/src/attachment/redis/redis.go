package redis

import (
	"attachment/config"
	"github.com/garyburd/redigo/redis"
	"log"
	"encoding/json"
)

func getConn() redis.Conn {
	conn, err := redis.Dial("tcp", config.GetApplication().Redis.Addr)
	if err != nil {
		log.Fatal("Failed to connect the redis, err =>", err.Error())
	}
	return conn
}

func Set(key string, value interface{}) error {
	data, err := json.Marshal(value)
	if err != nil {
		log.Printf("Value serialization failed. value => [%s]", value)
		return err
	}

	conn := getConn()
	defer conn.Close()

	_, err = conn.Do("set", key, data)
	if err != nil {
		log.Printf("Value save failed. key => [%s], value => [%s]", key, value)
		return err
	}
	return nil
}

func ESet(key string, value interface{}, expire uint64) error {
	data, err := json.Marshal(value)
	if err != nil {
		log.Printf("Value serialization failed. value => [%s]", value)
		return err
	}

	conn := getConn()
	defer conn.Close()

	_, err = conn.Do("set", key, data)
	if err != nil {
		log.Printf("Value save failed. key => [%s], value => [%s]", key, value)
		return err
	}

	_, err = conn.Do("expire", key, expire)
	if err != nil {
		log.Printf("Failed to set expire time [%s]", key)
		return err
	}

	return nil
}

func Get(key string) ([]byte, error) {
	conn := getConn()
	defer conn.Close()
	data, err := redis.Bytes(conn.Do("get", key))
	if err != nil {
		log.Printf("Failed to get value from redis. error => [%s]", err.Error())
		return nil, err
	}
	return data, nil
}

func Delete(key string) error {
	conn := getConn()
	defer conn.Close()

	_, err := conn.Do("del", key)
	if err != nil {
		log.Printf("Failed to delete value from redis. key => [%s], error => [%s]", key, err.Error())
		return err
	}
	return nil
}