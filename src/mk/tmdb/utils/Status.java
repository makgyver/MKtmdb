package mk.tmdb.utils;

import java.util.EnumSet;

/**
 * Enumeration with the possible response status codes.
 * 
 * @author Mirko Polato
 *
 */
public enum Status {

	NONE(0, ""),
	
	SUCCESS(1,"Success."),
	
	INVALID_SERVICE(2, "Invalid service - This service does not exist."),
	
	AUTHENTICATION_FAILED(3, "Authentication failed - You do not have permissions to access the service."),
	
	INVALID_FORMAT(4, "Invalid format - This service doesn't exist in that format."),
	
	INVALID_PARAMETERS(5, "Invalid parameters - Your request parameters are incorrect."),
	
	INVALID_ID(6, "Invalid id - The pre-requisite id is invalid or not found."),
	
	INVALID_API_KEY(7, "Invalid API key - You must be granted a valid key."),
	
	DUPLICATE_ENTRY(8, "Duplicate entry - The data you tried to submit already exists."),
	
	SERVICE_OFFLINE(9, "Service offline - This service is temporarily offline. Try again later."),
	
	SUSPENDED_API_KEY(10, "Suspended API key - Access to your account has been suspended, contact TMDb."),
	
	INTERNAL_ERROR(11, "Internal error - Something went wrong. Contact TMDb."),
	
	UPDATE_SUCCEEDED(12, "The item/record was updated successfully."),
	
	DELETION_SUCCEEDED(13, "The item/record was deleted successfully."),
	
	AUTHENTICATION_FAILED_2(14, "Authentication failed."),
	
	FAILED(15, "Failed."),
	
	DEVICE_DENIED(16, "Device denied."),
	
	SESSION_DENIED(17, "Session denied."),
	
	VALIDATION_FAILED(18, "Validation failed."),
	
	INVALID_HEADER(19, "Invalid accept header"),
	
	UNKNOWN_ERROR(99, "Unknown error.");
	
	/**
	 * Status message
	 */
	private final String message;
	
	/**
	 * Status code
	 */
	private final int code;
	
	/**
	 * Creates a new enumeration item with the specified code and message.
	 * @param code The status code
	 * @param message The status message
	 */
	Status(int code, String message) {
		this.code = code;
		this.message = message;
	}	
	
	/**
	 * Gets the status code.
	 * @return The status code
	 */
	public int getCode() {
		return code;
	}
	
	/**
	 * Gets the status message.
	 * @return The status message.
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * Number of status.
	 */
	private static final int amount = EnumSet.allOf(Status.class).size();
    
	/**
	 * Map between status and codes.
	 */
	private static Status[] val = new Status[amount];
    
	// Static initializazion
    static { 
    	for(Status s:EnumSet.allOf(Status.class)) { 
    		val[s.getCode()]=s; 
    		}
    	}
	
    /**
	 * Gets the status identified by the given code.
	 * @param code The status code.
	 * @return The status
	 */
    public static Status getStatusByCode(int code) { 
    	return val[code]; 
    }
}
