package main

import (
	"attachment/config"
	"attachment/eureka"
	"attachment/handler"
	"fmt"
	"log"
	"net/http"
	"os"
	"os/signal"
	"sync"
	"syscall"
	"go.uber.org/dig"
	"attachment/dao"
	"attachment/service"
	"attachment/model"
	"attachment/cache"
)

func buildContainer() *dig.Container {
	container := dig.New()
	container.Provide(config.NewConfiguration)
	container.Provide(dao.NewDataSource)
	container.Provide(cache.NewRedisClient)
	container.Provide(dao.NewAttachmentRepository)
	container.Provide(service.NewAttachmentService)
	return container
}

func startWebServer(env *config.Configuration) {
	router := handler.NewRouter()
	log.Println("Starting HTTP service at", env.Application.Server.Port)
	err := http.ListenAndServe(fmt.Sprintf(":%d", env.Application.Server.Port), router)
	if err != nil {
		log.Println("An errorhandler occured starting HTTP listener at port ", env.Application.Server.Port)
		log.Fatal(err)
	}
}

func handleSigterm(context *dig.Container) {
	c := make(chan os.Signal, 1)
	signal.Notify(c, os.Interrupt)
	signal.Notify(c, syscall.SIGTERM)
	go func() {
		<-c
		eureka.Deregister()
		context.Invoke(func(ds *dao.DataSource, client *cache.RedisClient) {
			ds.Destroy()
			client.Destroy()
		})
		os.Exit(1)
	}()
}

func main() {
	context := buildContainer()

	handleSigterm(context)

	context.Invoke(func(service service.AttachmentService, cache *cache.RedisClient, env *config.Configuration) {
		handler.SetAttachmentService(service)
		model.InitializePermission(env, cache)
		go startWebServer(env)
		eureka.Register(env)
	})

	wg := sync.WaitGroup{}
	wg.Add(1)
	wg.Wait()

}
