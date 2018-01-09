package model

import (
	"time"
	"net/http"
	"encoding/json"
	"log"
)

type ResponseError struct {
	Timestamp int64  `json:"timestamp"`
	Status    int    `json:"status"`
	Path      string `json:"path"`
	Message   string `json:"message"`
}

func buildResponseError(status int, path string, message string) ResponseError {
	return ResponseError{
		Timestamp: time.Now().Unix(),
		Status:    status,
		Path:      path,
		Message:   message,
	}
}

func WriterResponseError(writer http.ResponseWriter, status int, path string, message string) error {
	log.Println(message)
	writer.Header().Set("Content-Type", "application/json; charset=UTF-8")
	writer.WriteHeader(status);
	return json.NewEncoder(writer).Encode(buildResponseError(status, path, message));
}
