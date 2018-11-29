package config

import (
	"gopkg.in/yaml.v2"
	"io/ioutil"
	"log"
	"os"
	"attachment/util"
)

type Configuration struct {
	Application struct {
		Name string `yaml:"name"`
		Server struct {
			Port int `yaml:"port"`
		}
	}
	Eureka string `yaml:"eureka"`
	DataSource struct {
		Dialect string `yaml:"dialect"`
		URL     string `yaml:"url"`
	}
	Redis struct {
		Addr     string `yaml:"addr"`
		Password string `yaml:"password"`
	}
	Attachment struct {
		FormalStorageAddress string `yaml:"formal-storage-address"`
		TemporaryExpireTime  uint64 `yaml:"temporary-expire-time"`
		RequestParameter     string `yaml:"request-parameter"`
	}
	Api struct {
		Base              string `yaml:"base"`
		GetUserByUsername string `yaml:"get-user-by-username"`
	}
	Jwt struct {
		JwkSetUri string `yaml:"jwk-set-uri"`
	}
}

func NewConfiguration() *Configuration {
	content, err := ioutil.ReadFile(util.GetRoot() + "\\application.yaml")
	if err != nil {
		log.Fatal("Failed to read \"application.yaml\" file, Cause by: ", err)
	}

	config := &Configuration{}
	err = yaml.Unmarshal(content, &config)
	if err != nil {
		log.Fatal("File \"application.yaml\" format is incorrect, Cause by: ", err)
	}

	log.Println("Configuration has been loaded.")

	return config
}
