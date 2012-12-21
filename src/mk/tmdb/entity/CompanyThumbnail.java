package mk.tmdb.entity;

import mk.tmdb.core.Constants;
import mk.tmdb.utils.Log;
import net.sf.json.JSONObject;

public class CompanyThumbnail extends Entity {

	private Integer id;
	private String name;
	
	public CompanyThumbnail(JSONObject json) {
		super(json);
		parseJSON(json);
	}
	
	public CompanyThumbnail(CompanyThumbnail company) {
		this(company.getOriginJSON());
	}
	
	//region Getters/Setters
	
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

	//endregion
	
	@Override
	protected boolean parseJSON(JSONObject json) {
		try {
			
			setId(json.getInt(Constants.ID));
			setName(json.getString(Constants.NAME));
			
		} catch (Exception e) {
			Log.print(e);
			return false;
		}
		
		return true;	
	}
	
}
