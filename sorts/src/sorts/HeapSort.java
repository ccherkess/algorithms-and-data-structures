package sorts;

import java.util.Comparator;

public class HeapSort implements Sort {
	
	private Comparator<Object> comparator;
	
	@Override
	public <V extends Comparable<V>> void sort(V[] array) {
		
		HeapSorting(array, (v1, v2) -> v1.compareTo(v2));

	}

	@Override
	public <V> void sort(V[] array, Comparator<V> comparator) {
		
		HeapSorting(array, comparator);
		
	}
	
	@SuppressWarnings("unchecked")
	private <V> void HeapSorting(V[] array, Comparator<V> comparator) {
		
		this.comparator = (Comparator<Object>) comparator;
		
		buildHeap(array);
		
		for (int i = array.length - 1; i >= 0; i--) {
			
			swap(array, 0, i);

			heapify(array, 0, i);
			
		}	
		
	}
	
	private void buildHeap(Object[] array) {
		
		for (int i = (array.length - 2) / 2 + 1; i >= 0; i--) {
			heapify(array, i, array.length);
		}
		
	}
	
	private void heapify(Object[] array, int index, int len) {
		
		int l, r, largest;
		
		while (true) {
			
			l = 2 * index + 1;
			r = 2 * index + 2;
			largest = index;
			
			if (l < len && comparator.compare(array[l], array[largest]) > 0) {
				largest = l;
			}
			
			if (r < len && comparator.compare(array[r], array[largest]) > 0) {
				largest = r;
			}
			
			if (largest == index) break;
			else {
				
				swap(array, index, largest);
				index = largest;
				
			}
			
		}
		
	}

}
