package com.github.sqlbuilder.jonathanhds.dml;

class AndCondition extends Condition {

	AndCondition(Context context) {
		super(context);
	}

	@Override
	protected String getPrefix() {
		return "AND";
	}

}