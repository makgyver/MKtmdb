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

package mk.tmdb.utils;

import mk.tmdb.core.TMDbConstants;
import net.sf.json.JSONObject;


/**
 * Abstract class that contains the basic information about a server response.
 * 
 * @author Mirko Polato
 *
 */
public abstract class TMDbResponse {

	/**
	 * The response status (default sets to NONE)
	 */
	protected TMDbStatus status = TMDbStatus.NONE;
	
	/**
	 * Initializes the response basic information based on the given JSON object.
	 *  
	 * @param json The JSON response
	 */
	public TMDbResponse(JSONObject json) {
		if (json.has(TMDbConstants.STATUS_CODE)) setStatus(TMDbStatus.getStatusByCode(json.getInt(TMDbConstants.STATUS_CODE)));
	}
	
	/**
	 * Initializes the response status with the given one.
	 * 
	 * @param status The response status
	 */
	public TMDbResponse(TMDbStatus status) {
		this.status = status;
	}
	
	/**
	 * Gets whether the response status is an error.
	 * 
	 * @return Whether the response status is an error.
	 */
	public boolean hasError() {
		return status.getCode() > TMDbStatus.SUCCESS.getCode();
	}
	
	/**
	 * Gets the response status.
	 * 
	 * @return The response status
	 */
	public TMDbStatus getStatus() {
		return status;
	}

	/**
	 * Sets the response status.
	 * 
	 * @param status The new response status
	 */
	protected void setStatus(TMDbStatus status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return status.getMessage();
	}
}
