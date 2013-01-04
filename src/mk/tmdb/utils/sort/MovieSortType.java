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

import mk.tmdb.entity.movie.TMDbMovieReduced;

/**
 * Enumeration with the possible sort type for Movies.
 * 
 * @author Mirko Polato
 *
 */
public enum MovieSortType implements ISortType<TMDbMovieReduced> {
	
	/**
	 * Sort by ID in ascendent order.
	 */
	ASC_ID(new ICompare<TMDbMovieReduced>() {
		@Override
		public boolean compare(TMDbMovieReduced movie1, TMDbMovieReduced movie2) {
			return movie1.getId() < movie2.getId();
		}
	}),
	
	/**
	 * Sort by ID in descendant order.
	 */
	DESC_ID(new ICompare<TMDbMovieReduced>() {
		@Override
		public boolean compare(TMDbMovieReduced movie1, TMDbMovieReduced movie2) {
			return movie1.getId() >= movie2.getId();
		}
	}),
	
	/**
	 * Sort by title in ascendent order.
	 */
	ASC_TITLE(new ICompare<TMDbMovieReduced>() {
		@Override
		public boolean compare(TMDbMovieReduced movie1, TMDbMovieReduced movie2) {
			return movie1.getTitle().compareTo(movie2.getTitle()) < 0;
		}
	}),
	
	/**
	 * Sort by title in descendant order.
	 */
	DESC_TITLE(new ICompare<TMDbMovieReduced>() {
		@Override
		public boolean compare(TMDbMovieReduced movie1, TMDbMovieReduced movie2) {
			return movie1.getTitle().compareTo(movie2.getTitle()) >= 0;
		}
	}),
	
	/**
	 * Sort by original title in ascendent order.
	 */
	ASC_ORIGINAL_TITLE(new ICompare<TMDbMovieReduced>() {
		@Override
		public boolean compare(TMDbMovieReduced movie1, TMDbMovieReduced movie2) {
			return movie1.getOriginalTitle().compareTo(movie2.getOriginalTitle()) < 0;
		}
	}),
	
	/**
	 * Sort by original title in descendant order.
	 */
	DESC_ORIGINAL_TITLE(new ICompare<TMDbMovieReduced>() {
		@Override
		public boolean compare(TMDbMovieReduced movie1, TMDbMovieReduced movie2) {
			return movie1.getOriginalTitle().compareTo(movie2.getOriginalTitle()) >= 0;
		}
	}),
	
	/**
	 * Sort by release date in ascendent order.
	 */
	ASC_RELEASE(new ICompare<TMDbMovieReduced>() {
		@Override
		public boolean compare(TMDbMovieReduced movie1, TMDbMovieReduced movie2) {
			if (movie1.isReleaseDateSet()) {
				if (movie2.isReleaseDateSet()) {
					return movie1.getReleaseDate().compareTo(movie2.getReleaseDate()) < 0;
				} else {
					return true;
				}
			} else {
				if (movie2.isReleaseDateSet()) {
					return false;
				} else {
					return true;
				}
			}
		}
	}),
	
	/**
	 * Sort by release date in descendant order.
	 */
	DESC_RELEASE(new ICompare<TMDbMovieReduced>() {
		@Override
		public boolean compare(TMDbMovieReduced movie1, TMDbMovieReduced movie2) {
			if (movie1.isReleaseDateSet()) {
				if (movie2.isReleaseDateSet()) {
					return movie1.getReleaseDate().compareTo(movie2.getReleaseDate()) >= 0;
				} else {
					return true;
				}
			} else {
				if (movie2.isReleaseDateSet()) {
					return false;
				} else {
					return true;
				}
			}
		}
	}),
	
	/**
	 * Sort by popularity in ascendent order.
	 */
	ASC_POPULARITY(new ICompare<TMDbMovieReduced>() {
		@Override
		public boolean compare(TMDbMovieReduced movie1, TMDbMovieReduced movie2) {
			return movie1.getPopularity() < movie2.getPopularity();
		}
	}),
	
	/**
	 * Sort by popularity in descendant order.
	 */
	DESC_POPULARITY(new ICompare<TMDbMovieReduced>() {
		@Override
		public boolean compare(TMDbMovieReduced movie1, TMDbMovieReduced movie2) {
			return movie1.getPopularity() >= movie2.getPopularity();
		}
	}),
	
	/**
	 * Sort by vote average in ascendent order.
	 */
	ASC_VOTE_AVG(new ICompare<TMDbMovieReduced>() {
		@Override
		public boolean compare(TMDbMovieReduced movie1, TMDbMovieReduced movie2) {
			return movie1.getVoteAverage() < movie2.getVoteAverage();
		}
	}),
	
	/**
	 * Sort by vote average in descendant order.
	 */
	DESC_VOTE_AVG(new ICompare<TMDbMovieReduced>() {
		@Override
		public boolean compare(TMDbMovieReduced movie1, TMDbMovieReduced movie2) {
			return movie1.getVoteAverage() >= movie2.getVoteAverage();
		}
	}),
	
	/**
	 * Sort by vote count in ascendent order.
	 */
	ASC_VOTE_COUNT(new ICompare<TMDbMovieReduced>() {
		@Override
		public boolean compare(TMDbMovieReduced movie1, TMDbMovieReduced movie2) {
			return movie1.getVoteCount() < movie2.getVoteCount();
		}
	}),
	
	/**
	 * Sort by vote count in descendant order.
	 */
	DESC_VOTE_COUNT(new ICompare<TMDbMovieReduced>() {
		@Override
		public boolean compare(TMDbMovieReduced movie1, TMDbMovieReduced movie2) {
			return movie1.getVoteCount() >= movie2.getVoteCount();
		}
	});
	
	
	private final ICompare<TMDbMovieReduced> comparer;
	
	MovieSortType(ICompare<TMDbMovieReduced> comparer) {
		this.comparer = comparer;
	}
	
	public ICompare<TMDbMovieReduced> getComparer() {
		return comparer;
	}
}
