package mk.tmdb.entity.person;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import mk.tmdb.entity.Credit;
import mk.tmdb.entity.image.Profile;
import mk.tmdb.exception.ResponseException;
import mk.tmdb.utils.Log;
import net.sf.json.JSONObject;

public class PersonFull extends Person {

	private Set<Profile> images = Collections.synchronizedSet(new LinkedHashSet<Profile>());
	private Set<Credit> credits = Collections.synchronizedSet(new LinkedHashSet<Credit>());
	
	public PersonFull(JSONObject json) {
		super(json);
	}
	
	public PersonFull(JSONObject json, boolean loadAll) {
		super(json);
		if (loadAll)
			try {
				retrieveAllInformation();
			} catch (ResponseException e) {
				Log.print(e);
			}
	}
	
	public PersonFull(PersonThumbnail person) {
		this(person.getOriginJSON());
	}
	
	public PersonFull(PersonThumbnail person, boolean loadAll) {
		this(person.getOriginJSON());
		if(loadAll)
			try {
				retrieveAllInformation();
			} catch (ResponseException e) {
				Log.print(e);
			}
	}
	
	public Set<Profile> getImages() {
		return images;
	}

	public void setImages(Set<Profile> images) {
		this.images = images;
	}

	public Set<Credit> getCredits() {
		return credits;
	}

	public void setCredits(Set<Credit> credits) {
		this.credits = credits;
	}

	public void retrieveAllInformation() throws ResponseException {
		setCredits(getCredits(id));
		setImages(getImages(id));
	}

}
