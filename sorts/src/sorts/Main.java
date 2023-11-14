package sorts;

import java.util.Arrays;
import java.util.Date;
import java.util.Random;

public class Main {

	public static void main(String[] args) throws CloneNotSupportedException {
		
		test();
	
	}
	
	public static void test() {
		
		Sort[] sorts = { new MergeSort(), new TimSort(), new HeapSort()};
		
		Integer[] array = getRandomArray();
		long before, after;
		
		Integer[] arrayToSort = null;
		
		for (Sort sort: sorts) {
			
			arrayToSort = array.clone();
			
			before = new Date().getTime();
			sort.sort(arrayToSort);
			after = new Date().getTime();
			
			System.out.println(sort.getClass() + ": " + (after - before) + " ms.");
			
		}
		
		before = new Date().getTime();
		Arrays.sort(array);
		after = new Date().getTime();
		
		System.out.println("Java standard sort: " + (after - before) + " ms.");
		
		System.out.println(Arrays.equals(array, arrayToSort));
		
	}
	
	public static Integer[] getRandomArray() {
		
		Random random = new Random();
		
		int n = random.nextInt(1000000);
		n = 1000000;
		
		Integer[] array = new Integer[n];
		
		for (int i = 0; i < n; i++) {
			array[i] = random.nextInt(1000000);
		}
		
		return array;
	}

}
