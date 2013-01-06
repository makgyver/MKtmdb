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

package mk.tmdb.entity.company;

import java.util.LinkedHashSet;
import java.util.Set;

import mk.tmdb.core.TMDbConstants;
import mk.tmdb.core.TMDbAPI;
import mk.tmdb.entity.TMDbEntity;
import mk.tmdb.entity.movie.TMDbMovieReduced;
import mk.tmdb.exception.TMDbResponseException;
import mk.tmdb.response.TMDbResponseArray;
import mk.tmdb.response.TMDbResponseObject;
import mk.tmdb.utils.Log;
import net.sf.json.JSONObject;

/**
 * Class that represent a Company with reduced features.
 * For more feature Company see the class {@link TMDbCompany}.
 * 
 * @author Mirko Polato
 *
 */
public class TMDbCompanyThumbnail extends TMDbEntity {

	/**
	 * The company ID.
	 */
	protected Integer id;
	
	/**
	 * The name of the company.
	 */
	protected String name;
	
	/**
	 * The logo path of the company.
	 */
	protected String logoPath = null;
	
	/**
	 * Creates a new instance of CompanyThumbnail based on the origin JSON object. 
	 * @param json The origin JSON object
	 */
	public TMDbCompanyThumbnail(JSONObject json) {
		super(json);
		parseJSON(json);
	}
	
	/**
	 * Copy constructor.
	 * 
	 * @param company The company to copy.
	 */
	public TMDbCompanyThumbnail(TMDbCompanyThumbnail company) {
		this(company.getOriginJSON());
	}
	
	//region Getters/Setters
	
	/**
	 * Gets the company ID.
	 * 
	 * @return The company ID
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the company ID.
	 * 
	 * @param id The new company ID
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Gets the company name.
	 * 
	 * @return The company name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the company name.
	 * 
	 * @param name The new company name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Gets the company logo path.
	 * 
	 * @return The company logo path
	 */
	public String getLogoPath() {
		return logoPath;
	}

	/**
	 * Sets the company logo path.
	 * 
	 * @param logoPath The new company logo path
	 */
	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}

	/**
	 * Checks if the logo path is set.
	 * 
	 * @return Whether the company logo path is set or not
	 */
	public boolean isLogoPathSet() {
		return logoPath != null;
	}

	//endregion
	
	private boolean parseJSON(JSONObject json) {
		
		if (json.isNullObject()) return false;
		
		try {
			setId(json.getInt(TMDbConstants.ID));
			setName(json.getString(TMDbConstants.NAME));
			if (json.has(TMDbConstants.LOGO_PATH)) setLogoPath(json.getString(TMDbConstants.LOGO_PATH));
			
		} catch (Exception e) {
			Log.print(e);
			return false;
		}
		
		return true;	
	}
	
	//region Static methods
	
	/**
	 * Gets a new instance of Company filled with the retrieved information about it.
	 * 
	 * @param companyID The company ID
	 * @return The company
	 * @throws TMDbResponseException Throws whether the server response is not a success.
	 */
	public static TMDbCompany getInformation(int companyID) throws TMDbResponseException {
		
		TMDbResponseObject response = TMDbAPI.getCompanyInformation(companyID);
		
		if (response.hasError()) {
			throw new TMDbResponseException(response.getStatus());
		} else {
			return new TMDbCompany(response.getData());
		}
	}
	
	/**
	 * Gets the list of movies associated with the specified company.
	 * Returns the results of the first page.
	 * 
	 * @param companyID The company ID
	 * @return The list of movies
	 * @throws TMDbResponseException Throws whether the server response is not a success.
	 */
	public static Set<TMDbMovieReduced> getAssociatedMovies(int companyID) throws TMDbResponseException {
		return getAssociatedMovies(companyID, 1);
	}
	
	/**
	 * Gets the list of movies associated with the specified company.
	 * Returns the results of the given page number.
	 * 
	 * @param companyID The company ID
	 * @param page The page number to retrieve
	 * @return The list of movies
	 * @throws TMDbResponseException Throws whether the server response is not a success.
	 */
	public static Set<TMDbMovieReduced> getAssociatedMovies(int companyID, int page) throws TMDbResponseException {
		
		TMDbResponseArray response = TMDbAPI.getMoviesByCompany(companyID, page);
		
		if (response.hasError()) {
			throw new TMDbResponseException(response.getStatus());
		} else {
			Set<TMDbMovieReduced> movies = new LinkedHashSet<TMDbMovieReduced>();
			Set<JSONObject> array = response.getData();
			for(JSONObject json : array) {
				movies.add(new TMDbMovieReduced(json));
			}
			
			return movies;
		}
	}
	
	/**
	 * Gets the entire list of movies associated with the specified company.
	 * 
	 * @param companyID The company ID
	 * @return The list of movies
	 * @throws TMDbResponseException Throws whether the server response is not a success.
	 */
	public static Set<TMDbMovieReduced> getAllAssociatedMovies(int companyID) throws TMDbResponseException {
		
		TMDbResponseArray response = TMDbAPI.getAllMoviesByCompany(companyID);
		
		if (response.hasError()) {
			throw new TMDbResponseException(response.getStatus());
		} else {
			Set<TMDbMovieReduced> movies = new LinkedHashSet<TMDbMovieReduced>();
			Set<JSONObject> array = response.getData();
			for(JSONObject json : array) {
				movies.add(new TMDbMovieReduced(json));
			}
			
			return movies;
		}
	}
	
	/**
	 * Searches for companies by name.
	 * Returns the results of the first page.
	 * 
	 * @param name The company name
	 * @return The list of company that match the given name
	 * @throws TMDbResponseException Throws whether the server response is not a success.
	 */
	public static Set<TMDbCompanyThumbnail> searchByName(String name) throws TMDbResponseException {
		return searchByName(name, 1);
	}
	
	/**
	 * Searches for companies by name.
	 * Returns the results of the given page number.
	 * 
	 * @param name The company name
	 * @param page The page number to retrieve
	 * @return The list of company that match the given name
	 * @throws TMDbResponseException Throws whether the server response is not a success.
	 */
	public static Set<TMDbCompanyThumbnail> searchByName(String name, int page) throws TMDbResponseException {
		TMDbResponseArray response = TMDbAPI.searchCompanyByName(name, page);
		
		if (response.hasError()) {
			throw new TMDbResponseException(response.getStatus());
		} else {
			Set<JSONObject> array = response.getData();
			Set<TMDbCompanyThumbnail> companies = new LinkedHashSet<TMDbCompanyThumbnail>();
			for(JSONObject json : array) {
				companies.add(new TMDbCompanyThumbnail(json));
			}
			
			return companies;
		}
	}
	
	/**
	 * Searches for companies by name. Gets all the results.
	 * 
	 * @param name The company name
	 * @return The list of company that match the given name
	 * @throws TMDbResponseException Throws whether the server response is not a success.
	 */
	public static Set<TMDbCompanyThumbnail> fullSearchByName(String name) throws TMDbResponseException {
		TMDbResponseArray response = TMDbAPI.fullSearchCompanyByName(name);
		
		if (response.hasError()) {
			throw new TMDbResponseException(response.getStatus());
		} else {
			Set<JSONObject> array = response.getData();
			Set<TMDbCompanyThumbnail> companies = new LinkedHashSet<TMDbCompanyThumbnail>();
			for(JSONObject json : array) {
				companies.add(new TMDbCompanyThumbnail(json));
			}
			
			return companies;
		}
	}
	
	//endregion

}
