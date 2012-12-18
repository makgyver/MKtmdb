package mk.tmdb.entity;

import mk.tmdb.core.Constants;
import mk.tmdb.utils.Log;
import net.sf.json.JSONObject;

public class Country extends Entity {
	
	private String iso3166_1;
	private String name;
	
	public Country(String iso, String name) {
		this.iso3166_1 = iso;
		this.name = name;
	}
	
	public Country(JSONObject json) {
		this.originJson = json.toString();
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
	protected boolean parseJSON(JSONObject json) {
		try {
			
			setIso3166_1(json.getString(Constants.ISO_31661));
			setName(json.getString(Constants.NAME));
			
		} catch (Exception e) {
			Log.print(e);
			return false;
		}
		
		return true;
	}

}
