package mk.tmdb.entity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import mk.tmdb.core.Constants;
import mk.tmdb.utils.Log;
import net.sf.json.JSONObject;

/**
 * This class represents a valid request token.
 * 
 * @author Mirko Polato
 *
 */
public class Token extends Entity {
	
	/**
	 * The expiration date.
	 */
	private Date expirationDate;
	
	/**
	 * The token string.
	 */
	private String value;
	
	/**
	 * Creates a new Token based on the origin JSON object.
	 * 
	 * @param json The origin JSON object
	 */
	public Token(JSONObject json) {
		super(json);
		parseJSON(json);
	}	
	
	/**
	 * Copy constructor.
	 * 
	 * @param token The Token to copy
	 */
	public Token(Token token) {
		this(token.getOriginJSON());
	}
	
	//region Getters/Setters
	
	/**
	 * Gets the expiration date.
	 * 
	 * @return The expiration date
	 */
	public Date getExpirationDate() {
		return expirationDate;
	}

	/**
	 * Sets the expiration date.
	 * 
	 * @param exp the new expiration date
	 */
	private void setExpirationDate(String exp) {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
		try {
			this.expirationDate = (Date)formatter.parse(exp);
		} catch (ParseException e) {
			Log.print(e);
		}
	}

	/**
	 * Gets the token string.
	 * 
	 * @return The token string
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Sets the token string.
	 * 
	 * @param value The new token value
	 */
	private void setValue(String value) {
		this.value = value;
	}

	//endregion
	
	/**
	 * Parses the origin JSON object.
	 */
	private boolean parseJSON(JSONObject json) {
		
		setExpirationDate(json.getString(Constants.EXPIRATION));
		setValue(json.getString(Constants.TOKEN));
		
		return true;
	}

}
