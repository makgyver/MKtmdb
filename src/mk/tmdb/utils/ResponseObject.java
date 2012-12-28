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

import net.sf.json.JSONObject;

/**
 * Class that represents a response simple object.
 * 
 * @author Mirko Polato
 *
 */
public class ResponseObject extends Response {

	/**
	 * The JSON object response
	 */
	private JSONObject data;
	
	/**
	 * Creates a new ResponseObject instance based on the given JSON object.
	 *  
	 * @param json The JSON response
	 */
	public ResponseObject(JSONObject json) {
		super(json);
		
		if (hasError()) setData(null);
		else setData(json);
	}
	
	/**
	 * Initializes the response status to the given one.
	 * 
	 * @param status The response status
	 */
	public ResponseObject(Status status) {
		super(status);
		setData(null);
	}
	
	/**
	 * Set the data to the given JSON object.
	 * 
	 * @param json The new data
	 */
	private void setData(JSONObject json) {
		this.data = json;
	}

	/**
	 * Gets the JSON object response.
	 * 
	 * @return The data response
	 */
	public JSONObject getData() {
		return data;
	}

}
