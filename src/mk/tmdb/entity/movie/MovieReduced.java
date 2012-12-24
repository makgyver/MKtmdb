package mk.tmdb.entity.movie;


import mk.tmdb.core.Constants;
import net.sf.json.JSONObject;

public class MovieReduced extends MovieThumbnail {

	//region Fields
	
	protected Double voteAverage = null;
	protected Integer voteCount = null;
	protected String backdropPath = null;
	protected Double popularity = null;
	
	//endregion
	
	public MovieReduced(JSONObject json) {
		super(json);
		parseJSON(json);
	}
	
	public MovieReduced(MovieThumbnail movie) {
		this(movie.getOriginJSON());
	}
	
	//region Getters/Setters
	
	public double getVoteAverage() {
		return voteAverage;
	}
	
	public String getBackdropPath() {
		return backdropPath;
	}

	public void setBackdropPath(String backdropPath) {
		this.backdropPath = backdropPath;
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
	
	public boolean isVoteAverageSet() {
		return voteAverage != null;
	}
	
	public boolean isBackropPathSet() {
		return backdropPath != null;
	}
	
	public boolean isVoteCountSet() {
		return voteCount != null;
	}
	
	public double getPopularity() {
		return popularity;
	}

	public void setPopularity(double popularity) {
		this.popularity = popularity;
	}

	public boolean isPopularitySet() {
		return popularity != null;
	}
	
	//endregion

	@Override
	protected boolean parseJSON(JSONObject json) {
		
		if (json.has(Constants.AVERAGE)) setVoteAverage(json.getDouble(Constants.AVERAGE));
		if (json.has(Constants.COUNT)) setVoteCount(json.getInt(Constants.COUNT));
		if (json.has(Constants.BACKDROP_PATH)) setBackdropPath(json.getString(Constants.BACKDROP_PATH));
		if (json.has(Constants.POPULARITY)) setPopularity(json.getDouble(Constants.POPULARITY));
		
		return true;
	}
	
}
