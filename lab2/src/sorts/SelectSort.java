package sorts;

import java.util.Comparator;

import lists.List;

public class SelectSort implements Sort {
	
	@Override
	public <V extends Comparable<V>> void sort(V[] array) {
		
		SelectSorting(array, (v1, v2) -> v1.compareTo(v2));
		
	}

	@Override
	public <V> void sort(V[] array, Comparator<V> comparator) {
		
		SelectSorting(array, comparator);
		
	}
	
	@Override
	public <V extends Comparable<V>> void sort(List<V> list) {
		
		V[] array = list.toArray();
		
		SelectSorting(array, (V v1, V v2) -> v1.compareTo(v2));
		
		for (int i = 0; i < list.size(); i++) list.set(i, array[i]);
		
	}

	@Override
	public <V> void sort(List<V> list, Comparator<V> comparator) {
		
		V[] array = list.toArray();
		
		SelectSorting(array, comparator);
		
		for (int i = 0; i < list.size(); i++) list.set(i, array[i]);
		
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
