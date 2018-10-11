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
)

func BuildContainer() *dig.Container {
	container := dig.New();
	container.Provide(dao.NewDataSource)
	container.Provide(dao.NewAttachmentRepository)
	container.Provide(service.NewAttachmentService)
	container.Provide(handler.NewAttachmentHandler)
	container.Provide(handler.NewRoutes)
	return container
}

func startWebServer(context *dig.Container) {
	err := context.Invoke(func(routes handler.Routes) {
		router := handler.NewRouter(routes)
		log.Println("Starting HTTP service at", config.GetPort())
		err := http.ListenAndServe(fmt.Sprintf(":%s", config.GetPort()), router)
		if err != nil {
			log.Println("An errorhandler occured starting HTTP listener at port ", config.GetPort())
			log.Println("Error: ", err.Error())
		}
	})
	if err != nil {
		log.Println("Failed to start web server")
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
		context.Invoke(func(ds *dao.DataSource) {
			ds.Destroy();
		})
		os.Exit(1)
	}()
}

func main() {
	context := BuildContainer()

	handleSigterm(context)

	go startWebServer(context)

	eureka.Register()

	go eureka.StartHeartbeat()

	wg := sync.WaitGroup{}
	wg.Add(1)
	wg.Wait()

}
