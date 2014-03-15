package com.github.sqlbuilder.jonathanhds.update;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

import com.github.sqlbuilder.jonathanhds.IllegalQueryException;

public class UpdateQuery {

	private String table;

	private Map<String, String> sets;

	private Collection<String> wheres;

	public UpdateQuery(String table) {
		this.table = table;

		this.sets = new LinkedHashMap<String, String>();
		this.wheres = new LinkedList<String>();
	}

	public UpdateQuery set(String column, String value) {
		sets.put(column, value);
		return this;
	}

	public UpdateQuery addWhere(String where) {
		wheres.add(where);
		return this;
	}

	@Override
	public String toString() {
		if (sets.isEmpty()) throw new IllegalQueryException("Not contains SET statements!");

		StringBuilder result = new StringBuilder();

		result.append("UPDATE ")
			  .append(table)
			  .append(" SET ");

		for (Entry<String, String> entry : sets.entrySet()) {
			result.append(entry.getKey())
				  .append(" = ")
				  .append("'")
				  .append(entry.getValue())
				  .append("'");
		}

		if (!wheres.isEmpty()) {
			result.append(" WHERE ");
			result.append(StringUtils.join(wheres, " AND "));
		}

		return result.toString();
	}

}
