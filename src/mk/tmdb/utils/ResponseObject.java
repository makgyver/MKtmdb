package mk.tmdb.utils;

import net.sf.json.JSONObject;

public class ResponseObject extends Response {

	private JSONObject data;
	
	public ResponseObject(JSONObject json) {
		super(json);
		
		if (hasError()) setData(null);
		else setData(json);
	}
	
	public ResponseObject(Status status) {
		super(status);
		setData(null);
	}
	
	private void setData(JSONObject json) {
		this.data = json;
	}

	public JSONObject getData() {
		return data;
	}

}
