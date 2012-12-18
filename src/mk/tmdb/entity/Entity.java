package mk.tmdb.entity;

import net.sf.json.JSONObject;

public abstract class Entity {

	protected String originJson = "";
	
	protected boolean parseJSON(JSONObject json) {
		return false;
	}

	public String getOriginJSON() {
		return originJson;
	}

}
