package com.github.sqlbuilder.jonathanhds.insert;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.github.sqlbuilder.jonathanhds.IllegalQueryException;

public class InsertQuery {

	private final String table;

	private Collection<String> columns;

	private List<Object[]> values;

	public InsertQuery(String table) {
		this.table = table;
		this.columns = new LinkedList<String>();
		this.values = new LinkedList<Object[]>();
	}

	public InsertQuery columns(String... columns) {
		Collections.addAll(this.columns, columns);
		return this;
	}

	public InsertQuery values(Object... values) {
		this.values.add(values);
		return this;
	}

	@Override
	public String toString() {
		if (columns.isEmpty()) throw new IllegalQueryException("No columns informed!");
		if (values.isEmpty()) throw new IllegalQueryException("No values informed!");

		for (Object[] values : this.values) {
			if (values.length != columns.size()) {
				throw new IllegalQueryException("Values size different of columns size");
			}
		}

		StringBuilder result = new StringBuilder();

		result.append("INSERT INTO ")
			  .append(table)
			  .append(" (")
			  .append(StringUtils.join(columns, ", "))
			  .append(")")
			  .append(" VALUES ")
			  .append(StringUtils.join(toValue(), ", "));

		return result.toString();
	}

	private String[] toValue() {
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
