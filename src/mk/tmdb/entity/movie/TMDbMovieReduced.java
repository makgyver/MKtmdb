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

package mk.tmdb.entity.movie;

import mk.tmdb.core.TMDbConstants;
import net.sf.json.JSONObject;

/**
 * Class that represents a Movie with limited features.
 * MovieReduced class has more features than MovieThumbnail class.</br>
 * To get a more detailed class see the following classes:
 * <ul>
 * <li>{@link TMDbMovie}</li>
 * <li>{@link TMDbMovieFull}</li>
 * </ul>
 * 
 * @author Mirko Polato
 *
 */
public class TMDbMovieReduced extends TMDbMovieThumbnail {

	//region Fields
	
	/**
	 * The movie vote average
	 */
	protected Double voteAverage = null;
	
	/**
	 * The movie vote count
	 */
	protected Integer voteCount = null;
	
	/**
	 * The movie backdrop path
	 */
	protected String backdropPath = null;
	
	/**
	 * The movie popularity expressed in percentage.
	 */
	protected Double popularity = null;
	
	//endregion
	
	/**
	 * Creates a new instance of MovieReduced based on the origin JSON object.
	 *  
	 * @param json The origin JSON object
	 */
	public TMDbMovieReduced(JSONObject json) {
		super(json);
		parseJSON(json);
	}
	
	/**
	 * Copy constructor.
	 * 
	 * @param movie The movie to copy
	 */
	public TMDbMovieReduced(TMDbMovieThumbnail movie) {
		this(movie.getOriginJSON());
	}
	
	//region Getters/Setters
	
	/**
	 * Gets the movie vote average.
	 * 
	 * @return The movie vote average
	 */
	public double getVoteAverage() {
		return voteAverage;
	}
	
	/**
	 * Sets the movie vote average
	 * 
	 * @param voteAverage The new movie vote average
	 */
	public void setVoteAverage(double voteAverage) {
		this.voteAverage = voteAverage;
	}
	
	/**
	 * Gets the movie backdrop path.
	 * 
	 * @return The movie backdrop path
	 */
	public String getBackdropPath() {
		return backdropPath;
	}

	/**
	 * Sets the movie backdrop path.
	 * 
	 * @param backdropPath The new backrop path
	 */
	public void setBackdropPath(String backdropPath) {
		this.backdropPath = backdropPath;
	}

	/**
	 * Gets the movie vote count.
	 * 
	 * @return The movie votes count
	 */
	public int getVoteCount() {
		return voteCount;
	}

	/**
	 * Sets the movie vote count.
	 * 
	 * @param voteCount The new movie votes count
	 */
	public void setVoteCount(int voteCount) {
		this.voteCount = voteCount;
	}
	
	/**
	 * Checks if the vote average is set.
	 * 
	 * @return Whether the vote average is set or not
	 */
	public boolean isVoteAverageSet() {
		return voteAverage != null;
	}
	
	/**
	 * Checks if the backdrop path is set.
	 * 
	 * @return Whether the backdrop path is set or not
	 */
	public boolean isBackropPathSet() {
		return backdropPath != null;
	}
	
	/**
	 * Checks if the vote count is set.
	 * 
	 * @return Whether the vote count is set or not
	 */
	public boolean isVoteCountSet() {
		return voteCount != null;
	}
	
	/**
	 * Gets the movie popularity (in percentage).
	 * 
	 * @return The movie popularity (in percentage).
	 */
	public double getPopularity() {
		return popularity;
	}

	/**
	 * Sets the movie popularity.
	 * 
	 * @param popularity The new movie popularity
	 */
	public void setPopularity(double popularity) {
		this.popularity = Math.max(popularity, 100);
	}

	/**
	 * Checks if the popularity is set.
	 * 
	 * @return Whether the popularity is set or not
	 */
	public boolean isPopularitySet() {
		return popularity != null;
	}
	
	//endregion

	/**
	 * Parses the origin JSON object.
	 */
	private boolean parseJSON(JSONObject json) {
		
		if (json.isNullObject()) return false;
		
		if (json.has(TMDbConstants.AVERAGE)) setVoteAverage(json.getDouble(TMDbConstants.AVERAGE));
		if (json.has(TMDbConstants.COUNT)) setVoteCount(json.getInt(TMDbConstants.COUNT));
		if (json.has(TMDbConstants.BACKDROP_PATH)) setBackdropPath(json.getString(TMDbConstants.BACKDROP_PATH));
		if (json.has(TMDbConstants.POPULARITY)) setPopularity(json.getDouble(TMDbConstants.POPULARITY));
		
		return true;
	}
	
}
