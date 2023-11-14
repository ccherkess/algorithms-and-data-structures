package lists;

//класс, описывающий односвязный список
public class LinkedList<V> implements List<V> {
	
	//класс, описывающий узел односвязного списка
	private class Node {
		
		public V value; //значение узла
		public Node next; //сслылка на следующий узел
		
		public Node() {
			value = null;
			next = null;
		}
		
		public Node(V value) {
			this.value = value;
			next = null;
		}
		
		public Node(V value, Node next) {
			this.value = value;
			this.next = next;
		}
		
		@Override
		public String toString() {
			return value.toString();
		}
	}
	
	private Node head, tail; //ссылки на начало и конец списка
	private int size; //размер списка
	
	public LinkedList() {
		
		head = tail = null;
		size = 0;
		
	}
	
	//создает связный список заданного размера
	public LinkedList(int size) {
		
		this.size = size;
		
		tail = head = new Node();
		
		for (int i = 1; i < size; i++) {
			tail.next = new Node();
			tail = tail.next;
		}
	}
	
	//возвращает узел списка по индексу
	private Node find(int index) {
		
		Node tmp = head;
		
		for(int i = 0; i < index; i++) tmp = tmp.next;
		
		return tmp;
		
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
			throw new IndexOutOfListSizeException("Невозможно добавить элемент", index, size);
		
		if (head == null) tail = head = new Node(value); //если список пуст
		
		else if (index == 0) head = new Node(value, head); //если требуется вставить в начало списка
		else if (index == size) { //если требуется вставка в конец списка
			
			tail.next = new Node(value);
			tail = tail.next;
			
		} else { //вставка в любое место списка
			
			Node tmp = find(index-1);
			tmp.next = new Node(value, tmp.next);
			
		}
		
		size++;
	}

	@Override
	public void remove(int index) throws IndexOutOfListSizeException {
		
		//если индекс превышает размер списка или имеет отрицательное значение - кидаем исключение
		if (index >= size || index < 0) 
			throw new IndexOutOfListSizeException("Невозможно удалить элемент", index, size);
		
		if (index == 0) head = head.next; //если требуется удалить из начала списка
		else { //если требуется удалить из любого места списка
			
			Node tmp = find(index-1);
			tmp.next = tmp.next.next;
			
		}
		
		size--;
		
	}

	@Override
	public V get(int index) throws IndexOutOfListSizeException {
		
		//если индекс превышает размер списка или имеет отрицательное значение - кидаем исключение
		if (index >= size || index < 0) 
			throw new IndexOutOfListSizeException("Невозможно получить элемент", index, size);
		
		V result = null;
		
		if (index == 0) result =  head.value; //если искомый элемента в начале списка
		else if (index == size - 1) result = tail.value; //если искомый элемент в конце списка
		else { //если искомый элемент в любом месте списка
			
			Node tmp = find(index);
			result = tmp.value;
			
		}
		
		return result;
	}

	@Override
	public int size() {
		return size;
	}

	//получение строкового представления списка
	@Override
	public String toString() {
		
		StringBuilder result = new StringBuilder();
		Node tmp = head;
		
		result.append("(");
		
		for (int i = 0; i < size; i++) {
			
			result.append(tmp.value.toString());
			tmp = tmp.next;
			
			if (i + 1 != size) result.append(", ");
		}
		
		result.append(")");
	
		return result.toString();
	}
}
