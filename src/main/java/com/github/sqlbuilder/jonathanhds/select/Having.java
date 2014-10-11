package com.github.sqlbuilder.jonathanhds.select;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class Having implements TerminalExpression {

	private final Context context;

	private final List<String> conditions;

	Having(Context context) {
		this.context = context;
		this.context.append("HAVING");
		conditions = new LinkedList<>();
	}

    Having(Context context, String... conditions){
        this(context);
        this.conditions.addAll(Arrays.asList(conditions));
    }

	public Having condition(String condition) {
		conditions.add(condition);
		return this;
	}

    public Having conditions(String... conditions){
        this.conditions.addAll(Arrays.asList(conditions));
        return this;
    }

	@Override
	public <E> List<E> list(RowMapper<E> rowMapper) throws SQLException {
		end();
		return context.list(rowMapper);
	}

	@Override
	public <E> E single(RowMapper<E> rowMapper) throws SQLException {
		end();
		return context.single(rowMapper);
	}

	private void end() {
		context.append(StringUtils.join(conditions, ", "));
	}

}