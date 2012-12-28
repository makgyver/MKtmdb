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
public enum Size {
	
	W_45 {
	    public String toString() {
	        return "w45";
	    }
	},
	
	W_92 {
	    public String toString() {
	        return "w92";
	    }
	},
	
	W_154 {
	    public String toString() {
	        return "w154";
	    }
	},
	
	W_185 {
	    public String toString() {
	        return "w185";
	    }
	},
	
	W_300 {
	    public String toString() {
	        return "w300";
	    }
	},
	
	W_342 {
	    public String toString() {
	        return "w342";
	    }
	},
	
	W_500 {
	    public String toString() {
	        return "w500";
	    }
	},
	
	W_780 {
	    public String toString() {
	        return "w780";
	    }
	},
	
	W_1280 {
	    public String toString() {
	        return "w1280";
	    }
	},
	
	H_632 {
	    public String toString() {
	        return "h632";
	    }
	},
	
	ORIGINAL {
	    public String toString() {
	        return "original";
	    }
	}
	
}
