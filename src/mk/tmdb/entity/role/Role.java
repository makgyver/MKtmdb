package mk.tmdb.entity.role;

import mk.tmdb.core.Constants;
import mk.tmdb.entity.Entity;
import net.sf.json.JSONObject;

/**
 * Abstract class that represents a role (cast or crew) in a movie.
 * 
 * @author Mirko Polato
 *
 */
public abstract class Role extends Entity {

	/**
	 * The specific job.
	 */
	protected String job;
	
	/**
	 * Initializes the origin JSON object.
	 * @param json The origin JSON object
	 */
	public Role(JSONObject json) {
		super(json);
		parseJSON(json);
	}
	
	/**
	 * Gets the job.
	 * 
	 * @return The job.
	 */
	public String getJob() {
		return job;
	}

	/**
	 * Sets the job.
	 * 
	 * @param job The job
	 */
	public void setJob(String job) {
		this.job = job;
	}

	/**
	 * Parses the origin JSON object.
	 */
	@Override
	protected boolean parseJSON(JSONObject json) {
		
		if (json.has(Constants.JOB)) setJob(json.getString(Constants.JOB));
		else setJob(Constants.ACTOR);
		
		return true;
	}
	
}
