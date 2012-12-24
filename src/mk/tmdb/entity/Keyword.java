package mk.tmdb.entity;

import java.util.LinkedHashSet;
import java.util.Set;

import mk.tmdb.core.Constants;
import mk.tmdb.core.TMDbAPI;
import mk.tmdb.entity.movie.MovieReduced;
import mk.tmdb.exception.ResponseException;
import mk.tmdb.utils.Log;
import mk.tmdb.utils.ResponseArray;
import mk.tmdb.utils.ResponseObject;
import net.sf.json.JSONObject;

/**
 * Class that represents a movie plot keyword.
 * 
 * @author Mirko Polato
 *
 */
public class Keyword extends Entity {

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
	public Keyword(JSONObject json) {
		super(json);
		parseJSON(json);
	}
	
	/**
	 * Copy constructor.
	 * 
	 * @param keyword The keyword to copy
	 */
	public Keyword(Keyword keyword) {
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
	@Override
	protected boolean parseJSON(JSONObject json) {
		try {
			
			setId(json.getInt(Constants.ID));
			setValue(json.getString(Constants.NAME));
			
		} catch (Exception e) {
			Log.print(e);
			return false;
		}
		
		return true;
	}
	
	//region Static methods
	
	public static Keyword getInformation(int keywordID) throws ResponseException {
		
		ResponseObject response = TMDbAPI.getKeywordInformation(keywordID);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			return new Keyword(response.getData());
		}
	}
	
	public static Set<MovieReduced> getAssociatedMovies(int keywordID) throws ResponseException {
		return getAssociatedMovies(keywordID, 1);
	}
	
	public static Set<MovieReduced> getAssociatedMovies(int keywordID, int page) throws ResponseException {
		
		ResponseArray response = TMDbAPI.getMoviesByKeyword(keywordID, page);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			
			Set<JSONObject> array = response.getData();
			Set<MovieReduced> movies = new LinkedHashSet<MovieReduced>();
			for(JSONObject json : array) {
				movies.add(new MovieReduced(json));
			}
			
			return movies;
		}
	}
	
	public static Set<MovieReduced> getAllAssociatedMovies(int keywordID) throws ResponseException {
		
		ResponseArray response = TMDbAPI.getAllMoviesByKeyword(keywordID);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			
			Set<JSONObject> array = response.getData();
			Set<MovieReduced> movies = new LinkedHashSet<MovieReduced>();
			for(JSONObject json : array) {
				movies.add(new MovieReduced(json));
			}
			
			return movies;
		}
	}
	
	public static Set<Keyword> searchByName(String name) throws ResponseException {
		return searchByName(name, 1);
	}
	
	public static Set<Keyword> searchByName(String name, int page) throws ResponseException {
		ResponseArray response = TMDbAPI.searchKeywordByName(name, page);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			Set<JSONObject> array = response.getData();
			Set<Keyword> keys = new LinkedHashSet<Keyword>();
			for(JSONObject json : array) {
				keys.add(new Keyword(json));
			}
			
			return keys;
		}
	}
	
	public static Set<Keyword> fullSearchByName(String name) throws ResponseException {
		ResponseArray response = TMDbAPI.fullSearchKeywordByName(name);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			Set<JSONObject> array = response.getData();
			Set<Keyword> keys = new LinkedHashSet<Keyword>();
			for(JSONObject json : array) {
				keys.add(new Keyword(json));
			}
			
			return keys;
		}
	}
	
	//endregion
	
}
