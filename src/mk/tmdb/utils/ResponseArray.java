package mk.tmdb.utils;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import mk.tmdb.core.Constants;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ResponseArray extends Response {

	private int pages = 0;
	private int page = 0;
	private Set<JSONObject> data = Collections.synchronizedSet(new LinkedHashSet<JSONObject>());
	private int results = 0;
	
	public ResponseArray(JSONObject json) {
		super(json);
		
		if (json.has(Constants.PAGE)) setPage(json.getInt(Constants.PAGE));
		if (json.has(Constants.TOTAL_PAGES)) setPages(json.getInt(Constants.TOTAL_PAGES));
		if (json.has(Constants.TOTAL_RESULTS)) setResults(json.getInt(Constants.TOTAL_RESULTS));
		
		if (!hasError()) {
			if (json.has(Constants.RESULT)) setData(json.getJSONArray(Constants.RESULT));
		}
	}
	
	public ResponseArray(Status status) {
		super(status);
	}
	
	public Set<JSONObject> getData() {
		return data;
	}
	
	private void setData(JSONArray array) {
		for(Object obj : array) {
			data.add((JSONObject) obj);
		}
	}

	public int getResults() {
		return results;
	}
	
	private void setResults(int results) {
		this.results = results;
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
	
}
