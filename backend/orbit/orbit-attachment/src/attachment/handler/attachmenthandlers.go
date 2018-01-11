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
	"sync"
)

type AttachmentHandler struct {
	attachmentService service.AttachmentService
}

var attachmentHandlerInstance *AttachmentHandler
var once sync.Once

func getAttachmentHandlerInstance() *AttachmentHandler {
	once.Do(func() {
		attachmentHandlerInstance = &AttachmentHandler{}
		attachmentHandlerInstance.attachmentService = service.NewAttachmentService()
	})
	return attachmentHandlerInstance
}

func (handler AttachmentHandler) UploadAttachment(w http.ResponseWriter, r *http.Request) {
	vars := mux.Vars(r)
	file, header, err := r.FormFile("attachment")

	if file == nil || err != nil {
		model.WriteBadRequest(w, r.RequestURI, "Failed to get attachment from request body")
		return
	}
	defer file.Close()

	inst, err := model.MakeFromRequest(header, vars)
	if err != nil {
		model.WriteBadRequest(w, r.RequestURI, "Can't use request paramters to generate an attachment instance")
		return
	}

	f, err := os.OpenFile(inst.StoragePath(), os.O_WRONLY|os.O_CREATE, 0666)
	defer f.Close()
	_, err = io.Copy(f, file)

	if err != nil {
		model.WriteInternalServerError(w, r.RequestURI, "Failed to write file to server")
		log.Println(err.Error())
		return
	}

	inst, err = handler.attachmentService.Save(inst)

	if err != nil {
		model.WriteInternalServerError(w, r.RequestURI, "Failed to save temporary attachment instance to cache")
		log.Panicln(err.Error())
		return
	}
	json.NewEncoder(w).Encode(inst)
}

func (handler AttachmentHandler) DownloadAttachment(w http.ResponseWriter, r *http.Request) {
	vars := mux.Vars(r)
	id, err := strconv.ParseInt(vars["id"], 10, 64)
	if err != nil {
		message := fmt.Sprintf("Can't to format the parameter id. errorhandler => [%s] \r\n", err.Error())
		model.WriteBadRequest(w, r.RequestURI, message)
		return
	}
	attachment, err := handler.attachmentService.Get(id)
	if err == nil {
		download(w, r, attachment)
	} else {
		var message = fmt.Sprintf("Can't find attachment with id [%d]", id)
		model.WriteNotFound(w, r.RequestURI, message)
	}
}

func download(w http.ResponseWriter, r *http.Request, attachment model.Attachment) {
	filepath := attachment.StoragePath()
	w.Header().Set("Content-Type", "application/octet-stream")
	w.Header().Set("Content-Disposition", "attachment;filename=" + attachment.DownloadDisplayName())
	http.ServeFile(w, r, filepath)
}
