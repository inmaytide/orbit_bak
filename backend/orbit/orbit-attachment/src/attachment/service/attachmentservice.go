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

type AttachmentService interface {
	setDao(dao dao.AttachmentDao)
	getDao() dao.AttachmentDao
	Save(attachment model.Attachment) (model.Attachment, error)
	Get(id int64) (model.Attachment, error)
	GetFormal(id int64) (model.Attachment, error)
	GetTemporary(id int64) (model.Attachment, error)
	Formal(id int64) (model.Attachment, error)
}

type AttachmentServiceImpl struct {
	dao dao.AttachmentDao
}

func (service *AttachmentServiceImpl) setDao(dao dao.AttachmentDao) {
	service.dao = dao
}

func (service *AttachmentServiceImpl) getDao() dao.AttachmentDao {
	return service.dao
}

func NewAttachmentService() *AttachmentService {
	var service AttachmentService = new(AttachmentServiceImpl)
	service.setDao(dao.NewAttachmentDao())
	return &service
}

func (service AttachmentServiceImpl) Save(attachment model.Attachment) (model.Attachment, error) {
	attachment.ID = util.GetSnowflakeID()
	attachment.Status = null.IntFrom(model.ATTACHMENT_STATUS_TEMPORARY)
	attachment.Version = 0
	attachment.Creator = null.IntFrom(9999)
	attachment.CreateTime = model.Datetime(null.TimeFrom(time.Now()))
	return attachment, redis.ESet(attachment.CacheName(), attachment, config.GetTemporaryExpireTime())
}

func (service AttachmentServiceImpl) Get(id int64) (model.Attachment, error) {
	attachment, err := service.GetTemporary(id)
	if err != nil {
		attachment, err = service.GetFormal(id)
	}
	return attachment, err
}

func (service AttachmentServiceImpl) GetFormal(id int64) (model.Attachment, error) {
	attachment, err := service.dao.Get(id)
	if err != nil || attachment.ID == 0 {
		return model.Attachment{}, errors.New(fmt.Sprintf("Failed to get attachment from database with id [%d]", id))
	}
	return attachment, nil
}

func (service AttachmentServiceImpl) GetTemporary(id int64) (model.Attachment, error){
	var attachment = model.Attachment{}
	value, err := redis.Get(model.AttachmentCacheName(id))
	if err != nil {
		return attachment, err
	}
	if value != nil && len(value) != 0 {
		err := json.Unmarshal(value, &attachment)
		if err != nil {
			var message = fmt.Sprintf("Can't to deserialization the data. data => [%s], err => [%s]", value, err.Error())
			errorhandler.Termination(err, message)
		} else {
			return attachment, nil
		}
	}
	return model.Attachment{}, errors.New(fmt.Sprintf("Could not found attachment instance from cache with id [%d]", id))
}

func (service AttachmentServiceImpl) Formal(id int64) (model.Attachment, error) {
	attachment, err := service.GetTemporary(id)
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
	dst, err := os.OpenFile(formalPath, os.O_WRONLY | os.O_CREATE, 0666)
	if err != nil {
		return attachment, errors.New(fmt.Sprintf("path => [%s], error => [%s]", formalPath, err.Error()))
	}
	defer dst.Close()

	_, err = io.Copy(dst, src)

	if err != nil {
		return model.Attachment{}, err
	}
	redis.Delete(attachment.CacheName())
	return service.Save(attachment)
}

func getFormalStorageAddress() string {
	folder := time.Now().Format("20060102")
	return fmt.Sprintf("%s/%s", config.GetFormalStorageAddress(), folder)
}
