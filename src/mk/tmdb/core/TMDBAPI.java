package mk.tmdb.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import mk.tmdb.entity.Account;
import mk.tmdb.entity.Token;
import mk.tmdb.utils.Log;
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
	 * Makes an HTTP request (GET) and gets back the result as a string.
	 * @param url The query URL
	 * @return The result string
	 */
	private static String makeApiCallGet(URL url) {
		StringBuilder result = new StringBuilder();
		try {
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			for (String line = null; (line = reader.readLine()) != null;) {
			    result.append(line).append("\n");
			}
			reader.close();
			
		} catch (Exception e) {
			Log.print(e);
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
			
		} catch (Exception e) {
			Log.print(e);
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
			
			return new ResponseObject(Status.UNKNOWN_ERROR);
		}
	}
	
	public static ResponseObject getAuthenticationToken() {
		try {
			
			return new ResponseObject(toJSON(makeApiCallGet(URLCreator.getAuthTokenUrl())));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseObject(Status.UNKNOWN_ERROR);
		}
	}
	
	public static ResponseObject getAuthenticationSession(Token token) {
		try {
			
			return new ResponseObject(toJSON(makeApiCallGet(URLCreator.getAuthSessionUrl(token.getValue()))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseObject(Status.UNKNOWN_ERROR);
		}
	}
	
	public static ResponseObject getAuthenticationGuestSession() {
		try {
			
			return new ResponseObject(toJSON(makeApiCallGet(URLCreator.getGuestSessionUrl())));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseObject(Status.UNKNOWN_ERROR);
		}
	}
	
	public static ResponseObject getAccountInformation(String sessionID) {
		try {
			
			return new ResponseObject(toJSON(makeApiCallGet(URLCreator.getAccountInfoUrl(sessionID))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseObject(Status.UNKNOWN_ERROR);
		}
	}
	
	public static ResponseObject getAccountInformation(Account account, String sessionID) {
		try {
			
			return new ResponseObject(toJSON(makeApiCallGet(URLCreator.getAccountFavsListsUrl(account.getId(), sessionID))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseObject(Status.UNKNOWN_ERROR);
		}
	}
}
