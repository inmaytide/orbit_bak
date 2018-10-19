package handler

import (
	"github.com/gorilla/mux"
	"net/http"
	"captcha/eureka"
)

type Route struct {
	Name        string
	Method      string
	Pattern     string
	HandlerFunc http.HandlerFunc
}

var routes = []Route{
	{
		"Index",
		"GET",
		"/",
		eureka.Index,
	},
	{
		"Info",
		"GET",
		"/info",
		eureka.Info,
	},
	{
		"Health",
		"POST",
		"/health",
		eureka.Health,
	},
	{
		"VendorShow",
		"GET",
		"/vendors/{productId}",
		eureka.VendorShow,
	},
	{
		"Generate",
		"GET",
		"/captcha",
		GenerateAndWriteToHttpResponseWriter,
	},
	{
		"Validate",
		"GET",
		"/captcha/{captcha}",
		Validate,
	},
}

func NewRouter() *mux.Router {
	router := mux.NewRouter().StrictSlash(true)
	for _, route := range routes {
		router.Methods(route.Method).Path(route.Pattern).Name(route.Name).Handler(route.HandlerFunc)
	}
	return router
}
