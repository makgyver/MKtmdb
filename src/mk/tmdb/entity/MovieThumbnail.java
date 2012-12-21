package mk.tmdb.entity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import mk.tmdb.core.Constants;
import mk.tmdb.core.TMDBAPI;
import mk.tmdb.exception.ResponseException;
import mk.tmdb.utils.Log;
import mk.tmdb.utils.ResponseArray;
import mk.tmdb.utils.ResponseObject;
import net.sf.json.JSONObject;

public class MovieThumbnail extends Entity {

	protected boolean adult;
	protected int id;
	protected String originalTitle;
	protected String title;
	protected String posterPath;
	protected Date releaseDate;
	
	public MovieThumbnail(JSONObject json) {
		super(json);
		parseJSON(json);
	}
	
	public MovieThumbnail(MovieThumbnail movie) {
		this(movie.getOriginJSON());
	}
	
	public boolean isAdult() {
		return adult;
	}

	public void setAdult(boolean adult) {
		this.adult = adult;
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
	
	@Override
	protected boolean parseJSON(JSONObject json) {
		
		setAdult(json.getBoolean(Constants.ADULT));
		setId(json.getInt(Constants.ID));
		setOriginalTitle(json.getString(Constants.ORIGINAL_TITLE));
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
	
	public static boolean rateMovie(String sessionID, boolean guest, int movieID, float value) {
		ResponseObject response = TMDBAPI.setMovieRate(sessionID, guest, movieID, value);
		return !response.hasError();
	}
	
	public static Set<MovieReduced> getInTheatreMovies() throws ResponseException {
		return getInTheatreMovies(1);
	}

	public static Set<MovieReduced> getInTheatreMovies(int page) throws ResponseException {
		
		ResponseArray response = TMDBAPI.getInTheatresMovies(page);
		
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
		
		ResponseArray response = TMDBAPI.getAllInTheatresMovies();
		
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

		ResponseArray response = TMDBAPI.getUpcomingMovies(page);
		
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

		ResponseArray response = TMDBAPI.getAllUpcomingMovies();
		
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

		ResponseArray response = TMDBAPI.getPopularMovies(page);
		
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

		ResponseArray response = TMDBAPI.getAllPopularMovies();
		
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
		
		ResponseArray response = TMDBAPI.getTopRatedMovies(page);
		
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
		
		ResponseArray response = TMDBAPI.getAllTopRatedMovies();
		
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
	
	//endregion
	
}
