package dao

import (
	"attachment/config"
	"database/sql"
	_ "github.com/go-sql-driver/mysql"
	"attachment/errorhandler"
)

const (
	SQL_PATTERN_GET = "select %s from %s where id = ?"
	SQL_PATTERN_DELETE_BY_ID = "delete from %s where id = ?"
)

type basicDao struct {
	db *sql.DB
}


var basicDaoInstance *basicDao

func getBaseDaoInstance() *basicDao{
	if basicDaoInstance == nil {
		basicDaoInstance = new(basicDao)
	}
	return basicDaoInstance
}

func (dao *basicDao) getConn() *sql.DB {
	if dao.db != nil {
		return dao.db;
	}
	var err error
	dao.db, err = sql.Open("mysql", config.GetApplication().Datasource)
	errorhandler.Termination(err, "Database connection failed.")
	return dao.db
}
