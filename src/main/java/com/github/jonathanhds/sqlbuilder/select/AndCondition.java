package com.github.jonathanhds.sqlbuilder.select;

import com.github.jonathanhds.sqlbuilder.Context;

class AndCondition extends Condition {

	AndCondition(Context context) {
		super(context);
	}

	@Override
	protected String getPrefix() {
		return "AND";
	}
}
