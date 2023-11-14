package tree;

import java.util.Comparator;
import java.util.Stack;

public class AVLTree<V> extends BinarySearchTree<V> {

	private static class Node<V> implements abstractNode<V> {
		
		public V value;
		public int height;
		public Node<V> left;
		public Node<V> right;
		
		public Node(V value) {
			this.value = value;
			height = 1;
		}
		
		@Override
		public V getValue() {
			return value;
		}
		@Override
		public void setValue(V value) {
			this.value = value;
		}
		
		@Override
		public abstractNode<V> getLeft() {
			return left;
		}
		@Override
		public abstractNode<V> getRight() {
			return right;
		}
		@Override
		public void setLeft(abstractNode<V> node) {
			left = (Node<V>) node;
		}
		@Override
		public void setRight(abstractNode<V> node) {
			right = (Node<V>) node;
		}
		
		@Override
		public String toString() {
			return value.toString();
		}
	}

	public AVLTree() {
		super();
	}
	
	public AVLTree(Comparator<V> comparator) {
		super(comparator);
	}
	
	public AVLTree(BinaryTree<V> tree) {
		super(tree);
	}
	
	//получение баланса узла
	private int height(Node<V> node) {
		return (node == null) ? 0 : node.height;
	}
	
	//подсчёт баланса узла
	private int getBalance(Node<V> node) {
		if (node == null) return 0; 
		return (node.right != null ? node.right.height : 0) - (node.left != null ? node.left.height : 0);
	}
	
	//пересчёт высоты узла
	private void fixHeight(Node<V> node) {
		
		int leftHeight = height(node.left);
		int rightHeight = height(node.right);
		
		node.height = ((leftHeight > rightHeight) ? leftHeight : rightHeight) + 1;
		
	}
	
	private Node<V> rotateRight(Node<V> root) {
		
		/*
		 * P - root (корень)
		 * L - root.left (левый потом корня)
		 * R - L.right (правый потомок левого корня)
		 * 
		 * Сохраняем левого потомка P.
		 * Делаем R левым потомком P.
		 * Делаем P правым потомом L.
		 * 
		 * Так происходит правый поворот.
		 */
		
		Node<V> L = root.left;
		root.left = L.right;
		L.right = root;
		
		fixHeight(root);
		fixHeight(L);
		
		return L;
	}
	
	private Node<V> rotateLeft(Node<V> root) {
		
		/*
		 * P - root (корень)
		 * R - root.right (правый потом корня)
		 * L - R.left (левый потомок правого корня)
		 * 
		 * Сохраняем правого потомка P.
		 * Делаем R правым потомком P.
		 * Делаем P левым потомом L.
		 * 
		 * Так происходит левый поворот.
		 */
		
		Node<V> R = root.right;
		root.right = R.left;
		R.left = root;
		
		fixHeight(root);
		fixHeight(R);
		
		return R;
	}
	
	private Node<V> balance(Node<V> root) {
		
		fixHeight(root); //пересчёт высоты
		
		if (getBalance(root) == 2) { //перевес в право
			
			/*
			 * P - root.right
			 * R - P.right
			 * L - P.left
			 * 
			 * Если L больше R (баланс P отрицательный),
			 * то совершаем правый поворот P.
			 * Таким образом получается правый-левый поворот.
			 */
			if (getBalance(root.right) < 0) root.right = rotateRight(root.right);
			
			return rotateLeft(root);
			
		} else if (getBalance(root) == -2) { //перевес в лево
			
			/*
			 * P - root.right
			 * R - P.right
			 * L - P.left
			 * 
			 * Если R больше L (баланс P положительный),
			 * то совершаем левый поворот P.
			 * Таким образом получается левый-правый поворот.
			 */
			if (getBalance(root.left) > 0) root.left = rotateLeft(root.left);
			
			return rotateRight(root);
			
		}
		
		return root;
	}
	
	private void balance(Stack<abstractNode<V>> stack) {
	
		Node<V> tmp;
		
		while(stack.size() > 1) {
			
			tmp = (Node<V>) stack.pop();
			
			
			if (stack.peek().getLeft() == tmp) {
				stack.peek().setLeft(balance(tmp));
			} else {
				stack.peek().setRight(balance(tmp));
			}
		}
	}
	
	@Override
	public void add(V value) {
		
		/*
		 * Если дерево пустое, то добавляем элемент в корень.
		 */
		if (root == null) {
			root = new Node<>(value);
		}
		else {
		
			Stack<abstractNode<V>> stack = super.findIter(value);
			
			Node<V> tmp = (Node<V>) stack.pop();
			
			if (tmp != null) return; //элемент уже есть в дереве
			else {
				
				/*
				 *Элемента нет в дереве, добавляем его 
				 */
				tmp = (Node<V>) stack.peek();
				if (compare(tmp.value, value) > 0) tmp.left = new Node<>(value);
				else tmp.right = new Node<>(value);
				
			}
			
			//балансируем дерево
			balance(stack);
			
			root = balance((Node<V>) stack.pop());
			
		}
		
		size++;
	}
	
	@Override
	public void remove(V value) {
		
		root = remove(value, (Node<V>) root);
		if (root != null) root = balance((Node<V>) root);
		size--;
		
	}
	
	private Node<V> remove(V value, Node<V> root) {
		
		Stack<abstractNode<V>> stack = findIter(value);
		
		Node<V> tmp = (Node<V>) stack.pop();
		Node<V> parent = stack.isEmpty() ? null : (Node<V>) stack.peek();
		
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
		
		balance(stack);
		
		return root;
		
	}
	
}
