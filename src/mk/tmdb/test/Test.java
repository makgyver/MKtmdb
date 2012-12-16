package mk.tmdb.test;

import java.net.MalformedURLException;

import mk.tmdb.core.*;
import mk.tmdb.entity.Movie;
import mk.tmdb.exception.InvalidApiKeyException;
import mk.tmdb.utils.Log;

public class Test {
	
	public static void main(String[] args) {
		try {
			
			System.out.println(URLCreator.getCastInfoUrl(49051));
			System.out.println(URLCreator.getMovieChangesUrl(49051));
			System.out.println(URLCreator.getMovieImagesUrl(49051));
			System.out.println(URLCreator.getMovieInfoUrl(49051));
			System.out.println(URLCreator.getMovieKeywordsUrl(49051));
			System.out.println(URLCreator.getMovieReleasesDateUrl(49051));
			System.out.println(URLCreator.getMovieTrailersUrl(49051));
			System.out.println(URLCreator.getMovieTranslationsUrl(49051));
			System.out.println(URLCreator.getInTheatreMoviesUrl());
			System.out.println(URLCreator.searchCompanyByNameUrl("twisted"));
			System.out.println(URLCreator.searchPeopleByNameUrl("DiCaprio"));
			System.out.println(URLCreator.searchPeopleByNameUrl("DiCaprio", false));
			System.out.println(URLCreator.searchMovieByTitleUrl("inception", 2010));
			System.out.println(URLCreator.searchMovieByTitleUrl("inception", true));
			System.out.println(URLCreator.searchMovieByTitleUrl("inception", 2010, false));
			System.out.println(URLCreator.getPersonInfoUrl(109));
			System.out.println(WebRequest.getHttpJSON(URLCreator.getMovieInfoUrl(49051)));
			
			Movie movie = new Movie(WebRequest.getHttpJSON(URLCreator.getMovieInfoUrl(49051)));
			System.out.println(movie.getReleaseDate());

		} catch (MalformedURLException e) {
			Log.print(e);
		} catch (InvalidApiKeyException e) {
			Log.print(e);
		}
	}

}
