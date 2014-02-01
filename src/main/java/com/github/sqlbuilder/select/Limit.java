package com.github.sqlbuilder.select;

import java.sql.SQLException;
import java.util.List;

public class Limit implements TerminalExpression {

	private final Context context;

	public Limit(Context context, int start, int size) {
		this.context = limit(context, start, size);
	}

	@Override
	public <E> List<E> list(RowMapper<E> rowMapper) throws SQLException {
		return context.list(rowMapper);
	}

	@Override
	public <E> E single(RowMapper<E> rowMapper) throws SQLException {
		return context.single(rowMapper);
	}

	private Context limit(Context context, int start, int size) {
		return new LimiterFactory().create(context.getDatabase()).limit(context, start, size);
	}

}

interface Limiter {
	Context limit(Context context, int start, int size);
}

class HSQLDBLimiter implements Limiter {

	@Override
	public Context limit(Context context, int start, int size) {
		context.append("LIMIT ?");
		context.addParameters(size);
		context.append("OFFSET ?");
		context.addParameters(start);
		return context;
	}

}

class OracleLimiter implements Limiter {

	@Override
	public Context limit(Context context, int start, int size) {
		Context c = new Context(context);
		c.append("SELECT");
		c.append("data.*");
		c.append("FROM");
		c.append("(");
		c.append("SELECT");
		c.append("ord_data.*,");
		c.append("rownum AS rnum");
		c.append("FROM");
		c.append("(");
		c.append(context.toString());
		c.append(")");
		c.append("ord_data");
		c.append(")");
		c.append("data");
		c.append("WHERE");
		c.append("rnum BETWEEN ? AND ?");
		c.addParameters(start);
		c.addParameters(start + size);
		return c;
	}
	
}

class DefaultLimiter implements Limiter {
	@Override
	public Context limit(Context context, int start, int size) {
		return context;
	}
}

class LimiterFactory {
	Limiter create(Database database) {
		switch (database) {
		case HSQLDB:
			return new HSQLDBLimiter();
		case ORACLE:
			return new OracleLimiter();
		default:
			return new DefaultLimiter();
		}
	}
}