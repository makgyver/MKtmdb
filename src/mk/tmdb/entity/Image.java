package mk.tmdb.entity;

import java.net.MalformedURLException;
import java.net.URL;

import net.sf.json.JSONObject;

import mk.tmdb.core.Configuration;
import mk.tmdb.core.Constants;
import mk.tmdb.exception.ConfigurationNotLoadedException;
import mk.tmdb.exception.ImageSizeNotSupportedException;
import mk.tmdb.utils.Log;
import mk.tmdb.utils.Size;

public abstract class Image extends Entity {
		
	protected String path;
	protected int width = 0;
	protected int height = 0;
	protected String iso639_1 = "";
	protected double aspectRatio = 0.0;
	protected double average = 0.0;
	protected int count = 0;
	
	public Image(String path) {
		this.path = path;
	}

	public Image(JSONObject json) {
		super(json);
		parseJSON(json);
	}

	public String getPath() {
		return path;
	}

	private void setPath(String path) {
		this.path = path;
	}

	public int getWidth() {
		return width;
	}

	private void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	private void setHeight(int height) {
		this.height = height;
	}

	public String getIso639_1() {
		return iso639_1;
	}

	private void setIso639_1(String iso639_1) {
		this.iso639_1 = iso639_1;
	}

	public double getAspectRatio() {
		return aspectRatio;
	}

	private void setAspectRatio(double aspectRatio) {
		this.aspectRatio = aspectRatio;
	}

	public double getAverage() {
		return average;
	}

	private void setAverage(double average) {
		this.average = average;
	}

	public int getCount() {
		return count;
	}

	private void setCount(int count) {
		this.count = count;
	}

	public URL getUrl(Size size) throws MalformedURLException, 
										ConfigurationNotLoadedException, 
										ImageSizeNotSupportedException {
		
		return new URL(Configuration.getUrl() + 
				   	   size + 
				       path);
	}
	
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
