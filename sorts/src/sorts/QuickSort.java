package sorts;

import java.util.Comparator;

public class QuickSort implements Sort {
	
	@Override
	public <V extends Comparable<V>> void sort(V[] array) {
		
		QuickSorting(array, 0, array.length - 1, (v1, v2) -> v1.compareTo(v2));
		
	}

	@Override
	public <V> void sort(V[] array, Comparator<V> comparator) {
		
		QuickSorting(array, 0, array.length - 1, comparator);
		
	}
	
	private <V> void QuickSorting(V[] array, int l, int r, Comparator<V> comparator) {
		
		if (l < r) {
			
			int pIndex = partition(array, l, r, comparator);
			
			QuickSorting(array, l, pIndex - 1, comparator);
			QuickSorting(array, pIndex + 1, r, comparator);
			
		}
		
	}
	
	private <V> int partition(V[] array, int l, int r, Comparator<V> comparator) {
		
		V pivot = array[r];
		int pIndex = l;
		
		for (int i = l; i < r; i++) {
			
			if (comparator.compare(pivot, array[i]) > 0) {
				
				swap(array, i, pIndex++);
				
			}
			
		}
		
		swap(array, r, pIndex);
		
		return pIndex;
		
	}

}
