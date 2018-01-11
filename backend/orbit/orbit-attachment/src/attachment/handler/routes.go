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
	{
		"Index",
		"GET",
		"/",
		Index,
	},
	{
		"Info",
		"GET",
		"/info",
		Info,
	},
	{
		"Health",
		"POST",
		"/health",
		Health,
	},
	{
		"VendorShow",
		"GET",
		"/vendors/{productId}",
		VendorShow,
	},
	{
		"UploadAttachment",
		"POST",
		"/{belong}/attachments",
		getAttachmentHandlerInstance().UploadAttachment,
	},
	{
		"DownloadAttachment",
		"GET",
		"/attachments/{id}",
		getAttachmentHandlerInstance().DownloadAttachment,
	},
}
