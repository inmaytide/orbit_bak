package redis

import (
	"github.com/go-redis/redis"
	"attachment/config"
	"fmt"
)

var client *redis.Client

func GetClient() *redis.Client {
	fmt.Println(config.Apps.Redis)
	if client == nil {
		client = redis.NewClient(&redis.Options{
			Addr: config.Apps.Redis.Addr,
			Password: config.Apps.Redis.Password,
			DB: config.Apps.Redis.DB,
		})
	}
	return client
}
