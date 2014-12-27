package com.github.jonathanhds.sqlbuilder.select;

import com.github.jonathanhds.sqlbuilder.Context;

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