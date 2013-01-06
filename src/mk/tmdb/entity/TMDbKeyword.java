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

package mk.tmdb.entity;

import java.util.LinkedHashSet;
import java.util.Set;

import mk.tmdb.core.TMDbConstants;
import mk.tmdb.core.TMDbAPI;
import mk.tmdb.entity.movie.TMDbMovieReduced;
import mk.tmdb.exception.TMDbResponseException;
import mk.tmdb.response.TMDbResponseArray;
import mk.tmdb.response.TMDbResponseObject;
import mk.tmdb.utils.Log;
import net.sf.json.JSONObject;

/**
 * Class that represents a movie plot keyword.
 * 
 * @author Mirko Polato
 *
 */
public class TMDbKeyword extends TMDbEntity {

	/**
	 * The keyword ID.
	 */
	private int id;
	
	/**
	 * The keyword string.
	 */
	private String value;
	
	/**
	 * Creates a new Keyword based on the origin JSON object.
	 * 
	 * @param json The origin JSON object
	 */
	public TMDbKeyword(JSONObject json) {
		super(json);
		parseJSON(json);
	}
	
	/**
	 * Copy constructor.
	 * 
	 * @param keyword The keyword to copy
	 */
	public TMDbKeyword(TMDbKeyword keyword) {
		this(keyword.getOriginJSON());
	}
	
	//region Getters/Setters
	
	/**
	 * Gets the keyword ID.
	 * 
	 * @return The keyword ID
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the keyword ID.
	 * 
	 * @param id The new keyword ID
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Gets the keyword value.
	 * 
	 * @return The keyword string
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Sets the keyword value.
	 * 
	 * @param value The new keyword value
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
	//endregion
	
	/**
	 * Parses the origin JSON object.
	 */
	private boolean parseJSON(JSONObject json) {

		if (json.isNullObject()) return false;
		
		try {
			setId(json.getInt(TMDbConstants.ID));
			setValue(json.getString(TMDbConstants.NAME));
			
		} catch (Exception e) {
			Log.print(e);
			return false;
		}
		
		return true;
	}
	
	//region Static methods
	
	/**
	 * Gets the keyword information.
	 * 
	 * @param keywordID The keyword ID
	 * @return The keyword information
	 * @throws TMDbResponseException Throws whether the server response is not a success.
	 */
	public static TMDbKeyword getInformation(int keywordID) throws TMDbResponseException {
		
		TMDbResponseObject response = TMDbAPI.getKeywordInformation(keywordID);
		
		if (response.hasError()) {
			throw new TMDbResponseException(response.getStatus());
		} else {
			return new TMDbKeyword(response.getData());
		}
	}
	
	/**
	 * Gets the list of movies that has the specified keyword.
	 * Returns the results of the first page.
	 * 
	 * @param keywordID The keyword ID
	 * @return The list of movies that has the specified keyword
	 * @throws TMDbResponseException Throws whether the server response is not a success.
	 */
	public static Set<TMDbMovieReduced> getAssociatedMovies(int keywordID) throws TMDbResponseException {
		return getAssociatedMovies(keywordID, 1);
	}
	
	/**
	 * Gets the list of movies that has the specified keyword.
	 * Returns the results of the given page number.
	 * 
	 * @param keywordID The keyword ID
	 * @param page The page number to retrieve
	 * @return The list of movies that has the specified keyword
	 * @throws TMDbResponseException Throws whether the server response is not a success.
	 */
	public static Set<TMDbMovieReduced> getAssociatedMovies(int keywordID, int page) throws TMDbResponseException {
		
		TMDbResponseArray response = TMDbAPI.getMoviesByKeyword(keywordID, page);
		
		if (response.hasError()) {
			throw new TMDbResponseException(response.getStatus());
		} else {
			
			Set<JSONObject> array = response.getData();
			Set<TMDbMovieReduced> movies = new LinkedHashSet<TMDbMovieReduced>();
			for(JSONObject json : array) {
				movies.add(new TMDbMovieReduced(json));
			}
			
			return movies;
		}
	}
	
	/**
	 * Gets the list of movies that has the specified keyword. Gets all the results.
	 * 
	 * @param keywordID The keyword ID
	 * @return The list of movies that has the specified keyword
	 * @throws TMDbResponseException Throws whether the server response is not a success.
	 */
	public static Set<TMDbMovieReduced> getAllAssociatedMovies(int keywordID) throws TMDbResponseException {
		
		TMDbResponseArray response = TMDbAPI.getAllMoviesByKeyword(keywordID);
		
		if (response.hasError()) {
			throw new TMDbResponseException(response.getStatus());
		} else {
			
			Set<JSONObject> array = response.getData();
			Set<TMDbMovieReduced> movies = new LinkedHashSet<TMDbMovieReduced>();
			for(JSONObject json : array) {
				movies.add(new TMDbMovieReduced(json));
			}
			
			return movies;
		}
	}
	
	/**
	 * Searches for keyword by name.
	 * Returns the results of the first page.
	 * 
	 * @param name The keyword name
	 * @return The list of keywords
	 * @throws TMDbResponseException Throws whether the server response is not a success.
	 */
	public static Set<TMDbKeyword> searchByName(String name) throws TMDbResponseException {
		return searchByName(name, 1);
	}
	
	/**
	 * Searches for keyword by name.
	 * Returns the results of the given page number.
	 * 
	 * @param name The keyword name
	 * @param page The page number to retrieve
	 * @return The list of keywords
	 * @throws TMDbResponseException Throws whether the server response is not a success.
	 */
	public static Set<TMDbKeyword> searchByName(String name, int page) throws TMDbResponseException {
		TMDbResponseArray response = TMDbAPI.searchKeywordByName(name, page);
		
		if (response.hasError()) {
			throw new TMDbResponseException(response.getStatus());
		} else {
			Set<JSONObject> array = response.getData();
			Set<TMDbKeyword> keys = new LinkedHashSet<TMDbKeyword>();
			for(JSONObject json : array) {
				keys.add(new TMDbKeyword(json));
			}
			
			return keys;
		}
	}
	
	/**
	 * Searches for keyword by name. Gets all the results.
	 * 
	 * @param name The keyword name
	 * @return The list of keywords
	 * @throws TMDbResponseException Throws whether the server response is not a success.
	 */
	public static Set<TMDbKeyword> fullSearchByName(String name) throws TMDbResponseException {
		TMDbResponseArray response = TMDbAPI.fullSearchKeywordByName(name);
		
		if (response.hasError()) {
			throw new TMDbResponseException(response.getStatus());
		} else {
			Set<JSONObject> array = response.getData();
			Set<TMDbKeyword> keys = new LinkedHashSet<TMDbKeyword>();
			for(JSONObject json : array) {
				keys.add(new TMDbKeyword(json));
			}
			
			return keys;
		}
	}
	
	//endregion
	
}
