package mk.tmdb.utils;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import mk.tmdb.core.Constants;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ArrayResponse extends Response {

	private Set<JSONObject> data = Collections.synchronizedSet(new LinkedHashSet<JSONObject>());
	private int results;
	
	public ArrayResponse(JSONObject json) {
		super(json);
		
		if (json.has(Constants.PAGE)) setPage(json.getInt(Constants.PAGE));
		if (json.has(Constants.TOTAL_PAGES)) setPages(json.getInt(Constants.TOTAL_PAGES));
		if (json.has(Constants.TOTAL_RESULTS)) setResults(json.getInt(Constants.TOTAL_RESULTS));
		if (json.has(Constants.RESULT)) setData(json.getJSONArray(Constants.RESULT));
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
	
}
