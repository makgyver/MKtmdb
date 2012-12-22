package mk.tmdb.test;

import java.util.Date;

import mk.tmdb.core.Authentication;
import mk.tmdb.entity.Account;
import mk.tmdb.entity.Token;
import mk.tmdb.utils.Log;

public class Test {
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		try {
			
			// Configuration class tested with success.
			// URLCreator class tested with success.
			// Authentication class tested with success.
			// Constants class is ok.
			// Token class tested with success.
			
			Token token = Authentication.getNewToken();
			Authentication.showRequestAuthorization(token);
			System.in.read();
			String sessionID = Authentication.getNewSession(token);
			
			System.out.println("");
			
			
		

		} catch (Exception e){
			Log.print(e);
		}
	}

}
