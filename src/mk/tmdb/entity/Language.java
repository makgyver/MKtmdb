package mk.tmdb.entity;

import mk.tmdb.utils.Log;
import net.sf.json.JSONObject;

public class Language implements IEntity {
	
	private static final String ISO = "iso_639_1";
	private static final String NAME = "name";
	
	private String iso639_1;
	private String name;
	
	public Language(String iso, String name) {
		this.iso639_1 = iso;
		this.name = name;
	}
	
	public Language(JSONObject json) {
		parseJSON(json);
	}
	
	public String getIso639_1() {
		return iso639_1;
	}

	public void setIso639_1(String iso639_1) {
		this.iso639_1 = iso639_1;
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
			
			setIso639_1(json.getString(ISO));
			setName(json.getString(NAME));
			
		} catch (Exception e) {
			Log.print(e);
			return false;
		}
		
		return true;
	}
}
