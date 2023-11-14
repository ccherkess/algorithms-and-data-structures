package lists;

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
	 * получить размер списка
	 */
	public int size();
}
