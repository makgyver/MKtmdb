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

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import mk.tmdb.core.TMDbConstants;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Class that represents a response array.
 * 
 * @author Mirko Polato
 *
 */
public class TMDbResponseArray extends TMDbResponse {

	/**
	 * The response total number of pages.
	 */
	private int pages = 0;
	
	/**
	 * The page number of this response array (-1 if the response contains more than one page).
	 */
	private int page = 0;
	
	/**
	 * The JSON objects list contained in the response.
	 */
	private Set<JSONObject> data = Collections.synchronizedSet(new LinkedHashSet<JSONObject>());
	
	/**
	 * The total number of the retrieved results.
	 */
	private int results = 0;
	
	/**
	 * Creates a new ResponseArray instance based on the given JSON object.
	 * 
	 * @param json The JSON response
	 */
	public TMDbResponseArray(JSONObject json) {
		super(json);
		
		if (!hasError()) {
			
			if (json.has(TMDbConstants.PAGE)) setPage(json.getInt(TMDbConstants.PAGE));
			if (json.has(TMDbConstants.TOTAL_PAGES)) setPages(json.getInt(TMDbConstants.TOTAL_PAGES));
			if (json.has(TMDbConstants.TOTAL_RESULTS)) setResults(json.getInt(TMDbConstants.TOTAL_RESULTS));
		
			if (json.has(TMDbConstants.RESULT)) setData(json.getJSONArray(TMDbConstants.RESULT));
		}
	}
	
	/**
	 * Initializes the response status to the given one.
	 * 
	 * @param status The response status
	 */
	public TMDbResponseArray(TMDbStatus status) {
		super(status);
	}
	
	/**
	 * Gets the list of retrieved JSON object.
	 * 
	 * @return the list of JSON object
	 */
	public Set<JSONObject> getData() {
		return data;
	}
	
	/**
	 * Sets the list of the retrieved JSON object.
	 * 
	 * @param array The list of JSON object
	 */
	private void setData(JSONArray array) {
		for(Object obj : array) {
			data.add((JSONObject) obj);
		}
	}
	
	/**
	 * Adds a JSON object to the data.
	 * 
	 * @param json The JSON object to add
	 * @return Whether the operation succeeded or not
	 */
	public boolean addData(JSONObject json) {
		return data.add(json);
	}

	/**
	 * Gets the total number of the retrieved results.
	 * 
	 * @return The total number of the retrieved results
	 */
	public int getResults() {
		return results;
	}
	
	/**
	 * Sets the total number of the retrieved results.
	 * 
	 * @param results The new total number of the retrieved results
	 */
	private void setResults(int results) {
		this.results = results;
	}
	
	/**
	 * Gets the total number of the retrieved pages.
	 * 
	 * @return The total number of pages.
	 */
	public int getPages() {
		return pages;
	}
	
	/**
	 * Sets the total number of the retrieved pages.
	 * 
	 * @param pages The new total number of pages
	 */
	protected void setPages(int pages) {
		this.pages = pages;
	}
	
	/**
	 * Gets the page number of this response.
	 * 
	 * @return The page number of this response
	 */
	public int getPage() {
		return page;
	}
	
	/**
	 * Sets the page number of this response.
	 * 
	 * @param page The new page number of this response
	 */
	protected void setPage(int page) {
		this.page = page;
	}
	
}
