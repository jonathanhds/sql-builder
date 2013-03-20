package com.github.sqlbuilder;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

public class SelectQuery {

	private Collection<String> columns;

	private Collection<String> froms;

	private Collection<String> wheres;

	private Map<String, String> joins;

	private Collection<String> groups;

	private Collection<String> orders;

	private Collection<String> havings;

	public SelectQuery() {
		columns = new LinkedList<String>();
		froms = new LinkedList<String>();
		wheres = new LinkedList<String>();
		joins = new LinkedHashMap<String, String>();
		groups = new LinkedList<String>();
		orders = new LinkedList<String>();
		havings = new LinkedList<String>();
	}

	public SelectQuery addFrom(String from) {
		froms.add(from);
		return this;
	}

	public SelectQuery addFrom(String... froms) {
		for (String from : froms) {
			addFrom(from);
		}

		return this;
	}

	public SelectQuery addColumn(String column) {
		columns.add(column);
		return this;
	}

	public SelectQuery addColumn(String... columns) {
		for (String column : columns) {
			addColumn(column);
		}

		return this;
	}

	public SelectQuery addWhere(String where) {
		wheres.add(where);
		return this;
	}

	public SelectQuery and(String where) {
		return addWhere("AND " + where);
	}

	public SelectQuery or(String where) {
		return addWhere("OR " + where);
	}

	public SelectQuery join(String join, String on) {
		joins.put("JOIN " + join, on);
		return this;
	}

	public SelectQuery innerJoin(String join, String on) {
		joins.put("INNER JOIN " + join, on);
		return this;
	}

	public SelectQuery outterJoin(String join, String on) {
		joins.put("OUTTER JOIN " + join, on);
		return this;
	}

	public SelectQuery groupBy(String... groups) {
		for (String group : groups) {
			this.groups.add(group);
		}

		return this;
	}

	public SelectQuery orderBy(String... orders) {
		for (String order : orders) {
			this.orders.add(order);
		}

		return this;
	}

	public SelectQuery having(String... havings) {
		for (String having : havings) {
			this.havings.add(having);
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

		for (Entry<String, String> entry : joins.entrySet()) {
			result.append(" " + entry.getKey() + " ON " + entry.getValue());
		}

		if (!wheres.isEmpty()) {
			result.append(" WHERE ");
			result.append(StringUtils.join(wheres, " "));
		}

		if (!groups.isEmpty()) {
			result.append(" GROUP BY ");
			result.append(StringUtils.join(groups, ", "));
		}

		if (!havings.isEmpty()) {
			result.append(" HAVING ");
			result.append(StringUtils.join(havings, ", "));
		}

		if (!orders.isEmpty()) {
			result.append(" ORDER BY ");
			result.append(StringUtils.join(orders, ", "));
		}

		return result.toString();
	}

}