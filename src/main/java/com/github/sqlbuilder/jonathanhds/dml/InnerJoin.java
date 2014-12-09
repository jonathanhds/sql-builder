package com.github.sqlbuilder.jonathanhds.dml;

class InnerJoin extends Join {

	public InnerJoin(Context context, String condition) {
		super(context, condition);
	}

	public InnerJoin(Context context) {
		super(context);
	}

	@Override
	protected String expression() {
		return "INNER JOIN";
	}

}