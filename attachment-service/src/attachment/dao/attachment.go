package dao

import (
	"attachment/model"
	"fmt"
	"database/sql"
)

const attachmentFullColumns = "id, original_name, storage_name, extension, storage_address, group_id, belong, size, status, create_time, update_time, creator, updater, version"
const attachmentTableName = "sys_attachment"


func buildAttachment(rows *sql.Rows) model.Attachment {
	attachment := model.Attachment{}

	if rows.Next() {
		rows.Scan(&attachment.ID, &attachment.OriginalName, &attachment.StorageName, &attachment.Extension,
			&attachment.StorageAddress, &attachment.Group, &attachment.Belong, &attachment.Size, &attachment.Status,
			&attachment.CreateTime, &attachment.UpdateTime, &attachment.Creator, &attachment.Updater, &attachment.Version)
	}
	return attachment
}

func GetAttachment(id uint64) model.Attachment {
	db := GetConn()
	defer db.Close()

	rows, err := db.Query(fmt.Sprintf("select %s from %s where id = ?", attachmentFullColumns, attachmentTableName), id)
	defer rows.Close()
	CheckError(err)

	return buildAttachment(rows)
}

func SaveAttachment(inst model.Attachment) {

}

func DeleteAttachment(id uint64) int64 {
	db := GetConn();
	defer db.Close()

	result, err :=db.Exec(fmt.Sprintf("delete from %s where id = ?", attachmentTableName), id)
	CheckError(err)

	affected, err := result.RowsAffected()
	CheckError(err)

	return affected
}
