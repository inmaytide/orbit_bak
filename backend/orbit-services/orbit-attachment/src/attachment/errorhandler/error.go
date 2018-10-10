package errorhandler

import "log"

func Print(err error, message string) {
	if err != nil {
		log.Println(message)
		log.Println(err.Error())
	}
}

func Printf(err error, message string, v... interface{}) {
	if err != nil {
		log.Printf(message + "\n", v)
		log.Println(err.Error())
	}
}

func Terminate(err error, message string) {
	if err != nil {
		log.Println(message)
		log.Fatal(err.Error())
	}
}

func Terminatef(err error, message string, v...interface{}) {
	if err != nil {
		log.Printf(message + "\n", v)
		log.Fatal(err.Error())
	}
}