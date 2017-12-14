package service

import (
	"attachment/model"
	"attachment/redis"
	"database/sql"
	"time"
)

func SaveAttachment(attachment *model.Attachment) {
	attachment.ID = 359812029197979648
	attachment.Status = sql.NullInt64{Int64: 123213123123123, Valid:true}
	next := time.Now().AddDate(0, 0, 1)
	err := redis.GetClient().Set(string(attachment.ID), attachment, time.Duration(next.Unix())).Err()
	if err != nil {
		panic(err)
	}
}