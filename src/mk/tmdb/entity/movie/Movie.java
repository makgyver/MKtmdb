package mk.tmdb.entity.movie;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import mk.tmdb.core.Constants;
import mk.tmdb.entity.Collection;
import mk.tmdb.entity.Country;
import mk.tmdb.entity.Genre;
import mk.tmdb.entity.Language;
import mk.tmdb.entity.company.CompanyThumbnail;
import mk.tmdb.utils.Log;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Class that represents a Movie with limited features.</br>
 * MovieReduced class has more features than MovieThumbnail and MovieReduced classes.</br>
 * To get a more detailed class see {@link MovieFull}. 
 * 
 * @author Mirko Polato
 *
 */
public class Movie extends MovieReduced {
	
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
	protected Collection collection = null;
	
	/**
	 * The movie genres.
	 */
	protected Set<Genre> genres = Collections.synchronizedSet(new LinkedHashSet<Genre>());
	
	/**
	 * The movie companies.
	 */
	protected Set<CompanyThumbnail> companies = Collections.synchronizedSet(new LinkedHashSet<CompanyThumbnail>());
	
	/**
	 * The movie countries.
	 */
	protected Set<Country> countries = Collections.synchronizedSet(new LinkedHashSet<Country>());
	
	/**
	 * The movie languages.
	 */
	protected Set<Language> languages = Collections.synchronizedSet(new LinkedHashSet<Language>());
	
	//endregion
	
	/**
	 * Creates a new instance of Movie based on the origin JSON object.
	 * 
	 * @param json The origin JSON object
	 */
	public Movie(JSONObject json) {
		super(json);
		parseJSON(json);
	}
	
	/**
	 * Copy constructor.
	 * 
	 * @param movie The movie to copy
	 */
	public Movie(MovieThumbnail movie) {
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
	public Set<Genre> getGenres() {
		return genres;
	}
	
	/**
	 * Sets the movie genres.
	 * 
	 * @param genres The new list of movie genres
	 */
	public void setGenres(Set<Genre> genres) {
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
	public Set<CompanyThumbnail> getCompanies() {
		return companies;
	}
	
	/**
	 * Sets the countries of the movie. 
	 * 
	 * @param countries The new countries of the movie
	 */
	public void setCountries(Set<Country> countries) {
		this.countries.clear();
		this.countries.addAll(countries);
	}
	
	/**
	 * Gets the country of the movie.
	 * 
	 * @return The country of the movie
	 */
	public Set<Country> getCountries() {
		return countries;
	}
	
	/**
	 * Sets the companies of the movie.
	 * 
	 * @param companies The new companies of the movie
	 */
	public void setCompanies(Set<CompanyThumbnail> companies) {
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
	public Set<Language> getLanguages() {
		return languages;
	}
	
	/**
	 * Sets the languages of the movie.
	 * 
	 * @param languages The new languages of the movie
	 */
	public void setLanguages(Set<Language> languages) {
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
	public Collection getCollection() {
		return collection;
	}

	/**
	 * Sets the collection belongs to the movie.
	 * 
	 * @param collection The new collection belongs to the movie
	 */
	public void setCollection(Collection collection) {
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
			
		if (json.has(Constants.BUDGET)) setBudget(json.getInt(Constants.BUDGET));
		if (json.has(Constants.IMDB)) setImdbID(json.getString(Constants.IMDB));
		if (json.has(Constants.OVERVIEW)) setOverview(json.getString(Constants.OVERVIEW));
		if (json.has(Constants.REVENUE)) setRevenue(json.getInt(Constants.REVENUE));
		if (json.has(Constants.RUNTIME)) setRuntime(json.getInt(Constants.RUNTIME));
		if (json.has(Constants.STATUS)) setStatus(json.getString(Constants.STATUS));
		if (json.has(Constants.TAGLINE)) setTagline(json.getString(Constants.TAGLINE));
		if (json.has(Constants.BELONGS_TO_COLLECTION)) setCollection(new Collection(json.getJSONObject(Constants.BELONGS_TO_COLLECTION)));
		
		if (json.has(Constants.HOMEPAGE)) {
			try {
				setHomepage(new URL(json.getString(Constants.HOMEPAGE)));
			}
			catch (MalformedURLException e) {
				Log.print(e);
			}
		}
		
		if (json.has(Constants.GENRES)) {
			JSONArray genresList = json.getJSONArray(Constants.GENRES);
			genres = Collections.synchronizedSet(new LinkedHashSet<Genre>());
			for (Object obj : genresList) {
			    genres.add(new Genre((JSONObject) obj));
			}
		}
		
		if (json.has(Constants.COMPANIES)) {
			JSONArray companiesList = json.getJSONArray(Constants.COMPANIES);
			for (Object obj : companiesList) {
			    companies.add(new CompanyThumbnail((JSONObject) obj));
			}
		}
		
		if (json.has(Constants.COUNTRIES)) {
			JSONArray countriesList = json.getJSONArray(Constants.COUNTRIES);
			for (Object obj : countriesList) {
			    countries.add(new Country((JSONObject) obj));
			}
		}
		
		if (json.has(Constants.LANGUAGES)) {
			JSONArray langsList = json.getJSONArray(Constants.LANGUAGES);
			for (Object obj : langsList) {
			    languages.add(new Language((JSONObject) obj));
			}
		}
		
		return true;
	}
	
}
