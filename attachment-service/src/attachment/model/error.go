package model

import (
	"time"
)

type ResponseError struct {
	Timestamp int64  `json:"timestamp"`
	Status    int    `json:"status"`
	Path      string `json:"path"`
	Message   string `json:"message"`
}

func BuildResponseError(status int, path string, message string) ResponseError {
	return ResponseError{
		Timestamp: time.Now().Unix(),
		Status:    status,
		Path:      path,
		Message:   message,
	}
}
