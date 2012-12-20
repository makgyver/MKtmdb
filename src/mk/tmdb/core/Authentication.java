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

public class Authentication {

	public static Token getNewToken() throws ResponseException {
		
		ResponseObject response = TMDBAPI.getAuthenticationToken();
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			return new Token(response.getData());
		}
	}
	
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
	
	public static String getNewSession(Token token) throws ResponseException {
		
		ResponseObject response = TMDBAPI.getAuthenticationSession(token);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			return new String(response.getData().getString(Constants.SESSION_ID));
		}
	}
	
	public static String getNewGuestSession() throws ResponseException {
		
		ResponseObject response = TMDBAPI.getAuthenticationGuestSession();
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			return new String(response.getData().getString(Constants.SESSION_ID));
		}
	}
	
}
