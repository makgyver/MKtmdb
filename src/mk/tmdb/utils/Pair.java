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

}