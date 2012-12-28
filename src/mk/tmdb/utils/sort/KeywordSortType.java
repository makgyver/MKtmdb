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

import mk.tmdb.entity.Keyword;

public enum KeywordSortType implements ISortType<Keyword> {
	
	ASC_ID(new ICompare<Keyword>() {
		@Override
		public boolean compare(Keyword word1, Keyword word2) {
			return word1.getId() < word2.getId();
		}
	}),
	
	DESC_ID(new ICompare<Keyword>() {
		@Override
		public boolean compare(Keyword word1, Keyword word2) {
			return word1.getId() >= word2.getId();
		}
	}),
	
	ASC_VALUE(new ICompare<Keyword>() {
		@Override
		public boolean compare(Keyword word1, Keyword word2) {
			return word1.getValue().compareTo(word2.getValue()) < 0;
		}
	}),
	
	DESC_VALUE(new ICompare<Keyword>() {
		@Override
		public boolean compare(Keyword word1, Keyword word2) {
			return word1.getValue().compareTo(word2.getValue()) >= 0;
		}
	});
	
	
	private final ICompare<Keyword> comparer;
	
	KeywordSortType(ICompare<Keyword> comparer) {
		this.comparer = comparer;
	}
	
	public ICompare<Keyword> getComparer() {
		return comparer;
	}
}
