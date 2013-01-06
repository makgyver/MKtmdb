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

package mk.tmdb.exception;

import mk.tmdb.response.TMDbStatus;

/**
 * Signals that the server response is not a success. For more information 
 * about the error check the status response by calling {@link #getStatus() getStatus} method.
 * 
 * @author Mirko Polato
 *
 */
public class TMDbResponseException extends Exception {

	private static final long serialVersionUID = 1L;
	
	/**
	 * The response status.
	 */
	private TMDbStatus status;
	
	/**
	 * Default constructor: creates a new instance of ResponseException with the status FAILED.
	 */
	public TMDbResponseException() {
		super(TMDbStatus.FAILED.getMessage());
		this.status = TMDbStatus.FAILED;  
	}
	
	/**
	 * Creates a new instance of ImageSizeNotSupportedException with the given status.
	 * 
	 * @param status The response status.
	 */
	public TMDbResponseException(TMDbStatus status) {
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
	public TMDbStatus getStatus() {
		return status;
	}
	
	@Override
	public String toString() {
		return "ResponseException: " + status.getMessage();
	}
}
