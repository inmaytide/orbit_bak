package eureka

import "util"
import "os"
import "io/ioutil"
import "strings"
import "time"
import "fmt"

var instanceID string

// Register ...
func Register() {
	instanceID = util.GetUUID()

	dir, _ := os.Getwd()
	data, _ := ioutil.ReadFile(dir + "resources/application.json")

	tpl := string(data)
	tpl = strings.Replace(tpl, "${ipAddress}", util.GetLocalIP(), -1)
	tpl = strings.Replace(tpl, "${port}", "7002", -1)
	tpl = strings.Replace(tpl, "${instanceID}", instanceID, -1)

	registerAction := HttpAction{
		URL:         "http://127.0.0.1:7000/eureka/apps/vendor",
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
		URL:    "http://127.0.0.1:7000/eureka/apps/vendor/" + util.GetLocalIP() + ":vendor:" + instanceID,
		Method: "PUT",
	}
	DoHttpRequest(heartbeatAction)
}

// Deregister ...
func Deregister() {
	fmt.Println("Trying to deregister application...")

	deregisterAction := HttpAction{
		URL:    "http://127.0.0.1:7000/eureka/apps/vendor/" + util.GetLocalIP() + ":vendor:" + instanceID,
		Method: "DELETE",
	}

	DoHttpRequest(deregisterAction)

	fmt.Println("Deregistered application, exiting. Check Eureka...")
}
