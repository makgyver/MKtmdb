package mk.tmdb.exception;

import mk.tmdb.utils.Status;

/**
 * Signals that the server response is not a success. For more information 
 * about the error check the status response by calling {@link #getStatus() getStatus} method.
 * 
 * @author Mirko Polato
 *
 */
public class ResponseException extends Exception {

	private static final long serialVersionUID = 1L;
	
	/**
	 * The response status.
	 */
	private Status status;
	
	/**
	 * Default constructor: creates a new instance of ResponseException with the status FAILED.
	 */
	public ResponseException() {
		super(Status.FAILED.getMessage());
		this.status = Status.FAILED;  
	}
	
	/**
	 * Creates a new instance of ImageSizeNotSupportedException with the given status.
	 * 
	 * @param status The response status.
	 */
	public ResponseException(Status status) {
		super(status.getMessage());
		this.status = status;
	}
	
	/**
	 * Gets the mistake message.
	 * 
	 * @return The mistake message.
	 */
	public String getError() {
		return status.getMessage();
	}
	
	/**
	 * Gets the response status.
	 * 
	 * @return The response status
	 */
	public Status getStatus() {
		return status;
	}
}
