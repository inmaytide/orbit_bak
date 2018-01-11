package util

import (
	"github.com/bwmarrin/snowflake"
	"github.com/satori/go.uuid"
	"strings"
	"log"
)

func GetUUID() string {
	return strings.Replace(uuid.NewV4().String(), "-", "", -1)
}

var node *snowflake.Node

func getSnowflakeNode() *snowflake.Node {
	if node == nil {
		var err error
		node, err = snowflake.NewNode(2)
		if err != nil {
			log.Fatalf("Can't to generate a snowflake id, err => [%s]", err.Error());
		}
	}
	return node
}

func GetSnowflakeID() int64 {
	return getSnowflakeNode().Generate().Int64()
}
