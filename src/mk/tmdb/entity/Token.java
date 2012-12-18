package mk.tmdb.entity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import mk.tmdb.core.Constants;
import mk.tmdb.utils.Log;
import net.sf.json.JSONObject;

public class Token extends Entity {
	
	private Date expirationDate;
	private String value;
	
	public Token(JSONObject json) {
		super(json);
		parseJSON(json);
	}	
	
	public Date getExpirationDate() {
		return expirationDate;
	}

	private void setExpirationDate(String exp) {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd.HH:mm:ss Z");
		try {
			this.expirationDate = (Date)formatter.parse(exp);
		} catch (ParseException e) {
			Log.print(e);
		}
	}

	public String getValue() {
		return value;
	}

	private void setValue(String value) {
		this.value = value;
	}

	@Override
	protected boolean parseJSON(JSONObject json) {
		
		setExpirationDate(json.getString(Constants.EXPIRATION));
		setValue(json.getString(Constants.TOKEN));
		
		return true;
	}

}
