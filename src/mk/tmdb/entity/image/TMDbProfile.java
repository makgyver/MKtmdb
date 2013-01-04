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

package mk.tmdb.entity.image;

import java.net.MalformedURLException;
import java.net.URL;

import net.sf.json.JSONObject;

import mk.tmdb.core.TMDbConfiguration;
import mk.tmdb.exception.ConfigurationNotLoadedException;
import mk.tmdb.exception.ImageSizeNotSupportedException;
import mk.tmdb.utils.TMDbSize;

/**
 * Class that represents a profile image.
 * 
 * @author Mirko Polato
 *
 */
public class TMDbProfile extends TMDbImage {
	
	/**
	 * Creates a new instance of Profile based on the origin JSON object.
	 * 
	 * @param json The origin JSON object
	 */
	public TMDbProfile(JSONObject json) {
		super(json);
	}

	/**
	 * Copy constructor.
	 * 
	 * @param profile The image to copy
	 */
	public TMDbProfile(TMDbProfile profile) {
		this(profile.getOriginJSON());
	}
	
	@Override
	public URL getUrl(TMDbSize size) throws MalformedURLException, 
										ConfigurationNotLoadedException, 
										ImageSizeNotSupportedException {
		
		if (TMDbConfiguration.isLoaded()) {
			
			if (TMDbConfiguration.getProfileSizes().contains(size)) {
				
				return new URL(TMDbConfiguration.getUrl() + 
						   	   size + 
						   	   path);
				
			} else {
				throw new ImageSizeNotSupportedException();
			}
			
		} else {
			throw new ConfigurationNotLoadedException();
		}
	}
	
}
