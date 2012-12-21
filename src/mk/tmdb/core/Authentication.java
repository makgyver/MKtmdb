package mk.tmdb.core;

import java.awt.Desktop;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

import mk.tmdb.entity.Token;
import mk.tmdb.exception.ResponseException;
import mk.tmdb.utils.Log;
import mk.tmdb.utils.ResponseObject;
import mk.tmdb.utils.Status;

/**
 * This class provides methods to authenticate to the TMDB site.
 * 
 * @author Mirko Polato
 *
 */
public class Authentication {

	/**
	 * This method is used to generate a valid request token for user based authentication. 
	 * A request token is required in order to request a session id. 
	 * You can generate any number of request tokens but they will expire after 60 minutes. 
	 * As soon as a valid session id has been created the token will be destroyed.
	 * 
	 * @return The authentication token
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
	public static Token getNewToken() throws ResponseException {
		
		ResponseObject response = TMDbAPI.getAuthenticationToken();
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			return new Token(response.getData());
		}
	}
	
	/**
	 * Opens the request authorization web page in the default browser.
	 * 
	 * @param token The authentication token
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
	public static void showRequestAuthorization(Token token) throws ResponseException {
		try {
			
			URI url = new URI(URLCreator.getRequestAuthorizationUrl(token.getValue()).toString());
			Desktop.getDesktop().browse(url);
			
		} catch (MalformedURLException e) {
			Log.print(e);
			
			throw new ResponseException(Status.MALFORMED_URL);
		} catch (URISyntaxException e) {
			Log.print(e);
			
			throw new ResponseException(Status.MALFORMED_URL);
		} catch (IOException e) {
			Log.print(e);
			
			throw new ResponseException(Status.UNKNOWN_ERROR);
		}
	}
	
	/**
	 * This method is used to generate a session id for user based authentication. 
	 * A session id is required in order to use any of the write methods.
	 * 
	 * @param token The authentication token
	 * @return The authenticated session ID
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
	public static String getNewSession(Token token) throws ResponseException {
		
		ResponseObject response = TMDbAPI.getAuthenticationSession(token);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			return new String(response.getData().getString(Constants.SESSION_ID));
		}
	}
	
	/**
	 * This method is used to generate a guest session id. A guest session can be used to rate movies without 
	 * having a registered TMDb user account. You should only generate a single guest session per user 
	 * (or device) as you will be able to attach the ratings to a TMDb user account in the future. 
	 * There is also IP limits in place so you should always make sure it's the end user doing the 
	 * guest session actions. If a guest session is not used for the first time within 24 hours, 
	 * it will be automatically discarded.
	 * 
	 * @return The authenticated session ID for a guest
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
	public static String getNewGuestSession() throws ResponseException {
		
		ResponseObject response = TMDbAPI.getAuthenticationGuestSession();
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			return new String(response.getData().getString(Constants.SESSION_ID));
		}
	}
	
}
