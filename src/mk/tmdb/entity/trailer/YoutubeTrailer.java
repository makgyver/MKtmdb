package mk.tmdb.entity.trailer;

import java.net.MalformedURLException;
import java.net.URL;

import mk.tmdb.core.Constants;
import mk.tmdb.utils.Log;
import net.sf.json.JSONObject;

public class YoutubeTrailer extends Trailer {

	private static final String YOUTUBE_URL = "http://www.youtube.com/watch?v="; 
	
	public YoutubeTrailer(JSONObject json) {
		super(json, Constants.YOUTUBE);
		parseJSON(json);
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
