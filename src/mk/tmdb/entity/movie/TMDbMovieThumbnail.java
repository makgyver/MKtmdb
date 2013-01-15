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
import java.util.LinkedList;
import java.util.List;

import mk.tmdb.core.TMDbConstants;
import mk.tmdb.core.TMDbAPI;
import mk.tmdb.entity.TMDbAccount;
import mk.tmdb.entity.TMDbCountry;
import mk.tmdb.entity.TMDbEntity;
import mk.tmdb.entity.TMDbKeyword;
import mk.tmdb.entity.TMDbLanguage;
import mk.tmdb.entity.image.TMDbBackdrop;
import mk.tmdb.entity.image.TMDbPoster;
import mk.tmdb.entity.person.TMDbMovieCast;
import mk.tmdb.entity.person.TMDbMovieCrew;
import mk.tmdb.entity.trailer.TMDbQuicktimeTrailer;
import mk.tmdb.entity.trailer.TMDbTrailer;
import mk.tmdb.entity.trailer.TMDbYoutubeTrailer;
import mk.tmdb.exception.TMDbResponseException;
import mk.tmdb.response.TMDbResponseArray;
import mk.tmdb.response.TMDbResponseObject;
import mk.tmdb.utils.Log;
import mk.tmdb.utils.Pair;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Class that represents a Movie with limited features.</br>
 * To get a more detailed class see the following classes:
 * <ul>
 * <li>{@link TMDbMovieReduced}</li>
 * <li>{@link TMDbMovie}</li>
 * <li>{@link TMDbMovieFull}</li>
 * </ul>
 * 
 * @author Mirko Polato
 *
 */
public class TMDbMovieThumbnail extends TMDbEntity {

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
	protected Date releaseDate = null;
	
	/**
	 * The MPAA movie rating
	 */
	protected String mpaa;
	
	//endregion
	
	/**
	 * Creates a new instance of MovieThumbnails based on the origin JSON object.
	 * 
	 * @param json The origin JSON object
	 */
	public TMDbMovieThumbnail(JSONObject json) {
		super(json);
		parseJSON(json);
	}
	
	/**
	 * Copy constructor.
	 * 
	 * @param movie The movie to copy
	 */
	public TMDbMovieThumbnail(TMDbMovieThumbnail movie) {
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
	
	/**
	 * Checks if the release date is set.
	 * 
	 * @return Whether the release date is set or not
	 */
	public boolean isReleaseDateSet() {
		return this.releaseDate != null;
	}
	
	/**
	 * Gets the MPAA movie rating.
	 * 
	 * @return The MPAA movie rating
	 */
	public String getMPAARating() {
		return mpaa;
	}
	
	/**
	 * Sets the MPAA movie rating.
	 * 
	 * @param rating The new MPAA movie rating
	 */
	public void setMPAARating(String rating) {
		this.mpaa = rating;
	}
	
	/**
	 * Checks if the MPAA rating is set.
	 * 
	 * @return Whether the MPAA rating is set or not
	 */
	public boolean isMPAARatingSet() {
		return mpaa != null;
	}
	
	/**
	 * Gets the movie year.
	 * 
	 * @return The movie year
	 */
	public int getYear() {
		int year = 1900;
		SimpleDateFormat formatNowYear = new SimpleDateFormat("yyyy");
		
		if (isReleaseDateSet()) {
			year = Integer.valueOf(formatNowYear.format(releaseDate));
		}
		
		return year;
	}
	
	//endregion
	
	/**
	 * Parses the origin JSON object.
	 */
	private boolean parseJSON(JSONObject json) {
		
		if (json.isNullObject()) return false;
		
		if (json.has(TMDbConstants.ADULT)) setAdult(json.getBoolean(TMDbConstants.ADULT));
		setId(json.getInt(TMDbConstants.ID));
		if (json.has(TMDbConstants.ORIGINAL_TITLE)) setOriginalTitle(json.getString(TMDbConstants.ORIGINAL_TITLE));
		setPosterPath(json.getString(TMDbConstants.POSTER_PATH));
		setReleaseDate(json.getString(TMDbConstants.RELEASE_DATE));
		setTitle(json.getString(TMDbConstants.TITLE));
		
		return true;
	}
	
	/**
	 * Rates the movie.
	 * 
	 * @param account The user account
	 * @param value The rating value
	 * @return Whether the operation succeeded or not
	 */
	public boolean rateThisMovie(TMDbAccount account, float value) {
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
	public static boolean rateMovie(TMDbAccount account, int movieID, float value) {
		TMDbResponseObject response = TMDbAPI.setMovieRate(account.getSessionID(), false, movieID, value);
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
		TMDbResponseObject response = TMDbAPI.setMovieRate(sessionID, guest, movieID, value);
		return !response.hasError();
	}
	
	/**
	 * Gets the list of movies currently in theatre.
	 * Returns the results of the first page.
	 * 
	 * @return The list of movies currently in theatre
	 * @throws TMDbResponseException Throws whether the server response is not a success.
	 */
	public static List<TMDbMovieReduced> getInTheatreMovies() throws TMDbResponseException {
		return getInTheatreMovies(1);
	}

	/**
	 * Gets the list of movies currently in theatre.
	 * Returns the results of the given page number.
	 * 
	 * @param page The page number to retrieve
	 * @return The list of movies currently in theatre
	 * @throws TMDbResponseException Throws whether the server response is not a success.
	 */
	public static List<TMDbMovieReduced> getInTheatreMovies(int page) throws TMDbResponseException {
		
		TMDbResponseArray response = TMDbAPI.getInTheatresMovies(page);
		
		if (response.hasError()) {
			throw new TMDbResponseException(response.getStatus());
		} else {
			
			List<TMDbMovieReduced> inTheatre = new LinkedList<TMDbMovieReduced>();
			List<JSONObject> array = response.getData();
			for (JSONObject json : array) {
				inTheatre.add(new TMDbMovieReduced(json));
			}

			return inTheatre;
		}
	}
	
	/**
	 * Gets the entire list of movies currently in theatre.
	 * 
	 * @return The list of movies currently in theatre
	 * @throws TMDbResponseException Throws whether the server response is not a success.
	 */
	public static List<TMDbMovieReduced> getAllInTheatreMovies() throws TMDbResponseException {
		
		TMDbResponseArray response = TMDbAPI.getAllInTheatresMovies();
		
		if (response.hasError()) {
			throw new TMDbResponseException(response.getStatus());
		} else {
			
			List<TMDbMovieReduced> inTheatre = new LinkedList<TMDbMovieReduced>();
			List<JSONObject> array = response.getData();
			for (JSONObject json : array) {
				inTheatre.add(new TMDbMovieReduced(json));
			}

			return inTheatre;
		}
	}
	
	/**
	 * Gets the list of coming soon movies.
	 * Returns the results of the first page.
	 * 
	 * @return The list of coming soon movies
	 * @throws TMDbResponseException Throws whether the server response is not a success.
	 */
	public static List<TMDbMovieReduced> getUpcomingMovies() throws TMDbResponseException  {
		return getUpcomingMovies(1);
	}

	/**
	 * Gets the list of coming soon movies.
	 * Returns the results of the given page number.
	 * 
	 * @param page The page number to retrieve
	 * @return The list of coming soon movies
	 * @throws TMDbResponseException Throws whether the server response is not a success.
	 */
	public static List<TMDbMovieReduced> getUpcomingMovies(int page) throws TMDbResponseException {

		TMDbResponseArray response = TMDbAPI.getUpcomingMovies(page);
		
		if (response.hasError()) {
			throw new TMDbResponseException(response.getStatus());
		} else {
			
			List<TMDbMovieReduced> upcoming = new LinkedList<TMDbMovieReduced>();
			List<JSONObject> array = response.getData();
			for (JSONObject json : array) {
				upcoming.add(new TMDbMovieReduced(json));
			}

			return upcoming;
		}
	}
	
	/**
	 * Gets the entire list of coming soon movies.
	 * 
	 * @return The list of coming soon movies
	 * @throws TMDbResponseException Throws whether the server response is not a success.
	 */
	public static List<TMDbMovieReduced> getAllUpcomingMovies() throws TMDbResponseException {

		TMDbResponseArray response = TMDbAPI.getAllUpcomingMovies();
		
		if (response.hasError()) {
			throw new TMDbResponseException(response.getStatus());
		} else {
			
			List<TMDbMovieReduced> upcoming = new LinkedList<TMDbMovieReduced>();
			List<JSONObject> array = response.getData();
			for (JSONObject json : array) {
				upcoming.add(new TMDbMovieReduced(json));
			}

			return upcoming;
		}
	}

	/**
	 * Gets the list of popular movies.
	 * Returns the results of the first page.
	 * 
	 * @return The list of popular movies
	 * @throws TMDbResponseException Throws whether the server response is not a success.
	 */
	public static List<TMDbMovieReduced> getPopularMovies() throws TMDbResponseException  {
		return getPopularMovies(1);
	}

	/**
	 * Gets the list of popular movies.
	 * Returns the results of the given page number.
	 * 
	 * @param page The page number to retrieve
	 * @return The list of popular movies
	 * @throws TMDbResponseException Throws whether the server response is not a success.
	 */
	public static List<TMDbMovieReduced> getPopularMovies(int page) throws TMDbResponseException {

		TMDbResponseArray response = TMDbAPI.getPopularMovies(page);
		
		if (response.hasError()) {
			throw new TMDbResponseException(response.getStatus());
		} else {
			
			List<TMDbMovieReduced> popular = new LinkedList<TMDbMovieReduced>();
			List<JSONObject> array = response.getData();
			for (JSONObject json : array) {
				popular.add(new TMDbMovieReduced(json));
			}

			return popular;
		}
	}
	
	/**
	 * Gets the entire list of popular movies.
	 * 
	 * @return The list of popular movies
	 * @throws TMDbResponseException Throws whether the server response is not a success.
	 */
	public static List<TMDbMovieReduced> getAllPopularMovies() throws TMDbResponseException {

		TMDbResponseArray response = TMDbAPI.getAllPopularMovies();
		
		if (response.hasError()) {
			throw new TMDbResponseException(response.getStatus());
		} else {
			
			List<TMDbMovieReduced> popular = new LinkedList<TMDbMovieReduced>();
			List<JSONObject> array = response.getData();
			for (JSONObject json : array) {
				popular.add(new TMDbMovieReduced(json));
			}

			return popular;
		}
	}

	/**
	 * Gets the list of top rated movies.
	 * Returns the results of the first page.
	 * 
	 * @return The list of top rated movies
	 * @throws TMDbResponseException Throws whether the server response is not a success.
	 */
	public static List<TMDbMovieReduced> getTopRatedMovies() throws TMDbResponseException {
		return getTopRatedMovies(1);
	}

	/**
	 * Gets the list of top rated movies.
	 * Returns the results of the given page number.
	 * 
	 * @param page The page number to retrieve
	 * @return The list of top rated movies
	 * @throws TMDbResponseException Throws whether the server response is not a success.
	 */
	public static List<TMDbMovieReduced> getTopRatedMovies(int page) throws TMDbResponseException {
		
		TMDbResponseArray response = TMDbAPI.getTopRatedMovies(page);
		
		if (response.hasError()) {
			throw new TMDbResponseException(response.getStatus());
		} else {
			
			List<TMDbMovieReduced> tops = new LinkedList<TMDbMovieReduced>();
			List<JSONObject> array = response.getData();
			for (JSONObject json : array) {
				tops.add(new TMDbMovieReduced(json));
			}

			return tops;
		}
	}
	
	/**
	 * Gets the entire list of top rated movies.
	 * 
	 * @return The list of top rated movies
	 * @throws TMDbResponseException Throws whether the server response is not a success.
	 */
	public static List<TMDbMovieReduced> getAllTopRatedMovies() throws TMDbResponseException {
		
		TMDbResponseArray response = TMDbAPI.getAllTopRatedMovies();
		
		if (response.hasError()) {
			throw new TMDbResponseException(response.getStatus());
		} else {
			
			List<TMDbMovieReduced> tops = new LinkedList<TMDbMovieReduced>();
			List<JSONObject> array = response.getData();
			for (JSONObject json : array) {
				tops.add(new TMDbMovieReduced(json));
			}

			return tops;
		}
	}
	
	/**
	 * Gets the movie information by id.
	 * 
	 * @param movieID The movie ID
	 * @return The movie
	 * @throws TMDbResponseException Throws whether the server response is not a success.
	 */
	public static TMDbMovie getInformation(int movieID) throws TMDbResponseException {
		
		TMDbResponseObject response = TMDbAPI.getMovieInformation(movieID);
		
		if (response.hasError()) {
			throw new TMDbResponseException(response.getStatus());
		} else {
			return new TMDbMovie(response.getData());
		}
	}
	
	/**
	 * Gets the movie keywords.
	 * 
	 * @param movieID The movie ID
	 * @return The list of keywords
	 * @throws TMDbResponseException Throws whether the server response is not a success.
	 */
	public static List<TMDbKeyword> getKeywords(int movieID) throws TMDbResponseException {
		TMDbResponseObject response = TMDbAPI.getMovieKeywords(movieID);
		
		if (response.hasError()) {
			throw new TMDbResponseException(response.getStatus());
		} else {
			
			JSONArray allkeys = response.getData().getJSONArray(TMDbConstants.KEYWORDS);
			List<TMDbKeyword> keywords = new LinkedList<TMDbKeyword>();
			for (Object obj : allkeys) {
			    keywords.add(new TMDbKeyword((JSONObject) obj));
			}
			
			return keywords;
		}
	}
	
	/**
	 * Gets the list of translation languages of the movie.
	 * 
	 * @param movieID The movie ID
	 * @return The list of translation languages of the movie.
	 * @throws TMDbResponseException Throws whether the server response is not a success.
	 */
	public static List<TMDbLanguage> getTranslations(int movieID) throws TMDbResponseException {
		TMDbResponseObject response = TMDbAPI.getMovieTranslations(movieID);
		
		if (response.hasError()) {
			throw new TMDbResponseException(response.getStatus());
		} else {
			
			JSONArray allTrans = response.getData().getJSONArray(TMDbConstants.TRANSLATIONS);
			List<TMDbLanguage> translations = new LinkedList<TMDbLanguage>();
			for (Object obj : allTrans) {
			    translations.add(new TMDbLanguage((JSONObject) obj));
			}
			
			return translations;
		}
	}
	
	/**
	 * Gets the list of movie trailers.
	 * 
	 * @param movieID The movie ID
	 * @return The list of movie trailers
	 * @throws TMDbResponseException Throws whether the server response is not a success.
	 */
	public static List<TMDbTrailer> getTrailers(int movieID) throws TMDbResponseException {
		TMDbResponseObject response = TMDbAPI.getMovieTrailers(movieID);
		
		if (response.hasError()) {
			throw new TMDbResponseException(response.getStatus());
		} else {
		
			JSONArray utube = response.getData().getJSONArray(TMDbConstants.YOUTUBE);
			List<TMDbTrailer> trailers = new LinkedList<TMDbTrailer>();
			for (Object obj : utube) {
			    trailers.add(new TMDbYoutubeTrailer((JSONObject) obj));
			}
			
			JSONArray quick = response.getData().getJSONArray(TMDbConstants.QUICKTIME);
			
			for (Object obj : quick) {
				String name = ((JSONObject) obj).getString(TMDbConstants.NAME);
				JSONArray quicks = ((JSONObject) obj).getJSONArray(TMDbConstants.SOURCES);
				
				for (Object jobj : quicks) {
					trailers.add(new TMDbQuicktimeTrailer((JSONObject) jobj, name));
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
	 * @throws TMDbResponseException Throws whether the server response is not a success.
	 */
	public static List<TMDbMovieCast> getCastInformation(int movieID) throws TMDbResponseException {
		TMDbResponseObject response = TMDbAPI.getCastInformation(movieID);
		
		if (response.hasError()) {
			throw new TMDbResponseException(response.getStatus());
		} else {
		
			JSONArray castArray = response.getData().getJSONArray(TMDbConstants.CAST);
			List<TMDbMovieCast> cast = new LinkedList<TMDbMovieCast>();
			for (Object obj : castArray) {
				cast.add(new TMDbMovieCast((JSONObject) obj, movieID));
			}
			
			return cast;
		}
	}

	/**
	 * Gets the movie crew information.
	 * 
	 * @param movieID The movie ID
	 * @return The movie crew information
	 * @throws TMDbResponseException Throws whether the server response is not a success.
	 */
	public static List<TMDbMovieCrew> getCrewInformation(int movieID) throws TMDbResponseException {
		TMDbResponseObject response = TMDbAPI.getCastInformation(movieID);
		
		if (response.hasError()) {
			throw new TMDbResponseException(response.getStatus());
		} else {
		
			JSONArray crewArray = response.getData().getJSONArray(TMDbConstants.CREW);
			List<TMDbMovieCrew> crew = new LinkedList<TMDbMovieCrew>();
			for (Object obj : crewArray) {
				crew.add(new TMDbMovieCrew((JSONObject) obj, movieID));
			}
			
			return crew;
		}
	}
	
	/**
	 * Gets the list of movie posters.
	 * 
	 * @param movieID The movie ID
	 * @return The list of movie posters
	 * @throws TMDbResponseException Throws whether the server response is not a success.
	 */
	public static List<TMDbPoster> getPosters(int movieID) throws TMDbResponseException {
		
		TMDbResponseObject response = TMDbAPI.getMovieImages(movieID);
		
		if (response.hasError()) {
			throw new TMDbResponseException(response.getStatus());
		} else {
			
			JSONArray allPosters = response.getData().getJSONArray(TMDbConstants.POSTERS);
			
			List<TMDbPoster> images = new LinkedList<TMDbPoster>();
			for (Object obj : allPosters) {
			    images.add(new TMDbPoster((JSONObject) obj));
			}
			
			return images;
		}
	}
	
	/**
	 * Gets the list of movie backdrops.
	 * 
	 * @param movieID The movie ID
	 * @return The list of movie backdrops
	 * @throws TMDbResponseException Throws whether the server response is not a success.
	 */
	public static List<TMDbBackdrop> getBackdrops(int movieID) throws TMDbResponseException {
		
		TMDbResponseObject response = TMDbAPI.getMovieImages(movieID);
		
		if (response.hasError()) {
			throw new TMDbResponseException(response.getStatus());
		} else {
			
			List<TMDbBackdrop> images = new LinkedList<TMDbBackdrop>();
			
			JSONArray allBackdrops = response.getData().getJSONArray(TMDbConstants.BACKDROPS);
			for (Object obj : allBackdrops) {
			    images.add(new TMDbBackdrop((JSONObject) obj));
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
	 * @throws TMDbResponseException Throws whether the server response is not a success.
	 */
	public static List<Integer> getChanged() throws TMDbResponseException {
		
		TMDbResponseArray response = TMDbAPI.getChangedMovies();
		
		if (response.hasError()) {
			throw new TMDbResponseException(response.getStatus());
		} else {
			
			List<JSONObject> array = response.getData();
			List<Integer> ids = new LinkedList<Integer>();
			for (JSONObject json : array) {
			    ids.add(json.getInt(TMDbConstants.ID));
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
	 * @throws TMDbResponseException Throws whether the server response is not a success.
	 */
	public static List<Integer> getChanged(Date start, Date end) throws TMDbResponseException {
		
		TMDbResponseArray response = TMDbAPI.getChangedMovies(start, end);
		
		if (response.hasError()) {
			throw new TMDbResponseException(response.getStatus());
		} else {
			
			List<JSONObject> array = response.getData();
			List<Integer> ids = new LinkedList<Integer>();
			for (JSONObject json : array) {
			    ids.add(json.getInt(TMDbConstants.ID));
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
	 * @throws TMDbResponseException Throws whether the server response is not a success.
	 */
	public static List<Integer> getChanged(String start, String end) throws TMDbResponseException {
		
		TMDbResponseArray response = TMDbAPI.getChangedMovies(start, end);
		
		if (response.hasError()) {
			throw new TMDbResponseException(response.getStatus());
		} else {
			
			List<JSONObject> array = response.getData();
			List<Integer> ids = new LinkedList<Integer>();
			for (JSONObject json : array) {
			    ids.add(json.getInt(TMDbConstants.ID));
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
	 * @throws TMDbResponseException Throws whether the server response is not a success.
	 */
	public static List<Integer> getChanged(int page) throws TMDbResponseException {
		
		TMDbResponseArray response = TMDbAPI.getChangedMovies(page);
		
		if (response.hasError()) {
			throw new TMDbResponseException(response.getStatus());
		} else {
			
			List<JSONObject> array = response.getData();
			List<Integer> ids = new LinkedList<Integer>();
			for (JSONObject json : array) {
			    ids.add(json.getInt(TMDbConstants.ID));
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
	 * @throws TMDbResponseException Throws whether the server response is not a success.
	 */
	public static List<Integer> getChanged(Date start, Date end, int page) throws TMDbResponseException {
		
		TMDbResponseArray response = TMDbAPI.getChangedMovies(start, end, page);
		
		if (response.hasError()) {
			throw new TMDbResponseException(response.getStatus());
		} else {
			
			List<JSONObject> array = response.getData();
			List<Integer> ids = new LinkedList<Integer>();
			for (JSONObject json : array) {
			    ids.add(json.getInt(TMDbConstants.ID));
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
	 * @throws TMDbResponseException Throws whether the server response is not a success.
	 */
	public static List<Integer> getChanged(String start, String end, int page) throws TMDbResponseException {
		
		TMDbResponseArray response = TMDbAPI.getChangedMovies(start, end, page);
		
		if (response.hasError()) {
			throw new TMDbResponseException(response.getStatus());
		} else {
			
			List<JSONObject> array = response.getData();
			List<Integer> ids = new LinkedList<Integer>();
			for (JSONObject json : array) {
			    ids.add(json.getInt(TMDbConstants.ID));
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
	 * @throws TMDbResponseException Throws whether the server response is not a success.
	 */
	public static List<Integer> getAllChanged() throws TMDbResponseException {
		
		TMDbResponseArray response = TMDbAPI.getAllChangedMovies();
		
		if (response.hasError()) {
			throw new TMDbResponseException(response.getStatus());
		} else {
			
			List<JSONObject> array = response.getData();
			List<Integer> ids = new LinkedList<Integer>();
			for (JSONObject json : array) {
			    ids.add(json.getInt(TMDbConstants.ID));
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
	 * @throws TMDbResponseException Throws whether the server response is not a success.
	 */
	public static List<Integer> getAllChanged(Date start, Date end) throws TMDbResponseException {
		
		TMDbResponseArray response = TMDbAPI.getAllChangedMovies(start, end);
		
		if (response.hasError()) {
			throw new TMDbResponseException(response.getStatus());
		} else {
			
			List<JSONObject> array = response.getData();
			List<Integer> ids = new LinkedList<Integer>();
			for (JSONObject json : array) {
			    ids.add(json.getInt(TMDbConstants.ID));
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
	 * @throws TMDbResponseException Throws whether the server response is not a success.
	 */
	public static List<Integer> getAllChanged(String start, String end) throws TMDbResponseException {
		
		TMDbResponseArray response = TMDbAPI.getAllChangedMovies(start, end);
		
		if (response.hasError()) {
			throw new TMDbResponseException(response.getStatus());
		} else {
			
			List<JSONObject> array = response.getData();
			List<Integer> ids = new LinkedList<Integer>();
			for (JSONObject json : array) {
			    ids.add(json.getInt(TMDbConstants.ID));
			}
			
			return ids;
		}
	}
	
	/**
	 * Gets the movie alternative titles.
	 * 
	 * @param movieID The movie ID
	 * @return The movie alternative titles
	 * @throws TMDbResponseException Throws whether the server response is not a success.
	 */
	public static List<Pair<TMDbCountry, String>> getAlternativeTitles(int movieID) throws TMDbResponseException {
		
		TMDbResponseObject response = TMDbAPI.getAlternativeMovieTitles(movieID);
		
		if (response.hasError()) {
			throw new TMDbResponseException(response.getStatus());
		} else {
			
			JSONArray array = response.getData().getJSONArray(TMDbConstants.TITLES);
			List<Pair<TMDbCountry, String>> titles = new LinkedList<Pair<TMDbCountry, String>>();
			for (Object obj : array) {
				JSONObject json = (JSONObject) obj;
			    titles.add(new Pair<TMDbCountry, String>(new TMDbCountry(json), json.getString(TMDbConstants.TITLE)));
			}
			
			return titles;
		}
	}
	
	/**
	 * Gets the movie release dates.
	 * 
	 * @param movieID The movie ID
	 * @return The movie release dates
	 * @throws TMDbResponseException Throws whether the server response is not a success.
	 */
	public static List<Pair<TMDbCountry, Date>> getReleaseDates(int movieID) throws TMDbResponseException {
		
		// Certification skipped
		
		TMDbResponseObject response = TMDbAPI.getMovieReleases(movieID);
		
		if (response.hasError()) {
			throw new TMDbResponseException(response.getStatus());
		} else {
			
			JSONArray array = response.getData().getJSONArray(TMDbConstants.COUNTRIES);
			List<Pair<TMDbCountry, Date>> dates = new LinkedList<Pair<TMDbCountry, Date>>();
			for (Object obj : array) {
				JSONObject json = (JSONObject) obj;
				DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			    try {
					dates.add(new Pair<TMDbCountry, Date>(new TMDbCountry(json), (Date)formatter.parse(json.getString(TMDbConstants.RELEASE_DATE))));
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
	 * @throws TMDbResponseException Throws whether the server response is not a success.
	 */
	public static List<TMDbMovieReduced> getSimilarMovies(int movieID) throws TMDbResponseException {
		return getSimilarMovies(movieID, 1);
	}
	
	/**
	 * Gets a list of similar movies to the specified one.
	 * 
	 * @param movieID The movie ID
	 * @param page The page number to retrieve
	 * @return List of similar movies to the specified one
	 * @throws TMDbResponseException Throws whether the server response is not a success.
	 */
	public static List<TMDbMovieReduced> getSimilarMovies(int movieID, int page) throws TMDbResponseException {
		
		TMDbResponseArray response = TMDbAPI.getSimilarMovies(movieID, page);
		
		if (response.hasError()) {
			throw new TMDbResponseException(response.getStatus());
		} else {
			
			List<TMDbMovieReduced> sims = new LinkedList<TMDbMovieReduced>();
			List<JSONObject> array = response.getData();
			for (JSONObject json : array) {
				sims.add(new TMDbMovieReduced(json));
			}

			return sims;
		}
	}
	
	/**
	 * Gets the entire list of similar movies to the specified one.
	 * 
	 * @param movieID The movie ID
	 * @return List of similar movies to the specified one
	 * @throws TMDbResponseException Throws whether the server response is not a success.
	 */
	public static List<TMDbMovieReduced> getAllSimilarMovies(int movieID) throws TMDbResponseException {
		
		TMDbResponseArray response = TMDbAPI.getAllSimilarMovies(movieID);
		
		if (response.hasError()) {
			throw new TMDbResponseException(response.getStatus());
		} else {
			
			List<TMDbMovieReduced> sims = new LinkedList<TMDbMovieReduced>();
			List<JSONObject> array = response.getData();
			for (JSONObject json : array) {
				sims.add(new TMDbMovieReduced(json));
			}

			return sims;
		}
	}
	
	/**
	 * Gets the latest movie added to the TMDb.
	 * 
	 * @return The latest movie that has been added.
	 * @throws TMDbResponseException Throws whether the server response is not a success.
	 */
	public static TMDbMovie getLatestMovie() throws TMDbResponseException {
		
		TMDbResponseObject response = TMDbAPI.getLatestMovie();
		
		if (response.hasError()) {
			throw new TMDbResponseException(response.getStatus());
		} else {
			return new TMDbMovie(response.getData());
		}
	}
	
	/**
	 * Gets the MPAA rating of the specified movie.
	 * 
	 * @param movieID The movie ID
	 * @return The MPAA rating
	 * @throws TMDbResponseException Throws whether the server response is not a success.
	 */
	public static String getMovieMPAARating(int movieID) throws TMDbResponseException {
		
		TMDbResponseObject response = TMDbAPI.getMovieReleases(movieID);
		
		if (response.hasError()) {
			throw new TMDbResponseException(response.getStatus());
		} else {
			
			JSONArray array = response.getData().getJSONArray(TMDbConstants.COUNTRIES);
			String result = "";
			
			for (Object obj : array) {
				JSONObject json = (JSONObject) obj;
				if (json.getString(TMDbConstants.ISO_31661).equals("US")) {
					result = json.getString(TMDbConstants.CERTIFICATION);
					break;
				}
			}
			
			return result;
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
	 * @throws TMDbResponseException Throws whether the server response is not a success.
	 */
	public static List<TMDbMovieReduced> searchByTitle(String movieTitle) throws TMDbResponseException {
		
		TMDbResponseArray response = TMDbAPI.searchMovieByTitle(movieTitle);
		
		if (response.hasError()) {
			throw new TMDbResponseException(response.getStatus());
		} else {
			List<JSONObject> array = response.getData();
			List<TMDbMovieReduced> movies = new LinkedList<TMDbMovieReduced>();
			for(JSONObject json : array) {
				movies.add(new TMDbMovieReduced(json));
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
	 * @throws TMDbResponseException Throws whether the server response is not a success.
	 */
	public static List<TMDbMovieReduced> searchByTitle(String movieTitle, int page) throws TMDbResponseException {
		
		TMDbResponseArray response = TMDbAPI.searchMovieByTitle(movieTitle, page);
		
		if (response.hasError()) {
			throw new TMDbResponseException(response.getStatus());
		} else {
			List<JSONObject> array = response.getData();
			List<TMDbMovieReduced> movies = new LinkedList<TMDbMovieReduced>();
			for(JSONObject json : array) {
				movies.add(new TMDbMovieReduced(json));
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
	 * @throws TMDbResponseException Throws whether the server response is not a success.
	 */
	public static List<TMDbMovieReduced> searchByTitle(String movieTitle, boolean adult) throws TMDbResponseException {
		
		TMDbResponseArray response = TMDbAPI.searchMovieByTitle(movieTitle, adult);
		
		if (response.hasError()) {
			throw new TMDbResponseException(response.getStatus());
		} else {
			List<JSONObject> array = response.getData();
			List<TMDbMovieReduced> movies = new LinkedList<TMDbMovieReduced>();
			for(JSONObject json : array) {
				movies.add(new TMDbMovieReduced(json));
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
	 * @throws TMDbResponseException Throws whether the server response is not a success.
	 */
	public static List<TMDbMovieReduced> searchByTitle(String movieTitle, boolean adult, int page) throws TMDbResponseException {
		
		TMDbResponseArray response = TMDbAPI.searchMovieByTitle(movieTitle, adult, page);
		
		if (response.hasError()) {
			throw new TMDbResponseException(response.getStatus());
		} else {
			List<JSONObject> array = response.getData();
			List<TMDbMovieReduced> movies = new LinkedList<TMDbMovieReduced>();
			for(JSONObject json : array) {
				movies.add(new TMDbMovieReduced(json));
			}
			
			return movies;
		}
	}
	
	/**
	 * Searches for movie by title. Gets all the results.
	 * 
	 * @param movieTitle The movie title
	 * @return The list of movie that match the given title
	 * @throws TMDbResponseException Throws whether the server response is not a success.
	 */
	public static List<TMDbMovieReduced> fullSearchByTitle(String movieTitle) throws TMDbResponseException {
		
		TMDbResponseArray response = TMDbAPI.fullSearchMovieByTitle(movieTitle);
		
		if (response.hasError()) {
			throw new TMDbResponseException(response.getStatus());
		} else {
			List<JSONObject> array = response.getData();
			List<TMDbMovieReduced> movies = new LinkedList<TMDbMovieReduced>();
			for(JSONObject json : array) {
				movies.add(new TMDbMovieReduced(json));
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
	 * @throws TMDbResponseException Throws whether the server response is not a success.
	 */
	public static List<TMDbMovieReduced> fullSearchByTitle(String movieTitle, boolean adult) throws TMDbResponseException {
		
		TMDbResponseArray response = TMDbAPI.fullSearchMovieByTitle(movieTitle, adult);
		
		if (response.hasError()) {
			throw new TMDbResponseException(response.getStatus());
		} else {
			List<JSONObject> array = response.getData();
			List<TMDbMovieReduced> movies = new LinkedList<TMDbMovieReduced>();
			for(JSONObject json : array) {
				movies.add(new TMDbMovieReduced(json));
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
	 * @throws TMDbResponseException Throws whether the server response is not a success.
	 */
	public static List<TMDbMovieReduced> searchByTitleAndYear(String movieTitle, int year) throws TMDbResponseException {
		
		TMDbResponseArray response = TMDbAPI.searchMovieByTitleAndYear(movieTitle, year);
		
		if (response.hasError()) {
			throw new TMDbResponseException(response.getStatus());
		} else {
			List<JSONObject> array = response.getData();
			List<TMDbMovieReduced> movies = new LinkedList<TMDbMovieReduced>();
			for(JSONObject json : array) {
				movies.add(new TMDbMovieReduced(json));
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
	 * @throws TMDbResponseException Throws whether the server response is not a success.
	 */
	public static List<TMDbMovieReduced> searchByTitleAndYear(String movieTitle, int year, int page) throws TMDbResponseException {
		
		TMDbResponseArray response = TMDbAPI.searchMovieByTitleAndYear(movieTitle, year, page);
		
		if (response.hasError()) {
			throw new TMDbResponseException(response.getStatus());
		} else {
			List<JSONObject> array = response.getData();
			List<TMDbMovieReduced> movies = new LinkedList<TMDbMovieReduced>();
			for(JSONObject json : array) {
				movies.add(new TMDbMovieReduced(json));
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
	 * @throws TMDbResponseException Throws whether the server response is not a success.
	 */
	public static List<TMDbMovieReduced> searchByTitleAndYear(String movieTitle, int year, boolean adult) throws TMDbResponseException {
		
		TMDbResponseArray response = TMDbAPI.searchMovieByTitleAndYear(movieTitle, year, adult);
		
		if (response.hasError()) {
			throw new TMDbResponseException(response.getStatus());
		} else {
			List<JSONObject> array = response.getData();
			List<TMDbMovieReduced> movies = new LinkedList<TMDbMovieReduced>();
			for(JSONObject json : array) {
				movies.add(new TMDbMovieReduced(json));
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
	 * @throws TMDbResponseException Throws whether the server response is not a success.
	 */
	public static List<TMDbMovieReduced> searchByTitleAndYear(String movieTitle, int year, boolean adult, int page) throws TMDbResponseException {
		
		TMDbResponseArray response = TMDbAPI.searchMovieByTitleAndYear(movieTitle, year, adult, page);
		
		if (response.hasError()) {
			throw new TMDbResponseException(response.getStatus());
		} else {
			List<JSONObject> array = response.getData();
			List<TMDbMovieReduced> movies = new LinkedList<TMDbMovieReduced>();
			for(JSONObject json : array) {
				movies.add(new TMDbMovieReduced(json));
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
	 * @throws TMDbResponseException Throws whether the server response is not a success.
	 */
	public static List<TMDbMovieReduced> fullSearchByTitleAndYear(String movieTitle, int year) throws TMDbResponseException {
		
		TMDbResponseArray response = TMDbAPI.fullSearchMovieByTitleAndYear(movieTitle, year);
		
		if (response.hasError()) {
			throw new TMDbResponseException(response.getStatus());
		} else {
			List<JSONObject> array = response.getData();
			List<TMDbMovieReduced> movies = new LinkedList<TMDbMovieReduced>();
			for(JSONObject json : array) {
				movies.add(new TMDbMovieReduced(json));
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
	 * @throws TMDbResponseException Throws whether the server response is not a success.
	 */
	public static List<TMDbMovieReduced> fullSearchByTitleAndYear(String movieTitle, int year, boolean adult) throws TMDbResponseException {
		
		TMDbResponseArray response = TMDbAPI.fullSearchMovieByTitleAndYear(movieTitle, year, adult);
		
		if (response.hasError()) {
			throw new TMDbResponseException(response.getStatus());
		} else {
			List<JSONObject> array = response.getData();
			List<TMDbMovieReduced> movies = new LinkedList<TMDbMovieReduced>();
			for(JSONObject json : array) {
				movies.add(new TMDbMovieReduced(json));
			}
			
			return movies;
		}
	}
	
	//endregion
	
}
