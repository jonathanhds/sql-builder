package com.github.jonathanhds.sqlbuilder.select;

import com.github.jonathanhds.sqlbuilder.Context;
import com.github.jonathanhds.sqlbuilder.TerminalExpression;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang.StringUtils;

public class OrderBy implements TerminalExpression {
	private Context context;

	private OrderByType order;

	private boolean terminated = false;

	private final List<String> columns = new ArrayList<>();

	OrderBy(Context context) {
		this.context = context;
		this.order = OrderByType.ASC;
		context.appendLine("ORDER BY");
	}

	OrderBy(Context context, String... columns) {
		this(context);
		this.columns.addAll(Arrays.asList(columns));
	}

	OrderBy(Context context, OrderByType order, String... columns) {
		this(context, columns);
		this.order = order;
	}

	public OrderBy column(String column) {
		return column(column, OrderByType.ASC);
	}

	public OrderBy columns(String... columns) {
		this.columns.addAll(Arrays.asList(columns));
		this.order = OrderByType.ASC;
		return this;
	}

	public OrderBy columns(OrderByType order, String... columns) {
		columns(columns);
		this.order = order;
		return this;
	}

	public OrderBy column(String column, OrderByType order) {
		if (order == null) {
			return column(column);
		}

		columns.add(column);
		this.order = order;
		return this;
	}

	public Limit limit(int start, int size) {
		terminate();
		return new Limit(context, start, size);
	}

	@Override
	public <E> List<E> list(RowMapper<E> rowMapper) throws SQLException {
		terminate();
		return context.list(rowMapper);
	}

	@Override
	public <E> E single(RowMapper<E> rowMapper) throws SQLException {
		terminate();
		return context.single(rowMapper);
	}

	@Override
	public String toString() {
		terminate();
		return context.toString();
	}

	private void terminate() {
		if (!terminated) {
			context.appendLine(" ");
			context.appendLine(StringUtils.join(columns, ", "));
			context.appendLine(" " + order.name());
		}
	}
}
