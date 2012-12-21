package mk.tmdb.entity;

import mk.tmdb.core.Constants;
import net.sf.json.JSONObject;

public class Crew extends Role {

	private String department;
	
	public Crew(JSONObject json) {
		super(json);
		parseJSON(json);
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	@Override
	protected boolean parseJSON(JSONObject json) {
		
		setDepartment(json.getString(Constants.DEPARTMENT));
		
		return true;
	}
	
}
