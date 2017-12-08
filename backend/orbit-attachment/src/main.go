package src

import (
	"fmt"
	"log"
	"net/http"
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

func main() {
	http.HandleFunc("/", sayhelloName)
	err := http.ListenAndServe(":7002", nil)
	if err != nil {
		log.Fatal("ListenAndServe:", err)
	}
}
