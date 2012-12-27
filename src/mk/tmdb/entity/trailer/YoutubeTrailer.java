package mk.tmdb.entity.trailer;

import java.net.MalformedURLException;
import java.net.URL;

import mk.tmdb.core.Constants;
import mk.tmdb.utils.Log;
import net.sf.json.JSONObject;

/**
 * Class that represents a Youtube trailer.
 * 
 * @author Mirko Polato
 *
 */
public class YoutubeTrailer extends Trailer {

	/**
	 * The Youtube base URL.
	 */
	private static final String YOUTUBE_URL = "http://www.youtube.com/watch?v="; 
	
	/**
	 * Creates a new instance of Youtube trailer based on the origin JSON object.
	 * 
	 * @param json The origin JSON object
	 */
	public YoutubeTrailer(JSONObject json) {
		super(json, Constants.YOUTUBE);
		parseJSON(json);
	}
	
	/**
	 * Copy constructor.
	 * 
	 * @param trailer The trailer to copy
	 */
	public YoutubeTrailer(YoutubeTrailer trailer) {
		this(trailer.getOriginJSON());
	}
	
	@Override
	public boolean parseJSON(JSONObject json) {
		
		setName(json.getString(Constants.NAME));
		setSize(json.getString(Constants.SIZE));
		
		try {
			setLink(new URL(YOUTUBE_URL + json.getString(Constants.LINK)));
		} catch (MalformedURLException e) {
			Log.print(e);
		}
		
		return true;
	}

}
