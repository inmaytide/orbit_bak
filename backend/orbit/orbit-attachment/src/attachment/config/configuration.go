package config

import (
	"gopkg.in/yaml.v2"
	"io/ioutil"
	"log"
	"os"
	"attachment/util"
	"attachment/errorhandler"
	"sync"
)

type Application struct {
	ID         int64
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
}

var application Application
var once sync.Once

func loadApplication() {
	dir, err := os.Getwd()
	errorhandler.Termination(err, "Failed to get program root directory")

	content, err := ioutil.ReadFile(dir + "/resources/application.yaml")
	errorhandler.Termination(err, "Failed to read \"application.yaml\" file")

	application = Application{}
	err = yaml.Unmarshal(content, &application)
	errorhandler.Termination(err, "Failed to get program root directory")

	application.ID = util.GetSnowflakeID()
	log.Println("The configuration file is loaded successfully.")
}

func GetApplication() Application {
	once.Do(loadApplication)
	return application;
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
