package mk.tmdb.entity.person;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import mk.tmdb.entity.role.Crew;
import net.sf.json.JSONObject;

public class MovieCrew extends PersonThumbnail {

	private int movieID;
	private Set<Crew> crew = Collections.synchronizedSet(new LinkedHashSet<Crew>());
	
	public MovieCrew(JSONObject json, int movieID) {
		super(json);
		this.movieID = movieID;
		parseJSON(json);
	}
	
	public MovieCrew(MovieCrew mcrew, int movieID) {
		this(mcrew.getOriginJSON(), movieID);
	}

	public Set<Crew> getCrew() {
		return crew;
	}

	public void setCast(Set<Crew> crew) {
		this.crew.clear();
		this.crew.addAll(crew);
	}
	
	public void addCrewRole(Crew role) {
		crew.add(role);
	}

	public int getMovieID() {
		return movieID;
	}
	
	/**
	 * Parses the origin JSON object.
	 */
	private boolean parseJSON(JSONObject json) {
		
		if (json.isNullObject()) return false;
		
		addCrewRole(new Crew(json));
		
		return true;
	}
}
