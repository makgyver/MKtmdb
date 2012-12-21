package mk.tmdb.entity.movie;

import mk.tmdb.core.Constants;
import net.sf.json.JSONObject;

public class MovieBasic extends MovieReduced {
	
	protected Double popularity = null;
	
	public MovieBasic(JSONObject json) {
		super(json);
		parseJSON(json);
	}

	public MovieBasic(MovieThumbnail movie) {
		this(movie.getOriginJSON());
	}
	
	public double getPopularity() {
		return popularity;
	}

	public void setPopularity(double popularity) {
		this.popularity = popularity;
	}

	public boolean isPopularitySet() {
		return popularity != null;
	}
	
	@Override
	protected boolean parseJSON(JSONObject json) {
		
		if (json.has(Constants.POPULARITY)) setPopularity(json.getDouble(Constants.POPULARITY));

		return true;
	}

}
