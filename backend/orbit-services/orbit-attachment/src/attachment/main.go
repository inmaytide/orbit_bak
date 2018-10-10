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
	"attachment/dao"
	"go.uber.org/dig"
)

func BuildContainer() *dig.Container {
	container := dig.New()

	container.Provide(NewConfig)
	container.Provide(ConnectDatabase)
	container.Provide(NewPersonRepository)
	container.Provide(NewPersonService)
	container.Provide(NewServer)

	return container
}

func startWebServer() {
	router := handler.NewRouter()
	log.Println("Starting HTTP service at", config.GetPort())
	err := http.ListenAndServe(fmt.Sprintf(":%s", config.GetPort()), router)
	if err != nil {
		log.Println("An errorhandler occured starting HTTP listener at port ", config.GetPort())
		log.Println("Error: ", err.Error())
	}
}

func handleSigterm() {
	c := make(chan os.Signal, 1)
	signal.Notify(c, os.Interrupt)
	signal.Notify(c, syscall.SIGTERM)
	go func() {
		<-c
		eureka.Deregister()
		dao.Destroy()
		os.Exit(1)
	}()
}

func main() {
	handleSigterm()

	go startWebServer()

	eureka.Register()

	go eureka.StartHeartbeat()

	wg := sync.WaitGroup{}
	wg.Add(1)
	wg.Wait()

}
