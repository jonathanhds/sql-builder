package com.github.sqlbuilder.jonathanhds.select;

class RightOuterJoin extends Join {

	public RightOuterJoin(Context context, String condition) {
		super(context, condition);
	}

	public RightOuterJoin(Context context) {
		super(context);
	}

	@Override
	protected String expression() {
		return "RIGHT OUTER JOIN";
	}

}