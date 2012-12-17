package mk.tmdb.entity;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import net.sf.json.JSONObject;

import mk.tmdb.core.Constants;

public class CrewMember extends Person {

	public class CrewRole {
		
		private int movieID;
		private String department;
		private String job;
		
		public CrewRole(int movieID) {
			this.movieID = movieID;
		}
		
		public CrewRole(int movieID, String department, String job) {
			this.movieID = movieID;
			this.department = department;
			this.job = job;
		}

		public int getMovieID() {
			return movieID;
		}

		public void setMovieID(int movieID) {
			this.movieID = movieID;
		}

		public String getDepartment() {
			return department;
		}

		public void setDepartment(String department) {
			this.department = department;
		}

		public String getJob() {
			return job;
		}

		public void setJob(String job) {
			this.job = job;
		}
	}
	
	private Set<CrewRole> roles = Collections.synchronizedSet(new LinkedHashSet<CrewRole>());
	
	public CrewMember(JSONObject json) {
		super(json);
	}
	
	public CrewMember(JSONObject json, int movieID) {
		super(json);
		parsePartialJSON(json, movieID);
	}
	
	public Set<CrewRole> getRoles() {
		return roles;
	}
	
	public void addRole(CrewRole role) {
		roles.add(role);
	}

	private void parsePartialJSON(JSONObject json, int movieID) {
		CrewRole role = new CrewRole(movieID);
		
		if (json.has(Constants.DEPARTMENT)) role.setDepartment(json.getString(Constants.DEPARTMENT));
		if (json.has(Constants.JOB)) role.setJob(json.getString(Constants.JOB));
		
		addRole(role);
	}

}
