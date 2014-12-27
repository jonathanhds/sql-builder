package com.github.jonathanhds.sqlbuilder.select;

import com.github.jonathanhds.sqlbuilder.Context;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

abstract class Condition {

	protected final Context context;

	Condition(Context context) {
		this.context = context;
	}

	void add(Object condition) {
		context.appendLine(getPrefix() + " " + condition);
	}

	void add(Object condition, Object parameter) {
		if (parameter != null) {
			add(condition, new Object[] { parameter });
		}
	}

	void add(String condition, String parameter) {
		if (StringUtils.isNotBlank(parameter)) {
			add(condition, new Object[] { parameter });
		}
	}

	void add(Object condition, Object... parameters) {
		if (ArrayUtils.isNotEmpty(parameters)) {
			context.addParameters(parameters);
			add(condition);
		}
	}

	void between(String columnName, Object start, Object end) {
		if (start == null) {
			if (end == null) {
				return;
			} else {
				add(columnName + " <= ?", end);
			}
		} else {
			if (end == null) {
				add(columnName + " >= ?", start);
			} else {
				add(columnName + " BETWEEN ? AND ?", start, end);
			}
		}
	}

	protected abstract String getPrefix();

}