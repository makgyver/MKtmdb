package mk.tmdb.exception;

public class ConfigurationNotLoadedException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private String mistake;
	
	public ConfigurationNotLoadedException() {
		super("Configuration not loaded");
		mistake = "Configuration not loaded";
	}
	
	public ConfigurationNotLoadedException(String exception) {
		super(exception);
		mistake = exception;
	}
	
	public String getError() {
		return mistake;
	}
	
}
