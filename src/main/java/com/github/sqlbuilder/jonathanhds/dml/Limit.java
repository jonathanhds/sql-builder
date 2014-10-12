package com.github.sqlbuilder.jonathanhds.dml;

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

    @Override
    public String toSqlString(){
        return context.getSql();
    }
}

interface Limiter {
	Context limit(Context context, int start, int size);
}

class HSQLDBLimiter implements Limiter {

	@Override
	public Context limit(Context context, int start, int size) {
		context.appendLine("LIMIT ?");
		context.addParameters(size);
		context.appendLine("OFFSET ?");
		context.addParameters(start);
		return context;
	}

}

class OracleLimiter implements Limiter {

	@Override
	public Context limit(Context context, int start, int size) {
		Context c = new Context(context);
		c.appendLine("SELECT");
		c.appendLine("data.*");
		c.appendLine("FROM");
		c.appendLine("(");
		c.appendLine("SELECT");
		c.appendLine("ord_data.*,");
		c.appendLine("rownum AS rnum");
		c.appendLine("FROM");
		c.appendLine("(");
		c.appendLine(context.toString());
		c.appendLine(")");
		c.appendLine("ord_data");
		c.appendLine(")");
		c.appendLine("data");
		c.appendLine("WHERE");
		c.appendLine("rnum BETWEEN ? AND ?");
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