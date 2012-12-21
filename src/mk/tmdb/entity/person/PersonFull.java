package mk.tmdb.entity.person;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import mk.tmdb.core.Constants;
import mk.tmdb.core.TMDbAPI;
import mk.tmdb.entity.Credit;
import mk.tmdb.entity.Credit.Type;
import mk.tmdb.entity.image.Image;
import mk.tmdb.entity.image.Profile;
import mk.tmdb.utils.ResponseObject;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class PersonFull extends Person {

	private Set<Image> images = Collections.synchronizedSet(new LinkedHashSet<Image>());
	private Set<Credit> credits = Collections.synchronizedSet(new LinkedHashSet<Credit>());
	
	public PersonFull(JSONObject json) {
		super(json);
	}
	
	public PersonFull(JSONObject json, boolean loadAll) {
		super(json);
		if (loadAll) retrieveAllInformation();
	}
	
	public PersonFull(PersonThumbnail person) {
		this(person.getOriginJSON());
	}
	
	public PersonFull(PersonThumbnail person, boolean loadAll) {
		this(person.getOriginJSON());
		if(loadAll) retrieveAllInformation();
	}
	
	public Set<Image> getImages() {
		return images;
	}

	public void setImages(Set<Image> images) {
		this.images = images;
	}

	public Set<Credit> getCredits() {
		return credits;
	}

	public void setCredits(Set<Credit> credits) {
		this.credits = credits;
	}

	public void retrieveCredits() {
		ResponseObject response = TMDbAPI.getPersonCredits(id);
		
		if (!response.hasError()) {
		
			JSONObject castCrew = response.getData();
			
			JSONArray castArray = castCrew.getJSONArray(Constants.CAST);
			credits.clear();
			
			for (Object obj : castArray) {
				credits.add(new Credit((JSONObject) obj, Type.CAST));
			}
			
			JSONArray crewArray = castCrew.getJSONArray(Constants.CREW);
			
			for (Object obj : crewArray) {
				credits.add(new Credit((JSONObject) obj, Type.CREW));
			}
		}
	}
	
	public void retrieveImages() {
		ResponseObject response = TMDbAPI.getPersonImages(id);
		
		if (!response.hasError()) {
			
			JSONArray imgs = response.getData().getJSONArray(Constants.PROFILES);
			images.clear();
			for (Object obj : imgs) {
			    images.add(new Profile((JSONObject) obj));
			}
		}
	}
	
	public void retrieveAllInformation() {
		retrieveCredits();
		retrieveImages();
	}

}
