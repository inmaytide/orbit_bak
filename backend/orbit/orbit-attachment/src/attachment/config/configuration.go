package config

import (
	"gopkg.in/yaml.v2"
	"io/ioutil"
	"log"
	"os"
)

type Application struct {
	Port       string `yaml:"port"`
	Name       string `yaml:"application-name"`
	Eureka     string `yaml:"eureka"`
	Datasource string `yaml:"datasource"`
	Redis      struct {
		Addr     string `yaml:"addr"`
		Password string `yaml:"password"`
		DB       int    `yaml:"db"`
	}
	Attachment struct {
		FormalStorageAddress string `yaml:"formal-storage-address"`
		TemporaryExpireTime  uint64    `yaml:"temporary-expire-time"`
	}
}

var Apps = Application{}

func LoadConfiguration() {
	dir, _ := os.Getwd()
	content, _ := ioutil.ReadFile(dir + "/resources/application.yaml")
	err := yaml.Unmarshal(content, &Apps)
	if err != nil {
		log.Fatal("failed to load configuration", err)
	}
}
