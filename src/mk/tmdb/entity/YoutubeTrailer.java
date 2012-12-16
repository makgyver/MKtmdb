package mk.tmdb.entity;

import java.net.MalformedURLException;
import java.net.URL;

import mk.tmdb.utils.Log;
import net.sf.json.JSONObject;

public class YoutubeTrailer extends Trailer {

	private static final String YOUTUBE_URL = "http://www.youtube.com/watch?v=";
	private static final String YOUTUBE = "youtube"; 
	
	public YoutubeTrailer(JSONObject json) {
		super(YOUTUBE);
		parseJSON(json);
	}
	
	@Override
	public boolean parseJSON(JSONObject json) {
		
		setName(json.getString(NAME));
		setSize(json.getString(SIZE));
		
		try {
			setLink(new URL(YOUTUBE_URL + json.getString(LINK)));
		} catch (MalformedURLException e) {
			Log.print(e);
		}
		
		return true;
	}

}
