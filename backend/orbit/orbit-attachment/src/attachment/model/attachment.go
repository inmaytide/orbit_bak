package model

import (
	"fmt"
	"gopkg.in/guregu/null.v3"
	"strconv"
	"path"
	"strings"
	"attachment/util"
	"os"
	"mime/multipart"
	"errors"
	"log"
)

const (
	ATTACHMENT_CACHE_NAME = "attachment:%d"
	ATTACHMENT_STATUS_FORMAL    = 944103312234389504
	ATTACHMENT_STATUS_TEMPORARY = 944103645861912576
)

type AttachmentHelper interface {
	StoragePath() string
	DownloadDisplayName() string
	CacheName() string
}

type Attachment struct {
	ID             int64       `json:"id"`
	OriginalName   null.String `json:"originalName"`
	StorageName    null.String `json:"storageName"`
	Extension      null.String `json:"extension"`
	StorageAddress null.String `json:"storageAddress"`
	Group          null.Int    `json:"group"`
	Belong         null.Int    `json:"belong"`
	Size           null.Int    `json:"size"`
	Status         null.Int    `json:"status"`
	Creator        null.Int    `json:"creator"`
	CreateTime     null.Time    `json:"createTime"`
}

func (inst Attachment) StoragePath() string {
	if !inst.StorageAddress.Valid || !inst.StorageName.Valid {
		panic("invalid attachment")
	}
	return fmt.Sprintf("%s/%s", inst.StorageAddress.String, inst.StorageName.String);
}

func (inst Attachment) DownloadDisplayName() string {
	return fmt.Sprintf("%s.%s", inst.OriginalName.String, inst.Extension.String);
}

func (inst *Attachment) CacheName() string {
	// if ID value is invalid, generate a new id for it
	if inst.ID == 0 {
		inst.ID = util.GetSnowflakeID();
	}
	return AttachmentCacheName(inst.ID)
}

func AttachmentCacheName(id int64) string {
	return fmt.Sprintf(ATTACHMENT_CACHE_NAME, id);
}

func MakeFromRequest(header *multipart.FileHeader, vars map[string]string) (Attachment, error) {
	belong, err := strconv.ParseInt(vars["belong"], 10, 64)
	var inst = Attachment{}
	if err != nil {
		log.Println(err.Error())
		return inst, errors.New("bad parameter belong")
	}
	inst.Extension = null.StringFrom(path.Ext(header.Filename)[1:])
	inst.OriginalName = null.StringFrom(strings.TrimSuffix(header.Filename, path.Ext(header.Filename)))
	inst.StorageName = null.StringFrom(util.GetUUID())
	inst.Belong = null.IntFrom(belong)
	inst.StorageAddress = null.StringFrom(os.TempDir())
	inst.Size = null.IntFrom(header.Size)
	return inst, nil
}
