package mk.tmdb.core;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class provides methods for creating well formed query URL.
 * Before start to use this methods is needed setting the API Key otherwise
 * the URL will be wrong.
 * 
 * @author Mirko Polato
 *
 */
public class URLCreator {

	/**
	 * Request authorization base URL.
	 */
	private static final String REQUEST_AUTH_BASE_URL = "http://www.themoviedb.org/authenticate/";
	
	//region API Key
	
	/**
	 * The API Key.
	 */
	private static String apiKey = "";
	
	/**
	 * Gets the API Key.
	 * @return The query URL The API Key
	 */
	public static String getApiKey() {
		return apiKey;
	}

	/**
	 * Sets the API Key.
	 * @param key The new API Key
	 */
	public static void setApiKey(String key) {
		apiKey = key;
	}

	//endregion
	
	//region Utilities
	
	/**
	 * Forms a pair 'property=value'.
	 * 
	 * @param prop The property
	 * @param value The value
	 * @return The string 'prop=value'
	 */
	private static String pair(String prop, String value) {
		return prop + "=" + value;
	}
	
	/**
	 * Adds to the head of the given string a "&".
	 * 
	 * @param par The string
	 * @return The string with "&" symbol as first character
	 */
	private static String param(String par) {
		return "&" + par;
	}
	
	//endregion
	
	//region Configuration
	
	/**
	 * Returns the URL that gets the configuration information.
	 * 
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL getConfigurationUrl() throws MalformedURLException {
		return new URL(Constants.API_BASE_URL +
					  Constants.VERSION +
					  Constants.CONFIGURATION + 
					  pair(Constants.API_KEY, getApiKey()));
	}

	//endregion
	
	//region Authentication
	
	/**
	 * Returns the URL that performs the request authorization to the client.
	 * Open this URL in a web browser.
	 * 
	 * @param token The authentication token
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL getRequestAuthorizationUrl(String token) throws MalformedURLException {
		return new URL(REQUEST_AUTH_BASE_URL + token);
	}
	
	/**
	 * Returns the URL that generates a valid request token for user based authentication.
	 * 
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL getAuthTokenUrl() throws MalformedURLException {
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.GET_AUTH_TOKEN +
				  pair(Constants.API_KEY, getApiKey()));
	}

	/**
	 * Returns the URL that generates a session id for user based authentication.
	 * 
	 * @param token The authentication token
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL getAuthSessionUrl(String token) throws MalformedURLException {
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.GET_AUTH_SESSION +
				  pair(Constants.API_KEY, getApiKey()) +
				  param(pair(Constants.TOKEN, token)));
	}

	/**
	 * Returns the URL that generates a guest session id.
	 * 
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL getGuestSessionUrl() throws MalformedURLException {
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.GET_GUEST_SESSION +
				  pair(Constants.API_KEY, getApiKey()));
	}

	//endregion

	//region Account
	
	/**
	 * Returns the URL that gets the basic information for an account.
	 * 
	 * @param sessionID The session ID
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL getAccountInfoUrl(String sessionID) throws MalformedURLException {
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.ACCOUNT + 
				  pair(Constants.API_KEY, getApiKey()) +
				  param(pair(Constants.SESSION_ID, sessionID)));
	}

	/**
	 * Returns the URL that gets the lists that are marked as a favorite.
	 * 
	 * @param accountID The account ID
	 * @param sessionID The session ID
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL getAccountFavsListsUrl(int accountID, String sessionID) throws MalformedURLException {
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.ACCOUNT + Constants.SLASH +
				  accountID + Constants.SLASH +
				  Constants.LISTS +
				  pair(Constants.API_KEY, getApiKey()) +
				  param(pair(Constants.SESSION_ID, sessionID)));
	}
	
	/**
	 * Returns the URL that gets the lists that are marked as a favorite.
	 * 
	 * @param accountID The account ID
	 * @param sessionID The session ID
	 * @param page The page number
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL getAccountFavsListsUrl(int accountID, String sessionID, int page) throws MalformedURLException {
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.ACCOUNT + Constants.SLASH +
				  accountID + Constants.SLASH +
				  Constants.LISTS +
				  pair(Constants.API_KEY, getApiKey()) +
				  param(pair(Constants.SESSION_ID, sessionID)) +
				  param(pair(Constants.PAGE, String.valueOf(page))));
	}

	/**
	 * Returns the URL that gets the list of favorite movies for an account.
	 * 
	 * @param accountID The account ID
	 * @param sessionID The session ID
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL getAccountFavsMoviesUrl(int accountID, String sessionID) throws MalformedURLException {
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.ACCOUNT + Constants.SLASH +
				  accountID + Constants.SLASH +
				  Constants.FAVORITE_MOVIES +
				  pair(Constants.API_KEY, getApiKey()) +
				  param(pair(Constants.SESSION_ID, sessionID)));
	}
	
	/**
	 * Returns the URL that gets the list of favorite movies for an account.
	 * 
	 * @param accountID The account ID
	 * @param sessionID The session ID
	 * @param page The page number
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL getAccountFavsMoviesUrl(int accountID, String sessionID, int page) throws MalformedURLException {
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.ACCOUNT + Constants.SLASH +
				  accountID + Constants.SLASH +
				  Constants.FAVORITE_MOVIES +
				  pair(Constants.API_KEY, getApiKey()) +
				  param(pair(Constants.SESSION_ID, sessionID)) +
				  param(pair(Constants.PAGE, String.valueOf(page))));
	}	
	
	/**
	 * Returns the URL that adds a movie to an accounts favorite list.
	 * 
	 * @param accountID The account ID
	 * @param sessionID The session ID
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL addMovieToFavsUrl(int accountID, String sessionID) throws MalformedURLException {
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.ACCOUNT + Constants.SLASH +
				  accountID + Constants.SLASH +
				  Constants.FAVORITE +
				  pair(Constants.API_KEY, getApiKey()) +
				  param(pair(Constants.SESSION_ID, sessionID)));
	}

	/**
	 * Returns the URL that removes a movie from an accounts favorite list.
	 * 
	 * @param accountID The account ID
	 * @param sessionID The session ID
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL removeMovieFromFavsUrl(int accountID, String sessionID) throws MalformedURLException {
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.ACCOUNT + Constants.SLASH +
				  accountID + Constants.SLASH +
				  Constants.FAVORITE +
				  pair(Constants.API_KEY, getApiKey()) +
				  param(pair(Constants.SESSION_ID, sessionID)));
	}

	/**
	 * Returns the URL that gets the list of rated movies for an account.
	 * 
	 * @param accountID The account ID
	 * @param sessionID The session ID
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL getRatedMoviesUrl(int accountID, String sessionID) throws MalformedURLException {
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.ACCOUNT + Constants.SLASH +
				  accountID + Constants.SLASH +
				  Constants.RATED_MOVIES +
				  pair(Constants.API_KEY, getApiKey()) +
				  param(pair(Constants.SESSION_ID, sessionID)));
	}

	/**
	 * Returns the URL that gets the list of rated movies for an account.
	 * 
	 * @param accountID The account ID
	 * @param sessionID The session ID
	 * @param page The page number
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL getRatedMoviesUrl(int accountID, String sessionID, int page) throws MalformedURLException {
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.ACCOUNT + Constants.SLASH +
				  accountID + Constants.SLASH +
				  Constants.RATED_MOVIES +
				  pair(Constants.API_KEY, getApiKey()) +
				  param(pair(Constants.SESSION_ID, sessionID)) +
				  param(pair(Constants.PAGE, String.valueOf(page))));
	}
	
	/**
	 * Returns the URL that gets the list of movies on an accounts watch list.
	 * 
	 * @param accountID The account ID
	 * @param sessionID The session ID
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL getWatchlistUrl(int accountID, String sessionID) throws MalformedURLException {
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.ACCOUNT + Constants.SLASH +
				  accountID + Constants.SLASH +
				  Constants.WATCHLIST +
				  pair(Constants.API_KEY, getApiKey()) +
				  param(pair(Constants.SESSION_ID, sessionID)));
	}

	/**
	 * Returns the URL that gets the list of movies on an accounts watch list.
	 * 
	 * @param accountID The account ID
	 * @param sessionID The session ID
	 * @param page The page number
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL getWatchlistUrl(int accountID, String sessionID, int page) throws MalformedURLException {
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.ACCOUNT + Constants.SLASH +
				  accountID + Constants.SLASH +
				  Constants.WATCHLIST +
				  pair(Constants.API_KEY, getApiKey()) +
				  param(pair(Constants.SESSION_ID, sessionID)) +
				  param(pair(Constants.PAGE, String.valueOf(page))));
	}
	
	/**
	 * Returns the URL that adds a movie to an accounts watch list.
	 * 
	 * @param accountID The account ID
	 * @param sessionID The session ID
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL addMovieToWatchlistUrl(int accountID, String sessionID) throws MalformedURLException {
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.ACCOUNT + Constants.SLASH +
				  accountID + Constants.SLASH +
				  Constants.WATCHLIST +
				  pair(Constants.API_KEY, getApiKey()) +
				  param(pair(Constants.SESSION_ID, sessionID)));
	}
	
	/**
	 * Returns the URL that removes movie from an accounts watch list.
	 * 
	 * @param accountID The account ID
	 * @param sessionID The session ID
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL removeMovieFromWatchlistUrl(int accountID, String sessionID) throws MalformedURLException {
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.ACCOUNT + Constants.SLASH +
				  accountID + Constants.SLASH +
				  Constants.WATCHLIST +
				  pair(Constants.API_KEY, getApiKey()) +
				  param(pair(Constants.SESSION_ID, sessionID)));
	}
	
	//endregion

	//region Movie
	
	/**
	 * Returns the URL that gets the basic movie information for a specific movie id.
	 * 
	 * @param movieID The movie ID
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL getMovieInfoUrl(int movieID) throws MalformedURLException {
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.MOVIE + Constants.SLASH +
				  movieID +
				  pair(Constants.API_KEY, getApiKey()));
	}

	/**
	 * Returns the URL that gets the cast information for a specific movie id.
	 * 
	 * @param movieID The movie ID
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL getCastInfoUrl(int movieID) throws MalformedURLException {
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.MOVIE + Constants.SLASH +
				  movieID + Constants.SLASH +
				  Constants.CASTS +
				  pair(Constants.API_KEY, getApiKey()));
	}

	/**
	 * Returns the URL that gets the images (posters and backdrops) for a specific movie id.
	 * 
	 * @param movieID The movie ID
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL getMovieImagesUrl(int movieID) throws MalformedURLException {
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.MOVIE + Constants.SLASH +
				  movieID + Constants.SLASH +
				  Constants.IMAGES +
				  pair(Constants.API_KEY, getApiKey()));
	}

	/**
	 * Returns the URL that gets the alternative titles for a specific movie id.
	 * 
	 * @param movieID The movie ID
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL getAlternativeMovieTitlesUrl(int movieID) throws MalformedURLException {
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.MOVIE + Constants.SLASH +
				  movieID + Constants.SLASH +
				  Constants.ALT_TITLES +
				  pair(Constants.API_KEY, getApiKey()));
	}
	
	/**
	 * Returns the URL that gets the plot keywords for a specific movie id.
	 * 
	 * @param movieID The movie ID
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL getMovieKeywordsUrl(int movieID) throws MalformedURLException {
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.MOVIE + Constants.SLASH +
				  movieID + Constants.SLASH +
				  Constants.KEYWORDS +
				  pair(Constants.API_KEY, getApiKey()));
	}

	/**
	 * Returns the URL that gets the release date by country for a specific movie id.
	 * 
	 * @param movieID The movie ID
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL getMovieReleasesDatesUrl(int movieID) throws MalformedURLException {
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.MOVIE + Constants.SLASH +
				  movieID + Constants.SLASH +
				  Constants.RELEASE_DATE +
				  pair(Constants.API_KEY, getApiKey()));
	}

	/**
	 * Returns the URL that gets the trailers for a specific movie id.
	 * 
	 * @param movieID The movie ID
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL getMovieTrailersUrl(int movieID) throws MalformedURLException {
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.MOVIE + Constants.SLASH +
				  movieID + Constants.SLASH +
				  Constants.TRAILERS +
				  pair(Constants.API_KEY, getApiKey()));
	}

	/**
	 * Returns the URL that gets the translations for a specific movie id.
	 * 
	 * @param movieID The movie ID
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL getMovieTranslationsUrl(int movieID) throws MalformedURLException {
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.MOVIE + Constants.SLASH +
				  movieID + Constants.SLASH +
				  Constants.TRANSLATIONS +
				  pair(Constants.API_KEY, getApiKey()));
	}

	/**
	 * Returns the URL that gets the similar movies for a specific movie id.
	 * 
	 * @param movieID The movie ID
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL getSimilarMoviesUrl(int movieID) throws MalformedURLException {
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.MOVIE + Constants.SLASH +
				  movieID + Constants.SLASH +
				  Constants.SIMILAR +
				  pair(Constants.API_KEY, getApiKey()));
	}

	/**
	 * Returns the URL that gets the similar movies for a specific movie id.
	 * 
	 * @param movieID The movie ID
	 * @param page The page number
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL getSimilarMoviesUrl(int movieID, int page) throws MalformedURLException {
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.MOVIE + Constants.SLASH +
				  movieID + Constants.SLASH +
				  Constants.SIMILAR +
				  pair(Constants.API_KEY, getApiKey()) +
				  param(pair(Constants.PAGE, String.valueOf(page))));
	}
	
	/**
	 * Returns the URL that gets the changes for a specific movie id.
	 * 
	 * @param movieID The movie ID
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL getMovieChangesUrl(int movieID) throws MalformedURLException {
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.MOVIE + Constants.SLASH +
				  movieID + Constants.SLASH +
				  Constants.CHANGES +
				  pair(Constants.API_KEY, getApiKey()));
	}

	/**
	 * Returns the URL that gets the changes for a specific movie id.
	 * 
	 * @param movieID The movie ID
	 * @param start The date where the search starts 
	 * @param end The date where the search ends 
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL getMovieChangesUrl(int movieID, Date start, Date end) throws MalformedURLException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String startStr = dateFormat.format(start);
		String endStr = dateFormat.format(end);
		
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.MOVIE + Constants.SLASH +
				  movieID + Constants.SLASH +
				  Constants.CHANGES +
				  pair(Constants.API_KEY, getApiKey()) +
				  param(pair(Constants.START_DATE, startStr)) +
				  param(pair(Constants.END_DATE, endStr)));
	}
	
	/**
	 * Returns the URL that gets the changes for a specific movie id.
	 * 
	 * @param movieID The movie ID
	 * @param start The date where the search starts 
	 * @param end The date where the search ends 
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL getMovieChangesUrl(int movieID, String start, String end) throws MalformedURLException {
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.MOVIE + Constants.SLASH +
				  movieID + Constants.SLASH +
				  Constants.CHANGES +
				  pair(Constants.API_KEY, getApiKey()) +
				  param(pair(Constants.START_DATE, start)) +
				  param(pair(Constants.END_DATE, end)));
	}
	
	/**
	 * Returns the URL that gets the latest movie id.
	 * 
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL getLatestMovieUrl() throws MalformedURLException {
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.MOVIE + Constants.SLASH +
				  Constants.LATEST +
				  pair(Constants.API_KEY, getApiKey()));
	}

	/**
	 * Returns the URL that gets the list of upcoming movies.
	 * 
	 * @param page The page number
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL getUpcomingMoviesListUrl(int page) throws MalformedURLException {
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.MOVIE + Constants.SLASH +
				  Constants.MOVIES_UPCOMING +
				  pair(Constants.API_KEY, getApiKey()) + 
				  param(pair(Constants.PAGE, String.valueOf(page))));
	}

	/**
	 * Returns the URL that gets the list of upcoming movies.
	 * 
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL getUpcomingMoviesListUrl() throws MalformedURLException {
		return getUpcomingMoviesListUrl(1);
	}

	/**
	 * Returns the URL that gets the list of movies playing in theatres.
	 * 
	 * @param page The page number
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL getInTheatresMoviesUrl(int page) throws MalformedURLException {
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.MOVIE + Constants.SLASH +
				  Constants.MOVIES_INTHEATRE +
				  pair(Constants.API_KEY, getApiKey()) + 
				  param(pair(Constants.PAGE, String.valueOf(page))));
	}

	/**
	 * Returns the URL that gets the list of movies playing in theatres.
	 * 
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL getInTheatresMoviesUrl() throws MalformedURLException {
		return getInTheatresMoviesUrl(1);
	}

	/**
	 * Returns the URL that gets the list of popular movies on The Movie Database.
	 * 
	 * @param page The page number
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL getPopularMoviesUrl(int page) throws MalformedURLException {
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.MOVIE + Constants.SLASH +
				  Constants.POPULAR_MOVIES +
				  pair(Constants.API_KEY, getApiKey()) + 
				  param(pair(Constants.PAGE, String.valueOf(page))));
	}

	/**
	 * Returns the URL that gets the list of popular movies on The Movie Database.
	 * 
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL getPopularMoviesUrl() throws MalformedURLException {
		return getPopularMoviesUrl(1);
	}

	/**
	 * Returns the URL that gets the list of top rated movies.
	 * 
	 * @param page The page number
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL getTopRatedMoviesUrl(int page) throws MalformedURLException {
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.MOVIE + Constants.SLASH +
				  Constants.TOPRATED_MOVIES +
				  pair(Constants.API_KEY, getApiKey()) + 
				  param(pair(Constants.PAGE, String.valueOf(page))));
	}

	/**
	 * Returns the URL that gets the list of top rated movies.
	 * 
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL getTopRatedMoviesUrl() throws MalformedURLException {
		return getTopRatedMoviesUrl(1);
	}

	/**
	 * Returns the URL that lets users rate a movie.
	 * 
	 * @param sessionID The session ID
	 * @param movieID The movie ID
	 * @param guest Whether the session is a guest one
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL setMovieRateUrl(String sessionID, int movieID, boolean guest) throws MalformedURLException {
		String session = Constants.SESSION_ID; 
		if (guest) session = Constants.GUEST_ID;
		
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.MOVIE + Constants.SLASH +
				  movieID + Constants.SLASH +
				  Constants.RATING +
				  pair(Constants.API_KEY, getApiKey()) + 
				  param(pair(session, sessionID)));
	}
	
	/**
	 * Returns the URL that gets the lists that the movie belongs to.
	 * 
	 * @param movieID The movie ID
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL getListsBelongsToMovieUrl(int movieID) throws MalformedURLException {
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.MOVIE + Constants.SLASH +
				  Constants.LISTS +
				  pair(Constants.API_KEY, getApiKey()));
	}
	
	/**
	 * Returns the URL that gets the lists that the movie belongs to.
	 * 
	 * @param movieID The movie ID
	 * @param page The page number
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL getListsBelongsToMovieUrl(int movieID, int page) throws MalformedURLException {
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.MOVIE + Constants.SLASH +
				  Constants.LISTS +
				  pair(Constants.API_KEY, getApiKey()) +
				  param(pair(Constants.PAGE, String.valueOf(page))));
	}
	
	//endregion

	//region Collections
	
	/**
	 * Returns the URL that gets the basic collection information for a specific collection id.
	 * 
	 * @param collectionID The collection ID
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form 
	 */
	public static URL getCollectionInfoUrl(int collectionID) throws MalformedURLException {
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.COLLECTION + Constants.SLASH +
				  collectionID +
				  pair(Constants.API_KEY, getApiKey()));
	}
	
	/**
	 * Returns the URL that gets all of the images for a particular collection by collection id.
	 * 
	 * @param collectionID The collection ID
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form 
	 */
	public static URL getCollectionImagesUrl(int collectionID) throws MalformedURLException {
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.COLLECTION + Constants.SLASH +
				  collectionID + Constants.SLASH +
				  Constants.IMAGES +
				  pair(Constants.API_KEY, getApiKey()));
	}
	
	//endregion
	
	//region List
	
	/**
	 * Returns the URL that gets a list by id.
	 * 
	 * @param listID The list ID
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form 
	 */
	public static URL getListUrl(String listID) throws MalformedURLException {
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.LIST + Constants.SLASH +
				  listID +
				  pair(Constants.API_KEY, getApiKey()));
	}
	
	//endregion
	
	//region People

	/**
	 * Returns the URL that gets the general person information for a specific id.
	 * 
	 * @param personID The person ID
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL getPersonInfoUrl(int personID) throws MalformedURLException {
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.PERSON + Constants.SLASH +
				  personID +
				  pair(Constants.API_KEY, getApiKey()));
	}

	/**
	 * Returns the URL that gets the credits for a specific person id.
	 * 
	 * @param personID The person ID
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL getPersonCreditsUrl(int personID) throws MalformedURLException {
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.PERSON + Constants.SLASH +
				  personID + Constants.SLASH +
				  Constants.CREDITS +
				  pair(Constants.API_KEY, getApiKey()));
	}

	/**
	 * Returns the URL that gets the images for a specific person id.
	 * 
	 * @param personID The person ID
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL getPersonImagesUrl(int personID) throws MalformedURLException {
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.PERSON + Constants.SLASH +
				  personID + Constants.SLASH +
				  Constants.IMAGES +
				  pair(Constants.API_KEY, getApiKey()));
	}

	/**
	 * Returns the URL that gets the changes for a specific person id.
	 * 
	 * @param personID The person ID
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL getPersonChangesUrl(int personID) throws MalformedURLException {
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.PERSON + Constants.SLASH +
				  personID + Constants.SLASH +
				  Constants.CHANGES +
				  pair(Constants.API_KEY, getApiKey()));
	}
	
	/**
	 * Returns the URL that gets the changes for a specific person id.
	 * 
	 * @param personID The person ID
	 * @param start The date where the search starts 
	 * @param end The date where the search ends 
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL getPersonChangesUrl(int personID, String start, String end) throws MalformedURLException {
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.PERSON + Constants.SLASH +
				  personID + Constants.SLASH +
				  Constants.CHANGES +
				  pair(Constants.API_KEY, getApiKey())+
				  param(pair(Constants.START_DATE, start)) +
				  param(pair(Constants.END_DATE, end)));
	}
	
	/**
	 * Returns the URL that gets the changes for a specific person id.
	 * 
	 * @param personID The person ID
	 * @param start The date where the search starts 
	 * @param end The date where the search ends 
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL getPersonChangesUrl(int personID, Date start, Date end) throws MalformedURLException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String startStr = dateFormat.format(start);
		String endStr = dateFormat.format(end);
		
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.PERSON + Constants.SLASH +
				  personID + Constants.SLASH +
				  Constants.CHANGES +
				  pair(Constants.API_KEY, getApiKey())+
				  param(pair(Constants.START_DATE, startStr)) +
				  param(pair(Constants.END_DATE, endStr)));
	}

	/**
	 * Returns the URL that gets the latest person id.
	 * 
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL getLatestPerson() throws MalformedURLException {
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.PERSON + Constants.SLASH +
				  Constants.LATEST +
				  pair(Constants.API_KEY, getApiKey()));
	}

	//endregion
	
	//region Company
	
	/**
	 * Returns the URL that retrieves all of the basic information about a company.
	 * 
	 * @param companyID The company ID
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL getCompanyInfoUrl(int companyID) throws MalformedURLException {
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.COMPANY + Constants.SLASH +
				  companyID + Constants.SLASH +
				  Constants.CREDITS +
				  pair(Constants.API_KEY, getApiKey()));
	}

	/**
	 * Returns the URL that gets the list of movies associated with a particular company.
	 * 
	 * @param companyID The company ID
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL getMoviesListByCompanyUrl(int companyID) throws MalformedURLException {
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.COMPANY + Constants.SLASH +
				  companyID + Constants.SLASH +
				  Constants.MOVIES +
				  pair(Constants.API_KEY, getApiKey()));
	}
	
	/**
	 * Returns the URL that gets the list of movies associated with a particular company.
	 * 
	 * @param companyID The company ID
	 * @param page The page number
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL getMoviesListByCompanyUrl(int companyID, int page) throws MalformedURLException {
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.COMPANY + Constants.SLASH +
				  companyID + Constants.SLASH +
				  Constants.MOVIES +
				  pair(Constants.API_KEY, getApiKey()) +
				  param(pair(Constants.PAGE, String.valueOf(page))));
	}

	//endregion
	
	//region Genres
	
	/**
	 * Returns the URL that gets the list of genres.
	 * 
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL getGenresListUrl() throws MalformedURLException {
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.GENRE + Constants.SLASH +
				  Constants.LIST +
				  pair(Constants.API_KEY, getApiKey()));
	}

	/**
	 * Returns the URL that gets the list of movies for a particular genre by id.
	 * 
	 * @param genreID The genre ID
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL getMoviesListByGenreUrl(int genreID) throws MalformedURLException {
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.GENRE + Constants.SLASH +
				  genreID + Constants.SLASH +
				  Constants.MOVIES +
				  pair(Constants.API_KEY, getApiKey()));
	}
	
	/**
	 * Returns the URL that gets the list of movies for a particular genre by id.
	 * 
	 * @param genreID The genre ID
	 * @param page The page number
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL getMoviesListByGenreUrl(int genreID, int page) throws MalformedURLException {
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.GENRE + Constants.SLASH +
				  genreID + Constants.SLASH +
				  Constants.MOVIES +
				  pair(Constants.API_KEY, getApiKey()) +
				  param(pair(Constants.PAGE, String.valueOf(page))));
	}

	//endregion
	
	//region Keywords
	
	/**
	 * Returns the URL that gets the basic information for a specific keyword id.
	 * 
	 * @param keywordID The keyword ID
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL getKeywordInformation(int keywordID) throws MalformedURLException {
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.KEYWORD + Constants.SLASH +
				  keywordID +
				  pair(Constants.API_KEY, getApiKey()));
	}
	
	/**
	 * Return the URL that gets the list of movies for a particular keyword by id.
	 * 
	 * @param keywordID The keyword ID
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL getMoviesListByKeyword(int keywordID) throws MalformedURLException {
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.KEYWORD + Constants.SLASH +
				  keywordID + Constants.SLASH +
				  Constants.MOVIE +
				  pair(Constants.API_KEY, getApiKey()));
	}
	
	/**
	 * Return the URL that gets the list of movies for a particular keyword by id.
	 * 
	 * @param keywordID The keyword ID
	 * @param page The page number to retrieve
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL getMoviesListByKeyword(int keywordID, int page) throws MalformedURLException {
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.KEYWORD + Constants.SLASH +
				  keywordID + Constants.SLASH +
				  Constants.MOVIE +
				  pair(Constants.API_KEY, getApiKey()) +
				  param(pair(Constants.PAGE, String.valueOf(page))));
	}
	
	//endregion
	
	//region Search
	
	/**
	 * Returns the URL that searches for movies by title.
	 * 
	 * @param movieTitle The movie's title
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL searchMovieByTitleUrl(String movieTitle) throws MalformedURLException {
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.SEARCH +
				  Constants.MOVIE +
				  pair(Constants.API_KEY, getApiKey()) +
				  param(pair(Constants.QUERY, movieTitle)));
	}
	
	/**
	 * Returns the URL that searches for movies by title.
	 * 
	 * @param movieTitle The movie's title
	 * @param page The page number
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL searchMovieByTitleUrl(String movieTitle, int page) throws MalformedURLException {
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.SEARCH +
				  Constants.MOVIE +
				  pair(Constants.API_KEY, getApiKey()) +
				  param(pair(Constants.QUERY, movieTitle)) + 
				  param(pair(Constants.PAGE, String.valueOf(page))));
	}

	/**
	 * Returns the URL that searches for movies by title and year.
	 * 
	 * @param movieTitle The movie's title
	 * @param year The year of the movie
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL searchMovieByTitleAndYearUrl(String movieTitle, int year) throws MalformedURLException {
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.SEARCH + 
				  Constants.MOVIE +
				  pair(Constants.API_KEY, getApiKey()) +
				  param(pair(Constants.QUERY, movieTitle))+
				  param(pair(Constants.YEAR, String.valueOf(year))));
	}
	
	/**
	 * Returns the URL that searches for movies by title and year.
	 * 
	 * @param movieTitle The movie's title
	 * @param year The year of the movie
	 * @param page The page number
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL searchMovieByTitleAndYearUrl(String movieTitle, int year, int page) throws MalformedURLException {
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.SEARCH + 
				  Constants.MOVIE +
				  pair(Constants.API_KEY, getApiKey()) +
				  param(pair(Constants.QUERY, movieTitle))+
				  param(pair(Constants.YEAR, String.valueOf(year))) +
				  param(pair(Constants.PAGE, String.valueOf(page))));
	}

	/**
	 * Returns the URL that searches for movies by title and audience type.
	 * 
	 * @param movieTitle The movie's title
	 * @param adult Whether the audience is adult only
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL searchMovieByTitleUrl(String movieTitle, boolean adult) throws MalformedURLException {
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.SEARCH + 
				  Constants.MOVIE +
				  pair(Constants.API_KEY, getApiKey()) +
				  param(pair(Constants.QUERY, movieTitle))+
				  param(pair(Constants.INCLUDE_ADULT, String.valueOf(adult))));
	}
	
	/**
	 * Returns the URL that searches for movies by title and audience type.
	 * 
	 * @param movieTitle The movie's title
	 * @param adult Whether the audience is adult only
	 * @param page The page number
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL searchMovieByTitleUrl(String movieTitle, boolean adult, int page) throws MalformedURLException {
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.SEARCH + 
				  Constants.MOVIE +
				  pair(Constants.API_KEY, getApiKey()) +
				  param(pair(Constants.QUERY, movieTitle))+
				  param(pair(Constants.INCLUDE_ADULT, String.valueOf(adult))) +
				  param(pair(Constants.PAGE, String.valueOf(page))));
	}

	/**
	 * Returns the URL that searches for movies by title, year and audience type.
	 * 
	 * @param movieTitle The movie's title
	 * @param year The year of the movie
	 * @param adult Whether the audience is adult only
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL searchMovieByTitleUrl(String movieTitle, int year, boolean adult) throws MalformedURLException {
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.SEARCH + 
				  Constants.MOVIE +
				  pair(Constants.API_KEY, getApiKey()) +
				  param(pair(Constants.QUERY, movieTitle))+
				  param(pair(Constants.YEAR, String.valueOf(year))) +
				  param(pair(Constants.INCLUDE_ADULT, String.valueOf(adult))));
	}
	
	/**
	 * Returns the URL that searches for movies by title, year and audience type.
	 * 
	 * @param movieTitle The movie's title
	 * @param year The year of the movie
	 * @param adult Whether the audience is adult only
	 * @param page The page number
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL searchMovieByTitleUrl(String movieTitle, int year, boolean adult, int page) throws MalformedURLException {
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.SEARCH + 
				  Constants.MOVIE +
				  pair(Constants.API_KEY, getApiKey()) +
				  param(pair(Constants.QUERY, movieTitle))+
				  param(pair(Constants.YEAR, String.valueOf(year))) +
				  param(pair(Constants.INCLUDE_ADULT, String.valueOf(adult))) +
				  param(pair(Constants.PAGE, String.valueOf(page))));
	}

	/**
	 * Returns the URL that searches for people by name.
	 * 
	 * @param name The person name
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL searchPeopleByNameUrl(String name) throws MalformedURLException {
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.SEARCH + 
				  Constants.PERSON +
				  pair(Constants.API_KEY, getApiKey()) +
				  param(pair(Constants.QUERY, name)));
	}

	/**
	 * Returns the URL that searches for people by name.
	 * 
	 * @param name The person name
	 * @param page The page number
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL searchPeopleByNameUrl(String name, int page) throws MalformedURLException {
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.SEARCH + 
				  Constants.PERSON +
				  pair(Constants.API_KEY, getApiKey()) +
				  param(pair(Constants.QUERY, name)) +
				  param(pair(Constants.PAGE, String.valueOf(page))));
	}
	
	/**
	 * Returns the URL that searches for people by name and audience type.
	 * 
	 * @param name The person name
	 * @param adult Whether the audience is adult only
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL searchPeopleByNameUrl(String name, boolean adult) throws MalformedURLException {
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.SEARCH + 
				  Constants.PERSON +
				  pair(Constants.API_KEY, getApiKey()) +
				  param(pair(Constants.QUERY, name)) +
				  param(pair(Constants.INCLUDE_ADULT, String.valueOf(adult))));
	}
	
	/**
	 * Returns the URL that searches for people by name and audience type.
	 * 
	 * @param name The person name
	 * @param adult Whether the audience is adult only
	 * @param page The page number
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL searchPeopleByNameUrl(String name, boolean adult, int page) throws MalformedURLException {
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.SEARCH + 
				  Constants.PERSON +
				  pair(Constants.API_KEY, getApiKey()) +
				  param(pair(Constants.QUERY, name)) +
				  param(pair(Constants.INCLUDE_ADULT, String.valueOf(adult))) +
				  param(pair(Constants.PAGE, String.valueOf(page))));
	}

	/**
	 * Returns the URL that searches for companies by name.
	 * 
	 * @param name The company name
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL searchCompanyByNameUrl(String name) throws MalformedURLException {
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.SEARCH +
				  Constants.COMPANY +
				  pair(Constants.API_KEY, getApiKey()) +
				  param(pair(Constants.QUERY, name)));
	}

	/**
	 * Returns the URL that searches for companies by name.
	 * 
	 * @param name The company name
	 * @param page The page number
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL searchCompanyByNameUrl(String name, int page) throws MalformedURLException {
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.SEARCH +
				  Constants.COMPANY +
				  pair(Constants.API_KEY, getApiKey()) +
				  param(pair(Constants.QUERY, name)) +
				  param(pair(Constants.PAGE, String.valueOf(page))));
	}
	
	/**
	 * Returns the URL that searches for collections by name.
	 * 
	 * @param name The collection name
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL searchCollectionByNameUrl(String name) throws MalformedURLException {
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.SEARCH +
				  Constants.COLLECTION +
				  pair(Constants.API_KEY, getApiKey()) +
				  param(pair(Constants.QUERY, name)));
	}
	
	/**
	 * Returns the URL that searches for collections by name.
	 * 
	 * @param name The collection name
	 * @param page The page number
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL searchCollectionByNameUrl(String name, int page) throws MalformedURLException {
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.SEARCH +
				  Constants.COLLECTION +
				  pair(Constants.API_KEY, getApiKey()) +
				  param(pair(Constants.QUERY, name)) +
				  param(pair(Constants.PAGE, String.valueOf(page))));
	}
	
	/**
	 * Returns the URL that searches for lists by name.
	 * 
	 * @param name The list name
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL searchListByNameUrl(String name) throws MalformedURLException {
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.SEARCH +
				  Constants.LIST +
				  pair(Constants.API_KEY, getApiKey()) +
				  param(pair(Constants.QUERY, name)));
	}
	
	/**
	 * Returns the URL that searches for lists by name.
	 * 
	 * @param name The list name
	 * @param page The page number
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL searchListByNameUrl(String name, int page) throws MalformedURLException {
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.SEARCH +
				  Constants.LIST +
				  pair(Constants.API_KEY, getApiKey()) +
				  param(pair(Constants.QUERY, name)) +
				  param(pair(Constants.PAGE, String.valueOf(page))));
	}
	
	/**
	 * Returns the URL that searches for keywords by name.
	 * 
	 * @param name The keyword string/sub string
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL searchKeywordByNameUrl(String name) throws MalformedURLException {
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.SEARCH +
				  Constants.KEYWORD +
				  pair(Constants.API_KEY, getApiKey()) +
				  param(pair(Constants.QUERY, name)));
	}
	
	/**
	 * Returns the URL that searches for keywords by name.
	 * 
	 * @param name The keyword string/sub string
	 * @param page The page number to retrieve
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL searchKeywordByNameUrl(String name, int page) throws MalformedURLException {
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.SEARCH +
				  Constants.KEYWORD +
				  pair(Constants.API_KEY, getApiKey()) +
				  param(pair(Constants.QUERY, name)) +
				  param(pair(Constants.PAGE, String.valueOf(page))));
	}
	
	//endregion
	
	//region Changes
	
	/**
	 * Returns the URL that gets a list of movie ids that have been edited. 
	 * 
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL getChangedMoviesUrl() throws MalformedURLException {
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.MOVIE + Constants.SLASH +
				  Constants.CHANGES +
				  pair(Constants.API_KEY, getApiKey()));
	}

	/**
	 * Returns the URL that gets a list of people ids that have been edited. 
	 * 
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL getChangedPersonsUrl() throws MalformedURLException {
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.PERSON + Constants.SLASH +
				  Constants.CHANGES +
				  pair(Constants.API_KEY, getApiKey()));
	}

	/**
	 * Returns the URL that gets a list of movie ids that have been edited.
	 *  
	 * @param page The page number
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL getChangedMoviesUrl(int page) throws MalformedURLException {
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.MOVIE + Constants.SLASH +
				  Constants.CHANGES +
				  pair(Constants.API_KEY, getApiKey()) +
				  param(pair(Constants.PAGE, String.valueOf(page))));
	}

	/**
	 * Returns the URL that gets a list of people ids that have been edited.
	 * 
	 * @param page The page number 
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL getChangedPersonsUrl(int page) throws MalformedURLException {
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.PERSON + Constants.SLASH +
				  Constants.CHANGES +
				  pair(Constants.API_KEY, getApiKey()) +
				  param(pair(Constants.PAGE, String.valueOf(page))));
	}
	
	/**
	 * Returns the URL that gets a list of movie ids that have been edited.
	 * 
	 * @param start The date where the search starts 
	 * @param end The date where the search ends 
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL getChangedMoviesUrl(Date start, Date end) throws MalformedURLException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String startStr = dateFormat.format(start);
		String endStr = dateFormat.format(end);
		
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.MOVIE + Constants.SLASH +
				  Constants.CHANGES +
				  pair(Constants.API_KEY, getApiKey()) +
				  param(pair(Constants.START_DATE, startStr)) + 
				  param(pair(Constants.END_DATE, endStr)));
	}

	/**
	 * Returns the URL that gets a list of people ids that have been edited.
	 * 
	 * @param start The date where the search starts 
	 * @param end The date where the search ends 
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL getChangedPersonsUrl(Date start, Date end) throws MalformedURLException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String startStr = dateFormat.format(start);
		String endStr = dateFormat.format(end);
		
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.PERSON + Constants.SLASH +
				  Constants.CHANGES +
				  pair(Constants.API_KEY, getApiKey()) +
				  param(pair(Constants.START_DATE, startStr)) + 
				  param(pair(Constants.END_DATE, endStr)));
	}
	
	/**
	 * Returns the URL that gets a list of movie ids that have been edited.
	 * 
	 * @param start The date where the search starts 
	 * @param end The date where the search ends  
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL getChangedMoviesUrl(String start, String end) throws MalformedURLException {
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.MOVIE + Constants.SLASH +
				  Constants.CHANGES +
				  pair(Constants.API_KEY, getApiKey()) +
				  param(pair(Constants.START_DATE, start)) + 
				  param(pair(Constants.END_DATE, end)));
	}

	/**
	 * Returns the URL that gets a list of people ids that have been edited.
	 *  
	 * @param start The date where the search starts 
	 * @param end The date where the search ends 
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL getChangedPersonsUrl(String start, String end) throws MalformedURLException {
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.PERSON + Constants.SLASH +
				  Constants.CHANGES +
				  pair(Constants.API_KEY, getApiKey()) +
				  param(pair(Constants.START_DATE, start)) + 
				  param(pair(Constants.END_DATE, end)));
	}
	
	/**
	 * Returns the URL that gets a list of movie ids that have been edited.
	 * 
	 * @param start The date where the search starts 
	 * @param end The date where the search ends 
	 * @param page The page number 
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL getChangedMoviesUrl(Date start, Date end, int page) throws MalformedURLException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String startStr = dateFormat.format(start);
		String endStr = dateFormat.format(end);
		
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.MOVIE + Constants.SLASH +
				  Constants.CHANGES +
				  pair(Constants.API_KEY, getApiKey()) +
				  param(pair(Constants.START_DATE, startStr)) + 
				  param(pair(Constants.END_DATE, endStr)) +
				  param(pair(Constants.PAGE, String.valueOf(page))));
	}

	/**
	 * Returns the URL that gets a list of people ids that have been edited.
	 * 
	 * @param start The date where the search starts 
	 * @param end The date where the search ends 
	 * @param page The page number 
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL getChangedPersonsUrl(Date start, Date end, int page) throws MalformedURLException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String startStr = dateFormat.format(start);
		String endStr = dateFormat.format(end);
		
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.PERSON + Constants.SLASH +
				  Constants.CHANGES +
				  pair(Constants.API_KEY, getApiKey()) +
				  param(pair(Constants.START_DATE, startStr)) + 
				  param(pair(Constants.END_DATE, endStr)) +
				  param(pair(Constants.PAGE, String.valueOf(page))));
	}
	
	/**
	 * Returns the URL that gets a list of movie ids that have been edited.
	 * 
	 * @param start The date where the search starts 
	 * @param end The date where the search ends  
	 * @param page The page number 
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL getChangedMoviesUrl(String start, String end, int page) throws MalformedURLException {
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.MOVIE + Constants.SLASH +
				  Constants.CHANGES +
				  pair(Constants.API_KEY, getApiKey()) +
				  param(pair(Constants.START_DATE, start)) + 
				  param(pair(Constants.END_DATE, end)) +
				  param(pair(Constants.PAGE, String.valueOf(page))));
	}

	/**
	 * Returns the URL that gets a list of people ids that have been edited. 
	 * 
	 * @param start The date where the search starts 
	 * @param end The date where the search ends 
	 * @param page The page number 
	 * @return The query URL
	 * @throws MalformedURLException Throws if the URL has an invalid form
	 */
	public static URL getChangedPersonsUrl(String start, String end, int page) throws MalformedURLException {
		return new URL(Constants.API_BASE_URL +
				  Constants.VERSION +
				  Constants.PERSON + Constants.SLASH +
				  Constants.CHANGES +
				  pair(Constants.API_KEY, getApiKey()) +
				  param(pair(Constants.START_DATE, start)) + 
				  param(pair(Constants.END_DATE, end)) +
				  param(pair(Constants.PAGE, String.valueOf(page))));
	}
	
	//endregion
	
}
