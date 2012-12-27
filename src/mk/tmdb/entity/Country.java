package mk.tmdb.entity;

import mk.tmdb.core.Constants;
import mk.tmdb.utils.Log;
import net.sf.json.JSONObject;

/**
 * Class that represents a country.
 * 
 * @author Mirko Polato
 *
 */
public class Country extends Entity {
	
	/**
	 * Codes for the names of countries.
	 */
	private String iso3166_1;
	
	/**
	 * The name of the country.
	 */
	private String name = null;
	
	/**
	 * Creates a new instance of Country based on the origin JSON object.
	 * 
	 * @param json The origin JSON object
	 */
	public Country(JSONObject json) {
		super(json);
		parseJSON(json);
	}
	
	/**
	 * Copy constructor.
	 * 
	 * @param country The country to copy
	 */
	public Country(Country country) {
		this(country.getOriginJSON());
	}
	
	//region Getters/Setters
	
	/**
	 * Gets the account ISO 3166-1.
	 * 
	 * @return The account ISO 3166-1
	 */
	public String getIso3166_1() {
		return iso3166_1;
	}
	
	/**
	 * Sets the account ISO 3166-1.
	 * 
	 * @param iso3166_1 The new account ISO 3166-1
	 */
	public void setIso3166_1(String iso3166_1) {
		this.iso3166_1 = iso3166_1;
	}

	/**
	 * Gets the name of the country.
	 * 
	 * @return The name of the country
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the country.
	 * 
	 * @param name the new country name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Checks if the name of the country is set or not.
	 * 
	 * @return Whether the name of the country is set or not.
	 */
	public boolean isNameSet() {
		return name != null;
	}
	
	//endregion
	
	/**
	 * Parses the origin JSON object.
	 */
	private boolean parseJSON(JSONObject json) {

		if (json.isNullObject()) return false;
		
		try {
			setIso3166_1(json.getString(Constants.ISO_31661));
			if (json.has(Constants.NAME)) setName(json.getString(Constants.NAME));
			
		} catch (Exception e) {
			Log.print(e);
			return false;
		}
		
		return true;
	}

}
