package com.github.sqlbuilder.select;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

public class QueryBuilderOracle extends QueryBuilder {

	public QueryBuilderOracle(Connection connection) {
		super(Database.ORACLE, connection);
	}

	public QueryBuilderOracle(DataSource dataSource) throws SQLException {
		super(Database.ORACLE, dataSource);
	}

}
