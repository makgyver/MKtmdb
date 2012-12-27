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
import mk.tmdb.entity.Credit;
import mk.tmdb.entity.Genre;
import mk.tmdb.entity.Keyword;
import mk.tmdb.entity.Language;
import mk.tmdb.entity.MovieList;
import mk.tmdb.entity.Token;
import mk.tmdb.entity.company.Company;
import mk.tmdb.entity.company.CompanyThumbnail;
import mk.tmdb.entity.image.Backdrop;
import mk.tmdb.entity.image.Poster;
import mk.tmdb.entity.image.Profile;
import mk.tmdb.entity.movie.Movie;
import mk.tmdb.entity.movie.MovieReduced;
import mk.tmdb.entity.movie.MovieThumbnail;
import mk.tmdb.entity.person.MovieCast;
import mk.tmdb.entity.person.MovieCrew;
import mk.tmdb.entity.person.Person;
import mk.tmdb.entity.person.PersonThumbnail;
import mk.tmdb.entity.trailer.Trailer;
import mk.tmdb.utils.Log;
import mk.tmdb.utils.Pair;

@SuppressWarnings("unused")
public class Test {
	
	public static void main(String[] args) {
		try {
			
			// Configuration class tested with success.
			// URLCreator class tested with success.
			// Authentication class tested with success.
			// Constants class is ok.
			// Token class tested with success.
			// Account class tested with success.
			// Collection class tested with success.
			// Entity class is ok.
			// Genre class tested with success.
			// Keyword class tested with success.
			// MovieList class tested with success.
			// Company hierarchy tested with success.
			// Movie hierarchy tested with success.
			// Person hierarchy tested with success.
			
			
			System.out.print("OK");
		

		} catch (Exception e){
			Log.print(e);
		}
	}

}
