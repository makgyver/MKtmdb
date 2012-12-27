package mk.tmdb.test;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import mk.tmdb.core.Authentication;
import mk.tmdb.core.URLCreator;
import mk.tmdb.entity.Collection;
import mk.tmdb.entity.Country;
import mk.tmdb.entity.Genre;
import mk.tmdb.entity.Keyword;
import mk.tmdb.entity.Language;
import mk.tmdb.entity.MovieList;
import mk.tmdb.entity.Token;
import mk.tmdb.entity.company.Company;
import mk.tmdb.entity.company.CompanyThumbnail;
import mk.tmdb.entity.image.Backdrop;
import mk.tmdb.entity.image.Poster;
import mk.tmdb.entity.movie.Movie;
import mk.tmdb.entity.movie.MovieReduced;
import mk.tmdb.entity.movie.MovieThumbnail;
import mk.tmdb.entity.person.MovieCast;
import mk.tmdb.entity.person.MovieCrew;
import mk.tmdb.entity.trailer.Trailer;
import mk.tmdb.utils.Log;
import mk.tmdb.utils.Pair;

public class Test {
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		try {
			
			// Configuration class tested with success.
			// URLCreator class tested with success.
			// Authentication class tested with success.
			// Constants class is ok.
			// Token class tested with success.
			// Collection class tested with success.
			// Entity class is ok.
			// Genre class tested with success.
			// Keyword class tested with success.
			// MovieList class tested with success.
			// Company hierarchy tested with success.
			
			Movie movie = MovieThumbnail.getInformation(11);
			URL url = URLCreator.searchMovieByTitleUrl("last");
			/*Set<MovieReduced> movies1 = MovieThumbnail.fullSearchByTitle("last");
			Set<MovieReduced> movies2 = MovieThumbnail.fullSearchByTitle("last", false);
			Set<MovieReduced> movies3 = MovieThumbnail.fullSearchByTitleAndYear("last", 2000);
			Set<MovieReduced> movies4 = MovieThumbnail.fullSearchByTitleAndYear("last", 2000, true);
			Set<Integer> ids1 = MovieThumbnail.getAllChanged();
			

			//changed
			
			Set<MovieReduced> movies5 = MovieThumbnail.getAllInTheatreMovies();
			Set<MovieReduced> movies6 = MovieThumbnail.getAllPopularMovies();
			Set<MovieReduced> movies7 = MovieThumbnail.getAllSimilarMovies(11);
			Set<MovieReduced> movies8 = MovieThumbnail.getAllTopRatedMovies();
			Set<MovieReduced> movies9 = MovieThumbnail.getAllUpcomingMovies();
			
			Set<MovieReduced> movies11 = MovieThumbnail.getInTheatreMovies();
			Set<MovieReduced> movies12 = MovieThumbnail.getInTheatreMovies(2);
			Set<MovieReduced> movies13 = MovieThumbnail.getPopularMovies();
			Set<MovieReduced> movies14 = MovieThumbnail.getPopularMovies(2);
			Set<MovieReduced> movies15 = MovieThumbnail.getSimilarMovies(11);
			Set<MovieReduced> movies16 = MovieThumbnail.getSimilarMovies(11, 2);
			
			Set<MovieReduced> movies17 = MovieThumbnail.getTopRatedMovies();
			Set<MovieReduced> movies18 = MovieThumbnail.getTopRatedMovies(2);
			Set<MovieReduced> movies19 = MovieThumbnail.getUpcomingMovies();
			Set<MovieReduced> movies20 = MovieThumbnail.getUpcomingMovies(2);

			Set<MovieReduced> movies21 = MovieThumbnail.searchByTitle("last");
			Set<MovieReduced> movies22 = MovieThumbnail.searchByTitle("last", false);
			Set<MovieReduced> movies23 = MovieThumbnail.searchByTitle("last", 2);
			Set<MovieReduced> movies24 = MovieThumbnail.searchByTitle("inception", false, 2);
			
			Set<MovieReduced> movies25 = MovieThumbnail.searchByTitleAndYear("last", 2000);
			Set<MovieReduced> movies26 = MovieThumbnail.searchByTitleAndYear("last", 2000, 2);
			Set<MovieReduced> movies27 = MovieThumbnail.searchByTitleAndYear("last", 2000, false);
			Set<MovieReduced> movies28 = MovieThumbnail.searchByTitleAndYear("last", 2000, false, 2);
			
			Set<Pair<Country, String>> alt = MovieThumbnail.getAlternativeTitles(11);
			Set<Backdrop> backs = MovieThumbnail.getBackdrops(11);
			Set<MovieCast> cast = MovieThumbnail.getCastInformation(11);
			Set<MovieCrew> crew = MovieThumbnail.getCrewInformation(11);
			Set<Keyword> keys = MovieThumbnail.getKeywords(11);
			Movie mov = MovieThumbnail.getLatestMovie();
			Set<Poster> posters = MovieThumbnail.getPosters(11);
			Set<Pair<Country, Date>> dates = MovieThumbnail.getReleaseDates(11);
			Set<Trailer> trails = MovieThumbnail.getTrailers(11);
			Set<Language> langs = MovieThumbnail.getTranslations(11);*/
			
			Set<Integer> ids1 = MovieThumbnail.getChanged();
			Set<Integer> ids2 = MovieThumbnail.getChanged(2);
			Set<Integer> ids3 = MovieThumbnail.getChanged("2012-12-15", "2012-12-26");
			Set<Integer> ids4 = MovieThumbnail.getChanged("2012-12-15", "2012-12-26", 2);
			
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date start = (Date)formatter.parse("2012-12-15");
			Date end = (Date)formatter.parse("2012-12-26");
			
			Set<Integer> ids5 = MovieThumbnail.getChanged(start, end);
			Set<Integer> ids6 = MovieThumbnail.getChanged(start, end, 2);
			
			Set<Integer> ids7 = MovieThumbnail.getAllChanged(start, end);
			
			
			System.out.print("");
		

		} catch (Exception e){
			Log.print(e);
		}
	}

}
