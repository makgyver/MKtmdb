package mk.tmdb.entity.image;

import java.net.MalformedURLException;
import java.net.URL;

import net.sf.json.JSONObject;

import mk.tmdb.core.Configuration;
import mk.tmdb.exception.ConfigurationNotLoadedException;
import mk.tmdb.exception.ImageSizeNotSupportedException;
import mk.tmdb.utils.Size;

/**
 * Class that represents a backdrop image.
 * 
 * @author Mirko Polato
 *
 */
public class Backdrop extends Image {
	
	/**
	 * Creates a new instance of Backdrop based on the origin JSON object.
	 * 
	 * @param json The origin JSON object
	 */
	public Backdrop(JSONObject json) {
		super(json);
	}
	
	/**
	 * Copy constructor.
	 * 
	 * @param backdrop The image to copy
	 */
	public Backdrop(Backdrop backdrop) {
		this(backdrop.getOriginJSON());
	}

	@Override
	public URL getUrl(Size size) throws MalformedURLException, 
										ConfigurationNotLoadedException, 
										ImageSizeNotSupportedException {
		
		if (Configuration.isLoaded()) {
			
			if (Configuration.getBackdropSizes().contains(size)) {
				
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
