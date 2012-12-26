package mk.tmdb.utils;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import mk.tmdb.core.Constants;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Class that represents a response array.
 * 
 * @author Mirko Polato
 *
 */
public class ResponseArray extends Response {

	/**
	 * The response total number of pages.
	 */
	private int pages = 0;
	
	/**
	 * The page number of this response array (-1 if the response contains more than one page).
	 */
	private int page = 0;
	
	/**
	 * The Json objects list contained in the response.
	 */
	private Set<JSONObject> data = Collections.synchronizedSet(new LinkedHashSet<JSONObject>());
	
	/**
	 * The total number of the retrieved results.
	 */
	private int results = 0;
	
	/**
	 * Creates a new ResponseArray instance based on the given Json object.
	 * 
	 * @param json The Json response
	 */
	public ResponseArray(JSONObject json) {
		super(json);
		
		if (json.has(Constants.PAGE)) setPage(json.getInt(Constants.PAGE));
		if (json.has(Constants.TOTAL_PAGES)) setPages(json.getInt(Constants.TOTAL_PAGES));
		if (json.has(Constants.TOTAL_RESULTS)) setResults(json.getInt(Constants.TOTAL_RESULTS));
		
		if (!hasError()) {
			if (json.has(Constants.RESULT)) setData(json.getJSONArray(Constants.RESULT));
		}
	}
	
	/**
	 * Initializes the response status to the given one.
	 * 
	 * @param status The response status
	 */
	public ResponseArray(Status status) {
		super(status);
	}
	
	/**
	 * Gets the list of retrieved Json object.
	 * 
	 * @return the list of Json object
	 */
	public Set<JSONObject> getData() {
		return data;
	}
	
	/**
	 * Sets the list of the retrieved Json object.
	 * 
	 * @param array The list of Json object
	 */
	private void setData(JSONArray array) {
		for(Object obj : array) {
			data.add((JSONObject) obj);
		}
	}
	
	/**
	 * Adds a Json object to the data.
	 * 
	 * @param json The Json object to add
	 * @return Whether the operation succeeded or not
	 */
	public boolean addData(JSONObject json) {
		return data.add(json);
	}

	/**
	 * Gets the total number of the retrieved results.
	 * 
	 * @return The total number of the retrieved results
	 */
	public int getResults() {
		return results;
	}
	
	/**
	 * Sets the total number of the retrieved results.
	 * 
	 * @param results The new total number of the retrieved results
	 */
	private void setResults(int results) {
		this.results = results;
	}
	
	/**
	 * Gets the total number of the retrieved pages.
	 * 
	 * @return The total number of pages.
	 */
	public int getPages() {
		return pages;
	}
	
	/**
	 * Sets the total number of the retrieved pages.
	 * 
	 * @param pages The new total number of pages
	 */
	protected void setPages(int pages) {
		this.pages = pages;
	}
	
	/**
	 * Gets the page number of this response.
	 * 
	 * @return The page number of this response
	 */
	public int getPage() {
		return page;
	}
	
	/**
	 * Sets the page number of this response.
	 * 
	 * @param page The new page number of this response
	 */
	protected void setPage(int page) {
		this.page = page;
	}
	
}
