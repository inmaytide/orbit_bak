package util

import (
	"os"
	"log"
)

func GetRoot() string {
	dir, err := os.Getwd()
	if err != nil {
		log.Fatal("Failed to get program root directory, Cause by: ", err)
	}
	return dir
}
