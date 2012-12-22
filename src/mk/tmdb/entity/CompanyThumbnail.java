package mk.tmdb.entity;

import java.util.LinkedHashSet;
import java.util.Set;

import mk.tmdb.core.Constants;
import mk.tmdb.core.TMDbAPI;
import mk.tmdb.entity.movie.MovieReduced;
import mk.tmdb.exception.ResponseException;
import mk.tmdb.utils.Log;
import mk.tmdb.utils.ResponseArray;
import mk.tmdb.utils.ResponseObject;
import net.sf.json.JSONObject;

public class CompanyThumbnail extends Entity {

	private Integer id;
	private String name;
	
	public CompanyThumbnail(JSONObject json) {
		super(json);
		parseJSON(json);
	}
	
	public CompanyThumbnail(CompanyThumbnail company) {
		this(company.getOriginJSON());
	}
	
	//region Getters/Setters
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	//endregion
	
	@Override
	protected boolean parseJSON(JSONObject json) {
		try {
			
			setId(json.getInt(Constants.ID));
			setName(json.getString(Constants.NAME));
			
		} catch (Exception e) {
			Log.print(e);
			return false;
		}
		
		return true;	
	}
	
	//region Static methods
	
	public static Company getInformation(int companyID) throws ResponseException {
		
		ResponseObject response = TMDbAPI.getCompanyInformation(companyID);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			return new Company(response.getData());
		}
	}
	
	public static Set<MovieReduced> getAssociatedMovies(int companyID) throws ResponseException {
		return getAssociatedMovies(companyID, 1);
	}
	
	public static Set<MovieReduced> getAssociatedMovies(int companyID, int page) throws ResponseException {
		
		ResponseArray response = TMDbAPI.getMoviesByCompany(companyID, page);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			Set<MovieReduced> movies = new LinkedHashSet<MovieReduced>();
			Set<JSONObject> array = response.getData();
			for(JSONObject json : array) {
				movies.add(new MovieReduced(json));
			}
			
			return movies;
		}
	}
	
	public static Set<MovieReduced> getAllAssociatedMovies(int companyID) throws ResponseException {
		
		ResponseArray response = TMDbAPI.getAllMoviesByCompany(companyID);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			Set<MovieReduced> movies = new LinkedHashSet<MovieReduced>();
			Set<JSONObject> array = response.getData();
			for(JSONObject json : array) {
				movies.add(new MovieReduced(json));
			}
			
			return movies;
		}
	}
	
	//endregion
}
