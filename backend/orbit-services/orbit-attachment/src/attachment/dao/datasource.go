package dao

import (
	"attachment/config"
	"github.com/jinzhu/gorm"
	_ "github.com/jinzhu/gorm/dialects/postgres"
	"sync"
	"log"
	"attachment/model"
	"attachment/errorhandler"
)

type DataSource struct {
	db   *gorm.DB
	once sync.Once
}

func NewDataSource() *DataSource{
	return &DataSource{};
}

func (inst *DataSource) getConnection() *gorm.DB {
	inst.once.Do(func() {
		db, err := gorm.Open("postgres", config.GetApplication().Datasource)
		if !db.HasTable(&model.Attachment{}) {
			db.CreateTable(&model.Attachment{})
		}
		errorhandler.Terminate(err, "Database connection failed.")
		inst.db = db
	})
	return inst.db
}

func (inst *DataSource) Destroy() {
	if inst.db != nil {
		err := inst.db.Close()
		errorhandler.Print(err, "Failed to close database connection")
		if err == nil {
			log.Println("Database destroyed.")
		}
	}
}
