package service

import (
	"attachment/config"
	"attachment/model"
	"attachment/redis"
	"attachment/util"
	"gopkg.in/guregu/null.v3"
	"time"
	"encoding/json"
	"fmt"
	"attachment/dao"
	"os"
	"io"
	"log"
)

type AttachmentService interface {
	Save(attachment model.Attachment) (model.Attachment, error)
	Get(id int64) (model.Attachment, error)
	GetFormal(id int64) (model.Attachment, error)
	GetTemporary(id int64) (model.Attachment, error)
	Formal(id int64) (model.Attachment, error)
	Delete(id int64) (int, error)
}

type AttachmentServiceImpl struct {
	repository dao.AttachmentRepository
}

func NewAttachmentService(repository *dao.AttachmentRepository) AttachmentService {
	return &AttachmentServiceImpl{
		repository: repository,
	}
}

func (service *AttachmentServiceImpl) Save(attachment model.Attachment) (model.Attachment, error) {
	attachment.ID = util.GetSnowflakeID()
	return attachment, redis.GetClient().ESet(attachment.CacheName(), attachment, config.GetTemporaryExpireTime())
}

func (service *AttachmentServiceImpl) Get(id int64) (model.Attachment, error) {
	attachment, err := service.GetTemporary(id)
	if err != nil {
		attachment, err = service.GetFormal(id)
	}
	return attachment, err
}

func (service *AttachmentServiceImpl) GetFormal(id int64) (model.Attachment, error) {
	attachment, err := service.repository.Get(id)
	if err != nil || attachment.ID == 0 {
		return attachment, fmt.Errorf("Failed to get attachment from database with id [%d]", id)
	}
	return attachment, nil
}

func (service *AttachmentServiceImpl) GetTemporary(id int64) (model.Attachment, error) {
	var attachment model.Attachment
	value, err := redis.GetClient().Get(model.AttachmentCacheName(id))
	if err != nil {
		return attachment, err
	}
	if value != nil && len(value) != 0 {
		err := json.Unmarshal(value, &attachment)
		if err != nil {
			return attachment, fmt.Errorf("Can't to deserialization the data. data => [%s], err => [%s]", value, err.Error())
		}
		return attachment, nil
	}
	return attachment, fmt.Errorf("Could not found attachment instance from redis with id [%d]", id)
}

func (service *AttachmentServiceImpl) Formal(id int64) (model.Attachment, error) {
	attachment, err := service.GetTemporary(id)
	if err != nil {
		return attachment, err
	}

	err = formal(&attachment)
	if err != nil {
		return attachment, nil
	}

	attachment, err = service.repository.Create(attachment)
	if err != nil {
		return attachment, err
	}

	return attachment, redis.GetClient().Delete(attachment.CacheName())
}

func (service *AttachmentServiceImpl) Delete(id int64) (int, error) {
	attachment, err := service.GetFormal(id)
	if err != nil {
		return 0, err
	}

	affected, err := service.repository.Delete(id)
	if err != nil || affected == 0 {
		return 0, fmt.Errorf("Failed to remove attachment with id [%d]. Cause by: %s", id, err.Error())
	}

	err = os.Remove(attachment.StoragePath())
	if err != nil {
		log.Printf("Failed to remove attachment file. Cause by: %s", err.Error())
	}

	return 1, nil
}

func getFormalStorageAddress() string {
	folder := time.Now().Format("20060102")
	address := fmt.Sprintf("%s/%s", config.GetFormalStorageAddress(), folder)
	err := os.MkdirAll(address, os.ModePerm)
	if err != nil {
		log.Panicf("Can't to create storage folder for attachment. Cause by: %s \n", err.Error())
	}
	return address
}

func formal(inst *model.Attachment) error {
	path := inst.StoragePath()
	src, err := os.Open(path)
	if err != nil {
		return err
	}
	defer src.Close()

	inst.StorageAddress = null.StringFrom(getFormalStorageAddress())
	formalPath := inst.StoragePath()
	dst, err := os.OpenFile(formalPath, os.O_WRONLY|os.O_CREATE, os.ModePerm)
	if err != nil {
		return err
	}
	defer dst.Close()

	_, err = io.Copy(dst, src)
	return err
}
