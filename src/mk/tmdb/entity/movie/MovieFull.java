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

public class MovieFull extends Movie {

	private Set<Backdrop> backdrops = Collections.synchronizedSet(new LinkedHashSet<Backdrop>());
	private Set<Poster> posters = Collections.synchronizedSet(new LinkedHashSet<Poster>());
	private Set<Keyword> keywords = Collections.synchronizedSet(new LinkedHashSet<Keyword>());
	private Set<Language> translations = Collections.synchronizedSet(new LinkedHashSet<Language>());
	private Set<Trailer> trailers = Collections.synchronizedSet(new LinkedHashSet<Trailer>());
	private Set<MovieCast> cast = Collections.synchronizedSet(new LinkedHashSet<MovieCast>());
	private Set<MovieCrew> crew = Collections.synchronizedSet(new LinkedHashSet<MovieCrew>());
	
	public MovieFull(JSONObject json) {
		super(json);
	}
	
	public MovieFull(JSONObject json, boolean loadAll) {
		super(json);
		if (loadAll)
			try {
				getFullInformation();
			} catch (ResponseException e) {
				Log.print(e);
			}
	}
	
	public MovieFull(MovieThumbnail movie) {
		this(movie.getOriginJSON());
	}

	public MovieFull(MovieThumbnail movie, boolean loadAll) {
		this(movie.getOriginJSON());
		if (loadAll)
			try {
				getFullInformation();
			} catch (ResponseException e) {
				Log.print(e);
			}
	}
	
	public Set<Backdrop> getBackdrops() {
		return backdrops;
	}

	public void setBackdrops(Set<Backdrop> backdrops) {
		this.backdrops.clear();
		this.backdrops.addAll(backdrops);
	}

	public Set<Poster> getPosters() {
		return posters;
	}

	public void setPosters(Set<Poster> posters) {
		this.posters.clear();
		this.posters.addAll(posters);
	}

	public Set<Keyword> getKeywords() {
		return keywords;
	}

	public void setKeywords(Set<Keyword> keywords) {
		this.keywords.clear();
		this.keywords.addAll(keywords);
	}

	public Set<Language> getTranslations() {
		return translations;
	}

	public void setTranslations(Set<Language> translations) {
		this.translations.clear();
		this.translations.addAll(translations);
	}

	public Set<Trailer> getTrailers() {
		return trailers;
	}

	public void setTrailers(Set<Trailer> trailers) {
		this.trailers.clear();
		this.trailers.addAll(trailers);
	}

	public Set<MovieCast> getCast() {
		return cast;
	}

	public void setCast(Set<MovieCast> cast) {
		this.cast.clear();
		this.cast.addAll(cast);
	}

	public Set<MovieCrew> getCrew() {
		return crew;
	}

	public void setCrew(Set<MovieCrew> crew) {
		this.crew.clear();
		this.crew.addAll(crew);
	}
	
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
