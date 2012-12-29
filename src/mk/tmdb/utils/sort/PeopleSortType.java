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

import mk.tmdb.entity.person.PersonThumbnail;

/**
 * Enumeration with the possible sort type for People.
 * 
 * @author Mirko Polato
 *
 */
public enum PeopleSortType implements ISortType<PersonThumbnail> {
	
	ASC_ID(new ICompare<PersonThumbnail>() {
		@Override
		public boolean compare(PersonThumbnail person1, PersonThumbnail person2) {
			return person1.getId() < person2.getId();
		}
	}),
	
	DESC_ID(new ICompare<PersonThumbnail>() {
		@Override
		public boolean compare(PersonThumbnail person1, PersonThumbnail person2) {
			return person1.getId() >= person2.getId();
		}
	}),
	
	ASC_NAME(new ICompare<PersonThumbnail>() {
		@Override
		public boolean compare(PersonThumbnail person1, PersonThumbnail person2) {
			return person1.getName().compareTo(person2.getName()) < 0;
		}
	}),
	
	DESC_NAME(new ICompare<PersonThumbnail>() {
		@Override
		public boolean compare(PersonThumbnail person1, PersonThumbnail person2) {
			return person1.getName().compareTo(person2.getName()) >= 0;
		}
	});
	
	
	private final ICompare<PersonThumbnail> comparer;
	
	PeopleSortType(ICompare<PersonThumbnail> comparer) {
		this.comparer = comparer;
	}
	
	public ICompare<PersonThumbnail> getComparer() {
		return comparer;
	}
}
