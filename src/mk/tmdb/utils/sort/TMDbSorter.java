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

package mk.tmdb.utils.sort;

import java.util.Set;

import mk.tmdb.entity.TMDbKeyword;
import mk.tmdb.entity.movie.TMDbMovieReduced;
import mk.tmdb.entity.person.TMDbPersonThumbnail;

/**
 * Class that offers sort methods.
 * 
 * @author Mirko Polato
 *
 */
public class TMDbSorter {

	/**
	 * Sorts the given set of movies.
	 * 
	 * @param movies The set of movies
	 * @param sort The sort type
	 * @return A sorted array
	 */
	public static TMDbMovieReduced[] sortMovies(Set<TMDbMovieReduced> movies, TMDbMovieSortType sort) {
		
		TMDbMovieReduced[] moviesArray = movies.toArray(new TMDbMovieReduced[0]);
		
		heapSort(moviesArray, sort);
		
		return moviesArray;
	}
	
	/**
	 * Sorts the given set of people.
	 * 
	 * @param people The set of people
	 * @param sort The sort type
	 * @return A sorted array
	 */
	public static TMDbPersonThumbnail[] sortPeople(Set<TMDbPersonThumbnail> people, TMDbPeopleSortType sort) {
		
		TMDbPersonThumbnail[] peopleArray = people.toArray(new TMDbPersonThumbnail[0]);
		
		heapSort(peopleArray, sort);
		
		return peopleArray;
	}
	
	/**
	 * Sorts the given set of keywords.
	 * 
	 * @param keys The set of keywords
	 * @param sort The sort type
	 * @return A sorted array
	 */
	public static TMDbKeyword[] sortKeywords(Set<TMDbKeyword> keys, TMDbKeywordSortType sort) {
		
		TMDbKeyword[] keysArray = keys.toArray(new TMDbKeyword[0]);
		
		heapSort(keysArray, sort);
		
		return keysArray;
	}

	/**
	 * Parametric heap sort (Outer loop).
	 * 
	 * @param array The array to sort
	 * @param sort The sort type
	 */
	private static <T> void heapSort(T array[], ITMDbSortType<T> sort) {
		for(int i = array.length; i > 1; i--){
			internalHeapSort(array, i - 1, sort.getComparer());
		}
	}
	
	/**
	 * Parametric heap sort.
	 * 
	 * @param array The array to sort
	 * @param ubound The array length
	 * @param comp The comparer
	 */
	private static <T> void internalHeapSort(T array[], int ubound, ITMDbCompare<T> comp){

		int lChild, rChild, mChild;
		
		int root = (ubound - 1) / 2;

		for(int j = root; j >= 0; j--){
			
			for(int i = root; i >= 0; i--){
				
				lChild = (2 * i) + 1;
				rChild = (2 * i) + 2;
				
				if((lChild <= ubound) && (rChild <= ubound)){
					
					if(!comp.compare(array[rChild], array[lChild])) {
						mChild = rChild;
					} else { 
						mChild = lChild;
					}
					
				} else {
					
					if(rChild > ubound) { 
						mChild = lChild;
					} else { 
						mChild = rChild;
					}
					
				}

				if(comp.compare(array[i], array[mChild])){
					T temp = array[i];
					array[i] = array[mChild];
					array[mChild] = temp;
				}
			}
		}
		
		T temp = array[0];
		array[0] = array[ubound];
		array[ubound] = temp;
	}
}
