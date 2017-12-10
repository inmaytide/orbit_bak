package eureka

import (
	"net/http"
	"strings"
	"log"
	"crypto/tls"
)

func doHttpRequest(action HttpAction) bool {
	request := buildHttpRequest(action)

	var defaultTransport http.RoundTripper = &http.Transport{
		TLSClientConfig: &tls.Config{InsecureSkipVerify: true},
	}

	response, err := defaultTransport.RoundTrip(request)

	if err != nil {
		log.Printf("Http request failed: %s", err)
	} else {
		return true
		defer response.Body.Close()
	}

	return false
}

func buildHttpRequest(action HttpAction) *http.Request {
	var request *http.Request
	var err error
	var reader *strings.Reader = nil

	if action.Body != "" {
		reader = strings.NewReader(action.Body)
	} else if action.Template != "" {
		reader = strings.NewReader(action.Template)
	}

	request, err = http.NewRequest(action.Method, action.Url, reader)

	if err != nil {
		log.Fatal(err)
	}

	return request
}

func trimChar(str string, char byte) string {
	length := len(str)
	if length > 0 && str[length-1] == char {
		str = str[:length-1]
	}
	length = len(str)
	if length > 0 && str[0] == char {
		str = str[1:length]
	}
	return str;
}
