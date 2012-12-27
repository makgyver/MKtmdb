package mk.tmdb.entity.person;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import mk.tmdb.core.Constants;
import mk.tmdb.utils.Log;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Person extends PersonThumbnail {

	//region Fields
	
	
	private Set<String> aka = Collections.synchronizedSet(new LinkedHashSet<String>()); 
	private String bio = null;
	private Date birthday = null;
	private Date deathday = null;
	private URL homepage = null;
	private String placeOfBirth = null;
	
	//endregion
	
	public Person(JSONObject json) {
		super(json);
		parseJSON(json);
	}
	
	public Person(PersonThumbnail person) {
		this(person.getOriginJSON());
	}
	
	//region Getters/Setters
	
	public Set<String> getAka() {
		return aka;
	}
	
	public void setAka(Set<String> aka) {
		this.aka = aka;
	}
	
	public String getBiography() {
		return bio;
	}
	
	public void setBiography(String bio) {
		this.bio = bio;
	}
	
	public boolean isBiographySet() {
		return bio != null;
	}
	
	public Date getBirthday() {
		return birthday;
	}
	
	public void setBirthday(String birthday) {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			this.birthday = (Date)formatter.parse(birthday);
		} catch (ParseException e) {
			Log.print(e);
		}
	}
	
	public boolean isBirthdaySet() {
		return birthday != null;
	}
	
	public Date getDeathday() {
		return deathday;
	}
	
	public void setDeathday(String deathday) {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			this.deathday = (Date)formatter.parse(deathday);
		} catch (ParseException e) {
			Log.print(e);
		}
	}
	
	public boolean isDeathdaySet() {
		return deathday != null;
	}
	
	public URL getHomepage() {
		return homepage;
	}
	
	public void setHomepage(URL homepage) {
		this.homepage = homepage;
	}
	
	public String getPlaceOfBirth() {
		return placeOfBirth;
	}
	
	public void setPlaceOfBirth(String placeOfBirth) {
		this.placeOfBirth = placeOfBirth;
	}

	public boolean isPlaceOfBirthSet() {
		return placeOfBirth != null;
	}
	
	//endregion
	
	/**
	 * Parses the origin JSON object.
	 */
	private boolean parseJSON(JSONObject json) {
		
		if (json.has(Constants.BIO)) setBiography(json.getString(Constants.BIO));
		if (json.has(Constants.BIRTHDAY)) setBirthday(json.getString(Constants.BIRTHDAY));
		if (json.has(Constants.DEATHDAY)) setDeathday(json.getString(Constants.DEATHDAY));
		if (json.has(Constants.PLACE_OF_BIRTH)) setPlaceOfBirth(json.getString(Constants.PLACE_OF_BIRTH));
		
		
		if (json.has(Constants.AKA)) {
			JSONArray akas = json.getJSONArray(Constants.AKA); 
			for (Object obj : akas) {
			    aka.add((String) obj);
			}
		}
		
		if (json.has(Constants.HOMEPAGE)) { 
			try {
				setHomepage(new URL(json.getString(Constants.HOMEPAGE)));
			} catch (MalformedURLException e) {
				Log.print(e);
			}
		}
		
		return true;
	}
}
