package mk.tmdb.exception;

import mk.tmdb.core.Configuration;

/**
 * Signals that the Configuration information was not loaded. 
 * To avoid this exception call the method {@link Configuration#load() Configuration.load()} or
 * {@link Configuration#forceLoad() Configuration.forceLoad()}. 
 * 
 * @author Mirko Polato
 *
 */
public class ConfigurationNotLoadedException extends Exception {

	private static final long serialVersionUID = 1L;
	
	/**
	 * The mistake string.
	 */
	private String mistake;
	
	/**
	 * Default constructor: creates a new instance of ConfigurationNotLoadedException.
	 */
	public ConfigurationNotLoadedException() {
		super("Configuration not loaded");
		mistake = "Configuration not loaded";
	}
	
	/**
	 * Creates a new instance of ConfigurationNotLoadedException with the given mistake message.
	 * 
	 * @param exception The mistake message
	 */
	public ConfigurationNotLoadedException(String exception) {
		super(exception);
		mistake = exception;
	}
	
	/**
	 * Gets the mistake message.
	 * 
	 * @return The mistake message
	 */
	public String getError() {
		return mistake;
	}
	
	@Override
	public String toString() {
		return "ConfigurationNotLoadedException: " + mistake;
	}
}
