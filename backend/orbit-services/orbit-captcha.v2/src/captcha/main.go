package main

import (
	"captcha/cache"
	"captcha/config"
	"captcha/eureka"
	"captcha/handler"
	"fmt"
	"github.com/mojocn/base64Captcha"
	"go.uber.org/dig"
	"log"
	"net/http"
	"os"
	"os/signal"
	"sync"
	"syscall"
)

func buildContainer() *dig.Container {
	container := dig.New()
	container.Provide(config.NewConfiguration)
	container.Provide(cache.NewRedisClient)
	container.Provide(cache.NewCaptchaStore)
	container.Invoke(func(store *cache.CaptchaStore) {
		base64Captcha.SetCustomStore(store)
	})
	return container
}

func startWebServer(config *config.Configuration) {
	router := handler.NewRouter()
	port := config.Application.Server.Port
	log.Println("Starting HTTP service at", port)
	err := http.ListenAndServe(fmt.Sprintf(":%d", port), router)
	if err != nil {
		log.Println("An error occured starting HTTP listener at port", port)
		log.Fatal(err)
	}
}

func handleSigterm(context *dig.Container) {
	c := make(chan os.Signal, 1)
	signal.Notify(c, os.Interrupt)
	signal.Notify(c, syscall.SIGTERM)
	go func() {
		<-c
		context.Invoke(func(client *cache.RedisClient) {
			client.Destroy()
		})
		eureka.Deregister()
		os.Exit(1)
	}()
}

func main() {
	context := buildContainer()

	handleSigterm(context)

	context.Invoke(func(config *config.Configuration) {
		handler.SetCaptchaConfig(base64Captcha.ConfigCharacter{
			Height:             config.Captcha.Height,
			Width:              config.Captcha.Width,
			Mode:               config.Captcha.Mode,
			ComplexOfNoiseText: config.Captcha.ComplexOfNoiseText,
			ComplexOfNoiseDot:  config.Captcha.ComplexOfNoiseDot,
			IsUseSimpleFont:    config.Captcha.IsUseSimpleFont,
			IsShowHollowLine:   config.Captcha.IsShowHollowLine,
			IsShowNoiseDot:     config.Captcha.IsShowNoiseDot,
			IsShowNoiseText:    config.Captcha.IsShowNoiseText,
			IsShowSlimeLine:    config.Captcha.IsShowSlimeLine,
			IsShowSineLine:     config.Captcha.IsShowSineLine,
			CaptchaLen:         config.Captcha.CaptchaLen,
		})

		go startWebServer(config)

		eureka.Register(config)

	})

	wg := sync.WaitGroup{}
	wg.Add(1)
	wg.Wait()
}
