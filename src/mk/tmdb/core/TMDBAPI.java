package mk.tmdb.core;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;

import mk.tmdb.entity.Account;
import mk.tmdb.entity.Token;
import mk.tmdb.utils.Log;
import mk.tmdb.utils.ResponseArray;
import mk.tmdb.utils.ResponseObject;
import mk.tmdb.utils.Status;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

/**
 * Static class that offers methods for getting HTTP request.
 * Before to make any request is needed loading the configuration ( @see Configuration#Load )
 * to set up the environment, otherwise some request can raise a ConfigurationNotLoadedException.
 * 
 * @author Mirko Polato
 *
 */
public final class TMDBAPI {

	/**
	 * Timeout in milliseconds.
	 */
	public static int TIMEOUT = 20000; // 20 seconds
	
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
			conn.setReadTimeout(TIMEOUT);
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
			conn.setReadTimeout(TIMEOUT);
			
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

	
	public static ResponseObject getConfiguration() {
		try {
			
			return new ResponseObject(toJSON(makeApiCallGet(URLCreator.getConfigurationUrl())));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseObject(Status.MALFORMED_URL);
		}
	}
	
	public static ResponseObject getAuthenticationToken() {
		try {
			
			return new ResponseObject(toJSON(makeApiCallGet(URLCreator.getAuthTokenUrl())));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseObject(Status.MALFORMED_URL);
		}
	}
	
	public static ResponseObject getAuthenticationSession(Token token) {
		try {
			
			return new ResponseObject(toJSON(makeApiCallGet(URLCreator.getAuthSessionUrl(token.getValue()))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseObject(Status.MALFORMED_URL);
		}
	}
	
	public static ResponseObject getAuthenticationGuestSession() {
		try {
			
			return new ResponseObject(toJSON(makeApiCallGet(URLCreator.getGuestSessionUrl())));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseObject(Status.MALFORMED_URL);
		}
	}
	
	public static ResponseObject getAccountInformation(String sessionID) {
		try {
			
			return new ResponseObject(toJSON(makeApiCallGet(URLCreator.getAccountInfoUrl(sessionID))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseObject(Status.MALFORMED_URL);
		}
	}
	
	public static ResponseArray getFavoritesLists(Account account, String sessionID) {
		try {
			
			return new ResponseArray(toJSON(makeApiCallGet(URLCreator.getAccountFavsListsUrl(account.getId(), sessionID))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseArray(Status.MALFORMED_URL);
		}
	}
	
	public static ResponseArray getFavoritesMovies(Account account, String sessionID) {
		try {
			
			return new ResponseArray(toJSON(makeApiCallGet(URLCreator.getAccountFavsMoviesUrl(account.getId(), sessionID))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseArray(Status.MALFORMED_URL);
		}
	}
	
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
	
	public static ResponseObject removeMovieFromFavorites(Account account, String sessionID, int movieID) {
		try {
			
			JSONObject json = new JSONObject();
			json.put(Constants.MOVIE_ID, movieID);
			json.put(Constants.FAVORITE, false);
			
			return new ResponseObject(toJSON(makeApiCallPost(URLCreator.addMovieToFavsUrl(account.getId(), sessionID), json)));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseObject(Status.MALFORMED_URL);
		}
	}
	
	public static ResponseArray getRatedMovies(Account account, String sessionID) {
		try {
			
			return new ResponseArray(toJSON(makeApiCallGet(URLCreator.getRatedMoviesUrl(account.getId(), sessionID))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseArray(Status.MALFORMED_URL);
		}
	}
	
	public static ResponseArray getMovieWatchList(Account account, String sessionID) {
		try {
			
			return new ResponseArray(toJSON(makeApiCallGet(URLCreator.getWatchlistUrl(account.getId(), sessionID))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseArray(Status.MALFORMED_URL);
		}
	}
	
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
	
	public static ResponseObject removeMovieFromWatchlist(Account account, String sessionID, int movieID) {
		try {
			
			JSONObject json = new JSONObject();
			json.put(Constants.MOVIE_ID, movieID);
			json.put(Constants.WATCHLIST, false);
			
			return new ResponseObject(toJSON(makeApiCallPost(URLCreator.addMovieToWatchlistUrl(account.getId(), sessionID), json)));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseObject(Status.MALFORMED_URL);
		}
	}
	
	
}
