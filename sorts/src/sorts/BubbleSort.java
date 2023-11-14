package sorts;

import java.util.Comparator;

public class BubbleSort implements Sort {
	
	@Override
	public <V extends Comparable<V>> void sort(V[] array) {
	
		BubbleSorting(array, (v1, v2) -> v1.compareTo(v2));
		
	}

	@Override
	public <V> void sort(V[] array, Comparator<V> comparator) {
		
		BubbleSorting(array, comparator);
	}
	
	private <V> void BubbleSorting(V[] array, Comparator<V> comparator) {
		
		for (int i = 0; i <  array.length; i++) {
				
			boolean flag = true;
			
			for (int j = array.length - 1; j > i; j--) {
				
				if (comparator.compare(array[j], array[j-1]) < 0) {
					
					flag = false;
					
					swap(array, j, j-1);
					
				}
				
			}
			
			if (flag) break;
				
		}
	}
	
}
