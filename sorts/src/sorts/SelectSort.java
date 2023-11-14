package sorts;

import java.util.Comparator;

public class SelectSort implements Sort {
	
	@Override
	public <V extends Comparable<V>> void sort(V[] array) {
		
		SelectSorting(array, (v1, v2) -> v1.compareTo(v2));
		
	}

	@Override
	public <V> void sort(V[] array, Comparator<V> comparator) {
		
		SelectSorting(array, comparator);
		
	}
	
	private <V> void SelectSorting(V[] array, Comparator<V> comparator) {
		
		for (int i = 0; i < array.length; i++) {
			
			int index = i;
			
			for (int j = i + 1; j < array.length; j++) {
				
				if (comparator.compare(array[index], array[j]) > 0) index = j;
				
			}
			
			if (index != i) {
				
				swap(array, index, i);
				
			}
			
		}
		
	}
	
}
