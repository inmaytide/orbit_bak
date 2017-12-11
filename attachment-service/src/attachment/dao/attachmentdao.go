package dao

import (
	"attachment/model"
	"fmt"
)

const ATTACHMENT_FULL_COLUMNS = "id, original_name, storage_name, extension, storage_address, " +
	"group_id, belong, size, status, create_time, update_time, creator, updater, version"

const TABLE_NAME = "sys_attachment"

func GetAttachment(id uint64) model.Attachment {
	db := GetConn()
	defer db.Close()
	sql := fmt.Sprintf("select %s from %s where id = ?", ATTACHMENT_FULL_COLUMNS, TABLE_NAME)
	rows, err := db.Query(sql, id)
	CheckError(err)
	attachment := model.Attachment{}
	if rows.Next() {
		rows.Scan(&attachment.ID, &attachment.OriginalName, &attachment.StorageName, &attachment.Extension, &attachment.StorageAddress,
			&attachment.Group, &attachment.Belong, &attachment.Size, &attachment.Status, &attachment.CreateTime, &attachment.UpdateTime,
			&attachment.Creator, &attachment.Updater, &attachment.Version)
	}
	return attachment
}
