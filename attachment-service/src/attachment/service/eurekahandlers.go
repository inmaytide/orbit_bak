package service

import (
	"attachment/model"
	"encoding/json"
	"fmt"
	"github.com/gorilla/mux"
	"net/http"
	"strconv"
)

func Index(writer http.ResponseWriter, request *http.Request) {
	fmt.Fprintln(writer, "Welcome!!!")
}

func Info(writer http.ResponseWriter, request *http.Request) {
	writer.Header().Set("Content-Type", "application/json; charset=UTF-8")
	writer.WriteHeader(http.StatusOK)
	status := make(map[string]interface{})
	status["status"] = "OK"
	if err := json.NewEncoder(writer).Encode(status); err != nil {
		panic(err)
	}
}

func Health(writer http.ResponseWriter, request *http.Request) {
	writer.Header().Set("Content-Type", "application/json; charset=UTF-8")
	writer.WriteHeader(http.StatusOK)
	health := make(map[string]interface{})
	health["health"] = "OK"
	if err := json.NewEncoder(writer).Encode(health); err != nil {
		panic(err)
	}
}

func VendorShow(w http.ResponseWriter, r *http.Request) {
	vars := mux.Vars(r)
	var productId int
	var err error
	if productId, err = strconv.Atoi(vars["productId"]); err != nil {
		panic(err)
	}
	fmt.Println("Loading vendors for product " + strconv.Itoa(productId))
	vendors := make([]model.Vendor, 0, 2)
	v1 := model.Vendor{Id: 1, Name: "Internetstore.biz"}
	v2 := model.Vendor{Id: 2, Name: "Junkyard.nu"}
	vendors = append(vendors, v1, v2)
	if len(vendors) > 0 {
		w.Header().Set("Content-Type", "application/json; charset=UTF-8")
		w.WriteHeader(http.StatusOK)
		if err := json.NewEncoder(w).Encode(vendors); err != nil {
			panic(err)
		}
		return
	}

	// If we didn't find it, 404
	w.Header().Set("Content-Type", "application/json; charset=UTF-8")
	w.WriteHeader(http.StatusNotFound)
	if err := json.NewEncoder(w).Encode(model.JsonErr{Code: http.StatusNotFound, Text: "Not Found"}); err != nil {
		panic(err)
	}
}

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
