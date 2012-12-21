package mk.tmdb.entity.person;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import mk.tmdb.entity.role.Cast;
import net.sf.json.JSONObject;

public class MovieCast extends PersonThumbnail {

	private int movieID;
	private Set<Cast> cast = Collections.synchronizedSet(new LinkedHashSet<Cast>());
	
	public MovieCast(JSONObject json, int movieID) {
		super(json);
		this.movieID = movieID;
	}
	
	public MovieCast(MovieCast mcast, int movieID) {
		this(mcast.getOriginJSON(), movieID);
	}

	public Set<Cast> getCast() {
		return cast;
	}

	public void setCast(Set<Cast> cast) {
		this.cast.clear();
		this.cast.addAll(cast);
	}
	
	public void addCastRole(Cast role) {
		cast.add(role);
	}

	public int getMovieID() {
		return movieID;
	}
	
	@Override
	protected boolean parseJSON(JSONObject json) {
		
		addCastRole(new Cast(json));
		
		return true;
	}

}
