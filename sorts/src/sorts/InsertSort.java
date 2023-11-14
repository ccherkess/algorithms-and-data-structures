package sorts;

import java.util.Comparator;


public class InsertSort implements Sort {

	@Override
	public <V extends Comparable<V>> void sort(V[] array) {
		
		InsertSorting(array, (v1, v2) -> v1.compareTo(v2));
		
	}

	@Override
	public <V> void sort(V[] array, Comparator<V> comparator) {
		
		InsertSorting(array, comparator);
		
	}

	
	public <V> void InsertSorting(V[] array, Comparator<V> comparator) {
		
		for (int i = 1; i < array.length; i++) {
			
			V value = array[i];
			
			int j = i - 1;
			
			while (j >= 0 && comparator.compare(value, array[j]) < 0) {
				
				array[j + 1] = array[j];
				j--;
				
			}
			
			array[j+1] = value;
			
		}
		
	}
	
}
