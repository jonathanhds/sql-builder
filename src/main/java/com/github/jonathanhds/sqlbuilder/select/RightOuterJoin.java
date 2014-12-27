package com.github.jonathanhds.sqlbuilder.select;

import com.github.jonathanhds.sqlbuilder.Context;

class RightOuterJoin extends Join {

	RightOuterJoin(Context context, String condition) {
		super(context, condition);
	}

	RightOuterJoin(Context context) {
		super(context);
	}

	@Override
	protected String expression() {
		return "RIGHT OUTER JOIN";
	}

}