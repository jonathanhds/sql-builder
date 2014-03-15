package com.github.sqlbuilder.jonathanhds.select;

class LeftOuterJoin extends Join {

	public LeftOuterJoin(Context context) {
		super(context);
	}

	public LeftOuterJoin(Context context, String condition) {
		super(context, condition);
	}

	@Override
	protected String expression() {
		return "LEFT OUTER JOIN";
	}

}