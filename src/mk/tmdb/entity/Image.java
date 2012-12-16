package mk.tmdb.entity;

import java.net.MalformedURLException;
import java.net.URL;

import net.sf.json.JSONObject;

import mk.tmdb.core.Configuration;
import mk.tmdb.exception.ConfigurationNotLoadedException;
import mk.tmdb.exception.ImageSizeNotSupportedException;
import mk.tmdb.utils.Log;

public abstract class Image implements IEntity {
	
	private static final String PATH = "file_path";
	private static final String WIDTH = "width";
	private static final String HEIGHT = "height";
	private static final String ISO = "iso_639_1";
	private static final String RATIO = "aspect_ratio";
	private static final String AVERAGE = "vote_average";
	private static final String COUNT = "vote_count";
	
	protected String originJson = "";
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
		this.originJson = json.toString();
		parseJSON(json);
	}
	
	public String getOriginJson() {
		return originJson;
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
	public boolean parseJSON(JSONObject json) {
		try {
			
			setPath(json.getString(PATH));
			setWidth(json.getInt(WIDTH));
			setHeight(json.getInt(HEIGHT));
			setIso639_1(json.getString(ISO));
			setAspectRatio(json.getDouble(RATIO));
			setAverage(json.getDouble(AVERAGE));
			setCount(json.getInt(COUNT));
			
		} catch (Exception e) {
			Log.print(e);
			return false;
		}
		
		return true;
	}
}
