package dao

import (
	"attachment/config"
	"attachment/util"
	"database/sql"
	_ "github.com/go-sql-driver/mysql"
)

func GetConn() *sql.DB {
	db, err := sql.Open("mysql", config.Apps.Datasource)
	util.CheckError(err)
	return db
}
