package mk.tmdb.entity;

import java.net.URL;

import net.sf.json.JSONObject;

public abstract class Trailer implements IEntity {
	
	protected String originJson = "";
	protected String name;
	protected String size;
	protected URL link;
	protected String source;
	
	public Trailer(JSONObject json, String source) {
		this.originJson = json.toString();
		this.source = source;
	}
	
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

	@Override
	public boolean parseJSON(JSONObject json) {
		return false;
	}
	
	@Override
	public String getOriginJSON() {
		return originJson;
	}
}
