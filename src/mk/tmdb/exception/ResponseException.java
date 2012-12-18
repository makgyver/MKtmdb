package mk.tmdb.exception;

import mk.tmdb.utils.Status;

public class ResponseException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private Status status;
	
	public ResponseException() {
		super(Status.FAILED.getMessage());
		this.status = Status.FAILED;  
	}
	
	public ResponseException(Status status) {
		super(status.getMessage());
		this.status = status;
	}
	
	public String getError() {
		return status.getMessage();
	}
	
	public Status getStatus() {
		return status;
	}
}
