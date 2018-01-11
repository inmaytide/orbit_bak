package dao

import (
	"attachment/model"
	"database/sql"
	"fmt"
)

const ATTACHMENT_FULL_COLUMNS = "id, original_name, storage_name, extension, storage_address, group_id, belong, size, status, create_time, update_time, creator, updater, version"
const ATTACHMENT_TABLE_NAME = "sys_attachment"

type AttachmentDao interface {
	putSql(key string, sql string)
	getSql(string) string
	Get(id int64) (model.Attachment, error)
	Insert(inst model.Attachment) (model.Attachment, error)
	Delete(id int64) error
}

type AttachmentDaoImpl struct {
	sqls map[string]string
}

func NewAttachmentDao() *AttachmentDao {
	var dao AttachmentDao = new(AttachmentDaoImpl)
	dao.putSql("get", fmt.Sprintf(SQL_PATTERN_GET, ATTACHMENT_FULL_COLUMNS, ATTACHMENT_TABLE_NAME))
	dao.putSql("deleteById", fmt.Sprintf(SQL_PATTERN_DELETE_BY_ID, ATTACHMENT_TABLE_NAME))
	return &dao
}

func (dao AttachmentDaoImpl) putSql(key string, sql string) {
	dao.sqls[key] = sql;
}

func (dao AttachmentDaoImpl) getSql(key string) string {
	return dao.sqls[key]
}

func (dao AttachmentDaoImpl) Get(id int64) (model.Attachment, error) {
	db := getBaseDaoInstance().getConn()
	rows, err := db.Query(dao.getSql("get"), id);
	if err != nil {
		return model.Attachment{}, nil
	}
	defer rows.Close()
	return buildAttachment(rows)
}

func (dao AttachmentDaoImpl) Insert(inst model.Attachment) (model.Attachment, error) {
	return model.Attachment{}, nil
}

func (dao AttachmentDaoImpl) Delete(id int64) (error) {
	return nil
}



func buildAttachment(rows *sql.Rows) (model.Attachment, error) {
	attachment := model.Attachment{}
	var err error
	if rows.Next() {
		err = rows.Scan(&attachment.ID, &attachment.OriginalName, &attachment.StorageName, &attachment.Extension,
			&attachment.StorageAddress, &attachment.Group, &attachment.Belong, &attachment.Size, &attachment.Status,
			&attachment.CreateTime, &attachment.UpdateTime, &attachment.Creator, &attachment.Updater, &attachment.Version)
	}
	return attachment, err
}
