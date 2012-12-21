package mk.tmdb.entity;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import mk.tmdb.core.Constants;
import mk.tmdb.core.TMDBAPI;
import mk.tmdb.exception.ResponseException;
import mk.tmdb.utils.ResponseObject;
import net.sf.json.JSONObject;

public class PersonThumbnail extends Entity {

	protected int id;
	protected String name;
	protected String profilePath;
	protected Set<Role> roles = Collections.synchronizedSet(new LinkedHashSet<Role>());
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getProfilePath() {
		return profilePath;
	}
	
	public void setProfilePath(String profilePath) {
		this.profilePath = profilePath;
	}
	
	public Set<Role> getRoles() {
		return roles;
	}
	
	public void setRoles(Set<Role> roles) {
		this.roles.clear();
		this.roles.addAll(roles);
	}
	
	public void addRole(Role role) {
		roles.add(role);
	}
	
	public PersonThumbnail(JSONObject json) {
		super(json);
		parseJSON(json);
	}
	
	@Override
	protected boolean parseJSON(JSONObject json) {
		
		setName(json.getString(Constants.NAME));
		setId(json.getInt(Constants.ID));
		setProfilePath(json.getString(Constants.PROFILE_PATH));
		
		return true;
	}
	
	public static Person getLatest() throws ResponseException {
		
		ResponseObject response = TMDBAPI.getLatestPerson();
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			return new Person(response.getData());
		}
	}
	
}
