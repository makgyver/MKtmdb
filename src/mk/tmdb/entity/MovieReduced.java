package mk.tmdb.entity;

import mk.tmdb.core.Constants;
import net.sf.json.JSONObject;

public class MovieReduced extends MovieThumbnail {

	protected double voteAverage;
	protected int voteCount;
	
	public MovieReduced(JSONObject json) {
		super(json);
		parseJSON(json);
	}
	
	public double getVoteAverage() {
		return voteAverage;
	}

	public void setVoteAverage(double voteAverage) {
		this.voteAverage = voteAverage;
	}

	public int getVoteCount() {
		return voteCount;
	}

	public void setVoteCount(int voteCount) {
		this.voteCount = voteCount;
	}

	@Override
	protected boolean parseJSON(JSONObject json) {
		
		setVoteAverage(json.getDouble(Constants.AVERAGE));
		setVoteCount(json.getInt(Constants.COUNT));
		
		return true;
	}
}
