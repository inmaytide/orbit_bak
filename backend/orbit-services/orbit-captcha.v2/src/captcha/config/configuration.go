package config

import (
	"os"
	"log"
	"io/ioutil"
	"gopkg.in/yaml.v2"
)

type Configuration struct {
	Application struct {
		Name string `yaml:"name"`
		Server struct {
			Port int `yaml:"port"`
		}
	}
	Eureka string `yaml:"eureka"`
	Redis struct {
		Addr string `yaml:"addr"`
	}
	Captcha struct {
		Height             int  `yaml:"height"`
		Width              int  `yaml:"width"`
		Mode               int  `yaml:"mode"`
		ComplexOfNoiseText int  `yaml:"complex-of-noise-text"`
		ComplexOfNoiseDot  int  `yaml:"complex-of-noise-dot"`
		IsUseSimpleFont    bool `yaml:"use-simple-font"`
		IsShowHollowLine   bool `yaml:"show-hollow-line"`
		IsShowNoiseDot     bool `yaml:"show-noise-dot"`
		IsShowNoiseText    bool `yaml:"show-noise-text"`
		IsShowSlimeLine    bool `yaml:"show-slime-line"`
		IsShowSineLine     bool `yaml:"show-sine-line"`
		CaptchaLen         int  `yaml:"len"`
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
