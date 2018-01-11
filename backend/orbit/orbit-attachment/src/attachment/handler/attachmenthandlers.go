package handler

import (
	"attachment/model"
	"attachment/service"
	"encoding/json"
	"github.com/gorilla/mux"
	"io"
	"net/http"
	"os"
	"strconv"
	"fmt"
	"log"
)

func UploadAttachment(writer http.ResponseWriter, request *http.Request) {
	vars := mux.Vars(request)
	file, header, err := request.FormFile("attachment")

	if file == nil || err != nil {
		model.WriteBadRequest(writer, request.RequestURI, "Failed to get attachment from request body")
		return
	}
	defer file.Close()

	inst, err := model.MakeFromRequest(header, vars);
	if err != nil {
		model.WriteBadRequest(writer, request.RequestURI, "Can't use request paramters to generate an attachment instance")
		return
	}

	f, err := os.OpenFile(inst.StoragePath(), os.O_WRONLY|os.O_CREATE, 0666)
	defer f.Close()
	_, ioerr := io.Copy(f, file)

	if ioerr != nil {
		model.WriteInternalServerError(writer, request.RequestURI, "Failed to write file to server")
		log.Println(ioerr.Error())
		return
	}

	json.NewEncoder(writer).Encode(service.SaveAttachment(inst))
}

func DownloadAttachment(writer http.ResponseWriter, request *http.Request) {
	vars := mux.Vars(request)
	id, err := strconv.ParseInt(vars["id"], 10, 64)
	if err != nil {
		message := fmt.Sprintf("Can't to format the parameter id. errorhandler => [%s] \r\n", err.Error())
		model.WriteBadRequest(writer, request.RequestURI, message)
		return
	}
	attachment, err := service.GetAttachment(id);
	if err == nil {
		download(writer, request, attachment);
	} else {
		var message = fmt.Sprintf("Can't find attachment with id [%d]", id)
		model.WriteNotFound(writer, request.RequestURI, message)
	}
}

func download(w http.ResponseWriter, r *http.Request, attachment model.Attachment) {
	filepath := attachment.StoragePath()
	w.Header().Set("Content-Type", "application/octet-stream")
	w.Header().Set("Content-Disposition", "attachment;filename=" + attachment.DownloadDisplayName())
	http.ServeFile(w, r, filepath)
}
