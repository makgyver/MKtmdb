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
import mk.tmdb.entity.Entity;
import net.sf.json.JSONObject;

/**
 * Abstract class that represents a role (cast or crew) in a movie.
 * 
 * @author Mirko Polato
 *
 */
public abstract class Role extends Entity {

	/**
	 * The specific job.
	 */
	protected String job;
	
	/**
	 * Initializes the origin JSON object.
	 * 
	 * @param json The origin JSON object
	 */
	public Role(JSONObject json) {
		super(json);
		parseJSON(json);
	}
	
	/**
	 * Copy constructor.
	 * 
	 * @param role The role to copy
	 */
	public Role(Role role) {
		this(role.getOriginJSON());
	}
	
	/**
	 * Gets the job.
	 * 
	 * @return The job.
	 */
	public String getJob() {
		return job;
	}

	/**
	 * Sets the job.
	 * 
	 * @param job The job
	 */
	public void setJob(String job) {
		this.job = job;
	}

	/**
	 * Parses the origin JSON object.
	 */
	private boolean parseJSON(JSONObject json) {
		
		if (json.isNullObject()) return false;
		
		if (json.has(Constants.JOB)) setJob(json.getString(Constants.JOB));
		else setJob(Constants.ACTOR);
		
		return true;
	}
	
}
