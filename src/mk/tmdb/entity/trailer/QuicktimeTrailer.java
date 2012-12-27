package mk.tmdb.entity.trailer;

import java.net.MalformedURLException;
import java.net.URL;

import mk.tmdb.core.Constants;
import mk.tmdb.utils.Log;
import net.sf.json.JSONObject;

/**
 * Class that represents a Quicktime trailer.
 * 
 * @author Mirko Polato
 *
 */
public class QuicktimeTrailer extends Trailer {
	
	/**
	 * Creates a new instance of Quicktime trailer based on the origin JSON object and 
	 * assigning the given name.
	 * 
	 * @param json The origin JSON object
	 * @param name The trailer name
	 */
	public QuicktimeTrailer(JSONObject json, String name) {
		super(json, Constants.QUICKTIME);
		setName(name);
		parseJSON(json);
	}
	
	/**
	 * Copy constructor.
	 * 
	 * @param trailer The trailer to copy
	 * @param name The name of the trailer
	 */
	public QuicktimeTrailer(QuicktimeTrailer trailer, String name) {
		this(trailer.getOriginJSON(), name);
	}

	/**
	 * Parses the origin JSON object.
	 */
	private boolean parseJSON(JSONObject json) {
		
		if (json.isNullObject()) return false;
		
		setSize(json.getString(Constants.SIZE));
		
		try {
			setLink(new URL(json.getString(Constants.LINK)));
		} catch (MalformedURLException e) {
			Log.print(e);
		}
		
		return true;
	}
	
}
