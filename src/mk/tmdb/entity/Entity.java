package mk.tmdb.entity;

import net.sf.json.JSONObject;

/**
 * Abstract base class that represent an entity derived from a JSONObject.
 * 
 * @author Mirko Polato
 *
 */
public abstract class Entity {

	/**
	 * The origin JSON object.
	 */
	protected JSONObject originJson = null;
	
	/**
	 * Default constructor.
	 */
	public Entity() {}
	
	/**
	 * Sets the Json object to the given one.
	 * 
	 * @param json The origin JSON object
	 */
	public Entity(JSONObject json) {
		this.originJson = json;
	}
	
	/**
	 * Parses the JSON Object.
	 * 
	 * @param json
	 * @return
	 */
	protected boolean parseJSON(JSONObject json) {
		return false;
	}

	/**
	 * Gets the origin JSON object.
	 * 
	 * @return The origin JSON object
	 */
	public JSONObject getOriginJSON() {
		return originJson;
	}

}
