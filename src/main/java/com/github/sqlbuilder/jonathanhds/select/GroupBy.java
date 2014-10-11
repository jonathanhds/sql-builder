package com.github.sqlbuilder.jonathanhds.select;

import java.sql.SQLException;
import java.util.Arrays;
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

    GroupBy(Context context, String... columns){
        this(context);
        this.columns.addAll(Arrays.asList(columns));
    }

	public GroupBy column(String column) {
		columns.add(column);
		return this;
	}

    public GroupBy columns(String... columns){
        this.columns.addAll(Arrays.asList(columns));
        return this;
    }

	public Having having() {
		terminate();
		return new Having(context);
	}

    public Having having(String condition){
        terminate();
        return new Having(context, condition);
    }

    public OrderBy orderBy(){
        terminate();
        return new OrderBy(context);
    }

    public OrderBy orderBy(String... columns){
        terminate();
        return new OrderBy(context, columns);
    }

    public OrderBy orderBy(OrderByType order, String... columns){
        terminate();
        return new OrderBy(context, order, columns);
    }

    public OrderBy orderBy(String column, OrderByType order){
        terminate();
        return new OrderBy(context, order, column);
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

	private void terminate() {
		context.append(StringUtils.join(columns, ", "));
	}

}