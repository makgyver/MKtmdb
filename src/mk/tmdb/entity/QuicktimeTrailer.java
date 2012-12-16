package mk.tmdb.entity;

import java.net.MalformedURLException;
import java.net.URL;

import mk.tmdb.utils.Log;
import net.sf.json.JSONObject;

public class QuicktimeTrailer extends Trailer {

	private static final String QUICKTIME = "quicktime";
	
	public QuicktimeTrailer(JSONObject json, String name) {
		super(QUICKTIME);
		setName(name);
		parseJSON(json);
	}

	@Override
	public boolean parseJSON(JSONObject json) {
		
		setSize(json.getString(SIZE));
		
		try {
			setLink(new URL(json.getString(LINK)));
		} catch (MalformedURLException e) {
			Log.print(e);
		}
		
		return true;
	}
}
