package com.github.jonathanhds.sqlbuilder.select;

import com.github.jonathanhds.sqlbuilder.Context;

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