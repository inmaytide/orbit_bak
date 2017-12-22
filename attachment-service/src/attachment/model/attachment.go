package model

import (
	"encoding/json"
	"fmt"
	"gopkg.in/guregu/null.v3"
)

const (
	ATTACHMENT_STATUS_FORMAL    = 944103312234389504
	ATTACHMENT_STATUS_TEMPORARY = 944103645861912576
)

type Attachment struct {
	ID             int64
	OriginalName   null.String
	StorageName    null.String
	Extension      null.String
	StorageAddress null.String
	Group          null.Int
	Belong         null.Int
	Size           null.Int
	Status         null.Int
	Creator        null.Int
	CreateTime     Datetime
	Updater        null.Int
	UpdateTime     Datetime
	Version        int
}

type Datetime null.Time

func (obj Datetime) MarshalJSON() ([]byte, error) {
	return []byte(fmt.Sprintf(`"%s"`, obj.Time.Format("2006-01-02 15:04:05"))), nil
}

func (attachment Attachment) MarshalBinary() (data []byte, err error) {
	return json.Marshal(attachment)
}

func (attachment Attachment) UnmarshalBinary(data []byte) error {
	return json.Unmarshal(data, attachment);
}
