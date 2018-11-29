package util

import (
	"github.com/bwmarrin/snowflake"
	"github.com/satori/go.uuid"
	"strings"
	"log"
	"sync"
)

func GetUUID() string {
	return strings.Replace(uuid.NewV4().String(), "-", "", -1)
}

var node *snowflake.Node
var once sync.Once

func getSnowflakeNode() *snowflake.Node {
	once.Do(func() {
		var err error
		node, err = snowflake.NewNode(2)
		if err != nil {
			log.Fatal("Can't to generate a snowflake id, cause by: ", err.Error())
		}
	});
	return node
}

func GetSnowflakeID() int64 {
	return getSnowflakeNode().Generate().Int64()
}
