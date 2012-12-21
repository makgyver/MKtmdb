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
	
	@Override
	protected boolean parseJSON(JSONObject json) {
		
		addCrewRole(new Crew(json));
		
		return true;
	}
}
