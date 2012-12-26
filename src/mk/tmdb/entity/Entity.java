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
	 * Sets the origin JSON object to the given one.
	 * 
	 * @param json The origin JSON object
	 */
	public Entity(JSONObject json) {
		this.originJson = json;
	}
	
	/**
	 * Parses the origin JSON Object.
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
	
	@Override
	public String toString() {
		if (originJson != null)
		{
			return originJson.toString();
		} else {
			return "";
		}
	}
	
	@Override
	public boolean equals(Object object) {
		if (object != null) {
			Entity entity = (Entity) object;
			return originJson.equals((entity).getOriginJSON());
		} else {
			return false;
		}
	}

}
