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

public class Movie extends MovieReduced {
	
	//region Private fields

	protected String imdbID = null;
	protected Integer budget = null;
	protected URL homepage = null;
	protected String overview = null;
	protected Integer runtime = null;
	protected String status = null;
	protected String tagline = null;
	protected Integer revenue = null;
	protected Collection collection = null;
	protected Set<Genre> genres = Collections.synchronizedSet(new LinkedHashSet<Genre>());
	protected Set<CompanyThumbnail> companies = Collections.synchronizedSet(new LinkedHashSet<CompanyThumbnail>());
	protected Set<Country> countries = Collections.synchronizedSet(new LinkedHashSet<Country>());
	protected Set<Language> languages = Collections.synchronizedSet(new LinkedHashSet<Language>());
	
	//endregion
	
	public Movie(JSONObject json) {
		super(json);
		parseJSON(json);
	}
	
	public Movie(MovieThumbnail movie) {
		this(movie.getOriginJSON());
	}
	
	//region Getters/Setters
	
	public String getImdbID() {
		return imdbID;
	}
	
	public void setImdbID(String imdbID) {
		this.imdbID = imdbID;
	}
	
	public boolean isImdbIDSet() {
		return imdbID != null;
	}
	
	public int getBudget() {
		return budget;
	}
	
	public void setBudget(int budget) {
		this.budget = budget;
	}
	
	public boolean isBudgetSet() {
		return budget != null;
	}
	
	public Set<Genre> getGenres() {
		return genres;
	}
	
	public void setGenres(Set<Genre> genres) {
		this.genres.clear();
		this.genres.addAll(genres);
	}
	
	public URL getHomepage() {
		return homepage;
	}
	
	public void setHomepage(URL homepage) {
		this.homepage = homepage;
	}
	
	public boolean isHomepageSet() {
		return homepage != null;
	}
	
	public String getOverview() {
		return overview;
	}
	
	public void setOverview(String overview) {
		this.overview = overview;
	}
	
	public boolean isOverviewSet() {
		return overview != null;
	}
	
	public Set<CompanyThumbnail> getCompanies() {
		return companies;
	}
	
	public void setCountries(Set<Country> countries) {
		this.countries.clear();
		this.countries.addAll(countries);
	}
	
	public Set<Country> getCountries() {
		return countries;
	}
	
	public void setCompanies(Set<CompanyThumbnail> companies) {
		this.companies.clear();
		this.companies.addAll(companies);
	}
	
	public int getRuntime() {
		return runtime;
	}
	
	public void setRuntime(int runtime) {
		this.runtime = runtime;
	}
	
	public boolean isRuntimeSet() {
		return runtime != null;
	}
	
	public Set<Language> getLanguages() {
		return languages;
	}
	
	public void setLanguages(Set<Language> languages) {
		this.languages.clear();
		this.languages.addAll(languages);
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public boolean isStatusSet() {
		return status != null;
	}
	
	public String getTagline() {
		return tagline;
	}
	
	public void setTagline(String tagline) {
		this.tagline = tagline;
	}
	
	public boolean isTaglineSet() {
		return tagline != null;
	}
	
	public int getRevenue() {
		return revenue;
	}
	
	public void setRevenue(int revenue) {
		this.revenue = revenue;
	}
	
	public boolean isRevenueSet() {
		return revenue != null;
	}

	public Collection getCollection() {
		return collection;
	}

	public void setCollection(Collection collection) {
		this.collection = collection;
	}
	
	public boolean isCollectionSet() {
		return collection != null;
	}
	
	//endregion
	
	@Override
	protected boolean parseJSON(JSONObject json) {
			
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
