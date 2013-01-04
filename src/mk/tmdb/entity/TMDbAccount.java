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

import mk.tmdb.core.TMDbConstants;
import mk.tmdb.core.TMDbAPI;
import mk.tmdb.entity.movie.TMDbMovieReduced;
import mk.tmdb.exception.ResponseException;
import mk.tmdb.utils.TMDbResponseArray;
import mk.tmdb.utils.TMDbResponseObject;
import net.sf.json.JSONObject;

/**
 * This class represent an user account. 
 * Moreover can store the authenticated session id by calling the {@link #setSessionID(String) setSessionID} method.
 * To get the the information about a specific account call the {@link #getInformation(String) getInformation} static method.
 * 
 * @author Mirko Polato
 *
 */
public class TMDbAccount extends TMDbEntity {

	//region Fields
	
	/**
	 * The account id.
	 */
	private int id;
	
	/**
	 * Codes for the representation of names of languages.
	 */
	private String iso639_1;
	
	/**
	 * Codes for the names of countries.
	 */
	private String iso3166_1;
	
	/**
	 * .The account name.
	 */
	private String name;
	
	/**
	 * Whether the account is for adult only.
	 */
	private boolean adult;	
	
	/**
	 * The account user name.
	 */
	private String username;
	
	/**
	 * The authenticated session ID.
	 */
	private String sessionID;
	
	/**
	 * The favorite movies list for this account.
	 */
	private Set<TMDbMovieReduced> favoriteMovies = Collections.synchronizedSet(new LinkedHashSet<TMDbMovieReduced>());

	/**
	 * The favorite lists list for this account.
	 */
	private Set<TMDbMovieList> favoriteLists = Collections.synchronizedSet(new LinkedHashSet<TMDbMovieList>());
	
	/**
	 * The rated movies list for this account.
	 */
	private Set<TMDbMovieReduced> ratedMovies = Collections.synchronizedSet(new LinkedHashSet<TMDbMovieReduced>());
	
	/**
	 * The watch list for this account.
	 */
	private Set<TMDbMovieReduced> watchlist = Collections.synchronizedSet(new LinkedHashSet<TMDbMovieReduced>());
	
	//endregion
	
	/**
	 * Creates a new account based on the JSON object passed as parameter.
	 * 
	 * @param json The JSON object
	 */
	public TMDbAccount(JSONObject json) {
		super(json);
		parseJSON(json);
	}
	
	/**
	 * Copy constructors.
	 * 
	 * @param account The account to copy
	 */
	public TMDbAccount(TMDbAccount account) {
		this(account.getOriginJSON());
	}
	
	//region Getters/Setters
	
	/**
	 * Gets the account ID.
	 * 
	 * @return The account ID
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Sets the account ID.
	 * 
	 * @param id The new account ID
	 */
	public void setId(int id) {
		this.id = id;
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
	 * Gets the account ISO 3166-1.
	 * 
	 * @return The account ISO 3166-1
	 */
	public String getIso3166_1() {
		return iso3166_1;
	}
	
	/**
	 * Sets the account ISO 3166-1.
	 * 
	 * @param iso3166_1 The new account ISO 3166-1
	 */
	public void setIso3166_1(String iso3166_1) {
		this.iso3166_1 = iso3166_1;
	}
	
	/**
	 * Gets the account name.
	 * 
	 * @return The account name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the account name.
	 * 
	 * @param name The new account name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Gets whether the account is for adult only.
	 * 
	 * @return Whether the account is for adult only.
	 */
	public boolean isAdult() {
		return adult;
	}
	
	/**
	 * Sets whether the account is for adult only.
	 * 
	 * @param adult True if the account is for adult only
	 */
	public void setAdult(boolean adult) {
		this.adult = adult;
	}
	
	/**
	 * Gets the account user name.
	 * 
	 * @return The account user name
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * Sets the account user name.
	 * 
	 * @param username The new account user name
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * Gets the session ID.
	 * 
	 * @return The session ID
	 */
	public String getSessionID() {
		return sessionID;
	}
	
	/**
	 * Sets the session ID.
	 * 
	 * @param sessionID The new session ID
	 */
	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}
	
	/**
	 * Gets the watch list.
	 * 
	 * @return The watch list.
	 */
	public Set<TMDbMovieReduced> getWatchlist() {
		return watchlist;
	}

	/**
	 * Sets the watch list.
	 * 
	 * @param watchlist The new watch list
	 */
	public void setWatchlist(Set<TMDbMovieReduced> watchlist) {
		this.watchlist.addAll(watchlist);
	}

	/**
	 * Gets the favorite movies list.
	 * 
	 * @return The favorite movies list
	 */
	public Set<TMDbMovieReduced> getFavoriteMovies() {
		return this.favoriteMovies;
	}
	
	/**
	 * Sets the favorite movies list.
	 * 
	 * @param favoriteMovies The new favorite movies list
	 */
	public void setFavoriteMovies(Set<TMDbMovieReduced> favoriteMovies) {
		this.favoriteMovies.addAll(favoriteMovies);
	}
	
	/**
	 * Gets the favorite lists list.
	 * 
	 * @return The favorite lists list
	 */
	public Set<TMDbMovieList> getFavoriteLists() {
		return this.favoriteLists;
	}

	/**
	 * Sets the favorite lists list.
	 * 
	 * @param favoriteLists The new favorite lists list
	 */
	public void setFavoriteLists(Set<TMDbMovieList> favoriteLists) {
		this.favoriteLists.clear();
		this.favoriteLists.addAll(favoriteLists);
	}

	/**
	 * Gets the top rated movies list.
	 * 
	 * @return The top rated movies list
	 */
	public Set<TMDbMovieReduced> getRatedMovies() {
		return this.ratedMovies;
	}
	
	/**
	 * Sets the top rated movies list.
	 * 
	 * @param ratedMovies The new top rated movies list
	 */
	public void setRatedMovies(Set<TMDbMovieReduced> ratedMovies) {
		this.ratedMovies.clear();
		this.ratedMovies.addAll(ratedMovies);
	}
	
	//endregion
	
	/**
	 * Parses the origin JSON object.
	 */
	private boolean parseJSON(JSONObject json) {
		
		if (json.isNullObject()) return false;
		
		setId(json.getInt(TMDbConstants.ID));
		setName(json.getString(TMDbConstants.NAME));
		setUsername(json.getString(TMDbConstants.USERNAME));
		setIso3166_1(json.getString(TMDbConstants.ISO_31661));
		setIso639_1(json.getString(TMDbConstants.ISO_6391));
		setAdult(json.getBoolean(TMDbConstants.ADULT));
		
		return true;
	}
	
	// ----------------
	//  Static methods
	// ----------------
	
	/**
	 * Static method that gets a new Account instance filled with the basic information. 
	 * 
	 * @param sessionID The session ID
	 * @return A new user account
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
	public static TMDbAccount getInformation(String sessionID) throws ResponseException {
		
		TMDbResponseObject response = TMDbAPI.getAccountInformation(sessionID);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			TMDbAccount account = new TMDbAccount(response.getData());
			account.setSessionID(sessionID);
			return account;
		}
	}
	
	//region Favorite Movies
	
	/**
	 * Gets the list of favorite movies for this account.
	 * Returns the results of the first page.
	 * 
	 * @param account The user account
	 * @return The favorite movies list
	 * @throws ResponseException Throws whether the server response is not a success. 
	 */
	public static Set<TMDbMovieReduced> getFavoriteMovies(TMDbAccount account) throws ResponseException {
		return getFavoriteMovies(account, 1);
	}
	
	/**
	 * Gets the list of favorite movies for an account.
	 * Returns the results of the given page number.
	 * 
	 * @param account The user account
	 * @param page The page number to retrieve
	 * @return The favorite movies list
	 * @throws ResponseException Throws whether the server response is not a success. 
	 */
	public static Set<TMDbMovieReduced> getFavoriteMovies(TMDbAccount account, int page) throws ResponseException {
		
		TMDbResponseArray response = TMDbAPI.getFavoriteMovies(account, page);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			
			Set<TMDbMovieReduced> movies = new LinkedHashSet<TMDbMovieReduced>();
			Set<JSONObject> data = response.getData();
			
			for (JSONObject json : data) {
				movies.add(new TMDbMovieReduced(json));
			}
			
			return movies;
		}
	}
	
	/**
	 * Gets the entire list of favorite movies for an account.
	 * 
	 * @param account The user account
	 * @return The entire favorite movies list
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
	public static Set<TMDbMovieReduced> getAllFavoriteMovies(TMDbAccount account) throws ResponseException {
		TMDbResponseArray response = TMDbAPI.getAllFavoriteMovies(account);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			
			Set<TMDbMovieReduced> movies = new LinkedHashSet<TMDbMovieReduced>();
			Set<JSONObject> data = response.getData();
			
			for (JSONObject json : data) {
				movies.add(new TMDbMovieReduced(json));
			}
			
			return movies;
		}
	}
	
	/**
	 * Adds a movie to this accounts favorite list.
	 * 
	 * @param account The user account
	 * @param movieID The movie ID
	 * @return Whether the operation succeeded or not 
	 */
	public static boolean addMovieToFavorites(TMDbAccount account, int movieID) {
		TMDbResponseObject response = TMDbAPI.addMovieToFavorites(account, movieID);
		return !response.hasError();
	}
	
	/**
	 * Removes a movie from this accounts favorite list.
	 * 
	 * @param account The user account
	 * @param movieID The movie ID
	 * @return Whether the operation succeeded or not 
	 */
	public static boolean removeMovieFromFavorites(TMDbAccount account, int movieID) {
		TMDbResponseObject response = TMDbAPI.removeMovieFromFavorites(account, movieID);
		return !response.hasError();
	}
	
	//endregion
	
	//region Favorite Lists
	
	/**
	 * Gets the lists that you have created and marked as a favorite.
	 * Returns the results of the first page.
	 * 
	 * @param account The user account
	 * @return The favorite lists list
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
	public static Set<TMDbMovieList> getFavoriteLists(TMDbAccount account) throws ResponseException {
		return getFavoriteLists(account, 1);
	}
	
	/**
	 * Gets the lists that you have created and marked as a favorite.
	 * Returns the results of the given page number.
	 * 
	 * @param account The user account
	 * @param page The page number to retrieve
	 * @return The favorite lists list
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
	public static Set<TMDbMovieList> getFavoriteLists(TMDbAccount account, int page) throws ResponseException {
		
		TMDbResponseArray response = TMDbAPI.getFavoriteLists(account, page);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			
			Set<TMDbMovieList> lists = new LinkedHashSet<TMDbMovieList>();
			Set<JSONObject> data = response.getData();
			
			for (JSONObject json : data) {
				lists.add(new TMDbMovieList(json));
			}
			
			return lists;
		}
	}
	
	/**
	 * Gets all the lists that you have created and marked as a favorite.
	 * 
	 * @param account The user account
	 * @return The entire favorite lists list
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
	public static Set<TMDbMovieList> getAllFavoriteLists(TMDbAccount account) throws ResponseException {
		TMDbResponseArray response = TMDbAPI.getAllFavoriteLists(account);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			
			Set<TMDbMovieList> lists = new LinkedHashSet<TMDbMovieList>();
			Set<JSONObject> data = response.getData();
			
			for (JSONObject json : data) {
				lists.add(new TMDbMovieList(json));
			}
			
			return lists;
		}
	}
	
	//endregion
	
	//region Top Rated
	
	/**
	 * Gets the list of rated movies for this account.
	 * Returns the results of the first page.
	 * 
	 * @param account The user account
	 * @return The rated movies list
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
	public static Set<TMDbMovieReduced> getRatedMovies(TMDbAccount account) throws ResponseException {
		return getRatedMovies(account, 1);
	}
	
	/**
	 * Gets the list of rated movies for an account.
	 * Returns the results of the given page number.
	 *  
	 * @param account The user account
	 * @param page The page number to retrieve
	 * @return The rated movies list
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
	public static Set<TMDbMovieReduced> getRatedMovies(TMDbAccount account, int page) throws ResponseException {
		
		TMDbResponseArray response = TMDbAPI.getRatedMovies(account, page);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			
			Set<TMDbMovieReduced> movies = new LinkedHashSet<TMDbMovieReduced>();
			Set<JSONObject> data = response.getData();
			
			for (JSONObject json : data) {
				movies.add(new TMDbMovieReduced(json));
			}
			
			return movies;
		}
	}
	
	/**
	 * Gets the list of all rated movies for an account.
	 * 
	 * @param account The user account
	 * @return The rated movies list
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
	public static Set<TMDbMovieReduced> getAllRatedMovies(TMDbAccount account) throws ResponseException {
		TMDbResponseArray response = TMDbAPI.getAllRatedMovies(account);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			
			Set<TMDbMovieReduced> movies = new LinkedHashSet<TMDbMovieReduced>();
			Set<JSONObject> data = response.getData();
			
			for (JSONObject json : data) {
				movies.add(new TMDbMovieReduced(json));
			}
			
			return movies;
		}
	}
	
	//endregion
	
	//region Watch list
	
	/**
	 * Gets the list of movies on this accounts watch list.
	 * Returns the results of the first page.
	 * 
	 * @param account The user account
	 * @return The movies watch list
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
	public static Set<TMDbMovieReduced> getMovieWatchlist(TMDbAccount account) throws ResponseException {
		return getMovieWatchlist(account, 1);
	}
	
	/**
	 * Gets the list of movies on this accounts watch list.
	 * Returns the results of the given page number.
	 * 
	 * @param account The user account
	 * @param page The page number to retrieve
	 * @return The movies watch list
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
	public static Set<TMDbMovieReduced> getMovieWatchlist(TMDbAccount account, int page) throws ResponseException {
		
		TMDbResponseArray response = TMDbAPI.getMovieWatchList(account, page);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			
			Set<TMDbMovieReduced> movies = new LinkedHashSet<TMDbMovieReduced>();
			Set<JSONObject> data = response.getData();
			
			for (JSONObject json : data) {
				movies.add(new TMDbMovieReduced(json));
			}
			
			return movies;
		}
	}
	
	/**
	 * Gets the list of all movies on this accounts watch list.
	 * 
	 * @param account The user account
	 * @return The movies watch list
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
	public static Set<TMDbMovieReduced> getAllMovieWatchlist(TMDbAccount account) throws ResponseException {
		TMDbResponseArray response = TMDbAPI.getAllMovieWatchList(account);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			
			Set<TMDbMovieReduced> movies = new LinkedHashSet<TMDbMovieReduced>();
			Set<JSONObject> data = response.getData();
			
			for (JSONObject json : data) {
				movies.add(new TMDbMovieReduced(json));
			}
			
			return movies;
		}
	}
	
	/**
	 * Adds a movie to this accounts watch list.
	 * 
	 * @param account The user account
	 * @param movieID The movie ID
	 * @return Whether the operation succeeded or not
	 */
	public static boolean addMovieToWatchlist(TMDbAccount account, int movieID) {
		TMDbResponseObject response = TMDbAPI.addMovieToWatchlist(account, movieID);
		return !response.hasError();
	}
	
	/**
	 * Removes a movie from this accounts watch list.
	 * 
	 * @param account The user account
	 * @param movieID The movie ID
	 * @return Whether the operation succeeded or not
	 */
	public static boolean removeMovieToWatchlist(TMDbAccount account, int movieID) {
		TMDbResponseObject response = TMDbAPI.removeMovieFromWatchlist(account, movieID);
		return !response.hasError();
	}
	
	//endregion
	
}
