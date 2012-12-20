package mk.tmdb.utils;

import mk.tmdb.core.Constants;
import net.sf.json.JSONObject;


/**
 * Abstract class that contains the basic information about a server response.
 * 
 * @author Mirko Polato
 *
 */
public abstract class Response {

	/**
	 * The response status (default sets to NONE)
	 */
	protected Status status = Status.NONE;
	
	/**
	 * Initializes the response basic information based on the given Json object.
	 *  
	 * @param json The Json response
	 */
	public Response(JSONObject json) {
		if (json.has(Constants.STATUS_CODE)) setStatus(Status.getStatusByCode(json.getInt(Constants.STATUS_CODE)));
	}
	
	/**
	 * Initializes the response status with the given one.
	 * 
	 * @param status The response status
	 */
	public Response(Status status) {
		this.status = status;
	}
	
	/**
	 * Gets whether the response status is an error.
	 * 
	 * @return Whether the response status is an error.
	 */
	public boolean hasError() {
		return status.getCode() > Status.SUCCESS.getCode();
	}
	
	/**
	 * Gets the response status.
	 * 
	 * @return The response status
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * Sets the response status.
	 * 
	 * @param status The new response status
	 */
	protected void setStatus(Status status) {
		this.status = status;
	}
}
