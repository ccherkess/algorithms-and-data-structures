package tree;

import java.util.Comparator;
import java.util.Stack;

public class BinarySearchTree<V> extends BinaryTree<V> {

	private Comparator<V> comparator;
	
	public BinarySearchTree() {}
	
	public BinarySearchTree(Comparator<V> comparator) {
		
		this.comparator = comparator;
		
	}
	
	public BinarySearchTree(BinaryTree<V> tree) {
		
		tree.recPreOrder(this::add, true);
		
	}
	
    @SuppressWarnings("unchecked")
	protected final int compare(V v1, V v2) {
        return comparator == null ? ((Comparable<? super V>)v1).compareTo(v2)
            : comparator.compare(v1, v2);
    }
    
    /*
     * Поиск минимального узла в дереве,
     * который по определению находится левее всех
     */
    protected final abstractNode<V> getMin(abstractNode<V> root) {
    	
    	while (root.getLeft() != null) root = root.getLeft();
    	
    	return root;
    	
    }
    
    /*
	 * Метод для поиска узла по значению.
	 * Сохраняет в стэке путь к узлу.
	 * Если на вершине стэка null - значение не найдено.
	 */
	protected Stack<abstractNode<V>> findIter(V value) {
		
		Stack<abstractNode<V>> stack = new Stack<>();
		
		abstractNode<V> tmp = this.root;
		
		while (tmp != null) {
			
			int compare = compare(tmp.getValue(), value); //сравнивваем искомы элемент с текущим 
			
			if (compare == 0) break; //элемент найден
			else {
				stack.push(tmp);
				/*
				 * Текущий элемент меньше искомого
				 * Следовательно, искомый должен находится в левом поддереве
				 */
				if (compare > 0) tmp = tmp.getLeft(); 
				/*
				 * Текущий элемент больше искомого
				 * Следовательно, искомый должен находится в правом поддереве
				 */
				else tmp = tmp.getRight();
			}
			
		}
		
		stack.push(tmp);
		
		return stack;
		
	}
    
	@Override
	public void add(V value) {
		
		/*
		 * Если дерево пустое, то добавляем элемент в корень.
		 */
		if (root == null) root = new Node<>(value);
		else {
		
			Stack<abstractNode<V>> stack = findIter(value);
			
			Node<V> tmp = (Node<V>) stack.pop();
			
			if (tmp != null) return; //элемент уже есть в дереве
			else {
				
				/*
				 *Элемента нет в дереве, добавляем его 
				 */
				tmp = (Node<V>) stack.pop();
				if (compare(tmp.value, value) > 0) tmp.left = new Node<>(value);
				else tmp.right = new Node<>(value);
				
			}
		}
		
		size++;
	}
	
	@Override
	public void remove(V value) {
		
		root = remove(value, (Node<V>) root);
		size--;
		
	}
	
	
	private Node<V> remove(V value, Node<V> root) {
		
		Stack<abstractNode<V>> stack = findIter(value);
		
		Node<V> tmp = (Node<V>) stack.pop();
		Node<V> parent = stack.isEmpty() ? null : (Node<V>) stack.pop();
		
		if (tmp == null) return root; //элемент не найден
		
		if (tmp.left == null && tmp.right == null) { //у узла нет потомков
			
			if (tmp == root) root = null;
			else {
	
				if (parent.left == tmp) parent.left = null;
				else parent.right = null;
				
			}

		} else if (tmp.left == null || tmp.right == null) { //у узла есть один потомок
			
			Node<V> child = tmp.left == null ? tmp.right : tmp.left;
			
			if (tmp == root) root = child;
			else {
				if (parent.left == tmp) parent.left = child;
				else parent.right = child;
			}
			
		} else { //у узла два потомка
			
			/*
			 * Ищем элемент, который встанет на место удаляемого
			 */
			Node<V> successor = (Node<V>) getMin(tmp.right);
			
			tmp.right = remove(successor.value, tmp.right); //удаляем его из дерева
			
			tmp.value = successor.value; //вставляем его на место удаляемого
			
		}
		
		return root;
	}
	
	@Override
	public V find(V value) {
		
		abstractNode<V> tmp = findIter(value).pop();
		
		return tmp == null ? null : tmp.getValue();
		
	}
	
}
