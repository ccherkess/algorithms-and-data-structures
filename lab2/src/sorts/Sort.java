package sorts;

import java.util.Comparator;

import lists.List;

public interface Sort {

	public <V extends Comparable<V>> void sort(V[] array);
	
	public <V> void sort(V[] array, Comparator<V> comparator);
	
	public <V extends Comparable<V>> void sort(List<V> list);
	
	public <V> void sort(List<V> list, Comparator<V> comparator);
	
	default void swap(Object[] list, int i, int j) {
		
		Object tmp = list[i];
		
		list[i] = list[j];
		
		list[j] = tmp;
		
	}
	
}
