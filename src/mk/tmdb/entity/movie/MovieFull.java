package mk.tmdb.entity.movie;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import mk.tmdb.entity.Keyword;
import mk.tmdb.entity.Language;
import mk.tmdb.entity.image.Backdrop;
import mk.tmdb.entity.image.Poster;
import mk.tmdb.entity.person.MovieCast;
import mk.tmdb.entity.person.MovieCrew;
import mk.tmdb.entity.trailer.Trailer;
import mk.tmdb.exception.ResponseException;
import mk.tmdb.utils.Log;
import net.sf.json.JSONObject;

/**
 * Class that represents a full featured movie.
 * To get a less detailed class see the following classes:
 * <ul>
 * <li>{@link MovieThumbnail}</li>
 * <li>{@link MovieReduced}</li>
 * <li>{@link Movie}</li>
 * </ul>
 * 
 * @author Mirko Polato
 *
 */
public class MovieFull extends Movie {

	//region Fields
	
	/**
	 * The movie backdrops.
	 */
	private Set<Backdrop> backdrops = Collections.synchronizedSet(new LinkedHashSet<Backdrop>());
	
	/**
	 * The movie posters.
	 */
	private Set<Poster> posters = Collections.synchronizedSet(new LinkedHashSet<Poster>());
	
	/**
	 * The movie keywords.
	 */
	private Set<Keyword> keywords = Collections.synchronizedSet(new LinkedHashSet<Keyword>());
	
	/**
	 * The movie translations.
	 */
	private Set<Language> translations = Collections.synchronizedSet(new LinkedHashSet<Language>());
	
	/**
	 * The movie trailers.
	 */
	private Set<Trailer> trailers = Collections.synchronizedSet(new LinkedHashSet<Trailer>());
	
	/**
	 * The movie cast.
	 */
	private Set<MovieCast> cast = Collections.synchronizedSet(new LinkedHashSet<MovieCast>());
	
	/**
	 * The movie crew.
	 */
	private Set<MovieCrew> crew = Collections.synchronizedSet(new LinkedHashSet<MovieCrew>());
	
	//endregion
	
	/**
	 * Creates a new instance of MovieFull based on the origin JSON object.
	 * 
	 * @param json The origin JSON object
	 */
	public MovieFull(JSONObject json) {
		super(json);
	}
	
	/**
	 * Creates a new instance of MovieFull based on the origin JSON object and
	 * retrieves all the information.
	 * 
	 * @param json The origin JSON object
	 * @param loadAll Whether the remaining information have to be loaded or not
	 */
	public MovieFull(JSONObject json, boolean loadAll) {
		super(json);
		if (loadAll)
			try {
				getFullInformation();
			} catch (ResponseException e) {
				Log.print(e);
			}
	}
	
	/**
	 * Copy constructor.
	 * 
	 * @param movie The movie to copy
	 */
	public MovieFull(MovieThumbnail movie) {
		this(movie.getOriginJSON());
	}

	/**
	 * Copy constructor.
	 * 
	 * @param movie The movie to copy
	 * @param loadAll Whether the remaining information have to be loaded or not
	 */
	public MovieFull(MovieThumbnail movie, boolean loadAll) {
		this(movie.getOriginJSON());
		if (loadAll)
			try {
				getFullInformation();
			} catch (ResponseException e) {
				Log.print(e);
			}
	}
	
	//region Getters/Setters
	
	/**
	 * Gets the movie backdrops.
	 * 
	 * @return The movie backdrops
	 */
	public Set<Backdrop> getBackdrops() {
		return backdrops;
	}

	/**
	 * Sets the movie backdrops.
	 * 
	 * @param backdrops The movie backdrops
	 */
	public void setBackdrops(Set<Backdrop> backdrops) {
		this.backdrops.clear();
		this.backdrops.addAll(backdrops);
	}

	/**
	 * Gets the movie posters.
	 * 
	 * @return The movie posters
	 */
	public Set<Poster> getPosters() {
		return posters;
	}

	/**
	 * Sets the movie posters.
	 * 
	 * @param posters The movie posers
	 */
	public void setPosters(Set<Poster> posters) {
		this.posters.clear();
		this.posters.addAll(posters);
	}

	/**
	 * Gets the movie keywords.
	 * 
	 * @return The movie keywords
	 */
	public Set<Keyword> getKeywords() {
		return keywords;
	}

	/**
	 * Sets the movie keywords.
	 * 
	 * @param keywords The movie keywords
	 */
	public void setKeywords(Set<Keyword> keywords) {
		this.keywords.clear();
		this.keywords.addAll(keywords);
	}

	/**
	 * Gets the movie translations.
	 * 
	 * @return The movie translations
	 */
	public Set<Language> getTranslations() {
		return translations;
	}

	/**
	 * Sets the movie translations.
	 * 
	 * @param translations The movie translations
	 */
	public void setTranslations(Set<Language> translations) {
		this.translations.clear();
		this.translations.addAll(translations);
	}

	/**
	 * Gets the movie trailers.
	 * 
	 * @return The movie trailers
	 */
	public Set<Trailer> getTrailers() {
		return trailers;
	}

	/**
	 * Sets the movie trailers.
	 * 
	 * @param trailers The movie trailers
	 */
	public void setTrailers(Set<Trailer> trailers) {
		this.trailers.clear();
		this.trailers.addAll(trailers);
	}

	/**
	 * Gets the movie cast.
	 * 
	 * @return The movie cast
	 */
	public Set<MovieCast> getCast() {
		return cast;
	}

	/**
	 * Sets the movie cast.
	 * 
	 * @param cast The new movie cast
	 */
	public void setCast(Set<MovieCast> cast) {
		this.cast.clear();
		this.cast.addAll(cast);
	}

	/**
	 * Gets the movie crew.
	 * 
	 * @return The movie crew
	 */
	public Set<MovieCrew> getCrew() {
		return crew;
	}

	/**
	 * Sets the movie crew.
	 * 
	 * @param crew The new movie crew
	 */
	public void setCrew(Set<MovieCrew> crew) {
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
