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

package mk.tmdb.exception;

import mk.tmdb.core.TMDbConfiguration;

/**
 * Signals that the Configuration information was not loaded. 
 * To avoid this exception call the method {@link TMDbConfiguration#load() Configuration.load()} or
 * {@link TMDbConfiguration#forceLoad() Configuration.forceLoad()}. 
 * 
 * @author Mirko Polato
 *
 */
public class ConfigurationNotLoadedException extends Exception {

	private static final long serialVersionUID = 1L;
	
	/**
	 * The mistake string.
	 */
	private String mistake;
	
	/**
	 * Default constructor: creates a new instance of ConfigurationNotLoadedException.
	 */
	public ConfigurationNotLoadedException() {
		super("Configuration not loaded");
		mistake = "Configuration not loaded";
	}
	
	/**
	 * Creates a new instance of ConfigurationNotLoadedException with the given mistake message.
	 * 
	 * @param exception The mistake message
	 */
	public ConfigurationNotLoadedException(String exception) {
		super(exception);
		mistake = exception;
	}
	
	/**
	 * Gets the mistake message.
	 * 
	 * @return The mistake message
	 */
	public String getError() {
		return mistake;
	}
	
	@Override
	public String toString() {
		return "ConfigurationNotLoadedException: " + mistake;
	}
}
