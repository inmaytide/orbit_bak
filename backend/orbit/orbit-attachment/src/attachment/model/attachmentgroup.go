package model

import "gopkg.in/guregu/null.v3"

type AttachmentGroup struct {
	ID         int64     `json:"id"`
	Belong     null.Int  `json:"belong"`
	Status     null.Int  `json:"status"`
	Creator    null.Int  `json:"creator"`
	CreateTime null.Time `json:"createTime"`
	Updater    null.Int  `json:"updater"`
	UpdateTime null.Time `json:"updateTime"`
	Version    int       `json:"version"`
}
