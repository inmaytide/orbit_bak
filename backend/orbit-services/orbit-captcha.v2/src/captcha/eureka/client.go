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
	Registered     bool
}

var instances []Instance
var rawTpl []byte

func processTpl(env *config.Configuration, id string) string {
	tpl := string(rawTpl)
	tpl = strings.Replace(tpl, "${ip.address}", getLocalIP(), -1)
	tpl = strings.Replace(tpl, "${port}", fmt.Sprintf("%d", env.Application.Server.Port), -1)
	tpl = strings.Replace(tpl, "${instanceID}", id, -1)
	tpl = strings.Replace(tpl, "${application.name}", env.Application.Name, -1)
	return tpl
}

func makeInstance(env *config.Configuration, eureka string) Instance {
	id := strings.Replace(uuid.NewV4().String(), "-", "", -1)
	baseURL := fmt.Sprintf("%s/apps/%s", eureka, env.Application.Name)
	tpl := processTpl(env, id)
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
		Registered: false,
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
	dir, err := os.Getwd()
	if err != nil {
		log.Println("Failed to get program root directory")
		log.Println(err)
		return
	}
	if rawTpl, err = ioutil.ReadFile(dir + "/regtpl.json"); err != nil {
		log.Printf("Failed to read %s/regtpl.json file \r\n", dir)
		log.Println(err)
		return
	}

	for i, n := range servers {
		instances[i] = makeInstance(env, n)
		go register(instances[i])
	}
}

func register(instance Instance) {
	for {
		if DoHttpRequest(instance.RegisterAction) {
			instance.Registered = true
			go heartbeat(instance)
			// log.Printf("Registered: %s \r\n", instance.BaseURL)
			break
		} else {
			time.Sleep(time.Second * 5)
		}
	}
}

func heartbeat(instance Instance) {
	for {
		heartbeatAction := HttpAction{
			URL:    instance.StatusURL,
			Method: "PUT",
		}
		if DoHttpRequest(heartbeatAction) {
			time.Sleep(time.Second * 30)
		} else {
			// log.Printf("Heartbeat failure: %s \r\n", instance.BaseURL)
			instance.Registered = false
			register(instance)
			break
		}

	}
}

// Deregister ...
func Deregister() {
	if len(instances) == 0 {
		return
	}
	log.Println("Trying to deregister application...")
	for _, n := range instances {
		if n.Registered {
			deregister(n)
		}
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
