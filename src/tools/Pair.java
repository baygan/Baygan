package tools;

/**
 * Keeps Two Object as a pair
 * 
 * @author Salim
 * 
 * @param <T>
 *            First object is of type T
 * @param <S>
 *            Second object is of type S
 */
public class Pair<T, S> {
	/**
	 * First Object
	 */
	private T t;
	/**
	 * Second Object
	 */
	private S s;

	public Pair() {
	}

	public Pair(T t, S s) {
		this.setSecond(s);
		this.setFirst(t);
	}

	public void setFirst(T t) {
		this.t = t;
	}

	public T getFirst() {
		return t;
	}

	public void setSecond(S s) {
		this.s = s;
	}

	public S getSecond() {
		return s;
	}

	@Override
	public String toString() {
		return "(" + t + "," + s + ")";
	}
}
