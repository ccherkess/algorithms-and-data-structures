import lists.*;

import static sotring_station_algorithm.SortingStationAlgorithm.parsingPostfixForm;
//import static sotring_station_algorithm.SortingStationAlgorithm.calculateExpression;

public class Main {

	public static void main(String[] args) {
		
		try {
			System.out.println(parsingPostfixForm("10 - cos( 10 / 9"));
		} catch (Exception e) {
			
			System.out.println(e);
		}
		
	}

	public static void testList() {
		
		//java.util.List<Integer> list = new java.util.ArrayList<>(); 
		//List<Integer> list = new ArrayList<>();
		//List<Integer> list = new LinkedList<>();
		List<Integer> list = new DoubleLinkedList<>();
		
		for (int i = 0; i < 10; i++) list.add(i+1);
		
		System.out.println(list);
		
		for (int i = 0; i < 10; i++) list.add(0, i+1);
		
		System.out.println(list);
		
		list.add(list.size() / 2, 0);
		
		System.out.println(list);
		
		for (int i = 0; i < 5; i++) list.remove(list.size() / 2);
		
		System.out.println(list);
		
		for (int i = 0; i < 5; i++) {
			list.remove(0);
			list.remove(list.size()-1);
		}
		
		System.out.println(list);
		
		while (list.size() != 0) {
			
			list.remove(list.size() / 2);
			System.out.println(list);
			
		}
	
	}

}

