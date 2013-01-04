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

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import mk.tmdb.entity.TMDbKeyword;
import mk.tmdb.entity.TMDbLanguage;
import mk.tmdb.entity.image.TMDbBackdrop;
import mk.tmdb.entity.image.TMDbPoster;
import mk.tmdb.entity.person.TMDbMovieCast;
import mk.tmdb.entity.person.TMDbMovieCrew;
import mk.tmdb.entity.trailer.TMDbTrailer;
import mk.tmdb.exception.ResponseException;
import mk.tmdb.utils.TMDbLog;
import net.sf.json.JSONObject;

/**
 * Class that represents a full featured movie.</br>
 * To get a less detailed class see the following classes:
 * <ul>
 * <li>{@link TMDbMovieThumbnail}</li>
 * <li>{@link TMDbMovieReduced}</li>
 * <li>{@link TMDbMovie}</li>
 * </ul>
 * 
 * @author Mirko Polato
 *
 */
public class TMDbMovieFull extends TMDbMovie {

	//region Fields
	
	/**
	 * The movie backdrops.
	 */
	private Set<TMDbBackdrop> backdrops = Collections.synchronizedSet(new LinkedHashSet<TMDbBackdrop>());
	
	/**
	 * The movie posters.
	 */
	private Set<TMDbPoster> posters = Collections.synchronizedSet(new LinkedHashSet<TMDbPoster>());
	
	/**
	 * The movie keywords.
	 */
	private Set<TMDbKeyword> keywords = Collections.synchronizedSet(new LinkedHashSet<TMDbKeyword>());
	
	/**
	 * The movie translations.
	 */
	private Set<TMDbLanguage> translations = Collections.synchronizedSet(new LinkedHashSet<TMDbLanguage>());
	
	/**
	 * The movie trailers.
	 */
	private Set<TMDbTrailer> trailers = Collections.synchronizedSet(new LinkedHashSet<TMDbTrailer>());
	
	/**
	 * The movie cast.
	 */
	private Set<TMDbMovieCast> cast = Collections.synchronizedSet(new LinkedHashSet<TMDbMovieCast>());
	
	/**
	 * The movie crew.
	 */
	private Set<TMDbMovieCrew> crew = Collections.synchronizedSet(new LinkedHashSet<TMDbMovieCrew>());
	
	//endregion
	
	/**
	 * Creates a new instance of MovieFull based on the origin JSON object.
	 * 
	 * @param json The origin JSON object
	 */
	public TMDbMovieFull(JSONObject json) {
		super(json);
	}
	
	/**
	 * Creates a new instance of MovieFull based on the origin JSON object and
	 * retrieves all the information.
	 * 
	 * @param json The origin JSON object
	 * @param loadAll Whether the remaining information have to be loaded or not
	 */
	public TMDbMovieFull(JSONObject json, boolean loadAll) {
		super(json);
		if (loadAll)
			try {
				getFullInformation();
			} catch (ResponseException e) {
				TMDbLog.print(e);
			}
	}
	
	/**
	 * Copy constructor.
	 * 
	 * @param movie The movie to copy
	 */
	public TMDbMovieFull(TMDbMovieThumbnail movie) {
		this(movie.getOriginJSON());
	}

	/**
	 * Copy constructor.
	 * 
	 * @param movie The movie to copy
	 * @param loadAll Whether the remaining information have to be loaded or not
	 */
	public TMDbMovieFull(TMDbMovieThumbnail movie, boolean loadAll) {
		this(movie.getOriginJSON());
		if (loadAll)
			try {
				getFullInformation();
			} catch (ResponseException e) {
				TMDbLog.print(e);
			}
	}
	
	//region Getters/Setters
	
	/**
	 * Gets the movie backdrops.
	 * 
	 * @return The movie backdrops
	 */
	public Set<TMDbBackdrop> getBackdrops() {
		return backdrops;
	}

	/**
	 * Sets the movie backdrops.
	 * 
	 * @param backdrops The movie backdrops
	 */
	public void setBackdrops(Set<TMDbBackdrop> backdrops) {
		this.backdrops.clear();
		this.backdrops.addAll(backdrops);
	}

	/**
	 * Gets the movie posters.
	 * 
	 * @return The movie posters
	 */
	public Set<TMDbPoster> getPosters() {
		return posters;
	}

	/**
	 * Sets the movie posters.
	 * 
	 * @param posters The movie posers
	 */
	public void setPosters(Set<TMDbPoster> posters) {
		this.posters.clear();
		this.posters.addAll(posters);
	}

	/**
	 * Gets the movie keywords.
	 * 
	 * @return The movie keywords
	 */
	public Set<TMDbKeyword> getKeywords() {
		return keywords;
	}

	/**
	 * Sets the movie keywords.
	 * 
	 * @param keywords The movie keywords
	 */
	public void setKeywords(Set<TMDbKeyword> keywords) {
		this.keywords.clear();
		this.keywords.addAll(keywords);
	}

	/**
	 * Gets the movie translations.
	 * 
	 * @return The movie translations
	 */
	public Set<TMDbLanguage> getTranslations() {
		return translations;
	}

	/**
	 * Sets the movie translations.
	 * 
	 * @param translations The movie translations
	 */
	public void setTranslations(Set<TMDbLanguage> translations) {
		this.translations.clear();
		this.translations.addAll(translations);
	}

	/**
	 * Gets the movie trailers.
	 * 
	 * @return The movie trailers
	 */
	public Set<TMDbTrailer> getTrailers() {
		return trailers;
	}

	/**
	 * Sets the movie trailers.
	 * 
	 * @param trailers The movie trailers
	 */
	public void setTrailers(Set<TMDbTrailer> trailers) {
		this.trailers.clear();
		this.trailers.addAll(trailers);
	}

	/**
	 * Gets the movie cast.
	 * 
	 * @return The movie cast
	 */
	public Set<TMDbMovieCast> getCast() {
		return cast;
	}

	/**
	 * Sets the movie cast.
	 * 
	 * @param cast The new movie cast
	 */
	public void setCast(Set<TMDbMovieCast> cast) {
		this.cast.clear();
		this.cast.addAll(cast);
	}

	/**
	 * Gets the movie crew.
	 * 
	 * @return The movie crew
	 */
	public Set<TMDbMovieCrew> getCrew() {
		return crew;
	}

	/**
	 * Sets the movie crew.
	 * 
	 * @param crew The new movie crew
	 */
	public void setCrew(Set<TMDbMovieCrew> crew) {
		this.crew.clear();
		this.crew.addAll(crew);
	}
	
	//endregion
	
	/**
	 * Gets all the information.
	 * 
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
	private void getFullInformation() throws ResponseException {
		
		setCast(getCastInformation(id));
		setCrew(getCrewInformation(id));
		setKeywords(getKeywords(id));
		setTrailers(getTrailers(id));
		setTranslations(getTranslations(id));
		setPosters(getPosters(id));
		setBackdrops(getBackdrops(id));
	}
	
}
