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

/**
 * Class that represents a Movie with limited features.</br>
 * To get a more detailed class see the following classes:
 * <ul>
 * <li>{@link MovieReduced}</li>
 * <li>{@link Movie}</li>
 * <li>{@link MovieFull}</li>
 * </ul>
 * 
 * @author Mirko Polato
 *
 */
public class MovieThumbnail extends Entity {

	//region Fields
	
	/**
	 * Whether the audience is adults only.
	 */
	protected Boolean adult = null;
	
	/**
	 * The movie ID.
	 */
	protected Integer id;
	
	/**
	 * The movie original title.
	 */
	protected String originalTitle = null;
	
	/**
	 * The movie title.
	 */
	protected String title;
	
	/**
	 * The movie poster path.
	 */
	protected String posterPath;
	
	/**
	 * The movie release date.
	 */
	protected Date releaseDate = new Date();
	
	//endregion
	
	/**
	 * Creates a new instance of MovieThumbnails based on the origin JSON object.
	 * 
	 * @param json The origin JSON object
	 */
	public MovieThumbnail(JSONObject json) {
		super(json);
		parseJSON(json);
	}
	
	/**
	 * Copy constructor.
	 * 
	 * @param movie The movie to copy
	 */
	public MovieThumbnail(MovieThumbnail movie) {
		this(movie.getOriginJSON());
	}
	
	//region Getters/Setters
	
	/**
	 * Gets whether the audience is adults only.
	 * 
	 * @return Whether the audience is adults only
	 */
	public boolean isAdult() {
		return adult;
	}

	/**
	 * Sets if the audience is adults only.
	 * 
	 * @param adult The audience type
	 */
	public void setAdult(boolean adult) {
		this.adult = adult;
	}
	
	/**
	 * Checks if the audience type is set.
	 * 
	 * @return Whether the audience type is set or not
	 */
	public boolean isAdultSet() {
		return adult != null;
	}

	/**
	 * Gets the movie ID.
	 * 
	 * @return The movie ID
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the movie ID.
	 * 
	 * @param id the new movie ID
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Gets the movie original title.
	 * 
	 * @return The movie original title
	 */
	public String getOriginalTitle() {
		return originalTitle;
	}

	/**
	 * Sets the movie original title.
	 * 
	 * @param originalTitle The new movie original title
	 */
	public void setOriginalTitle(String originalTitle) {
		this.originalTitle = originalTitle;
	}
	
	/**
	 * Checks if the original title is set.
	 * 
	 * @return Whether the movie original title is set or not
	 */
	public boolean isOriginalTitleSet() {
		return originalTitle != null;
	}

	/**
	 * Gets the movie title.
	 * 
	 * @return The movie title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the movie title.
	 * 
	 * @param title The new movie title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the movie poster path.
	 * 
	 * @return The movie poster path
	 */
	public String getPosterPath() {
		return posterPath;
	}

	/**
	 * Sets the movie poster path.
	 * 
	 * @param posterPath the new movie poster path
	 */
	public void setPosterPath(String posterPath) {
		this.posterPath = posterPath;
	}

	/**
	 * Gets the movie release date.
	 * 
	 * @return The movie release date
	 */
	public Date getReleaseDate() {
		return releaseDate;
	}

	/**
	 * Sets the movie release date.
	 * 
	 * @param releaseDate The new movie release date
	 */
	public void setReleaseDate(String releaseDate) {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			this.releaseDate = (Date)formatter.parse(releaseDate);
		} catch (ParseException e) {
			Log.print(e);
		}
	}
	
	//endregion
	
	/**
	 * Parses the origin JSON object.
	 */
	private boolean parseJSON(JSONObject json) {
		
		if (json.isNullObject()) return false;
		
		if (json.has(Constants.ADULT)) setAdult(json.getBoolean(Constants.ADULT));
		setId(json.getInt(Constants.ID));
		if (json.has(Constants.ORIGINAL_TITLE)) setOriginalTitle(json.getString(Constants.ORIGINAL_TITLE));
		setPosterPath(json.getString(Constants.POSTER_PATH));
		setReleaseDate(json.getString(Constants.RELEASE_DATE));
		setTitle(json.getString(Constants.TITLE));
		
		return true;
	}
	
	/**
	 * Rates the movie.
	 * 
	 * @param account The user account
	 * @param value The rating value
	 * @return Whether the operation succeeded or not
	 */
	public boolean rateThisMovie(Account account, float value) {
		return rateMovie(account.getSessionID(), false, id, value);
	}
	
	/**
	 * Rates the movie by a guest session.
	 * 
	 * @param sessionID The guest session ID
	 * @param value The rating value
	 * @return Whether the operation succeeded or not
	 */
	public boolean rateThisMovieAsGuest(String sessionID, float value) {
		return rateMovie(sessionID, true, id, value);
	}
	
	//region Static methods
	
	/**
	 * Rates the specified movie.
	 * 
	 * @param account The user account
	 * @param movieID The movie ID
	 * @param value The rating value
	 * @return Whether the operation succeeded or not
	 */
	public static boolean rateMovie(Account account, int movieID, float value) {
		ResponseObject response = TMDbAPI.setMovieRate(account.getSessionID(), false, movieID, value);
		return !response.hasError();
	}
	
	/**
	 * Rates the specified movie.
	 * 
	 * @param sessionID The session ID
	 * @param guest Whether the session is a guest one or not
	 * @param movieID The movie ID
	 * @param value The rating value
	 * @return Whether the operation succeeded or not
	 */
	public static boolean rateMovie(String sessionID, boolean guest, int movieID, float value) {
		ResponseObject response = TMDbAPI.setMovieRate(sessionID, guest, movieID, value);
		return !response.hasError();
	}
	
	/**
	 * Gets the list of movies currently in theatre.
	 * Returns the results of the first page.
	 * 
	 * @return The list of movies currently in theatre
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
	public static Set<MovieReduced> getInTheatreMovies() throws ResponseException {
		return getInTheatreMovies(1);
	}

	/**
	 * Gets the list of movies currently in theatre.
	 * Returns the results of the given page number.
	 * 
	 * @param page The page number to retrieve
	 * @return The list of movies currently in theatre
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
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
	
	/**
	 * Gets the entire list of movies currently in theatre.
	 * 
	 * @return The list of movies currently in theatre
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
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
	
	/**
	 * Gets the list of coming soon movies.
	 * Returns the results of the first page.
	 * 
	 * @return The list of coming soon movies
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
	public static Set<MovieReduced> getUpcomingMovies() throws ResponseException  {
		return getUpcomingMovies(1);
	}

	/**
	 * Gets the list of coming soon movies.
	 * Returns the results of the given page number.
	 * 
	 * @param page The page number to retrieve
	 * @return The list of coming soon movies
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
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
	
	/**
	 * Gets the entire list of coming soon movies.
	 * 
	 * @return The list of coming soon movies
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
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

	/**
	 * Gets the list of popular movies.
	 * Returns the results of the first page.
	 * 
	 * @return The list of popular movies
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
	public static Set<MovieReduced> getPopularMovies() throws ResponseException  {
		return getPopularMovies(1);
	}

	/**
	 * Gets the list of popular movies.
	 * Returns the results of the given page number.
	 * 
	 * @param page The page number to retrieve
	 * @return The list of popular movies
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
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
	
	/**
	 * Gets the entire list of popular movies.
	 * 
	 * @return The list of popular movies
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
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

	/**
	 * Gets the list of top rated movies.
	 * Returns the results of the first page.
	 * 
	 * @return The list of top rated movies
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
	public static Set<MovieReduced> getTopRatedMovies() throws ResponseException {
		return getTopRatedMovies(1);
	}

	/**
	 * Gets the list of top rated movies.
	 * Returns the results of the given page number.
	 * 
	 * @param page The page number to retrieve
	 * @return The list of top rated movies
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
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
	
	/**
	 * Gets the entire list of top rated movies.
	 * 
	 * @return The list of top rated movies
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
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
	
	/**
	 * Gets the movie information by id.
	 * 
	 * @param movieID The movie ID
	 * @return The movie
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
	public static Movie getInformation(int movieID) throws ResponseException {
		
		ResponseObject response = TMDbAPI.getMovieInformation(movieID);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			return new Movie(response.getData());
		}
	}
	
	/**
	 * Gets the movie keywords.
	 * 
	 * @param movieID The movie ID
	 * @return The list of keywords
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
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
	
	/**
	 * Gets the list of translation languages of the movie.
	 * 
	 * @param movieID The movie ID
	 * @return The list of translation languages of the movie.
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
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
	
	/**
	 * Gets the list of movie trailers.
	 * 
	 * @param movieID The movie ID
	 * @return The list of movie trailers
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
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
	
	/**
	 * Gets the movie cast information.
	 * 
	 * @param movieID The movie ID
	 * @return The movie cast information
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
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

	/**
	 * Gets the movie crew information.
	 * 
	 * @param movieID The movie ID
	 * @return The movie crew information
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
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
	
	/**
	 * Gets the list of movie posters.
	 * 
	 * @param movieID The movie ID
	 * @return The list of movie posters
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
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
	
	/**
	 * Gets the list of movie backdrops.
	 * 
	 * @param movieID The movie ID
	 * @return The list of movie backdrops
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
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
	
	/**
	 * Gets a list of movie ids that have been edited. 
	 * By default we show the last 24 hours and only 100 items per page. 
	 * The maximum number of days that can be returned in a single request is 14. 
	 * You can then use the {@link #getChanged(int) getChanged(int)} static method to get the actual data that has been changed.
	 * Please note that the change log system to support this was changed on 
	 * October 5, 2012 and will only show movies that have been edited since.
	 * Returns the results of the first page.
	 * 
	 * @return List of movie ids that have been edited. 
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
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
	
	/**
	 * Gets a list of movie ids that have been edited. 
	 * By default we show the last 24 hours and only 100 items per page. 
	 * The maximum number of days that can be returned in a single request is 14. 
	 * You can then use the {@link #getChanged(int) getChanged(int)} static method to get the actual data that has been changed.
	 * Please note that the change log system to support this was changed on 
	 * October 5, 2012 and will only show movies that have been edited since.
	 * Returns the results of the first page.
	 * 
	 * @param start The date where the search starts 
	 * @param end The date where the search ends
	 * @return List of movie ids that have been edited. 
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
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
	
	/**
	 * Gets a list of movie ids that have been edited. 
	 * By default we show the last 24 hours and only 100 items per page. 
	 * The maximum number of days that can be returned in a single request is 14. 
	 * You can then use the {@link #getChanged(int) getChanged(int)} static method to get the actual data that has been changed.
	 * Please note that the change log system to support this was changed on 
	 * October 5, 2012 and will only show movies that have been edited since.
	 * Returns the results of the first page.
	 * 
	 * @param start The date where the search starts 
	 * @param end The date where the search ends
	 * @return List of movie ids that have been edited. 
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
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
	
	/**
	 * Gets a list of movie ids that have been edited. 
	 * By default we show the last 24 hours and only 100 items per page. 
	 * The maximum number of days that can be returned in a single request is 14. 
	 * You can then use the {@link #getChanged(int) getChanged(int)} static method to get the actual data that has been changed.
	 * Please note that the change log system to support this was changed on 
	 * October 5, 2012 and will only show movies that have been edited since.
	 * Returns the results of the first page.
	 * 
	 * @param page The page number to retrieve
	 * @return List of movie ids that have been edited. 
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
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
	
	/**
	 * Gets a list of movie ids that have been edited. 
	 * By default we show the last 24 hours and only 100 items per page. 
	 * The maximum number of days that can be returned in a single request is 14. 
	 * You can then use the {@link #getChanged(int) getChanged(int)} static method to get the actual data that has been changed.
	 * Please note that the change log system to support this was changed on 
	 * October 5, 2012 and will only show movies that have been edited since.
	 * Returns the results of the first page.
	 * 
	 * @param start The date where the search starts 
	 * @param end The date where the search ends
	 * @param page The page number to retrieve
	 * @return List of movie ids that have been edited. 
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
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
	
	/**
	 * Gets a list of movie ids that have been edited. 
	 * By default we show the last 24 hours and only 100 items per page. 
	 * The maximum number of days that can be returned in a single request is 14. 
	 * You can then use the {@link #getChanged(int) getChanged(int)} static method to get the actual data that has been changed.
	 * Please note that the change log system to support this was changed on 
	 * October 5, 2012 and will only show movies that have been edited since.
	 * Returns the results of the first page.
	 * 
	 * @param start The date where the search starts 
	 * @param end The date where the search ends
	 * @param page The page number to retrieve
	 * @return List of movie ids that have been edited. 
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
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
	
	/**
	 * Gets the entire list of movie ids that have been edited. 
	 * By default we show the last 24 hours and only 100 items per page. 
	 * The maximum number of days that can be returned in a single request is 14. 
	 * You can then use the {@link #getChanged(int) getChanged(int)} static method to get the actual data that has been changed.
	 * Please note that the change log system to support this was changed on 
	 * October 5, 2012 and will only show movies that have been edited since.
	 * Returns the results of the first page.
	 * 
	 * @return List of movie ids that have been edited. 
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
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
	
	/**
	 * Gets the entire list of movie ids that have been edited. 
	 * By default we show the last 24 hours and only 100 items per page. 
	 * The maximum number of days that can be returned in a single request is 14. 
	 * You can then use the {@link #getChanged(int) getChanged(int)} static method to get the actual data that has been changed.
	 * Please note that the change log system to support this was changed on 
	 * October 5, 2012 and will only show movies that have been edited since.
	 * Returns the results of the first page.
	 * 
	 * @param start The date where the search starts 
	 * @param end The date where the search ends
	 * @return List of movie ids that have been edited. 
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
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
	
	/**
	 * Gets the entire list of movie ids that have been edited. 
	 * By default we show the last 24 hours and only 100 items per page. 
	 * The maximum number of days that can be returned in a single request is 14. 
	 * You can then use the {@link #getChanged(int) getChanged(int)} static method to get the actual data that has been changed.
	 * Please note that the change log system to support this was changed on 
	 * October 5, 2012 and will only show movies that have been edited since.
	 * Returns the results of the first page.
	 * 
	 * @param start The date where the search starts 
	 * @param end The date where the search ends
	 * @return List of movie ids that have been edited. 
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
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
	
	/**
	 * Gets the movie alternative titles.
	 * 
	 * @param movieID The movie ID
	 * @return The movie alternative titles
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
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
	
	/**
	 * Gets the movie release dates.
	 * 
	 * @param movieID The movie ID
	 * @return The movie release dates
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
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
	
	/**
	 * Gets a list of similar movies to the specified one.
	 * 
	 * @param movieID The movie ID
	 * @return List of similar movies to the specified one
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
	public static Set<MovieReduced> getSimilarMovies(int movieID) throws ResponseException {
		return getSimilarMovies(movieID, 1);
	}
	
	/**
	 * Gets a list of similar movies to the specified one.
	 * 
	 * @param movieID The movie ID
	 * @param page The page number to retrieve
	 * @return List of similar movies to the specified one
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
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
	
	/**
	 * Gets the entire list of similar movies to the specified one.
	 * 
	 * @param movieID The movie ID
	 * @return List of similar movies to the specified one
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
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
	
	/**
	 * Gets the latest movie added to the TMDb.
	 * 
	 * @return The latest movie that has been added.
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
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
	
	/**
	 * Searches for movies by title.
	 * Returns the results of the first page.
	 * 
	 * @param movieTitle The movie title
	 * @return The list of movie that match the given title
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
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
	
	/**
	 * Searches for movies by title.
	 * Returns the results of the given page number.
	 * 
	 * @param movieTitle The movie title
	 * @param page The page number to retrieve
	 * @return The list of movie that match the given title
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
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
	
	/**
	 * Searches for movie by title.
	 * Returns the results of the first page.
	 * 
	 * @param movieTitle The movie title
	 * @param adult Whether the audience is adults only or not
	 * @return The list of movie that match the given title
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
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
	
	/**
	 * Searches for movie by title.
	 * Returns the results of the given page number.
	 * 
	 * @param movieTitle The movie title
	 * @param adult Whether the audience is adults only or not
	 * @param page The page number to retrieve
	 * @return The list of movie that match the given title
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
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
	
	/**
	 * Searches for movie by title. Gets all the results.
	 * 
	 * @param movieTitle The movie title
	 * @return The list of movie that match the given title
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
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
	
	/**
	 * Searches for movie by title. Gets all the results.
	 * 
	 * @param movieTitle The movie title
	 * @param adult Whether the audience is adults only or not
	 * @return The list of movie that match the given title
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
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
	
	/**
	 * Searches for movie by title and year.
	 * Returns the results of the first page.
	 * 
	 * @param movieTitle The movie title
	 * @param year The movie year
	 * @return The list of movie that match the given title
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
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
	
	/**
	 * Searches for movie by title and year.
	 * Returns the results of the given page number.
	 * 
	 * @param movieTitle The movie title
	 * @param year The movie year
	 * @param page The page number to retrieve
	 * @return The list of movie that match the given title
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
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
	
	/**
	 * Searches for movie by title and year.
	 * Returns the results of the first page.
	 * 
	 * @param movieTitle The movie title
	 * @param year The movie year
	 * @param adult Whether the audience is adults only or not
	 * @return The list of movie that match the given title
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
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
	
	/**
	 * Searches for movie by title and year.
	 * Returns the results of the given page number.
	 * 
	 * @param movieTitle The movie title
	 * @param year The movie year
	 * @param adult Whether the audience is adults only or not
	 * @param page The page number to retrieve
	 * @return The list of movie that match the given title
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
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
	
	/**
	 * Searches for movie by title and year. Gets all the results.
	 * 
	 * @param movieTitle The movie title
	 * @param year The movie year
	 * @return The list of movie that match the given title
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
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
	
	/**
	 * Searches for movie by title and year. Gets all the results.
	 * 
	 * @param movieTitle The movie title
	 * @param year The movie year
	 * @param adult Whether the audience is adults only or not
	 * @return The list of movie that match the given title
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
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
