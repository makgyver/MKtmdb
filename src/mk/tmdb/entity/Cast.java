package mk.tmdb.entity;

import mk.tmdb.core.Constants;
import net.sf.json.JSONObject;

public class Cast extends Role {

	private String character;
	
	public Cast(JSONObject json) {
		super(json);
		parseJSON(json);
	}

	public String getCharacter() {
		return character;
	}

	public void setCharacter(String character) {
		this.character = character;
	}

	@Override
	protected boolean parseJSON(JSONObject json) {
		
		setCharacter(json.getString(Constants.CHARACTER));
		
		return true;
	}
	
}
