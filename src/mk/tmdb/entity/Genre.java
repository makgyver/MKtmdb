package mk.tmdb.entity;

import mk.tmdb.core.Constants;
import mk.tmdb.utils.Log;
import net.sf.json.JSONObject;

/**
 * Class that represents a movie genre.
 * 
 * @author Mirko Polato
 *
 */
public class Genre extends Entity {

	/**
	 * The genre ID.
	 */
	private int id;
	
	/**
	 * The genre name.
	 */
	private String name;

	/**
	 * Creates a new Genre based on the origin JSON object.
	 * 
	 * @param json The origin JSON object
	 */
	public Genre(JSONObject json) {
		super(json);
		parseJSON(json);
	}
	
	/**
	 * Copy constructor.
	 * 
	 * @param genre The genre to copy.
	 */
	public Genre(Genre genre) {
		this(genre.getOriginJSON());
	}
	
	/**
	 * Gets the genre ID.
	 * 
	 * @return The genre ID
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the genre ID
	 * 
	 * @param id The new genre ID
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Gets the genre name.
	 * 
	 * @return The genre name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the genre name.
	 * 
	 * @param name The new genre name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Parses the origin JSON object.
	 */
	@Override
	protected boolean parseJSON(JSONObject json) {
		try {
			
			setId(json.getInt(Constants.ID));
			setName(json.getString(Constants.NAME));
			
		} catch (Exception e) {
			Log.print(e);
			return false;
		}
		
		return true;
	}
	
}
