package mk.tmdb.entity;

import net.sf.json.JSONObject;

public interface IEntity {
	
	boolean parseJSON(JSONObject json);

	String getOriginJSON();
	
}
