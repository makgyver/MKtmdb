package mk.tmdb.entity;

import net.sf.json.JSONObject;

public abstract class Entity {

	protected String originJson = "";
	
	public Entity() {}
	
	public Entity(JSONObject json) {
		this.originJson = json.toString();
	}
	
	protected boolean parseJSON(JSONObject json) {
		return false;
	}

	public String getOriginJSON() {
		return originJson;
	}

}
