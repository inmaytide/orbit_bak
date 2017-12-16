package main

import (
	"attachment/config"
	"attachment/eureka"
	"attachment/service"
	"log"
	"net/http"
	"os"
	"os/signal"
	"sync"
	"syscall"
	"fmt"
	"attachment/model"
	"attachment/util"
	"database/sql"
)

func startWebServer() {
	router := service.NewRouter()
	log.Println("Starting HTTP service at", config.Apps.Port)
	err := http.ListenAndServe(fmt.Sprintf(":%s", config.Apps.Port), router)
	if err != nil {
		log.Println("An error occured starting HTTP listener at port ", config.Apps.Port)
		log.Println("Error: " + err.Error())
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

	test()

	handleSigterm()

	go startWebServer()

	eureka.Register()

	go eureka.StartHeartbeat()

	wg := sync.WaitGroup{}
	wg.Add(1)
	wg.Wait()
}

func test() {
	service.SaveAttachment(&model.Attachment{
		OriginalName: sql.NullString{String: "orsdoifsd可我额我反动势力", Valid: true},
		StorageName:  sql.NullString{String: util.GetUUID(), Valid: true},
		StorageAddress: sql.NullString{String: "d:/attachment/20171214", Valid:true},
	})
}
