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

import mk.tmdb.entity.movie.MovieThumbnail;
import mk.tmdb.entity.role.Cast;
import mk.tmdb.entity.role.Crew;
import mk.tmdb.entity.role.Role;
import net.sf.json.JSONObject;

/**
 * Class that represents a person's credit.
 * 
 * @author Mirko Polato
 *
 */
public class Credit extends Entity {

	/**
	 * Enumeration that indicates the role's type. 
	 * 
	 * @author Mirko Polato
	 *
	 */
	public enum Type {
		CAST,
		CREW;
	}
	
	/**
	 * The movie of this credit.
	 */
	private MovieThumbnail movie;
	
	/**
	 * The role in the movie.
	 */
	private Role role;
	
	/**
	 * The type of the role.
	 */
	private Type type;
	
	/**
	 * Creates a new instance of Credit based on the origin JSON object.
	 * 
	 * @param json The origin JSON object
	 * @param type The type of the credit
	 */
	public Credit(JSONObject json, Type type) {
		super(json);
		this.type = type;
		parseJSON(json);
	}
	
	/**
	 * Copy constructor.
	 * 
	 * @param credit The credit to copy
	 */
	public Credit(Credit credit) {
		this(credit.getOriginJSON(), credit.getType());
	}
	
	//region Getters/Setters
	
	/**
	 * Gets the movie of this credit.
	 * 
	 * @return The movie of this credit
	 */
	public MovieThumbnail getMovie() {
		return movie;
	}

	/**
	 * Sets the movie of this credit.
	 * 
	 * @param movie The new movie of this credit.
	 */
	public void setMovie(MovieThumbnail movie) {
		this.movie = movie;
	}

	/**
	 * Gets the role in the movie.
	 * 
	 * @return The role in the movie
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * Sets the role in the movie
	 * 
	 * @param role The new role in the movie
	 */
	public void setRole(Role role) {
		this.role = role;
	}

	/**
	 * Gets the type of the role.
	 * 
	 * @return The type of the role
	 */
	public Type getType() {
		return type;
	}
	
	/**
	 * Sets the type of the role.
	 * 
	 * @param type The type of the role
	 */
	public void setType(Type type) {
		this.type = type;
	}
	
	//endregion

	/**
	 * Parses the origin JSON object.
	 */
	private boolean parseJSON(JSONObject json) {
		
		if (json.isNullObject()) return false;
		
		if (type.ordinal() == Type.CAST.ordinal()) setRole(new Cast(json));
		else setRole(new Crew(json));
		
		return true;
	}
	
}
