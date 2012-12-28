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
