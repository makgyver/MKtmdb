package mk.tmdb.entity.person;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import mk.tmdb.entity.Credit;
import mk.tmdb.entity.image.Profile;
import mk.tmdb.exception.ResponseException;
import mk.tmdb.utils.Log;
import net.sf.json.JSONObject;

/**
 * Class that represents a full featured person.</br>
 * To get a less detailed class see the following classes:
 * <ul>
 * <li>{@link PersonThumbnail}</li>
 * <li>{@link Person}</li>
 * </ul>
 * 
 * @author Mirko Polato
 *
 */
public class PersonFull extends Person {

	/**
	 * The profile images of the person.
	 */
	private Set<Profile> images = Collections.synchronizedSet(new LinkedHashSet<Profile>());
	
	/**
	 * The person credits list.
	 */
	private Set<Credit> credits = Collections.synchronizedSet(new LinkedHashSet<Credit>());
	
	/**
	 * Creates a new instance of PersonFull based on the origin JSON object.
	 * 
	 * @param json The origin JSON object
	 */
	public PersonFull(JSONObject json) {
		super(json);
	}
	
	/**
	 * Creates a new instance of PersonFull based on the origin JSON object and
	 * retrieves all the information.
	 * 
	 * @param json The origin JSON object
	 * @param loadAll Whether the remaining information have to be loaded or not
	 */
	public PersonFull(JSONObject json, boolean loadAll) {
		super(json);
		if (loadAll)
			try {
				getFullInformation();
			} catch (ResponseException e) {
				Log.print(e);
			}
	}
	
	/**
	 * Copy constructor.
	 * 
	 * @param person The person to copy
	 */
	public PersonFull(PersonThumbnail person) {
		this(person.getOriginJSON());
	}
	
	/**
	 * Copy constructor.
	 * 
	 * @param person The person to copy
	 * @param loadAll Whether the remaining information have to be loaded or not
	 */
	public PersonFull(PersonThumbnail person, boolean loadAll) {
		this(person.getOriginJSON());
		if(loadAll)
			try {
				getFullInformation();
			} catch (ResponseException e) {
				Log.print(e);
			}
	}
	
	/**
	 * Gets the person images list.
	 * 
	 * @return The person images list
	 */
	public Set<Profile> getImages() {
		return images;
	}

	/**
	 * Sets the person images list.
	 * 
	 * @param images The new person images list
	 */
	public void setImages(Set<Profile> images) {
		this.images = images;
	}

	/**
	 * Gets the credits list of the person.
	 * 
	 * @return The credits list of the person
	 */
	public Set<Credit> getCredits() {
		return credits;
	}

	/**
	 * Sets the credits list of the person.
	 * 
	 * @param credits The new credits list
	 */
	public void setCredits(Set<Credit> credits) {
		this.credits = credits;
	}

	/**
	 * Gets all the information.
	 * 
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
	private void getFullInformation() throws ResponseException {
		setCredits(getCredits(id));
		setImages(getImages(id));
	}

}
