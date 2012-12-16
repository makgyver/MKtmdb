package mk.tmdb.exception;

public class InvalidApiKeyException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private String mistake;
	
	public InvalidApiKeyException() {
		super("Invalid Api Key");
		mistake = "Invalid Api Key";
	}
	
	public InvalidApiKeyException(String exception) {
		super(exception);
		mistake = exception;
	}
	
	public String getError() {
		return mistake;
	}
	
}
