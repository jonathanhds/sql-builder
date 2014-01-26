package com.github.sqlbuilder.select;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class Having implements TerminalExpression {

	private final Context context;

	private final List<String> columns;

	public Having(Context context) {
		this.context = context;
		this.context.append("HAVING");
		columns = new LinkedList<>();
	}

	public Having column(String column) {
		columns.add(column);
		return this;
	}

	@Override
	public <E> List<E> list(RowMapper<E> rowMapper) throws SQLException {
		end();
		return context.list(rowMapper);
	}

	@Override
	public <E> E single(RowMapper<E> rowMapper) throws SQLException {
		end();
		return context.single(rowMapper);
	}

	private void end() {
		context.append(StringUtils.join(columns, ", "));
	}

}