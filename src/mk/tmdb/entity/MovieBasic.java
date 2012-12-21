package mk.tmdb.entity;

import mk.tmdb.core.Constants;
import net.sf.json.JSONObject;

public class MovieBasic extends MovieReduced {

	protected String backdropPath;
	protected double popularity;
	
	public MovieBasic(JSONObject json) {
		super(json);
		parseJSON(json);
	}
	
	public String getBackdropPath() {
		return backdropPath;
	}

	public void setBackdropPath(String backdropPath) {
		this.backdropPath = backdropPath;
	}

	public double getPopularity() {
		return popularity;
	}

	public void setPopularity(double popularity) {
		this.popularity = popularity;
	}

	@Override
	protected boolean parseJSON(JSONObject json) {
		
		setBackdropPath(json.getString(Constants.BACKDROP_PATH));
		setPopularity(json.getDouble(Constants.POPULARITY));

		return true;
	}

}
