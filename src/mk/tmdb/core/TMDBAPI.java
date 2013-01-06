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

import mk.tmdb.entity.TMDbAccount;
import mk.tmdb.entity.TMDbToken;
import mk.tmdb.response.TMDbResponseArray;
import mk.tmdb.response.TMDbResponseObject;
import mk.tmdb.response.TMDbStatus;
import mk.tmdb.utils.Log;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

/**
 * Static class that offers methods for calling the The Movie Database Api.
 * 
 * @author Mirko Polato
 *
 */
public final class TMDbAPI {

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
	 * 
	 * @param url The query URL
	 * @return The result string
	 */
	public static String makeApiCallGet(URL url) {
		StringBuilder result = new StringBuilder();
		try {
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setReadTimeout(timeout);
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			for (String line = null; (line = reader.readLine()) != null;) {
			    result.append(line).append("\n");
			}
			reader.close();
			
		} catch (SocketTimeoutException ste) {
			Log.print(ste);
			
			JSONObject json = new JSONObject();
			json.put(TMDbConstants.STATUS_CODE, TMDbStatus.TIMEOUT);
			
			result.setLength(0);
			result.append(json.toString());
			
		} catch (Exception e) {
			Log.print(e);
			
			JSONObject json = new JSONObject();
			json.put(TMDbConstants.STATUS_CODE, TMDbStatus.UNKNOWN_ERROR.getCode());
			
			result.setLength(0);
			result.append(json.toString());
		}
		
		return result.toString();
	}
	
	/**
	 * Makes an HTTP request (POST) and gets back the result as a string.
	 * 
	 * @param url The query URL
	 * @param json The JSON object to post
	 * @return The result string
	 */
	public static String makeApiCallPost(URL url, JSONObject json) {
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
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			for (String line = null; (line = reader.readLine()) != null;) {
			    result.append(line).append("\n");
			}
			reader.close();
			
		} catch (SocketTimeoutException ste) {
			Log.print(ste);
			
			JSONObject jsonErr = new JSONObject();
			jsonErr.put(TMDbConstants.STATUS_CODE, TMDbStatus.TIMEOUT);
			
			result.setLength(0);
			result.append(jsonErr.toString());
			
		} catch (Exception e) {
			Log.print(e);
			
			JSONObject jsonErr = new JSONObject();
			jsonErr.put(TMDbConstants.STATUS_CODE, TMDbStatus.UNKNOWN_ERROR);
			
			result.setLength(0);
			result.append(jsonErr.toString());
		}
		
		return result.toString();
	}
	
	/**
	 * Converts a string to a JSONObject.
	 * 
	 * @param strJson The JSON string
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
	 * @return The TMDb API response object.
	 */
	public static TMDbResponseObject getConfiguration() {
		try {
			
			return new TMDbResponseObject(toJSON(makeApiCallGet(TMDbURLCreator.getConfigurationUrl())));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseObject(TMDbStatus.MALFORMED_URL);
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
	 * @return The TMDb API response object.
	 */
	public static TMDbResponseObject getAuthenticationToken() {
		try {
			
			return new TMDbResponseObject(toJSON(makeApiCallGet(TMDbURLCreator.getAuthTokenUrl())));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseObject(TMDbStatus.MALFORMED_URL);
		}
	}
	
	/**
	 * This method is used to generate a session id for user based authentication. 
	 * A session id is required in order to use any of the write methods.
	 * 
	 * @param token The authentication token
	 * @return The TMDb API response object
	 */
	public static TMDbResponseObject getAuthenticationSession(TMDbToken token) {
		try {
			
			return new TMDbResponseObject(toJSON(makeApiCallGet(TMDbURLCreator.getAuthSessionUrl(token.getValue()))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseObject(TMDbStatus.MALFORMED_URL);
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
	 * @return The TMDb API response object
	 */
	public static TMDbResponseObject getAuthenticationGuestSession() {
		try {
			
			return new TMDbResponseObject(toJSON(makeApiCallGet(TMDbURLCreator.getGuestSessionUrl())));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseObject(TMDbStatus.MALFORMED_URL);
		}
	}
	
	//endregion
	
	//region Account
	
	/**
	 * Gets the basic information for an account. 
	 * 
	 * @param sessionID The session ID
	 * @return The TMSB API response object
	 */
	public static TMDbResponseObject getAccountInformation(String sessionID) {
		try {
			
			return new TMDbResponseObject(toJSON(makeApiCallGet(TMDbURLCreator.getAccountInfoUrl(sessionID))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseObject(TMDbStatus.MALFORMED_URL);
		}
	}
	
	/**
	 * Gets the lists that you have created and marked as a favorite.
	 * Returns the results of the first page.
	 * 
	 * @param account The account information
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray getFavoriteLists(TMDbAccount account) {
		return getFavoriteLists(account, 1);
	}
	
	/**
	 * Gets the lists that you have created and marked as a favorite.
	 * Returns the results of the given page number.
	 * 
	 * @param account The account information
	 * @param page The page number to retrieve
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray getFavoriteLists(TMDbAccount account, int page) {
		try {
			
			return new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.getAccountFavsListsUrl(account.getId(), account.getSessionID(), page))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseArray(TMDbStatus.MALFORMED_URL);
		}
	}
	 
	/**
	 * Gets all the lists that you have created and marked as a favorite.
	 * 
	 * @param account The account information
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray getAllFavoriteLists(TMDbAccount account) {
		try {
			
			TMDbResponseArray result = new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.getAccountFavsListsUrl(account.getId(), account.getSessionID()))));
			
			for (int p = 2; p <= result.getPages(); p++) {
                TMDbResponseArray page = new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.getAccountFavsListsUrl(account.getId(), account.getSessionID(), p))));
                for (Object obj : page.getData()) {
                    result.addData((JSONObject) obj);
                }
            }
            
            return result;
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseArray(TMDbStatus.MALFORMED_URL);
		}
	}
	
	/**
	 * Gets the list of favorite movies for an account.
	 * Returns the results of the first page.
	 * 
	 * @param account The account information
	 * @return The TMDb API response array.
	 */
	public static TMDbResponseArray getFavoriteMovies(TMDbAccount account) {
		return getFavoriteMovies(account, 1);
	}
	
	/**
	 * Gets the list of favorite movies for an account.
	 * Returns the results of the given page number.
	 * 
	 * @param account The account information
	 * @param page The page number to retrieve
	 * @return The TMDb API response array.
	 */
	public static TMDbResponseArray getFavoriteMovies(TMDbAccount account, int page) {
		try {
			
			return new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.getAccountFavsMoviesUrl(account.getId(), account.getSessionID(), page))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseArray(TMDbStatus.MALFORMED_URL);
		}
	}
	
	/**
	 * Gets all the list of all favorite movies for an account.
	 * 
	 * @param account The account information
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray getAllFavoriteMovies(TMDbAccount account) {
		try {
			
			TMDbResponseArray result = new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.getAccountFavsMoviesUrl(account.getId(), account.getSessionID()))));
			
			for (int p = 2; p <= result.getPages(); p++) {
                TMDbResponseArray page = new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.getAccountFavsMoviesUrl(account.getId(), account.getSessionID(), p))));
                for (Object obj : page.getData()) {
                    result.addData((JSONObject) obj);
                }
            }
            
            return result;
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseArray(TMDbStatus.MALFORMED_URL);
		}
	}
	
	/**
	 * Adds a movie to an accounts favorite list.
	 * 
	 * @param account The account information
	 * @param movieID The movie ID
	 * @return The TMDb API response object
	 */
	public static TMDbResponseObject addMovieToFavorites(TMDbAccount account, int movieID) {
		try {
			
			JSONObject json = new JSONObject();
			json.put(TMDbConstants.MOVIE_ID, movieID);
			json.put(TMDbConstants.FAVORITE, true);
			
			return new TMDbResponseObject(toJSON(makeApiCallPost(TMDbURLCreator.addMovieToFavsUrl(account.getId(), account.getSessionID()), json)));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseObject(TMDbStatus.MALFORMED_URL);
		}
	}
	
	/**
	 * Removes a movie to an accounts favorite list.
	 * 
	 * @param account The account information
	 * @param movieID The movie ID
	 * @return The TMDb API response object
	 */
	public static TMDbResponseObject removeMovieFromFavorites(TMDbAccount account, int movieID) {
		try {
			
			JSONObject json = new JSONObject();
			json.put(TMDbConstants.MOVIE_ID, movieID);
			json.put(TMDbConstants.FAVORITE, false);
			
			return new TMDbResponseObject(toJSON(makeApiCallPost(TMDbURLCreator.removeMovieFromFavsUrl(account.getId(), account.getSessionID()), json)));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseObject(TMDbStatus.MALFORMED_URL);
		}
	}
	
	/**
	 * Gets the list of rated movies for an account.
	 * Returns the results of the first page.
	 * 
	 * @param account The account information
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray getRatedMovies(TMDbAccount account) {
		return getRatedMovies(account, 1);
	}
	
	/**
	 * Gets the list of rated movies for an account.
	 * 
	 * @param account The account information
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray getAllRatedMovies(TMDbAccount account) {
		try {
			
			TMDbResponseArray result = new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.getRatedMoviesUrl(account.getId(), account.getSessionID()))));
			
			for (int p = 2; p <= result.getPages(); p++) {
                TMDbResponseArray page = new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.getRatedMoviesUrl(account.getId(), account.getSessionID(), p))));
                for (Object obj : page.getData()) {
                    result.addData((JSONObject) obj);
                }
            }
            
            return result;
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseArray(TMDbStatus.MALFORMED_URL);
		}
	}
	
	/**
	 * Gets the list of all rated movies for an account.
	 * Returns the results of the given page number.
	 * 
	 * @param account The account information
	 * @param page The page number to retrieve
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray getRatedMovies(TMDbAccount account, int page) {
		try {
			
			return new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.getRatedMoviesUrl(account.getId(), account.getSessionID(), page))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseArray(TMDbStatus.MALFORMED_URL);
		}
	}
	
	/**
	 * Gets the list of movies on an accounts watch list.
	 * Returns the results of the first page.
	 * 
	 * @param account The account information
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray getMovieWatchList(TMDbAccount account) {
		return getMovieWatchList(account, 1);
	}
	
	/**
	 * Gets the list of movies on an accounts watch list.
	 * Returns the results of the given page number.
	 * 
	 * @param account The account information
	 * @param page The page number to retrieve
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray getMovieWatchList(TMDbAccount account, int page) {
		try {
			
			return new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.getWatchlistUrl(account.getId(), account.getSessionID(), page))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseArray(TMDbStatus.MALFORMED_URL);
		}
	}
	
	/**
	 * Gets the list of all movies on an accounts watch list.
	 * 
	 * @param account The account information
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray getAllMovieWatchList(TMDbAccount account) {
		try {
			
			TMDbResponseArray result = new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.getWatchlistUrl(account.getId(), account.getSessionID()))));
			
			for (int p = 2; p <= result.getPages(); p++) {
                TMDbResponseArray page = new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.getWatchlistUrl(account.getId(), account.getSessionID(), p))));
                for (Object obj : page.getData()) {
                    result.addData((JSONObject) obj);
                }
            }
            
            return result;
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseArray(TMDbStatus.MALFORMED_URL);
		}
	}
	
	/**
	 * Adds a movie to an accounts watch list.
	 * 
	 * @param account The account information
	 * @param movieID The movie ID
	 * @return The TMDb API response object
	 */
	public static TMDbResponseObject addMovieToWatchlist(TMDbAccount account, int movieID) {
		try {
			
			JSONObject json = new JSONObject();
			json.put(TMDbConstants.MOVIE_ID, movieID);
			json.put(TMDbConstants.WATCHLIST, true);
			
			return new TMDbResponseObject(toJSON(makeApiCallPost(TMDbURLCreator.addMovieToWatchlistUrl(account.getId(), account.getSessionID()), json)));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseObject(TMDbStatus.MALFORMED_URL);
		}
	}
	
	/**
	 * Removes a movie to an accounts watch list.
	 * 
	 * @param account The account information
	 * @param movieID The movie ID
	 * @return The TMDb API response object
	 */
	public static TMDbResponseObject removeMovieFromWatchlist(TMDbAccount account, int movieID) {
		try {
			
			JSONObject json = new JSONObject();
			json.put(TMDbConstants.MOVIE_ID, movieID);
			json.put(TMDbConstants.WATCHLIST, false);
			
			return new TMDbResponseObject(toJSON(makeApiCallPost(TMDbURLCreator.removeMovieFromWatchlistUrl(account.getId(), account.getSessionID()), json)));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseObject(TMDbStatus.MALFORMED_URL);
		}
	}
	
	//endregion
	
	//region Movie
	
	/**
	 * Gets the basic movie information for a specific movie id.
	 * 
	 * @param movieID The movie ID
	 * @return The TMDb API response object
	 */
	public static TMDbResponseObject getMovieInformation(int movieID) {
		try {
			return new TMDbResponseObject(toJSON(makeApiCallGet(TMDbURLCreator.getMovieInfoUrl(movieID))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseObject(TMDbStatus.MALFORMED_URL);
		}
	}
	
	/**
	 * Gets the alternative titles for a specific movie id.
	 * 
	 * @param movieID The movie ID
	 * @return The TMDb API response object
	 */
	public static TMDbResponseObject getAlternativeMovieTitles(int movieID) {
		
		// Country parameter has been skipped
		
		try {
			return new TMDbResponseObject(toJSON(makeApiCallGet(TMDbURLCreator.getAlternativeMovieTitlesUrl(movieID))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseObject(TMDbStatus.MALFORMED_URL);
		}
	}
	
	/**
	 * Gets the cast information for a specific movie id.
	 * 
	 * @param movieID The movie ID
	 * @return The TMDb API response object
	 */
	public static TMDbResponseObject getCastInformation(int movieID) {
		try {
			return new TMDbResponseObject(toJSON(makeApiCallGet(TMDbURLCreator.getCastInfoUrl(movieID))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseObject(TMDbStatus.MALFORMED_URL);
		}
	}
	
	/**
	 * Gets the images (posters and backdrops) for a specific movie id.
	 * 
	 * @param movieID The movie ID
	 * @return The TMDb API response object
	 */
	public static TMDbResponseObject getMovieImages(int movieID) {
		try {
			return new TMDbResponseObject(toJSON(makeApiCallGet(TMDbURLCreator.getMovieImagesUrl(movieID))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseObject(TMDbStatus.MALFORMED_URL);
		}
	}
	
	/**
	 * Gets the plot keywords for a specific movie id.
	 * 
	 * @param movieID The movie ID
	 * @return The TMDb API response object
	 */
	public static TMDbResponseObject getMovieKeywords(int movieID) {
		try {
			return new TMDbResponseObject(toJSON(makeApiCallGet(TMDbURLCreator.getMovieKeywordsUrl(movieID))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseObject(TMDbStatus.MALFORMED_URL);
		}
	}
	
	/**
	 * Gets the release date by country for a specific movie id.
	 * 
	 * @param movieID The movie ID
	 * @return The TMDb API response object
	 */
	public static TMDbResponseObject getMovieReleases(int movieID) {
		try {
			return new TMDbResponseObject(toJSON(makeApiCallGet(TMDbURLCreator.getMovieReleasesDatesUrl(movieID))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseObject(TMDbStatus.MALFORMED_URL);
		}
	}
	
	/**
	 * Gets the trailers for a specific movie id.
	 * 
	 * @param movieID The movie ID
	 * @return The TMDb API response object
	 */
	public static TMDbResponseObject getMovieTrailers(int movieID) {
		try {
			return new TMDbResponseObject(toJSON(makeApiCallGet(TMDbURLCreator.getMovieTrailersUrl(movieID))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseObject(TMDbStatus.MALFORMED_URL);
		}
	}
	
	/**
	 * Gets the translations for a specific movie id.
	 * 
	 * @param movieID The movie ID
	 * @return The TMDb API response object
	 */
	public static TMDbResponseObject getMovieTranslations(int movieID) {
		try {
			return new TMDbResponseObject(toJSON(makeApiCallGet(TMDbURLCreator.getMovieTranslationsUrl(movieID))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseObject(TMDbStatus.MALFORMED_URL);
		}
	}
	
	/**
	 * Gets the similar movies for a specific movie id.
	 * Returns the results of the first page.
	 * 
	 * @param movieID The movie ID
	 * @return The TMDb API response object
	 */
	public static TMDbResponseArray getSimilarMovies(int movieID) {
		return getSimilarMovies(movieID, 1);
	}
	
	/**
	 * Gets the similar movies for a specific movie id.
	 * Returns the results of the given page number.
	 * 
	 * @param movieID The movie ID
	 * @param page The page number to retrieve
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray getSimilarMovies(int movieID, int page) {
		try {
			return new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.getSimilarMoviesUrl(movieID, page))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseArray(TMDbStatus.MALFORMED_URL);
		}
	}
	
	/**
	 * Gets all the similar movies for a specific movie id.
	 * 
	 * @param movieID The movie ID
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray getAllSimilarMovies(int movieID) {
		try {
			TMDbResponseArray result = new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.getSimilarMoviesUrl(movieID))));
			
			for (int p = 2; p <= result.getPages(); p++) {
				TMDbResponseArray page = new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.getSimilarMoviesUrl(movieID, p))));
				for (Object obj : page.getData()) {
					result.addData((JSONObject) obj);
				}
			}
			
			return result;
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseArray(TMDbStatus.MALFORMED_URL);
		}
	}
	
	/**
	 * Get the changes for a specific movie id. Changes are grouped by key, 
	 * and ordered by date in descending order. By default, only the last 24 hours of changes are returned. 
	 * The maximum number of days that can be returned in a single request is 14. 
	 * The language is present on fields that are translatable.
	 * 
	 * @param movieID The movie ID
	 * @return The TMDb API response object
	 */
	public static TMDbResponseObject getMovieChanges(int movieID) {
		try {
			return new TMDbResponseObject(toJSON(makeApiCallGet(TMDbURLCreator.getMovieChangesUrl(movieID))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseObject(TMDbStatus.MALFORMED_URL);
		}
	}
	
	/**
	 * Get the changes for a specific movie id. Changes are grouped by key, 
	 * and ordered by date in descending order. By default, only the last 24 hours of changes are returned. 
	 * The maximum number of days that can be returned in a single request is 14. 
	 * The language is present on fields that are translatable.
	 * 
	 * @param movieID The movie ID
	 * @param start The date where the search starts 
	 * @param end The date where the search ends 
	 * @return The TMDb API response object
	 */
	public static TMDbResponseObject getMovieChanges(int movieID, Date start, Date end) {
		try {
			return new TMDbResponseObject(toJSON(makeApiCallGet(TMDbURLCreator.getMovieChangesUrl(movieID, start, end))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseObject(TMDbStatus.MALFORMED_URL);
		}
	}
	
	/**
	 * Get the changes for a specific movie id. Changes are grouped by key, 
	 * and ordered by date in descending order. By default, only the last 24 hours of changes are returned. 
	 * The maximum number of days that can be returned in a single request is 14. 
	 * The language is present on fields that are translatable.
	 * 
	 * @param movieID The movie ID
	 * @param start The date where the search starts 
	 * @param end The date where the search ends 
	 * @return The TMDb API response object
	 */
	public static TMDbResponseObject getMovieChanges(int movieID, String start, String end) {
		try {
			return new TMDbResponseObject(toJSON(makeApiCallGet(TMDbURLCreator.getMovieChangesUrl(movieID, start, end))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseObject(TMDbStatus.MALFORMED_URL);
		}
	}
	
	/**
	 * Gets the lists that the movie belongs to.
	 * Returns the results of the first page.
	 * 
	 * @param movieID The movie ID
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray getListsBelongsToMovie(int movieID) {
		return getListsBelongsToMovie(movieID, 1);
	}
	
	/**
	 * Gets the lists that the movie belongs to.
	 * Returns the results of the given page number.
	 * 
	 * @param movieID The movie ID
	 * @param page The page number to retrieve
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray getListsBelongsToMovie(int movieID, int page) {
		try {
			return new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.getListsBelongsToMovieUrl(movieID, page))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseArray(TMDbStatus.MALFORMED_URL);
		}
	}
	
	/**
	 * Gets all the lists that the movie belongs to.
	 * 
	 * @param movieID The movie ID
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray getAllListsBelongsToMovie(int movieID) {
		try {
			TMDbResponseArray result = new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.getListsBelongsToMovieUrl(movieID))));
			
			for (int p = 2; p <= result.getPages(); p++) {
				TMDbResponseArray page = new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.getUpcomingMoviesListUrl(p))));
				for (Object obj : page.getData()) {
					result.addData((JSONObject) obj);
				}
			}
			
			return result;
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseArray(TMDbStatus.MALFORMED_URL);
		}
	}
	
	/**
	 * Gets the latest movie id.
	 * 
	 * @return The TMDb API response object
	 */
	public static TMDbResponseObject getLatestMovie() {
		try {
			return new TMDbResponseObject(toJSON(makeApiCallGet(TMDbURLCreator.getLatestMovieUrl())));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseObject(TMDbStatus.MALFORMED_URL);
		}
	}
	
	/**
	 * Gets the list of upcoming movies. This list refreshes every day. 
	 * The maximum number of items this list will include is 100.
	 * Returns the results of the first page.
	 * 
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray getUpcomingMovies() {
		return getUpcomingMovies(1);
	}
	
	/**
	 * Gets the list of upcoming movies. This list refreshes every day. 
	 * The maximum number of items this list will include is 100.
	 * Returns the results of the given page number.
	 * 
	 * @param page The page number to retrieve
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray getUpcomingMovies(int page) {
		try {
			return new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.getUpcomingMoviesListUrl(page))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseArray(TMDbStatus.MALFORMED_URL);
		}
	}
	
	/**
	 * Gets the entire list of upcoming movies. This list refreshes every day. 
	 * The maximum number of items this list will include is 100.
	 * 
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray getAllUpcomingMovies() {
		try {
			
			TMDbResponseArray result = new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.getUpcomingMoviesListUrl())));
			
			for (int p = 2; p <= result.getPages(); p++) {
				TMDbResponseArray page = new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.getUpcomingMoviesListUrl(p))));
				for (Object obj : page.getData()) {
					result.addData((JSONObject) obj);
				}
			}
			
			return result;
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseArray(TMDbStatus.MALFORMED_URL);
		}
	}
	
	/**
	 * Gets the list of movies playing in theatres. This list refreshes every day. 
	 * The maximum number of items this list will include is 100.
	 * Returns the results of the first page.
	 * 
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray getInTheatresMovies() {
		return getInTheatresMovies(1);
	}
	
	/**
	 * Gets the list of movies playing in theatres. This list refreshes every day. 
	 * The maximum number of items this list will include is 100.
	 * Returns the results of the given page number.
	 * 
	 * @param page The page number to retrieve
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray getInTheatresMovies(int page) {
		try {
			return new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.getInTheatresMoviesUrl(page))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseArray(TMDbStatus.MALFORMED_URL);
		}
	}
	
	/**
	 * Gets the entire list of movies playing in theatres. This list refreshes every day. 
	 * The maximum number of items this list will include is 100.
	 * 
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray getAllInTheatresMovies() {
		try {
			
			TMDbResponseArray result = new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.getInTheatresMoviesUrl())));
			
			for (int p = 2; p <= result.getPages(); p++) {
				TMDbResponseArray page = new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.getInTheatresMoviesUrl(p))));
				for (Object obj : page.getData()) {
					result.addData((JSONObject) obj);
				}
			}
			
			return result;
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseArray(TMDbStatus.MALFORMED_URL);
		}
	}
	
	/**
	 * Gets the list of popular movies on The Movie Database. This list refreshes every day.
	 * Returns the results of the first page.
	 * 
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray getPopularMovies() {
		return getPopularMovies(1);
	}
	
	/**
	 * Gets the list of popular movies on The Movie Database. This list refreshes every day.
	 * Returns the results of the given page number.
	 * 
	 * @param page The page number to retrieve
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray getPopularMovies(int page) {
		try {
			return new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.getPopularMoviesUrl(page))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseArray(TMDbStatus.MALFORMED_URL);
		}
	}
	
	/**
	 * Gets the entire list of popular movies on The Movie Database. This list refreshes every day.
	 * 
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray getAllPopularMovies() {
		try {
			
			TMDbResponseArray result = new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.getPopularMoviesUrl())));
			
			for (int p = 2; p <= result.getPages(); p++) {
				TMDbResponseArray page = new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.getPopularMoviesUrl(p))));
				for (Object obj : page.getData()) {
					result.addData((JSONObject) obj);
				}
			}
			
			return result;
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseArray(TMDbStatus.MALFORMED_URL);
		}
	}
	
	/**
	 * Gets the list of top rated movies. 
	 * By default, this list will only include movies that have 10 or more votes. This list refreshes every day.
	 * Returns the results of the first page.
	 * 
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray getTopRatedMovies() {
		return getTopRatedMovies(1);
	}
	
	/**
	 * Gets the list of top rated movies. 
	 * By default, this list will only include movies that have 10 or more votes. This list refreshes every day.
	 * Returns the results of the given page number.
	 * 
	 * @param page The page number to retrieve
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray getTopRatedMovies(int page) {
		try {
			return new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.getTopRatedMoviesUrl(page))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseArray(TMDbStatus.MALFORMED_URL);
		}
	}
	
	/**
	 * Gets the list of all top rated movies. 
	 * By default, this list will only include movies that have 10 or more votes. This list refreshes every day.
	 * 
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray getAllTopRatedMovies() {
		try {
			
			TMDbResponseArray result = new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.getTopRatedMoviesUrl())));
			
			for (int p = 2; p <= result.getPages(); p++) {
				TMDbResponseArray page = new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.getTopRatedMoviesUrl(p))));
				for (Object obj : page.getData()) {
					result.addData((JSONObject) obj);
				}
			}
			
			return result;
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseArray(TMDbStatus.MALFORMED_URL);
		}
	}
	
	/**
	 * This method lets users rate a movie.
	 * 
	 * @param sessionID The session ID
	 * @param guest Whether the session is a guest session
	 * @param movieID The movie ID
	 * @param rating The rating of the movie
	 * @return The TMDb API response object
	 */
	public static TMDbResponseObject setMovieRate(String sessionID, boolean guest, int movieID, float rating) {
		try {
			
			DecimalFormat oneDigit = new DecimalFormat("#,##0.0");
			String strValue = oneDigit.format(rating);
			
			JSONObject json = new JSONObject();
			json.put(TMDbConstants.VALUE, strValue);
			
			return new TMDbResponseObject(toJSON(makeApiCallPost(TMDbURLCreator.setMovieRateUrl(sessionID, movieID, guest), json)));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseObject(TMDbStatus.MALFORMED_URL);
		}
	}
	
	
	
	//endregion
	
	//region People
	
	/**
	 * Gets the general person information for a specific id.
	 * 
	 * @param personID The person ID
	 * @return The TMDb API response object
	 */
	public static TMDbResponseObject getPersonInformation(int personID) {
		try {
			return new TMDbResponseObject(toJSON(makeApiCallGet(TMDbURLCreator.getPersonInfoUrl(personID))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseObject(TMDbStatus.MALFORMED_URL);
		}
	}
	
	/**
	 * Gets the credits for a specific person id.
	 * 
	 * @param personID The person ID
	 * @return The TMDb API response object
	 */
	public static TMDbResponseObject getPersonCredits(int personID) {
		try {
			return new TMDbResponseObject(toJSON(makeApiCallGet(TMDbURLCreator.getPersonCreditsUrl(personID))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseObject(TMDbStatus.MALFORMED_URL);
		}
	}
	
	/**
	 * Gets the images for a specific person id.
	 * 
	 * @param personID The person ID
	 * @return The TMDb API response object
	 */
	public static TMDbResponseObject getPersonImages(int personID) {
		try {
			return new TMDbResponseObject(toJSON(makeApiCallGet(TMDbURLCreator.getPersonImagesUrl(personID))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseObject(TMDbStatus.MALFORMED_URL);
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
	 * @return The TMDb API response object
	 */
	public static TMDbResponseObject getPersonChanges(int personID) {
		try {
			return new TMDbResponseObject(toJSON(makeApiCallGet(TMDbURLCreator.getPersonChangesUrl(personID))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseObject(TMDbStatus.MALFORMED_URL);
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
	 * @param start The date where the search starts 
	 * @param end The date where the search ends 
	 * @return The TMDb API response object
	 */
	public static TMDbResponseObject getPersonChanges(int personID, Date start, Date end) {
		try {
			return new TMDbResponseObject(toJSON(makeApiCallGet(TMDbURLCreator.getPersonChangesUrl(personID, start, end))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseObject(TMDbStatus.MALFORMED_URL);
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
	 * @param start The date where the search starts 
	 * @param end The date where the search ends 
	 * @return The TMDb API response object
	 */
	public static TMDbResponseObject getPersonChanges(int personID, String start, String end) {
		try {
			return new TMDbResponseObject(toJSON(makeApiCallGet(TMDbURLCreator.getPersonChangesUrl(personID, start, end))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseObject(TMDbStatus.MALFORMED_URL);
		}
	}
	
	/**
	 * Gets the latest person id.
	 * 
	 * @return The TMDb API response object
	 */
	public static TMDbResponseObject getLatestPerson() {
		try {
			return new TMDbResponseObject(toJSON(makeApiCallGet(TMDbURLCreator.getLatestPerson())));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseObject(TMDbStatus.MALFORMED_URL);
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
	 * @return The TMDb API response object
	 */
	public static TMDbResponseObject getCollectionInformation(int collectionID) {
		try {
			return new TMDbResponseObject(toJSON(makeApiCallGet(TMDbURLCreator.getCollectionInfoUrl(collectionID))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseObject(TMDbStatus.MALFORMED_URL);
		}
	}
	
	/**
	 * Gets all of the images for a particular collection by collection id.
	 * 
	 * @param collectionID The collection ID
	 * @return The TMDb API response object
	 */
	public static TMDbResponseObject getCollectionImages(int collectionID) {
		try {
			return new TMDbResponseObject(toJSON(makeApiCallGet(TMDbURLCreator.getCollectionImagesUrl(collectionID))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseObject(TMDbStatus.MALFORMED_URL);
		}
	}
	
	//endregion
	
	//region Lists
	
	/**
	 * Gets a list by id.
	 * 
	 * @param listID The list ID
	 * @return The TMDb API response object
	 */
	public static TMDbResponseObject getList(String listID) {
		try {
			return new TMDbResponseObject(toJSON(makeApiCallGet(TMDbURLCreator.getListUrl(listID))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseObject(TMDbStatus.MALFORMED_URL);
		}
	}
	
	//endregion
	
	//region Company
	
	/**
	 * This method is used to retrieve all of the basic information about a company.
	 * 
	 * @param companyID The company ID
	 * @return The TMDb API response object
	 */
	public static TMDbResponseObject getCompanyInformation(int companyID) {
		try {
			return new TMDbResponseObject(toJSON(makeApiCallGet(TMDbURLCreator.getCompanyInfoUrl(companyID))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseObject(TMDbStatus.MALFORMED_URL);
		}
	}
	
	/**
	 * Gets the list of movies associated with a particular company.
	 * Returns the results of the first page.
	 * 
	 * @param companyID The company ID
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray getMoviesByCompany(int companyID) {
		return getMoviesByCompany(companyID, 1);
	}
	
	/**
	 * Gets the list of movies associated with a particular company.
	 * Returns the results of the given page number.
	 * 
	 * @param companyID The company ID
	 * @param page The page number to retrieve
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray getMoviesByCompany(int companyID, int page) {
		try {
			return new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.getMoviesListByCompanyUrl(companyID, page))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseArray(TMDbStatus.MALFORMED_URL);
		}
	}
	
	/**
	 * Gets the list of all movies associated with a particular company.
	 * 
	 * @param companyID The company ID
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray getAllMoviesByCompany(int companyID) {
		try {
			
			TMDbResponseArray result = new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.getMoviesListByCompanyUrl(companyID))));
			
			for (int p = 2; p <= result.getPages(); p++) {
				TMDbResponseArray page = new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.getMoviesListByCompanyUrl(companyID, p))));
				for (Object obj : page.getData()) {
					result.addData((JSONObject) obj);
				}
			}
			
			return result;
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseArray(TMDbStatus.MALFORMED_URL);
		}
	}
	
	//endregion
	
	//region Genre
	
	/**
	 * Get the list of genres.
	 * 
	 * @return The TMDb API response object
	 */
	public static TMDbResponseObject getGenresList() {
		try {
			return new TMDbResponseObject(toJSON(makeApiCallGet(TMDbURLCreator.getGenresListUrl())));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseObject(TMDbStatus.MALFORMED_URL);
		}
	}
	
	/**
	 * Gets the list of movies for a particular genre by id. 
	 * By default, only movies with 10 or more votes are included.
	 * Returns the results of the first page.
	 * 
	 * @param genreID The genre ID
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray getMoviesByGenre(int genreID) {
		return getMoviesByGenre(genreID, 1);
	}
	
	/**
	 * Gets the list of movies for a particular genre by id. 
	 * By default, only movies with 10 or more votes are included.
	 * Returns the results of the given page number.
	 * 
	 * @param genreID The genre ID
	 * @param page The page number to retrieve
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray getMoviesByGenre(int genreID, int page) {
		try {
			return new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.getMoviesListByGenreUrl(genreID, page))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseArray(TMDbStatus.MALFORMED_URL);
		}
	}
	
	/**
	 * Gets the list of all movies for a particular genre by id. 
	 * By default, only movies with 10 or more votes are included.
	 * 
	 * @param genreID The genre ID
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray getAllMoviesByGenre(int genreID) {
		try {
			
			TMDbResponseArray result = new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.getMoviesListByGenreUrl(genreID))));
			
			for (int p = 2; p <= result.getPages(); p++) {
				TMDbResponseArray page = new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.getMoviesListByGenreUrl(genreID, p))));
				for (JSONObject json : page.getData()) {
					result.addData(json);
				}
			}
			
			return result;
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseArray(TMDbStatus.MALFORMED_URL);
		}
	}
	
	//endregion
	
	//region Keyword
	
	/**
	 * Gets the keyword information.
	 * 
	 * @param keywordID The keyword ID
	 * @return The TMDb API response object
	 */
	public static TMDbResponseObject getKeywordInformation(int keywordID) {
		try {
			return new TMDbResponseObject(toJSON(makeApiCallGet(TMDbURLCreator.getKeywordInformation(keywordID))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseObject(TMDbStatus.MALFORMED_URL);
		}
	}
	
	/**
	 * Gets the list of movies that has the specified keyword.
	 * Returns the results of the first page.
	 * 
	 * @param keywordID The keyword ID
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray getMoviesByKeyword(int keywordID) {
		try {
			return new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.getMoviesListByKeyword(keywordID))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseArray(TMDbStatus.MALFORMED_URL);
		}
	}
	
	/**
	 * Gets the list of movies that has the specified keyword.
	 * Returns the results of the given page number.
	 * 
	 * @param keywordID The keyword ID
	 * @param page The page number to retrieve
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray getMoviesByKeyword(int keywordID, int page) {
		try {
			return new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.getMoviesListByKeyword(keywordID, page))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseArray(TMDbStatus.MALFORMED_URL);
		}
	}
	
	/**
	 * Gets the entire list of movies that has the specified keyword.
	 * 
	 * @param keywordID The keyword ID
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray getAllMoviesByKeyword(int keywordID) {
		try {
			
			TMDbResponseArray result = new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.getMoviesListByKeyword(keywordID))));
			
			for (int p = 2; p <= result.getPages(); p++) {
				TMDbResponseArray page = new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.getMoviesListByKeyword(keywordID, p))));
				for (Object obj : page.getData()) {
					result.addData((JSONObject) obj);
				}
			}
			
			return result;
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseArray(TMDbStatus.MALFORMED_URL);
		}
	}
	
	//endregion
	
	//region Search
	
	/**
	 * Searches for movies by title.
	 * Returns the results of the first page.
	 * 
	 * @param movieTitle The movie's title
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray searchMovieByTitle(String movieTitle) {
		return searchMovieByTitle(movieTitle, 1);
	}
	
	/**
	 * Searches for movies by title.
	 * Returns the results of the given page number.
	 * 
	 * @param movieTitle The movie's title
	 * @param page The page number to retrieve
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray searchMovieByTitle(String movieTitle, int page) {
		try {
			return new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.searchMovieByTitleUrl(movieTitle, page))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseArray(TMDbStatus.MALFORMED_URL);
		}
	}
	
	/**
	 * Searches for movies by title. Gets all the results.
	 * 
	 * @param movieTitle The movie's title
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray fullSearchMovieByTitle(String movieTitle) {
		try {
			
			TMDbResponseArray result = new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.searchMovieByTitleUrl(movieTitle))));
			
			for (int p = 2; p <= result.getPages(); p++) {
				TMDbResponseArray page = new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.searchMovieByTitleUrl(movieTitle, p))));
				for (Object obj : page.getData()) {
					result.addData((JSONObject) obj);
				}
			}
			
			return result;
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseArray(TMDbStatus.MALFORMED_URL);
		}
	}
	
	/**
	 * Searches for movies by title and year.
	 * Returns the results of the first page.
	 * 
	 * @param movieTitle The movie's title
	 * @param year The year of the movie
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray searchMovieByTitleAndYear(String movieTitle, int year) {
		return searchMovieByTitleAndYear(movieTitle, year, 1);
	}
	
	/**
	 * Searches for movies by title and year.
	 * Returns the results of the given page number.
	 * 
	 * @param movieTitle The movie's title
	 * @param year The year of the movie
	 * @param page the page number to retrieve
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray searchMovieByTitleAndYear(String movieTitle, int year, int page) {
		try {
			return new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.searchMovieByTitleAndYearUrl(movieTitle, year, page))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseArray(TMDbStatus.MALFORMED_URL);
		}
	}
	
	/**
	 * Searches for movies by title and year. Gets all the results.
	 * 
	 * @param movieTitle The movie's title
	 * @param year The year of the movie
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray fullSearchMovieByTitleAndYear(String movieTitle, int year) {
		try {
			
			TMDbResponseArray result = new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.searchMovieByTitleAndYearUrl(movieTitle, year))));
			
			for (int p = 2; p <= result.getPages(); p++) {
				TMDbResponseArray page = new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.searchMovieByTitleAndYearUrl(movieTitle, year, p))));
				for (Object obj : page.getData()) {
					result.addData((JSONObject) obj);
				}
			}
			
			return result;
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseArray(TMDbStatus.MALFORMED_URL);
		}
	}
	
	/**
	 * Searches for movies by title.
	 * Returns the results of the first page.
	 * 
	 * @param movieTitle The movie's title
	 * @param adult Whether the movie audience is adult only
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray searchMovieByTitle(String movieTitle, boolean adult) {
		return searchMovieByTitle(movieTitle, adult, 1);
	}
	
	/**
	 * Searches for movies by title.
	 * Returns the results of the given page number.
	 * 
	 * @param movieTitle The movie's title
	 * @param adult Whether the movie audience is adult only
	 * @param page The page number to retrieve
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray searchMovieByTitle(String movieTitle, boolean adult, int page) {
		try {
			return new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.searchMovieByTitleUrl(movieTitle, adult, page))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseArray(TMDbStatus.MALFORMED_URL);
		}
	}
	
	/**
	 * Searches for movies by title. Gets all the results.
	 * 
	 * @param movieTitle The movie's title
	 * @param adult Whether the movie audience is adult only
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray fullSearchMovieByTitle(String movieTitle, boolean adult) {
		try {
			
			TMDbResponseArray result = new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.searchMovieByTitleUrl(movieTitle, adult))));
			
			for (int p = 2; p <= result.getPages(); p++) {
				TMDbResponseArray page = new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.searchMovieByTitleUrl(movieTitle, adult, p))));
				for (Object obj : page.getData()) {
					result.addData((JSONObject) obj);
				}
			}
			
			return result;
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseArray(TMDbStatus.MALFORMED_URL);
		}
	}
	
	/**
	 * Searches for movies by title.
	 * Returns the results of the first page.
	 * 
	 * @param movieTitle The movie's title
	 * @param year The year of the movie
	 * @param adult Whether the movie audience is adult only
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray searchMovieByTitleAndYear(String movieTitle, int year, boolean adult) {
		return searchMovieByTitleAndYear(movieTitle, year, adult, 1);
	}
	
	/**
	 * Searches for movies by title.
	 * Returns the results of the given page number.
	 * 
	 * @param movieTitle The movie's title
	 * @param year The year of the movie
	 * @param adult Whether the movie audience is adult only
	 * @param page The page number to retrieve
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray searchMovieByTitleAndYear(String movieTitle, int year, boolean adult, int page) {
		try {
			return new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.searchMovieByTitleUrl(movieTitle, year, adult, page))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseArray(TMDbStatus.MALFORMED_URL);
		}
	}
	
	/**
	 * Searches for movies by title. Gets all the results.
	 * 
	 * @param movieTitle The movie's title
	 * @param year The year of the movie
	 * @param adult Whether the movie audience is adult only
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray fullSearchMovieByTitleAndYear(String movieTitle, int year, boolean adult) {
		try {
			
			TMDbResponseArray result = new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.searchMovieByTitleUrl(movieTitle, year, adult))));
			
			for (int p = 2; p <= result.getPages(); p++) {
				TMDbResponseArray page = new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.searchMovieByTitleUrl(movieTitle, year, adult, p))));
				for (Object obj : page.getData()) {
					result.addData((JSONObject) obj);
				}
			}
			
			return result;
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseArray(TMDbStatus.MALFORMED_URL);
		}
	}
	
	/**
	 * Searches for people by name.
	 * Returns the results of the first page.
	 * 
	 * @param name The person name
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray searchPersonByName(String name) {
		return searchPersonByName(name, 1);
	}
	
	/**
	 * Searches for people by name.
	 * Returns the results of the given page number.
	 * 
	 * @param name The person name
	 * @param page The page number to retrieve
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray searchPersonByName(String name, int page) {
		try {
			return new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.searchPeopleByNameUrl(name, page))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseArray(TMDbStatus.MALFORMED_URL);
		}
	}
	
	/**
	 * Searches for people by name.
	 * Returns the results of the first page.
	 * 
	 * @param name The person name
	 * @param adult Whether the movie audience is adult only
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray searchPersonByName(String name, boolean adult) {
		return searchPersonByName(name, adult, 1);
	}
	
	/**
	 * Searches for people by name.
	 * Returns the results of the given page number.
	 * 
	 * @param name The person name
	 * @param adult Whether the movie audience is adult only
	 * @param page The page number to retrieve
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray searchPersonByName(String name, boolean adult, int page) {
		try {
			return new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.searchPeopleByNameUrl(name, adult, page))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseArray(TMDbStatus.MALFORMED_URL);
		}
	}
	
	/**
	 * Searches for people by name. Gets all the results.
	 * 
	 * @param name The person name
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray fullSearchPersonByName(String name) {
		try {
			
			TMDbResponseArray result = new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.searchPeopleByNameUrl(name))));
			
			for (int p = 2; p <= result.getPages(); p++) {
				TMDbResponseArray page = new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.searchPeopleByNameUrl(name, p))));
				for (Object obj : page.getData()) {
					result.addData((JSONObject) obj);
				}
			}
			
			return result;
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseArray(TMDbStatus.MALFORMED_URL);
		}
	}
	
	/**
	 * Searches for people by name. Gets all the results.
	 * 
	 * @param name The person name
	 * @param adult Whether the movie audience is adult only
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray fullSearchPersonByName(String name, boolean adult) {
		try {
			
			TMDbResponseArray result = new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.searchPeopleByNameUrl(name, adult))));
			
			for (int p = 2; p <= result.getPages(); p++) {
				TMDbResponseArray page = new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.searchPeopleByNameUrl(name, adult, p))));
				for (Object obj : page.getData()) {
					result.addData((JSONObject) obj);
				}
			}
			
			return result;
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseArray(TMDbStatus.MALFORMED_URL);
		}
	}
	
	/**
	 * Searches for company by name.
	 * Returns the results of the first page.
	 * 
	 * @param name The company name
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray searchCompanyByName(String name) {
		return searchCompanyByName(name, 1);
	}
	
	/**
	 * Searches for company by name.
	 * Returns the results of the given page number.
	 * 
	 * @param name The company name
	 * @param page The page number to retrieve
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray searchCompanyByName(String name, int page) {
		try {
			return new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.searchCompanyByNameUrl(name, page))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseArray(TMDbStatus.MALFORMED_URL);
		}
	}
	
	/**
	 * Searches for company by name. Gets all the results.
	 * 
	 * @param name The company name
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray fullSearchCompanyByName(String name) {
		try {
			
			TMDbResponseArray result = new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.searchCompanyByNameUrl(name))));
			
			for (int p = 2; p <= result.getPages(); p++) {
				TMDbResponseArray page = new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.searchCompanyByNameUrl(name, p))));
				for (Object obj : page.getData()) {
					result.addData((JSONObject) obj);
				}
			}
			
			return result;
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseArray(TMDbStatus.MALFORMED_URL);
		}
	}
	
	/**
	 * Searches for keyword by name.
	 * Returns the results of the first page.
	 * 
	 * @param name The keyword name
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray searchKeywordByName(String name) {
		return searchKeywordByName(name, 1);
	}
	
	/**
	 * Searches for keyword by name.
	 * Returns the results of the given page number.
	 * 
	 * @param name The keyword name
	 * @param page The page number to retrieve
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray searchKeywordByName(String name, int page) {
		try {
			return new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.searchKeywordByNameUrl(name, page))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseArray(TMDbStatus.MALFORMED_URL);
		}
	}
	
	/**
	 * Searches for keyword by name. Gets all the results.
	 * 
	 * @param name The keyword name
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray fullSearchKeywordByName(String name) {
		try {
			
			TMDbResponseArray result = new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.searchKeywordByNameUrl(name))));
			
			for (int p = 2; p <= result.getPages(); p++) {
				TMDbResponseArray page = new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.searchKeywordByNameUrl(name, p))));
				for (Object obj : page.getData()) {
					result.addData((JSONObject) obj);
				}
			}
			
			return result;
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseArray(TMDbStatus.MALFORMED_URL);
		}
	}
	
	/**
	 * Searches for lists by name and description.
	 * Returns the results of the first page.
	 * 
	 * @param name The list name or description
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray searchListByName(String name) {
		return searchListByName(name, 1);
	}
	
	/**
	 * Searches for lists by name and description.
	 * Returns the results of the given page number.
	 * 
	 * @param name The list name or description
	 * @param page The page number to retrieve
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray searchListByName(String name, int page) {
		try {
			
			return new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.searchListByNameUrl(name, page))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseArray(TMDbStatus.MALFORMED_URL);
		}
	}
	
	/**
	 * Searches for lists by name and description. Gets all the results.
	 * 
	 * @param name The list name or description
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray fullSearchListByName(String name) {
		try {
			
			TMDbResponseArray result = new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.searchListByNameUrl(name))));
			
			for (int p = 2; p <= result.getPages(); p++) {
				TMDbResponseArray page = new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.searchListByNameUrl(name, p))));
				for (Object obj : page.getData()) {
					result.addData((JSONObject) obj);
				}
			}
			
			return result;
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseArray(TMDbStatus.MALFORMED_URL);
		}
	}
	
	/**
	 * Searches for collections by name.
	 * Returns the results of the first page.
	 * 
	 * @param name The collection name
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray searchCollectionByName(String name) {
		return searchListByName(name, 1);
	}
	
	/**
	 * Search for collections by name.
	 * Returns the results of the given page number.
	 * 
	 * @param name The collection name
	 * @param page The page number to retrieve
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray searchCollectionByName(String name, int page) {
		try {
			
			return new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.searchCollectionByNameUrl(name, page))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseArray(TMDbStatus.MALFORMED_URL);
		}
	}
	
	/**
	 * Search for collections by name. Gets all the results.
	 * 
	 * @param name The collection name
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray fullSearchCollectionByName(String name) {
		try {
			
			TMDbResponseArray result = new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.searchCollectionByNameUrl(name))));
			
			for (int p = 2; p <= result.getPages(); p++) {
				TMDbResponseArray page = new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.searchCollectionByNameUrl(name, p))));
				for (Object obj : page.getData()) {
					result.addData((JSONObject) obj);
				}
			}
			
			return result;
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseArray(TMDbStatus.MALFORMED_URL);
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
	 * Returns the results of the first page.
	 * 
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray getChangedMovies() {
		return getChangedMovies(1);
	}
	
	/**
	 * Gets a list of people ids that have been edited. 
	 * By default we show the last 24 hours and only 100 items per page. 
	 * The maximum number of days that can be returned in a single request is 14. 
	 * You can then use the {@link #getPersonChanges(int) getPersonChanges} method to get the actual data that has been changed. 
	 * Please note that the change log system to support this was changed on 
	 * October 5, 2012 and will only show people that have been edited since.
	 * Returns the results of the first page.
	 * 
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray getChangedPersons() {
		return getChangedPersons(1);
	}
	
	/**
	 * Gets a list of movie ids that have been edited. 
	 * By default we show the last 24 hours and only 100 items per page. 
	 * The maximum number of days that can be returned in a single request is 14. 
	 * You can then use the {@link #getMovieChanges(int) getMovieChanges} method to get the actual data that has been changed.
	 * Please note that the change log system to support this was changed on 
	 * October 5, 2012 and will only show movies that have been edited since.
	 * Returns the results of the given page number.
	 * 
	 * @param page The page number to retrieve
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray getChangedMovies(int page) {
		try {
			return new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.getChangedMoviesUrl(page))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseArray(TMDbStatus.MALFORMED_URL);
		}
	}
	
	/**
	 * Gets a list of people ids that have been edited. 
	 * By default we show the last 24 hours and only 100 items per page. 
	 * The maximum number of days that can be returned in a single request is 14. 
	 * You can then use the {@link #getPersonChanges(int) getPersonChanges} method to get the actual data that has been changed. 
	 * Please note that the change log system to support this was changed on 
	 * October 5, 2012 and will only show people that have been edited since.
	 * Returns the results of the given page number.
	 * 
	 * @param page The page number to retrieve
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray getChangedPersons(int page) {
		try {
			return new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.getChangedPersonsUrl(page))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseArray(TMDbStatus.MALFORMED_URL);
		}
	}
	
	/**
	 * Gets a list of all movie ids that have been edited. 
	 * By default we show the last 24 hours and only 100 items per page. 
	 * The maximum number of days that can be returned in a single request is 14. 
	 * You can then use the {@link #getMovieChanges(int) getMovieChanges} method to get the actual data that has been changed.
	 * Please note that the change log system to support this was changed on 
	 * October 5, 2012 and will only show movies that have been edited since.
	 * 
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray getAllChangedMovies() {
		try {
			TMDbResponseArray result = new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.getChangedMoviesUrl())));
			
			for (int p = 2; p <= result.getPages(); p++) {
				TMDbResponseArray page = new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.getChangedMoviesUrl(p))));
				for (Object obj : page.getData()) {
					result.addData((JSONObject) obj);
				}
			}
			
			return result;
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseArray(TMDbStatus.MALFORMED_URL);
		}
	}
	
	/**
	 * Gets a list of all people ids that have been edited. 
	 * By default we show the last 24 hours and only 100 items per page. 
	 * The maximum number of days that can be returned in a single request is 14. 
	 * You can then use the {@link #getPersonChanges(int) getPersonChanges} method to get the actual data that has been changed. 
	 * Please note that the change log system to support this was changed on 
	 * October 5, 2012 and will only show people that have been edited since.
	 * 
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray getAllChangedPersons() {
		try {
			TMDbResponseArray result = new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.getChangedPersonsUrl())));
			
			for (int p = 2; p <= result.getPages(); p++) {
				TMDbResponseArray page = new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.getChangedPersonsUrl(p))));
				for (Object obj : page.getData()) {
					result.addData((JSONObject) obj);
				}
			}
			
			return result;
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseArray(TMDbStatus.MALFORMED_URL);
		}
	}
	
	/**
	 * Gets a list of movie ids that have been edited. 
	 * By default we show the last 24 hours and only 100 items per page. 
	 * The maximum number of days that can be returned in a single request is 14. 
	 * You can then use the {@link #getMovieChanges(int) getMovieChanges} method to get the actual data that has been changed.
	 * Please note that the change log system to support this was changed on 
	 * October 5, 2012 and will only show movies that have been edited since.
	 * Returns the results of the first page.
	 * 
	 * @param start The date where the search starts 
	 * @param end The date where the search ends
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray getChangedMovies(Date start, Date end) {
		return getChangedMovies(start, end, 1);
	}
	
	/**
	 * Gets a list of people ids that have been edited. 
	 * By default we show the last 24 hours and only 100 items per page. 
	 * The maximum number of days that can be returned in a single request is 14. 
	 * You can then use the {@link #getPersonChanges(int) getPersonChanges} method to get the actual data that has been changed. 
	 * Please note that the change log system to support this was changed on 
	 * October 5, 2012 and will only show people that have been edited since.
	 * Returns the results of the first page.
	 * 
	 * @param start The date where the search starts 
	 * @param end The date where the search ends 
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray getChangedPersons(Date start, Date end) {
		return getChangedPersons(start, end, 1);
	}
	
	/**
	 * Gets a list of movie ids that have been edited. 
	 * By default we show the last 24 hours and only 100 items per page. 
	 * The maximum number of days that can be returned in a single request is 14. 
	 * You can then use the {@link #getMovieChanges(int) getMovieChanges} method to get the actual data that has been changed.
	 * Please note that the change log system to support this was changed on 
	 * October 5, 2012 and will only show movies that have been edited since.
	 * Returns the results of the given page number.
	 * 
	 * @param start The date where the search starts 
	 * @param end The date where the search ends
	 * @param page The page number to retrieve
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray getChangedMovies(Date start, Date end, int page) {
		try {
			return new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.getChangedMoviesUrl(start, end, page))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseArray(TMDbStatus.MALFORMED_URL);
		}
	}
	
	/**
	 * Gets a list of people ids that have been edited. 
	 * By default we show the last 24 hours and only 100 items per page. 
	 * The maximum number of days that can be returned in a single request is 14. 
	 * You can then use the {@link #getPersonChanges(int) getPersonChanges} method to get the actual data that has been changed. 
	 * Please note that the change log system to support this was changed on 
	 * October 5, 2012 and will only show people that have been edited since.
	 * Returns the results of the given page number.
	 * 
	 * @param start The date where the search starts 
	 * @param end The date where the search ends
	 * @param page The page number to retrieve 
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray getChangedPersons(Date start, Date end, int page) {
		try {
			return new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.getChangedPersonsUrl(start, end, page))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseArray(TMDbStatus.MALFORMED_URL);
		}
	}
	
	/**
	 * Gets a list of all movie ids that have been edited. 
	 * By default we show the last 24 hours and only 100 items per page. 
	 * The maximum number of days that can be returned in a single request is 14. 
	 * You can then use the {@link #getMovieChanges(int) getMovieChanges} method to get the actual data that has been changed.
	 * Please note that the change log system to support this was changed on 
	 * October 5, 2012 and will only show movies that have been edited since.
	 * 
	 * @param start The date where the search starts 
	 * @param end The date where the search ends
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray getAllChangedMovies(Date start, Date end) {
		try {
			TMDbResponseArray result = new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.getChangedMoviesUrl(start, end))));
			
			for (int p = 2; p <= result.getPages(); p++) {
				TMDbResponseArray page = new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.getChangedMoviesUrl(start, end, p))));
				for (Object obj : page.getData()) {
					result.addData((JSONObject) obj);
				}
			}
			
			return result;
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseArray(TMDbStatus.MALFORMED_URL);
		}
	}
	
	/**
	 * Gets a list of all people ids that have been edited. 
	 * By default we show the last 24 hours and only 100 items per page. 
	 * The maximum number of days that can be returned in a single request is 14. 
	 * You can then use the {@link #getPersonChanges(int) getPersonChanges} method to get the actual data that has been changed. 
	 * Please note that the change log system to support this was changed on 
	 * October 5, 2012 and will only show people that have been edited since.
	 * 
	 * @param start The date where the search starts 
	 * @param end The date where the search ends
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray getAllChangedPersons(Date start, Date end) {
		try {
			TMDbResponseArray result = new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.getChangedPersonsUrl(start, end))));
			
			for (int p = 2; p <= result.getPages(); p++) {
				TMDbResponseArray page = new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.getChangedPersonsUrl(start, end, p))));
				for (Object obj : page.getData()) {
					result.addData((JSONObject) obj);
				}
			}
			
			return result;
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseArray(TMDbStatus.MALFORMED_URL);
		}
	}
	
	/**
	 * Gets a list of movie ids that have been edited. 
	 * By default we show the last 24 hours and only 100 items per page. 
	 * The maximum number of days that can be returned in a single request is 14. 
	 * You can then use the {@link #getMovieChanges(int) getMovieChanges} method to get the actual data that has been changed.
	 * Please note that the change log system to support this was changed on 
	 * October 5, 2012 and will only show movies that have been edited since.
	 * Returns the results of the first page.
	 * 
	 * @param start The date where the search starts 
	 * @param end The date where the search ends
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray getChangedMovies(String start, String end) {
		return getChangedMovies(start, end, 1);
	}
	
	/**
	 * Gets a list of people ids that have been edited. 
	 * By default we show the last 24 hours and only 100 items per page. 
	 * The maximum number of days that can be returned in a single request is 14. 
	 * You can then use the {@link #getPersonChanges(int) getPersonChanges} method to get the actual data that has been changed. 
	 * Please note that the change log system to support this was changed on 
	 * October 5, 2012 and will only show people that have been edited since.
	 * Returns the results of the first page.
	 * 
	 * @param start The date where the search starts 
	 * @param end The date where the search ends 
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray getChangedPersons(String start, String end) {
		return getChangedPersons(start, end, 1);
	}
	
	/**
	 * Gets a list of movie ids that have been edited. 
	 * By default we show the last 24 hours and only 100 items per page. 
	 * The maximum number of days that can be returned in a single request is 14. 
	 * You can then use the {@link #getMovieChanges(int) getMovieChanges} method to get the actual data that has been changed.
	 * Please note that the change log system to support this was changed on 
	 * October 5, 2012 and will only show movies that have been edited since.
	 * Returns the results of the given page number.
	 * 
	 * @param start The date where the search starts 
	 * @param end The date where the search ends
	 * @param page The page number to retrieve
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray getChangedMovies(String start, String end, int page) {
		try {
			return new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.getChangedMoviesUrl(start, end, page))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseArray(TMDbStatus.MALFORMED_URL);
		}
	}
	
	/**
	 * Gets a list of people ids that have been edited. 
	 * By default we show the last 24 hours and only 100 items per page. 
	 * The maximum number of days that can be returned in a single request is 14. 
	 * You can then use the {@link #getPersonChanges(int) getPersonChanges} method to get the actual data that has been changed. 
	 * Please note that the change log system to support this was changed on 
	 * October 5, 2012 and will only show people that have been edited since.
	 * Returns the results of the given page number.
	 * 
	 * @param start The date where the search starts 
	 * @param end The date where the search ends 
	 * @param page The page number to retrieve
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray getChangedPersons(String start, String end, int page) {
		try {
			return new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.getChangedPersonsUrl(start, end, page))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseArray(TMDbStatus.MALFORMED_URL);
		}
	}
	
	/**
	 * Gets a list of all movie ids that have been edited. 
	 * By default we show the last 24 hours and only 100 items per page. 
	 * The maximum number of days that can be returned in a single request is 14. 
	 * You can then use the {@link #getMovieChanges(int) getMovieChanges} method to get the actual data that has been changed.
	 * Please note that the change log system to support this was changed on 
	 * October 5, 2012 and will only show movies that have been edited since.
	 * 
	 * @param start The date where the search starts 
	 * @param end The date where the search ends
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray getAllChangedMovies(String start, String end) {
		try {
			TMDbResponseArray result = new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.getChangedMoviesUrl(start, end))));
			
			for (int p = 2; p <= result.getPages(); p++) {
				TMDbResponseArray page = new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.getChangedMoviesUrl(start, end, p))));
				for (Object obj : page.getData()) {
					result.addData((JSONObject) obj);
				}
			}
			
			return result;
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseArray(TMDbStatus.MALFORMED_URL);
		}
	}
	
	/**
	 * Gets a list of all people ids that have been edited. 
	 * By default we show the last 24 hours and only 100 items per page. 
	 * The maximum number of days that can be returned in a single request is 14. 
	 * You can then use the {@link #getPersonChanges(int) getPersonChanges} method to get the actual data that has been changed. 
	 * Please note that the change log system to support this was changed on 
	 * October 5, 2012 and will only show people that have been edited since.
	 * 
	 * @param start The date where the search starts 
	 * @param end The date where the search ends
	 * @return The TMDb API response array
	 */
	public static TMDbResponseArray getAllChangedPersons(String start, String end) {
		try {
			TMDbResponseArray result = new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.getChangedPersonsUrl(start, end))));
			
			for (int p = 2; p <= result.getPages(); p++) {
				TMDbResponseArray page = new TMDbResponseArray(toJSON(makeApiCallGet(TMDbURLCreator.getChangedPersonsUrl(start, end, p))));
				for (Object obj : page.getData()) {
					result.addData((JSONObject) obj);
				}
			}
			
			return result;
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new TMDbResponseArray(TMDbStatus.MALFORMED_URL);
		}
	}
	
	//endregion
	
}
