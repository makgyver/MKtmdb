package mk.tmdb.entity;

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

	@Override
	protected boolean parseJSON(JSONObject json) {
		
		if (type == Type.CAST) setRole(new Cast(json));
		else setRole(new Crew(json));
		
		return true;
	}
	
}
