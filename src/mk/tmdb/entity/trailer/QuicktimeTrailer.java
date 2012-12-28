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

package mk.tmdb.entity.trailer;

import java.net.MalformedURLException;
import java.net.URL;

import mk.tmdb.core.Constants;
import mk.tmdb.utils.Log;
import net.sf.json.JSONObject;

/**
 * Class that represents a Quicktime trailer.
 * 
 * @author Mirko Polato
 *
 */
public class QuicktimeTrailer extends Trailer {
	
	/**
	 * Creates a new instance of Quicktime trailer based on the origin JSON object and 
	 * assigning the given name.
	 * 
	 * @param json The origin JSON object
	 * @param name The trailer name
	 */
	public QuicktimeTrailer(JSONObject json, String name) {
		super(json, Constants.QUICKTIME);
		setName(name);
		parseJSON(json);
	}
	
	/**
	 * Copy constructor.
	 * 
	 * @param trailer The trailer to copy
	 * @param name The name of the trailer
	 */
	public QuicktimeTrailer(QuicktimeTrailer trailer, String name) {
		this(trailer.getOriginJSON(), name);
	}

	/**
	 * Parses the origin JSON object.
	 */
	private boolean parseJSON(JSONObject json) {
		
		if (json.isNullObject()) return false;
		
		setSize(json.getString(Constants.SIZE));
		
		try {
			setLink(new URL(json.getString(Constants.LINK)));
		} catch (MalformedURLException e) {
			Log.print(e);
		}
		
		return true;
	}
	
}
