package mk.tmdb.test;

import java.awt.Desktop;
import java.net.URI;
import java.net.URL;

import net.sf.json.JSONObject;

import mk.tmdb.core.TMDBAPI;
import mk.tmdb.core.URLCreator;
import mk.tmdb.utils.Log;

public class Test {
	
	public static void main(String[] args) {
		try {
			
			//System.out.println(URLCreator.getCastInfoUrl(49051));
			/*System.out.println(URLCreator.getMovieInfoUrl(49051));
			System.out.println(URLCreator.getMovieChangesUrl(49051));
			System.out.println(URLCreator.getMovieImagesUrl(49051));
			System.out.println(URLCreator.getMovieInfoUrl(49051));
			System.out.println(URLCreator.getMovieKeywordsUrl(49051));
			System.out.println(URLCreator.getMovieReleasesDatesUrl(49051));
			System.out.println(URLCreator.getMovieTrailersUrl(49051));
			System.out.println(URLCreator.getMovieTranslationsUrl(49051));
			System.out.println(URLCreator.getAlternativeMovieTitlesUrl(49051));
			System.out.println(URLCreator.getInTheatresMoviesUrl());
			System.out.println(URLCreator.searchCompanyByNameUrl("twisted"));
			System.out.println(URLCreator.searchPeopleByNameUrl("DiCaprio"));
			System.out.println(URLCreator.searchPeopleByNameUrl("DiCaprio", false));
			System.out.println(URLCreator.searchMovieByTitleUrl("inception", 2010));
			System.out.println(URLCreator.searchMovieByTitleUrl("inception", true));
			System.out.println(URLCreator.searchMovieByTitleUrl("inception", 2010, false));*/
			
			
			//ce0176326843ee3106195ef8a1e1eec7ebea1f23
			//System.out.println(URLCreator.getMovieTrailersUrl(49051));
			//System.out.println(URLCreator.getAccountInfoUrl("ad1b1fd3fdfa6a07cda5d3b924caf5d6f19d06c5"));
			//System.out.println(URLCreator.getAccountFavsListsUrl("ad1b1fd3fdfa6a07cda5d3b924caf5d6f19d06c5"));
			//System.out.println(URLCreator.getAccountFavsMoviesUrl("ad1b1fd3fdfa6a07cda5d3b924caf5d6f19d06c5"));
			//System.out.println(URLCreator.addMovieToFavsUrl("2296309", "ad1b1fd3fdfa6a07cda5d3b924caf5d6f19d06c5", 49051));
			//System.out.println(URLCreator.getInTheatreMoviesUrl());
			//System.out.println(TMDBAPI.getMovieInformation(49051).getData().toString());
			
			
			
			//JSONObject j = new JSONObject();
			//j.put("movie_id", 49051);
			//j.put("favorite", true);
			
			//URL e = new URL("kjvfv fdv sdfvj sdfjvsdfvo");
			
			System.out.println(URLCreator.getAuthTokenUrl());
			System.out.println(URLCreator.getRequestAuthorizationUrl(""));
			System.out.println(URLCreator.getAuthSessionUrl(""));
			System.out.println(URLCreator.getAccountInfoUrl(""));
			System.out.println(URLCreator.getAccountFavsListsUrl(1, ""));
			
			//System.out.println(TMDBAPI.ma(new URL("http://api.themoviedb.org/3/account/2296309/favorite?api_key=6827f4afdbf8291669e04ca338ab087c&session_id=69713017689af29008212bb1c96ba772dcb6a05d"), j));

			//System.out.println(URLCreator.searchMovieByTitleUrl("inception", 2013));
			
			//Movie movie = new Movie(WebRequest.getHttpJSON(URLCreator.getMovieInfoUrl(49051)));
			//System.out.println(movie.getReleaseDate());

		} catch (Exception e){
			Log.print(e);
		}
	}

}
