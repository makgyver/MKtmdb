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

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import mk.tmdb.core.TMDbConstants;
import mk.tmdb.utils.Log;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Class that represents a person with limited features.</br>
 * Person class has more features than PersonThumbnail class.</br>
 * To get a more detailed class see {@link TMDbPersonFull}.
 * 
 * @author Mirko Polato
 *
 */
public class TMDbPerson extends TMDbPersonThumbnail {

	//region Fields
	
	/**
	 * The aliases (Also-Known-As) of the person
	 */
	private Set<String> aka = Collections.synchronizedSet(new LinkedHashSet<String>());
	
	/**
	 * The person biography.
	 */
	private String bio = null;
	
	/**
	 * The birthday of the person.
	 */
	private Date birthday = null;
	
	/**
	 * The eventaul deathday of the person.
	 */
	private Date deathday = null;
	
	/**
	 * The person home page.
	 */
	private URL homepage = null;
	
	/**
	 * The place of birth of the person.
	 */
	private String placeOfBirth = null;
	
	//endregion
	
	/**
	 * Creates a new instance of Person based on the origin JSON object.
	 * 
	 * @param json The origin JSON object
	 */
	public TMDbPerson(JSONObject json) {
		super(json);
		parseJSON(json);
	}
	
	/**
	 * Copy constructor.
	 * 
	 * @param person The person to copy
	 */
	public TMDbPerson(TMDbPersonThumbnail person) {
		this(person.getOriginJSON());
	}
	
	//region Getters/Setters
	
	/**
	 * Gets the aliases of the person.
	 * 
	 * @return The aliases of the person.
	 */
	public Set<String> getAka() {
		return aka;
	}
	
	/**
	 * Sets the aliases of the person.
	 * 
	 * @param aka The new aliases list of the person.
	 */
	public void setAka(Set<String> aka) {
		this.aka = aka;
	}
	
	/**
	 * Gets the person biography.
	 * 
	 * @return the person biography
	 */
	public String getBiography() {
		return bio;
	}
	
	/**
	 * Sets the person biography.
	 * 
	 * @param bio The new person biography
	 */
	public void setBiography(String bio) {
		this.bio = bio;
	}
	
	/**
	 * Checks if the biography is set.
	 * 
	 * @return Whether the biography is set or not
	 */
	public boolean isBiographySet() {
		return bio != null;
	}
	
	/**
	 * Gets the birthday of the person.
	 * 
	 * @return The birthday of the person
	 */
	public Date getBirthday() {
		return birthday;
	}
	
	/**
	 * Sets the birthday of the person.
	 * 
	 * @param birthday The new birthday of the person
	 */
	public void setBirthday(String birthday) {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			this.birthday = (Date)formatter.parse(birthday);
		} catch (ParseException e) {
			Log.print(e);
		}
	}
	
	/**
	 * Checks if the birthday is set.
	 * 
	 * @return Whether the birthday is set or not.
	 */
	public boolean isBirthdaySet() {
		return birthday != null;
	}
	
	/**
	 * Gets the eventual deathday of the person.
	 * 
	 * @return The deathday of the person
	 */
	public Date getDeathday() {
		return deathday;
	}
	
	/**
	 * Sets the deathday of the person.
	 * 
	 * @param deathday The deathday of the person
	 */
	public void setDeathday(String deathday) {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			this.deathday = (Date)formatter.parse(deathday);
		} catch (ParseException e) {
			Log.print(e);
		}
	}
	
	/**
	 * Checks if the deathday is set.
	 * 
	 * @return Whether the deathday is set or not.
	 */
	public boolean isDeathdaySet() {
		return deathday != null;
	}
	
	/**
	 * Gets the person home page.
	 * 
	 * @return The person home page
	 */
	public URL getHomepage() {
		return homepage;
	}
	
	/**
	 * Sets the person home page.
	 * 
	 * @param homepage The new person home page
	 */
	public void setHomepage(URL homepage) {
		this.homepage = homepage;
	}
	
	/**
	 * Gets the place of birth.
	 * 
	 * @return The place of birth
	 */
	public String getPlaceOfBirth() {
		return placeOfBirth;
	}
	
	/**
	 * Sets the place of birth.
	 * 
	 * @param placeOfBirth The new place of birth
	 */
	public void setPlaceOfBirth(String placeOfBirth) {
		this.placeOfBirth = placeOfBirth;
	}

	/**
	 * Checks if the place of birth is set.
	 * 
	 * @return Whether the place of birth is set or not
	 */
	public boolean isPlaceOfBirthSet() {
		return placeOfBirth != null;
	}
	
	//endregion
	
	/**
	 * Parses the origin JSON object.
	 */
	private boolean parseJSON(JSONObject json) {
		
		if (json.isNullObject()) return false;
		
		if (json.has(TMDbConstants.BIO)) setBiography(json.getString(TMDbConstants.BIO));
		if (json.has(TMDbConstants.BIRTHDAY)) setBirthday(json.getString(TMDbConstants.BIRTHDAY));
		if (json.has(TMDbConstants.DEATHDAY)) setDeathday(json.getString(TMDbConstants.DEATHDAY));
		if (json.has(TMDbConstants.PLACE_OF_BIRTH)) setPlaceOfBirth(json.getString(TMDbConstants.PLACE_OF_BIRTH));
		
		
		if (json.has(TMDbConstants.AKA)) {
			JSONArray akas = json.getJSONArray(TMDbConstants.AKA); 
			for (Object obj : akas) {
			    aka.add((String) obj);
			}
		}
		
		if (json.has(TMDbConstants.HOMEPAGE)) { 
			try {
				setHomepage(new URL(json.getString(TMDbConstants.HOMEPAGE)));
			} catch (MalformedURLException e) {
				Log.print(e);
			}
		}
		
		return true;
	}
}
