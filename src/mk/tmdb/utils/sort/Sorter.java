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

import mk.tmdb.entity.Keyword;
import mk.tmdb.entity.movie.MovieReduced;
import mk.tmdb.entity.person.PersonThumbnail;

public class Sorter {

	public static MovieReduced[] sortMovies(Set<MovieReduced> movies, MovieSortType sort) {
		
		MovieReduced[] moviesArray = movies.toArray(new MovieReduced[0]);
		
		heapSort(moviesArray, sort);
		
		return moviesArray;
	}
	
	public static PersonThumbnail[] sortPeople(Set<PersonThumbnail> people, PeopleSortType sort) {
		
		PersonThumbnail[] peopleArray = people.toArray(new PersonThumbnail[0]);
		
		heapSort(peopleArray, sort);
		
		return peopleArray;
	}
	
	public static Keyword[] sortKeywords(Set<Keyword> keys, KeywordSortType sort) {
		
		Keyword[] keysArray = keys.toArray(new Keyword[0]);
		
		heapSort(keysArray, sort);
		
		return keysArray;
	}

	private static <T> void heapSort(T array[], ISortType<T> sort) {
		for(int i = array.length; i > 1; i--){
			internalHeapSort(array, i - 1, sort.getComparer());
		}
	}
	
	private static <T> void internalHeapSort(T array[], int ubound, ICompare<T> comp){

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
