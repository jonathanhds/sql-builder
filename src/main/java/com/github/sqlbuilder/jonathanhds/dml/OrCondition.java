package com.github.sqlbuilder.jonathanhds.dml;

class OrCondition extends Condition {

	OrCondition(Context context) {
		super(context);
	}

	@Override
	protected String getPrefix() {
		return "OR";
	}

}