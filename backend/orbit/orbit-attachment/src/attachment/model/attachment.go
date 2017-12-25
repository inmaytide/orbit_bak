package model

import (
	"fmt"
	"gopkg.in/guregu/null.v3"
	"time"
)

const (
	ATTACHMENT_STATUS_FORMAL    = 944103312234389504
	ATTACHMENT_STATUS_TEMPORARY = 944103645861912576
)

type Attachment struct {
	ID             int64       `json:"id"`
	OriginalName   null.String `json:"originalName"`
	StorageName    null.String `json:"storageName"`
	Extension      null.String `json:"extension"`
	StorageAddress null.String `json:"storageAddress"`
	Group          null.Int    `json:"group"`
	Belong         null.Int    `json:"belong"`
	Size           null.Int    `json:"size"`
	Status         null.Int    `json:"status"`
	Creator        null.Int    `json:"creator"`
	CreateTime     Datetime    `json:"createTime"`
	Updater        null.Int    `json:"updater"`
	UpdateTime     Datetime    `json:"updateTime"`
	Version        int         `json:"version"`
}

type Datetime null.Time

func (obj Datetime) MarshalJSON() ([]byte, error) {
	return []byte(fmt.Sprintf(`"%s"`, obj.Time.Format("2006-01-02 15:04:05"))), nil
}

func (obj Datetime) UnmarshalJSON(bytes []byte) error {
	return nil
}
