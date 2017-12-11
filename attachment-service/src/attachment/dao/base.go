package dao

import (
	"database/sql"
	_ "github.com/go-sql-driver/mysql"
	"attachment/config"
)

func CheckError(err error) {
	if err != nil {
		panic(err)
	}
}

func GetConn() *sql.DB {
	db, err := sql.Open("mysql", config.Apps.Datasource)
	CheckError(err)

	return db
}
