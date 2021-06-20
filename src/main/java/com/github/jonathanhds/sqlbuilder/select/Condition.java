package com.github.jonathanhds.sqlbuilder.select;

import com.github.jonathanhds.sqlbuilder.Context;
import com.github.jonathanhds.sqlbuilder.IllegalQueryException;
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
		} else {
			if (isEqualCondition(condition.toString())) {
				add(extractColumnName(condition.toString()) + " IS NULL");
			} else {
				throw new IllegalQueryException("Could not solve '" + condition + "' condition with a null parameter");
			}
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
			if (end != null) {
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

	private Boolean isEqualCondition(String condition) {
		return StringUtils.contains(condition, " =");
	}

	private String extractColumnName(String condition) {
		String[] conditions = new String[] {
			" =",
			" >",
			" >=",
			" <",
			" <=",
			" <>",
			" IN",
			" IS",
			" BETWEEN"
		};
		String result = condition.toString();

		for (String c : conditions) {
			result = StringUtils.substringBefore(result, c);
		}

		return result;
	}

	protected abstract String getPrefix();
}
