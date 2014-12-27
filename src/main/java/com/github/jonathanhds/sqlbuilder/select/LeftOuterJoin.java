package com.github.jonathanhds.sqlbuilder.select;

import com.github.jonathanhds.sqlbuilder.Context;

class LeftOuterJoin extends Join {

	LeftOuterJoin(Context context) {
		super(context);
	}

	LeftOuterJoin(Context context, String condition) {
		super(context, condition);
	}

	@Override
	protected String expression() {
		return "LEFT OUTER JOIN";
	}

}