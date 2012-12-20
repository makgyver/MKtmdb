package mk.tmdb.core;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Date;

import mk.tmdb.entity.Account;
import mk.tmdb.entity.Token;
import mk.tmdb.utils.Log;
import mk.tmdb.utils.ResponseArray;
import mk.tmdb.utils.ResponseObject;
import mk.tmdb.utils.Status;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

/**
 * Static class that offers methods for calling the The Movie DataBase Api.
 * 
 * @author Mirko Polato
 *
 */
public final class TMDBAPI {

	//region Timeout
	
	/**
	 * Timeout in milliseconds.
	 */
	private static int timeout = 20000; // 20 seconds
	
	/**
	 * Sets the timeout to the given value (milliseconds).
	 * @param limit The timeout in milliseconds.
	 */
	public static void setTimeout(int limit) {
		if (limit < 0) limit = 0;
		timeout = limit;
	}
	
	//endregion
	
	//region API Calls
	
	/**
	 * Makes an HTTP request (GET) and gets back the result as a string.
	 * @param url The query URL
	 * @return The result string
	 */
	private static String makeApiCallGet(URL url) {
		StringBuilder result = new StringBuilder();
		try {
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setReadTimeout(timeout);
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			for (String line = null; (line = reader.readLine()) != null;) {
			    result.append(line).append("\n");
			}
			reader.close();
			
		} catch (SocketTimeoutException ste) {
			Log.print(ste);
			
			JSONObject json = new JSONObject();
			json.put(Constants.STATUS_CODE, Status.TIMEOUT);
			
			result.setLength(0);
			result.append(json.toString());
			
		} catch (Exception e) {
			Log.print(e);
			
			JSONObject json = new JSONObject();
			json.put(Constants.STATUS_CODE, Status.UNKNOWN_ERROR);
			
			result.setLength(0);
			result.append(json.toString());
		}
		
		return result.toString();
	}
	
	/**
	 * Makes an HTTP request (POST) and gets back the result as a string.
	 * @param url The query URL
	 * @param json The Json object to post
	 * @return The result string
	 */
	private static String makeApiCallPost(URL url, JSONObject json) {
		StringBuilder result = new StringBuilder();
		try {
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setDoOutput(true);
			conn.setReadTimeout(timeout);
			
			OutputStream out = conn.getOutputStream();
			OutputStreamWriter wr = new OutputStreamWriter(out);
			wr.write(json.toString());
			wr.flush();
			wr.close();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			for (String line = null; (line = reader.readLine()) != null;) {
			    result.append(line).append("\n");
			}
			reader.close();
			
		} catch (SocketTimeoutException ste) {
			Log.print(ste);
			
			JSONObject jsonErr = new JSONObject();
			jsonErr.put(Constants.STATUS_CODE, Status.TIMEOUT);
			
			result.setLength(0);
			result.append(jsonErr.toString());
			
		} catch (Exception e) {
			Log.print(e);
			
			JSONObject jsonErr = new JSONObject();
			jsonErr.put(Constants.STATUS_CODE, Status.UNKNOWN_ERROR);
			
			result.setLength(0);
			result.append(jsonErr.toString());
		}
		
		return result.toString();
	}
	
	/**
	 * Converts a string to a JSONObject
	 * @param strJson The Json string
	 * @return The JSONObject
	 */
	private static JSONObject toJSON(String strJson) {
		return (JSONObject) JSONSerializer.toJSON(strJson);
	}
	
	//endregion

	//region Configuration
	
	
	/**
	 * Gets the system wide configuration information. Some elements of the API require some knowledge 
	 * of this configuration data. This method currently holds the data relevant to building 
	 * image URLs as well as the change key map. To build an image URL, you will need 3 pieces of data. 
	 * The base_url, size and file_path. Simply combine them all and you will have a fully qualified URL.
	 *  
	 * @return The TMDB Api response object.
	 */
	public static ResponseObject getConfiguration() {
		try {
			
			return new ResponseObject(toJSON(makeApiCallGet(URLCreator.getConfigurationUrl())));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseObject(Status.MALFORMED_URL);
		}
	}
	
	//endregion
	
	//region Authentication
	
	/**
	 * This method is used to generate a valid request token for user based authentication. 
	 * A request token is required in order to request a session id. 
	 * You can generate any number of request tokens but they will expire after 60 minutes. 
	 * As soon as a valid session id has been created the token will be destroyed.
	 * 
	 * @return The TMDB Api response object.
	 */
	public static ResponseObject getAuthenticationToken() {
		try {
			
			return new ResponseObject(toJSON(makeApiCallGet(URLCreator.getAuthTokenUrl())));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseObject(Status.MALFORMED_URL);
		}
	}
	
	/**
	 * This method is used to generate a session id for user based authentication. 
	 * A session id is required in order to use any of the write methods.
	 * 
	 * @param token The authentication token
	 * @return The TMDB Api response object
	 */
	public static ResponseObject getAuthenticationSession(Token token) {
		try {
			
			return new ResponseObject(toJSON(makeApiCallGet(URLCreator.getAuthSessionUrl(token.getValue()))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseObject(Status.MALFORMED_URL);
		}
	}
	
	/**
	 * This method is used to generate a guest session id. A guest session can be used to rate movies without 
	 * having a registered TMDb user account. You should only generate a single guest session per user 
	 * (or device) as you will be able to attach the ratings to a TMDb user account in the future. 
	 * There is also IP limits in place so you should always make sure it's the end user doing the 
	 * guest session actions. If a guest session is not used for the first time within 24 hours, 
	 * it will be automatically discarded.
	 *  
	 * @return The TMDB Api response object
	 */
	public static ResponseObject getAuthenticationGuestSession() {
		try {
			
			return new ResponseObject(toJSON(makeApiCallGet(URLCreator.getGuestSessionUrl())));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseObject(Status.MALFORMED_URL);
		}
	}
	
	//endregion
	
	//region Account
	
	/**
	 * Gets the basic information for an account. 
	 * 
	 * @param sessionID The session ID
	 * @return The TMSB Api response object
	 */
	public static ResponseObject getAccountInformation(String sessionID) {
		try {
			
			return new ResponseObject(toJSON(makeApiCallGet(URLCreator.getAccountInfoUrl(sessionID))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseObject(Status.MALFORMED_URL);
		}
	}
	
	/**
	 * Gets the lists that you have created and marked as a favorite.
	 * 
	 * @param account The account information
	 * @param sessionID The session ID
	 * @return The TMDB Api response array
	 */
	public static ResponseArray getFavoritesLists(Account account, String sessionID) {
		return getFavoritesLists(account, sessionID, 1);
	}
	
	/**
	 * /**
	 * Gets the lists that you have created and marked as a favorite.
	 * 
	 * @param account The account information
	 * @param sessionID The session ID
	 * @param page The page number to retrieve
	 * @return The TMDB Api response array
	 */
	 public static ResponseArray getFavoritesLists(Account account, String sessionID, int page) {
		try {
			
			return new ResponseArray(toJSON(makeApiCallGet(URLCreator.getAccountFavsListsUrl(account.getId(), sessionID, page))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseArray(Status.MALFORMED_URL);
		}
	}
	
	 /**
	  * Gets the list of favorite movies for an account.
	  * 
	  * @param account The account information
	  * @param sessionID The session ID
	  * @return The TMDB Api response array.
	  */
	public static ResponseArray getFavoritesMovies(Account account, String sessionID) {
		return getFavoritesMovies(account, sessionID, 1);
	}
	
	 /**
	  * Gets the list of favorite movies for an account.
	  * 
	  * @param account The account information
	  * @param sessionID The session ID
	  * @param page The page number to retrieve
	  * @return The TMDB Api response array.
	  */
	public static ResponseArray getFavoritesMovies(Account account, String sessionID, int page) {
		try {
			
			return new ResponseArray(toJSON(makeApiCallGet(URLCreator.getAccountFavsMoviesUrl(account.getId(), sessionID, page))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseArray(Status.MALFORMED_URL);
		}
	}
	
	/**
	 * Adds a movie to an accounts favorite list.
	 * 
	 * @param account The account information
	 * @param sessionID The session ID
	 * @param movieID The movie ID
	 * @return The TMDB Api response object
	 */
	public static ResponseObject addMovieToFavorites(Account account, String sessionID, int movieID) {
		try {
			
			JSONObject json = new JSONObject();
			json.put(Constants.MOVIE_ID, movieID);
			json.put(Constants.FAVORITE, true);
			
			return new ResponseObject(toJSON(makeApiCallPost(URLCreator.addMovieToFavsUrl(account.getId(), sessionID), json)));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseObject(Status.MALFORMED_URL);
		}
	}
	
	/**
	 * Removes a movie to an accounts favorite list.
	 * 
	 * @param account The account information
	 * @param sessionID The session ID
	 * @param movieID The movie ID
	 * @return The TMDB Api response object
	 */
	public static ResponseObject removeMovieFromFavorites(Account account, String sessionID, int movieID) {
		try {
			
			JSONObject json = new JSONObject();
			json.put(Constants.MOVIE_ID, movieID);
			json.put(Constants.FAVORITE, false);
			
			return new ResponseObject(toJSON(makeApiCallPost(URLCreator.removeMovieFromFavsUrl(account.getId(), sessionID), json)));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseObject(Status.MALFORMED_URL);
		}
	}
	
	/**
	 * Gets the list of rated movies for an account.
	 * 
	 * @param account The account information
	 * @param sessionID The session ID
	 * @return The TMDB Api response array
	 */
	public static ResponseArray getRatedMovies(Account account, String sessionID) {
		return getRatedMovies(account, sessionID, 1);
	}
	
	/**
	 * Gets the list of rated movies for an account.
	 * 
	 * @param account The account information
	 * @param sessionID The session ID
	 * @param page The page number to retrieve
	 * @return The TMDB Api response array
	 */
	public static ResponseArray getRatedMovies(Account account, String sessionID, int page) {
		try {
			
			return new ResponseArray(toJSON(makeApiCallGet(URLCreator.getRatedMoviesUrl(account.getId(), sessionID, page))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseArray(Status.MALFORMED_URL);
		}
	}
	
	/**
	 * Gets the list of movies on an accounts watchlist.
	 * 
	 * @param account The account information
	 * @param sessionID The session ID
	 * @return The TMDB Api response array
	 */
	public static ResponseArray getMovieWatchList(Account account, String sessionID) {
		return getMovieWatchList(account, sessionID, 1);
	}
	
	/**
	 * Gets the list of movies on an accounts watchlist.
	 * 
	 * @param account The account information
	 * @param sessionID The session ID
	 * @param page The page number to retrieve
	 * @return The TMDB Api response array
	 */
	public static ResponseArray getMovieWatchList(Account account, String sessionID, int page) {
		try {
			
			return new ResponseArray(toJSON(makeApiCallGet(URLCreator.getWatchlistUrl(account.getId(), sessionID, page))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseArray(Status.MALFORMED_URL);
		}
	}
	
	/**
	 * Adds a movie to an accounts watch list.
	 * 
	 * @param account The account information
	 * @param sessionID The session ID
	 * @param movieID The movie ID
	 * @return The TMDB Api response object
	 */
	public static ResponseObject addMovieToWatchlist(Account account, String sessionID, int movieID) {
		try {
			
			JSONObject json = new JSONObject();
			json.put(Constants.MOVIE_ID, movieID);
			json.put(Constants.WATCHLIST, true);
			
			return new ResponseObject(toJSON(makeApiCallPost(URLCreator.addMovieToWatchlistUrl(account.getId(), sessionID), json)));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseObject(Status.MALFORMED_URL);
		}
	}
	
	/**
	 * Removes a movie to an accounts watch list.
	 * 
	 * @param account The account information
	 * @param sessionID The session ID
	 * @param movieID The movie ID
	 * @return The TMDB Api response object
	 */
	public static ResponseObject removeMovieFromWatchlist(Account account, String sessionID, int movieID) {
		try {
			
			JSONObject json = new JSONObject();
			json.put(Constants.MOVIE_ID, movieID);
			json.put(Constants.WATCHLIST, false);
			
			return new ResponseObject(toJSON(makeApiCallPost(URLCreator.removeMovieFromWatchlistUrl(account.getId(), sessionID), json)));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseObject(Status.MALFORMED_URL);
		}
	}
	
	//endregion
	
	//region Movie
	
	/**
	 * Gets the basic movie information for a specific movie id.
	 * 
	 * @param movieID The movie ID
	 * @return The TMDB Api response object
	 */
	public static ResponseObject getMovieInformation(int movieID) {
		try {
			return new ResponseObject(toJSON(makeApiCallGet(URLCreator.getMovieInfoUrl(movieID))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseObject(Status.MALFORMED_URL);
		}
	}
	
	/**
	 * Gets the alternative titles for a specific movie id.
	 * 
	 * @param movieID The movie ID
	 * @return The TMDB Api response object
	 */
	public static ResponseObject getAlternativeMovieTitles(int movieID) {
		try {
			return new ResponseObject(toJSON(makeApiCallGet(URLCreator.getAlternativeMovieTitlesUrl(movieID))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseObject(Status.MALFORMED_URL);
		}
	}
	
	/**
	 * Gets the cast information for a specific movie id.
	 * 
	 * @param movieID The movie ID
	 * @return The TMDB Api response object
	 */
	public static ResponseObject getCastInformation(int movieID) {
		try {
			return new ResponseObject(toJSON(makeApiCallGet(URLCreator.getCastInfoUrl(movieID))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseObject(Status.MALFORMED_URL);
		}
	}
	
	/**
	 * Gets the images (posters and backdrops) for a specific movie id.
	 * 
	 * @param movieID The movie ID
	 * @return The TMDB Api response object
	 */
	public static ResponseObject getMovieImages(int movieID) {
		try {
			return new ResponseObject(toJSON(makeApiCallGet(URLCreator.getMovieImagesUrl(movieID))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseObject(Status.MALFORMED_URL);
		}
	}
	
	/**
	 * Gets the plot keywords for a specific movie id.
	 * 
	 * @param movieID The movie ID
	 * @return The TMDB Api response object
	 */
	public static ResponseObject getMovieKeywords(int movieID) {
		try {
			return new ResponseObject(toJSON(makeApiCallGet(URLCreator.getMovieKeywordsUrl(movieID))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseObject(Status.MALFORMED_URL);
		}
	}
	
	/**
	 * Gets the release date by country for a specific movie id.
	 * 
	 * @param movieID The movie ID
	 * @return The TMDB Api response object
	 */
	public static ResponseObject getMovieReleases(int movieID) {
		try {
			return new ResponseObject(toJSON(makeApiCallGet(URLCreator.getMovieReleasesDatesUrl(movieID))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseObject(Status.MALFORMED_URL);
		}
	}
	
	/**
	 * Gets the trailers for a specific movie id.
	 * 
	 * @param movieID The movie ID
	 * @return The TMDB Api response object
	 */
	public static ResponseObject getMovieTrailers(int movieID) {
		try {
			return new ResponseObject(toJSON(makeApiCallGet(URLCreator.getMovieTrailersUrl(movieID))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseObject(Status.MALFORMED_URL);
		}
	}
	
	/**
	 * Gets the translations for a specific movie id.
	 * 
	 * @param movieID The movie ID
	 * @return The TMDB Api response object
	 */
	public static ResponseObject getMovieTranslations(int movieID) {
		try {
			return new ResponseObject(toJSON(makeApiCallGet(URLCreator.getMovieTranslationsUrl(movieID))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseObject(Status.MALFORMED_URL);
		}
	}
	
	/**
	 * Gets the similar movies for a specific movie id.
	 * 
	 * @param movieID The movie ID
	 * @return The TMDB Api response object
	 */
	public static ResponseArray getSimilarMovies(int movieID) {
		return getSimilarMovies(movieID, 1);
	}
	
	/**
	 * Gets the similar movies for a specific movie id.
	 * 
	 * @param movieID The movie ID
	 * @param page The page number to retrieve
	 * @return The TMDB Api response array
	 */
	public static ResponseArray getSimilarMovies(int movieID, int page) {
		try {
			return new ResponseArray(toJSON(makeApiCallGet(URLCreator.getSimilarMoviesUrl(movieID, page))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseArray(Status.MALFORMED_URL);
		}
	}
	
	/**
	 * Get the changes for a specific movie id. Changes are grouped by key, 
	 * and ordered by date in descending order. By default, only the last 24 hours of changes are returned. 
	 * The maximum number of days that can be returned in a single request is 14. 
	 * The language is present on fields that are translatable.
	 * 
	 * @param movieID The movie ID
	 * @return The TMDB Api response object
	 */
	public static ResponseObject getMovieChanges(int movieID) {
		try {
			return new ResponseObject(toJSON(makeApiCallGet(URLCreator.getMovieChangesUrl(movieID))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseObject(Status.MALFORMED_URL);
		}
	}
	
	/**
	 * Gets the lists that the movie belongs to.
	 * 
	 * @param movieID The movie ID
	 * @return The TMDB Api response array
	 */
	public static ResponseArray getListsBelongsToMovie(int movieID) {
		return getListsBelongsToMovie(movieID, 1);
	}
	
	/**
	 * Gets the lists that the movie belongs to.
	 * 
	 * @param movieID The movie ID
	 * @param page The page number to retrieve
	 * @return The TMDB Api response array
	 */
	public static ResponseArray getListsBelongsToMovie(int movieID, int page) {
		try {
			return new ResponseArray(toJSON(makeApiCallGet(URLCreator.getListsBelongsToMovieUrl(movieID, page))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseArray(Status.MALFORMED_URL);
		}
	}
	
	/**
	 * Gets the lastest movie id.
	 * 
	 * @return The TMDB Api response object
	 */
	public static ResponseObject getLatestMovie() {
		try {
			return new ResponseObject(toJSON(makeApiCallGet(URLCreator.getLatestMovieUrl())));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseObject(Status.MALFORMED_URL);
		}
	}
	
	/**
	 * Gets the list of upcoming movies. This list refreshes every day. 
	 * The maximum number of items this list will include is 100.
	 * 
	 * @return The TMDB Api response array
	 */
	public static ResponseArray getUpcomingMovies() {
		return getUpcomingMovies(1);
	}
	
	/**
	 * Gets the list of upcoming movies. This list refreshes every day. 
	 * The maximum number of items this list will include is 100.
	 * 
	 * @param page The page number to retrieve
	 * @return The TMDB Api response array
	 */
	public static ResponseArray getUpcomingMovies(int page) {
		try {
			return new ResponseArray(toJSON(makeApiCallGet(URLCreator.getUpcomingMoviesListUrl(page))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseArray(Status.MALFORMED_URL);
		}
	}
	
	/**
	 * Gets the list of all upcoming movies. This list refreshes every day. 
	 * The maximum number of items this list will include is 100.
	 * 
	 * @return The TMDB Api response array
	 */
	public static ResponseArray getAllUpcomingMovies() {
		try {
			
			ResponseArray result = new ResponseArray(toJSON(makeApiCallGet(URLCreator.getUpcomingMoviesListUrl())));
			
			for (int p = 2; p <= result.getPages(); p++) {
				ResponseArray page = new ResponseArray(toJSON(makeApiCallGet(URLCreator.getUpcomingMoviesListUrl(p))));
				for (Object obj : page.getData()) {
					result.addData((JSONObject) obj);
				}
			}
			
			return result;
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseArray(Status.MALFORMED_URL);
		}
	}
	
	/**
	 * Gets the list of movies playing in theatres. This list refreshes every day. 
	 * The maximum number of items this list will include is 100.
	 * 
	 * @return The TMDB Api response array
	 */
	public static ResponseArray getInTheatresMovies() {
		return getInTheatresMovies(1);
	}
	
	/**
	 * Gets the list of movies playing in theatres. This list refreshes every day. 
	 * The maximum number of items this list will include is 100.
	 * 
	 * @param page The page number to retrieve
	 * @return The TMDB Api response array
	 */
	public static ResponseArray getInTheatresMovies(int page) {
		try {
			return new ResponseArray(toJSON(makeApiCallGet(URLCreator.getInTheatresMoviesUrl(page))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseArray(Status.MALFORMED_URL);
		}
	}
	
	/**
	 * Gets the list of all movies playing in theatres. This list refreshes every day. 
	 * The maximum number of items this list will include is 100.
	 * 
	 * @return The TMDB Api response array
	 */
	public static ResponseArray getAllInTheatresMovies() {
		try {
			
			ResponseArray result = new ResponseArray(toJSON(makeApiCallGet(URLCreator.getInTheatresMoviesUrl())));
			
			for (int p = 2; p <= result.getPages(); p++) {
				ResponseArray page = new ResponseArray(toJSON(makeApiCallGet(URLCreator.getInTheatresMoviesUrl(p))));
				for (Object obj : page.getData()) {
					result.addData((JSONObject) obj);
				}
			}
			
			return result;
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseArray(Status.MALFORMED_URL);
		}
	}
	
	/**
	 * Gets the list of popular movies on The Movie Database. This list refreshes every day.
	 * 
	 * @return The TMDB Api response array
	 */
	public static ResponseArray getPopularMovies() {
		return getPopularMovies(1);
	}
	
	/**
	 * Gets the list of popular movies on The Movie Database. This list refreshes every day.
	 * 
	 * @param page The page number to retrieve
	 * @return The TMDB Api response array
	 */
	public static ResponseArray getPopularMovies(int page) {
		try {
			return new ResponseArray(toJSON(makeApiCallGet(URLCreator.getPopularMoviesUrl(page))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseArray(Status.MALFORMED_URL);
		}
	}
	
	/**
	 * Gets the list of all popular movies on The Movie Database. This list refreshes every day.
	 * 
	 * @return The TMDB Api response array
	 */
	public static ResponseArray getAllPopularMovies() {
		try {
			
			ResponseArray result = new ResponseArray(toJSON(makeApiCallGet(URLCreator.getPopularMoviesUrl())));
			
			for (int p = 2; p <= result.getPages(); p++) {
				ResponseArray page = new ResponseArray(toJSON(makeApiCallGet(URLCreator.getPopularMoviesUrl(p))));
				for (Object obj : page.getData()) {
					result.addData((JSONObject) obj);
				}
			}
			
			return result;
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseArray(Status.MALFORMED_URL);
		}
	}
	
	/**
	 * Gets the list of top rated movies. 
	 * By default, this list will only include movies that have 10 or more votes. This list refreshes every day.
	 * 
	 * @return The TMDB Api response array
	 */
	public static ResponseArray getTopRatedMovies() {
		return getTopRatedMovies(1);
	}
	
	/**
	 * Gets the list of top rated movies. 
	 * By default, this list will only include movies that have 10 or more votes. This list refreshes every day.
	 * 
	 * @param page The page number to retrieve
	 * @return The TMDB Api response array
	 */
	public static ResponseArray getTopRatedMovies(int page) {
		try {
			return new ResponseArray(toJSON(makeApiCallGet(URLCreator.getTopRatedMoviesUrl(page))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseArray(Status.MALFORMED_URL);
		}
	}
	
	/**
	 * Gets the list of all top rated movies. 
	 * By default, this list will only include movies that have 10 or more votes. This list refreshes every day.
	 * 
	 * @return The TMDB Api response array
	 */
	public static ResponseArray getAllTopRatedMovies() {
		try {
			
			ResponseArray result = new ResponseArray(toJSON(makeApiCallGet(URLCreator.getTopRatedMoviesUrl())));
			
			for (int p = 2; p <= result.getPages(); p++) {
				ResponseArray page = new ResponseArray(toJSON(makeApiCallGet(URLCreator.getTopRatedMoviesUrl(p))));
				for (Object obj : page.getData()) {
					result.addData((JSONObject) obj);
				}
			}
			
			return result;
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseArray(Status.MALFORMED_URL);
		}
	}
	
	/**
	 * This method lets users rate a movie.
	 * 
	 * @param sessionID The session ID
	 * @param guest Whether the session is a guest session
	 * @param movieID The movie ID
	 * @param rating The rating of the movie
	 * @return The TMDB Api response object
	 */
	public static ResponseObject setMovieRate(String sessionID, boolean guest, int movieID, float rating) {
		try {
			
			DecimalFormat oneDigit = new DecimalFormat("#,##0.0");
			String strValue = oneDigit.format(rating);
			
			JSONObject json = new JSONObject();
			json.put(Constants.VALUE, strValue);
			
			return new ResponseObject(toJSON(makeApiCallPost(URLCreator.setMovieRateUrl(sessionID, movieID, guest), json)));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseObject(Status.MALFORMED_URL);
		}
	}
	
	//endregion
	
	//region People
	
	/**
	 * Gets the general person information for a specific id.
	 * 
	 * @param personID The person ID
	 * @return The TMDB Api response object
	 */
	public static ResponseObject getPersonInformation(int personID) {
		try {
			return new ResponseObject(toJSON(makeApiCallGet(URLCreator.getPersonInfoUrl(personID))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseObject(Status.MALFORMED_URL);
		}
	}
	
	/**
	 * Gets the credits for a specific person id.
	 * 
	 * @param personID The person ID
	 * @return The TMDB Api response object
	 */
	public static ResponseObject getPersonCredits(int personID) {
		try {
			return new ResponseObject(toJSON(makeApiCallGet(URLCreator.getPersonCreditsUrl(personID))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseObject(Status.MALFORMED_URL);
		}
	}
	
	/**
	 * Gets the images for a specific person id.
	 * 
	 * @param personID The person ID
	 * @return The TMDB Api response object
	 */
	public static ResponseObject getPersonImages(int personID) {
		try {
			return new ResponseObject(toJSON(makeApiCallGet(URLCreator.getPersonImagesUrl(personID))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseObject(Status.MALFORMED_URL);
		}
	}
	
	/**
	 * Gets the changes for a specific person id. 
	 * Changes are grouped by key, and ordered by date in descending order. 
	 * By default, only the last 24 hours of changes are returned. 
	 * The maximum number of days that can be returned in a single request is 14. 
	 * The language is present on fields that are translatable.
	 * 
	 * @param personID The person ID
	 * @return The TMDB Api response object
	 */
	public static ResponseObject getPersonChanges(int personID) {
		try {
			return new ResponseObject(toJSON(makeApiCallGet(URLCreator.getPersonChangesUrl(personID))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseObject(Status.MALFORMED_URL);
		}
	}
	
	//TODO: dates!!!
	
	/**
	 * Gets the latest person id.
	 * 
	 * @return The TMDB Api response object
	 */
	public static ResponseObject getLatestPerson() {
		try {
			return new ResponseObject(toJSON(makeApiCallGet(URLCreator.getLatestPerson())));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseObject(Status.MALFORMED_URL);
		}
	}
	
	//endregion
	
	//region Collections
	
	/**
	 * Gets the basic collection information for a specific collection id. 
	 * You can get the ID needed for this method by making a /movie/{id} request 
	 * and paying attention to the belongs_to_collection hash. Movie parts are not sorted in any particular order. 
	 * If you would like to sort them yourself you can use the provided release_date.
	 * 
	 * @param collectionID The collection ID
	 * @return The TMDB Api response object
	 */
	public static ResponseObject getCollectionInformation(int collectionID) {
		try {
			return new ResponseObject(toJSON(makeApiCallGet(URLCreator.getCollectionInfoUrl(collectionID))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseObject(Status.MALFORMED_URL);
		}
	}
	
	/**
	 * Gets all of the images for a particular collection by collection id.
	 * 
	 * @param collectionID The collection ID
	 * @return The TMDB Api response object
	 */
	public static ResponseObject getCollectionImages(int collectionID) {
		try {
			return new ResponseObject(toJSON(makeApiCallGet(URLCreator.getCollectionInfoUrl(collectionID))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseObject(Status.MALFORMED_URL);
		}
	}
	
	//endregion
	
	//region Lists
	
	/**
	 * Gets a list by id.
	 * 
	 * @param listID The list ID
	 * @return The TMDB Api response object
	 */
	public static ResponseObject getList(int listID) {
		try {
			return new ResponseObject(toJSON(makeApiCallGet(URLCreator.getListUrl(listID))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseObject(Status.MALFORMED_URL);
		}
	}
	
	//endregion
	
	//region Company
	
	/**
	 * This method is used to retrieve all of the basic information about a company.
	 * 
	 * @param companyID The company ID
	 * @return The TMDB Api response object
	 */
	public static ResponseObject getCompanyInformation(int companyID) {
		try {
			return new ResponseObject(toJSON(makeApiCallGet(URLCreator.getCompanyInfoUrl(companyID))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseObject(Status.MALFORMED_URL);
		}
	}
	
	/**
	 * Gets the list of movies associated with a particular company.
	 * 
	 * @param companyID The company ID
	 * @return The TMDB Api response array
	 */
	public static ResponseArray getMoviesListByCompany(int companyID) {
		return getMoviesListByCompany(companyID, 1);
	}
	
	/**
	 * Gets the list of movies associated with a particular company.
	 * 
	 * @param companyID The company ID
	 * @param page The page number to retrieve
	 * @return The TMDB Api response array
	 */
	public static ResponseArray getMoviesListByCompany(int companyID, int page) {
		try {
			return new ResponseArray(toJSON(makeApiCallGet(URLCreator.getMoviesListByCompanyUrl(companyID, page))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseArray(Status.MALFORMED_URL);
		}
	}
	
	//endregion
	
	//region Genre
	
	/**
	 * Get the list of genres.
	 * 
	 * @return The TMDB Api response object
	 */
	public static ResponseObject getGenresList() {
		try {
			return new ResponseObject(toJSON(makeApiCallGet(URLCreator.getGenresListUrl())));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseObject(Status.MALFORMED_URL);
		}
	}
	
	/**
	 * Gets the list of movies for a particular genre by id. 
	 * By default, only movies with 10 or more votes are included.
	 * 
	 * @param genreID The genre ID
	 * @return The TMDB Api response array
	 */
	public static ResponseArray getMoviesListByGenre(int genreID) {
		return getMoviesListByGenre(genreID, 1);
	}
	
	/**
	 * Gets the list of movies for a particular genre by id. 
	 * By default, only movies with 10 or more votes are included.
	 * 
	 * @param genreID The genre ID
	 * @param page The page number to retrieve
	 * @return The TMDB Api response array
	 */
	public static ResponseArray getMoviesListByGenre(int genreID, int page) {
		try {
			return new ResponseArray(toJSON(makeApiCallGet(URLCreator.getMoviesListByGenreUrl(genreID, page))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseArray(Status.MALFORMED_URL);
		}
	}
	
	//endregion
	
	//region Search
	
	/**
	 * Searches for movies by title.
	 * 
	 * @param movieTitle The movie title
	 * @return The TMDB Api response array
	 */
	public static ResponseArray searchMovieByTitle(String movieTitle) {
		return searchMovieByTitle(movieTitle, 1);
	}
	
	/**
	 * Searches for movies by title.
	 * 
	 * @param movieTitle The movie title
	 * @param page The page number to retrieve
	 * @return The TMDB Api response array
	 */
	public static ResponseArray searchMovieByTitle(String movieTitle, int page) {
		try {
			return new ResponseArray(toJSON(makeApiCallGet(URLCreator.searchMovieByTitleUrl(movieTitle, page))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseArray(Status.MALFORMED_URL);
		}
	}
	
	/**
	 * Searches for movies by title and yer.
	 * 
	 * @param movieTitle The movie title
	 * @param year The movie year
	 * @return The TMDB Api response array
	 */
	public static ResponseArray searchMovieByTitleAndYear(String movieTitle, int year) {
		return searchMovieByTitleAndYear(movieTitle, year, 1);
	}
	
	/**
	 * Searches for movies by title and year.
	 * 
	 * @param movieTitle The movie title
	 * @param year The movie year
	 * @param page the page number to retrieve
	 * @return The TMDB Api response array
	 */
	public static ResponseArray searchMovieByTitleAndYear(String movieTitle, int year, int page) {
		try {
			return new ResponseArray(toJSON(makeApiCallGet(URLCreator.searchMovieByTitleAndYearUrl(movieTitle, year, page))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseArray(Status.MALFORMED_URL);
		}
	}
	
	/**
	 * Searches for movies by title.
	 * 
	 * @param movieTitle The movie title
	 * @param adult Whether the movie audience is adult only
	 * @return The TMDB Api response array
	 */
	public static ResponseArray searchMovieByTitle(String movieTitle, boolean adult) {
		return searchMovieByTitle(movieTitle, adult, 1);
	}
	
	/**
	 * Searches for movies by title.
	 * 
	 * @param movieTitle The movie title
	 * @param adult Whether the movie audience is adult only
	 * @param page The page number to retrieve
	 * @return The TMDB Api response array
	 */
	public static ResponseArray searchMovieByTitle(String movieTitle, boolean adult, int page) {
		try {
			return new ResponseArray(toJSON(makeApiCallGet(URLCreator.searchMovieByTitleUrl(movieTitle, adult, page))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseArray(Status.MALFORMED_URL);
		}
	}
	
	/**
	 * Searches for movies by title.
	 * 
	 * @param movieTitle The movie title
	 * @param year The movie year
	 * @param adult Whether the movie audience is adult only
	 * @return The TMDB Api response array
	 */
	public static ResponseArray searchMovieByTitle(String movieTitle, int year, boolean adult) {
		return searchMovieByTitle(movieTitle, year, adult, 1);
	}
	
	/**
	 * Searches for movies by title.
	 * 
	 * @param movieTitle The movie title
	 * @param year The movie year
	 * @param adult Whether the movie audience is adult only
	 * @param page The page number to retrieve
	 * @return The TMDB Api response array
	 */
	public static ResponseArray searchMovieByTitle(String movieTitle, int year, boolean adult, int page) {
		try {
			return new ResponseArray(toJSON(makeApiCallGet(URLCreator.searchMovieByTitleUrl(movieTitle, year, adult, page))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseArray(Status.MALFORMED_URL);
		}
	}
	
	/**
	 * Searches for people by name.
	 * 
	 * @param name The person name
	 * @return The TMDB Api response array
	 */
	public static ResponseArray searchPersonByName(String name) {
		return searchPersonByName(name, 1);
	}
	
	/**
	 * Searches for people by name.
	 * 
	 * @param name The person name
	 * @param page The page number to retrieve
	 * @return The TMDB Api response array
	 */
	public static ResponseArray searchPersonByName(String name, int page) {
		try {
			return new ResponseArray(toJSON(makeApiCallGet(URLCreator.searchPeopleByNameUrl(name, page))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseArray(Status.MALFORMED_URL);
		}
	}
	
	/**
	 * Searches for people by name.
	 * 
	 * @param name The person name
	 * @param adult Whether the movie audience is adult only
	 * @return The TMDB Api response array
	 */
	public static ResponseArray searchPersonByName(String name, boolean adult) {
		return searchPersonByName(name, adult, 1);
	}
	
	/**
	 * Searches for people by name.
	 * 
	 * @param name The person name
	 * @param adult Whether the movie audience is adult only
	 * @param page The page number to retrieve
	 * @return The TMDB Api response array
	 */
	public static ResponseArray searchPersonByName(String name, boolean adult, int page) {
		try {
			return new ResponseArray(toJSON(makeApiCallGet(URLCreator.searchPeopleByNameUrl(name, adult, page))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseArray(Status.MALFORMED_URL);
		}
	}
	
	/**
	 * Searches for company by name.
	 * 
	 * @param name The company name
	 * @return The TMDB Api response array
	 */
	public static ResponseArray searchCompanyByName(String name) {
		return searchCompanyByName(name, 1);
	}
	
	/**
	 * Searches for company by name.
	 * 
	 * @param name The company name
	 * @param page The page number to retrieve
	 * @return The TMDB Api response array
	 */
	public static ResponseArray searchCompanyByName(String name, int page) {
		try {
			return new ResponseArray(toJSON(makeApiCallGet(URLCreator.searchCompanyByNameUrl(name, page))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseArray(Status.MALFORMED_URL);
		}
	}
	
	//endregion
	
	//region Changes
	
	/**
	 * Gets a list of movie ids that have been edited. 
	 * By default we show the last 24 hours and only 100 items per page. 
	 * The maximum number of days that can be returned in a single request is 14. 
	 * You can then use the {@link #getMovieChanges(int) getMovieChanges} method to get the actual data that has been changed.
	 * Please note that the change log system to support this was changed on 
	 * October 5, 2012 and will only show movies that have been edited since.
	 * 
	 * @return The TMDB Api response array
	 */
	public static ResponseArray getChangedMovies() {
		return getChangedMovies(1);
	}
	
	/**
	 * Gets a list of people ids that have been edited. 
	 * By default we show the last 24 hours and only 100 items per page. 
	 * The maximum number of days that can be returned in a single request is 14. 
	 * You can then use the {@link #getPersonChanges(int) getPersonChanges} method to get the actual data that has been changed. 
	 * Please note that the change log system to support this was changed on 
	 * October 5, 2012 and will only show people that have been edited since.
	 * 
	 * @return The TMDB Api response array
	 */
	public static ResponseArray getChangedPersons() {
		return getChangedPersons(1);
	}
	
	/**
	 * Gets a list of movie ids that have been edited. 
	 * By default we show the last 24 hours and only 100 items per page. 
	 * The maximum number of days that can be returned in a single request is 14. 
	 * You can then use the {@link #getMovieChanges(int) getMovieChanges} method to get the actual data that has been changed.
	 * Please note that the change log system to support this was changed on 
	 * October 5, 2012 and will only show movies that have been edited since.
	 * 
	 * @param page The page number to retrieve
	 * @return The TMDB Api response array
	 */
	public static ResponseArray getChangedMovies(int page) {
		try {
			return new ResponseArray(toJSON(makeApiCallGet(URLCreator.getChangedMoviesUrl(page))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseArray(Status.MALFORMED_URL);
		}
	}
	
	/**
	 * Gets a list of people ids that have been edited. 
	 * By default we show the last 24 hours and only 100 items per page. 
	 * The maximum number of days that can be returned in a single request is 14. 
	 * You can then use the {@link #getPersonChanges(int) getPersonChanges} method to get the actual data that has been changed. 
	 * Please note that the change log system to support this was changed on 
	 * October 5, 2012 and will only show people that have been edited since.
	 * 
	 * @param page The page number to retrieve
	 * @return The TMDB Api response array
	 */
	public static ResponseArray getChangedPersons(int page) {
		try {
			return new ResponseArray(toJSON(makeApiCallGet(URLCreator.getChangedPersonsUrl(page))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseArray(Status.MALFORMED_URL);
		}
	}
	
	/**
	 * Gets a list of movie ids that have been edited. 
	 * By default we show the last 24 hours and only 100 items per page. 
	 * The maximum number of days that can be returned in a single request is 14. 
	 * You can then use the {@link #getMovieChanges(int) getMovieChanges} method to get the actual data that has been changed.
	 * Please note that the change log system to support this was changed on 
	 * October 5, 2012 and will only show movies that have been edited since.
	 * 
	 * @param start The date where the search starts 
	 * @param end The date where the search ends
	 * @return The TMDB Api response array
	 */
	public static ResponseArray getChangedMovies(Date start, Date end) {
		return getChangedMovies(start, end, 1);
	}
	
	/**
	 * Gets a list of people ids that have been edited. 
	 * By default we show the last 24 hours and only 100 items per page. 
	 * The maximum number of days that can be returned in a single request is 14. 
	 * You can then use the {@link #getPersonChanges(int) getPersonChanges} method to get the actual data that has been changed. 
	 * Please note that the change log system to support this was changed on 
	 * October 5, 2012 and will only show people that have been edited since.
	 * 
	 * @param start The date where the search starts 
	 * @param end The date where the search ends 
	 * @return The TMDB Api response array
	 */
	public static ResponseArray getChangedPersons(Date start, Date end) {
		return getChangedPersons(start, end, 1);
	}
	
	/**
	 * Gets a list of movie ids that have been edited. 
	 * By default we show the last 24 hours and only 100 items per page. 
	 * The maximum number of days that can be returned in a single request is 14. 
	 * You can then use the {@link #getMovieChanges(int) getMovieChanges} method to get the actual data that has been changed.
	 * Please note that the change log system to support this was changed on 
	 * October 5, 2012 and will only show movies that have been edited since.
	 * 
	 * @param start The date where the search starts 
	 * @param end The date where the search ends
	 * @param page The page number to retrieve
	 * @return The TMDB Api response array
	 */
	public static ResponseArray getChangedMovies(Date start, Date end, int page) {
		try {
			return new ResponseArray(toJSON(makeApiCallGet(URLCreator.getChangedMoviesUrl(start, end, page))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseArray(Status.MALFORMED_URL);
		}
	}
	
	/**
	 * Gets a list of people ids that have been edited. 
	 * By default we show the last 24 hours and only 100 items per page. 
	 * The maximum number of days that can be returned in a single request is 14. 
	 * You can then use the {@link #getPersonChanges(int) getPersonChanges} method to get the actual data that has been changed. 
	 * Please note that the change log system to support this was changed on 
	 * October 5, 2012 and will only show people that have been edited since.
	 * 
	 * @param start The date where the search starts 
	 * @param end The date where the search ends
	 * @param page The page number to retrieve 
	 * @return The TMDB Api response array
	 */
	public static ResponseArray getChangedPersons(Date start, Date end, int page) {
		try {
			return new ResponseArray(toJSON(makeApiCallGet(URLCreator.getChangedPersonsUrl(start, end, page))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseArray(Status.MALFORMED_URL);
		}
	}
	
	/**
	 * Gets a list of movie ids that have been edited. 
	 * By default we show the last 24 hours and only 100 items per page. 
	 * The maximum number of days that can be returned in a single request is 14. 
	 * You can then use the {@link #getMovieChanges(int) getMovieChanges} method to get the actual data that has been changed.
	 * Please note that the change log system to support this was changed on 
	 * October 5, 2012 and will only show movies that have been edited since.
	 * 
	 * @param start The date where the search starts 
	 * @param end The date where the search ends
	 * @return The TMDB Api response array
	 */
	public static ResponseArray getChangedMovies(String start, String end) {
		return getChangedMovies(start, end, 1);
	}
	
	/**
	 * Gets a list of people ids that have been edited. 
	 * By default we show the last 24 hours and only 100 items per page. 
	 * The maximum number of days that can be returned in a single request is 14. 
	 * You can then use the {@link #getPersonChanges(int) getPersonChanges} method to get the actual data that has been changed. 
	 * Please note that the change log system to support this was changed on 
	 * October 5, 2012 and will only show people that have been edited since.
	 * 
	 * @param start The date where the search starts 
	 * @param end The date where the search ends 
	 * @return The TMDB Api response array
	 */
	public static ResponseArray getChangedPersons(String start, String end) {
		return getChangedPersons(start, end, 1);
	}
	
	/**
	 * Gets a list of movie ids that have been edited. 
	 * By default we show the last 24 hours and only 100 items per page. 
	 * The maximum number of days that can be returned in a single request is 14. 
	 * You can then use the {@link #getMovieChanges(int) getMovieChanges} method to get the actual data that has been changed.
	 * Please note that the change log system to support this was changed on 
	 * October 5, 2012 and will only show movies that have been edited since.
	 * 
	 * @param start The date where the search starts 
	 * @param end The date where the search ends
	 * @param page The page number to retrieve
	 * @return The TMDB Api response array
	 */
	public static ResponseArray getChangedMovies(String start, String end, int page) {
		try {
			return new ResponseArray(toJSON(makeApiCallGet(URLCreator.getChangedMoviesUrl(start, end, page))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseArray(Status.MALFORMED_URL);
		}
	}
	
	/**
	 * Gets a list of people ids that have been edited. 
	 * By default we show the last 24 hours and only 100 items per page. 
	 * The maximum number of days that can be returned in a single request is 14. 
	 * You can then use the {@link #getPersonChanges(int) getPersonChanges} method to get the actual data that has been changed. 
	 * Please note that the change log system to support this was changed on 
	 * October 5, 2012 and will only show people that have been edited since.
	 * 
	 * @param start The date where the search starts 
	 * @param end The date where the search ends 
	 * @param page The page number to retrieve
	 * @return The TMDB Api response array
	 */
	public static ResponseArray getChangedPersons(String start, String end, int page) {
		try {
			return new ResponseArray(toJSON(makeApiCallGet(URLCreator.getChangedPersonsUrl(start, end, page))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseArray(Status.MALFORMED_URL);
		}
	}
	
	//endregion
	
}
