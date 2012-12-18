package mk.tmdb.entity;

import mk.tmdb.core.Constants;
import net.sf.json.JSONObject;

public class Account extends Entity {

	private int id;
	private String iso639_1;
	private String iso3166_1;
	private String name;
	private boolean adult;	
	private String username;
	
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
	
}
