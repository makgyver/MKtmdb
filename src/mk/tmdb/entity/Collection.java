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

public class Collection extends Entity {

	private String backdropPath;
	private int id;
	private String name;
	private String posterPath;
	private Set<MovieThumbnail> movies = Collections.synchronizedSet(new LinkedHashSet<MovieThumbnail>());
	private Set<Poster> posters = Collections.synchronizedSet(new LinkedHashSet<Poster>());
	private Set<Backdrop> backdrops = Collections.synchronizedSet(new LinkedHashSet<Backdrop>());
	
	public Collection(JSONObject json) {
		super(json);
		parseJSON(json);
	}
	
	public Collection(Collection collection) {
		this(collection.getOriginJSON());
	}

	//region Getters/Setters
	
	public String getBackdropPath() {
		return backdropPath;
	}

	public void setBackdropPath(String backdropPath) {
		this.backdropPath = backdropPath;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPosterPath() {
		return posterPath;
	}

	public void setPosterPath(String posterPath) {
		this.posterPath = posterPath;
	}

	public Set<MovieThumbnail> getMovies() {
		return movies;
	}

	public void setMovies(Set<MovieThumbnail> movies) {
		this.movies = movies;
	}
	
	
	public Set<Poster> getPosters() {
		return posters;
	}

	public void setPosters(Set<Poster> posters) {
		this.posters = posters;
	}

	public Set<Backdrop> getBackdrops() {
		return backdrops;
	}

	public void setBackdrops(Set<Backdrop> backdrops) {
		this.backdrops = backdrops;
	}

	//endregion
	
	@Override
	protected boolean parseJSON(JSONObject json) {
		
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
	
	public static Collection getInformation(int collectionID) throws ResponseException {
		ResponseObject response = TMDbAPI.getCollectionInformation(collectionID);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			return new Collection(response.getData());
		}
	}
	
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
	
	public static Set<Collection> searchByName(String name) throws ResponseException {
		return searchByName(name, 1);
	}
	
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
