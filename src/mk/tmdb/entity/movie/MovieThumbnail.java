package mk.tmdb.entity.movie;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import mk.tmdb.core.Constants;
import mk.tmdb.core.TMDbAPI;
import mk.tmdb.entity.Account;
import mk.tmdb.entity.Country;
import mk.tmdb.entity.Entity;
import mk.tmdb.entity.Keyword;
import mk.tmdb.entity.Language;
import mk.tmdb.entity.image.Backdrop;
import mk.tmdb.entity.image.Poster;
import mk.tmdb.entity.person.MovieCast;
import mk.tmdb.entity.person.MovieCrew;
import mk.tmdb.entity.trailer.QuicktimeTrailer;
import mk.tmdb.entity.trailer.Trailer;
import mk.tmdb.entity.trailer.YoutubeTrailer;
import mk.tmdb.exception.ResponseException;
import mk.tmdb.utils.Log;
import mk.tmdb.utils.Pair;
import mk.tmdb.utils.ResponseArray;
import mk.tmdb.utils.ResponseObject;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class MovieThumbnail extends Entity {

	//region Fields
	
	protected Boolean adult = null;
	protected Integer id;
	protected String originalTitle = null;
	protected String title;
	protected String posterPath;
	protected Date releaseDate;
	
	//endregion
	
	public MovieThumbnail(JSONObject json) {
		super(json);
		parseJSON(json);
	}
	
	public MovieThumbnail(MovieThumbnail movie) {
		this(movie.getOriginJSON());
	}
	
	//region Getters/Setters
	
	public boolean isAdult() {
		return adult;
	}

	public void setAdult(boolean adult) {
		this.adult = adult;
	}
	
	public boolean isAdultSet() {
		return adult != null;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOriginalTitle() {
		return originalTitle;
	}

	public void setOriginalTitle(String originalTitle) {
		this.originalTitle = originalTitle;
	}
	
	public boolean isOriginalTitleSet() {
		return originalTitle != null;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPosterPath() {
		return posterPath;
	}

	public void setPosterPath(String posterPath) {
		this.posterPath = posterPath;
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
	
	//endregion
	
	@Override
	protected boolean parseJSON(JSONObject json) {
		
		if (json.has(Constants.ADULT)) setAdult(json.getBoolean(Constants.ADULT));
		setId(json.getInt(Constants.ID));
		if (json.has(Constants.ORIGINAL_TITLE)) setOriginalTitle(json.getString(Constants.ORIGINAL_TITLE));
		setPosterPath(json.getString(Constants.POSTER_PATH));
		setReleaseDate(json.getString(Constants.RELEASE_DATE));
		setTitle(json.getString(Constants.TITLE));
		
		return true;
	}
	
	public boolean rateThisMovie(Account account, float value) {
		return rateMovie(account.getSessionID(), false, id, value);
	}
	
	public boolean rateThisMovieAsGuest(String sessionID, float value) {
		return rateMovie(sessionID, true, id, value);
	}
	
	//region Static methods
	
	public static boolean rateMovie(Account account, int movieID, float value) {
		ResponseObject response = TMDbAPI.setMovieRate(account.getSessionID(), false, movieID, value);
		return !response.hasError();
	}
	
	public static boolean rateMovie(String sessionID, boolean guest, int movieID, float value) {
		ResponseObject response = TMDbAPI.setMovieRate(sessionID, guest, movieID, value);
		return !response.hasError();
	}
	
	public static Set<MovieReduced> getInTheatreMovies() throws ResponseException {
		return getInTheatreMovies(1);
	}

	public static Set<MovieReduced> getInTheatreMovies(int page) throws ResponseException {
		
		ResponseArray response = TMDbAPI.getInTheatresMovies(page);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			
			Set<MovieReduced> inTheatre = new LinkedHashSet<MovieReduced>();
			Set<JSONObject> array = response.getData();
			for (JSONObject json : array) {
				inTheatre.add(new MovieReduced(json));
			}

			return inTheatre;
		}
	}
	
	public static Set<MovieReduced> getAllInTheatreMovies() throws ResponseException {
		
		ResponseArray response = TMDbAPI.getAllInTheatresMovies();
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			
			Set<MovieReduced> inTheatre = new LinkedHashSet<MovieReduced>();
			Set<JSONObject> array = response.getData();
			for (JSONObject json : array) {
				inTheatre.add(new MovieReduced(json));
			}

			return inTheatre;
		}
	}
	
	public static Set<MovieReduced> getUpcomingMovies() throws ResponseException  {
		return getUpcomingMovies(1);
	}

	public static Set<MovieReduced> getUpcomingMovies(int page) throws ResponseException {

		ResponseArray response = TMDbAPI.getUpcomingMovies(page);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			
			Set<MovieReduced> upcoming = new LinkedHashSet<MovieReduced>();
			Set<JSONObject> array = response.getData();
			for (JSONObject json : array) {
				upcoming.add(new MovieReduced(json));
			}

			return upcoming;
		}
	}
	
	public static Set<MovieReduced> getAllUpcomingMovies() throws ResponseException {

		ResponseArray response = TMDbAPI.getAllUpcomingMovies();
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			
			Set<MovieReduced> upcoming = new LinkedHashSet<MovieReduced>();
			Set<JSONObject> array = response.getData();
			for (JSONObject json : array) {
				upcoming.add(new MovieReduced(json));
			}

			return upcoming;
		}
	}

	public static Set<MovieReduced> getPopularMovies() throws ResponseException  {
		return getPopularMovies(1);
	}

	public static Set<MovieReduced> getPopularMovies(int page) throws ResponseException {

		ResponseArray response = TMDbAPI.getPopularMovies(page);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			
			Set<MovieReduced> popular = new LinkedHashSet<MovieReduced>();
			Set<JSONObject> array = response.getData();
			for (JSONObject json : array) {
				popular.add(new MovieReduced(json));
			}

			return popular;
		}
	}
	
	public static Set<MovieReduced> getAllPopularMovies() throws ResponseException {

		ResponseArray response = TMDbAPI.getAllPopularMovies();
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			
			Set<MovieReduced> popular = new LinkedHashSet<MovieReduced>();
			Set<JSONObject> array = response.getData();
			for (JSONObject json : array) {
				popular.add(new MovieReduced(json));
			}

			return popular;
		}
	}

	public static Set<MovieReduced> getTopRatedMovies() throws ResponseException {
		return getTopRatedMovies(1);
	}

	public static Set<MovieReduced> getTopRatedMovies(int page) throws ResponseException {
		
		ResponseArray response = TMDbAPI.getTopRatedMovies(page);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			
			Set<MovieReduced> tops = new LinkedHashSet<MovieReduced>();
			Set<JSONObject> array = response.getData();
			for (JSONObject json : array) {
				tops.add(new MovieReduced(json));
			}

			return tops;
		}
	}
	
	public static Set<MovieReduced> getAllTopRatedMovies() throws ResponseException {
		
		ResponseArray response = TMDbAPI.getAllTopRatedMovies();
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			
			Set<MovieReduced> tops = new LinkedHashSet<MovieReduced>();
			Set<JSONObject> array = response.getData();
			for (JSONObject json : array) {
				tops.add(new MovieReduced(json));
			}

			return tops;
		}
	}
	
	public static Movie getInformation(int movieID) throws ResponseException {
		
		ResponseObject response = TMDbAPI.getMovieInformation(movieID);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			return new Movie(response.getData());
		}
	}
	
	public static Set<Keyword> getKeywords(int movieID) throws ResponseException {
		ResponseObject response = TMDbAPI.getMovieKeywords(movieID);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			
			JSONArray allkeys = response.getData().getJSONArray(Constants.KEYWORDS);
			Set<Keyword> keywords = new LinkedHashSet<Keyword>();
			for (Object obj : allkeys) {
			    keywords.add(new Keyword((JSONObject) obj));
			}
			
			return keywords;
		}
	}
	
	public static Set<Language> getTranslations(int movieID) throws ResponseException {
		ResponseObject response = TMDbAPI.getMovieTranslations(movieID);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			
			JSONArray allTrans = response.getData().getJSONArray(Constants.TRANSLATIONS);
			Set<Language> translations = new LinkedHashSet<Language>();
			for (Object obj : allTrans) {
			    translations.add(new Language((JSONObject) obj));
			}
			
			return translations;
		}
	}
	
	public static Set<Trailer> getTrailers(int movieID) throws ResponseException {
		ResponseObject response = TMDbAPI.getMovieTrailers(movieID);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
		
			JSONArray utube = response.getData().getJSONArray(Constants.YOUTUBE);
			Set<Trailer> trailers = new LinkedHashSet<Trailer>();
			for (Object obj : utube) {
			    trailers.add(new YoutubeTrailer((JSONObject) obj));
			}
			
			JSONArray quick = response.getData().getJSONArray(Constants.QUICKTIME);
			
			for (Object obj : quick) {
				String name = ((JSONObject) obj).getString(Constants.NAME);
				JSONArray quicks = ((JSONObject) obj).getJSONArray(Constants.SOURCES);
				
				for (Object jobj : quicks) {
					trailers.add(new QuicktimeTrailer((JSONObject) jobj, name));
				}
			}	
			
			return trailers;
		}
	}
	
	public static Set<MovieCast> getCastInformation(int movieID) throws ResponseException {
		ResponseObject response = TMDbAPI.getCastInformation(movieID);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
		
			JSONArray castArray = response.getData().getJSONArray(Constants.CAST);
			Set<MovieCast> cast = new LinkedHashSet<MovieCast>();
			for (Object obj : castArray) {
				cast.add(new MovieCast((JSONObject) obj, movieID));
			}
			
			return cast;
		}
	}

	public static Set<MovieCrew> getCrewInformation(int movieID) throws ResponseException {
		ResponseObject response = TMDbAPI.getCastInformation(movieID);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
		
			JSONArray crewArray = response.getData().getJSONArray(Constants.CREW);
			Set<MovieCrew> crew = new LinkedHashSet<MovieCrew>();
			for (Object obj : crewArray) {
				crew.add(new MovieCrew((JSONObject) obj, movieID));
			}
			
			return crew;
		}
	}
	
	public static Set<Poster> getPosters(int movieID) throws ResponseException {
		
		ResponseObject response = TMDbAPI.getMovieImages(movieID);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			
			JSONArray allPosters = response.getData().getJSONArray(Constants.POSTERS);
			
			Set<Poster> images = new LinkedHashSet<Poster>();
			for (Object obj : allPosters) {
			    images.add(new Poster((JSONObject) obj));
			}
			
			return images;
		}
	}
	
	public static Set<Backdrop> getBackdrops(int movieID) throws ResponseException {
		
		ResponseObject response = TMDbAPI.getMovieImages(movieID);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			
			Set<Backdrop> images = new LinkedHashSet<Backdrop>();
			
			JSONArray allBackdrops = response.getData().getJSONArray(Constants.BACKDROPS);
			for (Object obj : allBackdrops) {
			    images.add(new Backdrop((JSONObject) obj));
			}
			
			return images;
		}
	}
	
	public static Set<Integer> getChanged() throws ResponseException {
		
		ResponseArray response = TMDbAPI.getChangedMovies();
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			
			Set<JSONObject> array = response.getData();
			Set<Integer> ids = new LinkedHashSet<Integer>();
			for (JSONObject json : array) {
			    ids.add(json.getInt(Constants.ID));
			}
			
			return ids;
		}
	}
	
	public static Set<Integer> getChanged(Date start, Date end) throws ResponseException {
		
		ResponseArray response = TMDbAPI.getChangedMovies(start, end);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			
			Set<JSONObject> array = response.getData();
			Set<Integer> ids = new LinkedHashSet<Integer>();
			for (JSONObject json : array) {
			    ids.add(json.getInt(Constants.ID));
			}
			
			return ids;
		}
	}
	
	public static Set<Integer> getChanged(String start, String end) throws ResponseException {
		
		ResponseArray response = TMDbAPI.getChangedMovies(start, end);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			
			Set<JSONObject> array = response.getData();
			Set<Integer> ids = new LinkedHashSet<Integer>();
			for (JSONObject json : array) {
			    ids.add(json.getInt(Constants.ID));
			}
			
			return ids;
		}
	}
	
	public static Set<Integer> getChanged(int page) throws ResponseException {
		
		ResponseArray response = TMDbAPI.getChangedMovies(page);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			
			Set<JSONObject> array = response.getData();
			Set<Integer> ids = new LinkedHashSet<Integer>();
			for (JSONObject json : array) {
			    ids.add(json.getInt(Constants.ID));
			}
			
			return ids;
		}
	}
	
	public static Set<Integer> getChanged(Date start, Date end, int page) throws ResponseException {
		
		ResponseArray response = TMDbAPI.getChangedMovies(start, end, page);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			
			Set<JSONObject> array = response.getData();
			Set<Integer> ids = new LinkedHashSet<Integer>();
			for (JSONObject json : array) {
			    ids.add(json.getInt(Constants.ID));
			}
			
			return ids;
		}
	}
	
	public static Set<Integer> getChanged(String start, String end, int page) throws ResponseException {
		
		ResponseArray response = TMDbAPI.getChangedMovies(start, end, page);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			
			Set<JSONObject> array = response.getData();
			Set<Integer> ids = new LinkedHashSet<Integer>();
			for (JSONObject json : array) {
			    ids.add(json.getInt(Constants.ID));
			}
			
			return ids;
		}
	}
	
	public static Set<Integer> getAllChanged() throws ResponseException {
		
		ResponseArray response = TMDbAPI.getAllChangedMovies();
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			
			Set<JSONObject> array = response.getData();
			Set<Integer> ids = new LinkedHashSet<Integer>();
			for (JSONObject json : array) {
			    ids.add(json.getInt(Constants.ID));
			}
			
			return ids;
		}
	}
	
	public static Set<Integer> getAllChanged(Date start, Date end) throws ResponseException {
		
		ResponseArray response = TMDbAPI.getAllChangedMovies(start, end);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			
			Set<JSONObject> array = response.getData();
			Set<Integer> ids = new LinkedHashSet<Integer>();
			for (JSONObject json : array) {
			    ids.add(json.getInt(Constants.ID));
			}
			
			return ids;
		}
	}
	
	public static Set<Integer> getAllChanged(String start, String end) throws ResponseException {
		
		ResponseArray response = TMDbAPI.getAllChangedMovies(start, end);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			
			Set<JSONObject> array = response.getData();
			Set<Integer> ids = new LinkedHashSet<Integer>();
			for (JSONObject json : array) {
			    ids.add(json.getInt(Constants.ID));
			}
			
			return ids;
		}
	}
	
	public static Set<Pair<Country, String>> getAlternativeTitles(int movieID) throws ResponseException {
		
		ResponseObject response = TMDbAPI.getAlternativeMovieTitles(movieID);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			
			JSONArray array = response.getData().getJSONArray(Constants.TITLES);
			Set<Pair<Country, String>> titles = new LinkedHashSet<Pair<Country, String>>();
			for (Object obj : array) {
				JSONObject json = (JSONObject) obj;
			    titles.add(new Pair<Country, String>(new Country(json), json.getString(Constants.TITLE)));
			}
			
			return titles;
		}
	}
	
	public static Set<Pair<Country, Date>> getReleaseDates(int movieID) throws ResponseException {
		
		// Certification skipped
		
		ResponseObject response = TMDbAPI.getMovieReleases(movieID);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			
			JSONArray array = response.getData().getJSONArray(Constants.COUNTRIES);
			Set<Pair<Country, Date>> dates = new LinkedHashSet<Pair<Country, Date>>();
			for (Object obj : array) {
				JSONObject json = (JSONObject) obj;
				DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			    try {
					dates.add(new Pair<Country, Date>(new Country(json), (Date)formatter.parse(json.getString(Constants.RELEASE_DATE))));
				} catch (ParseException e) {
					Log.print(e);
				}
			}
			
			return dates;
		}
	}
	
	public static Set<MovieReduced> getSimilarMovies(int movieID) throws ResponseException {
		return getSimilarMovies(movieID, 1);
	}
	
	public static Set<MovieReduced> getSimilarMovies(int movieID, int page) throws ResponseException {
		
		ResponseArray response = TMDbAPI.getSimilarMovies(movieID, page);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			
			Set<MovieReduced> sims = new LinkedHashSet<MovieReduced>();
			Set<JSONObject> array = response.getData();
			for (JSONObject json : array) {
				sims.add(new MovieReduced(json));
			}

			return sims;
		}
	}
	
	public static Set<MovieReduced> getAllSimilarMovies(int movieID) throws ResponseException {
		
		ResponseArray response = TMDbAPI.getAllSimilarMovies(movieID);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			
			Set<MovieReduced> sims = new LinkedHashSet<MovieReduced>();
			Set<JSONObject> array = response.getData();
			for (JSONObject json : array) {
				sims.add(new MovieReduced(json));
			}

			return sims;
		}
	}
	
	public static Movie getLatestMovie() throws ResponseException {
		
		ResponseObject response = TMDbAPI.getLatestMovie();
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			return new Movie(response.getData());
		}
	}
	
	//endregion

	//region Search methods
	
	public static Set<MovieReduced> searchByTitle(String movieTitle) throws ResponseException {
		
		ResponseArray response = TMDbAPI.searchMovieByTitle(movieTitle);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			Set<JSONObject> array = response.getData();
			Set<MovieReduced> movies = new LinkedHashSet<MovieReduced>();
			for(JSONObject json : array) {
				movies.add(new MovieReduced(json));
			}
			
			return movies;
		}
	}
	
	public static Set<MovieReduced> searchByTitle(String movieTitle, int page) throws ResponseException {
		
		ResponseArray response = TMDbAPI.searchMovieByTitle(movieTitle, page);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			Set<JSONObject> array = response.getData();
			Set<MovieReduced> movies = new LinkedHashSet<MovieReduced>();
			for(JSONObject json : array) {
				movies.add(new MovieReduced(json));
			}
			
			return movies;
		}
	}
	
	public static Set<MovieReduced> searchByTitle(String movieTitle, boolean adult) throws ResponseException {
		
		ResponseArray response = TMDbAPI.searchMovieByTitle(movieTitle, adult);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			Set<JSONObject> array = response.getData();
			Set<MovieReduced> movies = new LinkedHashSet<MovieReduced>();
			for(JSONObject json : array) {
				movies.add(new MovieReduced(json));
			}
			
			return movies;
		}
	}
	
	public static Set<MovieReduced> searchByTitle(String movieTitle, boolean adult, int page) throws ResponseException {
		
		ResponseArray response = TMDbAPI.searchMovieByTitle(movieTitle, adult, page);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			Set<JSONObject> array = response.getData();
			Set<MovieReduced> movies = new LinkedHashSet<MovieReduced>();
			for(JSONObject json : array) {
				movies.add(new MovieReduced(json));
			}
			
			return movies;
		}
	}
	
	public static Set<MovieReduced> fullSearchByTitle(String movieTitle) throws ResponseException {
		
		ResponseArray response = TMDbAPI.fullSearchMovieByTitle(movieTitle);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			Set<JSONObject> array = response.getData();
			Set<MovieReduced> movies = new LinkedHashSet<MovieReduced>();
			for(JSONObject json : array) {
				movies.add(new MovieReduced(json));
			}
			
			return movies;
		}
	}
	
	public static Set<MovieReduced> fullSearchByTitle(String movieTitle, boolean adult) throws ResponseException {
		
		ResponseArray response = TMDbAPI.fullSearchMovieByTitle(movieTitle, adult);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			Set<JSONObject> array = response.getData();
			Set<MovieReduced> movies = new LinkedHashSet<MovieReduced>();
			for(JSONObject json : array) {
				movies.add(new MovieReduced(json));
			}
			
			return movies;
		}
	}
	
	public static Set<MovieReduced> searchByTitleAndYear(String movieTitle, int year) throws ResponseException {
		
		ResponseArray response = TMDbAPI.searchMovieByTitleAndYear(movieTitle, year);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			Set<JSONObject> array = response.getData();
			Set<MovieReduced> movies = new LinkedHashSet<MovieReduced>();
			for(JSONObject json : array) {
				movies.add(new MovieReduced(json));
			}
			
			return movies;
		}
	}
	
	public static Set<MovieReduced> searchByTitleAndYear(String movieTitle, int year, int page) throws ResponseException {
		
		ResponseArray response = TMDbAPI.searchMovieByTitleAndYear(movieTitle, year, page);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			Set<JSONObject> array = response.getData();
			Set<MovieReduced> movies = new LinkedHashSet<MovieReduced>();
			for(JSONObject json : array) {
				movies.add(new MovieReduced(json));
			}
			
			return movies;
		}
	}
	
	public static Set<MovieReduced> searchByTitleAndYear(String movieTitle, int year, boolean adult) throws ResponseException {
		
		ResponseArray response = TMDbAPI.searchMovieByTitleAndYear(movieTitle, year, adult);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			Set<JSONObject> array = response.getData();
			Set<MovieReduced> movies = new LinkedHashSet<MovieReduced>();
			for(JSONObject json : array) {
				movies.add(new MovieReduced(json));
			}
			
			return movies;
		}
	}
	
	public static Set<MovieReduced> searchByTitleAndYear(String movieTitle, int year, boolean adult, int page) throws ResponseException {
		
		ResponseArray response = TMDbAPI.searchMovieByTitleAndYear(movieTitle, year, adult, page);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			Set<JSONObject> array = response.getData();
			Set<MovieReduced> movies = new LinkedHashSet<MovieReduced>();
			for(JSONObject json : array) {
				movies.add(new MovieReduced(json));
			}
			
			return movies;
		}
	}
	
	public static Set<MovieReduced> fullSearchByTitleAndYear(String movieTitle, int year) throws ResponseException {
		
		ResponseArray response = TMDbAPI.fullSearchMovieByTitleAndYear(movieTitle, year);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			Set<JSONObject> array = response.getData();
			Set<MovieReduced> movies = new LinkedHashSet<MovieReduced>();
			for(JSONObject json : array) {
				movies.add(new MovieReduced(json));
			}
			
			return movies;
		}
	}
	
	public static Set<MovieReduced> fullSearchByTitleAndYear(String movieTitle, int year, boolean adult) throws ResponseException {
		
		ResponseArray response = TMDbAPI.fullSearchMovieByTitleAndYear(movieTitle, year, adult);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			Set<JSONObject> array = response.getData();
			Set<MovieReduced> movies = new LinkedHashSet<MovieReduced>();
			for(JSONObject json : array) {
				movies.add(new MovieReduced(json));
			}
			
			return movies;
		}
	}
	
	//endregion
	
}
