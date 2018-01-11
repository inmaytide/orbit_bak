package eureka

import "attachment/util"
import "os"
import "io/ioutil"
import "strings"
import "time"
import (
	"attachment/config"
	"fmt"
)

var instanceID string
var basicUrl string
var statusUrl string

// Register ...
func Register() {
	instanceID = util.GetUUID()
	basicUrl = strings.Join([]string{config.GetEurekaServer(), "apps/", config.GetApplicationName()}, "")
	statusUrl = strings.Join([]string{fmt.Sprintf("%s/%s", basicUrl, util.GetLocalIP()), config.GetApplicationName(), instanceID}, ":")

	dir, _ := os.Getwd()
	data, _ := ioutil.ReadFile(dir + "/resources/regtpl.json")

	tpl := string(data)
	tpl = strings.Replace(tpl, "${ip.address}", util.GetLocalIP(), -1)
	tpl = strings.Replace(tpl, "${port}", config.GetPort(), -1)
	tpl = strings.Replace(tpl, "${instanceID}", instanceID, -1)
	tpl = strings.Replace(tpl, "${application.name}", config.GetApplicationName(), -1)

	registerAction := HttpAction{
		URL:         basicUrl,
		Method:      "POST",
		ContentType: "application/json",
		Body:        tpl,
	}

	var result bool

	for {
		result = DoHttpRequest(registerAction)
		if result {
			break
		} else {
			time.Sleep(time.Second * 5)
		}
	}
}

// StartHeartbeat ...
func StartHeartbeat() {
	for {
		time.Sleep(time.Second * 30)
		heartbeat()
	}
}

func heartbeat() {
	heartbeatAction := HttpAction{
		URL:    statusUrl,
		Method: "PUT",
	}
	DoHttpRequest(heartbeatAction)
}

// Deregister ...
func Deregister() {
	fmt.Println("Trying to deregister application...")

	deregisterAction := HttpAction{
		URL:    statusUrl,
		Method: "DELETE",
	}

	DoHttpRequest(deregisterAction)

	fmt.Println("Deregistered application, exiting. Check Eureka...")
}
