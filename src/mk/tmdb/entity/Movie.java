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

import mk.tmdb.core.Constants;
import mk.tmdb.core.URLCreator;
import mk.tmdb.core.WebRequest;
import mk.tmdb.exception.InvalidApiKeyException;
import mk.tmdb.utils.Log;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

public class Movie implements IEntity {
	
	private static final int JSON_SIZE = 23;
	
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
			
			setAdult(json.getBoolean(Constants.ADULT));
			setBackdrop(json.getString(Constants.BACKDROP_PATH));
			setId(json.getInt(Constants.ID));
			setOriginalTitle(json.getString(Constants.ORIGINAL_TITLE));
			setPopularity(json.getDouble(Constants.POPULARITY));
			setPoster(json.getString(Constants.POSTER_PATH));
			setReleaseDate(json.getString(Constants.RELEASE_DATE));
			setTitle(json.getString(Constants.TITLE));
			setVoteAverage(json.getDouble(Constants.AVERAGE));
			setVoteCount(json.getInt(Constants.COUNT));
			
			// -- Reduced version
			
			setReduced(json.size() < JSON_SIZE); 
			
			if (json.has(Constants.BUDGET)) setBudget(json.getInt(Constants.BUDGET));
			if (json.has(Constants.IMDB)) setImdbID(json.getString(Constants.IMDB));
			if (json.has(Constants.OVERVIEW))setOverview(json.getString(Constants.OVERVIEW));
			if (json.has(Constants.REVENUE)) setRevenue(json.getInt(Constants.REVENUE));
			if (json.has(Constants.RUNTIME)) setRuntime(json.getInt(Constants.RUNTIME));
			if (json.has(Constants.STATUS)) setStatus(json.getString(Constants.STATUS));
			if (json.has(Constants.TAGLINE)) setTagline(json.getString(Constants.TAGLINE));
			
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
				    companies.add(new Company((JSONObject) obj));
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
			
			if (!reduced) {
				JSONObject images = WebRequest.getHttpJSON(URLCreator.getMovieImagesUrl(id)); 
				
				JSONArray allPosters = images.getJSONArray(Constants.POSTERS);
				for (Object obj : allPosters) {
				    posters.add(new Poster((JSONObject) obj));
				}
				
				JSONArray allBackdrops = images.getJSONArray(Constants.BACKDROPS);
				for (Object obj : allBackdrops) {
				    posters.add(new Poster((JSONObject) obj));
				}
				
				JSONObject words = WebRequest.getHttpJSON(URLCreator.getMovieKeywordsUrl(id));
				
				JSONArray allkeys = words.getJSONArray(Constants.KEYWORDS);
				for (Object obj : allkeys) {
				    keywords.add(new Keyword((JSONObject) obj));
				}
				
				JSONObject trans = WebRequest.getHttpJSON(URLCreator.getMovieTranslationsUrl(id));
				
				JSONArray allTrans = trans.getJSONArray(Constants.TRANSLATIONS);
				for (Object obj : allTrans) {
				    translations.add(new Language((JSONObject) obj));
				}
				
				JSONObject videos = WebRequest.getHttpJSON(URLCreator.getMovieTrailersUrl(id));
				
				JSONArray utube = videos.getJSONArray(Constants.YOUTUBE); 
				for (Object obj : utube) {
				    trailers.add(new YoutubeTrailer((JSONObject) obj));
				}
				
				JSONArray quick = videos.getJSONArray(Constants.QUICKTIME);
				
				for (Object obj : quick) {
					String name = ((JSONObject) obj).getString(Constants.NAME);
					JSONArray quicks = ((JSONObject) obj).getJSONArray(Constants.SOURCES);
					
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
	
	public static Set<Movie> getUpcomingMovies() throws MalformedURLException, 
														InvalidApiKeyException {
		return getUpcomingMovies(1);
	}

	public static Set<Movie> getUpcomingMovies(int page) throws MalformedURLException, 
																InvalidApiKeyException {
		Set<Movie> upcoming = new LinkedHashSet<Movie>();

		JSONObject result = WebRequest.getHttpJSON(URLCreator.getUpcomingMoviesListUrl(page));
		JSONArray array = result.getJSONArray(Constants.RESULT); 
		for (Object obj : array) {
			upcoming.add(new Movie((JSONObject) obj));
		}

		return upcoming;
	}

	public static Movie getLatestMovie() throws MalformedURLException, 
												InvalidApiKeyException {
		return new Movie(WebRequest.getHttpJSON(URLCreator.getLatestMovieUrl()));
	}

	public static Set<Movie> getInTheatreMovies() throws MalformedURLException, 
														 InvalidApiKeyException {
		return getInTheatreMovies(1);
	}

	public static Set<Movie> getInTheatreMovies(int page) throws MalformedURLException, 
																 InvalidApiKeyException {
		Set<Movie> inTheatre = new LinkedHashSet<Movie>();

		JSONObject result = WebRequest.getHttpJSON(URLCreator.getInTheatreMoviesUrl(page));
		JSONArray array = result.getJSONArray(Constants.RESULT); 
		for (Object obj : array) {
			inTheatre.add(new Movie((JSONObject) obj));
		}

		return inTheatre;
	}

	public static Set<Movie> getPopularMovies() throws MalformedURLException,
													   InvalidApiKeyException {
		return getPopularMovies(1);
	}

	public static Set<Movie> getPopularMovies(int page) throws MalformedURLException, 
															   InvalidApiKeyException {
		Set<Movie> popular = new LinkedHashSet<Movie>();

		JSONObject result = WebRequest.getHttpJSON(URLCreator.getPopularMoviesUrl(page));
		JSONArray array = result.getJSONArray(Constants.RESULT); 
		for (Object obj : array) {
			popular.add(new Movie((JSONObject) obj));
		}

		return popular;
	}

	public static Set<Movie> getTopRatedMovies() throws MalformedURLException,
														InvalidApiKeyException {
		return getTopRatedMovies(1);
	}

	public static Set<Movie> getTopRatedMovies(int page) throws MalformedURLException, 
																InvalidApiKeyException {
		Set<Movie> top = new LinkedHashSet<Movie>();

		JSONObject result = WebRequest.getHttpJSON(URLCreator.getTopRatedMoviesUrl(page));
		JSONArray array = result.getJSONArray(Constants.RESULT); 
		for (Object obj : array) {
			top.add(new Movie((JSONObject) obj));
		}

		return top;
	}

	public static void rateMovie(String sessionID, boolean guest, int movieID, int value) throws MalformedURLException, 
																								 InvalidApiKeyException {
		WebRequest.getHttpJSON(URLCreator.setMovieRateUrl(sessionID, movieID, value, guest));
	}
}
