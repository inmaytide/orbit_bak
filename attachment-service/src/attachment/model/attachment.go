package model

import (
	"time"
	"database/sql"
	"encoding/json"
)

type Attachment struct {
	ID             uint64
	OriginalName   sql.NullString
	StorageName    sql.NullString
	Extension      sql.NullString
	StorageAddress sql.NullString
	Group          sql.NullInt64
	Belong         sql.NullInt64
	Size           sql.NullInt64
	Status         sql.NullInt64
	Creator        sql.NullInt64
	CreateTime     time.Time
	Updater        sql.NullInt64
	UpdateTime     time.Time
	Version        uint
}

func (attachment Attachment) MarshalBinary() (byte[], err){

	return []byte(json.Encoder{}.Encode())

}
