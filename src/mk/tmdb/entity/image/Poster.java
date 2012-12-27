package mk.tmdb.entity.image;

import java.net.MalformedURLException;
import java.net.URL;

import net.sf.json.JSONObject;

import mk.tmdb.core.Configuration;
import mk.tmdb.exception.ConfigurationNotLoadedException;
import mk.tmdb.exception.ImageSizeNotSupportedException;
import mk.tmdb.utils.Size;

/**
 * Class that represents a poster image.
 * 
 * @author Mirko Polato
 *
 */
public class Poster extends Image {
	
	/**
	 * Creates a new instance of Poster based on the origin JSON object.
	 * 
	 * @param json The origin JSON object
	 */
	public Poster(JSONObject json) {
		super(json);
	}

	/**
	 * Copy constructor.
	 * 
	 * @param poster The image to copy
	 */
	public Poster(Poster poster) {
		this(poster.getOriginJSON());
	}
	
	@Override
	public URL getUrl(Size size) throws MalformedURLException, 
										ConfigurationNotLoadedException, 
										ImageSizeNotSupportedException {
		
		if (Configuration.isLoaded()) {
			
			if (Configuration.getPosterSizes().contains(size)) {
				
				return new URL(Configuration.getUrl() + 
						   	   size + 
						   	   path);
				
			} else {
				throw new ImageSizeNotSupportedException();
			}
			
		} else {
			throw new ConfigurationNotLoadedException();
		}
	}
	
}
