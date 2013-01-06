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

import mk.tmdb.entity.TMDbCredit;
import mk.tmdb.entity.image.TMDbProfile;
import mk.tmdb.exception.TMDbResponseException;
import mk.tmdb.utils.Log;
import net.sf.json.JSONObject;

/**
 * Class that represents a full featured person.</br>
 * To get a less detailed class see the following classes:
 * <ul>
 * <li>{@link TMDbPersonThumbnail}</li>
 * <li>{@link TMDbPerson}</li>
 * </ul>
 * 
 * @author Mirko Polato
 *
 */
public class TMDbPersonFull extends TMDbPerson {

	/**
	 * The profile images of the person.
	 */
	private Set<TMDbProfile> images = Collections.synchronizedSet(new LinkedHashSet<TMDbProfile>());
	
	/**
	 * The person credits list.
	 */
	private Set<TMDbCredit> credits = Collections.synchronizedSet(new LinkedHashSet<TMDbCredit>());
	
	/**
	 * Creates a new instance of PersonFull based on the origin JSON object.
	 * 
	 * @param json The origin JSON object
	 */
	public TMDbPersonFull(JSONObject json) {
		super(json);
	}
	
	/**
	 * Creates a new instance of PersonFull based on the origin JSON object and
	 * retrieves all the information.
	 * 
	 * @param json The origin JSON object
	 * @param loadAll Whether the remaining information have to be loaded or not
	 */
	public TMDbPersonFull(JSONObject json, boolean loadAll) {
		super(json);
		if (loadAll)
			try {
				getFullInformation();
			} catch (TMDbResponseException e) {
				Log.print(e);
			}
	}
	
	/**
	 * Copy constructor.
	 * 
	 * @param person The person to copy
	 */
	public TMDbPersonFull(TMDbPersonThumbnail person) {
		this(person.getOriginJSON());
	}
	
	/**
	 * Copy constructor.
	 * 
	 * @param person The person to copy
	 * @param loadAll Whether the remaining information have to be loaded or not
	 */
	public TMDbPersonFull(TMDbPersonThumbnail person, boolean loadAll) {
		this(person.getOriginJSON());
		if(loadAll)
			try {
				getFullInformation();
			} catch (TMDbResponseException e) {
				Log.print(e);
			}
	}
	
	/**
	 * Gets the person images list.
	 * 
	 * @return The person images list
	 */
	public Set<TMDbProfile> getImages() {
		return images;
	}

	/**
	 * Sets the person images list.
	 * 
	 * @param images The new person images list
	 */
	public void setImages(Set<TMDbProfile> images) {
		this.images = images;
	}

	/**
	 * Gets the credits list of the person.
	 * 
	 * @return The credits list of the person
	 */
	public Set<TMDbCredit> getCredits() {
		return credits;
	}

	/**
	 * Sets the credits list of the person.
	 * 
	 * @param credits The new credits list
	 */
	public void setCredits(Set<TMDbCredit> credits) {
		this.credits = credits;
	}

	/**
	 * Gets all the information.
	 * 
	 * @throws TMDbResponseException Throws whether the server response is not a success.
	 */
	private void getFullInformation() throws TMDbResponseException {
		setCredits(getCredits(id));
		setImages(getImages(id));
	}

}
