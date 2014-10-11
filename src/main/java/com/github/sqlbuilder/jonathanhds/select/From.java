package com.github.sqlbuilder.jonathanhds.select;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang.StringUtils;

public class From implements TerminalExpression {

	private Context context;

    private final List<String> tables = new ArrayList<>();

	From(Context context) {
		this.context = context;
		this.context.append("FROM");
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
        concatenateTables();
		return new Where(context);
	}

    public Where where(String condition) {
        concatenateTables();
        return new Where(context, condition);
    }
	
	public GroupBy groupBy() {
        concatenateTables();
		return new GroupBy(context);
	}

    public GroupBy groupBy(String... columns){
        concatenateTables();
        return new GroupBy(context, columns);
    }

	public Join leftOuterJoin(String condition) {
        concatenateTables();
		return new LeftOuterJoin(context, condition);
	}
	
	public Join rightOuterJoin(String condition) {
        concatenateTables();
		return new RightOuterJoin(context, condition);
	}
	
	public Join innerJoin(String condition) {
        concatenateTables();
		return new InnerJoin(context, condition);
	}
	
	public OrderBy orderBy() {
        concatenateTables();
		return new OrderBy(context);
	}
	
	public Limit limit(int start, int size) {
        concatenateTables();
		return new Limit(context, start, size);
	}

	public <E> List<E> list(RowMapper<E> rowMapper) throws SQLException {
        concatenateTables();
		return context.list(rowMapper);
	}

	public <E> E single(RowMapper<E> rowMapper) throws SQLException {
        concatenateTables();
		return context.single(rowMapper);
	}

    private void concatenateTables(){
        final String newLine = System.getProperty("line.separator");
        this.context.append(StringUtils.join(tables, "," + newLine));
        this.context.append(newLine);
    }
}