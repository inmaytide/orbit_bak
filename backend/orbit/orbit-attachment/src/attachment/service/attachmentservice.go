package service

import (
	"attachment/config"
	"attachment/model"
	"attachment/redis"
	"attachment/util"
	"gopkg.in/guregu/null.v3"
	"time"
	"errors"
	"encoding/json"
	"fmt"
	"attachment/dao"
	"attachment/errorhandler"
	"os"
	"io"
)


type AttachmentServiceImpl struct {
	dao dao.AttachmentDao
}

func NewAttachmentService() *AttachmentServiceImpl {
	var service = new(AttachmentServiceImpl)
	service.dao = dao.NewAttachmentDao()
	return service
}

func (service AttachmentServiceImpl) SaveAttachment(attachment model.Attachment) model.Attachment {
	attachment.ID = util.GetSnowflakeID()
	attachment.Status = null.IntFrom(model.ATTACHMENT_STATUS_TEMPORARY)
	attachment.Version = 0
	attachment.Creator = null.IntFrom(9999)
	attachment.CreateTime = model.Datetime(null.TimeFrom(time.Now()))
	redis.ESet(attachment.CacheName(), attachment, config.GetTemporaryExpireTime())
	return attachment
}

func (service AttachmentServiceImpl) GetAttachment(id int64) (model.Attachment, error) {
	attachment, err := GetTemporaryAttachment(id)
	if err != nil {
		attachment, err = GetFormalAttachment(id)
	}
	return attachment, err
}

func GetFormalAttachment(id int64) (model.Attachment, error) {
	attachment := dao.GetAttachment(id)
	if attachment.ID == 0 {
		return model.Attachment{}, errors.New(fmt.Sprintf("Failed to get attachment from database with id [%d]", id))
	}
	return attachment, nil;
}

func GetTemporaryAttachment(id int64) (model.Attachment, error){
	var attachment = model.Attachment{}
	value := redis.Get(model.AttachmentCacheName(id))
	if value != nil && len(value) != 0 {
		err := json.Unmarshal(value, &attachment);
		if err != nil {
			var message = fmt.Sprintf("Can't to deserialization the data. data => [%s], err => [%s]", value, err.Error())
			errorhandler.Termination(err, message)
		} else {
			return attachment, nil
		}
	}
	return model.Attachment{}, errors.New(fmt.Sprintf("Could not found attachment instance from cache with id [%d]", id))
}

func FormalAttachment(id int64) (model.Attachment, error) {
	attachment, err := GetTemporaryAttachment(id);
	if err != nil {
		return attachment, err
	}

	attachment.Status = null.IntFrom(model.ATTACHMENT_STATUS_FORMAL)
	path := attachment.StoragePath()
	src, err := os.Open(path)
	if err != nil {
		return attachment, errors.New(fmt.Sprintf("Failed to read file. path => [%s], error => [%s]", path, err.Error()))
	}
	defer src.Close()

	attachment.StorageAddress = null.StringFrom(getFormalStorageAddress())
	formalPath := attachment.StoragePath()
	dst, err := os.OpenFile(formalPath, os.O_WRONLY | os.O_CREATE, 0666);
	if err != nil {
		return attachment, errors.New(fmt.Sprintf("path => [%s], error => [%s]", formalPath, err.Error()))
	}
	defer dst.Close()

	_, err = io.Copy(dst, src)

	if err != nil {
		return model.Attachment{}, err
	}
	redis.Delete(attachment.CacheName());
	return dao.SaveAttachment(attachment), nil
}

func getFormalStorageAddress() string {
	folder := time.Now().Format("20060102")
	return fmt.Sprintf("%s/%s", config.GetFormalStorageAddress(), folder);
}
