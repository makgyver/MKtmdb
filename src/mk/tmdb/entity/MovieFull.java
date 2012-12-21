package mk.tmdb.entity;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import mk.tmdb.core.Constants;
import mk.tmdb.core.TMDBAPI;
import mk.tmdb.utils.ResponseObject;
import net.sf.json.JSONArray;
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
		retrieveFullInfo();
	}
	
	public MovieFull(MovieThumbnail movie) {
		this(movie.getOriginJSON());
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

	private void retrieveFullInfo() {
		
		ResponseObject response = TMDBAPI.getMovieImages(id);
		
		if (!response.hasError()) {
			
			JSONObject images = response.getData();
			
			JSONArray allPosters = images.getJSONArray(Constants.POSTERS);
			posters.clear();
			for (Object obj : allPosters) {
			    posters.add(new Poster((JSONObject) obj));
			}
			
			JSONArray allBackdrops = images.getJSONArray(Constants.BACKDROPS);
			backdrops.clear();
			for (Object obj : allBackdrops) {
			    backdrops.add(new Backdrop((JSONObject) obj));
			}
		}
		
		response = TMDBAPI.getMovieKeywords(id);
		
		if (!response.hasError()) { 
			
			JSONObject words = response.getData();
			
			JSONArray allkeys = words.getJSONArray(Constants.KEYWORDS);
			keywords.clear();
			for (Object obj : allkeys) {
			    keywords.add(new Keyword((JSONObject) obj));
			}
		}
		
		response = TMDBAPI.getMovieTranslations(id);
		
		if (!response.hasError()) {
			
			JSONObject trans = response.getData();
			
			JSONArray allTrans = trans.getJSONArray(Constants.TRANSLATIONS);
			translations.clear();
			for (Object obj : allTrans) {
			    translations.add(new Language((JSONObject) obj));
			}
		}
		
		response = TMDBAPI.getMovieTrailers(id);
		
		if (!response.hasError()) {
		
			JSONObject videos = response.getData();
			
			JSONArray utube = videos.getJSONArray(Constants.YOUTUBE);
			trailers.clear();
			for (Object obj : utube) {
			    trailers.add(new YoutubeTrailer((JSONObject) obj));
			}
			
			JSONArray quick = videos.getJSONArray(Constants.QUICKTIME);
			
			for (Object obj : quick) {
				String name = ((JSONObject) obj).getString(Constants.NAME);
				JSONArray quicks = ((JSONObject) obj).getJSONArray(Constants.SOURCES);
				
				for (Object jobj : quicks) {
					trailers.add(new QuicktimeTrailer((JSONObject) jobj, name));
				}
			}	
		}
		
		response = TMDBAPI.getCastInformation(id);
		
		if (!response.hasError()) {
		
			JSONObject castCrew = response.getData();
			
			JSONArray castArray = castCrew.getJSONArray(Constants.CAST);
			cast.clear();
			for (Object obj : castArray) {
				cast.add(new MovieCast((JSONObject) obj, id));
			}
			
			JSONArray crewArray = castCrew.getJSONArray(Constants.CREW);
			crew.clear();
			for (Object obj : crewArray) {
				crew.add(new MovieCrew((JSONObject) obj, id));
			}
		}
	}
	
}
