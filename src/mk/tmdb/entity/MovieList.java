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

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import mk.tmdb.core.Constants;
import mk.tmdb.core.TMDbAPI;
import mk.tmdb.entity.movie.MovieReduced;
import mk.tmdb.exception.ResponseException;
import mk.tmdb.utils.ResponseArray;
import mk.tmdb.utils.ResponseObject;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Class that represents a list of movies.
 * 
 * @author Mirko Polato
 *
 */
public class MovieList extends Entity {

	//region Fields
	
	/**
	 * The list ID.
	 */
	private String id;
	
	/**
	 * The element count of the list
	 */
	private int count;
	
	/**
	 * Number of the favorite movies in the list.
	 */
	private int favoritesCount;
	
	/**
	 * The poster path of the list.
	 */
	private String posterPath;
	
	/**
	 * The list name.
	 */
	private String name;
	
	/**
	 * The list type.
	 */
	private String type = null;
	
	/**
	 * The list description.
	 */
	private String description;
	
	/**
	 * Codes for the representation of names of languages.
	 */
	private String iso639_1;
	
	/**
	 * The name of the list creator.
	 */
	private String creator = null;
	
	/**
	 * The list of movies.
	 */
	private Set<MovieReduced> movies = Collections.synchronizedSet(new LinkedHashSet<MovieReduced>());
	
	//endregion
	
	/**
	 * Creates a new instance of MovieList based on the origin JSON object.
	 * 
	 * @param json The origin JSON object
	 */
	public MovieList(JSONObject json) {
		super(json);
		parseJSON(json);
	}
	
	/**
	 * Copy constructor.
	 * 
	 * @param list The movie list to copy
	 */
	public MovieList(MovieList list) {
		this(list.getOriginJSON());
	}
	
	//region Getters/Setters
	
	/**
	 * Gets the list ID.
	 * 
	 * @return The list ID
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Sets the list ID.
	 * 
	 * @param id The new list ID
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * Gets the movies count.
	 * 
	 * @return The movies count
	 */
	public int getCount() {
		return count;
	}
	
	/**
	 * Sets the movies count.
	 * 
	 * @param count The new movies count
	 */
	public void setCount(int count) {
		this.count = count;
	}
	
	/**
	 * Gets the number of favorite movies in the list.
	 * 
	 * @return The number of favorite movies in the list.
	 */
	public int getFavoritesCount() {
		return favoritesCount;
	}
	
	/**
	 * Sets the number of favorite movies in the list.
	 * 
	 * @param favoritesCount The new number of favorite movies in the list.
	 */
	public void setFavoritesCount(int favoritesCount) {
		this.favoritesCount = favoritesCount;
	}
	
	/**
	 * Gets the poster path.
	 *  
	 * @return The poster path
	 */
	public String getPosterPath() {
		return posterPath;
	}
	
	/**
	 * Sets the poster path.
	 * 
	 * @param posterPath the new poster path
	 */
	public void setPosterPath(String posterPath) {
		this.posterPath = posterPath;
	}
	
	/**
	 * Gets the list name.
	 * 
	 * @return The list name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the list name.
	 * 
	 * @param name The new list name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Gets the list type.
	 * 
	 * @return The list type
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * Sets the list type.
	 * 
	 * @param type The new list type
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * Checks if the list type is set.
	 * @return Whether the list type is set or not.
	 */
	public boolean isTypeSet() {
		return type != null;
	}
	
	/**
	 * Gets the list description.
	 * 
	 * @return The list description
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Sets the list description.
	 * 
	 * @param description The new list description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Gets the account ISO 639-1.
	 * 
	 * @return The account ISO 639-1
	 */
	public String getIso639_1() {
		return iso639_1;
	}
	
	/**
	 * Sets the account ISO 639-1.
	 * 
	 * @param iso639_1 The new account ISO 639-1
	 */
	public void setIso639_1(String iso639_1) {
		this.iso639_1 = iso639_1;
	}
	
	/**
	 * Gets the creator of the list.
	 * 
	 * @return The creator of the list
	 */
	public String getCreator() {
		return creator;
	}

	/**
	 * Sets the creator of the list.
	 * 
	 * @param creator The new list creator
	 */
	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	/**
	 * Checks if the creator is set.
	 * 
	 * @return Whether the creator is set or not
	 */
	public boolean isCreatorSet() {
		return creator != null;
	}

	/**
	 * Gets the movies list.
	 * 
	 * @return The movies list
	 */
	public Set<MovieReduced> getMovies() {
		return movies;
	}

	/**
	 * Sets the movies list.
	 * 
	 * @param movies The new movies list
	 */
	public void setMovies(Set<MovieReduced> movies) {
		this.movies.clear();
		this.movies.addAll(movies);
	}
	
	//endregion

	/**
	 * Parses the origin JSON object.
	 */
	private boolean parseJSON(JSONObject json) {
		
		if (json.isNullObject()) return false;
		
		setId(json.getString(Constants.ID));
		setDescription(json.getString(Constants.DESCRIPTION));
		if (json.has(Constants.LIST_TYPE)) setType(json.getString(Constants.LIST_TYPE));
		setName(json.getString(Constants.NAME));
		setFavoritesCount(json.getInt(Constants.FAVORITE_COUNT));
		setCount(json.getInt(Constants.ITEM_COUNT));
		setPosterPath(json.getString(Constants.POSTER_PATH));
		setIso639_1(json.getString(Constants.ISO_6391));
		if (json.has(Constants.CREATED_BY)) setCreator(json.getString(Constants.CREATED_BY));
		
		if (json.has(Constants.ITEMS)) {
			JSONArray array = json.getJSONArray(Constants.ITEMS);
			movies.clear();
			for (Object obj : array) {
				movies.add(new MovieReduced((JSONObject) obj));
			}
		}
		
		return true;
	}
	
	//region Static methods
	
	/**
	 * Gets a list by id.
	 * 
	 * @param listID The list ID
	 * @return The TMDb Api response object
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
	public static MovieList getList(String listID) throws ResponseException {
		
		ResponseObject response = TMDbAPI.getList(listID);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			return new MovieList(response.getData());
		}
	}
	
	/**
	 * Searches for lists by name and description.
	 * Returns the results of the first page.
	 * 
	 * @param name The list name or description
	 * @return The list of movie lists
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
	public static Set<MovieList> searchByName(String name) throws ResponseException {
		return searchByName(name, 1);
	}
	
	/**
	 * Searches for lists by name and description.
	 * Returns the results of the given page number.
	 * 
	 * @param name The list name or description
	 * @param page The page number to retrieve
	 * @return The list of movie lists
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
	public static Set<MovieList> searchByName(String name, int page) throws ResponseException {
		ResponseArray response = TMDbAPI.searchListByName(name, page);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			Set<JSONObject> array = response.getData();
			Set<MovieList> lists = new LinkedHashSet<MovieList>();
			for(JSONObject json : array) {
				lists.add(new MovieList(json));
			}
			
			return lists;
		}
	}
	
	/**
	 * Searches for lists by name and description. Gets all the results.
	 * 
	 * @param name The list name or description
	 * @return The list of movie lists
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
	public static Set<MovieList> fullSearchByName(String name) throws ResponseException {
		ResponseArray response = TMDbAPI.fullSearchListByName(name);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			Set<JSONObject> array = response.getData();
			Set<MovieList> lists = new LinkedHashSet<MovieList>();
			for(JSONObject json : array) {
				lists.add(new MovieList(json));
			}
			
			return lists;
		}
	}
	
	//endregion
	
}
