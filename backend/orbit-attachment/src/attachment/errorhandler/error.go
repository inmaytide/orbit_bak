package errorhandler

import "log"

func Throws(err error, message string) {
	if err != nil {
		log.Printf("error message => [%s] \n", err.Error())
		log.Panicln(message)
	}
}

func Throwsf(err error, message string, v... interface{}) {
	if err != nil {
		log.Printf("error message => [%s] \n", err.Error())
		log.Panicf(message + "\n", v)
	}
}

func Termination(err error, message string) {
	if err != nil {
		log.Printf("error message => [%s] \n", err.Error())
		log.Fatalln(message)
	}
}

func Terminationf(err error, message string, v...interface{}) {
	if err != nil {
		log.Printf("error message => [%s] \n", err.Error())
		log.Fatalf(message + "\n", v)
	}
}