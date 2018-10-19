package cache

import (
	"github.com/mediocregopher/radix.v3"
	"log"
	"encoding/json"
	"captcha/config"
)

type RedisClient struct {
	pool *radix.Pool
}

func NewRedisClient(config *config.Configuration) *RedisClient {
	pool, err := radix.NewPool("tcp", config.Redis.Addr, 10, nil)
	if err != nil {
		log.Println("Failed to connect the redis server")
		log.Fatal(err)
	}
	return &RedisClient{
		pool: pool,
	}
}

func (client *RedisClient) GetPool() *radix.Pool {
	return client.pool
}

func (client *RedisClient) Destroy() {
	if err := client.pool.Close(); err != nil {
		log.Println("Failed to destroy redis client")
	} else {
		log.Println("Redis client destroyed")
	}
}

func (client *RedisClient) Set(key string, value interface{}) error {
	data, err := json.Marshal(value)
	if err != nil {
		return err
	}
	return client.GetPool().Do(radix.FlatCmd(nil, "set", key, data))
}

func (client *RedisClient) ESet(key string, value interface{}, expire uint64) error {
	err := client.Set(key, value)
	if err != nil {
		return err
	}
	return client.GetPool().Do(radix.FlatCmd(nil, "expire", key, expire))
}

func (client *RedisClient) Get(key string) ([]byte, error) {
	var data []byte
	err := client.GetPool().Do(radix.FlatCmd(&data, "get", key))
	if err != nil {
		return nil, err
	}
	return data, nil
}

func (client *RedisClient) Delete(key string) error {
	return client.GetPool().Do(radix.Cmd(nil, "del", key))
}
