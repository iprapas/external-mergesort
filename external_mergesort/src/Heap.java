
public interface Heap<T extends Comparable<? super T>> {
	boolean isEmpty();
	int size();
	void insert(T x);
	T minElement();
	void deleteMinElement();
}
