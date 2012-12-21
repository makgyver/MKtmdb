package mk.tmdb.entity;

import mk.tmdb.core.Constants;
import mk.tmdb.utils.Log;
import net.sf.json.JSONObject;

/**
 * Class that represents a movie plot keyword.
 * 
 * @author Mirko Polato
 *
 */
public class Keyword extends Entity {

	/**
	 * The keyword ID.
	 */
	private int id;
	
	/**
	 * The keyword string.
	 */
	private String value;
	
	/**
	 * Creates a new Keyword based on the origin JSON object.
	 * 
	 * @param json The origin JSON object
	 */
	public Keyword(JSONObject json) {
		super(json);
		parseJSON(json);
	}
	
	/**
	 * Copy constructor.
	 * 
	 * @param keyword The keyword to copy
	 */
	public Keyword(Keyword keyword) {
		this(keyword.getOriginJSON());
	}
	
	/**
	 * Gets the keyword ID.
	 * 
	 * @return The keyword ID
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the keyword ID.
	 * 
	 * @param id The new keyword ID
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Gets the keyword value.
	 * 
	 * @return The keyword string
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Sets the keyword value.
	 * 
	 * @param value The new keyword value
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	protected boolean parseJSON(JSONObject json) {
		try {
			
			setId(json.getInt(Constants.ID));
			setValue(json.getString(Constants.NAME));
			
		} catch (Exception e) {
			Log.print(e);
			return false;
		}
		
		return true;
	}
	
}
