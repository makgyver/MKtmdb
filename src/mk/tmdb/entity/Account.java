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

public class Account extends Entity {

	private int id;
	private String iso639_1;
	private String iso3166_1;
	private String name;
	private boolean adult;	
	private String username;
	
	private String sessionID;
	
	public Account(JSONObject json) {
		super(json);
		parseJSON(json);
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getIso639_1() {
		return iso639_1;
	}
	
	public void setIso639_1(String iso639_1) {
		this.iso639_1 = iso639_1;
	}
	
	public String getIso3166_1() {
		return iso3166_1;
	}
	
	public void setIso3166_1(String iso3166_1) {
		this.iso3166_1 = iso3166_1;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isAdult() {
		return adult;
	}
	
	public void setAdult(boolean adult) {
		this.adult = adult;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getSessionID() {
		return sessionID;
	}
	
	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}
	
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
	
	public static Account getInfromation(String sessionID) throws ResponseException {
		
		ResponseObject response = TMDBAPI.getAccountInformation(sessionID);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			return new Account(response.getData());
		}
	}
	
	public List<Movie> getFavoriteMovies() throws ResponseException {
		return getFavoriteMovies(1);
	}
	
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
	
	public List<MovieList> getFavoriteLists() throws ResponseException {
		return getFavoriteLists(1);
	}
	
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
}
