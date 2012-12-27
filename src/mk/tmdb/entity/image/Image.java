package mk.tmdb.entity.image;

import java.net.MalformedURLException;
import java.net.URL;

import net.sf.json.JSONObject;

import mk.tmdb.core.Configuration;
import mk.tmdb.core.Constants;
import mk.tmdb.entity.Entity;
import mk.tmdb.exception.ConfigurationNotLoadedException;
import mk.tmdb.exception.ImageSizeNotSupportedException;
import mk.tmdb.utils.Log;
import mk.tmdb.utils.Size;

/**
 * Abstract class that represents an image.
 * 
 * @author Mirko Polato
 *
 */
public abstract class Image extends Entity {
		
	//region Fields 
	
	/**
	 * The image path.
	 */
	protected String path;
	
	/**
	 * The image width.
	 */
	protected int width = 0;
	
	/**
	 * The image height.
	 */
	protected int height = 0;
	
	/**
	 * Codes for the representation of names of languages.
	 */
	protected String iso639_1 = "";
	
	/**
	 * The image aspect ratio.
	 */
	protected double aspectRatio = 0.0;
	
	/**
	 * The vote average of the image.
	 */
	protected double average = 0.0;
	
	/**
	 * The votes count of the image.
	 */
	protected int count = 0;
	
	//endregion

	/**
	 * Sets the origin JSON object to the given one.
	 * 
	 * @param json The origin JSON object
	 */
	public Image(JSONObject json) {
		super(json);
		parseJSON(json);
	}
	
	//region Getters/Setters

	/**
	 * Gets the image path.
	 * 
	 * @return The image path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * Sets the image path.
	 * 
	 * @param path The new image path
	 */
	private void setPath(String path) {
		this.path = path;
	}

	/**
	 * Gets the image width.
	 * 
	 * @return The image width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Sets the image width.
	 * 
	 * @param width The new image width
	 */
	private void setWidth(int width) {
		this.width = width;
	}

	/**
	 * Gets the image height.
	 * 
	 * @return The image height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Sets the image height.
	 * 
	 * @param height The new image height
	 */
	private void setHeight(int height) {
		this.height = height;
	}

	/**
	 * Gets the account ISO 639-1.
	 * 
	 * @return The account ISO 639-1
	 */
	public String getIso639_1() {
		return iso639_1;
	}
	
	/**
	 * Sets the account ISO 639-1.
	 * 
	 * @param iso639_1 The new account ISO 639-1
	 */
	public void setIso639_1(String iso639_1) {
		this.iso639_1 = iso639_1;
	}

	/**
	 * Gets the aspect ration of the image.
	 * 
	 * @return The aspect ratio of the image
	 */
	public double getAspectRatio() {
		return aspectRatio;
	}

	/**
	 * Sets the aspect ratio of the image.
	 * 
	 * @param aspectRatio The new image aspect ratio
	 */
	private void setAspectRatio(double aspectRatio) {
		this.aspectRatio = aspectRatio;
	}

	/**
	 * Gets the votes average of the image.
	 * 
	 * @return The image votes average
	 */
	public double getAverage() {
		return average;
	}

	/**
	 * Sets the votes average of the image.
	 * 
	 * @param average The new vote average of the image.
	 */
	private void setAverage(double average) {
		this.average = average;
	}

	/**
	 * Gets the votes count of the image.
	 * 
	 * @return The image votes count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * Sets the votes count of the image.
	 * 
	 * @param count The new image votes count
	 */
	private void setCount(int count) {
		this.count = count;
	}

	/**
	 * Gets the image URL.
	 * 
	 * @param size The image size
	 * @return The image URL
	 * @throws MalformedURLException Throws if the URL is in a wrong form.
	 * @throws ConfigurationNotLoadedException Throws if the configuration information are not been loaded.
	 * @throws ImageSizeNotSupportedException Throws if the given image size is not supported.
	 */
	public URL getUrl(Size size) throws MalformedURLException, 
										ConfigurationNotLoadedException, 
										ImageSizeNotSupportedException {
		
		return new URL(Configuration.getUrl() + 
				   	   size + 
				       path);
	}
	
	//endregion
	
	@Override
	protected boolean parseJSON(JSONObject json) {
		try {
			
			setPath(json.getString(Constants.PATH));
			setWidth(json.getInt(Constants.WIDTH));
			setHeight(json.getInt(Constants.HEIGHT));
			setIso639_1(json.getString(Constants.ISO_6391));
			setAspectRatio(json.getDouble(Constants.RATIO));
			setAverage(json.getDouble(Constants.AVERAGE));
			setCount(json.getInt(Constants.COUNT));
			
		} catch (Exception e) {
			Log.print(e);
			return false;
		}
		
		return true;
	}
	
}
