package lists;

import java.util.Comparator;

import sorts.TimSort;

public interface List<V> {

	/*
	 * вставить элемент в конец списка
	 */
	public void add(V value);
	
	/*
	 * вставить элемент в список по заданному индексу 
	 * Если индекс выходит за пределы размера списка - кидает исключение
	 */
	public void add(int index, V value) throws IndexOutOfListSizeException;
	
	/*
	 * удалить элемент из списка по заданному индексу
	 * Если индекс выходит за пределы размера списка - кидает исключение
	 */
	public void remove(int index) throws IndexOutOfListSizeException;
	
	/*
	 * получить значение элемента по заданному индексу
	 * Если индекс выходит за пределы размера списка - кидает исключение
	 */
	public V get(int index) throws IndexOutOfListSizeException;
	
	/*
	 * установить значение элемента по заданному индексу
	 * Если индекс выходит за пределы размера списка - кидает исключение
	 */
	public void set(int index, V value) throws IndexOutOfListSizeException;
	
	/*
	 * получить размер списка
	 */
	public int size();
	
	/*
	 * Возращает список в виде массива
	 */
	public V[] toArray();
	
	public List<V> copy();
	
	/*
	 * Сортировка списка
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	default public void sort(Comparator<V> comparator) {
		
		Object[] array = this.toArray();
		
		new TimSort().sort(array,(Comparator) comparator);
		
		for (int i = 0; i < this.size(); i++) this.set(i, (V) array[i]);
		
	}
	
}
