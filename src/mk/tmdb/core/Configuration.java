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

import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import mk.tmdb.exception.ConfigurationNotLoadedException;
import mk.tmdb.exception.ResponseException;
import mk.tmdb.utils.ResponseObject;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Class that contains the configuration information. 
 * First of all is necessary to {@link #load() load} these information calling load method.
 * Then if you want to be sure that the configuration is up to date call the 
 * {@link #forceLoad() forceLoad} method.
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
	 * 
	 * @return The base URL
	 * @throws ConfigurationNotLoadedException Throws if the load method has not been called before
	 */
	public static String getUrl() throws ConfigurationNotLoadedException {
		if (!loaded) throw new ConfigurationNotLoadedException();
		return url;
	}

	/**
	 * Gets the secure (https) base URL.
	 * 
	 * @return The secure base URL
	 * @throws ConfigurationNotLoadedException Throws if the load method has not been called before
	 */
	public static String getSecureUrl() throws ConfigurationNotLoadedException {
		if (!loaded) throw new ConfigurationNotLoadedException();
		return secureUrl;
	}

	/**
	 * Gets the poster sizes.
	 * 
	 * @return The poster sisez
	 * @throws ConfigurationNotLoadedException Throws if the load method has not been called before
	 */
	public static Set<String> getPosterSizes() throws ConfigurationNotLoadedException {
		if (!loaded) throw new ConfigurationNotLoadedException();
		return posterSizes;
	}

	/**
	 * Gets the backdrop sizes.
	 * 
	 * @return The backdrop sizes
	 * @throws ConfigurationNotLoadedException Throws if the load method has not been called before
	 */
	public static Set<String> getBackdropSizes() throws ConfigurationNotLoadedException {
		if (!loaded) throw new ConfigurationNotLoadedException();
		return backdropSizes;
	}

	/**
	 * Gets the profile sizes.
	 * 
	 * @return The profile sizes.
	 * @throws ConfigurationNotLoadedException Throws if the load method has not been called before
	 */
	public static Set<String> getProfileSizes() throws ConfigurationNotLoadedException {
		if (!loaded) throw new ConfigurationNotLoadedException();
		return profileSizes;
	}

	/**
	 * Gets the logo sizes.
	 * 
	 * @return The logo sizes
	 * @throws ConfigurationNotLoadedException Throws if the load method has not been called before
	 */
	public static Set<String> getLogoSizes() throws ConfigurationNotLoadedException {
		if (!loaded) throw new ConfigurationNotLoadedException();
		return logoSizes;
	}

	/**
	 * Gets the change keys.
	 * 
	 * @return The change keys
	 * @throws ConfigurationNotLoadedException Throws if the load method has not been called before
	 */
	public static Set<String> getChangeKeys() throws ConfigurationNotLoadedException {
		if (!loaded) throw new ConfigurationNotLoadedException();
		return changeKeys;
	}

	/**
	 * Gets whether the configuration information has been loaded.
	 * 
	 * @return Whether the configuration information has been loaded.
	 */
	public static boolean isLoaded() {
		return loaded;
	}

	/**
	 * Gets the last time that the configuration information has been loaded.
	 * 
	 * @return The last time that the configuration information has been loaded.
	 */
	public static Date getLoadedTime() {
		return loadedTime;
	}

	/**
	 * Loads the configuration information (if it was not already loaded). 
	 * 
	 * @throws ResponseException Throws if the API call is failed for some reason.
	 */
	public static void load() throws ResponseException {
			
		if (loaded) return;
		
		ResponseObject response = TMDbAPI.getConfiguration(); 
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		}
		
		JSONObject json = response.getData().getJSONObject(Constants.IMAGES);
		
		url = json.getString(Constants.BASE_URL);
		secureUrl = json.getString(Constants.SECURE_URL);
		
		JSONArray posters = json.getJSONArray(Constants.POSTER_SIZES);
		for (Object obj : posters) {
			posterSizes.add((String) obj);
		}
		
		JSONArray backdrops = json.getJSONArray(Constants.BACKDROP_SIZES);
		for (Object obj : backdrops) {
			backdropSizes.add((String) obj);
		}
		
		JSONArray profiles = json.getJSONArray(Constants.PROFILE_SIZES);
		for (Object obj : profiles) {
			profileSizes.add((String) obj);
		}
		
		JSONArray logos = json.getJSONArray(Constants.LOGO_SIZES);
		for (Object obj : logos) {
			logoSizes.add((String) obj);
		}
		
		JSONArray keys = response.getData().getJSONArray(Constants.CHANGE_KEYS);
		for (Object obj : keys) {
			changeKeys.add((String) obj);
		}
		
		loaded = true;
		loadedTime = new Date();
	}
	
	/**
	 * Forces the loading of the configuration information.
	 * 
	 * @throws ResponseException Throws if the API call fail for some reason.
	 */
	public void forceLoad() throws ResponseException {
		loaded = false;
		load();
	}
	
}
