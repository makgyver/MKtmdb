package mk.tmdb.entity;

import net.sf.json.JSONObject;

public abstract class Entity {

	protected JSONObject originJson = null;
	
	public Entity() {}
	
	public Entity(JSONObject json) {
		this.originJson = json;
	}
	
	protected boolean parseJSON(JSONObject json) {
		return false;
	}

	public JSONObject getOriginJSON() {
		return originJson;
	}

}
