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

import mk.tmdb.entity.Credit;
import mk.tmdb.entity.image.Profile;
import mk.tmdb.exception.ResponseException;
import mk.tmdb.utils.Log;
import net.sf.json.JSONObject;

/**
 * Class that represents a full featured person.</br>
 * To get a less detailed class see the following classes:
 * <ul>
 * <li>{@link PersonThumbnail}</li>
 * <li>{@link Person}</li>
 * </ul>
 * 
 * @author Mirko Polato
 *
 */
public class PersonFull extends Person {

	/**
	 * The profile images of the person.
	 */
	private Set<Profile> images = Collections.synchronizedSet(new LinkedHashSet<Profile>());
	
	/**
	 * The person credits list.
	 */
	private Set<Credit> credits = Collections.synchronizedSet(new LinkedHashSet<Credit>());
	
	/**
	 * Creates a new instance of PersonFull based on the origin JSON object.
	 * 
	 * @param json The origin JSON object
	 */
	public PersonFull(JSONObject json) {
		super(json);
	}
	
	/**
	 * Creates a new instance of PersonFull based on the origin JSON object and
	 * retrieves all the information.
	 * 
	 * @param json The origin JSON object
	 * @param loadAll Whether the remaining information have to be loaded or not
	 */
	public PersonFull(JSONObject json, boolean loadAll) {
		super(json);
		if (loadAll)
			try {
				getFullInformation();
			} catch (ResponseException e) {
				Log.print(e);
			}
	}
	
	/**
	 * Copy constructor.
	 * 
	 * @param person The person to copy
	 */
	public PersonFull(PersonThumbnail person) {
		this(person.getOriginJSON());
	}
	
	/**
	 * Copy constructor.
	 * 
	 * @param person The person to copy
	 * @param loadAll Whether the remaining information have to be loaded or not
	 */
	public PersonFull(PersonThumbnail person, boolean loadAll) {
		this(person.getOriginJSON());
		if(loadAll)
			try {
				getFullInformation();
			} catch (ResponseException e) {
				Log.print(e);
			}
	}
	
	/**
	 * Gets the person images list.
	 * 
	 * @return The person images list
	 */
	public Set<Profile> getImages() {
		return images;
	}

	/**
	 * Sets the person images list.
	 * 
	 * @param images The new person images list
	 */
	public void setImages(Set<Profile> images) {
		this.images = images;
	}

	/**
	 * Gets the credits list of the person.
	 * 
	 * @return The credits list of the person
	 */
	public Set<Credit> getCredits() {
		return credits;
	}

	/**
	 * Sets the credits list of the person.
	 * 
	 * @param credits The new credits list
	 */
	public void setCredits(Set<Credit> credits) {
		this.credits = credits;
	}

	/**
	 * Gets all the information.
	 * 
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
	private void getFullInformation() throws ResponseException {
		setCredits(getCredits(id));
		setImages(getImages(id));
	}

}
