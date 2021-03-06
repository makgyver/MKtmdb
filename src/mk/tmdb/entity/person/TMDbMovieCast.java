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

package mk.tmdb.entity.person;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import mk.tmdb.entity.role.TMDbCast;
import net.sf.json.JSONObject;

/**
 * Class that represents a member of a movie cast.
 * 
 * @author Mirko Polato
 *
 */
public class TMDbMovieCast extends TMDbPersonThumbnail {

	/**
	 * The movie ID.
	 */
	private int movieID;
	
	/**
	 * The list of role in the cast.
	 */
	private List<TMDbCast> cast = Collections.synchronizedList(new LinkedList<TMDbCast>());
	
	/**
	 * Creates a new instance of MovieCast based on the origin JSON object.
	 * 
	 * @param json The origin JSON object
	 * @param movieID The movie ID
	 */
	public TMDbMovieCast(JSONObject json, int movieID) {
		super(json);
		this.movieID = movieID;
		parseJSON(json);
	}
	
	/**
	 * Copy constructor.
	 * 
	 * @param mcast The person to copy
	 * @param movieID The movie ID
	 */
	public TMDbMovieCast(TMDbMovieCast mcast, int movieID) {
		this(mcast.getOriginJSON(), movieID);
	}

	/**
	 * Gets the roles list of the person.
	 * 
	 * @return The roles list in the cast of the movie
	 */
	public List<TMDbCast> getCast() {
		return cast;
	}

	/**
	 * Sets the list of roles.
	 * 
	 * @param cast The new list of roles
	 */
	public void setCast(List<TMDbCast> cast) {
		this.cast.clear();
		this.cast.addAll(cast);
	}
	
	/**
	 * Adds a new role in the cast.
	 * 
	 * @param role The new role
	 */
	public void addCastRole(TMDbCast role) {
		cast.add(role);
	}

	/**
	 * Gets the movie ID.
	 * 
	 * @return The movie ID
	 */
	public int getMovieID() {
		return movieID;
	}

	/**
	 * Sets the movie ID.
	 * 
	 * @param id The new movie ID
	 */
	public void setMovieID(int id) {
		this.movieID = id;
	}
	
	/**
	 * Parses the origin JSON object.
	 */
	private boolean parseJSON(JSONObject json) {
		
		if (json.isNullObject()) return false;
		
		addCastRole(new TMDbCast(json));
		
		return true;
	}

}
