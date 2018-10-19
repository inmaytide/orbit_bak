package eureka

import (
	"fmt"
	"strings"
	"os"
	"io/ioutil"
	"time"
	"log"
	"captcha/config"
	"net"
	"github.com/satori/go.uuid"
	"net/http"
)

type Instance struct {
	ID             string
	BaseURL        string
	StatusURL      string
	RegisterAction HttpAction
}

var instances []Instance

func makeInstance(data []byte, env *config.Configuration, eureka string) Instance {
	id := strings.Replace(uuid.NewV4().String(), "-", "", -1)
	baseURL := fmt.Sprintf("%s/apps/%s", eureka, env.Application.Name)
	tpl := string(data)
	tpl = strings.Replace(tpl, "${ip.address}", getLocalIP(), -1)
	tpl = strings.Replace(tpl, "${port}", fmt.Sprintf("%d", env.Application.Server.Port), -1)
	tpl = strings.Replace(tpl, "${instanceID}", id, -1)
	tpl = strings.Replace(tpl, "${application.name}", env.Application.Name, -1)
	return Instance{
		ID:        id,
		BaseURL:   baseURL,
		StatusURL: fmt.Sprintf("%s/%s:%s:%s", baseURL, getLocalIP(), env.Application.Name, id),
		RegisterAction: HttpAction{
			URL:         baseURL,
			Method:      http.MethodPost,
			ContentType: "application/json",
			Body:        tpl,
		},
	}
}

// Register ...
func Register(env *config.Configuration) {
	if env.Eureka == "" {
		log.Println("Cannot found available eureka server...")
		return
	}

	servers := strings.Split(env.Eureka, ",")
	instances = make([]Instance, len(servers))
	dir, _ := os.Getwd()
	data, _ := ioutil.ReadFile(dir + "/regtpl.json")

	for i, n := range servers {
		instances[i] = makeInstance(data, env, n)
	}

	for _, n := range instances {
		go (func() {
			var result bool
			for {
				result = DoHttpRequest(n.RegisterAction)
				if result {
					break
				} else {
					time.Sleep(time.Second * 5)
				}
			}
		})()
	}
}

// StartHeartbeat ...
func StartHeartbeat() {
	if len(instances) == 0 {
		return
	}
	for _, n := range instances {
		go func() {
			for {
				time.Sleep(time.Second * 30)
				heartbeat(n)
			}
		}()
	}
}

func heartbeat(instance Instance) {
	heartbeatAction := HttpAction{
		URL:    instance.StatusURL,
		Method: "PUT",
	}
	DoHttpRequest(heartbeatAction)
}

// Deregister ...
func Deregister() {
	if len(instances) == 0 {
		return
	}
	log.Println("Trying to deregister application...")
	for _, n := range instances {
		deregister(n)
	}
	log.Println("Deregistered application, exiting. Check Eureka...")
}

func deregister(instance Instance) {
	deregisterAction := HttpAction{
		URL:    instance.StatusURL,
		Method: "DELETE",
	}
	DoHttpRequest(deregisterAction)
}

func getLocalIP() string {
	host, _ := os.Hostname()
	addrs, _ := net.LookupIP(host)

	for _, addr := range addrs {
		if ipv4 := addr.To4(); ipv4 != nil {
			return ipv4.String()
		}
	}
	return ""
}
