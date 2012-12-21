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
import mk.tmdb.core.TMDBAPI;
import mk.tmdb.utils.Log;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

public class Movie extends MovieBasic {
	
	//region Private fields

	protected String imdbID;
	protected int budget;
	protected URL homepage;
	protected String overview;
	protected int runtime;
	protected String status;
	protected String tagline;
	protected int revenue;
	protected Set<Genre> genres = Collections.synchronizedSet(new LinkedHashSet<Genre>());
	protected Set<Company> companies = Collections.synchronizedSet(new LinkedHashSet<Company>());
	protected Set<Country> countries = Collections.synchronizedSet(new LinkedHashSet<Country>());
	protected Set<Language> languages = Collections.synchronizedSet(new LinkedHashSet<Language>());
	
	//endregion
	
	public Movie(JSONObject json) {
		super(json);
		parseJSON(json);
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
	
	public int getRevenue() {
		return revenue;
	}
	
	public void setRevenue(int revenue) {
		this.revenue = revenue;
	}

	@Override
	protected boolean parseJSON(JSONObject json) {
			
		setBudget(json.getInt(Constants.BUDGET));
		setImdbID(json.getString(Constants.IMDB));
		setOverview(json.getString(Constants.OVERVIEW));
		setRevenue(json.getInt(Constants.REVENUE));
		setRuntime(json.getInt(Constants.RUNTIME));
		setStatus(json.getString(Constants.STATUS));
		setTagline(json.getString(Constants.TAGLINE));
		
		try {
			setHomepage(new URL(json.getString(Constants.HOMEPAGE)));
		}
		catch (MalformedURLException e) {
			Log.print(e);
		}
		
		JSONArray genresList = json.getJSONArray(Constants.GENRES);
		for (Object obj : genresList) {
		    genres.add(new Genre((JSONObject) obj));
		}

		JSONArray companiesList = json.getJSONArray(Constants.COMPANIES);
		for (Object obj : companiesList) {
		    companies.add(new Company((JSONObject) obj));
		}
		
		JSONArray countriesList = json.getJSONArray(Constants.COUNTRIES);
		for (Object obj : countriesList) {
		    countries.add(new Country((JSONObject) obj));
		}
		
		JSONArray langsList = json.getJSONArray(Constants.LANGUAGES);
		for (Object obj : langsList) {
		    languages.add(new Language((JSONObject) obj));
		}
		
		
		return true;
	}
	
	/*
	public void getNormalVersion() throws MalformedURLException {
		
		if (version != Version.REDUCED) return;
		version = Version.NORMAL;
		
		JSONObject json = TMDBAPI.getHttpJSON(URLCreator.getMovieInfoUrl(id));
		
		originJson = json.toString();
		
		setBudget(json.getInt(Constants.BUDGET));
		setImdbID(json.getString(Constants.IMDB));
		setOverview(json.getString(Constants.OVERVIEW));
		setRevenue(json.getInt(Constants.REVENUE));
		setRuntime(json.getInt(Constants.RUNTIME));
		setStatus(json.getString(Constants.STATUS));
		setTagline(json.getString(Constants.TAGLINE));
		
		try {
			setHomepage(new URL(json.getString(Constants.HOMEPAGE)));
		}
		catch (MalformedURLException e) {
			Log.print(e);
		}
	
		JSONArray genresList = json.getJSONArray(Constants.GENRES);
		for (Object obj : genresList) {
		    genres.add(new Genre((JSONObject) obj));
		}
	
		JSONArray companiesList = json.getJSONArray(Constants.COMPANIES);
		for (Object obj : companiesList) {
		    companies.add(new Company((JSONObject) obj));
		}
	
		JSONArray countriesList = json.getJSONArray(Constants.COUNTRIES);
		for (Object obj : countriesList) {
		    countries.add(new Country((JSONObject) obj));
		}
	
		JSONArray langsList = json.getJSONArray(Constants.LANGUAGES);
		for (Object obj : langsList) {
		    languages.add(new Language((JSONObject) obj));
		}
	}
	
	public void getFullVersion() throws MalformedURLException {
		
		if (version != Version.NORMAL) return;
		
		version = Version.FULL;
		
		JSONObject images = TMDBAPI.getHttpJSON(URLCreator.getMovieImagesUrl(id)); 
		
		JSONArray allPosters = images.getJSONArray(Constants.POSTERS);
		posters.clear();
		for (Object obj : allPosters) {
		    posters.add(new Poster((JSONObject) obj));
		}
		
		JSONArray allBackdrops = images.getJSONArray(Constants.BACKDROPS);
		backdrops.clear();
		for (Object obj : allBackdrops) {
		    backdrops.add(new Backdrop((JSONObject) obj));
		}
		
		JSONObject words = TMDBAPI.getHttpJSON(URLCreator.getMovieKeywordsUrl(id));
		
		JSONArray allkeys = words.getJSONArray(Constants.KEYWORDS);
		keywords.clear();
		for (Object obj : allkeys) {
		    keywords.add(new Keyword((JSONObject) obj));
		}
		
		JSONObject trans = TMDBAPI.getHttpJSON(URLCreator.getMovieTranslationsUrl(id));
		
		JSONArray allTrans = trans.getJSONArray(Constants.TRANSLATIONS);
		translations.clear();
		for (Object obj : allTrans) {
		    translations.add(new Language((JSONObject) obj));
		}
		
		JSONObject videos = TMDBAPI.getHttpJSON(URLCreator.getMovieTrailersUrl(id));
		
		JSONArray utube = videos.getJSONArray(Constants.YOUTUBE);
		trailers.clear();
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
		
		JSONObject castCrew = TMDBAPI.getHttpJSON(URLCreator.getCastInfoUrl(id));
		
		JSONArray castArray = castCrew.getJSONArray(Constants.CAST);
		cast.clear();
		for (Object obj : castArray) {
			cast.add(new Actor((JSONObject) obj));
		}
		
		JSONArray crewArray = castCrew.getJSONArray(Constants.CREW);
		crew.clear();
		for (Object obj : crewArray) {
			crew.add(new CrewMember((JSONObject) obj));
		}
	}
	
	public static Set<Movie> getUpcomingMovies() throws MalformedURLException, 
														InvalidApiKeyException {
		return getUpcomingMovies(1);
	}

	public static Set<Movie> getUpcomingMovies(int page) throws MalformedURLException, 
																InvalidApiKeyException {
		Set<Movie> upcoming = new LinkedHashSet<Movie>();

		JSONObject result = TMDBAPI.getHttpJSON(URLCreator.getUpcomingMoviesListUrl(page));
		JSONArray array = result.getJSONArray(Constants.RESULT); 
		for (Object obj : array) {
			upcoming.add(new Movie((JSONObject) obj));
		}

		return upcoming;
	}

	public static Movie getLatestMovie() throws MalformedURLException, 
												InvalidApiKeyException {
		return new Movie(TMDBAPI.getHttpJSON(URLCreator.getLatestMovieUrl()));
	}

	public static Set<Movie> getInTheatreMovies() throws MalformedURLException, 
														 InvalidApiKeyException {
		return getInTheatreMovies(1);
	}

	public static Set<Movie> getInTheatreMovies(int page) throws MalformedURLException, 
																 InvalidApiKeyException {
		Set<Movie> inTheatre = new LinkedHashSet<Movie>();

		JSONObject result = TMDBAPI.getHttpJSON(URLCreator.getInTheatreMoviesUrl(page));
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

		JSONObject result = TMDBAPI.getHttpJSON(URLCreator.getPopularMoviesUrl(page));
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

		JSONObject result = TMDBAPI.getHttpJSON(URLCreator.getTopRatedMoviesUrl(page));
		JSONArray array = result.getJSONArray(Constants.RESULT); 
		for (Object obj : array) {
			top.add(new Movie((JSONObject) obj));
		}

		return top;
	}

	public static void rateMovie(String sessionID, boolean guest, int movieID, int value) throws MalformedURLException, 
																								 InvalidApiKeyException {
		TMDBAPI.getHttpJSON(URLCreator.setMovieRateUrl(sessionID, movieID, value, guest));
	}
	*/
}
