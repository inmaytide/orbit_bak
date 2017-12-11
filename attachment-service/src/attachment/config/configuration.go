package config

import (
	"gopkg.in/yaml.v2"
	"io/ioutil"
	"os"
	"log"
)

type Application struct {
	Port   string `yaml:"port"`
	Name   string `yaml:"application-name"`
	Eureka string `yaml:"eureka"`
	Datasource string `yaml:"datasource"`
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
