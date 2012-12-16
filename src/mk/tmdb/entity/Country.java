package mk.tmdb.entity;

import mk.tmdb.utils.Log;
import net.sf.json.JSONObject;

public class Country implements IEntity {

	private static final String ISO = "iso_3166_1";
	private static final String NAME = "name";
	
	private String iso3166_1;
	private String name;
	
	public Country(String iso, String name) {
		this.iso3166_1 = iso;
		this.name = name;
	}
	
	public Country(JSONObject json) {
		parseJSON(json);
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
	
	@Override
	public boolean parseJSON(JSONObject json) {
		try {
			
			setIso3166_1(json.getString(ISO));
			setName(json.getString(NAME));
			
		} catch (Exception e) {
			Log.print(e);
			return false;
		}
		
		return true;
	}

}
