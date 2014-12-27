package com.github.jonathanhds.sqlbuilder.select;

import com.github.jonathanhds.sqlbuilder.Context;
import com.github.jonathanhds.sqlbuilder.TerminalExpression;
import org.apache.commons.lang.StringUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class From implements TerminalExpression {

	private Context context;

    private boolean terminated = false;

    private final List<String> tables = new ArrayList<>();

	From(Context context) {
		this.context = context;
		this.context.appendLine("FROM");
	}

	public From table(String table) {
        tables.add(table);
		return this;
	}

    public From tables(String... tables){
        this.tables.addAll(Arrays.asList(tables));
        return this;
    }

    public From select(String selectQuery, String alias){
        this.tables.add("(" + selectQuery + ") " + alias);
        return this;
    }

	public Where where() {
        terminate();
		return new Where(context);
	}

    public Where where(String condition) {
        terminate();
        return new Where(context, condition);
    }
	
	public GroupBy groupBy() {
        terminate();
		return new GroupBy(context);
	}

    public GroupBy groupBy(String... columns){
        terminate();
        return new GroupBy(context, columns);
    }

	public Join leftOuterJoin(String condition) {
        terminate();
		return new LeftOuterJoin(context, condition);
	}
	
	public Join rightOuterJoin(String condition) {
        terminate();
		return new RightOuterJoin(context, condition);
	}
	
	public Join innerJoin(String condition) {
        terminate();
		return new InnerJoin(context, condition);
	}
	
	public OrderBy orderBy() {
        terminate();
		return new OrderBy(context);
	}
	
	public Limit limit(int start, int size) {
        terminate();
		return new Limit(context, start, size);
	}

	public <E> List<E> list(RowMapper<E> rowMapper) throws SQLException {
        terminate();
		return context.list(rowMapper);
	}

	public <E> E single(RowMapper<E> rowMapper) throws SQLException {
        terminate();
		return context.single(rowMapper);
	}

    @Override
    public String toSqlString(){
        terminate();
        return context.getSql();
    }

    private void terminate(){
        if(!terminated){
            final String newLine = System.getProperty("line.separator");
            this.context.appendLine(StringUtils.join(tables, "," + newLine));
            this.context.appendLine(newLine);
            terminated = true;
        }
    }
}