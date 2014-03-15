package com.github.sqlbuilder.jonathanhds.select;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class GroupBy implements TerminalExpression {

	private final Context context;

	private final List<String> columns;

	public GroupBy(Context context) {
		this.context = context;
		context.append("GROUP BY");
		columns = new LinkedList<>();
	}

	public GroupBy column(String column) {
		columns.add(column);
		return this;
	}

	public Having having() {
		end();
		return new Having(context);
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