package com.github.sqlbuilder.select;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

public class QueryBuilder {

	private final Context context;

	public QueryBuilder(DataSource dataSource) throws SQLException {
		this(dataSource.getConnection());
	}

	public QueryBuilder(Connection connection) {
		this.context = new Context(connection);
	}

	public Select select() {
		return new Select(context);
	}
	
	@Override
	public String toString() {
		return context.toString();
	}

}