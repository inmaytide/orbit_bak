package handler

import (
	"github.com/gorilla/mux"
	"net/http"
)

type Route struct {
	Name        string
	Method      string
	Pattern     string
	HandlerFunc http.HandlerFunc
}

func NewRouter() *mux.Router {
	router := mux.NewRouter().StrictSlash(true)
	for _, route := range routes {
		var handler http.Handler
		handler = route.HandlerFunc
		handler = Logger(handler, route.Name)
		router.Methods(route.Method).Path(route.Pattern).Name(route.Name).Handler(handler)
	}
	return router
}

var routes = []Route{
	Route{
		"Index",
		"GET",
		"/",
		Index,
	},
	Route{
		"Info",
		"GET",
		"/info",
		Info,
	},
	Route{
		"Health",
		"POST",
		"/health",
		Health,
	},
	Route{
		"VendorShow",
		"GET",
		"/vendors/{productId}",
		VendorShow,
	},
	Route{
		"UploadAttachment",
		"POST",
		"/{belong}/attachments",
		UploadAttachment,
	},
	Route{
		"DownloadAttachment",
		"GET",
		"/attachments/{id}",
		DownloadAttachment,
	},
}
