package mk.tmdb.entity.role;

import mk.tmdb.core.Constants;
import net.sf.json.JSONObject;

/**
 * Class that represents a cast role in a movie.
 * 
 * @author Mirko Polato
 *
 */
public class Cast extends Role {

	/**
	 * The character of the actor/actress.
	 */
	private String character;
	
	/**
	 * Creates a new Cast role based on the given JSON object.
	 * 
	 * @param json The origin JSON object
	 */
	public Cast(JSONObject json) {
		super(json);
		parseJSON(json);
	}
	
	/**
	 * Copy constructor.
	 * 
	 * @param role The role to copy
	 */
	public Cast(Role role) {
		this(role.getOriginJSON());
	}
	
	/**
	 * Gets the character name.
	 * 
	 * @return The character name
	 */
	public String getCharacter() {
		return character;
	}

	/**
	 * Sets the character name
	 * 
	 * @param character The new character name
	 */
	public void setCharacter(String character) {
		this.character = character;
	}

	/**
	 * Parses the origin JSON object.
	 */
	@Override
	protected boolean parseJSON(JSONObject json) {
		
		setCharacter(json.getString(Constants.CHARACTER));
		
		return true;
	}
	
}
