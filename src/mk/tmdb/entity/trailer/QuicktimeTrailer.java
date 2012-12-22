package mk.tmdb.entity.trailer;

import java.net.MalformedURLException;
import java.net.URL;

import mk.tmdb.core.Constants;
import mk.tmdb.utils.Log;
import net.sf.json.JSONObject;

public class QuicktimeTrailer extends Trailer {
	
	public QuicktimeTrailer(JSONObject json, String name) {
		super(json, Constants.QUICKTIME);
		setName(name);
		parseJSON(json);
	}
	
	public QuicktimeTrailer(QuicktimeTrailer trailer, String name) {
		this(trailer.getOriginJSON(), name);
	}

	@Override
	public boolean parseJSON(JSONObject json) {
		
		setSize(json.getString(Constants.SIZE));
		
		try {
			setLink(new URL(json.getString(Constants.LINK)));
		} catch (MalformedURLException e) {
			Log.print(e);
		}
		
		return true;
	}
	
}