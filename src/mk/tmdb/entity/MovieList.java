package mk.tmdb.entity;

import mk.tmdb.core.Constants;
import net.sf.json.JSONObject;

public class MovieList extends Entity {

	private String id;
	private int count;
	private int favoritesCount;
	private String posterPath;
	private String name;
	private String type;
	private String description;
	private String iso639_1;
	
	public MovieList(JSONObject json) {
		super(json);
		parseJSON(json);
	}
	
	public MovieList(MovieList list) {
		this(list.getOriginJSON());
	}
	
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
	
	@Override
	protected boolean parseJSON(JSONObject json) {
		
		setId(json.getString(Constants.ID));
		setDescription(json.getString(Constants.DESCRIPTION));
		setType(json.getString(Constants.LIST_TYPE));
		setName(json.getString(Constants.NAME));
		setFavoritesCount(json.getInt(Constants.FAVORITE_COUNT));
		setCount(json.getInt(Constants.ITEM_COUNT));
		setPosterPath(json.getString(Constants.POSTER_PATH));
		setIso639_1(json.getString(Constants.ISO_6391));
		
		return true;
	}
	
}
