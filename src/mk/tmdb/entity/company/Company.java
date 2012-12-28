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

import java.net.MalformedURLException;
import java.net.URL;

import mk.tmdb.core.Constants;
import mk.tmdb.utils.Log;
import net.sf.json.JSONObject;

/**
 * Class that represents a company.
 * 
 * @author Mirko Polato
 *
 */
public class Company extends CompanyThumbnail {

	//region Fields
	
	/**
	 * Company description.
	 */
	private String description = null;
	
	/**
	 * Headquarters of the company.
	 */
	private String headquarters = null;
	
	/**
	 * Company home page.
	 */
	private URL homepage = null;
	
	/**
	 * Parent company.
	 */
	private String parentCompany = null;
	
	//endregion
	
	/**
	 * Creates a new instance of Company based on the origin JSON object.
	 * 
	 * @param json The origin JSON object
	 */
	public Company(JSONObject json) {
		super(json);
		parseJSON(json);
	}
	
	/**
	 * Copy constructor.
	 * 
	 * @param company The company to copy
	 */
	public Company(CompanyThumbnail company) {
		this(company.getOriginJSON());
	}

	//region Getters/Setters
	
	/**
	 * Gets the company description.
	 * 
	 * @return The company description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the company description.
	 * 
	 * @param description The new company description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Checks if the company description is set.
	 * 
	 * @return Whether the company description is set or not
	 */
	public boolean isDescriptionSet() {
		return description != null;
	}

	/**
	 * Gets the name of the company headquarters.
	 * 
	 * @return The name of the company headquarters.
	 */
	public String getHeadquarters() {
		return headquarters;
	}

	/**
	 * Sets the name of the company headquarters.
	 * 
	 * @param headquarters The new name of the company headquarters.
	 */
	public void setHeadquarters(String headquarters) {
		this.headquarters = headquarters;
	}
	
	/**
	 * Checks if the headquarters is set.
	 * 
	 * @return Whether the headquarters is set or not
	 */
	public boolean isHeadquartersSet() {
		return headquarters != null;
	}

	/**
	 * Gets the company home page.
	 * 
	 * @return The company home page
	 */
	public URL getHomepage() {
		return homepage;
	}

	/**
	 * Sets the company home page.
	 * 
	 * @param homepage The new company home page
	 */
	public void setHomepage(URL homepage) {
		this.homepage = homepage;
	}

	/**
	 * Checks if the home page is set.
	 * 
	 * @return Whether the home page is set or not
	 */
	public boolean isHomepageSet() {
		return homepage != null;
	}
	
	/**
	 * Gets the parent company name.
	 * 
	 * @return The parent company name
	 */
	public String getParentCompany() {
		return parentCompany;
	}

	/**
	 * Sets the parent company name.
	 * 
	 * @param parentCompany The new parent company name
	 */
	public void setParentCompany(String parentCompany) {
		this.parentCompany = parentCompany;
	}
	
	/**
	 * Checks if the parent company is set.
	 * 
	 * @return Whether the parent company is set or not
	 */
	public boolean isParentCompanySet() {
		return parentCompany != null;
	}
	
	//endregion
	
	/**
	 * Parses the origin JSON object.
	 */
	private boolean parseJSON(JSONObject json) {
		
		if (json.isNullObject()) return false;
		
		if (json.has(Constants.DESCRIPTION)) setDescription(json.getString(Constants.DESCRIPTION));
		if (json.has(Constants.HOMEPAGE))
			try {
				setHomepage(new URL(json.getString(Constants.HOMEPAGE)));
			} catch (MalformedURLException e) {
				Log.print(e);
			}
		if (json.has(Constants.PARENT_COMPANY)) setParentCompany(json.getString(Constants.PARENT_COMPANY));
		if (json.has(Constants.HEADQUARTERS)) setHeadquarters(json.getString(Constants.HEADQUARTERS));
		
		return true;
	}
	
}
