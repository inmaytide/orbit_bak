package handler

import (
	"github.com/mojocn/base64Captcha"
	"net/http"
	"log"
	"encoding/json"
	"github.com/gorilla/mux"
)

const HEADER_NAME_CPATCHA_ID = "x-captcha-name"

var defaultConfig = base64Captcha.ConfigCharacter{
	Height:             60,
	Width:              260,
	Mode:               base64Captcha.CaptchaModeArithmetic,
	ComplexOfNoiseText: base64Captcha.CaptchaComplexLower,
	ComplexOfNoiseDot:  base64Captcha.CaptchaComplexLower,
	IsUseSimpleFont:    false,
	IsShowHollowLine:   false,
	IsShowNoiseDot:     false,
	IsShowNoiseText:    true,
	IsShowSlimeLine:    false,
	IsShowSineLine:     false,
	CaptchaLen:         6,
}

type Captcha struct {
	ID    string `json:"captchaName"`
	Image string `json:"image"`
}

func SetCaptchaConfig(config base64Captcha.ConfigCharacter) {
	defaultConfig = config
}

func Generate() Captcha {
	id, instance := base64Captcha.GenerateCaptcha("", defaultConfig)
	return Captcha{
		ID:    id,
		Image: base64Captcha.CaptchaWriteToBase64Encoding(instance),
	}
}

func GenerateAndWriteToHttpResponseWriter(w http.ResponseWriter, r *http.Request) {
	w.Header().Set("Content-Type", "application/json;charset=utf-8")
	err := json.NewEncoder(w).Encode(Generate())
	if err != nil {
		log.Println("Failed to write captcha to http response")
		log.Println(err)
		WriteInternalServerError(w, r.RequestURI, "Failed to write captcha to http response")
	}
}

func Validate(w http.ResponseWriter, r *http.Request) {
	vars := mux.Vars(r);
	captcha := vars["captcha"]
	id := r.Header.Get(HEADER_NAME_CPATCHA_ID)
	isValid := base64Captcha.VerifyCaptcha(id, captcha)

	body := make(map[string]bool)
	body["isValid"] = isValid

	w.Header().Set("Content-Type", "application/json;charset=utf-8")
	err := json.NewEncoder(w).Encode(body)
	if err != nil {
		log.Println("Failed to write validate result to http response")
		log.Println(err)
		WriteInternalServerError(w, r.RequestURI, "Failed to write validate result to http response")
	}
}
