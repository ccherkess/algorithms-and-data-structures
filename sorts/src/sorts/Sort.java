package sorts;

import java.util.Comparator;

public interface Sort {

	public <V extends Comparable<V>> void sort(V[] array);
	
	public <V> void sort(V[] array, Comparator<V> comparator);
	
	default void swap(Object[] array, int i, int j) {
		
		Object tmp = array[i];
		
		array[i] = array[j];
		
		array[j] = tmp;
		
	}
	
}
