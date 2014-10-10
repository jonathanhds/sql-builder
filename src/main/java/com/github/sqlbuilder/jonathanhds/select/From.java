package com.github.sqlbuilder.jonathanhds.select;

import java.sql.SQLException;
import java.util.List;

public class From implements TerminalExpression {

	private Context context;

	public From(Context context) {
		this.context = context;
		this.context.append("FROM");
	}

	public From table(String table) {
		this.context.append(table);
		return this;
	}

	public Where where() {
		return new Where(context);
	}

    public Where where(String condition) {
        return new Where(context, condition);
    }
	
	public GroupBy groupBy() {
		return new GroupBy(context);
	}

	public Join leftOuterJoin(String condition) {
		return new LeftOuterJoin(context, condition);
	}
	
	public Join rightOuterJoin(String condition) {
		return new RightOuterJoin(context, condition);
	}
	
	public Join innerJoin(String condition) {
		return new InnerJoin(context, condition);
	}
	
	public OrderBy orderBy() {
		return new OrderBy(context);
	}
	
	public Limit limit(int start, int size) {
		return new Limit(context, start, size);
	}

	public <E> List<E> list(RowMapper<E> rowMapper) throws SQLException {
		return context.list(rowMapper);
	}

	public <E> E single(RowMapper<E> rowMapper) throws SQLException {
		return context.single(rowMapper);
	}

}