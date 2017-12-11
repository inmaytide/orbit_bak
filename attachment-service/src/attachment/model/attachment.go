package model

import "time"

type Attachment struct {
	ID             uint64
	CreateTime     time.Time
	UpdateTime     time.Time
	Creator        uint64
	Updater        uint64
	Version        uint
	OriginalName   string
	StorageName    string
	Extension      string
	StorageAddress string
	Group          uint64
	Belong         uint64
	Size           uint64
	Status         uint64
}
