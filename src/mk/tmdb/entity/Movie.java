package mk.tmdb.entity;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import mk.tmdb.core.URLCreator;
import mk.tmdb.core.WebRequest;
import mk.tmdb.utils.Log;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

public class Movie implements IEntity {
	
	//region Static fields
	
	private static final int JSON_SIZE = 23;
	private static final String ADULT = "adult";
	private static final String BACKDROP_PATH = "backdrop_path";
	private static final String BACKDROPS = "backdrops";
	private static final String BUDGET = "budget";
	private static final String GENRES = "genres";
	private static final String HOMEPAGE = "homepage";
	private static final String ID = "id";
	private static final String IMDB = "imdb_id";
	private static final String ORIGINAL_TITLE = "original_title";
	private static final String OVERVIEW = "overview";
	private static final String POPULARITY = "popularity";
	private static final String POSTER_PATH = "poster_path";
	private static final String POSTERS = "posters";
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
	private static final String KEYWORDS = "keywords";
	private static final String TRANSLATIONS = "translations";
	private static final String YOUTUBE = "youtube";
	private static final String QUICKTIME = "quicktime";
	//endregion
	
	//region Private fields

	private String originJson;
	private boolean adult;
	private Backdrop backdrop;
	private Poster poster;
	private int id;
	private String imdbID;
	private int budget;
	private Set<Genre> genres = Collections.synchronizedSet(new LinkedHashSet<Genre>());
	private URL homepage;
	private String overview;
	private double popularity;
	private String originalTitle;
	private Set<Company> companies = Collections.synchronizedSet(new LinkedHashSet<Company>());
	private Set<Country> countries = Collections.synchronizedSet(new LinkedHashSet<Country>());
	private Date releaseDate;
	private int runtime;
	private Set<Language> languages = Collections.synchronizedSet(new LinkedHashSet<Language>());
	private String status;
	private String tagline;
	private String title;
	private double voteAverage;
	private int voteCount;
	private int revenue;
	private Set<Backdrop> backdrops = Collections.synchronizedSet(new LinkedHashSet<Backdrop>());
	private Set<Poster> posters = Collections.synchronizedSet(new LinkedHashSet<Poster>());
	private Set<Keyword> keywords = Collections.synchronizedSet(new LinkedHashSet<Keyword>());
	private Set<Language> translations = Collections.synchronizedSet(new LinkedHashSet<Language>());
	private Set<Trailer> trailers = Collections.synchronizedSet(new LinkedHashSet<Trailer>());
	private boolean reduced = false;
	
	//endregion
	
	public Movie(String jsonString) {
		this((JSONObject) JSONSerializer.toJSON(jsonString));
		this.originJson = jsonString;
	}
	
	public Movie(JSONObject json) {
		this.originJson= json.toString(); 
		parseJSON(json);
	}
	
	public String getOriginJson() {
		return originJson;
	}
	
	public boolean isAdult() {
		return adult;
	}
	
	public void setAdult(boolean adult) {
		this.adult = adult;
	}
	
	public Backdrop getBackdrop() {
		return backdrop;
	}
	
	public void setBackdrop(String backdropPath) {
		this.backdrop = new Backdrop(backdropPath);
	}
	
	public Poster getPoster() {
		return poster;
	}
	
	public void setPoster(String posterPath) {
		this.poster = new Poster(posterPath);
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
	
	public Set<Company> getCompanies() {
		return companies;
	}
	
	public void setCountries(Set<Country> countries) {
		this.countries.clear();
		this.countries.addAll(countries);
	}
	
	public Set<Country> getCountries() {
		return countries;
	}
	
	public void setCompanies(Set<Company> companies) {
		this.companies.clear();
		this.companies.addAll(companies);
	}
	
	public Date getReleaseDate() {
		return releaseDate;
	}
	
	public void setReleaseDate(String releaseDate) {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			this.releaseDate = (Date)formatter.parse(releaseDate);
		} catch (ParseException e) {
			Log.print(e);
		}
	}
	
	public int getRuntime() {
		return runtime;
	}
	
	public void setRuntime(int runtime) {
		this.runtime = runtime;
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

	public Set<Backdrop> getBackdrops() {
		return backdrops;
	}

	public void setBackdrops(Set<Backdrop> backdrops) {
		this.backdrops = backdrops;
	}

	public Set<Poster> getPosters() {
		return posters;
	}

	public void setPosters(Set<Poster> posters) {
		this.posters = posters;
	}
	
	public boolean isReduced() {
		return reduced;
	}
	
	public void setReduced(boolean reduced) {
		this.reduced = reduced;
	}

	public Set<Language> getTranslations() {
		return translations;
	}

	public void setTranslations(Set<Language> translations) {
		this.translations = translations;
	}

	@Override
	public boolean parseJSON(JSONObject json) {
		try {
			
			setAdult(json.getBoolean(ADULT));
			setBackdrop(json.getString(BACKDROP_PATH));
			setId(json.getInt(ID));
			setOriginalTitle(json.getString(ORIGINAL_TITLE));
			setPopularity(json.getDouble(POPULARITY));
			setPoster(json.getString(POSTER_PATH));
			setReleaseDate(json.getString(RELEASE_DATE));
			setTitle(json.getString(TITLE));
			setVoteAverage(json.getDouble(AVERAGE));
			setVoteCount(json.getInt(COUNT));
			
			// -- Reduced version
			
			setReduced(json.size() < JSON_SIZE); 
			
			if (json.has(BUDGET)) setBudget(json.getInt(BUDGET));
			if (json.has(IMDB)) setImdbID(json.getString(IMDB));
			if (json.has(OVERVIEW))setOverview(json.getString(OVERVIEW));
			if (json.has(REVENUE)) setRevenue(json.getInt(REVENUE));
			if (json.has(RUNTIME)) setRuntime(json.getInt(RUNTIME));
			if (json.has(STATUS)) setStatus(json.getString(STATUS));
			if (json.has(TAGLINE)) setTagline(json.getString(TAGLINE));
			
			if (json.has(HOMEPAGE)) {
				try {
					setHomepage(new URL(json.getString(HOMEPAGE)));
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
			
			if (!reduced) {
				JSONObject images = WebRequest.getHttpJSON(URLCreator.getMovieImagesUrl(id)); 
				
				JSONArray allPosters = images.getJSONArray(POSTERS);
				for (Object obj : allPosters) {
				    posters.add(new Poster((JSONObject) obj));
				}
				
				JSONArray allBackdrops = images.getJSONArray(BACKDROPS);
				for (Object obj : allBackdrops) {
				    posters.add(new Poster((JSONObject) obj));
				}
				
				JSONObject words = WebRequest.getHttpJSON(URLCreator.getMovieKeywordsUrl(id));
				
				JSONArray allkeys = words.getJSONArray(KEYWORDS);
				for (Object obj : allkeys) {
				    keywords.add(new Keyword((JSONObject) obj));
				}
				
				JSONObject trans = WebRequest.getHttpJSON(URLCreator.getMovieTranslationsUrl(id));
				
				JSONArray allTrans = trans.getJSONArray(TRANSLATIONS);
				for (Object obj : allTrans) {
				    translations.add(new Language((JSONObject) obj));
				}
				
				JSONObject videos = WebRequest.getHttpJSON(URLCreator.getMovieTrailersUrl(id));
				
				JSONArray utube = videos.getJSONArray(YOUTUBE); 
				for (Object obj : utube) {
				    trailers.add(new YoutubeTrailer((JSONObject) obj));
				}
				
				JSONArray quick = videos.getJSONArray(QUICKTIME);
				
				for (Object obj : quick) {
					String name = ((JSONObject) obj).getString("name");
					JSONArray quicks = ((JSONObject) obj).getJSONArray("sources");
					
					for (Object jobj : quicks) {
						trailers.add(new QuicktimeTrailer((JSONObject) jobj, name));
					}
				}
			}
		
		} catch (Exception e) {
			Log.print(e);
			return false;
		}
		
		return true;
	}
	
	
}
