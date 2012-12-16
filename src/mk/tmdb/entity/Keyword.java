package mk.tmdb.entity;

import mk.tmdb.utils.Log;
import net.sf.json.JSONObject;

public class Keyword implements IEntity {

	private static final String ID = "id";
	private static final String NAME = "name";
	
	private int id;
	private String name;
	
	public Keyword(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public Keyword(JSONObject json) {
		parseJSON(json);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public boolean parseJSON(JSONObject json) {
		try {
			
			setId(json.getInt(ID));
			setName(json.getString(NAME));
			
		} catch (Exception e) {
			Log.print(e);
			return false;
		}
		
		return true;
	}

}
