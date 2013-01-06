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
import mk.tmdb.exception.TMDbConfigurationNotLoadedException;
import mk.tmdb.exception.TMDbImageSizeNotSupportedException;
import mk.tmdb.utils.TMDbSize;

/**
 * Class that represents a poster image.
 * 
 * @author Mirko Polato
 *
 */
public class TMDbPoster extends TMDbImage {
	
	/**
	 * Creates a new instance of Poster based on the origin JSON object.
	 * 
	 * @param json The origin JSON object
	 */
	public TMDbPoster(JSONObject json) {
		super(json);
	}

	/**
	 * Copy constructor.
	 * 
	 * @param poster The image to copy
	 */
	public TMDbPoster(TMDbPoster poster) {
		this(poster.getOriginJSON());
	}
	
	@Override
	public URL getUrl(TMDbSize size) throws MalformedURLException, 
										TMDbConfigurationNotLoadedException, 
										TMDbImageSizeNotSupportedException {
		
		if (TMDbConfiguration.isLoaded()) {
			
			if (TMDbConfiguration.getPosterSizes().contains(size)) {
				
				return new URL(TMDbConfiguration.getUrl() + 
						   	   size + 
						   	   path);
				
			} else {
				throw new TMDbImageSizeNotSupportedException();
			}
			
		} else {
			throw new TMDbConfigurationNotLoadedException();
		}
	}
	
}
