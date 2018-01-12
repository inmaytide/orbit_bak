package dao

import (
	"attachment/config"
	"database/sql"
	_ "github.com/go-sql-driver/mysql"
	"attachment/errorhandler"
	"sync"
)

const (
	SQL_PATTERN_GET          = "select %s from %s where id = ?"
	SQL_PATTERN_DELETE_BY_ID = "delete from %s where id = ?"
)

type dao struct {
	db *sql.DB
}

var instance *dao
var once sync.Once

func getInstance() *dao {
	once.Do(func() {
		instance = &dao{}
		var err error
		instance.db, err = sql.Open("mysql", config.GetApplication().Datasource)
		errorhandler.Termination(err, "Database connection failed.")
	})
	return instance
}

func (dao *dao) getConnection() *sql.DB {
	return dao.db
}
