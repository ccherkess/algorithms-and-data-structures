package lab2;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;

import lists.*;
import sorts.*;


public class Main {
	
	private static Scanner input;

	public static void main(String[] args) {
		
		List<Integer> list = new ArrayList<>();
		
		Comparator<Integer> comparator = (i1, i2) -> i1.compareTo(i2);
		
		input = new Scanner(System.in);

		while (true) {
			
			switch(commands()) {
			case 1:
				
				initRandomList(list);
				
				break;
				
			case 2:
				
				list = initList();
				
				break;
				
			case 3:
				
				System.out.println(list);
				
				break;
				
			case 4:
				
				sort(list, comparator);
				
				break;
				
			case 5:
				
				checkSorted(list, comparator);
				
				break;
				
			case 6:
				
				System.out.println(list.size());
				
				break;
				
			case 7:
					
				System.out.print("Введите индекс элемента: ");
				int index = -1;
				
				if (input.hasNext()) index =  input.nextInt();
				
				System.out.print("Введите элемент: ");
				
				int element = 0;
				
				if (input.hasNext()) element = input.nextInt();
				
				list.add(index, element);
					
				break;
			
			case 8:
					
				System.out.print("Введите индекс элемента: ");
				index = -1;
				if (input.hasNext()) index = input.nextInt();
				
				list.remove(index);
				
				break;
				
			case 0:
				
				System.exit(0);
			
			default:
				System.out.println("Такой команды нет =(");
			}
				
			
		}
		
	}
	
	private static <V> int commands() {
		
		int result = -1;
	
		System.out.println("Список команд:");
		System.out.println("1) Сгенерировать список рандомно.");
		System.out.println("2) Сгенерировать список ручками.");
		System.out.println("3) Посмотреть список.");
		System.out.println("4) Отсортировать список.");
		System.out.println("5) Проверить отсортирован список или нет.");
		System.out.println("6) Посмотреть длину списка.");
		System.out.println("7) Добавить элемент в список.");
		System.out.println("8) Удалить элемент из списка");
		
		System.out.println("0) Выйти из программы.");
		
		System.out.print("Введите номер команды: ");
		if (input.hasNext()) result = input.nextInt();
		
		return result;
		
	}
	
	private static void initRandomList(List<Integer> list) {
		
		Random random = new Random();
		
		int n = random.nextInt(1000000);
		
		for (int i = 0; i < n; i++) list.add(random.nextInt(10000));
		
	}
	
	private static List<Integer> initList() {
		
		List<Integer> list;
			
		System.out.print("Введите длину списка: ");
		list = new ArrayList<>(input.nextInt());
		
		boolean flag = true;
		
		while (flag) {
		
			System.out.println("Способы заполнить список:");
			System.out.println("1) Заполнить список рандомными значениями.");
			System.out.println("2) Заполнить список ручками.");
			System.out.print("Введите номер способа: ");
			
			switch (input.nextInt()) {
			case 1:
				
				Random random = new Random();
				for (int i = 0; i < list.size(); i++) list.set(i, random.nextInt(10000));
				
				flag = false;
				
				break;
				
			case 2:
				
				System.out.println("Введите элементы списка:");
				for (int i = 0; i < list.size(); i++) {
					if (input.hasNext()) list.set(i, input.nextInt());
				}
				
				flag = false;
				
				break;
			
			default:
				
				System.out.println("Такого способа нет =(");
				break;
				
			}
		
		}
		
		return list;
		
	}
	
	private static <V> void sort(List<V> list, Comparator<V> comparator) {
		
		long before, after;
		
		if (list.size() <= 10000) {
			
			before = System.currentTimeMillis();
			new SelectSort().sort(list.copy(), comparator);
			after = System.currentTimeMillis();
			
			System.out.println("SelectSort: " + (after - before) + "ms.");
			
		}
		
		before = System.currentTimeMillis();
		new MergeSort().sort(list.copy(), comparator);
		after = System.currentTimeMillis();
		
		System.out.println("MergeSort: " + (after - before) + "ms.");
		
		before = System.currentTimeMillis();
		list.sort(comparator);
		after = System.currentTimeMillis();
		
		System.out.println("TimSort: " + (after - before) + "ms.");
		
	}
	
	@SuppressWarnings("unchecked")
	private static <V> void checkSorted(List<V> list, Comparator<V> comparator) {
		
		V[] array = (V[]) list.toArray();
		java.util.List<Object> javaList = Arrays.asList(array);
		
		javaList.sort((Comparator<? super Object>) comparator);;
		
		boolean flag = true;
		
		for (int i = 0; i < list.size(); i++) {
			
			if (!list.get(i).equals(javaList.get(i))) {
				
				flag = false;
				break;
				
			}
			
		}
		
		System.out.println((flag) ? "Список отсортирован." : "Список не отсортирован.");
		
	}

}
