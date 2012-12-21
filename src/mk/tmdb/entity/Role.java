package mk.tmdb.entity;

import mk.tmdb.core.Constants;
import net.sf.json.JSONObject;

public abstract class Role extends Entity {

	protected String job;
	
	public Role(JSONObject json) {
		super(json);
		parseJSON(json);
	}
	
	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	@Override
	protected boolean parseJSON(JSONObject json) {
		
		if (json.has(Constants.JOB)) setJob(json.getString(Constants.JOB));
		else setJob(Constants.ACTOR);
		
		return true;
	}
	
}
