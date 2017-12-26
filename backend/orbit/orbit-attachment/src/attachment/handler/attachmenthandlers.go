package handler

import (
	"attachment/model"
	"attachment/service"
	"attachment/util"
	"encoding/json"
	"github.com/gorilla/mux"
	"gopkg.in/guregu/null.v3"
	"io"
	"log"
	"net/http"
	"os"
	"path"
	"strconv"
	"strings"
	"attachment/redis"
)

func UploadAttachment(writer http.ResponseWriter, request *http.Request) {
	vars := mux.Vars(request)
	file, header, err := request.FormFile("attachment")

	if file == nil || err != nil {
		response := model.BuildResponseError(http.StatusBadRequest, request.RequestURI, "Failed to get attachment from request body")
		json.NewEncoder(writer).Encode(response)
		return
	}
	defer file.Close()

	belong, _ := strconv.ParseInt(vars["belong"], 10, 64)
	var inst = model.Attachment{}
	inst.Extension = null.StringFrom(path.Ext(header.Filename)[1:])
	inst.OriginalName = null.StringFrom(strings.TrimSuffix(header.Filename, path.Ext(header.Filename)))
	inst.StorageName = null.StringFrom(util.GetUUID())
	inst.Belong = null.IntFrom(belong)
	inst.StorageAddress = null.StringFrom(os.TempDir())
	inst.Size = null.IntFrom(header.Size)

	f, err := os.OpenFile(inst.StorageAddress.String+"/"+inst.StorageName.String, os.O_WRONLY|os.O_CREATE, 0666)
	defer f.Close()
	_, ioerr := io.Copy(f, file)

	if ioerr != nil {
		log.Fatalln(ioerr.Error())
		response := model.BuildResponseError(http.StatusInternalServerError, request.RequestURI, "Failed to write file to server")
		json.NewEncoder(writer).Encode(response)
		return
	}

	json.NewEncoder(writer).Encode(service.SaveAttachment(inst))
}

func DownloadAttachment(writer http.ResponseWriter, request *http.Request) {
	vars := mux.Vars(request)
	id := vars["id"]
	value := redis.Get("attachment:" + id)
	var attachment = model.Attachment{}

	err := json.Unmarshal(value, &attachment);

	if err != nil {
		log.Fatalf("Can't to deserialization the data. data => [%s], err => [%s]\r\n", value, err.Error())
	}

	json.NewEncoder(writer).Encode(attachment)
}
