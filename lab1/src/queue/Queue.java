package queue;

import lists.List;
import lists.DoubleLinkedList;

public class Queue<V> {

	/*
	 * Ссылка на "базу" очереди
	 * В качестве "базы" очереди может быть любая струтура, реализующая интерфейс List
	 */
	private List<V> queue;
	
	//конструктор по умолчанию
	public Queue() {
		
		queue = new DoubleLinkedList<V>(); //"базой" очереди по умолчанию является двусвязный список
		
	}
	
	//конструктор, который позволяет пользователю установить "базу" стэка не по умолчанию 
	public Queue(List<V> queue) throws QueueBaseIsNullPointerException {
		
		//если пользовательская "база" указывает на null - кидает иключение
		if (queue == null) throw new QueueBaseIsNullPointerException();
		
		this.queue = queue;
		
	}
	
	//добавление элемента в конец очереди
	public Queue<V> push(V item) {
		
		queue.add(item);
		
		return this;
		
	}
	
	//извлечение элемента из начала очерди и удаление его из очереди
	public V pop() throws QueueIsEmptyException {
		
		//если стэк пуст - кидает иключение
		if (isEmpty()) throw new QueueIsEmptyException("Невозможно извлечь элемент из начала очереди");
		
		V result = queue.get(0);
		queue.remove(0);
		
		return result;
		
	}
	
	//извлечение элемента из начала очереди без удаления элемента из очереди
	public V top() throws QueueIsEmptyException {
		
		//если очередь пуста - кидает иключение
		if (isEmpty()) throw new QueueIsEmptyException("Невозможно извлечь элемент из начала очереди");
		
		return queue.get(0);
		
	}
	
	//проверка очереди на пустоту
	public boolean isEmpty() {
		
		return queue.size() == 0;
		
	}
	
	//получение размера очереди
	public int size() {
		
		return queue.size();
		
	}
	
	//получение строкового представления очереди
	@Override
	public String toString() {
		
		return queue.toString();
		
	}
}