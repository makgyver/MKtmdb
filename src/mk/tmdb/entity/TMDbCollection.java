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
import java.util.LinkedList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import mk.tmdb.core.TMDbConstants;
import mk.tmdb.core.TMDbAPI;
import mk.tmdb.entity.image.TMDbBackdrop;
import mk.tmdb.entity.image.TMDbPoster;
import mk.tmdb.entity.movie.TMDbMovieThumbnail;
import mk.tmdb.exception.TMDbResponseException;
import mk.tmdb.response.TMDbResponseArray;
import mk.tmdb.response.TMDbResponseObject;

/**
 * Class that represents a movie collection. 
 * 
 * @author Mirko Polato
 *
 */
public class TMDbCollection extends TMDbEntity {

	//region Fields
	
	/**
	 * The collection backdrop path.
	 */
	private String backdropPath;
	
	/**
	 * The collection ID.
	 */
	private int id;
	
	/**
	 * The collection name.
	 */
	private String name;
	
	/**
	 * The collection poster path.
	 */
	private String posterPath;
	
	/**
	 * The movies list belongs this collection.
	 */
	private List<TMDbMovieThumbnail> movies = Collections.synchronizedList(new LinkedList<TMDbMovieThumbnail>());
	
	/**
	 * The posters list of this collection.
	 */
	private List<TMDbPoster> posters = Collections.synchronizedList(new LinkedList<TMDbPoster>());
	
	/**
	 * The backdrops list of this collection.
	 */
	private List<TMDbBackdrop> backdrops = Collections.synchronizedList(new LinkedList<TMDbBackdrop>());
	
	//endregion
	
	/**
	 * Creates a new movie Collection based on the origin JSON object.
	 * 
	 * @param json The origin JSON object
	 */
	public TMDbCollection(JSONObject json) {
		super(json);
		parseJSON(json);
	}
	
	/**
	 * Copy constructor.
	 * 
	 * @param collection The collection to copy
	 */
	public TMDbCollection(TMDbCollection collection) {
		this(collection.getOriginJSON());
	}

	//region Getters/Setters
	
	/**
	 * Gets the backdrop path
	 * 
	 * @return The backdrop path
	 */
	public String getBackdropPath() {
		return backdropPath;
	}

	/**
	 * Sets the backdrop path.
	 * 
	 * @param backdropPath The new backdrop path
	 */
	public void setBackdropPath(String backdropPath) {
		this.backdropPath = backdropPath;
	}

	/**
	 * Gets the collection ID.
	 * 
	 * @return The collection ID
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the collection ID.
	 * 
	 * @param id The collection ID
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Gets the name of the collection.
	 * 
	 * @return The nam of the collection
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the collection.
	 * 
	 * @param name The new collection name
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @param posterPath The new poster path
	 */
	public void setPosterPath(String posterPath) {
		this.posterPath = posterPath;
	}

	/**
	 * Gets the movies belong to this collection.
	 * 
	 * @return The movies belong to this collection
	 */
	public List<TMDbMovieThumbnail> getMovies() {
		return movies;
	}

	/**
	 * Sets the movies list belong to this collection.
	 * 
	 * @param movies The new movies list belong to this collection
	 */
	public void setMovies(List<TMDbMovieThumbnail> movies) {
		this.movies = movies;
	}
	
	/**
	 * Gets the posters of the collection.
	 * 
	 * @return The posters of the collection
	 */
	public List<TMDbPoster> getPosters() {
		return posters;
	}

	/**
	 * Sets the posters of the collection.
	 * 
	 * @param posters The new posters of the collection
	 */
	public void setPosters(List<TMDbPoster> posters) {
		this.posters = posters;
	}

	/**
	 * Gets the backdrops of the collection.
	 * 
	 * @return The backdrops of the collection
	 */
	public List<TMDbBackdrop> getBackdrops() {
		return backdrops;
	}

	/**
	 * Sets the backdrops of the collection.
	 * 
	 * @param backdrops The new backdrops of the collection
	 */
	public void setBackdrops(List<TMDbBackdrop> backdrops) {
		this.backdrops = backdrops;
	}

	//endregion
	
	/**
	 * Parses the origin JSON object.
	 */
	private boolean parseJSON(JSONObject json) {
		
		if (json.isNullObject()) return false;
		
		setPosterPath(json.getString(TMDbConstants.POSTER_PATH));
		setBackdropPath(json.getString(TMDbConstants.BACKDROP_PATH));
		setName(json.getString(TMDbConstants.NAME));
		setId(json.getInt(TMDbConstants.ID));
		
		if (json.has(TMDbConstants.PARTS)) {
			JSONArray array = json.getJSONArray(TMDbConstants.PARTS);
			for(Object obj : array) {
				movies.add(new TMDbMovieThumbnail((JSONObject) obj));
			}
		}
		
		return true;
	}
	
	//region Static methods
	
	/**
	 * Gets a new Collection filled with the retrieved information.
	 * 
	 * @param collectionID The collection ID
	 * @return The collection
	 * @throws TMDbResponseException Throws whether the server response is not a success.
	 */
	public static TMDbCollection getInformation(int collectionID) throws TMDbResponseException {
		TMDbResponseObject response = TMDbAPI.getCollectionInformation(collectionID);
		
		if (response.hasError()) {
			throw new TMDbResponseException(response.getStatus());
		} else {
			return new TMDbCollection(response.getData());
		}
	}
	
	/**
	 * Retrieves from the web the posters of the collection.
	 * 
	 * @param collectionID The collection ID
	 * @return The posters of the collection
	 * @throws TMDbResponseException Throws whether the server response is not a success.
	 */
	public static List<TMDbPoster> getPosters(int collectionID) throws TMDbResponseException {
		TMDbResponseObject response = TMDbAPI.getCollectionImages(collectionID);
		
		if (response.hasError()) {
			throw new TMDbResponseException(response.getStatus());
		} else {
			JSONArray array = response.getData().getJSONArray(TMDbConstants.POSTERS);
			List<TMDbPoster> images = new LinkedList<TMDbPoster>(); 
			for(Object obj : array) {
				images.add(new TMDbPoster((JSONObject) obj));
			}
			return images;
		}
	}
	
	/**
	 * Retrieves from the web the backdrops of the collection.
	 * 
	 * @param collectionID The collection ID
	 * @return The backdrops of the collection
	 * @throws TMDbResponseException Throws whether the server response is not a success.
	 */
	public static List<TMDbBackdrop> getBackrops(int collectionID) throws TMDbResponseException {
		TMDbResponseObject response = TMDbAPI.getCollectionImages(collectionID);
		
		if (response.hasError()) {
			throw new TMDbResponseException(response.getStatus());
		} else {
			JSONArray array = response.getData().getJSONArray(TMDbConstants.BACKDROPS);
			List<TMDbBackdrop> images = new LinkedList<TMDbBackdrop>(); 
			for(Object obj : array) {
				images.add(new TMDbBackdrop((JSONObject) obj));
			}
			return images;
		}
	}
	
	//endregion

	//region Search methods
	
	/**
	 * Searches for the collections by name.
	 * 
	 * @param name The collection name
	 * @return The list of collection that match with the given name
	 * @throws TMDbResponseException Throws whether the server response is not a success.
	 */
	public static List<TMDbCollection> searchByName(String name) throws TMDbResponseException {
		return searchByName(name, 1);
	}
	
	/**
	 * Searches for the collections by name.
	 * 
	 * @param name The collection name
	 * @param page The page number to retrieve
	 * @return The list of collection that match with the given name
	 * @throws TMDbResponseException Throws whether the server response is not a success.
	 */
	public static List<TMDbCollection> searchByName(String name, int page) throws TMDbResponseException {
		
		TMDbResponseArray response = TMDbAPI.searchCollectionByName(name, page);
		
		if (response.hasError()) {
			throw new TMDbResponseException(response.getStatus());
		} else {
			List<JSONObject> array = response.getData();
			List<TMDbCollection> collections = new LinkedList<TMDbCollection>();
			for(JSONObject json : array) {
				collections.add(new TMDbCollection(json));
			}
			
			return collections;
		}
	}

	/**
	 * Searches for the collections by name. Gets all the results.
	 * 
	 * @param name The collection name
	 * @return The list of collection that match with the given name
	 * @throws TMDbResponseException Throws whether the server response is not a success.
	 */
	public static List<TMDbCollection> fullSearchByName(String name) throws TMDbResponseException {
		
		TMDbResponseArray response = TMDbAPI.fullSearchCollectionByName(name);
		
		if (response.hasError()) {
			throw new TMDbResponseException(response.getStatus());
		} else {
			List<JSONObject> array = response.getData();
			List<TMDbCollection> collections = new LinkedList<TMDbCollection>();
			for(JSONObject json : array) {
				collections.add(new TMDbCollection(json));
			}
			
			return collections;
		}
	}

	//endregion
	
}
