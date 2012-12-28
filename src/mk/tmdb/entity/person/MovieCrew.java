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
import java.util.LinkedHashSet;
import java.util.Set;

import mk.tmdb.entity.role.Crew;
import net.sf.json.JSONObject;

/**
 * Class that represents a member of a movie crew.
 * 
 * @author Mirko Polato
 *
 */
public class MovieCrew extends PersonThumbnail {

	/**
	 * The movie ID.
	 */
	private int movieID;
	
	/**
	 * The list of role in the crew.
	 */
	private Set<Crew> crew = Collections.synchronizedSet(new LinkedHashSet<Crew>());
	
	/**
	 * Creates a new instance of MovieCrew based on the origin JSON object.
	 * 
	 * @param json The origin JSON object
	 * @param movieID The movie ID
	 */
	public MovieCrew(JSONObject json, int movieID) {
		super(json);
		this.movieID = movieID;
		parseJSON(json);
	}
	
	/**
	 * Copy constructor.
	 * 
	 * @param mcrew The person to copy
	 * @param movieID The movie ID
	 */
	public MovieCrew(MovieCrew mcrew, int movieID) {
		this(mcrew.getOriginJSON(), movieID);
	}

	/**
	 * Gets the roles list of the person.
	 * 
	 * @return The roles list in the crew of the movie
	 */
	public Set<Crew> getCrew() {
		return crew;
	}

	/**
	 * Sets the list of roles.
	 * 
	 * @param crew The new list of roles
	 */
	public void setCast(Set<Crew> crew) {
		this.crew.clear();
		this.crew.addAll(crew);
	}
	
	/**
	 * Adds a new role in the crew.
	 * 
	 * @param role The new role
	 */
	public void addCrewRole(Crew role) {
		crew.add(role);
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
		
		addCrewRole(new Crew(json));
		
		return true;
	}
}
