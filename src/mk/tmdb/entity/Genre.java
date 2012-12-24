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
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Class that represents a movie genre.
 * 
 * @author Mirko Polato
 *
 */
public class Genre extends Entity {

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
	public Genre(JSONObject json) {
		super(json);
		parseJSON(json);
	}
	
	/**
	 * Copy constructor.
	 * 
	 * @param genre The genre to copy.
	 */
	public Genre(Genre genre) {
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
	@Override
	protected boolean parseJSON(JSONObject json) {
		try {
			
			setId(json.getInt(Constants.ID));
			setName(json.getString(Constants.NAME));
			
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
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
	public static Set<Genre> getList() throws ResponseException {
		
		ResponseObject response = TMDbAPI.getGenresList();
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			Set<Genre> genres = new LinkedHashSet<Genre>();
			JSONArray array = response.getData().getJSONArray(Constants.GENRES);
			for(Object json : array) {
				genres.add(new Genre((JSONObject) json));
			}
			
			return genres;
		}
	}
	
	public static Set<MovieReduced> getAssociatedMovies(int genreID) throws ResponseException {
		return getAssociatedMovies(genreID, 1);
	}
	
	public static Set<MovieReduced> getAssociatedMovies(int genreID, int page) throws ResponseException {
		
		ResponseArray response = TMDbAPI.getMoviesByGenre(genreID, page);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			Set<MovieReduced> movies = new LinkedHashSet<MovieReduced>();
			Set<JSONObject> array = response.getData();
			for(JSONObject json : array) {
				movies.add(new MovieReduced(json));
			}
			
			return movies;
		}
	}
	
	public static Set<MovieReduced> getAllAssociatedMovies(int genreID) throws ResponseException {
		
		ResponseArray response = TMDbAPI.getAllMoviesByGenre(genreID);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			Set<MovieReduced> movies = new LinkedHashSet<MovieReduced>();
			Set<JSONObject> array = response.getData();
			for(JSONObject json : array) {
				movies.add(new MovieReduced(json));
			}
			
			return movies;
		}
	}
	
	//endregion
	
}
