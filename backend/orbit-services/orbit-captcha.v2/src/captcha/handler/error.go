package handler

import (
	"time"
	"net/http"
	"encoding/json"
	"log"
	"io"
)

type ResponseError struct {
	Timestamp int64  `json:"timestamp"`
	Status    int    `json:"status"`
	Path      string `json:"path"`
	Message   string `json:"message"`
}

func (response ResponseError) write(w io.Writer) error {
	return json.NewEncoder(w).Encode(response);
}

func buildResponseError(status int, path string, message string) ResponseError {
	return ResponseError{
		Timestamp: time.Now().Unix(),
		Status:    status,
		Path:      path,
		Message:   message,
	}
}

func WriteResponseError(w http.ResponseWriter, status int, path string, message string) {
	log.Println(message)
	w.Header().Set("Content-Type", "application/json; charset=UTF-8")
	w.WriteHeader(status);
	if err := buildResponseError(status, path, message).write(w); err != nil {
		panic(err)
	}
}

func WriteBadRequest(w http.ResponseWriter, path string, message string) {
	WriteResponseError(w, http.StatusBadRequest, path, message);
}

func WriteNotFound(w http.ResponseWriter, path string, message string) {
	WriteResponseError(w, http.StatusNotFound, path, message);
}

func WriteInternalServerError(w http.ResponseWriter, path string, message string) {
	WriteResponseError(w, http.StatusInternalServerError, path, message);
}

func WriterForbidden(w http.ResponseWriter, path string, message string) {
	WriteResponseError(w, http.StatusForbidden, path, message)
}
