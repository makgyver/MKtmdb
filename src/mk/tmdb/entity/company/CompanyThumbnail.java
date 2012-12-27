package mk.tmdb.entity.company;

import java.util.LinkedHashSet;
import java.util.Set;

import mk.tmdb.core.Constants;
import mk.tmdb.core.TMDbAPI;
import mk.tmdb.entity.Entity;
import mk.tmdb.entity.movie.MovieReduced;
import mk.tmdb.exception.ResponseException;
import mk.tmdb.utils.Log;
import mk.tmdb.utils.ResponseArray;
import mk.tmdb.utils.ResponseObject;
import net.sf.json.JSONObject;

/**
 * Class that represent a Company with reduced features.
 * For more feature Company see the class {@link Company}.
 * 
 * @author Mirko Polato
 *
 */
public class CompanyThumbnail extends Entity {

	/**
	 * The company ID.
	 */
	protected Integer id;
	
	/**
	 * The name of the company.
	 */
	protected String name;
	
	/**
	 * The logo path of the company.
	 */
	protected String logoPath = null;
	
	/**
	 * Creates a new instance of CompanyThumbnail based on the origin JSON object. 
	 * @param json The origin JSON object
	 */
	public CompanyThumbnail(JSONObject json) {
		super(json);
		parseJSON(json);
	}
	
	/**
	 * Copy constructor.
	 * 
	 * @param company The company to copy.
	 */
	public CompanyThumbnail(CompanyThumbnail company) {
		this(company.getOriginJSON());
	}
	
	//region Getters/Setters
	
	/**
	 * Gets the company ID.
	 * 
	 * @return The company ID
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the company ID.
	 * 
	 * @param id The new company ID
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Gets the company name.
	 * 
	 * @return The company name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the company name.
	 * 
	 * @param name The new company name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Gets the company logo path.
	 * 
	 * @return The company logo path
	 */
	public String getLogoPath() {
		return logoPath;
	}

	/**
	 * Sets the company logo path.
	 * 
	 * @param logoPath The new company logo path
	 */
	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}

	/**
	 * Checks if the logo path is set.
	 * 
	 * @return Whether the company logo path is set or not
	 */
	public boolean isLogoPathSet() {
		return logoPath != null;
	}

	//endregion
	
	private boolean parseJSON(JSONObject json) {
		
		if (json.isNullObject()) return false;
		
		try {
			setId(json.getInt(Constants.ID));
			setName(json.getString(Constants.NAME));
			if (json.has(Constants.LOGO_PATH)) setLogoPath(json.getString(Constants.LOGO_PATH));
			
		} catch (Exception e) {
			Log.print(e);
			return false;
		}
		
		return true;	
	}
	
	//region Static methods
	
	/**
	 * Gets a new instance of Company filled with the retrieved information about it.
	 * 
	 * @param companyID The company ID
	 * @return The company
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
	public static Company getInformation(int companyID) throws ResponseException {
		
		ResponseObject response = TMDbAPI.getCompanyInformation(companyID);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			return new Company(response.getData());
		}
	}
	
	/**
	 * Gets the list of movies associated with the specified company.
	 * Returns the results of the first page.
	 * 
	 * @param companyID The company ID
	 * @return The list of movies
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
	public static Set<MovieReduced> getAssociatedMovies(int companyID) throws ResponseException {
		return getAssociatedMovies(companyID, 1);
	}
	
	/**
	 * Gets the list of movies associated with the specified company.
	 * Returns the results of the given page number.
	 * 
	 * @param companyID The company ID
	 * @param page The page number to retrieve
	 * @return The list of movies
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
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
	
	/**
	 * Gets the entire list of movies associated with the specified company.
	 * 
	 * @param companyID The company ID
	 * @return The list of movies
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
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
	
	/**
	 * Searches for companies by name.
	 * Returns the results of the first page.
	 * 
	 * @param name The company name
	 * @return The list of company that match the given name
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
	public static Set<CompanyThumbnail> searchByName(String name) throws ResponseException {
		return searchByName(name, 1);
	}
	
	/**
	 * Searches for companies by name.
	 * Returns the results of the given page number.
	 * 
	 * @param name The company name
	 * @param page The page number to retrieve
	 * @return The list of company that match the given name
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
	public static Set<CompanyThumbnail> searchByName(String name, int page) throws ResponseException {
		ResponseArray response = TMDbAPI.searchCompanyByName(name, page);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			Set<JSONObject> array = response.getData();
			Set<CompanyThumbnail> companies = new LinkedHashSet<CompanyThumbnail>();
			for(JSONObject json : array) {
				companies.add(new CompanyThumbnail(json));
			}
			
			return companies;
		}
	}
	
	/**
	 * Searches for companies by name. Gets all the results.
	 * 
	 * @param name The company name
	 * @return The list of company that match the given name
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
	public static Set<CompanyThumbnail> fullSearchByName(String name) throws ResponseException {
		ResponseArray response = TMDbAPI.fullSearchCompanyByName(name);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			Set<JSONObject> array = response.getData();
			Set<CompanyThumbnail> companies = new LinkedHashSet<CompanyThumbnail>();
			for(JSONObject json : array) {
				companies.add(new CompanyThumbnail(json));
			}
			
			return companies;
		}
	}
	
	//endregion

}
