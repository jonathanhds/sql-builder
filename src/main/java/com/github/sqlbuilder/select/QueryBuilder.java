package com.github.sqlbuilder.select;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

public class QueryBuilder {

	private final Context context;

	public QueryBuilder(Database database, DataSource dataSource) throws SQLException {
		this(database, dataSource.getConnection());
	}

	public QueryBuilder(Database database, Connection connection) {
		this.context = new Context(database, connection);
	}

	public Select select() {
		return new Select(context);
	}
	
	@Override
	public String toString() {
		return context.toString();
	}

}