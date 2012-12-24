package mk.tmdb.entity.trailer;

import java.net.URL;

import mk.tmdb.entity.Entity;
import net.sf.json.JSONObject;

public abstract class Trailer extends Entity {
	
	//region Fields
	
	protected String name;
	protected String size;
	protected URL link;
	protected String source;
	
	//endregion
	
	public Trailer(JSONObject json, String source) {
		super(json);
		this.source = source;
	}
	
	//region Getters/Setters
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSize() {
		return size;
	}
	
	public void setSize(String size) {
		this.size = size;
	}
	
	public URL getLink() {
		return link;
	}
	
	public void setLink(URL link) {
		this.link = link;
	}
	
	public String getSource() {
		return source;
	}
	
	public void setSource(String source) {
		this.source = source;
	}

	//endregion
	
	@Override
	protected boolean parseJSON(JSONObject json) {
		return false;
	}
	
}
