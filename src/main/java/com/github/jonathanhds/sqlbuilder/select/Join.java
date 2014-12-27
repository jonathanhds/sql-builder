package com.github.jonathanhds.sqlbuilder.select;

import com.github.jonathanhds.sqlbuilder.Context;
import com.github.jonathanhds.sqlbuilder.TerminalExpression;

import java.sql.SQLException;
import java.util.List;

public abstract class Join implements TerminalExpression {

	private Context context;

	Join(Context context) {
		this.context = context;
		context.appendLine(expression());
	}

	Join(Context context, String condition) {
		this(context);
		context.appendLine(condition);
	}
	
	public OrderBy orderBy() {
		return new OrderBy(context);
	}

	public Where where() {
		return new Where(context);
	}

    public Where where(String condition) {
        return new Where(context, condition);
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

    @Override
    public String toSqlString(){
        return context.getSql();
    }
}