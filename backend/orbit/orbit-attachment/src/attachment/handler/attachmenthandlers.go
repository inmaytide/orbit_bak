package handler

import (
	"attachment/model"
	"attachment/service"
	"attachment/util"
	"encoding/json"
	"github.com/gorilla/mux"
	"gopkg.in/guregu/null.v3"
	"io"
	"net/http"
	"os"
	"path"
	"strconv"
	"strings"
	"attachment/redis"
	"attachment/dao"
	"fmt"
	"log"
)

func UploadAttachment(writer http.ResponseWriter, request *http.Request) {
	vars := mux.Vars(request)
	file, header, err := request.FormFile("attachment")

	if file == nil || err != nil {
		model.WriterResponseError(writer, http.StatusBadRequest, request.RequestURI, "Failed to get attachment from request body")
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

	f, err := os.OpenFile(attachmentUrl(inst), os.O_WRONLY|os.O_CREATE, 0666)
	defer f.Close()
	_, ioerr := io.Copy(f, file)

	if ioerr != nil {
		model.WriterResponseError(writer, http.StatusInternalServerError, request.RequestURI, "Failed to write file to server")
		log.Println(ioerr.Error())
		return
	}

	json.NewEncoder(writer).Encode(service.SaveAttachment(inst))
}

func DownloadAttachment(writer http.ResponseWriter, request *http.Request) {
	vars := mux.Vars(request)
	id, err := strconv.ParseInt(vars["id"], 10, 64)
	if err != nil {
		message := fmt.Sprintf("Can't to format the parameter id. error => [%s] \r\n", err.Error())
		model.WriterResponseError(writer, http.StatusBadRequest, request.RequestURI, message);
		return;
	}

	value := redis.Get(fmt.Sprintf("attachment:%d", id))
	var attachment = model.Attachment{}

	if value != nil && len(value) != 0 {
		err := json.Unmarshal(value, &attachment);
		if err != nil {
			var message = fmt.Sprintf("Can't to deserialization the data. data => [%s], err => [%s]", value, err.Error())
			model.WriterResponseError(writer, http.StatusInternalServerError, request.RequestURI, message);
			return;
		}
	} else {
		attachment = dao.GetAttachment(id)
	}

	if attachment.ID == id {
		download(writer, request, attachment);
	} else {
		var message = fmt.Sprintf("Can't find attachment with id [%d]", id)
		model.WriterResponseError(writer, http.StatusNotFound, request.RequestURI, message)
	}
}

func download(w http.ResponseWriter, r *http.Request, attachment model.Attachment) {
	filepath := attachmentUrl(attachment)
	w.Header().Set("Content-Type", "application/octet-stream")
	w.Header().Set("Content-Disposition", "attachment;filename=" + displayFilename(attachment))
	http.ServeFile(w, r, filepath);
}

func attachmentUrl(attachment model.Attachment) string {
	return fmt.Sprintf("%s/%s", attachment.StorageAddress.String, attachment.StorageName.String);
}

func displayFilename(attachment model.Attachment) string {
	return fmt.Sprintf("%s.%s", attachment.OriginalName.String, attachment.Extension.String)
}
