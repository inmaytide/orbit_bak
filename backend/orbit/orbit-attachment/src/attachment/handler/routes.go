package handler

import (
	"github.com/gorilla/mux"
	"net/http"
	"attachment/model"
	"github.com/dgrijalva/jwt-go"
	"context"
)

type Route struct {
	Name        string
	Method      string
	Pattern     string
	HandlerFunc http.HandlerFunc
}

func wrappedHandler(inner http.Handler) http.Handler {
	return http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
		authorization := r.Header.Get("Authorization")
		if authorization == "" || len(authorization) < 7 || authorization[0:6] != "Bearer" {
			model.WriterForbidden(w, r.RequestURI, "No Authorization")
			return;
		}
		authorization = authorization[7:]
		token, err := jwt.Parse(authorization, func(token *jwt.Token) (interface{}, error) {
			return []byte("59a84cbf83227a35"), nil
		})
		if err != nil {
			model.WriterForbidden(w, r.RequestURI, err.Error())
			return
		}
		inner.ServeHTTP(w, r.WithContext(context.WithValue(r.Context(), "token", token)))
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
