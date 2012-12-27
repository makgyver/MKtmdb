package mk.tmdb.entity.trailer;

import java.net.URL;

import mk.tmdb.entity.Entity;
import net.sf.json.JSONObject;

/**
 * Abstract class that represents a movie trailer.
 * 
 * @author Mirko Polato
 *
 */
public abstract class Trailer extends Entity {
	
	//region Fields
	
	/**
	 * The trailer name.
	 */
	protected String name;
	
	/**
	 * The trailer size.
	 */
	protected String size;
	
	/**
	 * The trailer link.
	 */
	protected URL link;
	
	/**
	 * The trailer source.
	 */
	protected String source;
	
	//endregion
	
	/**
	 * Sets the origin JSON object to the given one.
	 * 
	 * @param json The origin JSON object
	 * @param source The trailer source
	 */
	public Trailer(JSONObject json, String source) {
		super(json);
		this.source = source;
	}
	
	//region Getters/Setters
	
	/**
	 * Gets the trailer name.
	 * 
	 * @return The trailer name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the trailer name.
	 * 
	 * @param name The new trailer name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Gets the trailer size.
	 * 
	 * @return The trailer size
	 */
	public String getSize() {
		return size;
	}
	
	/**
	 * Sets the trailer size.
	 * 
	 * @param size The new trailer size
	 */
	public void setSize(String size) {
		this.size = size;
	}
	
	/**
	 * Gets the trailer link.
	 * 
	 * @return The trailer link
	 */
	public URL getLink() {
		return link;
	}
	
	/**
	 * Sets the trailer link.
	 * 
	 * @param link The new trailer link
	 */
	public void setLink(URL link) {
		this.link = link;
	}
	
	/**
	 * Gets the trailer source.
	 * 
	 * @return The trailer source
	 */
	public String getSource() {
		return source;
	}
	
	/**
	 * Sets the trailer source.
	 * 
	 * @param source The new trailer source
	 */
	public void setSource(String source) {
		this.source = source;
	}

	//endregion
	
	@Override
	protected boolean parseJSON(JSONObject json) {
		return false;
	}
	
}
