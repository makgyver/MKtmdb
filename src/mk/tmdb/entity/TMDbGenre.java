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

import java.util.LinkedList;
import java.util.List;

import mk.tmdb.core.TMDbConstants;
import mk.tmdb.core.TMDbAPI;
import mk.tmdb.entity.movie.TMDbMovieReduced;
import mk.tmdb.exception.TMDbResponseException;
import mk.tmdb.response.TMDbResponseArray;
import mk.tmdb.response.TMDbResponseObject;
import mk.tmdb.utils.Log;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Class that represents a movie genre.
 * 
 * @author Mirko Polato
 *
 */
public class TMDbGenre extends TMDbEntity {

	/**
	 * The genre ID.
	 */
	private int id;
	
	/**
	 * The genre name.
	 */
	private String name;

	/**
	 * Creates a new Genre based on the origin JSON object.
	 * 
	 * @param json The origin JSON object
	 */
	public TMDbGenre(JSONObject json) {
		super(json);
		parseJSON(json);
	}
	
	/**
	 * Copy constructor.
	 * 
	 * @param genre The genre to copy.
	 */
	public TMDbGenre(TMDbGenre genre) {
		this(genre.getOriginJSON());
	}
	
	//region Getters/Setters
	
	/**
	 * Gets the genre ID.
	 * 
	 * @return The genre ID
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the genre ID
	 * 
	 * @param id The new genre ID
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Gets the genre name.
	 * 
	 * @return The genre name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the genre name.
	 * 
	 * @param name The new genre name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	//endregion
	
	/**
	 * Parses the origin JSON object.
	 */
	private boolean parseJSON(JSONObject json) {

		if (json.isNullObject()) return false;		
		
		try {
			setId(json.getInt(TMDbConstants.ID));
			setName(json.getString(TMDbConstants.NAME));
			
		} catch (Exception e) {
			Log.print(e);
			return false;
		}
		
		return true;
	}
	
	//region Static methods
	
	/**
	 * Gets the genres list.
	 * 
	 * @return The genres list
	 * @throws TMDbResponseException Throws whether the server response is not a success.
	 */
	public static List<TMDbGenre> getList() throws TMDbResponseException {
		
		TMDbResponseObject response = TMDbAPI.getGenresList();
		
		if (response.hasError()) {
			throw new TMDbResponseException(response.getStatus());
		} else {
			List<TMDbGenre> genres = new LinkedList<TMDbGenre>();
			JSONArray array = response.getData().getJSONArray(TMDbConstants.GENRES);
			for(Object json : array) {
				genres.add(new TMDbGenre((JSONObject) json));
			}
			
			return genres;
		}
	}
	
	/**
	 * Gets the movies associated to the given genre. Returns the results of the first page.
	 * 
	 * @param genreID The genre ID
	 * @return The movies associated to the genre
	 * @throws TMDbResponseException Throws whether the server response is not a success.
	 */
	public static List<TMDbMovieReduced> getAssociatedMovies(int genreID) throws TMDbResponseException {
		return getAssociatedMovies(genreID, 1);
	}
	
	/**
	 * Gets the movies associated to the given genre. Returns the results of the given page number.
	 * 
	 * @param genreID The genre ID
	 * @param page The page number to retrieve
	 * @return The movies associated to the genre
	 * @throws TMDbResponseException Throws whether the server response is not a success.
	 */
	public static List<TMDbMovieReduced> getAssociatedMovies(int genreID, int page) throws TMDbResponseException {
		
		TMDbResponseArray response = TMDbAPI.getMoviesByGenre(genreID, page);
		
		if (response.hasError()) {
			throw new TMDbResponseException(response.getStatus());
		} else {
			List<TMDbMovieReduced> movies = new LinkedList<TMDbMovieReduced>();
			List<JSONObject> array = response.getData();
			for(JSONObject json : array) {
				movies.add(new TMDbMovieReduced(json));
			}
			
			return movies;
		}
	}
	
	/**
	 * Gets the movies associated to the given genre. Gets all the results.
	 * 
	 * @param genreID The genre ID
	 * @return The movies associated to the genre.
	 * @throws TMDbResponseException Throws whether the server response is not a success.
	 */
	public static List<TMDbMovieReduced> getAllAssociatedMovies(int genreID) throws TMDbResponseException {
		
		TMDbResponseArray response = TMDbAPI.getAllMoviesByGenre(genreID);
		
		if (response.hasError()) {
			throw new TMDbResponseException(response.getStatus());
		} else {
			List<TMDbMovieReduced> movies = new LinkedList<TMDbMovieReduced>();
			List<JSONObject> array = response.getData();
			for(JSONObject json : array) {
				movies.add(new TMDbMovieReduced(json));
			}
			
			return movies;
		}
	}
	
	//endregion
	
}
