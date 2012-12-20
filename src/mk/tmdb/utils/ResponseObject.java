package mk.tmdb.utils;

import net.sf.json.JSONObject;

/**
 * Class that represents a response simple object.
 * 
 * @author Mirko Polato
 *
 */
public class ResponseObject extends Response {

	/**
	 * The Json object response
	 */
	private JSONObject data;
	
	/**
	 * Creates a new ResponseObject instance based on the given Json object.
	 *  
	 * @param json The Json response
	 */
	public ResponseObject(JSONObject json) {
		super(json);
		
		if (hasError()) setData(null);
		else setData(json);
	}
	
	/**
	 * Initializes the response status to the given one.
	 * 
	 * @param status The response status
	 */
	public ResponseObject(Status status) {
		super(status);
		setData(null);
	}
	
	/**
	 * Set the data to the given Json object.
	 * 
	 * @param json The new data
	 */
	private void setData(JSONObject json) {
		this.data = json;
	}

	/**
	 * Gets the Json object response.
	 * 
	 * @return The data response
	 */
	public JSONObject getData() {
		return data;
	}

}
