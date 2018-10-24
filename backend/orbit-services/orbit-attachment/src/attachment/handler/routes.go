package handler

import (
	"attachment/model"
	"context"
	"github.com/gorilla/mux"
	"net/http"
	"attachment/eureka"
)

type Route struct {
	Name          string
	Method        string
	Pattern       string
	Authenticated bool
	HandlerFunc   http.HandlerFunc
}

func wrappedHandler(inner http.Handler, authenticated bool) http.Handler {
	return http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
		if !authenticated {
			inner.ServeHTTP(w, r);
		} else {
			visitor, err := model.VisitorResolver(r)
			if err != nil {
				WriterForbidden(w, r.RequestURI, err.Error())
				return
			}
			if visitor.ID == 0 {
				WriterForbidden(w, r.RequestURI, "Invalid authorization")
				return
			}
			inner.ServeHTTP(w, r.WithContext(context.WithValue(r.Context(), "visitor", visitor)))
		}

	})
}

func NewRouter() *mux.Router {
	router := mux.NewRouter().StrictSlash(true)
	for _, route := range routes {
		var handler http.Handler
		handler = route.HandlerFunc
		handler = wrappedHandler(handler, route.Authenticated)
		router.Methods(route.Method).Path(route.Pattern).Name(route.Name).Handler(handler)
	}
	return router
}

var routes = []Route{
	{
		"Index",
		"GET",
		"/",
		false,
		eureka.Index,
	},
	{
		"Info",
		"GET",
		"/info",
		false,
		eureka.Info,
	},
	{
		"Health",
		"POST",
		"/health",
		false,
		eureka.Health,
	},
	{
		"VendorShow",
		"GET",
		"/vendors/{productId}",
		false,
		eureka.VendorShow,
	},
	{
		"UploadAttachment",
		"POST",
		"/attachments/{belong}",
		true,
		UploadAttachment,
	},
	{
		"DownloadAttachment",
		"GET",
		"/attachments/{id}",
		false,
		DownloadAttachment,
	},
	{
		"FormalAttachment",
		"PATCH",
		"/attachments/{id}/as-formal",
		true,
		FormalAttachment,
	},
}
