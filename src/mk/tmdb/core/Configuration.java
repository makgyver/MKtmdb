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

/**
 * Class that contains the configuration information. 
 * First of all is necessary to load these information calling load method.
 * Then if you want to be sure that the configuration is up to date call the 
 * forceLoad method.
 * 
 * @author mirkopolato
 *
 */
public class Configuration {
	
	/**
	 * Base URL for getting the images.
	 */
	private static String url;
	
	/**
	 * Base secure URL (https) for getting the images.
	 */
	private static String secureUrl;
	
	/**
	 * List of poster sizes.
	 */
	private static Set<String> posterSizes = Collections.synchronizedSet(new LinkedHashSet<String>());
	
	/**
	 * List of backdrop sizes.
	 */
	private static Set<String> backdropSizes = Collections.synchronizedSet(new LinkedHashSet<String>());
	
	/**
	 * List of profile sizes.
	 */
	private static Set<String> profileSizes = Collections.synchronizedSet(new LinkedHashSet<String>());
	
	/**
	 * List of logo sizes.
	 */
	private static Set<String> logoSizes = Collections.synchronizedSet(new LinkedHashSet<String>());
	
	/**
	 * List of change keys.
	 */
	private static Set<String> changeKeys = Collections.synchronizedSet(new LinkedHashSet<String>());
	
	/**
	 * Whether the configuration information has been loaded or not.
	 */
	private static boolean loaded = false;
	
	/**
	 * Last time when the configuration information has been loaded.
	 */
	private static Date loadedTime = null;

	
	/**
	 * Gets the base URL.
	 * @return The base URL
	 * @throws ConfigurationNotLoadedException Throws if the load method has not been called before
	 */
	public static String getUrl() throws ConfigurationNotLoadedException {
		if (!loaded) throw new ConfigurationNotLoadedException();
		return url;
	}

	/**
	 * Gets the secure base URL.
	 * @return The secure base URL
	 * @throws ConfigurationNotLoadedException Throws if the load method has not been called before
	 */
	public static String getSecureUrl() throws ConfigurationNotLoadedException {
		if (!loaded) throw new ConfigurationNotLoadedException();
		return secureUrl;
	}

	/**
	 * Gets the poster sizes.
	 * @return The poster sisez
	 * @throws ConfigurationNotLoadedException Throws if the load method has not been called before
	 */
	public static Set<String> getPosterSizes() throws ConfigurationNotLoadedException {
		if (!loaded) throw new ConfigurationNotLoadedException();
		return posterSizes;
	}

	/**
	 * Gets the backdrop sizes.
	 * @return The backdrop sizes
	 * @throws ConfigurationNotLoadedException Throws if the load method has not been called before
	 */
	public static Set<String> getBackdropSizes() throws ConfigurationNotLoadedException {
		if (!loaded) throw new ConfigurationNotLoadedException();
		return backdropSizes;
	}

	/**
	 * Gets the profile sizes.
	 * @return The profile sizes.
	 * @throws ConfigurationNotLoadedException Throws if the load method has not been called before
	 */
	public static Set<String> getProfileSizes() throws ConfigurationNotLoadedException {
		if (!loaded) throw new ConfigurationNotLoadedException();
		return profileSizes;
	}

	/**
	 * Gets the logo sizes.
	 * @return The logo sizes
	 * @throws ConfigurationNotLoadedException Throws if the load method has not been called before
	 */
	public static Set<String> getLogoSizes() throws ConfigurationNotLoadedException {
		if (!loaded) throw new ConfigurationNotLoadedException();
		return logoSizes;
	}

	/**
	 * Gets the change keys.
	 * @return The change keys
	 * @throws ConfigurationNotLoadedException Throws if the load method has not been called before
	 */
	public static Set<String> getChangeKeys() throws ConfigurationNotLoadedException {
		if (!loaded) throw new ConfigurationNotLoadedException();
		return changeKeys;
	}

	/**
	 * Gets whether the configuration information has been loaded.
	 * @return whether the configuration information has been loaded.
	 */
	public static boolean isLoaded() {
		return loaded;
	}

	/**
	 * Gets the last time that the configuration information has been loaded.
	 * @return The last time that the configuration information has been loaded.
	 */
	public static Date getLoadedTime() {
		return loadedTime;
	}

	/**
	 * Loads the configuration information.
	 */
	public static void load() {
		try {
			
			if (loaded) return;
			
			JSONObject json = WebRequest.getHttpJSON(URLCreator.getConfigurationUrl());
			
			url = json.getString(Constants.BASE_URL);
			secureUrl = json.getString(Constants.SECURE_URL);
			
			JSONObject jimages = json.getJSONObject(Constants.IMAGES);
			
			JSONArray posters = jimages.getJSONArray(Constants.POSTER_SIZES);
			for (Object obj : posters) {
				posterSizes.add((String) obj);
			}
			
			JSONArray backdrops = jimages.getJSONArray(Constants.BACKDROP_SIZES);
			for (Object obj : backdrops) {
				backdropSizes.add((String) obj);
			}
			
			JSONArray profiles = jimages.getJSONArray(Constants.PROFILE_SIZES);
			for (Object obj : profiles) {
				profileSizes.add((String) obj);
			}
			
			JSONArray logos = jimages.getJSONArray(Constants.LOGO_SIZES);
			for (Object obj : logos) {
				logoSizes.add((String) obj);
			}
			
			JSONArray keys = json.getJSONArray(Constants.CHANGE_KEYS);
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
	
	/**
	 * Forces the loading of the configuration information.
	 */
	public void forceLoad() {
		loaded = false;
		load();
	}
	
}
