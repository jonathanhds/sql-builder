package com.github.sqlbuilder;

public class IllegalQueryException extends RuntimeException {

	private static final long serialVersionUID = -4847411495350655382L;

	public IllegalQueryException() { }

	public IllegalQueryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

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
