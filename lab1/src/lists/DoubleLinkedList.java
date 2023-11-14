package lists;

public class DoubleLinkedList<V> implements List<V> {
	
	private class Node {
		
		public V value;
		public Node next;
		public Node last;
		
		public Node() {
			
			value = null;
			next = null;
			last = null;
			
		}
		
		public Node(V value) {
			
			this.value = value;
			next = null;
			last = null;
			
		}
		
		public Node(V value, Node next) {
			
			this.value = value;
			this.next = next;
			last = null;
			
		}
		
		public Node(V value, Node next, Node last) {
			
			this.value = value;
			this.next = next;
			this.last = last;
			
		}
	}
	
	private Node head;
	private Node tail;
	private int size;
	
	public DoubleLinkedList() {
		
		size = 0;
		tail = head = null;
		
	}
	
	//создает двусвязный список заданного размера
	public DoubleLinkedList(int size) {
		
		this.size = size;
		
		tail = head = new Node();
		
		for (int i = 1; i < size; i++) {
			tail.next = new Node(null, null, tail);
			tail = tail.next;
		}
		
	}
	
	private Node find(int index) {
		
		Node tmp;
		
		if (index < size / 2) {
			
			tmp = head;
			
			for (int i = 0; i < index; i++) tmp = tmp.next;
			
		} else {
			
			tmp = tail;
			
			for (int i = size - 1; i > index; i--) tmp = tmp.last;
			
		}
		
		return tmp;
		
	}

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
				
				tail.next = new Node(value, null, tail);
				tail = tail.next;
				
			} else { //вставка в любое место списка
				
				Node tmp = find(index-1);
				tmp.next = new Node(value, tmp.next, tmp.last);
				
			}
			
			size++;
		}

		@Override
		public void remove(int index) throws IndexOutOfListSizeException {
			
			//если индекс превышает размер списка или имеет отрицательное значение - кидаем исключение
			if (index >= size || index < 0) 
				throw new IndexOutOfListSizeException("Невозможно удалить элемент", index, size);
			
			if (index == 0) { //если требуется удалить из начала списка
				
				head = head.next;
				if (head != null) head.last = null;
				
			} else if (index == size - 1) {
			
				tail = tail.last;
				tail.next = null;
				
			} else { //если требуется удалить из любого места списка
				
				Node tmp = find(index-1);
				tmp.next = tmp.next.next;
				tmp.next.last = tmp;
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

