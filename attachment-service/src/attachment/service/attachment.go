package service

import (
	"attachment/config"
	"attachment/model"
	"attachment/redis"
	"attachment/util"
	"gopkg.in/guregu/null.v3"
	"strconv"
	"time"
)

func SaveAttachment(attachment model.Attachment) model.Attachment {
	attachment.ID = util.GetSnowflakeID()
	attachment.Status = null.IntFrom(model.ATTACHMENT_STATUS_TEMPORARY)
	attachment.Version = 0
	attachment.Creator = null.IntFrom(9999)
	attachment.CreateTime = model.Datetime(null.TimeFrom(time.Now()))
	redis.ESet("attachment:"+strconv.FormatInt(attachment.ID, 10), attachment, config.Apps.Attachment.TemporaryExpireTime)
	return attachment
}
