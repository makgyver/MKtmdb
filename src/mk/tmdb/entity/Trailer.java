package mk.tmdb.entity;

import java.net.URL;

import net.sf.json.JSONObject;

public abstract class Trailer implements IEntity {
	
	protected static final String NAME = "name";
	protected static final String SIZE = "size";
	protected static final String LINK = "source";
	
	protected String name;
	protected String size;
	protected URL link;
	protected String source;
	
	public Trailer(String source) {
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
}
