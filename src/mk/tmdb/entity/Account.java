package mk.tmdb.entity;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import mk.tmdb.core.Constants;
import mk.tmdb.core.TMDBAPI;
import mk.tmdb.exception.ResponseException;
import mk.tmdb.utils.ResponseArray;
import mk.tmdb.utils.ResponseObject;
import net.sf.json.JSONObject;

/**
 * This class represent an user account. 
 * Moreover can store the authenticated session id by calling the {@link #setSessionID(String) setSessionID} method.
 * To get the the information about a specific account call the {@link #getInformation(String) getInformation} static method.
 * 
 * @author Mirko Polato
 *
 */
public class Account extends Entity {

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
	
	//endregion
	
	/**
	 * Creates a new account based on the Json object passed as parameter.
	 * 
	 * @param json The Json object
	 */
	public Account(JSONObject json) {
		super(json);
		parseJSON(json);
	}
	
	/**
	 * Copy constructors.
	 * 
	 * @param account The account to copy
	 */
	public Account(Account account) {
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
	 * Gets the accoun name.
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
	
	//endregion
	
	/**
	 * Parses the Json object for retrieving the account information.
	 */
	@Override
	protected boolean parseJSON(JSONObject json) {
		
		setId(json.getInt(Constants.ID));
		setName(json.getString(Constants.NAME));
		setUsername(json.getString(Constants.USERNAME));
		setIso3166_1(json.getString(Constants.ISO_31661));
		setIso639_1(json.getString(Constants.ISO_6391));
		setAdult(json.getBoolean(Constants.ADULT));
		
		return true;
	}
	
	/**
	 * Static method that gets the basic information for an account. 
	 * 
	 * @param sessionID The session ID
	 * @return The account
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
	public static Account getInformation(String sessionID) throws ResponseException {
		
		ResponseObject response = TMDBAPI.getAccountInformation(sessionID);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			Account account = new Account(response.getData());
			account.setSessionID(sessionID);
			return account;
		}
	}
	
	//region Favorite Movies
	
	/**
	 * Gets the list of favorite movies for this account.
	 * 
	 * @return The favorite movies list
	 * @throws ResponseException Throws whether the server response is not a success. 
	 */
	public List<Movie> getFavoriteMovies() throws ResponseException {
		return getFavoriteMovies(1);
	}
	
	/**
	 * Gets the list of favorite movies for an account.
	 * 
	 * @param page The page number to retrieve
	 * @return The favorite movies list
	 * @throws ResponseException Throws whether the server response is not a success. 
	 */
	public List<Movie> getFavoriteMovies(int page) throws ResponseException {
		
		ResponseArray response = TMDBAPI.getFavoriteMovies(this, page);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			
			List<Movie> movies = new LinkedList<Movie>();
			Set<JSONObject> data = response.getData();
			
			for (JSONObject json : data) {
				movies.add(new Movie(json));
			}
			
			return movies;
		}
	}
	
	/**
	 * Gets the entire list of favorite movies for an account.
	 * 
	 * @return The entire favorite movies list
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
	public List<Movie> getAllFavoriteMovies() throws ResponseException {
		ResponseArray response = TMDBAPI.getAllFavoriteMovies(this);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			
			List<Movie> movies = new LinkedList<Movie>();
			Set<JSONObject> data = response.getData();
			
			for (JSONObject json : data) {
				movies.add(new Movie(json));
			}
			
			return movies;
		}
	}
	
	/**
	 * Adds a movie to this accounts favorite list.
	 * 
	 * @param movieID The movie ID
	 * @return Whether the operation succeeded or not 
	 */
	public boolean addMovieToFavorites(int movieID) {
		ResponseObject response = TMDBAPI.addMovieToFavorites(this, movieID);
		return response.hasError();
	}
	
	/**
	 * Removes a movie from this accounts favorite list.
	 * 
	 * @param movieID The movie ID
	 * @return Whether the operation succeeded or not 
	 */
	public boolean removeMovieFromFavorites(int movieID) {
		ResponseObject response = TMDBAPI.removeMovieFromFavorites(this, movieID);
		return response.hasError();
	}
	
	//endregion
	
	//region Favorite Lists
	
	/**
	 * Gets the lists that you have created and marked as a favorite.
	 * 
	 * @return The favorite lists list
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
	public List<MovieList> getFavoriteLists() throws ResponseException {
		return getFavoriteLists(1);
	}
	
	/**
	 * Gets the lists that you have created and marked as a favorite.
	 * 
	 * @param page The page number to retrieve
	 * @return The favorite lists list
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
	public List<MovieList> getFavoriteLists(int page) throws ResponseException {
		
		ResponseArray response = TMDBAPI.getFavoriteLists(this, page);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			
			List<MovieList> lists = new LinkedList<MovieList>();
			Set<JSONObject> data = response.getData();
			
			for (JSONObject json : data) {
				lists.add(new MovieList(json));
			}
			
			return lists;
		}
	}
	
	/**
	 * Gets all the lists that you have created and marked as a favorite.
	 * 
	 * @return The entire favorite lists list
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
	public List<MovieList> getAllFavoriteLists() throws ResponseException {
		ResponseArray response = TMDBAPI.getAllFavoriteLists(this);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			
			List<MovieList> lists = new LinkedList<MovieList>();
			Set<JSONObject> data = response.getData();
			
			for (JSONObject json : data) {
				lists.add(new MovieList(json));
			}
			
			return lists;
		}
	}
	
	//endregion
	
	//region Top Rated
	
	/**
	 * Gets the list of rated movies for this account.
	 * 
	 * @return The rated movies list
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
	public List<Movie> getRatedMovies() throws ResponseException {
		return getRatedMovies(1);
	}
	
	/**
	 * Gets the list of rated movies for an account.
	 *  
	 * @param page The page number to retrieve
	 * @return The rated movies list
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
	public List<Movie> getRatedMovies(int page) throws ResponseException {
		
		ResponseArray response = TMDBAPI.getRatedMovies(this, page);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			
			List<Movie> movies = new LinkedList<Movie>();
			Set<JSONObject> data = response.getData();
			
			for (JSONObject json : data) {
				movies.add(new Movie(json));
			}
			
			return movies;
		}
	}
	
	/**
	 * Gets the list of all rated movies for an account.
	 * 
	 * @return The rated movies list
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
	public List<Movie> getAllRatedMovies() throws ResponseException {
		ResponseArray response = TMDBAPI.getAllRatedMovies(this);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			
			List<Movie> movies = new LinkedList<Movie>();
			Set<JSONObject> data = response.getData();
			
			for (JSONObject json : data) {
				movies.add(new Movie(json));
			}
			
			return movies;
		}
	}
	
	//endregion
	
	//region Watch list
	
	/**
	 * Gets the list of movies on this accounts watch list.
	 * 
	 * @return The movies watch list
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
	public List<Movie> getMovieWatchlist() throws ResponseException {
		return getMovieWatchlist(1);
	}
	
	/**
	 * Gets the list of movies on this accounts watch list.
	 * 
	 * @param page The page number to retrieve
	 * @return The movies watch list
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
	public List<Movie> getMovieWatchlist(int page) throws ResponseException {
		
		ResponseArray response = TMDBAPI.getMovieWatchList(this, page);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			
			List<Movie> movies = new LinkedList<Movie>();
			Set<JSONObject> data = response.getData();
			
			for (JSONObject json : data) {
				movies.add(new Movie(json));
			}
			
			return movies;
		}
	}
	
	/**
	 * Gets the list of all movies on this accounts watch list.
	 * 
	 * @return The movies watch list
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
	public List<Movie> getAllMovieWatchlist() throws ResponseException {
		ResponseArray response = TMDBAPI.getAllMovieWatchList(this);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			
			List<Movie> movies = new LinkedList<Movie>();
			Set<JSONObject> data = response.getData();
			
			for (JSONObject json : data) {
				movies.add(new Movie(json));
			}
			
			return movies;
		}
	}
	
	/**
	 * Adds a movie to this accounts watch list.
	 * 
	 * @param movieID The movie ID
	 * @return Whether the operation succeeded or not
	 */
	public boolean addMovieToWatchlist(int movieID) {
		ResponseObject response = TMDBAPI.addMovieToWatchlist(this, movieID);
		return response.hasError();
	}
	
	/**
	 * Removes a movie from this accounts watch list.
	 * 
	 * @param movieID The movie ID
	 * @return Whether the operation succeeded or not
	 */
	public boolean removeMovieToWatchlist(int movieID) {
		ResponseObject response = TMDBAPI.removeMovieFromWatchlist(this, movieID);
		return response.hasError();
	}
	
	//endregion
	
}
