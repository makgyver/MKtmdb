package mk.tmdb.test;

import java.util.Set;

import mk.tmdb.core.Authentication;
import mk.tmdb.core.URLCreator;
import mk.tmdb.entity.Collection;
import mk.tmdb.entity.Token;
import mk.tmdb.entity.image.Backdrop;
import mk.tmdb.entity.image.Poster;
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
			
			Collection collection = Collection.getInformation(10);
			Set<Collection> colls1 = Collection.searchByName("star");
			Set<Collection> colls2 = Collection.searchByName("star", 2);
			Set<Collection> colls3 = Collection.fullSearchByName("star");
			String s = URLCreator.getCollectionInfoUrl(10).toString();
			Set<Backdrop> backs = Collection.getBackrops(10);
			Set<Poster> posts = Collection.getPosters(10);
			
			System.out.println("");
			
			
		

		} catch (Exception e){
			Log.print(e);
		}
	}

}
