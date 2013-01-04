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

package mk.tmdb.utils;

/**
 * Enumeration with the possible sizes of all images.
 * 
 * @author Mirko Polato
 *
 */
public enum TMDbSize {
	
	/**
	 * Image width 45 px.
	 */
	W_45 {
	    public String toString() {
	        return "w45";
	    }
	},
	
	/**
	 * Image width 92 px.
	 */
	W_92 {
	    public String toString() {
	        return "w92";
	    }
	},
	
	/**
	 * Image width 154 px.
	 */
	W_154 {
	    public String toString() {
	        return "w154";
	    }
	},
	
	/**Image width 185 px.
	 * 
	 */
	W_185 {
	    public String toString() {
	        return "w185";
	    }
	},
	
	/**
	 * Image width 300 px.
	 */
	W_300 {
	    public String toString() {
	        return "w300";
	    }
	},
	
	/**
	 * Image width 342 px.
	 */
	W_342 {
	    public String toString() {
	        return "w342";
	    }
	},
	
	/**
	 * Image width 500 px.
	 */
	W_500 {
	    public String toString() {
	        return "w500";
	    }
	},
	
	/**
	 * Image width 780 px.
	 */
	W_780 {
	    public String toString() {
	        return "w780";
	    }
	},
	
	/**
	 * Image width 1280 px.
	 */
	W_1280 {
	    public String toString() {
	        return "w1280";
	    }
	},
	
	/**
	 * Image height 632 px.
	 */
	H_632 {
	    public String toString() {
	        return "h632";
	    }
	},
	
	/**
	 * Original image size.
	 */
	ORIGINAL {
	    public String toString() {
	        return "original";
	    }
	}
	
}
