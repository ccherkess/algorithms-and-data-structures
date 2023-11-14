package stack;

import lists.List;
import lists.LinkedList;

public class Stack<V> {

	/*
	 * Ссылка на "базу" стэка
	 * В качестве "базы" стэка может быть любая струтура, реализующая интерфейс List
	 */
	private List<V> stack;
	
	//конструктор по умолчанию
	public Stack() {
		
		stack = new LinkedList<V>(); //"базой" стэка по умолчанию является односвязный список
		
	}
	
	//конструктор, который позволяет пользователю установить "базу" стэка не по умолчанию 
	public Stack(List<V> stack) throws StackBaseIsNullPointerException {
		
		//если пользовательская "база" указывает на null - кидает иключение
		if (stack == null) throw new StackBaseIsNullPointerException();
		
		this.stack = stack;
		
	}
	
	//добавление элемента на вершину стека
	public Stack<V> push(V item) {
		
		stack.add(0, item);
		
		return this;
		
	}
	
	//извлечение элемента с вершины стека и удаление его из стека
	public V pop() throws StackIsEmptyException {
		
		//если стэк пуст - кидает иключение
		if (isEmpty()) throw new StackIsEmptyException("Невозможно извлечь элемент с вершины стэка");
		
		V result = stack.get(0);
		stack.remove(0);
		
		return result;
		
	}
	
	//извлечение элемента с вершины стека без удаления элемента из стэка
	public V top() {
		
		//если стэк пуст - кидает иключение
		if (isEmpty()) throw new StackIsEmptyException("Невозможно извлечь элемент с вершины стэка");
		
		return stack.get(0);
		
	}
	
	//проверка стэка на пустоту
	public boolean isEmpty() {
		
		return stack.size() == 0;
		
	}
	
	//получение размера стэка
	public int size() {
		
		return stack.size();
		
	}
	
	//получение строкового представления стэка
	@Override
	public String toString() {
		
		return stack.toString();
		
	}
}
