package mk.tmdb.entity.person;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import mk.tmdb.core.Constants;
import mk.tmdb.core.TMDbAPI;
import mk.tmdb.entity.Credit;
import mk.tmdb.entity.Entity;
import mk.tmdb.entity.Credit.Type;
import mk.tmdb.entity.image.Profile;
import mk.tmdb.entity.role.Role;
import mk.tmdb.exception.ResponseException;
import mk.tmdb.utils.ResponseObject;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class PersonThumbnail extends Entity {

	//region Fields
	
	protected Integer id;
	protected String name;
	protected String profilePath;
	protected Set<Role> roles = Collections.synchronizedSet(new LinkedHashSet<Role>());
	
	//endregion
	
	public PersonThumbnail(JSONObject json) {
		super(json);
		parseJSON(json);
	}
	
	public PersonThumbnail(PersonThumbnail person) {
		this(person.getOriginJSON());
	}
	
	//region Getters/Setters
	
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
	
	//endregion
	
	@Override
	protected boolean parseJSON(JSONObject json) {
		
		setName(json.getString(Constants.NAME));
		setId(json.getInt(Constants.ID));
		setProfilePath(json.getString(Constants.PROFILE_PATH));
		
		return true;
	}
	
	//region Static methods
	
	public static Person getLatest() throws ResponseException {
		
		ResponseObject response = TMDbAPI.getLatestPerson();
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			return new Person(response.getData());
		}
	}
	
	public static Person getInformation(int personID) throws ResponseException {
		
		ResponseObject response = TMDbAPI.getPersonInformation(personID);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			return new Person(response.getData());
		}
	}
	
	//endregion
	
	public static Set<Credit> getCredits(int persondID) throws ResponseException {
		
		ResponseObject response = TMDbAPI.getPersonCredits(persondID);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
		
			Set<Credit> credits = new LinkedHashSet<Credit>();
			
			JSONArray castArray = response.getData().getJSONArray(Constants.CAST);
			
			for (Object obj : castArray) {
				credits.add(new Credit((JSONObject) obj, Type.CAST));
			}
			
			JSONArray crewArray = response.getData().getJSONArray(Constants.CREW);
			
			for (Object obj : crewArray) {
				credits.add(new Credit((JSONObject) obj, Type.CREW));
			}
			
			return credits;
		}
	}
	
	public static Set<Profile> getImages(int personID) throws ResponseException {
		ResponseObject response = TMDbAPI.getPersonImages(personID);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			
			JSONArray imgs = response.getData().getJSONArray(Constants.PROFILES);
			Set<Profile> images = new LinkedHashSet<Profile>();
			for (Object obj : imgs) {
			    images.add(new Profile((JSONObject) obj));
			}
			
			return images;
		}
	}
	
}
