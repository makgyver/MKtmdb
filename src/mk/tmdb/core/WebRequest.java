package mk.tmdb.core;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import mk.tmdb.utils.Log;
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
public final class WebRequest {

	/**
	 * Makes an HTTP request and gets back the result as a string.
	 * @param url The query URL
	 * @return The result string
	 */
	public static String getHttpString(URL url) {
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
	 * Makes an HTTP request and gets back the result as a JSON object.
	 * @param url The query URL
	 * @return The result JSON object
	 */
	public static JSONObject getHttpJSON(URL url) {
		return (JSONObject) JSONSerializer.toJSON(getHttpString(url));
	}

}
