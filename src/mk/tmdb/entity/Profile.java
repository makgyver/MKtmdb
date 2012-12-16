package mk.tmdb.entity;

import java.net.MalformedURLException;
import java.net.URL;

import mk.tmdb.core.Configuration;
import mk.tmdb.exception.ConfigurationNotLoadedException;
import mk.tmdb.exception.ImageSizeNotSupportedException;

public class Profile extends Image {

	public Profile(String path) {
		super(path);
	}

	@Override
	public URL getUrl(Size size) throws MalformedURLException, 
										ConfigurationNotLoadedException, 
										ImageSizeNotSupportedException {
		
		if (Configuration.isLoaded()) {
			
			if (Configuration.getProfileSizes().contains(size)) {
				
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
