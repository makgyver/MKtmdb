package mk.tmdb.core;

import java.net.MalformedURLException;
import java.util.Date;
import java.util.Vector;

import mk.tmdb.exception.InvalidApiKeyException;
import mk.tmdb.utils.Log;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Configuration {

	private static final String IMAGES = "images";
	private static final String BASE_URL = "base_url";
	private static final String SECURE_URL = "secure_base_url";
	private static final String POSTER_SIZES = "poster_sizes";
	private static final String BACKDROP_SIZES = "backdrop_sizes";
	private static final String PROFILE_SIZES = "profile_sizes";
	private static final String LOGO_SIZES = "logo_sizes";
	private static final String CHANGE_KEYS = "change_keys";
	
	private static String url;
	private static String secureUrl;
	private static Vector<String> posterSizes = new Vector<String>();
	private static Vector<String> backdropSizes = new Vector<String>();
	private static Vector<String> profileSizes = new Vector<String>();
	private static Vector<String> logoSizes = new Vector<String>();
	private static Vector<String> changeKeys = new Vector<String>();
	private static boolean loaded = false;
	private static Date loadedTime = null;

	public static String getUrl() {
		return url;
	}

	public static String getSecureUrl() {
		return secureUrl;
	}

	public static Vector<String> getPosterSizes() {
		return posterSizes;
	}

	public static Vector<String> getBackdropSizes() {
		return backdropSizes;
	}

	public static Vector<String> getProfileSizes() {
		return profileSizes;
	}

	public static Vector<String> getLogoSizes() {
		return logoSizes;
	}

	public static Vector<String> getChangeKeys() {
		return changeKeys;
	}

	public static boolean isLoaded() {
		return loaded;
	}

	public static Date getLoadedTime() {
		return loadedTime;
	}

	public static void Load() {
		try {
			
			if (loaded) return;
			
			JSONObject json = WebRequest.getHttpJSON(URLCreator.getConfigurationUrl());
			
			url = json.getString(BASE_URL);
			secureUrl = json.getString(SECURE_URL);
			
			JSONObject jimages = json.getJSONObject(IMAGES);
			
			JSONArray posters = jimages.getJSONArray(POSTER_SIZES);
			for (Object obj : posters) {
				posterSizes.add((String) obj);
			}
			
			JSONArray backdrops = jimages.getJSONArray(BACKDROP_SIZES);
			for (Object obj : backdrops) {
				backdropSizes.add((String) obj);
			}
			
			JSONArray profiles = jimages.getJSONArray(PROFILE_SIZES);
			for (Object obj : profiles) {
				profileSizes.add((String) obj);
			}
			
			JSONArray logos = jimages.getJSONArray(LOGO_SIZES);
			for (Object obj : logos) {
				logoSizes.add((String) obj);
			}
			
			JSONArray keys = json.getJSONArray(CHANGE_KEYS);
			for (Object obj : keys) {
				changeKeys.add((String) obj);
			}
			
			loaded = true;
			loadedTime = new Date();
			
		} catch (MalformedURLException e) {
			Log.print(e);
		} catch (InvalidApiKeyException e) {
			Log.print(e);
		}
	}
	
	public void ForceLoad() {
		loaded = false;
		Load();
	}
}
