package mk.tmdb.utils;

import net.sf.json.JSONObject;

public class ObjectResponse extends Response {

	private JSONObject data;
	
	public ObjectResponse(JSONObject json) {
		super(json);
	}
	
	public JSONObject getData() {
		return data;
	}

}
