package com.github.sqlbuilder.select;

import java.sql.SQLException;
import java.util.List;

public class Limit implements TerminalExpression {

	private final Context context;

	public Limit(Context context, int start, int size) {
		this.context = withLimit(context, start, size);
		// this.context.append("LIMIT ? OFFSET ?");
		// this.context.addParameters(start, size);
	}

	@Override
	public <E> List<E> list(RowMapper<E> rowMapper) throws SQLException {
		return context.list(rowMapper);
	}

	@Override
	public <E> E single(RowMapper<E> rowMapper) throws SQLException {
		return context.single(rowMapper);
	}

	private Context withLimit(Context context, int start, int size) {
		Context result = new Context(context);

		result.append("SELECT");
		result.append("*");
		result.append("FROM");
		result.append("(");
		result.append(context.toString());
		result.append(")");
		result.append("WHERE");
		result.append("ROWNUM BETWEEN ? AND ?");

		result.addParameters(start);
		result.addParameters(start + size);

		return result;
	}

}