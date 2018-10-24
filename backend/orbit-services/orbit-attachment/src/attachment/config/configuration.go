package config

import (
	"gopkg.in/yaml.v2"
	"io/ioutil"
	"log"
	"os"
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
	}
	Api struct {
		Base               string `yaml:"base"`
		GetUserByUsername string `yaml:"get-user-by-username"`
	}
	Jwt struct {
		JwkSetUri string `yaml:"jwk-set-uri"`
	}
}

func NewConfiguration() *Configuration {
	dir, err := os.Getwd()
	if err != nil {
		log.Println("Failed to get program root directory")
		log.Fatal(err)
	}

	content, err := ioutil.ReadFile(dir + "\\application.yaml")
	if err != nil {
		log.Println("Failed to read \"application.yaml\" file")
		log.Fatal(err)
	}

	config := &Configuration{}
	err = yaml.Unmarshal(content, &config)
	if err != nil {
		log.Println("File \"application.yaml\" format is incorrect")
		log.Fatal(err)
	}

	log.Println("The configuration file is loaded successfully.")

	return config
}
