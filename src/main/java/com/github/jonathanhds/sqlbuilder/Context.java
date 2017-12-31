package com.github.jonathanhds.sqlbuilder;

import com.github.jonathanhds.sqlbuilder.select.RowMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Context {

	private static final String NEW_LINE = System.getProperty("line.separator");

	private final Connection connection;

	private final StringBuilder sql;

	private final List<Object> parameters;

	private final Database database;

	private transient final Logger log;

	{
		this.log = Logger.getLogger(getClass().getName());
	}

	public Context(Context clone) {
		this.connection = clone.connection;
		this.parameters = new LinkedList<Object>(clone.parameters);
		this.sql = new StringBuilder();
		this.database = clone.database;
	}

	public Context(Database database, Connection connection) {
		this.connection = connection;
		this.database = database;
		sql = new StringBuilder();
		parameters = new LinkedList<>();
	}

	@Override
	public String toString() {
		return sql.toString();
	}

	public <E> List<E> list(RowMapper<E> rowMapper) throws SQLException {
		List<E> result = new LinkedList<>();

		try (ResultSet resultSet = execute(toString())) {
			int rowNum = 0;
			while (resultSet.next()) {
				E obj = rowMapper.convert(resultSet, rowNum++);
				result.add(obj);
			}
		}

		return result;
	}

	public <E> E single(RowMapper<E> rowMapper) throws SQLException {
		E result = null;

		try (ResultSet resultSet = execute(toString())) {
			if (resultSet.next()) {
				result = rowMapper.convert(resultSet, 1);

				if (resultSet.next()) {
					throw new SQLException("The query returned more than one result");
				}
			}
		}

		return result;
	}

	public Context append(String expression) {
		sql.append(expression);
		return this;
	}

	public Context appendLine(String expression) {
		sql.append(expression);
		sql.append(NEW_LINE);
		return this;
	}

	public Context newLine() {
		sql.append(NEW_LINE);
		return this;
	}

	public void addParameters(Object... parameters) {
		this.parameters.addAll(Arrays.asList(parameters));
	}

	public Database getDatabase() {
		return database;
	}

	private ResultSet execute(String sql) throws SQLException {
		log.log(Level.INFO, "\n" + sql);
		try (PreparedStatement statement = prepareStatement(sql)) {
			return statement.executeQuery();
		}
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