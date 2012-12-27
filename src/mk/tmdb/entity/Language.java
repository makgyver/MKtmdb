package mk.tmdb.entity;

import mk.tmdb.core.Constants;
import mk.tmdb.utils.Log;
import net.sf.json.JSONObject;

/**
 * Class that represents a language.
 * 
 * @author Mirko Polato
 *
 */
public class Language extends Entity {
	
	/**
	 * Codes for the representation of names of languages.
	 */
	private String iso639_1;
	
	/**
	 * The language name
	 */
	private String name;
	
	/**
	 * The language english name
	 */
	private String englishName = "";
	
	/**
	 * Creates a new instance of Language based on the origin JSON object.
	 * 
	 * @param json The origin JSON object
	 */
	public Language(JSONObject json) {
		super(json);
		parseJSON(json);
	}
	
	/**
	 * Copy constructor.
	 * 
	 * @param lang The language to copy
	 */
	public Language(Language lang) {
		this(lang.getOriginJSON());
	}
	
	//region Getters/Setters
	
	/**
	 * Gets the account ISO 639-1.
	 * 
	 * @return The account ISO 639-1
	 */
	public String getIso639_1() {
		return iso639_1;
	}
	
	/**
	 * Sets the account ISO 639-1.
	 * 
	 * @param iso639_1 The new account ISO 639-1
	 */
	public void setIso639_1(String iso639_1) {
		this.iso639_1 = iso639_1;
	}

	/**
	 * Gets the language name.
	 * 
	 * @return The language name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the language name.
	 * 
	 * @param name the new language name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the language english name.
	 * 
	 * @return The language english name
	 */
	public String getEnglishName() {
		return englishName;
	}

	/**
	 * Sets the language english name.
	 * 
	 * @param englishName The new language english name
	 */
	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}
	
	//endregion
	
	/**
	 * Parses the origin JSON object.
	 */
	private boolean parseJSON(JSONObject json) {

		if (json.isNullObject()) return false;
		
		try {
			setIso639_1(json.getString(Constants.ISO_6391));
			setName(json.getString(Constants.NAME));
			
			if (json.has(Constants.ENGLISH_NAME)) setEnglishName(json.getString(Constants.ENGLISH_NAME));
			
		} catch (Exception e) {
			Log.print(e);
			return false;
		}
		
		return true;
	}
	
}
