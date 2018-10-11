package handler

import (
	"attachment/model"
	"attachment/service"
	"encoding/json"
	"fmt"
	"github.com/gorilla/mux"
	"gopkg.in/guregu/null.v3"
	"io"
	"log"
	"net/http"
	"os"
	"strconv"
)

type AttachmentHandler struct {
	attachmentService service.AttachmentService
}

func NewAttachmentHandler(service service.AttachmentService) *AttachmentHandler {
	return &AttachmentHandler{
		attachmentService: service,
	}
}

func (handler AttachmentHandler) UploadAttachment(w http.ResponseWriter, r *http.Request) {
	vars := mux.Vars(r)
	file, header, err := r.FormFile("files")

	if file == nil || err != nil {
		model.WriteBadRequest(w, r.RequestURI, "Failed to get attachment from request body")
		return
	}
	defer file.Close()

	inst, err := model.MakeFromRequest(header, vars)
	if err != nil {
		model.WriteBadRequest(w, r.RequestURI, "Can't use request parameters to generate an attachment instance")
		return
	}
	inst.Creator = null.IntFrom(r.Context().Value("visitor").(model.User).ID)

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
		model.WriteInternalServerError(w, r.RequestURI, "Failed to save temporary attachment instance to redis")
		log.Panicln(err.Error())
		return
	}

	body, err := json.Marshal(inst)
	w.Write(body)
}

func (handler AttachmentHandler) FormalAttachment(w http.ResponseWriter, r *http.Request) {
	vars := mux.Vars(r)
	id, err := getIdFormVars(vars)
	if err != nil {
		model.WriteBadRequest(w, r.RequestURI, err.Error())
		return
	}

	attachment, err := handler.attachmentService.Formal(id)
	if err != nil {
		model.WriteInternalServerError(w, r.RequestURI, err.Error())
		return
	}

	body, err := json.Marshal(attachment)
	w.Write(body)
}

func (handler AttachmentHandler) DownloadAttachment(w http.ResponseWriter, r *http.Request) {
	vars := mux.Vars(r)
	id, err := getIdFormVars(vars)
	if err != nil {
		model.WriteBadRequest(w, r.RequestURI, err.Error())
		return
	}

	attachment, err := handler.attachmentService.Get(id)
	if err != nil {
		model.WriteNotFound(w, r.RequestURI, fmt.Sprintf("Can't find attachment with id [%d]", id))
		return
	}

	download(w, r, attachment)
}

func download(w http.ResponseWriter, r *http.Request, attachment model.Attachment) {
	filepath := attachment.StoragePath()
	w.Header().Set("Content-Type", "application/octet-stream")
	w.Header().Set("Content-Disposition", "attachment;filename="+attachment.DownloadDisplayName())
	http.ServeFile(w, r, filepath)
}

func getIdFormVars(vars map[string]string) (int64, error) {
	id, err := strconv.ParseInt(vars["id"], 10, 64)
	if err != nil {
		return 0, fmt.Errorf("can't to format the parameter id. cause by: [%s]", err.Error())
	}
	return id, nil
}
