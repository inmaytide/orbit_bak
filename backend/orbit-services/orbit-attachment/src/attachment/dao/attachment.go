package dao

import (
	"attachment/model"
	"fmt"
)

type AttachmentRepository interface {
	Get(id int64) (model.Attachment, error)
	Create(inst model.Attachment) (model.Attachment, error)
	Delete(id int64) (int64, error)
}

type attachmentRepositoryImpl struct {
	dataSource *DataSource
}

func NewAttachmentRepository(ds *DataSource) AttachmentRepository {
	return &attachmentRepositoryImpl{
		dataSource: ds,
	}
}

func (repository *attachmentRepositoryImpl) Get(id int64) (model.Attachment, error) {
	var inst = model.Attachment{}
	if repository.dataSource.getConnection().Where("id = $1", id).First(&inst).RecordNotFound() {
		return inst, fmt.Errorf("Can't found attachment with %d", id)
	}
	return inst, nil
}

func (repository *attachmentRepositoryImpl) Create(inst model.Attachment) (model.Attachment, error) {
	err := repository.dataSource.getConnection().Create(&inst).Error
	if err != nil {
		return inst, err
	}
	return repository.Get(inst.ID)
}

func (repository *attachmentRepositoryImpl) Delete(id int64) (int64, error) {
	if err := repository.dataSource.getConnection().Delete(&model.Attachment{ID: id}).Error; err != nil {
		return 0, err
	}
	return 1, nil
}
