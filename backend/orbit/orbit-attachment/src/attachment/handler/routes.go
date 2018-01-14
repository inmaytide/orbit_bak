package handler

import (
	"attachment/model"
	"context"
	"github.com/gorilla/mux"
	"net/http"
)

type Route struct {
	Name        string
	Method      string
	Pattern     string
	HandlerFunc http.HandlerFunc
}

func wrappedHandler(inner http.Handler) http.Handler {
	return http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
		user, err := model.VisitorResolver(r)
		if err != nil {
			model.WriterForbidden(w, r.RequestURI, err.Error())
			return
		}
		if user.ID == 0 {
			model.WriterForbidden(w, r.RequestURI, "Invalid authorization")
			return
		}

		inner.ServeHTTP(w, r.WithContext(context.WithValue(r.Context(), "user", user)))
	})
}

func NewRouter() *mux.Router {
	router := mux.NewRouter().StrictSlash(true)
	for _, route := range routes {
		var handler http.Handler
		handler = route.HandlerFunc
		handler = wrappedHandler(handler)
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
	{
		"FormalAttachment",
		"PATCH",
		"/attachments/{id}/as-formal",
		getAttachmentHandlerInstance().FormalAttachment,
	},
}
