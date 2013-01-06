/*******************************************************************************
 * Copyright (C) 2012-2013  Mirko Polato
 * 
 * This file is part of MKtmdb.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 ******************************************************************************/

package mk.tmdb.entity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import mk.tmdb.core.TMDbConstants;
import mk.tmdb.utils.Log;
import net.sf.json.JSONObject;

/**
 * This class represents a valid request token.
 * 
 * @author Mirko Polato
 *
 */
public class TMDbToken extends TMDbEntity {
	
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
	public TMDbToken(JSONObject json) {
		super(json);
		parseJSON(json);
	}	
	
	/**
	 * Copy constructor.
	 * 
	 * @param token The Token to copy
	 */
	public TMDbToken(TMDbToken token) {
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
		
		if (json.isNullObject()) return false;
		
		setExpirationDate(json.getString(TMDbConstants.EXPIRATION));
		setValue(json.getString(TMDbConstants.TOKEN));
		
		return true;
	}

}
