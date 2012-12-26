package mk.tmdb.test;

import java.util.Set;

import mk.tmdb.core.Authentication;
import mk.tmdb.core.URLCreator;
import mk.tmdb.entity.Collection;
import mk.tmdb.entity.Genre;
import mk.tmdb.entity.Keyword;
import mk.tmdb.entity.MovieList;
import mk.tmdb.entity.Token;
import mk.tmdb.entity.image.Backdrop;
import mk.tmdb.entity.image.Poster;
import mk.tmdb.entity.movie.MovieReduced;
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
			// Collection class tested with success.
			// Entity class is ok.
			// Genre class tested with success.
			// Keyword class tested with success.
			// MovieList class tested with success.
			
			MovieList list = MovieList.getList("509ec17b19c2950a0600050d");
			Set<MovieList> lists1 = MovieList.searchByName("winners");
			Set<MovieList> lists2 = MovieList.searchByName("winners", 2);
			Set<MovieList> lists3 = MovieList.fullSearchByName("winners");
			
			System.out.print("");
		

		} catch (Exception e){
			Log.print(e);
		}
	}

}
