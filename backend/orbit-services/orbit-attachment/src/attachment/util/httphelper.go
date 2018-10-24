package util

import (
	"net/http"
	"fmt"
	"io/ioutil"
)

func DoRequest(r *http.Request) ([]byte, error) {
	response, err := http.DefaultClient.Do(r)
	if err != nil || response.StatusCode != http.StatusOK {
		return nil, fmt.Errorf("Remote service is not available, cause by: [%s]", r.URL.RawPath, err.Error())
	}
	defer response.Body.Close()
	body, err := ioutil.ReadAll(response.Body)
	if err != nil {
		return nil, err
	}
	return body, nil
}
