package mk.tmdb.test;

import java.net.URL;
import java.util.Date;
import java.util.Set;

import mk.tmdb.core.Configuration;
import mk.tmdb.core.URLCreator;
import mk.tmdb.utils.Log;

public class Test {
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		try {
			
			// Configuration class tested with success.
			
			int accountID = 1, movieID = 1, page = 2, collectionID = 1, companyID = 1, listID = 1;
			String sessionID = "session", token = "token", start = "start", end = "end";
			
			String s1 = URLCreator.getApiKey();
			
			URL s2 = URLCreator.addMovieToFavsUrl(accountID, sessionID);
			URL s3 = URLCreator.addMovieToWatchlistUrl(accountID, sessionID);
			URL s4 = URLCreator.getAccountFavsListsUrl(accountID, sessionID);
			URL s5 = URLCreator.getAccountFavsListsUrl(accountID, sessionID, page);
			URL s6 = URLCreator.getAccountFavsMoviesUrl(accountID, sessionID);
			URL s7 = URLCreator.getAccountFavsMoviesUrl(accountID, sessionID, page);
			URL s8 = URLCreator.getAccountInfoUrl(sessionID);
			URL s9 = URLCreator.getAlternativeMovieTitlesUrl(movieID);
			URL s10 = URLCreator.getAuthSessionUrl(token);
			URL s11 = URLCreator.getAuthTokenUrl();
			URL s12 = URLCreator.getCastInfoUrl(movieID);
			URL s13 = URLCreator.getChangedMoviesUrl();
			URL s14 = URLCreator.getChangedMoviesUrl(page);
			URL s15 = URLCreator.getChangedMoviesUrl(start, end);
			URL s16 = URLCreator.getChangedMoviesUrl(start, end);
			URL s17 = URLCreator.getChangedMoviesUrl(start, end, page);
			URL s18 = URLCreator.getChangedMoviesUrl(start, end, page);
			URL s19 = URLCreator.getChangedPersonsUrl();
			URL s20 = URLCreator.getChangedPersonsUrl(page);
			URL s21 = URLCreator.getChangedPersonsUrl(start, end);
			URL s22 = URLCreator.getChangedPersonsUrl(start, end);
			URL s23 = URLCreator.getChangedPersonsUrl(start, end, page);
			URL s24 = URLCreator.getChangedPersonsUrl(start, end, page);
			URL s25 = URLCreator.getCollectionImagesUrl(collectionID);
			URL s26 = URLCreator.getCollectionInfoUrl(collectionID);
			URL s27 = URLCreator.getCompanyInfoUrl(companyID);
			URL s28 = URLCreator.getConfigurationUrl();
			URL s29 = URLCreator.getGenresListUrl();
			URL s30 = URLCreator.getGuestSessionUrl();
			URL s31 = URLCreator.getInTheatresMoviesUrl();
			URL s32 = URLCreator.getInTheatresMoviesUrl(page);
			URL s33 = URLCreator.getLatestMovieUrl();
			URL s34 = URLCreator.getLatestPerson();
			URL s35 = URLCreator.getListsBelongsToMovieUrl(movieID);
			URL s36 = URLCreator.getListsBelongsToMovieUrl(movieID, page);
			URL s37 = URLCreator.getListUrl(listID);
			URL s38 = URLCreator.getMovieChangesUrl(movieID);
			URL s39 = URLCreator.getMovieChangesUrl(movieID, start, end);
			URL s40 = URLCreator.getMovieChangesUrl(movieID, start, end);
			URL s41 = URLCreator.getMovieImagesUrl(movieID);
			URL s42 = URLCreator.getMovieInfoUrl(movieID);
			
			

		} catch (Exception e){
			Log.print(e);
		}
	}

}
