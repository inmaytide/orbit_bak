package dao

import (
	"attachment/config"
	"github.com/jinzhu/gorm"
	_ "github.com/jinzhu/gorm/dialects/postgres"
	"log"
	"attachment/model"
)

type DataSource struct {
	db *gorm.DB
}

func NewDataSource(config *config.Configuration) *DataSource {
	db, err := gorm.Open(config.DataSource.Dialect, config.DataSource.URL)
	if err != nil {
		log.Println("Failed to connection database")
		log.Fatal(err)
	}
	if !db.HasTable(&model.Attachment{}) {
		db.CreateTable(&model.Attachment{})
	}
	return &DataSource{
		db: db,
	}
}

func (inst *DataSource) getConnection() *gorm.DB {
	return inst.db
}

func (inst *DataSource) Destroy() {
	if inst.db != nil {
		err := inst.db.Close()
		log.Println("Failed to close database connection")
		if err == nil {
			log.Println("Database destroyed")
		}
	}
}
