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

package mk.tmdb.utils;

public class Pair<L,R> {

	private final L first;
	private final R second;

	public Pair(L left, R right) {
		this.first = left;
		this.second = right;
	}

	public L getFirst() { 
		return first; 
	}
	
	public R getSecond() { 
		return second; 
	}
	
	@Override
	public String toString() {
		return "(" + first.toString() + ", " + second.toString() + ")";
	}
	
	@Override
	public boolean equals(Object object) {
		try
		{
			if (object != null) {
				@SuppressWarnings("unchecked")
				Pair<L,R> pair = (Pair<L, R>) object;
				return first.equals(pair.getFirst()) && second.equals(pair.getSecond());
			} else {
				return false;
			}
		}
		catch(Exception e)
		{
			return false;
		}
	}

}
