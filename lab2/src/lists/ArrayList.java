package lists;

import java.util.Arrays;

//класс, описывающий динамический массив
public class ArrayList<V>  implements List<V> {

	private Object[] array; //массив, хранящий данные
	private int capacity; //ёмкость списка
	private int size; //размер списка
	
	//конструктор по усолчанию
	public ArrayList() {
		
		capacity = 2;
		size = 0;
		
		array = new Object[capacity];
		
	}
	
	//конструктор, задающий начальный размер списка
	public ArrayList(int size) {
		
		capacity = size;
		this.size = size;
		
		array = new Object[capacity];
		
	}
	
	//добавление элемента в конец списка
	@Override
	public void add(V value) {
		
		add(size, value);
		
	}

	//добавление элемента в список по индексу
	@Override
	public void add(int index, V value) throws IndexOutOfListSizeException {
		
		//если индекс превышает размер списка или имеет отрицательное значение - кидаем исключение
		if (index > size || index < 0)
			throw new IndexOutOfListSizeException("Невозможно добавить элемент в список", index, size);
		
		//если размер списка равен ёмкость - расширяем список
		if (size == capacity) changesCapacity(size * 2);
		
		//освобождаем место под новый элемент
		for (int i = size; i > index; i--) {
			array[i] = array[i-1];
		}
		
		array[index] = value;
		size++;
		
	}

	//удаление элемента из списка по индексу
	@Override
	public void remove(int index) throws IndexOutOfListSizeException{
		
		//если индекс превышает размер списка или имеет отрицательное значение - кидаем исключение
		if (index >= size || index < 0) 
			throw new IndexOutOfListSizeException("Невозможно удалить элемент", index, size);
		
		//удаляем элемент
		for (int i = index; i < size - 1; i++) {
			array[i] = array[i+1];
		}
		
		array[--size] = null;
		
		if (size == capacity / 2) changesCapacity(size);
		
	}

	//получение элемента по индексу
	@SuppressWarnings("unchecked")
	@Override
	public V get(int index) throws IndexOutOfListSizeException {
		
		//если индекс превышает размер списка или имеет отрицательное значение - кидаем исключение
		if (index >= size || index < 0) 
			throw new IndexOutOfListSizeException("Невозможно получить элемент", index, size);
		
		return (V) array[index];
		
	}
	
	@Override
	public void set(int index, V value) throws IndexOutOfListSizeException {
		
		//если индекс превышает размер списка или имеет отрицательное значение - кидаем исключение
		if (index >= size || index < 0) 
				throw new IndexOutOfListSizeException("Невозможно установить значение элементу", index, size);
		
		array[index] = value;
		
	}

	//получение размера списка
	@Override
	public int size() {
		
		return size;
		
	}
	
	//получение строкового представления списка
	@Override
	public String toString() {
		
		StringBuilder result = new StringBuilder();
		
		result.append("(");
		
		for (int i = 0; i < size; i++) {
			
			result.append(array[i].toString());
			
			if (i + 1 != size) result.append(", ");
		}
		
		result.append(")");
		
		return result.toString();
		
	}
	
	//изменяет ёмкость списка на задданную  
	private void changesCapacity(int newCapacity) {
		
		capacity = newCapacity;
		
		Object[] array = new Object[capacity];
		
		for (int i = 0; i < size; i++) {
			array[i] = this.array[i];
		}
		
		this.array = array;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public V[] toArray() {
		
		return (V[]) Arrays.copyOf(this.array, size);
	}

	@Override
	public List<V> copy() {
		
		ArrayList<V> copyList = new ArrayList<>();
		
		copyList.array = Arrays.copyOf(this.array, size);
		copyList.capacity = this.capacity;
		copyList.size = this.size;
		
		return copyList;
		
	}

}
