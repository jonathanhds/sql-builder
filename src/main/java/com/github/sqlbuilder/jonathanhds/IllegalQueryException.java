package com.github.sqlbuilder.jonathanhds;

public class IllegalQueryException extends RuntimeException {

	private static final long serialVersionUID = -4847411495350655382L;

	public IllegalQueryException() { }

	public IllegalQueryException(String message, Throwable cause) {
		super(message, cause);
	}

	public IllegalQueryException(String message) {
		super(message);
	}

	public IllegalQueryException(Throwable cause) {
		super(cause);
	}

}
