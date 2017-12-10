package main

import (
	"eureka"
	"fmt"
	"log"
	"net/http"
	"os"
	"os/signal"
	"sync"
	"syscall"
)

func sayhelloName(w http.ResponseWriter, request *http.Request) {
	request.ParseForm()
	fmt.Println(request.Form)
	fmt.Println("path", request.URL.Path)
	fmt.Println("scheme", request.URL.Scheme)
	for k, v := range request.Form {
		fmt.Println("key", k)
		fmt.Println("value", v)
	}
	fmt.Fprintf(w, "Hello astaxie!!!!")
}

func startWebServer() {
	http.HandleFunc("/", sayhelloName)
	err := http.ListenAndServe(":7002", nil)
	if err != nil {
		log.Fatal("ListenAndServe:", err)
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
	handleSigterm()

	go startWebServer()

	eureka.Register()

	go eureka.StartHeartbeat()

	wg := sync.WaitGroup{}
	wg.Add(1)
	wg.Wait()
}
