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

import mk.tmdb.entity.person.TMDbPersonThumbnail;

/**
 * Enumeration with the possible sort type for People.
 * 
 * @author Mirko Polato
 *
 */
public enum TMDbPeopleSortType implements ITMDbSortType<TMDbPersonThumbnail> {
	
	/**
	 * Sort by ID in ascendent order.
	 */
	ASC_ID(new ITMDbCompare<TMDbPersonThumbnail>() {
		@Override
		public boolean compare(TMDbPersonThumbnail person1, TMDbPersonThumbnail person2) {
			return person1.getId() < person2.getId();
		}
	}),
	
	/**
	 * Sort by ID in descendant order.
	 */
	DESC_ID(new ITMDbCompare<TMDbPersonThumbnail>() {
		@Override
		public boolean compare(TMDbPersonThumbnail person1, TMDbPersonThumbnail person2) {
			return person1.getId() >= person2.getId();
		}
	}),
	
	/**
	 * Sort by name in ascendent order.
	 */
	ASC_NAME(new ITMDbCompare<TMDbPersonThumbnail>() {
		@Override
		public boolean compare(TMDbPersonThumbnail person1, TMDbPersonThumbnail person2) {
			return person1.getName().compareTo(person2.getName()) < 0;
		}
	}),
	
	/**
	 * Sort by name in descendant order.
	 */
	DESC_NAME(new ITMDbCompare<TMDbPersonThumbnail>() {
		@Override
		public boolean compare(TMDbPersonThumbnail person1, TMDbPersonThumbnail person2) {
			return person1.getName().compareTo(person2.getName()) >= 0;
		}
	});
	
	
	private final ITMDbCompare<TMDbPersonThumbnail> comparer;
	
	TMDbPeopleSortType(ITMDbCompare<TMDbPersonThumbnail> comparer) {
		this.comparer = comparer;
	}
	
	public ITMDbCompare<TMDbPersonThumbnail> getComparer() {
		return comparer;
	}
}
