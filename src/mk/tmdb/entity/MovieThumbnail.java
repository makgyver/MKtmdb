package mk.tmdb.entity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import mk.tmdb.core.Constants;
import mk.tmdb.utils.Log;
import net.sf.json.JSONObject;

public class MovieThumbnail extends Entity {

	protected boolean adult;
	protected int id;
	protected String originalTitle;
	protected String title;
	protected String posterPath;
	protected Date releaseDate;
	
	public MovieThumbnail(JSONObject json) {
		super(json);
		parseJSON(json);
	}
	
	public boolean isAdult() {
		return adult;
	}

	public void setAdult(boolean adult) {
		this.adult = adult;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOriginalTitle() {
		return originalTitle;
	}

	public void setOriginalTitle(String originalTitle) {
		this.originalTitle = originalTitle;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPosterPath() {
		return posterPath;
	}

	public void setPosterPath(String posterPath) {
		this.posterPath = posterPath;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			this.releaseDate = (Date)formatter.parse(releaseDate);
		} catch (ParseException e) {
			Log.print(e);
		}
	}
	
	@Override
	protected boolean parseJSON(JSONObject json) {
		
		setAdult(json.getBoolean(Constants.ADULT));
		setId(json.getInt(Constants.ID));
		setOriginalTitle(json.getString(Constants.ORIGINAL_TITLE));
		setPosterPath(json.getString(Constants.POSTER_PATH));
		setReleaseDate(json.getString(Constants.RELEASE_DATE));
		setTitle(json.getString(Constants.TITLE));
		
		return true;
	}
	
}
