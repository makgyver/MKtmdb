/*******************************************************************************
 * Copyright (C) 2012-2013  Mirko Polato
 * 
 * This file is part of MKtmdb.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 ******************************************************************************/

package mk.tmdb.core;

import java.awt.Desktop;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

import mk.tmdb.entity.TMDbToken;
import mk.tmdb.exception.ResponseException;
import mk.tmdb.utils.TMDbLog;
import mk.tmdb.utils.TMDbResponseObject;
import mk.tmdb.utils.TMDbStatus;

/**
 * This class provides methods to authenticate to the TMDB site.
 * 
 * @author Mirko Polato
 *
 */
public class TMDbAuthentication {

	/**
	 * This method is used to generate a valid request token for user based authentication. 
	 * A request token is required in order to request a session id. 
	 * You can generate any number of request tokens but they will expire after 60 minutes. 
	 * As soon as a valid session id has been created the token will be destroyed.
	 * 
	 * @return The authentication token
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
	public static TMDbToken getNewToken() throws ResponseException {
		
		TMDbResponseObject response = TMDbAPI.getAuthenticationToken();
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			return new TMDbToken(response.getData());
		}
	}
	
	/**
	 * Opens the request authorization web page in the default browser.
	 * 
	 * @param token The authentication token
	 * @throws ResponseException Throws whether the server response is not a success.
	 */
	public static void showRequestAuthorization(TMDbToken token) throws ResponseException {
		try {
			
			URI url = new URI(TMDbURLCreator.getRequestAuthorizationUrl(token.getValue()).toString());
			Desktop.getDesktop().browse(url);
			
		} catch (MalformedURLException e) {
			TMDbLog.print(e);
			
			throw new ResponseException(TMDbStatus.MALFORMED_URL);
		} catch (URISyntaxException e) {
			TMDbLog.print(e);
			
			throw new ResponseException(TMDbStatus.MALFORMED_URL);
		} catch (IOException e) {
			TMDbLog.print(e);
			
			throw new ResponseException(TMDbStatus.UNKNOWN_ERROR);
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
	public static String getNewSession(TMDbToken token) throws ResponseException {
		
		TMDbResponseObject response = TMDbAPI.getAuthenticationSession(token);
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			return new String(response.getData().getString(TMDbConstants.SESSION_ID));
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
		
		TMDbResponseObject response = TMDbAPI.getAuthenticationGuestSession();
		
		if (response.hasError()) {
			throw new ResponseException(response.getStatus());
		} else {
			return new String(response.getData().getString(TMDbConstants.SESSION_ID));
		}
	}
	
}
