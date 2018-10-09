package config

import (
	"attachment/errorhandler"
	"gopkg.in/yaml.v2"
	"io/ioutil"
	"log"
	"os"
	"sync"
	"strings"
)

type Api struct {
	Base              string `yaml:"base"`
	GetUserByUsername_ string `yaml:"get-user-by-username"`
}

type Application struct {
	Port       string `yaml:"port"`
	Name       string `yaml:"application-name"`
	Eureka     string `yaml:"eureka"`
	Datasource string `yaml:"datasource"`
	Redis struct {
		Addr     string `yaml:"addr"`
		Password string `yaml:"password"`
	}
	Attachment struct {
		FormalStorageAddress string `yaml:"formal-storage-address"`
		TemporaryExpireTime  uint64 `yaml:"temporary-expire-time"`
	}
	Api Api
}

var application Application
var once sync.Once

func loadApplication() {
	dir, err := os.Getwd()
	errorhandler.Termination(err, "Failed to get program root directory")

	content, err := ioutil.ReadFile(dir + "/resources/application.yaml")
	errorhandler.Termination(err, "Failed to read \"application.yaml\" file")

	err = yaml.Unmarshal(content, &application)
	errorhandler.Termination(err, "Failed to get program root directory")

	log.Println("The configuration file is loaded successfully.")
}

func GetApplication() Application {
	once.Do(loadApplication)
	return application
}

func GetPort() string {
	return GetApplication().Port
}

func GetEurekaServer() string {
	return GetApplication().Eureka
}

func GetApplicationName() string {
	return GetApplication().Name
}

func GetTemporaryExpireTime() uint64 {
	return GetApplication().Attachment.TemporaryExpireTime
}

func GetFormalStorageAddress() string {
	return GetApplication().Attachment.FormalStorageAddress
}

func GetApi() Api {
	return GetApplication().Api
}

func (api Api) GetUserByUsername(username string) string {
	var url = api.Base + api.GetUserByUsername_
	url = strings.Replace(url, "{username}", username, -1)
	return url
}
