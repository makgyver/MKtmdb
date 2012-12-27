package mk.tmdb.entity;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import mk.tmdb.core.Constants;
import mk.tmdb.core.TMDbAPI;
import mk.tmdb.entity.image.Backdrop;
import mk.tmdb.entity.image.Poster;
import mk.tmdb.entity.movie.MovieThumbnail;
import mk.tmdb.exception.ResponseException;
import mk.tmdb.utils.ResponseArray;
import mk.tmdb.utils.ResponseObject;

/**
 * Class that represents a movie collection. 
 * 
 * @author Mirko Polato
 *
 */
public class Collection extends Entity {

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
	private Set<MovieThumbnail> movies = Collections.synchronizedSet(new LinkedHashSet<MovieThumbnail>());
	
	/**
	 * The posters list of this collection.
	 */
	private Set<Poster> posters = Collections.synchronizedSet(new LinkedHashSet<Poster>());
	
	/**
	 * The backdrops list of this collection.
	 */
	private Set<Backdrop> backdrops = Collections.synchronizedSet(new LinkedHashSet<Backdrop>());
	
	//endregion
	
	/**
	 * Creates a new movie Collection based on the origin JSON object.
	 * 
	 * @param json The origin JSON object
	 */
	public Collection(JSONObject json) {
		super(json);
		parseJSON(json);
	}
	
	/**
	 * Copy constructor.
	 * 
	 * @param collection The collection to copy
	 */
	public Collection(Collection collection) {
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
	public Set<MovieThumbnail> getMovies() {
		return movies;
	}

	/**
	 * Sets the movies list belong to this collection.
	 * 
	 * @param movies The new movies list belong to this collection
	 */
	public void setMovies(Set<MovieThumbnail> movies) {
		this.movies = movies;
	}
	
	/**
	 * Gets the posters of the collection.
	 * 
	 * @return The posters of the collection
	 */
	public Set<Poster> getPosters() {
		return posters;
	}

	/**
	 * Sets the posters of the collection.
	 * 
	 * @param posters The new posters of the collection
	 */
	public void setPosters(Set<Poster> posters) {
		this.posters = posters;
	}

	/**
	 * Gets the backdrops of the collection.
	 * 
	 * @return The backdrops of the collection
	 */
	public Set<Backdrop> getBackdrops() {
		return backdrops;
	}

	/**
	 * Sets the backdrops of the collection.
	 * 
	 * @param backdrops The new backdrops of the collection
	 */
	public void setBackdrops(Set<Backdrop> backdrops) {
		this.backdrops = backdrops;
	}

	//endregion
	
	/**
	 * Parses the origin JSON object.
	 */
	private boolean parseJSON(JSONObject json) {
		
		setPosterPath(json.getString(Constants.POSTER_PATH));
		setBackdropPath(json.getString(Constants.POSTER_PATH));
		setName(json.getString(Constants.NAME));
		setId(json.getInt(Constants.ID));
		
		if (json.has(Constants.PARTS)) {
			JSONArray array = json.getJSONArray(Constants.PARTS);
			for(Object obj : array) {
				movies.add(new MovieThumbnail((JSONObject) obj));
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
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
	public static Collection getInformation(int collectionID) throws ResponseException {
		ResponseObject response = TMDbAPI.getCollectionInformation(collectionID);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			return new Collection(response.getData());
		}
	}
	
	/**
	 * Retrieves from the web the posters of the collection.
	 * 
	 * @param collectionID The collection ID
	 * @return The posters of the collection
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
	public static Set<Poster> getPosters(int collectionID) throws ResponseException {
		ResponseObject response = TMDbAPI.getCollectionImages(collectionID);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			JSONArray array = response.getData().getJSONArray(Constants.POSTERS);
			Set<Poster> images = new LinkedHashSet<Poster>(); 
			for(Object obj : array) {
				images.add(new Poster((JSONObject) obj));
			}
			return images;
		}
	}
	
	/**
	 * Retrieves from the web the backdrops of the collection.
	 * 
	 * @param collectionID The collection ID
	 * @return The backdrops of the collection
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
	public static Set<Backdrop> getBackrops(int collectionID) throws ResponseException {
		ResponseObject response = TMDbAPI.getCollectionImages(collectionID);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			JSONArray array = response.getData().getJSONArray(Constants.BACKDROPS);
			Set<Backdrop> images = new LinkedHashSet<Backdrop>(); 
			for(Object obj : array) {
				images.add(new Backdrop((JSONObject) obj));
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
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
	public static Set<Collection> searchByName(String name) throws ResponseException {
		return searchByName(name, 1);
	}
	
	/**
	 * Searches for the collections by name.
	 * 
	 * @param name The collection name
	 * @param page The page number to retrieve
	 * @return The list of collection that match with the given name
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
	public static Set<Collection> searchByName(String name, int page) throws ResponseException {
		
		ResponseArray response = TMDbAPI.searchCollectionByName(name, page);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			Set<JSONObject> array = response.getData();
			Set<Collection> collections = new LinkedHashSet<Collection>();
			for(JSONObject json : array) {
				collections.add(new Collection(json));
			}
			
			return collections;
		}
	}

	/**
	 * Searches for the collections by name. Gets all the results.
	 * 
	 * @param name The collection name
	 * @return The list of collection that match with the given name
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
	public static Set<Collection> fullSearchByName(String name) throws ResponseException {
		
		ResponseArray response = TMDbAPI.fullSearchCollectionByName(name);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			Set<JSONObject> array = response.getData();
			Set<Collection> collections = new LinkedHashSet<Collection>();
			for(JSONObject json : array) {
				collections.add(new Collection(json));
			}
			
			return collections;
		}
	}

	//endregion
	
}
