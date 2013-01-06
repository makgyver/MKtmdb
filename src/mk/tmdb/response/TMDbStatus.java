/*******************************************************************************
 * Copyright (C) 2012-2013  Mirko Polato
 * 
 * This file is part of MKtmdb.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 ******************************************************************************/

package mk.tmdb.response;

/**
 * Enumeration with the possible response status codes.
 * 
 * @author Mirko Polato
 *
 */
public enum TMDbStatus {

	/**
	 * Code 0: None.
	 */
	NONE(0, ""),
	
	/**
	 * Code 1: Success.
	 */
	SUCCESS(1,"Success."),
	
	/**
	 * Code 2: Invalid service - This service does not exist.
	 */
	INVALID_SERVICE(2, "Invalid service - This service does not exist."),
	
	/**
	 * Code 3: Authentication failed - You do not have permissions to access the service.
	 */
	AUTHENTICATION_FAILED(3, "Authentication failed - You do not have permissions to access the service."),
	
	/**
	 * Code 4: Invalid format - This service doesn't exist in that format.
	 */
	INVALID_FORMAT(4, "Invalid format - This service doesn't exist in that format."),
	
	/**
	 * Code 5: Invalid parameters - Your request parameters are incorrect.
	 */
	INVALID_PARAMETERS(5, "Invalid parameters - Your request parameters are incorrect."),
	
	/**
	 * Code 6: Invalid id - The pre-requisite id is invalid or not found.
	 */
	INVALID_ID(6, "Invalid id - The pre-requisite id is invalid or not found."),
	
	/**
	 * Code 7: Invalid API key - You must be granted a valid key.
	 */
	INVALID_API_KEY(7, "Invalid API key - You must be granted a valid key."),
	
	/**
	 * Code 8: Duplicate entry - The data you tried to submit already exists.
	 */
	DUPLICATE_ENTRY(8, "Duplicate entry - The data you tried to submit already exists."),
	
	/**
	 * Code 9: Service offline - This service is temporarily offline. Try again later.
	 */
	SERVICE_OFFLINE(9, "Service offline - This service is temporarily offline. Try again later."),
	
	/**
	 * Code 10: Suspended API key - Access to your account has been suspended, contact TMDb.
	 */
	SUSPENDED_API_KEY(10, "Suspended API key - Access to your account has been suspended, contact TMDb."),
	
	/**
	 * Code 11: Internal error - Something went wrong. Contact TMDb.
	 */
	INTERNAL_ERROR(11, "Internal error - Something went wrong. Contact TMDb."),
	
	/**
	 * Code 12: The item/record was updated successfully.
	 */
	UPDATE_SUCCEEDED(12, "The item/record was updated successfully."),
	
	/**
	 * Code 13: The item/record was deleted successfully.
	 */
	DELETION_SUCCEEDED(13, "The item/record was deleted successfully."),
	
	/**
	 * Code 14: Authentication failed.
	 */
	AUTHENTICATION_FAILED_2(14, "Authentication failed."),
	
	/**
	 * Code 15: Failed.
	 */
	FAILED(15, "Failed."),
	
	/**
	 * Code 16: Device denied.
	 */
	DEVICE_DENIED(16, "Device denied."),
	
	/**
	 * Code 17: Session denied.
	 */
	SESSION_DENIED(17, "Session denied."),
	
	/**
	 * Code 18: Validation failed.
	 */
	VALIDATION_FAILED(18, "Validation failed."),
	
	/**
	 * Code 19: Invalid accept header.
	 */
	INVALID_HEADER(19, "Invalid accept header."),
	
	/**
	 * Code 20: Invalid date range. Should be a range no longer than 14 days.
	 */
	INVALID_DATE_RANGE(20, "Invalid date range. Should be a range no longer than 14 days."),
	
	/**
	 * Code 98: Malformed URL.
	 */
	MALFORMED_URL(98, "Malformed url."),
	
	/**
	 * Code 99: Request Timeout. Retry in a few minutes.
	 */
	TIMEOUT(99, "Request Timeout. Retry in a few minutes."),
	
	/**
	 * Code 100: Unknown error. See the log for more information.
	 */
	UNKNOWN_ERROR(100, "Unknown error. See the log for more information.");
	
	/**
	 * Status message.
	 */
	private final String message;
	
	/**
	 * Status code.
	 */
	private final int code;
	
	/**
	 * Creates a new enumeration item with the specified code and message.
	 * 
	 * @param code The status code
	 * @param message The status message
	 */
	TMDbStatus(int code, String message) {
		this.code = code;
		this.message = message;
	}	
	
	/**
	 * Gets the status code.
	 * 
	 * @return The status code
	 */
	public int getCode() {
		return code;
	}
	
	/**
	 * Gets the status message.
	 * 
	 * @return The status message
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * Gets the status identified by the given code.
	 * 
	 * @param code The status code
	 * @return The status
	 */
	public static TMDbStatus getStatusByCode(int code) {
	    
	    for (TMDbStatus s : values()) {
	    	if (s.getCode() == code) return s;
	    }
	    
	    return TMDbStatus.UNKNOWN_ERROR;
	}
}
