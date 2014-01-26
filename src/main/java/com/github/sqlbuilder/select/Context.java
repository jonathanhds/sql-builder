package com.github.sqlbuilder.select;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

class Context {

	private final Connection connection;

	private final StringBuilder sql;

	private final List<Object> parameters;

	Context(Context clone) {
		this.connection = clone.connection;
		this.parameters = new LinkedList<Object>(clone.parameters);
		this.sql = new StringBuilder();
	}

	Context(Connection connection) {
		this.connection = connection;
		sql = new StringBuilder();
		parameters = new LinkedList<>();
	}

	@Override
	public String toString() {
		return getSql();
	}

	<E> List<E> list(RowMapper<E> rowMapper) throws SQLException {
		List<E> result = new LinkedList<>();

		try (ResultSet resultSet = execute(getSql())) {
			int rowNum = 0;
			while (resultSet.next()) {
				E obj = rowMapper.convert(resultSet, rowNum++);
				result.add(obj);
			}
		}

		return result;
	}

	<E> E single(RowMapper<E> rowMapper) throws SQLException {
		E result = null;

		try (ResultSet resultSet = execute(getSql())) {
			if (resultSet.next()) {
				result = rowMapper.convert(resultSet, 1);

				if (resultSet.next()) {
					throw new SQLException("The query returned more than one result");
				}
			}
		}

		return result;
	}

	void append(String expression) {
		sql.append(expression);
		sql.append("\n");
	}

	void addParameters(Object... parameters) {
		for (Object parameter : parameters) {
			this.parameters.add(parameter);
		}
	}

	private ResultSet execute(String sql) throws SQLException {
		try (PreparedStatement statement = prepareStatement(sql)) {
			return statement.executeQuery();
		}
	}

	private String getSql() {
		return sql.toString();
	}

	private PreparedStatement prepareStatement(String sql) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(sql);

		int i = 1;
		for (Object parameter : parameters) {
			statement.setObject(i++, parameter);
		}

		return statement;
	}

}