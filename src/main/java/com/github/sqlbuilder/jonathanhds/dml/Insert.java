package com.github.sqlbuilder.jonathanhds.dml;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.github.sqlbuilder.jonathanhds.IllegalQueryException;

public class Insert implements Query {

	private String table;
	private final List<String> columns;
	private final List<Object[]> values;
    private final Context context;
    private boolean terminated = false;

    Insert(Context context){
        this.context = context;
        this.context.append("INSERT INTO ");
		this.columns = new LinkedList<>();
		this.values = new LinkedList<>();
    }

	Insert(Context context, String table) {
        this(context);
		this.table = table;
	}

    public Insert table(String table){
        this.table = table;
        return this;
    }

	public Insert columns(String... columns) {
		Collections.addAll(this.columns, columns);
		return this;
	}

	public Insert values(Object... values) {
		this.values.add(values);
		return this;
	}

	@Override
	public String toSqlString() {
        terminate();
		return context.getSql();
	}

    private void terminate(){
		if (columns.isEmpty()) throw new IllegalQueryException("No columns informed!");
		if (values.isEmpty()) throw new IllegalQueryException("No values informed!");

        for(Object[] valueSet : values){
            if(valueSet.length != columns.size()){
                throw new IllegalQueryException("Value size different from column size!");
            }
        }

        if(!terminated){
			  context.appendLine(table)
			  .append(" ( ")
			  .append(StringUtils.join(columns, ", "))
			  .appendLine(" )")
			  .append("VALUES ")
			  .append(StringUtils.join(getValues(), ", "));
        }
    }

	private String[] getValues() {
		String[] result = new String[values.size()];

		for (int i = 0; i < result.length; i++) {
			Object[] objs = values.get(i);
			result[i] = toValue(objs);
		}

		return result;
	}

	private String toValue(Object[] objs) {
		String[] result = new String[objs.length];

		for (int i = 0; i < result.length; i++) {
			if (objs[i] instanceof String) {
				result[i] = "'" + objs[i].toString() + "'";
			} else {
				result[i] = objs[i].toString();
			}
		}

		return "(" + StringUtils.join(result, ", ") + ")";
	}
}