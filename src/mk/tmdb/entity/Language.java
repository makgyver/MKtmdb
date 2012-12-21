package mk.tmdb.entity;

import mk.tmdb.core.Constants;
import mk.tmdb.utils.Log;
import net.sf.json.JSONObject;

public class Language extends Entity {
	
	private String iso639_1;
	private String name;
	private String englishName = "";
	
	public Language(JSONObject json) {
		super(json);
		parseJSON(json);
	}
	
	public Language(Language lang) {
		this(lang.getOriginJSON());
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

	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}
	
	@Override
	protected boolean parseJSON(JSONObject json) {
		try {
			
			setIso639_1(json.getString(Constants.ISO_6391));
			setName(json.getString(Constants.NAME));
			
			if (json.has(Constants.ENGLISH_NAME)) setEnglishName(json.getString(Constants.ENGLISH_NAME));
			
		} catch (Exception e) {
			Log.print(e);
			return false;
		}
		
		return true;
	}
	
}
