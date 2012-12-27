package mk.tmdb.test;

import java.util.Set;

import mk.tmdb.core.Authentication;
import mk.tmdb.core.URLCreator;
import mk.tmdb.entity.Collection;
import mk.tmdb.entity.Genre;
import mk.tmdb.entity.Keyword;
import mk.tmdb.entity.MovieList;
import mk.tmdb.entity.Token;
import mk.tmdb.entity.company.Company;
import mk.tmdb.entity.company.CompanyThumbnail;
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
			// Company hierarchy tested with success.
			
			Company company = CompanyThumbnail.getInformation(1);
			Set<MovieReduced> movies1 = CompanyThumbnail.getAssociatedMovies(1);
			Set<MovieReduced> movies2 = CompanyThumbnail.getAssociatedMovies(1, 2);
			Set<MovieReduced> movies3 = CompanyThumbnail.getAllAssociatedMovies(1);
			Set<CompanyThumbnail> comps1 = Company.searchByName("lucas");
			Set<CompanyThumbnail> comps2 = Company.searchByName("lucas", 2);
			Set<CompanyThumbnail> comps3 = Company.fullSearchByName("lucas");
			
			System.out.print("");
		

		} catch (Exception e){
			Log.print(e);
		}
	}

}
