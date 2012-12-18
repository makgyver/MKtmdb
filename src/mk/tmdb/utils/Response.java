package mk.tmdb.utils;

import mk.tmdb.core.Constants;
import net.sf.json.JSONObject;

public class Response {

	protected int pages = 0;
	protected int page = 0;
	protected Status status = Status.NONE;
	
	public Response(JSONObject json) {
		if (json.has(Constants.STATUS_CODE)) setStatus(Status.getStatusByCode(json.getInt(Constants.STATUS_CODE)));
	}
	
	public int getPages() {
		return pages;
	}
	
	protected void setPages(int pages) {
		this.pages = pages;
	}
	
	public int getPage() {
		return page;
	}
	
	protected void setPage(int page) {
		this.page = page;
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
