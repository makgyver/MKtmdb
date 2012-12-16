package mk.tmdb.core;

import java.net.MalformedURLException;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import mk.tmdb.exception.ConfigurationNotLoadedException;
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
	private static Set<String> posterSizes = Collections.synchronizedSet(new LinkedHashSet<String>());
	private static Set<String> backdropSizes = Collections.synchronizedSet(new LinkedHashSet<String>());
	private static Set<String> profileSizes = Collections.synchronizedSet(new LinkedHashSet<String>());
	private static Set<String> logoSizes = Collections.synchronizedSet(new LinkedHashSet<String>());
	private static Set<String> changeKeys = Collections.synchronizedSet(new LinkedHashSet<String>());
	private static boolean loaded = false;
	private static Date loadedTime = null;

	public static String getUrl() throws ConfigurationNotLoadedException {
		if (!loaded) throw new ConfigurationNotLoadedException();
		return url;
	}

	public static String getSecureUrl() throws ConfigurationNotLoadedException {
		if (!loaded) throw new ConfigurationNotLoadedException();
		return secureUrl;
	}

	public static Set<String> getPosterSizes() throws ConfigurationNotLoadedException {
		if (!loaded) throw new ConfigurationNotLoadedException();
		return posterSizes;
	}

	public static Set<String> getBackdropSizes() throws ConfigurationNotLoadedException {
		if (!loaded) throw new ConfigurationNotLoadedException();
		return backdropSizes;
	}

	public static Set<String> getProfileSizes() throws ConfigurationNotLoadedException {
		if (!loaded) throw new ConfigurationNotLoadedException();
		return profileSizes;
	}

	public static Set<String> getLogoSizes() throws ConfigurationNotLoadedException {
		if (!loaded) throw new ConfigurationNotLoadedException();
		return logoSizes;
	}

	public static Set<String> getChangeKeys() throws ConfigurationNotLoadedException {
		if (!loaded) throw new ConfigurationNotLoadedException();
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
