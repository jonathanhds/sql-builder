package com.github.sqlbuilder;

import java.util.Collection;
import java.util.LinkedList;

import org.apache.commons.lang.StringUtils;

public class SelectQuery {

	private Collection<String> columns;

	private Collection<String> froms;

	public SelectQuery() {
		columns = new LinkedList<String>();
		froms = new LinkedList<String>();
	}

	public SelectQuery addFrom(String from) {
		froms.add(from);
		return this;
	}

	public SelectQuery addColumn(String column) {
		columns.add(column);
		return this;
	}

	public SelectQuery addColumns(String... columns) {
		for (String column : columns) {
			addColumn(column);
		}

		return this;
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();

		if (columns.isEmpty() && froms.isEmpty()) return result.toString();

		result.append("SELECT ");

		if (columns.isEmpty()) {
			result.append("*");
		} else {
			result.append(StringUtils.join(columns, ", "));
		}

		result.append(" FROM ");

		if (froms.isEmpty()) {
			throw new IllegalQueryException("No 'from' informed!");
		} else {
			result.append(StringUtils.join(froms, ", "));
		}

		return result.toString();
	}

}