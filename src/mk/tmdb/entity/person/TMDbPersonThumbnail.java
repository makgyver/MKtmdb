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

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import mk.tmdb.core.TMDbConstants;
import mk.tmdb.core.TMDbAPI;
import mk.tmdb.entity.TMDbCredit;
import mk.tmdb.entity.TMDbEntity;
import mk.tmdb.entity.TMDbCredit.Type;
import mk.tmdb.entity.image.TMDbProfile;
import mk.tmdb.exception.ResponseException;
import mk.tmdb.utils.TMDbResponseArray;
import mk.tmdb.utils.TMDbResponseObject;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Class that represents a person with limited features.</br>
 * To get a more detailed class see the following classes:
 * <ul>
 * <li>{@link TMDbPerson}</li>
 * <li>{@link TMDbPersonFull}</li>
 * </ul>
 * 
 * @author Mirko Polato
 *
 */
public class TMDbPersonThumbnail extends TMDbEntity {

	//region Fields
	
	/**
	 * The person ID.
	 */
	protected Integer id;
	
	/**
	 * The person name.
	 */
	protected String name;
	
	/**
	 * The person profile path.
	 */
	protected String profilePath;
	
	/**
	 * Whether the person is in movies for adults only.
	 */
	protected Boolean adult = null;
	
	//endregion
	
	/**
	 * Creates a new instance of PersonThumbnail based on the origin JSON object.
	 * 
	 * @param json The origin JSON object
	 */
	public TMDbPersonThumbnail(JSONObject json) {
		super(json);
		parseJSON(json);
	}
	
	/**
	 * Copy constructor.
	 * 
	 * @param person The person to copy
	 */
	public TMDbPersonThumbnail(TMDbPersonThumbnail person) {
		this(person.getOriginJSON());
	}
	
	//region Getters/Setters
	
	/**
	 * Gets the person ID.
	 * 
	 * @return The person ID
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Sets the person ID.
	 * 
	 * @param id The new person ID
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Gets the person name.
	 * 
	 * @return The person name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the person name.
	 * 
	 * @param name The new person name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Gets the perofile path.
	 * 
	 * @return The profile path
	 */
	public String getProfilePath() {
		return profilePath;
	}
	
	/**
	 * Sets the profile path.
	 * 
	 * @param profilePath The new profile path
	 */
	public void setProfilePath(String profilePath) {
		this.profilePath = profilePath;
	}
	
	/**
	 * Gets whether the person is in movies for adults only or not
	 * 
	 * @return Whether the person is in movies for adults only or not
	 */
	public boolean isAdult() {
		return adult;
	}
	
	/**
	 * Sets if the person is in movies for adults only
	 * 
	 * @param adult Whether the person is in movies for adults only or not
	 */
	public void setAdult(boolean adult) {
		this.adult = adult;
	}
	
	/**
	 * Checks if adult property is set.
	 * 
	 * @return Whether adult property is set or not.
	 */
	public boolean isAdultSet() {
		return adult != null;
	}
	
	//endregion
	
	/**
	 * Parses the origin JSON object.
	 */
	private boolean parseJSON(JSONObject json) {
		
		if (json.isNullObject()) return false;
		
		setName(json.getString(TMDbConstants.NAME));
		setId(json.getInt(TMDbConstants.ID));
		setProfilePath(json.getString(TMDbConstants.PROFILE_PATH));
		if (json.has(TMDbConstants.ADULT)) setAdult(json.getBoolean(TMDbConstants.ADULT));
		
		return true;
	}
	
	//region Static methods
	
	/**
	 * Gets the latest person added to the TMDb.
	 * 
	 * @return The latest person added to the TMDb
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
	public static TMDbPerson getLatest() throws ResponseException {
		
		TMDbResponseObject response = TMDbAPI.getLatestPerson();
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			return new TMDbPerson(response.getData());
		}
	}
	
	/**
	 * Gets the general person information for a specific id.
	 * 
	 * @param personID The person ID
	 * @return The general person information
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
	public static TMDbPerson getInformation(int personID) throws ResponseException {
		
		TMDbResponseObject response = TMDbAPI.getPersonInformation(personID);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			return new TMDbPerson(response.getData());
		}
	}
	
	/**
	 * Gets the credits list of the specified person.
	 * 
	 * @param persondID The person ID
	 * @return The credits list
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
	public static Set<TMDbCredit> getCredits(int persondID) throws ResponseException {
		
		TMDbResponseObject response = TMDbAPI.getPersonCredits(persondID);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
		
			Set<TMDbCredit> credits = new LinkedHashSet<TMDbCredit>();
			
			JSONArray castArray = response.getData().getJSONArray(TMDbConstants.CAST);
			
			for (Object obj : castArray) {
				credits.add(new TMDbCredit((JSONObject) obj, Type.CAST));
			}
			
			JSONArray crewArray = response.getData().getJSONArray(TMDbConstants.CREW);
			
			for (Object obj : crewArray) {
				credits.add(new TMDbCredit((JSONObject) obj, Type.CREW));
			}
			
			return credits;
		}
	}
	
	/**
	 * Gets the images list of the specified person.
	 * 
	 * @param personID The person ID
	 * @return The images list
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
	public static Set<TMDbProfile> getImages(int personID) throws ResponseException {
		TMDbResponseObject response = TMDbAPI.getPersonImages(personID);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			
			JSONArray imgs = response.getData().getJSONArray(TMDbConstants.PROFILES);
			Set<TMDbProfile> images = new LinkedHashSet<TMDbProfile>();
			for (Object obj : imgs) {
			    images.add(new TMDbProfile((JSONObject) obj));
			}
			
			return images;
		}
	}
	
	/**
	 * Gets a list of people ids that have been edited. 
	 * By default we show the last 24 hours and only 100 items per page. 
	 * The maximum number of days that can be returned in a single request is 14. 
	 * Please note that the change log system to support this was changed on 
	 * October 5, 2012 and will only show people that have been edited since.
	 * Returns the results of the first page.
	 * 
	 * @return List of people ids that have been edited
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
	public static Set<Integer> getChanged() throws ResponseException {
		
		TMDbResponseArray response = TMDbAPI.getChangedPersons();
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			
			Set<JSONObject> array = response.getData();
			Set<Integer> ids = new LinkedHashSet<Integer>();
			for (JSONObject json : array) {
			    ids.add(json.getInt(TMDbConstants.ID));
			}
			
			return ids;
		}
	}
	
	/**
	 * Gets a list of people ids that have been edited. 
	 * By default we show the last 24 hours and only 100 items per page. 
	 * The maximum number of days that can be returned in a single request is 14. 
	 * Please note that the change log system to support this was changed on 
	 * October 5, 2012 and will only show people that have been edited since.
	 * Returns the results of the first page.
	 * 
	 * @param start The date where the search starts 
	 * @param end The date where the search ends
	 * @return List of people ids that have been edited
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
	public static Set<Integer> getChanged(Date start, Date end) throws ResponseException {
		
		TMDbResponseArray response = TMDbAPI.getChangedPersons(start, end);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			
			Set<JSONObject> array = response.getData();
			Set<Integer> ids = new LinkedHashSet<Integer>();
			for (JSONObject json : array) {
			    ids.add(json.getInt(TMDbConstants.ID));
			}
			
			return ids;
		}
	}

	/**
	 * Gets a list of people ids that have been edited. 
	 * By default we show the last 24 hours and only 100 items per page. 
	 * The maximum number of days that can be returned in a single request is 14. 
	 * Please note that the change log system to support this was changed on 
	 * October 5, 2012 and will only show people that have been edited since.
	 * Returns the results of the first page.
	 * 
	 * @param start The date where the search starts 
	 * @param end The date where the search ends
	 * @return List of people ids that have been edited
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
	public static Set<Integer> getChanged(String start, String end) throws ResponseException {
	
		TMDbResponseArray response = TMDbAPI.getChangedPersons(start, end);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			
			Set<JSONObject> array = response.getData();
			Set<Integer> ids = new LinkedHashSet<Integer>();
			for (JSONObject json : array) {
			    ids.add(json.getInt(TMDbConstants.ID));
			}
			
			return ids;
		}
	}
	
	/**
	 * Gets a list of people ids that have been edited. 
	 * By default we show the last 24 hours and only 100 items per page. 
	 * The maximum number of days that can be returned in a single request is 14. 
	 * Please note that the change log system to support this was changed on 
	 * October 5, 2012 and will only show people that have been edited since.
	 * Returns the results of the given page number.
	 * 
	 * @param page The page number to retrieve
	 * @return List of people ids that have been edited
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
	public static Set<Integer> getChanged(int page) throws ResponseException {
		
		TMDbResponseArray response = TMDbAPI.getChangedPersons(page);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			
			Set<JSONObject> array = response.getData();
			Set<Integer> ids = new LinkedHashSet<Integer>();
			for (JSONObject json : array) {
			    ids.add(json.getInt(TMDbConstants.ID));
			}
			
			return ids;
		}
	}
	
	/**
	 * Gets a list of people ids that have been edited. 
	 * By default we show the last 24 hours and only 100 items per page. 
	 * The maximum number of days that can be returned in a single request is 14. 
	 * Please note that the change log system to support this was changed on 
	 * October 5, 2012 and will only show people that have been edited since.
	 * Returns the results of the given page number.
	 * 
	 * @param start The date where the search starts 
	 * @param end The date where the search ends
	 * @param page The page number to retrieve
	 * @return List of people ids that have been edited
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
	public static Set<Integer> getChanged(String start, String end, int page) throws ResponseException {
		
		TMDbResponseArray response = TMDbAPI.getChangedPersons(start, end, page);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			
			Set<JSONObject> array = response.getData();
			Set<Integer> ids = new LinkedHashSet<Integer>();
			for (JSONObject json : array) {
			    ids.add(json.getInt(TMDbConstants.ID));
			}
			
			return ids;
		}
	}
	
	/**
	 * Gets a list of people ids that have been edited. 
	 * By default we show the last 24 hours and only 100 items per page. 
	 * The maximum number of days that can be returned in a single request is 14. 
	 * Please note that the change log system to support this was changed on 
	 * October 5, 2012 and will only show people that have been edited since.
	 * Returns the results of the given page number.
	 * 
	 * @param start The date where the search starts 
	 * @param end The date where the search ends
	 * @param page The page number to retrieve
	 * @return List of people ids that have been edited
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
	public static Set<Integer> getChanged(Date start, Date end, int page) throws ResponseException {
		
		TMDbResponseArray response = TMDbAPI.getChangedPersons(start, end, page);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			
			Set<JSONObject> array = response.getData();
			Set<Integer> ids = new LinkedHashSet<Integer>();
			for (JSONObject json : array) {
			    ids.add(json.getInt(TMDbConstants.ID));
			}
			
			return ids;
		}
	}
	
	/**
	 * Gets the entire list of people ids that have been edited. 
	 * By default we show the last 24 hours and only 100 items per page. 
	 * The maximum number of days that can be returned in a single request is 14. 
	 * Please note that the change log system to support this was changed on 
	 * October 5, 2012 and will only show people that have been edited since.
	 * 
	 * @return List of people ids that have been edited
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
	public static Set<Integer> getAllChanged() throws ResponseException {
		
		TMDbResponseArray response = TMDbAPI.getAllChangedPersons();
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			
			Set<JSONObject> array = response.getData();
			Set<Integer> ids = new LinkedHashSet<Integer>();
			for (JSONObject json : array) {
			    ids.add(json.getInt(TMDbConstants.ID));
			}
			
			return ids;
		}
	}
	
	/**
	 * Gets the entire list of people ids that have been edited. 
	 * By default we show the last 24 hours and only 100 items per page. 
	 * The maximum number of days that can be returned in a single request is 14. 
	 * Please note that the change log system to support this was changed on 
	 * October 5, 2012 and will only show people that have been edited since.
	 * 
	 * @param start The date where the search starts 
	 * @param end The date where the search ends
	 * @return List of people ids that have been edited
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
	public static Set<Integer> getAllChanged(String start, String end) throws ResponseException {
		
		TMDbResponseArray response = TMDbAPI.getAllChangedPersons(start, end);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			
			Set<JSONObject> array = response.getData();
			Set<Integer> ids = new LinkedHashSet<Integer>();
			for (JSONObject json : array) {
			    ids.add(json.getInt(TMDbConstants.ID));
			}
			
			return ids;
		}
	}
	
	/**
	 * Gets the entire list of people ids that have been edited. 
	 * By default we show the last 24 hours and only 100 items per page. 
	 * The maximum number of days that can be returned in a single request is 14. 
	 * Please note that the change log system to support this was changed on 
	 * October 5, 2012 and will only show people that have been edited since.
	 * 
	 * @param start The date where the search starts 
	 * @param end The date where the search ends
	 * @return List of people ids that have been edited
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
	public static Set<Integer> getAllChanged(Date start, Date end) throws ResponseException {
		
		TMDbResponseArray response = TMDbAPI.getAllChangedPersons(start, end);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			
			Set<JSONObject> array = response.getData();
			Set<Integer> ids = new LinkedHashSet<Integer>();
			for (JSONObject json : array) {
			    ids.add(json.getInt(TMDbConstants.ID));
			}
			
			return ids;
		}
	}
	
	//endregion
	
	//region Search methods
	
	/**
	 * Searches for people by name. 
	 * Returns the results of the first page.
	 * 
	 * @param name The person name
	 * @return The people that match with the given name
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
	public static Set<TMDbPersonThumbnail> searchByName(String name) throws ResponseException {
		return searchByName(name, 1);
	}
	
	/**
	 * Searches for people by name. 
	 * Returns the results of the given page number.
	 * 
	 * @param name The person name
	 * @param page The page number to retrieve
	 * @return The people that match with the given name
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
	public static Set<TMDbPersonThumbnail> searchByName(String name, int page) throws ResponseException {
		TMDbResponseArray response = TMDbAPI.searchPersonByName(name, page);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			Set<JSONObject> array = response.getData();
			Set<TMDbPersonThumbnail> people = new LinkedHashSet<TMDbPersonThumbnail>();
			for(JSONObject json : array) {
				people.add(new TMDbPersonThumbnail(json));
			}
			
			return people;
		}
	}
	
	/**
	 * Searches for people by name. 
	 * Returns the results of the first page.
	 * 
	 * @param name The person name
	 * @param adult Whether the person is in movies for adults only or not
	 * @return The people that match with the given name
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
	public static Set<TMDbPersonThumbnail> searchByName(String name, boolean adult) throws ResponseException {
		return searchByName(name, adult, 1);
	}
	
	/**
	 * Searches for people by name. 
	 * Returns the results of the given page number.
	 * 
	 * @param name The person name
	 * @param adult Whether the person is in movies for adults only or not
	 * @param page The page number to retrieve
	 * @return The people that match with the given name
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
	public static Set<TMDbPersonThumbnail> searchByName(String name, boolean adult, int page) throws ResponseException {
		TMDbResponseArray response = TMDbAPI.searchPersonByName(name, adult, page);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			Set<JSONObject> array = response.getData();
			Set<TMDbPersonThumbnail> people = new LinkedHashSet<TMDbPersonThumbnail>();
			for(JSONObject json : array) {
				people.add(new TMDbPersonThumbnail(json));
			}
			
			return people;
		}
	}
	
	/**
	 * Searches for people by name. 
	 * Gets all the results.
	 * 
	 * @param name The person name
	 * @return The people that match with the given name
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
	public static Set<TMDbPersonThumbnail> fullSearchByName(String name) throws ResponseException {
		TMDbResponseArray response = TMDbAPI.fullSearchPersonByName(name);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			Set<JSONObject> array = response.getData();
			Set<TMDbPersonThumbnail> people = new LinkedHashSet<TMDbPersonThumbnail>();
			for(JSONObject json : array) {
				people.add(new TMDbPersonThumbnail(json));
			}
			
			return people;
		}
	}
	
	/**
	 * Searches for people by name. 
	 * Gets all the results.
	 * 
	 * @param name The person name
	 * @param adult Whether the person is in movies for adults only or not
	 * @return The people that match with the given name
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
	public static Set<TMDbPersonThumbnail> fullSearchByName(String name, boolean adult) throws ResponseException {
		TMDbResponseArray response = TMDbAPI.fullSearchPersonByName(name, adult);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			Set<JSONObject> array = response.getData();
			Set<TMDbPersonThumbnail> people = new LinkedHashSet<TMDbPersonThumbnail>();
			for(JSONObject json : array) {
				people.add(new TMDbPersonThumbnail(json));
			}
			
			return people;
		}
	}
	
	//endregion
	
}
