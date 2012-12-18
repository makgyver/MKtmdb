package mk.tmdb.utils;

import mk.tmdb.core.Constants;
import net.sf.json.JSONObject;

public class Response {

	protected Status status = Status.NONE;
	
	public Response(JSONObject json) {
		if (json.has(Constants.STATUS_CODE)) setStatus(Status.getStatusByCode(json.getInt(Constants.STATUS_CODE)));
	}
	
	public Response(Status status) {
		this.status = status;
	}
	
	public boolean hasError() {
		return status.getCode() > Status.SUCCESS.getCode();
	}
	
	public Status getStatus() {
		return status;
	}

	protected void setStatus(Status status) {
		this.status = status;
	}
}
