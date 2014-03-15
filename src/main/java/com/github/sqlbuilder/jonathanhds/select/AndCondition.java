package com.github.sqlbuilder.jonathanhds.select;

class AndCondition extends Condition {

	AndCondition(Context context) {
		super(context);
	}

	@Override
	protected String getPrefix() {
		return "AND";
	}

}