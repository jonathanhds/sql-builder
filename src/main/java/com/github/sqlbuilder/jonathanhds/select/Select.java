package com.github.sqlbuilder.jonathanhds.select;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class Select {

	private Context context;

	private final List<String> columns;

	public Select(Context context) {
		this.context = context;
		this.context.append("SELECT");
		columns = new LinkedList<>();
	}

	public From from() {
		this.context.append(StringUtils.join(columns, ",\n"));
		return new From(context);
	}

	public Select all() {
		append("*");
		return this;
	}

	public Select column(String column) {
		append(column);
		return this;
	}

    public Select columns(String... columns) {
        for(String column : columns){
            append(column);
        }

        return this;
    }

	public Select count(String column) {
		append("COUNT(" + column + ")");
		return this;
	}

	private void append(String expression) {
		columns.add(expression);
	}

}