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

import mk.tmdb.entity.movie.MovieReduced;

public enum MovieSortType implements ISortType<MovieReduced> {
	
	ASC_ID(new ICompare<MovieReduced>() {
		@Override
		public boolean compare(MovieReduced movie1, MovieReduced movie2) {
			return movie1.getId() < movie2.getId();
		}
	}),
	
	DESC_ID(new ICompare<MovieReduced>() {
		@Override
		public boolean compare(MovieReduced movie1, MovieReduced movie2) {
			return movie1.getId() >= movie2.getId();
		}
	}),
	
	ASC_TITLE(new ICompare<MovieReduced>() {
		@Override
		public boolean compare(MovieReduced movie1, MovieReduced movie2) {
			return movie1.getTitle().compareTo(movie2.getTitle()) < 0;
		}
	}),
	
	DESC_TITLE(new ICompare<MovieReduced>() {
		@Override
		public boolean compare(MovieReduced movie1, MovieReduced movie2) {
			return movie1.getTitle().compareTo(movie2.getTitle()) >= 0;
		}
	}),
	
	ASC_RELEASE(new ICompare<MovieReduced>() {
		@Override
		public boolean compare(MovieReduced movie1, MovieReduced movie2) {
			return movie1.getReleaseDate().compareTo(movie2.getReleaseDate()) < 0;
		}
	}),
	
	DESC_RELEASE(new ICompare<MovieReduced>() {
		@Override
		public boolean compare(MovieReduced movie1, MovieReduced movie2) {
			return movie1.getReleaseDate().compareTo(movie2.getReleaseDate()) >= 0;
		}
	}),
	
	ASC_POPULARITY(new ICompare<MovieReduced>() {
		@Override
		public boolean compare(MovieReduced movie1, MovieReduced movie2) {
			return movie1.getPopularity() < movie2.getPopularity();
		}
	}),
	
	DESC_POPULARITY(new ICompare<MovieReduced>() {
		@Override
		public boolean compare(MovieReduced movie1, MovieReduced movie2) {
			return movie1.getPopularity() >= movie2.getPopularity();
		}
	}),
	
	ASC_VOTE_AVG(new ICompare<MovieReduced>() {
		@Override
		public boolean compare(MovieReduced movie1, MovieReduced movie2) {
			return movie1.getVoteAverage() < movie2.getVoteAverage();
		}
	}),
	
	DESC_VOTE_AVG(new ICompare<MovieReduced>() {
		@Override
		public boolean compare(MovieReduced movie1, MovieReduced movie2) {
			return movie1.getVoteAverage() >= movie2.getVoteAverage();
		}
	}),
	
	ASC_VOTE_COUNT(new ICompare<MovieReduced>() {
		@Override
		public boolean compare(MovieReduced movie1, MovieReduced movie2) {
			return movie1.getVoteCount() < movie2.getVoteCount();
		}
	}),
	
	DESC_VOTE_COUNT(new ICompare<MovieReduced>() {
		@Override
		public boolean compare(MovieReduced movie1, MovieReduced movie2) {
			return movie1.getVoteCount() >= movie2.getVoteCount();
		}
	});
	
	
	private final ICompare<MovieReduced> comparer;
	
	MovieSortType(ICompare<MovieReduced> comparer) {
		this.comparer = comparer;
	}
	
	public ICompare<MovieReduced> getComparer() {
		return comparer;
	}
}
