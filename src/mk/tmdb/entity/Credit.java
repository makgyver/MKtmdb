package mk.tmdb.entity;

import mk.tmdb.entity.movie.MovieThumbnail;
import mk.tmdb.entity.role.Cast;
import mk.tmdb.entity.role.Crew;
import mk.tmdb.entity.role.Role;
import net.sf.json.JSONObject;

public class Credit extends Entity {

	public enum Type {
		CAST,
		CREW;
	}
	
	private MovieThumbnail movie;
	private Role role;
	private Type type;
	
	public Credit(JSONObject json, Type type) {
		super(json);
		parseJSON(json);
		this.type = type;
	}
	
	public Credit(Credit credit) {
		this(credit.getOriginJSON(), credit.getType());
	}
	
	//region Getters/Setters
	
	public MovieThumbnail getMovie() {
		return movie;
	}

	public void setMovie(MovieThumbnail movie) {
		this.movie = movie;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Type getType() {
		return type;
	}
	
	//endregion

	@Override
	protected boolean parseJSON(JSONObject json) {
		
		if (type == Type.CAST) setRole(new Cast(json));
		else setRole(new Crew(json));
		
		return true;
	}
	
}
