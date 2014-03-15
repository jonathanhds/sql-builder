package com.github.sqlbuilder.jonathanhds.select;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

public class QueryBuilderHSQLDB extends QueryBuilder {

	public QueryBuilderHSQLDB(Connection connection) {
		super(Database.HSQLDB, connection);
	}

	public QueryBuilderHSQLDB(DataSource dataSource) throws SQLException {
		super(Database.HSQLDB, dataSource);
	}

}