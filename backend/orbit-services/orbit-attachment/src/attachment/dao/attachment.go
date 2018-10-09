package dao

import (
	"attachment/model"
	"database/sql"
	"fmt"
	"errors"
)

const ATTACHMENT_FULL_COLUMNS = "id, original_name, storage_name, extension, storage_address, belong, size, status, creator"
const ATTACHMENT_TABLE_NAME = "attachment"

type AttachmentDao interface {
	Get(id int64) (model.Attachment, error)
	Insert(inst model.Attachment) (model.Attachment, error)
	Delete(id int64) (int64, error)
}

type AttachmentDaoImpl struct {
	sqlmap map[string]string
}

func NewAttachmentDao() AttachmentDao {
	var dao = new(AttachmentDaoImpl)
	dao.sqlmap = make(map[string]string)
	dao.sqlmap["get"] = fmt.Sprintf(SQL_PATTERN_GET, ATTACHMENT_FULL_COLUMNS, ATTACHMENT_TABLE_NAME)
	dao.sqlmap["deleteById"] = fmt.Sprintf(SQL_PATTERN_DELETE_BY_ID, ATTACHMENT_TABLE_NAME)
	dao.sqlmap["insert"] = fmt.Sprintf("insert into %s(%s) values(?, ?, ?, ?, ?, ?, ?, ?, ?)", ATTACHMENT_TABLE_NAME, ATTACHMENT_FULL_COLUMNS)
	return dao
}

func (dao AttachmentDaoImpl) Get(id int64) (model.Attachment, error) {
	rows, err := getInstance().getConnection().Query(dao.sqlmap["get"], id);
	if err != nil {
		return model.Attachment{}, nil
	}
	defer rows.Close()
	return buildAttachment(rows)
}

func (dao AttachmentDaoImpl) Insert(inst model.Attachment) (model.Attachment, error) {
	result, err := getInstance().getConnection().Exec(dao.sqlmap["insert"], inst.ID, inst.OriginalName, inst.StorageName, inst.Extension, inst.StorageAddress, inst.Belong, inst.Size, inst.Status, inst.Creator)
	if err != nil {
		return model.Attachment{}, err
	}
	if affected, err := result.RowsAffected(); err != nil || affected == 0 {
		return model.Attachment{}, errors.New("Insert failed")
	}
	return dao.Get(inst.ID)
}

func (dao AttachmentDaoImpl) Delete(id int64) (affected int64, err error) {
	result, err := getInstance().getConnection().Exec(dao.sqlmap["deleteById"], id)
	if err != nil {
		return 0, err
	}
	return result.RowsAffected()
}

func buildAttachment(rows *sql.Rows) (model.Attachment, error) {
	attachment := model.Attachment{}
	var err error
	if rows.Next() {
		err = rows.Scan(&attachment.ID, &attachment.OriginalName, &attachment.StorageName, &attachment.Extension,
			&attachment.StorageAddress, &attachment.Belong, &attachment.Size, &attachment.Status,
			&attachment.CreateTime, &attachment.Creator)
	}
	return attachment, err
}
