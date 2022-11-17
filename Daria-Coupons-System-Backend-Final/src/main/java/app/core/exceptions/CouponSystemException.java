package app.core.exceptions;

import org.springframework.http.HttpStatus;

public class CouponSystemException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5836969268923084085L;

	public CouponSystemException(HttpStatus unauthorized, String string) {
		super();
		// TODO Auto-generated constructor stub
	}

	public CouponSystemException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public CouponSystemException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public CouponSystemException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public CouponSystemException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public CouponSystemException(String string, String lastName) {
		// TODO Auto-generated constructor stub
	}

}
