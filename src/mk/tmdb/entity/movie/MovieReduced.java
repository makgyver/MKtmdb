package mk.tmdb.entity.movie;


import mk.tmdb.core.Constants;
import net.sf.json.JSONObject;

/**
 * Class that represents a Movie with limited features.
 * MovieReduced class has more features than MovieThumbnail class.
 * To get a more detailed class see the following classes:
 * <ul>
 * <li>{@link Movie}</li>
 * <li>{@link MovieFull}</li>
 * </ul>
 * @author Mirko Polato
 *
 */
public class MovieReduced extends MovieThumbnail {

	//region Fields
	
	/**
	 * The movie vote average
	 */
	protected Double voteAverage = null;
	
	/**
	 * The movie vote count
	 */
	protected Integer voteCount = null;
	
	/**
	 * The movie backdrop path
	 */
	protected String backdropPath = null;
	
	/**
	 * The movie popularity expressed in percentage.
	 */
	protected Double popularity = null;
	
	//endregion
	
	/**
	 * Creates a new instance of MovieReduced based on the origin JSON object.
	 *  
	 * @param json The origin JSON object
	 */
	public MovieReduced(JSONObject json) {
		super(json);
		parseJSON(json);
	}
	
	/**
	 * Copy constructor.
	 * 
	 * @param movie The movie to copy
	 */
	public MovieReduced(MovieThumbnail movie) {
		this(movie.getOriginJSON());
	}
	
	//region Getters/Setters
	
	/**
	 * Gets the movie vote average.
	 * 
	 * @return The movie vote average
	 */
	public double getVoteAverage() {
		return voteAverage;
	}
	
	/**
	 * Sets the movie vote average
	 * 
	 * @param voteAverage The new movie vote average
	 */
	public void setVoteAverage(double voteAverage) {
		this.voteAverage = voteAverage;
	}
	
	/**
	 * Gets the movie backdrop path.
	 * 
	 * @return The movie backdrop path
	 */
	public String getBackdropPath() {
		return backdropPath;
	}

	/**
	 * Sets the movie backdrop path.
	 * 
	 * @param backdropPath The new backrop path
	 */
	public void setBackdropPath(String backdropPath) {
		this.backdropPath = backdropPath;
	}

	/**
	 * Gets the movie vote count.
	 * 
	 * @return The movie votes count
	 */
	public int getVoteCount() {
		return voteCount;
	}

	/**
	 * Sets the movie vote count.
	 * 
	 * @param voteCount The new movie votes count
	 */
	public void setVoteCount(int voteCount) {
		this.voteCount = voteCount;
	}
	
	/**
	 * Checks if the vote average is set.
	 * 
	 * @return Whether the vote average is set or not
	 */
	public boolean isVoteAverageSet() {
		return voteAverage != null;
	}
	
	/**
	 * Checks if the backdrop path is set.
	 * 
	 * @return Whether the backdrop path is set or not
	 */
	public boolean isBackropPathSet() {
		return backdropPath != null;
	}
	
	/**
	 * Checks if the vote count is set.
	 * 
	 * @return Whether the vote count is set or not
	 */
	public boolean isVoteCountSet() {
		return voteCount != null;
	}
	
	/**
	 * Gets the movie popularity (in percentage).
	 * 
	 * @return The movie popularity (in percentage).
	 */
	public double getPopularity() {
		return popularity;
	}

	/**
	 * Sets the movie popularity.
	 * 
	 * @param popularity The new movie popularity
	 */
	public void setPopularity(double popularity) {
		this.popularity = Math.max(popularity, 100);
	}

	/**
	 * Checks if the popularity is set.
	 * 
	 * @return Whether the popularity is set or not
	 */
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
