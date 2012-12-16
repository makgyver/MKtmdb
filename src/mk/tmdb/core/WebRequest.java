package mk.tmdb.core;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import mk.tmdb.utils.Log;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

public class WebRequest {

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
	
	public static JSONObject getHttpJSON(URL url) {
		return (JSONObject) JSONSerializer.toJSON(getHttpString(url));
	}

}
