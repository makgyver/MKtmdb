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
			@SuppressWarnings("unchecked")
			Pair<L,R> pair = (Pair<L, R>) object;
			return first.equals(pair.getFirst()) && second.equals(pair.getSecond());
		}
		catch(Exception e)
		{
			return false;
		}
	}

}