package mk.tmdb.entity;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;

import mk.tmdb.utils.Log;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

public class Movie implements IEntity {
	
	private static final String ADULT = "adult";
	private static final String BACKDROP_PATH = "backdrop_path";
	private static final String BUDGET = "budget";
	private static final String GENRES = "genres";
	private static final String HOMEPAGE = "homepage";
	private static final String ID = "id";
	private static final String IMDB = "imdb_id";
	private static final String ORIGINAL_TITLE = "original_title";
	private static final String OVERVIEW = "overview";
	private static final String POPULARITY = "popularity";
	private static final String POSTER_PATH = "poster_path";
	private static final String COMPANIES = "production_companies";
	private static final String COUNTRIES = "production_countries";
	private static final String RELEASE_DATE = "release_date";
	private static final String REVENUE = "revenue";
	private static final String RUNTIME = "runtime";
	private static final String LANGUAGES = "spoken_languages";
	private static final String STATUS = "status";
	private static final String TAGLINE = "tagline";
	private static final String TITLE = "title";
	private static final String AVERAGE = "vote_average";
	private static final String COUNT = "vote_count";
	
	private boolean adult;
	private String backdropPath;
	private String posterPath;
	private int id;
	private String imdbID;
	private int budget;
	private Vector<Genre> genres = new Vector<Genre>();
	private URL hompage;
	private String overview;
	private double popularity;
	private String originalTitle;
	private Vector<Company> companies = new Vector<Company>();
	private Vector<Country> countries = new Vector<Country>();
	private String releaseDate;
	private int runtime;
	private Vector<Language> languages = new Vector<Language>();
	private String status;
	private String tagline;
	private String title;
	private double voteAverage;
	private int voteCount;
	private int revenue;
	
	public Movie(String jsonString) {
		this((JSONObject) JSONSerializer.toJSON(jsonString));
	}
	
	public Movie(JSONObject json) {
		parseJSON(json);
	}
	
	public boolean isAdult() {
		return adult;
	}
	
	public void setAdult(boolean adult) {
		this.adult = adult;
	}
	
	public String getBackdropPath() {
		return backdropPath;
	}
	
	public void setBackdropPath(String backdropPath) {
		this.backdropPath = backdropPath;
	}
	
	public String getPosterPath() {
		return posterPath;
	}
	
	public void setPosterPath(String posterPath) {
		this.posterPath = posterPath;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getImdbID() {
		return imdbID;
	}
	
	public void setImdbID(String imdbID) {
		this.imdbID = imdbID;
	}
	
	public int getBudget() {
		return budget;
	}
	
	public void setBudget(int budget) {
		this.budget = budget;
	}
	
	public Vector<Genre> getGenres() {
		return genres;
	}
	
	public void setGenres(Vector<Genre> genres) {
		this.genres = genres;
	}
	
	public URL getHompage() {
		return hompage;
	}
	
	public void setHompage(URL hompage) {
		this.hompage = hompage;
	}
	
	public String getOverview() {
		return overview;
	}
	
	public void setOverview(String overview) {
		this.overview = overview;
	}
	
	public double getPopularity() {
		return popularity;
	}
	
	public void setPopularity(double popularity) {
		this.popularity = popularity;
	}
	
	public String getOriginalTitle() {
		return originalTitle;
	}
	
	public void setOriginalTitle(String originalTitle) {
		this.originalTitle = originalTitle;
	}
	
	public Vector<Company> getCompanies() {
		return companies;
	}
	
	public void setCountries(Vector<Country> countries) {
		this.countries = countries;
	}
	
	public Vector<Country> getCountries() {
		return countries;
	}
	
	public void setCompanies(Vector<Company> companies) {
		this.companies = companies;
	}
	
	public String getReleaseDate() {
		return releaseDate;
	}
	
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}
	
	public int getRuntime() {
		return runtime;
	}
	
	public void setRuntime(int runtime) {
		this.runtime = runtime;
	}
	
	public Vector<Language> getLanguages() {
		return languages;
	}
	
	public void setLanguages(Vector<Language> languages) {
		this.languages = languages;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getTagline() {
		return tagline;
	}
	
	public void setTagline(String tagline) {
		this.tagline = tagline;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public double getVoteAverage() {
		return voteAverage;
	}
	
	public void setVoteAverage(double voteAverage) {
		this.voteAverage = voteAverage;
	}
	
	public int getVoteCount() {
		return voteCount;
	}
	
	public void setVoteCount(int voteCount) {
		this.voteCount = voteCount;
	}
	
	public int getRevenue() {
		return revenue;
	}
	
	public void setRevenue(int revenue) {
		this.revenue = revenue;
	}

	@Override
	public boolean parseJSON(JSONObject json) {
		try {
			
			setAdult(json.getBoolean(ADULT));
			setBackdropPath(json.getString(BACKDROP_PATH));
			setId(json.getInt(ID));
			setOriginalTitle(json.getString(ORIGINAL_TITLE));
			setPopularity(json.getDouble(POPULARITY));
			setPosterPath(json.getString(POSTER_PATH));
			setReleaseDate(json.getString(RELEASE_DATE));
			setTitle(json.getString(TITLE));
			setVoteAverage(json.getDouble(AVERAGE));
			setVoteCount(json.getInt(COUNT));
			
			// -- Reduced version
			
			if (json.has(BUDGET)) setBudget(json.getInt(BUDGET));
			if (json.has(IMDB)) setImdbID(json.getString(IMDB));
			if (json.has(OVERVIEW))setOverview(json.getString(OVERVIEW));
			if (json.has(REVENUE)) setRevenue(json.getInt(REVENUE));
			if (json.has(RUNTIME)) setRuntime(json.getInt(RUNTIME));
			if (json.has(STATUS)) setStatus(json.getString(STATUS));
			if (json.has(TAGLINE)) setTagline(json.getString(TAGLINE));
			
			if (json.has(HOMEPAGE)) {
				try {
					setHompage(new URL(json.getString(HOMEPAGE)));
				}
				catch (MalformedURLException e) {
					Log.print(e);
				}
			}
			
			if (json.has(GENRES)) {
				JSONArray genresList = json.getJSONArray(GENRES);
				for (Object obj : genresList) {
				    genres.add(new Genre((JSONObject) obj));
				}
			}
			
			if (json.has(COMPANIES)) {
				JSONArray companiesList = json.getJSONArray(COMPANIES);
				for (Object obj : companiesList) {
				    companies.add(new Company((JSONObject) obj));
				}
			}
			
			if (json.has(COUNTRIES)) {
				JSONArray countriesList = json.getJSONArray(COUNTRIES);
				for (Object obj : countriesList) {
				    countries.add(new Country((JSONObject) obj));
				}
			}
			
			if (json.has(LANGUAGES)) {
				JSONArray langsList = json.getJSONArray(LANGUAGES);
				for (Object obj : langsList) {
				    languages.add(new Language((JSONObject) obj));
				}
			}
			
		} catch (Exception e) {
			Log.print(e);
			return false;
		}
		
		return true;
	}
}
