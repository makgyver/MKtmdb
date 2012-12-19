package mk.tmdb.core;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Date;

import mk.tmdb.entity.Account;
import mk.tmdb.entity.Token;
import mk.tmdb.utils.Log;
import mk.tmdb.utils.ResponseArray;
import mk.tmdb.utils.ResponseObject;
import mk.tmdb.utils.Status;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

/**
 * Static class that offers methods for getting HTTP request.
 * Before to make any request is needed loading the configuration ( @see Configuration#Load )
 * to set up the environment, otherwise some request can raise a ConfigurationNotLoadedException.
 * 
 * @author Mirko Polato
 *
 */
public final class TMDBAPI {

	//region Timeout
	
	/**
	 * Timeout in milliseconds.
	 */
	private static int timeout = 20000; // 20 seconds
	
	/**
	 * Sets the timeout to the given value (milliseconds).
	 * @param limit The timeout in milliseconds.
	 */
	public static void setTimeout(int limit) {
		if (limit < 0) limit = 0;
		timeout = limit;
	}
	
	//endregion
	
	//region API Calls
	
	/**
	 * Makes an HTTP request (GET) and gets back the result as a string.
	 * @param url The query URL
	 * @return The result string
	 */
	private static String makeApiCallGet(URL url) {
		StringBuilder result = new StringBuilder();
		try {
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setReadTimeout(timeout);
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			for (String line = null; (line = reader.readLine()) != null;) {
			    result.append(line).append("\n");
			}
			reader.close();
			
		} catch (SocketTimeoutException ste) {
			Log.print(ste);
			
			JSONObject json = new JSONObject();
			json.put(Constants.STATUS_CODE, Status.TIMEOUT);
			
			result.setLength(0);
			result.append(json.toString());
			
		} catch (Exception e) {
			Log.print(e);
			
			JSONObject json = new JSONObject();
			json.put(Constants.STATUS_CODE, Status.UNKNOWN_ERROR);
			
			result.setLength(0);
			result.append(json.toString());
		}
		
		return result.toString();
	}
	
	/**
	 * Makes an HTTP request (POST) and gets back the result as a string.
	 * @param url The query URL
	 * @param json The Json object to post
	 * @return The result string
	 */
	private static String makeApiCallPost(URL url, JSONObject json) {
		StringBuilder result = new StringBuilder();
		try {
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setDoOutput(true);
			conn.setReadTimeout(timeout);
			
			OutputStream out = conn.getOutputStream();
			OutputStreamWriter wr = new OutputStreamWriter(out);
			wr.write(json.toString());
			wr.flush();
			wr.close();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			for (String line = null; (line = reader.readLine()) != null;) {
			    result.append(line).append("\n");
			}
			reader.close();
			
		} catch (SocketTimeoutException ste) {
			Log.print(ste);
			
			JSONObject jsonErr = new JSONObject();
			jsonErr.put(Constants.STATUS_CODE, Status.TIMEOUT);
			
			result.setLength(0);
			result.append(jsonErr.toString());
			
		} catch (Exception e) {
			Log.print(e);
			
			JSONObject jsonErr = new JSONObject();
			jsonErr.put(Constants.STATUS_CODE, Status.UNKNOWN_ERROR);
			
			result.setLength(0);
			result.append(jsonErr.toString());
		}
		
		return result.toString();
	}
	
	/**
	 * Converts a string to a JSONObject
	 * @param strJson The Json string
	 * @return The JSONObject
	 */
	private static JSONObject toJSON(String strJson) {
		return (JSONObject) JSONSerializer.toJSON(strJson);
	}
	
	//endregion

	//region Configuration
	
	public static ResponseObject getConfiguration() {
		try {
			
			return new ResponseObject(toJSON(makeApiCallGet(URLCreator.getConfigurationUrl())));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseObject(Status.MALFORMED_URL);
		}
	}
	
	//endregion
	
	//region Authentication
	
	public static ResponseObject getAuthenticationToken() {
		try {
			
			return new ResponseObject(toJSON(makeApiCallGet(URLCreator.getAuthTokenUrl())));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseObject(Status.MALFORMED_URL);
		}
	}
	
	public static ResponseObject getAuthenticationSession(Token token) {
		try {
			
			return new ResponseObject(toJSON(makeApiCallGet(URLCreator.getAuthSessionUrl(token.getValue()))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseObject(Status.MALFORMED_URL);
		}
	}
	
	public static ResponseObject getAuthenticationGuestSession() {
		try {
			
			return new ResponseObject(toJSON(makeApiCallGet(URLCreator.getGuestSessionUrl())));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseObject(Status.MALFORMED_URL);
		}
	}
	
	//endregion
	
	//region Account
	
	public static ResponseObject getAccountInformation(String sessionID) {
		try {
			
			return new ResponseObject(toJSON(makeApiCallGet(URLCreator.getAccountInfoUrl(sessionID))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseObject(Status.MALFORMED_URL);
		}
	}
	
	public static ResponseArray getFavoritesLists(Account account, String sessionID) {
		return getFavoritesLists(account, sessionID, 1);
	}
	
	public static ResponseArray getFavoritesLists(Account account, String sessionID, int page) {
		try {
			
			return new ResponseArray(toJSON(makeApiCallGet(URLCreator.getAccountFavsListsUrl(account.getId(), sessionID, page))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseArray(Status.MALFORMED_URL);
		}
	}
	
	public static ResponseArray getFavoritesMovies(Account account, String sessionID) {
		return getFavoritesMovies(account, sessionID, 1);
	}
	
	public static ResponseArray getFavoritesMovies(Account account, String sessionID, int page) {
		try {
			
			return new ResponseArray(toJSON(makeApiCallGet(URLCreator.getAccountFavsMoviesUrl(account.getId(), sessionID, page))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseArray(Status.MALFORMED_URL);
		}
	}
	
	public static ResponseObject addMovieToFavorites(Account account, String sessionID, int movieID) {
		try {
			
			JSONObject json = new JSONObject();
			json.put(Constants.MOVIE_ID, movieID);
			json.put(Constants.FAVORITE, true);
			
			return new ResponseObject(toJSON(makeApiCallPost(URLCreator.addMovieToFavsUrl(account.getId(), sessionID), json)));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseObject(Status.MALFORMED_URL);
		}
	}
	
	public static ResponseObject removeMovieFromFavorites(Account account, String sessionID, int movieID) {
		try {
			
			JSONObject json = new JSONObject();
			json.put(Constants.MOVIE_ID, movieID);
			json.put(Constants.FAVORITE, false);
			
			return new ResponseObject(toJSON(makeApiCallPost(URLCreator.removeMovieFromFavsUrl(account.getId(), sessionID), json)));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseObject(Status.MALFORMED_URL);
		}
	}
	
	public static ResponseArray getRatedMovies(Account account, String sessionID) {
		return getRatedMovies(account, sessionID, 1);
	}
	
	public static ResponseArray getRatedMovies(Account account, String sessionID, int page) {
		try {
			
			return new ResponseArray(toJSON(makeApiCallGet(URLCreator.getRatedMoviesUrl(account.getId(), sessionID, page))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseArray(Status.MALFORMED_URL);
		}
	}
	
	public static ResponseArray getMovieWatchList(Account account, String sessionID) {
		return getMovieWatchList(account, sessionID, 1);
	}
	
	public static ResponseArray getMovieWatchList(Account account, String sessionID, int page) {
		try {
			
			return new ResponseArray(toJSON(makeApiCallGet(URLCreator.getWatchlistUrl(account.getId(), sessionID, page))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseArray(Status.MALFORMED_URL);
		}
	}
	
	public static ResponseObject addMovieToWatchlist(Account account, String sessionID, int movieID) {
		try {
			
			JSONObject json = new JSONObject();
			json.put(Constants.MOVIE_ID, movieID);
			json.put(Constants.WATCHLIST, true);
			
			return new ResponseObject(toJSON(makeApiCallPost(URLCreator.addMovieToWatchlistUrl(account.getId(), sessionID), json)));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseObject(Status.MALFORMED_URL);
		}
	}
	
	public static ResponseObject removeMovieFromWatchlist(Account account, String sessionID, int movieID) {
		try {
			
			JSONObject json = new JSONObject();
			json.put(Constants.MOVIE_ID, movieID);
			json.put(Constants.WATCHLIST, false);
			
			return new ResponseObject(toJSON(makeApiCallPost(URLCreator.removeMovieFromWatchlistUrl(account.getId(), sessionID), json)));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseObject(Status.MALFORMED_URL);
		}
	}
	
	//endregion
	
	//region Movie
	
	public static ResponseObject getMovieInformation(int movieID) {
		try {
			return new ResponseObject(toJSON(makeApiCallGet(URLCreator.getMovieInfoUrl(movieID))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseObject(Status.MALFORMED_URL);
		}
	}
	
	public static ResponseObject getAlternativeMovieTitles(int movieID) {
		try {
			return new ResponseObject(toJSON(makeApiCallGet(URLCreator.getAlternativeMovieTitlesUrl(movieID))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseObject(Status.MALFORMED_URL);
		}
	}
	
	public static ResponseObject getCastInformation(int movieID) {
		try {
			return new ResponseObject(toJSON(makeApiCallGet(URLCreator.getCastInfoUrl(movieID))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseObject(Status.MALFORMED_URL);
		}
	}
	
	public static ResponseObject getMovieImages(int movieID) {
		try {
			return new ResponseObject(toJSON(makeApiCallGet(URLCreator.getMovieImagesUrl(movieID))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseObject(Status.MALFORMED_URL);
		}
	}
	
	public static ResponseObject getMovieKeywords(int movieID) {
		try {
			return new ResponseObject(toJSON(makeApiCallGet(URLCreator.getMovieKeywordsUrl(movieID))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseObject(Status.MALFORMED_URL);
		}
	}
	
	public static ResponseObject getMovieReleases(int movieID) {
		try {
			return new ResponseObject(toJSON(makeApiCallGet(URLCreator.getMovieReleasesDatesUrl(movieID))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseObject(Status.MALFORMED_URL);
		}
	}
	
	public static ResponseObject getMovieTrailers(int movieID) {
		try {
			return new ResponseObject(toJSON(makeApiCallGet(URLCreator.getMovieTrailersUrl(movieID))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseObject(Status.MALFORMED_URL);
		}
	}
	
	public static ResponseObject getMovieTranslations(int movieID) {
		try {
			return new ResponseObject(toJSON(makeApiCallGet(URLCreator.getMovieTranslationsUrl(movieID))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseObject(Status.MALFORMED_URL);
		}
	}
	
	public static ResponseObject getSimilarMovies(int movieID) {
		return getSimilarMovies(movieID, 1);
	}
	
	public static ResponseObject getSimilarMovies(int movieID, int page) {
		try {
			return new ResponseObject(toJSON(makeApiCallGet(URLCreator.getSimilarMoviesUrl(movieID, page))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseObject(Status.MALFORMED_URL);
		}
	}
	
	public static ResponseObject getMovieChanges(int movieID) {
		try {
			return new ResponseObject(toJSON(makeApiCallGet(URLCreator.getMovieChangesUrl(movieID))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseObject(Status.MALFORMED_URL);
		}
	}
	
	public static ResponseArray getListsBelongsToMovie(int movieID) {
		return getListsBelongsToMovie(movieID, 1);
	}
	
	public static ResponseArray getListsBelongsToMovie(int movieID, int page) {
		try {
			return new ResponseArray(toJSON(makeApiCallGet(URLCreator.getListsBelongsToMovieUrl(movieID, page))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseArray(Status.MALFORMED_URL);
		}
	}
	
	public static ResponseObject getLatestMovie() {
		try {
			return new ResponseObject(toJSON(makeApiCallGet(URLCreator.getLatestMovieUrl())));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseObject(Status.MALFORMED_URL);
		}
	}
	
	public static ResponseArray getUpcomingMovies(int page) {
		try {
			return new ResponseArray(toJSON(makeApiCallGet(URLCreator.getUpcomingMoviesListUrl(page))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseArray(Status.MALFORMED_URL);
		}
	}
	
	public static ResponseArray getUpcomingMovies() {
		return getUpcomingMovies(1);
	}
	
	public static ResponseArray getAllUpcomingMovies() {
		try {
			
			ResponseArray result = new ResponseArray(toJSON(makeApiCallGet(URLCreator.getUpcomingMoviesListUrl())));
			
			for (int p = 2; p <= result.getPages(); p++) {
				ResponseArray page = new ResponseArray(toJSON(makeApiCallGet(URLCreator.getUpcomingMoviesListUrl(p))));
				for (Object obj : page.getData()) {
					result.addData((JSONObject) obj);
				}
			}
			
			return result;
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseArray(Status.MALFORMED_URL);
		}
	}
	
	public static ResponseArray getInTheatresMovies(int page) {
		try {
			return new ResponseArray(toJSON(makeApiCallGet(URLCreator.getInTheatresMoviesUrl(page))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseArray(Status.MALFORMED_URL);
		}
	}
	
	public static ResponseArray getInTheatresMovies() {
		return getInTheatresMovies(1);
	}
	
	public static ResponseArray getAllInTheatresMovies() {
		try {
			
			ResponseArray result = new ResponseArray(toJSON(makeApiCallGet(URLCreator.getInTheatresMoviesUrl())));
			
			for (int p = 2; p <= result.getPages(); p++) {
				ResponseArray page = new ResponseArray(toJSON(makeApiCallGet(URLCreator.getInTheatresMoviesUrl(p))));
				for (Object obj : page.getData()) {
					result.addData((JSONObject) obj);
				}
			}
			
			return result;
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseArray(Status.MALFORMED_URL);
		}
	}
	
	public static ResponseArray getPopularMovies(int page) {
		try {
			return new ResponseArray(toJSON(makeApiCallGet(URLCreator.getPopularMoviesUrl(page))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseArray(Status.MALFORMED_URL);
		}
	}
	
	public static ResponseArray getPopularMovies() {
		return getPopularMovies(1);
	}
	
	public static ResponseArray getAllPopularMovies() {
		try {
			
			ResponseArray result = new ResponseArray(toJSON(makeApiCallGet(URLCreator.getPopularMoviesUrl())));
			
			for (int p = 2; p <= result.getPages(); p++) {
				ResponseArray page = new ResponseArray(toJSON(makeApiCallGet(URLCreator.getPopularMoviesUrl(p))));
				for (Object obj : page.getData()) {
					result.addData((JSONObject) obj);
				}
			}
			
			return result;
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseArray(Status.MALFORMED_URL);
		}
	}
	
	public static ResponseArray getTopRatedMovies(int page) {
		try {
			return new ResponseArray(toJSON(makeApiCallGet(URLCreator.getTopRatedMoviesUrl(page))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseArray(Status.MALFORMED_URL);
		}
	}
	
	public static ResponseArray getTopRatedMovies() {
		return getTopRatedMovies(1);
	}
	
	public static ResponseArray getAllTopRatedMovies() {
		try {
			
			ResponseArray result = new ResponseArray(toJSON(makeApiCallGet(URLCreator.getTopRatedMoviesUrl())));
			
			for (int p = 2; p <= result.getPages(); p++) {
				ResponseArray page = new ResponseArray(toJSON(makeApiCallGet(URLCreator.getTopRatedMoviesUrl(p))));
				for (Object obj : page.getData()) {
					result.addData((JSONObject) obj);
				}
			}
			
			return result;
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseArray(Status.MALFORMED_URL);
		}
	}
	
	public static ResponseObject setMovieRate(String sessionID, boolean guest, int movieID, float rating) {
		try {
			
			DecimalFormat oneDigit = new DecimalFormat("#,##0.0");
			String strValue = oneDigit.format(rating);
			
			JSONObject json = new JSONObject();
			json.put(Constants.VALUE, strValue);
			
			return new ResponseObject(toJSON(makeApiCallPost(URLCreator.setMovieRateUrl(sessionID, movieID, guest), json)));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseObject(Status.MALFORMED_URL);
		}
	}
	
	//endregion
	
	//region People
	
	public static ResponseObject getPersonInformation(int personID) {
		try {
			return new ResponseObject(toJSON(makeApiCallGet(URLCreator.getPersonInfoUrl(personID))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseObject(Status.MALFORMED_URL);
		}
	}
	
	public static ResponseObject getPersonCredits(int personID) {
		try {
			return new ResponseObject(toJSON(makeApiCallGet(URLCreator.getPersonCreditsUrl(personID))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseObject(Status.MALFORMED_URL);
		}
	}
	
	
	public static ResponseObject getPersonImages(int personID) {
		try {
			return new ResponseObject(toJSON(makeApiCallGet(URLCreator.getPersonImagesUrl(personID))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseObject(Status.MALFORMED_URL);
		}
	}
	
	public static ResponseObject getPersonChanges(int personID) {
		try {
			return new ResponseObject(toJSON(makeApiCallGet(URLCreator.getPersonChangesUrl(personID))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseObject(Status.MALFORMED_URL);
		}
	}
	
	public static ResponseObject getLatestPerson() {
		try {
			return new ResponseObject(toJSON(makeApiCallGet(URLCreator.getLatestPerson())));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseObject(Status.MALFORMED_URL);
		}
	}
	
	//endregion
	
	//region Collections
	
	public static ResponseObject getCollectionInformation(int collectionID) {
		try {
			return new ResponseObject(toJSON(makeApiCallGet(URLCreator.getCollectionInfoUrl(collectionID))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseObject(Status.MALFORMED_URL);
		}
	}
	
	public static ResponseObject getCollectionImages(int collectionID) {
		try {
			return new ResponseObject(toJSON(makeApiCallGet(URLCreator.getCollectionInfoUrl(collectionID))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseObject(Status.MALFORMED_URL);
		}
	}
	
	//endregion
	
	//region Lists
	
	public static ResponseObject getList(int listID) {
		try {
			return new ResponseObject(toJSON(makeApiCallGet(URLCreator.getListUrl(listID))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseObject(Status.MALFORMED_URL);
		}
	}
	
	//endregion
	
	//region Company
	
	public static ResponseObject getCompanyInformation(int companyID) {
		try {
			return new ResponseObject(toJSON(makeApiCallGet(URLCreator.getCompanyInfoUrl(companyID))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseObject(Status.MALFORMED_URL);
		}
	}
	
	public static ResponseArray getMoviesListByCompany(int companyID) {
		return getMoviesListByCompany(companyID, 1);
	}
	
	public static ResponseArray getMoviesListByCompany(int companyID, int page) {
		try {
			return new ResponseArray(toJSON(makeApiCallGet(URLCreator.getMoviesListByCompanyUrl(companyID, page))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseArray(Status.MALFORMED_URL);
		}
	}
	
	//endregion
	
	//region Genre
	
	public static ResponseObject getGenresList() {
		try {
			return new ResponseObject(toJSON(makeApiCallGet(URLCreator.getGenresListUrl())));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseObject(Status.MALFORMED_URL);
		}
	}
	
	public static ResponseArray getMoviesListByGenre(int genreID) {
		return getMoviesListByGenre(genreID, 1);
	}
	
	public static ResponseArray getMoviesListByGenre(int genreID, int page) {
		try {
			return new ResponseArray(toJSON(makeApiCallGet(URLCreator.getMoviesListByGenreUrl(genreID, page))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseArray(Status.MALFORMED_URL);
		}
	}
	
	//endregion
	
	//region Search
	
	public static ResponseArray searchMovieByTitle(String movieTitle) {
		return searchMovieByTitle(movieTitle, 1);
	}
	
	public static ResponseArray searchMovieByTitle(String movieTitle, int page) {
		try {
			return new ResponseArray(toJSON(makeApiCallGet(URLCreator.searchMovieByTitleUrl(movieTitle, page))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseArray(Status.MALFORMED_URL);
		}
	}
	
	public static ResponseArray searchMovieByTitleAndYear(String movieTitle, int year) {
		return searchMovieByTitleAndYear(movieTitle, year, 1);
	}
	
	public static ResponseArray searchMovieByTitleAndYear(String movieTitle, int year, int page) {
		try {
			return new ResponseArray(toJSON(makeApiCallGet(URLCreator.searchMovieByTitleAndYearUrl(movieTitle, year, page))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseArray(Status.MALFORMED_URL);
		}
	}
	
	public static ResponseArray searchMovieByTitle(String movieTitle, boolean adult) {
		return searchMovieByTitle(movieTitle, adult, 1);
	}
	
	public static ResponseArray searchMovieByTitle(String movieTitle, boolean adult, int page) {
		try {
			return new ResponseArray(toJSON(makeApiCallGet(URLCreator.searchMovieByTitleUrl(movieTitle, adult, page))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseArray(Status.MALFORMED_URL);
		}
	}
	
	public static ResponseArray searchMovieByTitle(String movieTitle, int year, boolean adult) {
		return searchMovieByTitle(movieTitle, year, adult, 1);
	}
	
	public static ResponseArray searchMovieByTitle(String movieTitle, int year, boolean adult, int page) {
		try {
			return new ResponseArray(toJSON(makeApiCallGet(URLCreator.searchMovieByTitleUrl(movieTitle, year, adult, page))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseArray(Status.MALFORMED_URL);
		}
	}
	
	public static ResponseArray searchPersonByName(String name) {
		return searchPersonByName(name, 1);
	}
	
	public static ResponseArray searchPersonByName(String name, int page) {
		try {
			return new ResponseArray(toJSON(makeApiCallGet(URLCreator.searchPeopleByNameUrl(name, page))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseArray(Status.MALFORMED_URL);
		}
	}
	
	public static ResponseArray searchPersonByName(String name, boolean adult) {
		return searchPersonByName(name, adult, 1);
	}
	
	public static ResponseArray searchPersonByName(String name, boolean adult, int page) {
		try {
			return new ResponseArray(toJSON(makeApiCallGet(URLCreator.searchPeopleByNameUrl(name, adult, page))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseArray(Status.MALFORMED_URL);
		}
	}
	
	public static ResponseArray searchCompanyByName(String name) {
		return searchCompanyByName(name, 1);
	}
	
	public static ResponseArray searchCompanyByName(String name, int page) {
		try {
			return new ResponseArray(toJSON(makeApiCallGet(URLCreator.searchCompanyByNameUrl(name, page))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseArray(Status.MALFORMED_URL);
		}
	}
	
	//endregion
	
	//region Changes
	
	public static ResponseArray getChangedMovies() {
		return getChangedMovies(1);
	}
	
	public static ResponseArray getChangedPersons() {
		return getChangedPersons(1);
	}
	
	public static ResponseArray getChangedMovies(int page) {
		try {
			return new ResponseArray(toJSON(makeApiCallGet(URLCreator.getChangedMoviesUrl(page))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseArray(Status.MALFORMED_URL);
		}
	}
	
	public static ResponseArray getChangedPersons(int page) {
		try {
			return new ResponseArray(toJSON(makeApiCallGet(URLCreator.getChangedPersonsUrl(page))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseArray(Status.MALFORMED_URL);
		}
	}
	
	public static ResponseArray getChangedMovies(Date start, Date end) {
		return getChangedMovies(start, end, 1);
	}
	
	public static ResponseArray getChangedPersons(Date start, Date end) {
		return getChangedPersons(start, end, 1);
	}
	
	public static ResponseArray getChangedMovies(Date start, Date end, int page) {
		try {
			return new ResponseArray(toJSON(makeApiCallGet(URLCreator.getChangedMoviesUrl(start, end, page))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseArray(Status.MALFORMED_URL);
		}
	}
	
	public static ResponseArray getChangedPersons(Date start, Date end, int page) {
		try {
			return new ResponseArray(toJSON(makeApiCallGet(URLCreator.getChangedPersonsUrl(start, end, page))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseArray(Status.MALFORMED_URL);
		}
	}
	
	
	public static ResponseArray getChangedMovies(String start, String end) {
		return getChangedMovies(start, end, 1);
	}
	
	public static ResponseArray getChangedPersons(String start, String end) {
		return getChangedPersons(start, end, 1);
	}
	
	public static ResponseArray getChangedMovies(String start, String end, int page) {
		try {
			return new ResponseArray(toJSON(makeApiCallGet(URLCreator.getChangedMoviesUrl(start, end, page))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseArray(Status.MALFORMED_URL);
		}
	}
	
	public static ResponseArray getChangedPersons(String start, String end, int page) {
		try {
			return new ResponseArray(toJSON(makeApiCallGet(URLCreator.getChangedPersonsUrl(start, end, page))));
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			return new ResponseArray(Status.MALFORMED_URL);
		}
	}
	
	//endregion
	
}
