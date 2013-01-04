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

package mk.tmdb.entity.movie;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import mk.tmdb.core.TMDbConstants;
import mk.tmdb.entity.TMDbCollection;
import mk.tmdb.entity.TMDbCountry;
import mk.tmdb.entity.TMDbGenre;
import mk.tmdb.entity.TMDbLanguage;
import mk.tmdb.entity.company.TMDbCompanyThumbnail;
import mk.tmdb.utils.TMDbLog;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Class that represents a Movie with limited features.</br>
 * MovieReduced class has more features than MovieThumbnail and MovieReduced classes.</br>
 * To get a more detailed class see {@link TMDbMovieFull}. 
 * 
 * @author Mirko Polato
 *
 */
public class TMDbMovie extends TMDbMovieReduced {
	
	//region Private fields

	/**
	 * The Internet Movie Database (IMDb) ID.
	 */
	protected String imdbID = null;
	
	/**
	 * The movie budget.
	 */
	protected Integer budget = null;
	
	/**
	 * The movie home page.
	 */
	protected URL homepage = null;
	
	/**
	 * The movie overview.
	 */
	protected String overview = null;
	
	/**
	 * The movie runtime.
	 */
	protected Integer runtime = null;
	
	/**
	 * The movie status.
	 */
	protected String status = null;
	
	/**
	 * The movie tagline.
	 */
	protected String tagline = null;
	
	/**
	 * The movie revenue.
	 */
	protected Integer revenue = null;
	
	/**
	 * The collection belongs to the movie.
	 */
	protected TMDbCollection collection = null;
	
	/**
	 * The movie genres.
	 */
	protected Set<TMDbGenre> genres = Collections.synchronizedSet(new LinkedHashSet<TMDbGenre>());
	
	/**
	 * The movie companies.
	 */
	protected Set<TMDbCompanyThumbnail> companies = Collections.synchronizedSet(new LinkedHashSet<TMDbCompanyThumbnail>());
	
	/**
	 * The movie countries.
	 */
	protected Set<TMDbCountry> countries = Collections.synchronizedSet(new LinkedHashSet<TMDbCountry>());
	
	/**
	 * The movie languages.
	 */
	protected Set<TMDbLanguage> languages = Collections.synchronizedSet(new LinkedHashSet<TMDbLanguage>());
	
	//endregion
	
	/**
	 * Creates a new instance of Movie based on the origin JSON object.
	 * 
	 * @param json The origin JSON object
	 */
	public TMDbMovie(JSONObject json) {
		super(json);
		parseJSON(json);
	}
	
	/**
	 * Copy constructor.
	 * 
	 * @param movie The movie to copy
	 */
	public TMDbMovie(TMDbMovieThumbnail movie) {
		this(movie.getOriginJSON());
	}
	
	//region Getters/Setters
	
	/**
	 * Gets the IMDb ID.
	 * 
	 * @return The IMDb ID
	 */
	public String getImdbID() {
		return imdbID;
	}
	
	/**
	 * Sets the IMDb ID.
	 * 
	 * @param imdbID The new IMDb ID
	 */
	public void setImdbID(String imdbID) {
		this.imdbID = imdbID;
	}
	
	/**
	 * Checks if the IMDb ID is set.
	 * 
	 * @return Whether the IMDb ID is set or not
	 */
	public boolean isImdbIDSet() {
		return imdbID != null;
	}
	
	/**
	 * Gets the movie budget.
	 * 
	 * @return The movie budget
	 */
	public int getBudget() {
		return budget;
	}
	
	/**
	 * Sets the movie budget.
	 * 
	 * @param budget The new movie budget
	 */
	public void setBudget(int budget) {
		this.budget = budget;
	}
	
	/**
	 * Checks if the movie budget is set.
	 * 
	 * @return Whether the movie budget is set or not
	 */
	public boolean isBudgetSet() {
		return budget != null;
	}
	
	/**
	 * Gets the movie genres.
	 * 
	 * @return The movie genres
	 */
	public Set<TMDbGenre> getGenres() {
		return genres;
	}
	
	/**
	 * Sets the movie genres.
	 * 
	 * @param genres The new list of movie genres
	 */
	public void setGenres(Set<TMDbGenre> genres) {
		this.genres.clear();
		this.genres.addAll(genres);
	}
	
	/**
	 * Gets the movie home page.
	 * 
	 * @return The movie home page
	 */
	public URL getHomepage() {
		return homepage;
	}
	
	/**
	 * Sets the movie home page.
	 * 
	 * @param homepage The new movie home page
	 */
	public void setHomepage(URL homepage) {
		this.homepage = homepage;
	}
	
	/**
	 * Checks if the movie home page is set.
	 * 
	 * @return Whether the movie home page is set or not
	 */
	public boolean isHomepageSet() {
		return homepage != null;
	}
	
	/**
	 * Gets the movie overview.
	 * 
	 * @return The movie overview
	 */
	public String getOverview() {
		return overview;
	}
	
	/**
	 * Sets the movie overview.
	 * 
	 * @param overview The new movie overview
	 */
	public void setOverview(String overview) {
		this.overview = overview;
	}
	
	/**
	 * Checks if the movie overview is set.
	 * 
	 * @return Whether the movie overview is set or not
	 */
	public boolean isOverviewSet() {
		return overview != null;
	}
	
	/**
	 * Gets the companies of the movie.
	 * 
	 * @return The companies of the movies
	 */
	public Set<TMDbCompanyThumbnail> getCompanies() {
		return companies;
	}
	
	/**
	 * Sets the countries of the movie. 
	 * 
	 * @param countries The new countries of the movie
	 */
	public void setCountries(Set<TMDbCountry> countries) {
		this.countries.clear();
		this.countries.addAll(countries);
	}
	
	/**
	 * Gets the country of the movie.
	 * 
	 * @return The country of the movie
	 */
	public Set<TMDbCountry> getCountries() {
		return countries;
	}
	
	/**
	 * Sets the companies of the movie.
	 * 
	 * @param companies The new companies of the movie
	 */
	public void setCompanies(Set<TMDbCompanyThumbnail> companies) {
		this.companies.clear();
		this.companies.addAll(companies);
	}
	
	/**
	 * Gets the movie runtime.
	 * 
	 * @return The movie runtime
	 */
	public int getRuntime() {
		return runtime;
	}
	
	/**
	 * Sets the movie runtime.
	 * 
	 * @param runtime The new movie runtime
	 */
	public void setRuntime(int runtime) {
		this.runtime = runtime;
	}
	
	/**
	 * Checks if the runtime is set.
	 * 
	 * @return Whether the runtime is set or not
	 */
	public boolean isRuntimeSet() {
		return runtime != null;
	}
	
	/**
	 * Gets the languages of the movie.
	 * 
	 * @return The languages of the movie
	 */
	public Set<TMDbLanguage> getLanguages() {
		return languages;
	}
	
	/**
	 * Sets the languages of the movie.
	 * 
	 * @param languages The new languages of the movie
	 */
	public void setLanguages(Set<TMDbLanguage> languages) {
		this.languages.clear();
		this.languages.addAll(languages);
	}
	
	/**
	 * Gets the movie status.
	 * 
	 * @return The movie status
	 */
	public String getStatus() {
		return status;
	}
	
	/**
	 * Sets the movie status.
	 * 
	 * @param status The new movie status
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	/**
	 * Checks if the movie status is set.
	 * 
	 * @return Whether the movie status is set or not
	 */
	public boolean isStatusSet() {
		return status != null;
	}
	
	/**
	 * Gets the movie tagline.
	 * 
	 * @return The movie tagline
	 */
	public String getTagline() {
		return tagline;
	}
	
	/**
	 * Sets the movie tagline.
	 * 
	 * @param tagline The new movie tagline
	 */
	public void setTagline(String tagline) {
		this.tagline = tagline;
	}
	
	/**
	 * Checks if the movie tagline is set.
	 * 
	 * @return Whether the movie tgline is set or not
	 */
	public boolean isTaglineSet() {
		return tagline != null;
	}
	
	/**
	 * Gets the movie revenue.
	 * 
	 * @return The movie revenue
	 */
	public int getRevenue() {
		return revenue;
	}
	
	/**
	 * Sets the movie revenue
	 * 
	 * @param revenue The new movie revenue
	 */
	public void setRevenue(int revenue) {
		this.revenue = revenue;
	}
	
	/**
	 * Checks if the movie revenue is set.
	 * 
	 * @return Whether the movie revenue is set or not
	 */
	public boolean isRevenueSet() {
		return revenue != null;
	}

	/**
	 * Gets the collection belongs to the movie.
	 * 
	 * @return The collection belongs to the movie
	 */
	public TMDbCollection getCollection() {
		return collection;
	}

	/**
	 * Sets the collection belongs to the movie.
	 * 
	 * @param collection The new collection belongs to the movie
	 */
	public void setCollection(TMDbCollection collection) {
		this.collection = collection;
	}
	
	/**
	 * Checks if the collection is set.
	 * 
	 * @return Whether the collection is set or not
	 */
	public boolean isCollectionSet() {
		return collection != null;
	}
	
	//endregion
	
	/**
	 * Parses the origin JSON object.
	 */
	private boolean parseJSON(JSONObject json) {
			
		if (json.isNullObject()) return false;
		
		if (json.has(TMDbConstants.BUDGET)) setBudget(json.getInt(TMDbConstants.BUDGET));
		if (json.has(TMDbConstants.IMDB)) setImdbID(json.getString(TMDbConstants.IMDB));
		if (json.has(TMDbConstants.OVERVIEW)) setOverview(json.getString(TMDbConstants.OVERVIEW));
		if (json.has(TMDbConstants.REVENUE)) setRevenue(json.getInt(TMDbConstants.REVENUE));
		if (json.has(TMDbConstants.RUNTIME)) setRuntime(json.getInt(TMDbConstants.RUNTIME));
		if (json.has(TMDbConstants.STATUS)) setStatus(json.getString(TMDbConstants.STATUS));
		if (json.has(TMDbConstants.TAGLINE)) setTagline(json.getString(TMDbConstants.TAGLINE));
		if (json.has(TMDbConstants.BELONGS_TO_COLLECTION)) setCollection(new TMDbCollection(json.getJSONObject(TMDbConstants.BELONGS_TO_COLLECTION)));
		
		if (json.has(TMDbConstants.HOMEPAGE)) {
			try {
				setHomepage(new URL(json.getString(TMDbConstants.HOMEPAGE)));
			}
			catch (MalformedURLException e) {
				TMDbLog.print(e);
			}
		}
		
		if (json.has(TMDbConstants.GENRES)) {
			JSONArray genresList = json.getJSONArray(TMDbConstants.GENRES);
			genres = Collections.synchronizedSet(new LinkedHashSet<TMDbGenre>());
			for (Object obj : genresList) {
			    genres.add(new TMDbGenre((JSONObject) obj));
			}
		}
		
		if (json.has(TMDbConstants.COMPANIES)) {
			JSONArray companiesList = json.getJSONArray(TMDbConstants.COMPANIES);
			for (Object obj : companiesList) {
			    companies.add(new TMDbCompanyThumbnail((JSONObject) obj));
			}
		}
		
		if (json.has(TMDbConstants.PRODUCTION_COUNTRIES)) {
			JSONArray countriesList = json.getJSONArray(TMDbConstants.PRODUCTION_COUNTRIES);
			for (Object obj : countriesList) {
			    countries.add(new TMDbCountry((JSONObject) obj));
			}
		}
		
		if (json.has(TMDbConstants.LANGUAGES)) {
			JSONArray langsList = json.getJSONArray(TMDbConstants.LANGUAGES);
			for (Object obj : langsList) {
			    languages.add(new TMDbLanguage((JSONObject) obj));
			}
		}
		
		return true;
	}
	
}
