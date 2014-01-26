package com.github.sqlbuilder.select;

import java.sql.SQLException;
import java.util.List;

public class Limit implements TerminalExpression {

	private final Context context;

	public Limit(Context context, int start, int size) {
		this.context = context;
	}

	@Override
	public <E> List<E> list(RowMapper<E> rowMapper) throws SQLException {
		return context.list(rowMapper);
	}

	@Override
	public <E> E single(RowMapper<E> rowMapper) throws SQLException {
		return context.single(rowMapper);
	}

}