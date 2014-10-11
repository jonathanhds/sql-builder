package com.github.sqlbuilder.jonathanhds.select;

import java.sql.SQLException;
import java.util.List;

public class Where extends Condition implements TerminalExpression {

	Where(Context context) {
        super(context);
        add("1 = 1");
	}

    Where(Context context, String condition) {
        super(context);
        add(condition);
    }

    public GroupBy groupBy(){
        return new GroupBy(context);
    }

    public GroupBy groupBy(String... columns){
        return new GroupBy(context, columns);
    }

	public OrderBy orderBy() {
		return new OrderBy(context);
	}

	public Where and(Object condition) {
		new AndCondition(context).add(condition);
		return this;
	}

	public Where and(Object condition, Object parameter) {
		new AndCondition(context).add(condition, parameter);
		return this;
	}

	public Where and(String condition, String parameter) {
		new AndCondition(context).add(condition, parameter);
		return this;
	}

	public Where and(Object condition, Object... parameters) {
		new AndCondition(context).add(condition, parameters);
		return this;
	}

	public Where andBetween(String columnName, Object start, Object end) {
		new AndCondition(context).between(columnName, start, end);
		return this;
	}

	public Where or(Object condition) {
		new OrCondition(context).add(condition);
		return this;
	}

	public Where or(Object condition, Object parameter) {
		new OrCondition(context).add(condition, parameter);
		return this;
	}

	public Where or(String condition, String parameter) {
		new OrCondition(context).add(condition, parameter);
		return this;
	}

	public Where or(Object condition, Object... parameters) {
		new OrCondition(context).add(condition, parameters);
		return this;
	}

	public Where orBetween(String columnName, Object start, Object end) {
		new OrCondition(context).between(columnName, start, end);
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

    @Override
    protected String getPrefix() {
        return "WHERE";
    }
}