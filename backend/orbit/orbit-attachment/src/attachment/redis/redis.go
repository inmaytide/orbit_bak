package redis

import (
	"log"
	"encoding/json"
	"attachment/config"
	"sync"
	"github.com/mediocregopher/radix.v3"
)

type Client struct {
	pool *radix.Pool
}

var client *Client
var once sync.Once

func GetClient() *Client {
	once.Do(func() {
		client = new(Client)
		var err error
		client.pool, err = radix.NewPool("tcp", config.GetApplication().Redis.Addr, 10, nil)
		if err != nil {
			log.Fatal("Failed to connect the redis, err =>", err.Error())
		}
	})
	return client
}

func (client *Client) GetPool() *radix.Pool {
	return client.pool
}

func (client Client) Set(key string, value interface{}) error {
	data, err := json.Marshal(value)
	if err != nil {
		log.Printf("Value serialization failed. value => [%s]", value)
		return err
	}

	err = client.GetPool().Do(radix.FlatCmd(nil, "set", key, data))
	if err != nil {
		log.Printf("Value save failed. key => [%s], value => [%s]", key, value)
		return err
	}
	return nil
}

func (client Client) ESet(key string, value interface{}, expire uint64) error {
	err := client.Set(key, value)
	if err != nil {
		return err
	}

	err = client.GetPool().Do(radix.FlatCmd(nil, "expire", key, expire))
	if err != nil {
		log.Printf("Failed to set expire time [%s]", key)
		return err
	}
	return nil
}

func (client Client) Get(key string) ([]byte, error) {
	var data []byte;
	err := client.GetPool().Do(radix.FlatCmd(&data, "get", key))
	if err != nil {
		log.Printf("Failed to get value from redis. error => [%s]", err.Error())
		return nil, err
	}
	return data, nil
}

func (client Client) Delete(key string) error {
	err := client.GetPool().Do(radix.Cmd(nil, "del", key))
	if err != nil {
		log.Printf("Failed to delete value from redis. key => [%s], error => [%s]", key, err.Error())
		return err
	}
	return nil
}
