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

import net.sf.json.JSONObject;

/**
 * Abstract base class that represent an entity derived from a JSONObject.
 * 
 * @author Mirko Polato
 *
 */
public abstract class Entity {

	/**
	 * The origin JSON object.
	 */
	protected JSONObject originJson = null;
	
	/**
	 * Default constructor.
	 */
	public Entity() {}
	
	/**
	 * Sets the origin JSON object to the given one.
	 * 
	 * @param json The origin JSON object
	 */
	public Entity(JSONObject json) {
		this.originJson = json;
	}

	/**
	 * Gets the origin JSON object.
	 * 
	 * @return The origin JSON object
	 */
	public JSONObject getOriginJSON() {
		return originJson;
	}
	
	@Override
	public String toString() {
		if (originJson != null)
		{
			return originJson.toString();
		} else {
			return "";
		}
	}
	
	@Override
	public boolean equals(Object object) {
		if (object != null) {
			Entity entity = (Entity) object;
			return originJson.equals((entity).getOriginJSON());
		} else {
			return false;
		}
	}

}
