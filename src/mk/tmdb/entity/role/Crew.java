package mk.tmdb.entity.role;

import mk.tmdb.core.Constants;
import net.sf.json.JSONObject;

/**
 * Class that represents a crew role in a movie.
 * 
 * @author Mirko Polato
 *
 */
public class Crew extends Role {

	/**
	 * The department of this role.
	 */
	private String department;
	
	/**
	 * Creates a new Crew role based on the given JSON object.
	 * 
	 * @param json The origin JSON object
	 */
	public Crew(JSONObject json) {
		super(json);
		parseJSON(json);
	}
	
	/**
	 * Copy constructor.
	 * 
	 * @param role The role to copy
	 */
	public Crew(Role role) {
		this(role.getOriginJSON());
	}

	/**
	 * Gets the department.
	 * 
	 * @return The department
	 */
	public String getDepartment() {
		return department;
	}

	/**
	 * Sets the department.
	 * 
	 * @param department The new department of this role
	 */
	public void setDepartment(String department) {
		this.department = department;
	}

	/**
	 * Parses the origin JSON object.
	 */
	private boolean parseJSON(JSONObject json) {
		
		if (json.isNullObject()) return false;
		
		setDepartment(json.getString(Constants.DEPARTMENT));
		
		return true;
	}
	
}
