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

public class MovieList extends Entity {

	//region Fields
	
	private String id;
	private int count;
	private int favoritesCount;
	private String posterPath;
	private String name;
	private String type = null;
	private String description;
	private String iso639_1;
	private String creator = null;
	private Set<MovieReduced> movies = Collections.synchronizedSet(new LinkedHashSet<MovieReduced>());
	
	//endregion
	
	public MovieList(JSONObject json) {
		super(json);
		parseJSON(json);
	}
	
	public MovieList(MovieList list) {
		this(list.getOriginJSON());
	}
	
	//region Getters/Setters
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public int getCount() {
		return count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
	
	public int getFavoritesCount() {
		return favoritesCount;
	}
	
	public void setFavoritesCount(int favoritesCount) {
		this.favoritesCount = favoritesCount;
	}
	
	public String getPosterPath() {
		return posterPath;
	}
	
	public void setPosterPath(String posterPath) {
		this.posterPath = posterPath;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public boolean isTypeSet() {
		return type != null;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getIso639_1() {
		return iso639_1;
	}
	
	public void setIso639_1(String iso639_1) {
		this.iso639_1 = iso639_1;
	}
	
	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	public boolean isCreatorSet() {
		return creator != null;
	}

	public Set<MovieReduced> getMovies() {
		return movies;
	}

	public void setMovies(Set<MovieReduced> movies) {
		this.movies.clear();
		this.movies.addAll(movies);
	}
	
	//endregion

	@Override
	protected boolean parseJSON(JSONObject json) {
		
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
	
	// Static methods
	
	public static MovieList getList(String listID) throws ResponseException {
		
		ResponseObject response = TMDbAPI.getList(listID);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			return new MovieList(response.getData());
		}
	}
	
	public static Set<MovieList> searchByName(String name) throws ResponseException {
		return searchByName(name, 1);
	}
	
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
	
}
