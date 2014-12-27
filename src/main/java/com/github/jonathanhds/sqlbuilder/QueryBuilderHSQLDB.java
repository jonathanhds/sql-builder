package com.github.jonathanhds.sqlbuilder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class QueryBuilderHSQLDB extends QueryBuilder {

	public QueryBuilderHSQLDB(Connection connection) {
		super(Database.HSQLDB, connection);
	}

	public QueryBuilderHSQLDB(DataSource dataSource) throws SQLException {
		super(Database.HSQLDB, dataSource);
	}

}