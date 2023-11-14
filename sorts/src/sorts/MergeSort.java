package sorts;

import java.util.Comparator;

public class MergeSort implements Sort {
	
	@Override
	public <V extends Comparable<V>> void sort(V[] array) {
		
		MergeSorting(array, 0, array.length - 1, (v1, v2) -> v1.compareTo(v2));		
		
	}

	@Override
	public <V> void sort(V[] array, Comparator<V> comparator) {
		
		MergeSorting(array, 0, array.length - 1, comparator);
		
	}
	
	private <V> void MergeSorting(V[] array, int l, int r, Comparator<V> comparator) {
		
		if (l < r) {
			
			int m = (r + l) / 2;
			
			MergeSorting(array, l, m, comparator);
			MergeSorting(array, m+1, r, comparator);
			
			merge(array, l, m, r, comparator);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	private static <T> void merge(T[] array, int l, int m, int r, Comparator<T> comparator) {
		
		T[] leftArray = (T[]) new Object[m - l + 1];
		T[] rightArray = (T[]) new Object[r - m];
		
		for (int i = 0; i < leftArray.length; i++) leftArray[i] = array[i + l];
		for (int i = 0; i < rightArray.length; i++) rightArray[i] = array[i + m + 1];
		
		int i = 0, j = 0, k = l;
		
		while (i < leftArray.length && j < rightArray.length) {
			
			if (comparator.compare(leftArray[i], rightArray[j]) < 0) {
				
				array[k++] = leftArray[i++]; 
				
			} else array[k++] = rightArray[j++]; 
			
		}
		
		while (i < leftArray.length) array[k++] = leftArray[i++]; 
		while (j < rightArray.length) array[k++] = rightArray[j++]; 
		
	}
	
}
