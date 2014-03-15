package com.github.sqlbuilder.jonathanhds.select;

import java.sql.SQLException;
import java.util.List;

public abstract class Join implements TerminalExpression {

	private Context context;

	public Join(Context context) {
		this.context = context;
		context.append(expression());
	}

	public Join(Context context, String condition) {
		this(context);
		context.append(condition);
	}
	
	public OrderBy orderBy() {
		return new OrderBy(context);
	}

	public Where where() {
		return new Where(context);
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

	protected abstract String expression();

}