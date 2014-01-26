package com.github.sqlbuilder.select;

import java.sql.SQLException;
import java.util.List;

public class OrderBy implements TerminalExpression {

	private Context context;

	public OrderBy(Context context) {
		this.context = context;
		context.append("ORDER BY");
	}

	public OrderBy column(String column) {
		return column(column, OrderByType.ASC);
	}

	public OrderBy column(String column, OrderByType type) {
		if (type == null) {
			return column(column);
		}

		context.append(column + " " + type);

		return this;
	}
	
	public Limit limit(int start, int size) {
		return new Limit(context, start, size);
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