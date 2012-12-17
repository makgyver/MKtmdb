package mk.tmdb.entity;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import mk.tmdb.core.Constants;
import net.sf.json.JSONObject;

public class Actor extends Person {

	public class MovieRole {
		
		private int movieID;
		private int castID;
		private int order;
		private String character;
		
		public MovieRole(int movieID) {
			this.movieID = movieID;
		}
		
		public MovieRole(int movieID, int castID, int order, String character) {
			this.movieID = movieID;
			this.castID = castID;
			this.order = order;
			this.character = character;
		}
		
		public int getMovieID() {
			return movieID;
		}
		
		public void setMovieID(int movieID) {
			this.movieID = movieID;
		}
		
		public int getCastID() {
			return castID;
		}
		
		public void setCastID(int castID) {
			this.castID = castID;
		}
		
		public int getOrder() {
			return order;
		}
		
		public void setOrder(int order) {
			this.order = order;
		}
		
		public String getCharacter() {
			return character;
		}
		
		public void setCharacter(String character) {
			this.character = character;
		}
	}
	
	private Set<MovieRole> roles = Collections.synchronizedSet(new LinkedHashSet<MovieRole>());

	public Actor(JSONObject json) {
		super(json);
	}
	
	public Actor(JSONObject json, int movieID) {
		super(json);
		parsePartialJSON(json, movieID);
	}
	
	public Set<MovieRole> getRoles() {
		return roles;
	}
	
	public void addRole(MovieRole role) {
		roles.add(role);
	}

	private void parsePartialJSON(JSONObject json, int movieID) {
		MovieRole role = new MovieRole(movieID);
		
		if (json.has(Constants.CAST_ID)) role.setCastID(json.getInt(Constants.CAST_ID));
		if (json.has(Constants.ORDER)) role.setOrder(json.getInt(Constants.ORDER));
		if (json.has(Constants.CHARACTER)) role.setCharacter(json.getString(Constants.CHARACTER));
		
		addRole(role);
	}

}
