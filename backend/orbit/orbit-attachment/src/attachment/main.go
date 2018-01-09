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
)

func startWebServer() {
	router := handler.NewRouter()
	log.Println("Starting HTTP service at", config.Apps.Port)
	err := http.ListenAndServe(fmt.Sprintf(":%s", config.Apps.Port), router)
	if err != nil {
		log.Println("An error occured starting HTTP listener at port ", config.Apps.Port)
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
		os.Exit(1)
	}()
}

func main() {

	config.LoadConfiguration()

	handleSigterm()

	go startWebServer()

	eureka.Register()

	go eureka.StartHeartbeat()

	wg := sync.WaitGroup{}
	wg.Add(1)
	wg.Wait()

}
