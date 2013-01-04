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

package mk.tmdb.entity.role;

import mk.tmdb.core.TMDbConstants;
import net.sf.json.JSONObject;

/**
 * Class that represents a cast role in a movie.
 * 
 * @author Mirko Polato
 *
 */
public class TMDbCast extends TMDbRole {

	/**
	 * The character of the actor/actress.
	 */
	private String character;
	
	/**
	 * Creates a new Cast role based on the given JSON object.
	 * 
	 * @param json The origin JSON object
	 */
	public TMDbCast(JSONObject json) {
		super(json);
		parseJSON(json);
	}
	
	/**
	 * Copy constructor.
	 * 
	 * @param role The role to copy
	 */
	public TMDbCast(TMDbRole role) {
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
	private boolean parseJSON(JSONObject json) {
		
		if (json.isNullObject()) return false;
		
		setCharacter(json.getString(TMDbConstants.CHARACTER));
		
		return true;
	}
	
}
