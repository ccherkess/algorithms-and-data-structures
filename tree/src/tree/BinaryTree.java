package tree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.function.Consumer;

public abstract class BinaryTree <V> {
	
	protected static class Node<V> implements abstractNode<V> {
		
		public V value;
		public Node<V> left;
		public Node<V> right;
		
		Node() {};
		
		Node(V value) {
			this.value = value;
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
			this.left = (Node<V>) node;
		}

		@Override
		public void setRight(abstractNode<V> node) {
			this.right = (Node<V>) node;
		}
		
		@Override
		public String toString() {
			return value.toString();
		}
		
	}
	
	protected int size; //размер дерева
	protected abstractNode<V> root; //корневой элемент
	
	/**
	 * Метод для добавления нового элемента в дерево.
	 * 
	 * @param value - элемент, который нужно добавить.
	 * @return Метод ничего не возвращает.
	 */
	abstract public void add(V value);
	
	/**
	 * Метод для удаления элемента из дерева.
	 * 
	 * @param value - элемент, который нужно удалить.
	 * @return Метод ничего не возвращает.
	 */
	abstract public void remove(V value);
	
	/**
	 * Метод для поиска элемента в дереве.
	 * 
	 * @param value - элемент, который нужно найти.
	 * @return Если элемент найден, то метод вернет его. Иначе он вернет null.
	 */
	abstract public V find(V value);

	public static BinaryTree<Integer> parsingFromString(String s) {
		
		/*
		 * Создание обычного бинарного дерева,
		 * которое не поддерживает вставку, удаление, поиск элементов.
		 */
		BinaryTree<Integer> tree = new BinaryTree<>() {

			@Override
			public void add(Integer value) {
				
				throw new UnsupportedOperationException("The usual binary tree is not changeable!");
				
			}

			@Override
			public void remove(Integer value) {
				
				throw new UnsupportedOperationException("The usual binary tree is not changeable!");
				
			}

			@Override
			public Integer find(Integer value) {
				
				throw new UnsupportedOperationException("The usual binary tree is not changeable!");
				
			}
			
		};
		
		Stack<Node<Integer>> stack = new Stack<>();
		Stack<Character> staples = new Stack<>();
		
		for (int i = 0; i < s.length(); i++) {
			
			if (s.charAt(i) == '(') {
				stack.push(new Node<>());
				staples.push('(');
			}
			
			if (s.charAt(i) == ')') {
				
				if (stack.size() > 1) {
					Node<Integer> child = stack.pop();
					Node<Integer> parent = stack.peek();
					
					if (child.value == null) throw new RuntimeException("Ошибка в скобочной записи!"); 
					
					if (parent.left == null) parent.left = child;
					else if (parent.right == null) parent.right = child;
					else throw new RuntimeException("Данное дерево не бинарное!");
				}
				staples.pop();
			}
			
			if (47 < s.charAt(i) && s.charAt(i) < 58) {
				
				int j = i;
				
				while (47 < s.charAt(i) && s.charAt(i) < 58) i++;
				
				if (stack.peek().value == null) stack.peek().value = Integer.parseInt(s.substring(j, i));
				else throw new RuntimeException("Ошибка в скобочной записи!"); 
					
				i--;
				
			}
			
		}
		
		tree.root = stack.pop();
		
		if (!staples.isEmpty()) throw new RuntimeException("Отсутствует скобка!");
		
		return tree;
	}
	
	/**
	 * Прямой рекурсивный обход дерева.
	 * Центр - Лево - Право.
	 * 
	 * @param func - объект для обработки значений.
	 * @return Метод ничего не возвращает.
	 */
	public void recPreOrder(Consumer<V> func) {
		
		recPreOrderRec(root, func);
		
	}
	
	/**
	 * Прямой обход дерева.
	 * Центр - Лево - Право.
	 * 
	 * @param func - объект для обработки значений.
	 * 
	 * @param flag - если значение true - обход осуществляется итеративно, иначе - рекурсивно.
	 * 
	 * @return Метод ничего не возвращает.
	 */
	public void recPreOrder(Consumer<V> func, boolean flag) {
		
		if (flag) recPreOrderIter(root, func);
		else recPreOrderRec(root, func);
		
	}
	
	//прямой обход
	private void recPreOrderRec(abstractNode<V> root, Consumer<V> func) {
		
		if (root == null) return;
		
		func.accept(root.getValue());
		
		recPreOrderRec(root.getLeft(), func);
		recPreOrderRec(root.getRight(), func);

	}
	
	//прямой обход
	private void recPreOrderIter(abstractNode<V> root, Consumer<V> func) {
		
		Stack<abstractNode<V>> stack = new Stack<>();

		while (root != null || !stack.isEmpty()) {
			
			if (!stack.isEmpty()) root = stack.pop();
			
			while (root != null) {
				
				func.accept(root.getValue());
				
				if (root.getRight() != null) stack.push(root.getRight());
				
				root = root.getLeft();
				
			}
			
		}
		
	}
	
	/**
	 * Центрированный обход дерева.
	 * Лево - Цент - Право.
	 * 
	 * @param func - объект для обработки значений.
	 * 
	 * @return Метод ничего не возвращает.
	 */
	public void recInOrder(Consumer<V> func) {
		
		recInOrderRec(root, func);
		
	}
	
	/**
	 * Центрированный обход дерева.
	 * Лево - Цент - Право.
	 * 
	 * @param func - объект для обработки значений.
	 * 
	 * @param flag - если значение true - обход осуществляется итеративно, иначе - рекурсивно.
	 * 
	 * @return Метод ничего не возвращает.
	 */
	public void recInOrder(Consumer<V> func, boolean flag) {
		
		if (flag) recInOrderIter(root, func);
		else recInOrderRec(root, func);
		
	}
	
	//центрированный
	private void recInOrderRec(abstractNode<V> root, Consumer<V> func) {
		
		if (root == null) return;
		
		recInOrderRec(root.getLeft(), func);
		func.accept(root.getValue());
		recInOrderRec(root.getRight(), func);
		
	}
	
	//центрированный
	private void recInOrderIter(abstractNode<V> root, Consumer<V> func) {
		
		Stack<abstractNode<V>> stack = new Stack<>();
		
		while (root != null || !stack.isEmpty()) {
			
			if (!stack.isEmpty()) {
				
				root = stack.pop();
				func.accept(root.getValue());
				
				if (root.getRight() != null) root = root.getRight();
				else root = null;
				
			}
			
			while (root != null) {
				
				stack.push(root);
				root = root.getLeft();
				
			}
			
		}
		
	}
	
	/**
	 * Обратный обход дерева.
	 * Лево - Право - Центр.
	 * 
	 * @param func - объект для обработки значений.
	 * 
	 * @return Метод ничего не возвращает.
	 */
	public void recPostOrder(Consumer<V> func) {
		
		recPostOrderRec(root, func);
		
	}
	
	/**
	 * Обратный обход дерева.
	 * Лево - Право - Центр.
	 * 
	 * @param func - объект для обработки значений.
	 * 
	 * @param flag - если значение true - обход осуществляется итеративно, иначе - рекурсивно.
	 * 
	 * @return Метод ничего не возвращает.
	 */
	public void recPostOrder(Consumer<V> func, boolean flag) {
		
		if (flag) recPostOrderIter(root, func);
		else recPostOrderRec(root, func);
		
	}
	
	//обратный обход
	private void recPostOrderRec(abstractNode<V> root, Consumer<V> func) {
		
		if (root == null) return;
		
		recPostOrderRec(root.getLeft(), func);
		recPostOrderRec(root.getRight(), func);
		func.accept(root.getValue());
		
	}
	
	//обратный обход
	private void recPostOrderIter(abstractNode<V> root, Consumer<V> func) {
		
		Stack<abstractNode<V>> stack = new Stack<>();
		abstractNode<V> last = null;
		
		while(root != null || !stack.isEmpty()) {
			
			if (!stack.isEmpty()) {
				
				if (stack.peek().getRight() != null && stack.peek().getRight() != last) {
					
					root = stack.peek().getRight();
					
				} else {
					
					last = stack.pop();
					func.accept(last.getValue());
					
				}
				
			}
			
			while (root != null) {
				
				stack.push(root);
				root = root.getLeft();
				
			}
		}
	}
	
	/**
	 * Обход дерева в ширину.
	 * 
	 * @param func - объект для обработки значений.
	 * 
	 * @return Метод ничего не возвращает.
	 */
	public void contLevelOrder(Consumer<V> func) {
		
		Queue<abstractNode<V>> queue = new LinkedList<>(); 
		
		abstractNode<V> root = this.root;
		queue.add(root);
		
		while (!queue.isEmpty()) {
			
			root = queue.poll();
			func.accept(root.getValue());
			
			if (root.getLeft() != null) queue.add(root.getLeft());
			if (root.getRight() != null) queue.add(root.getRight());
			
		}
		
	}
	
	/*
	 * @return количество элементов в дереве
	 */
	public int size() {
		return size;
	}
	
	@Override
	public String toString() {
		
		String ch_r = "\u2514\u2500" /*└─*/,
				ch_l = "\u250C\u2500" /*┌─*/,
				ch_c = "\u2502 " /*│ */; 
		
		
		StringBuilder result = new StringBuilder();	
		
		Stack<abstractNode<V>> stack = new Stack<>();
		abstractNode<V> root = this.root;
		
		Stack<String> rpref = new Stack<>();
		Stack<String> cpref = new Stack<>();
		Stack<String> lpref = new Stack<>();
		
		String c = "", r = "", l = "";
		rpref.push(r);
		cpref.push(c);
		lpref.push(l);
		
		boolean isRight = true;
		
		while (root != null || !stack.isEmpty()) {
			
			if (!stack.isEmpty()) {
				
				root = stack.pop();
				
				r = rpref.pop();
				c = cpref.pop();
				l = lpref.pop();
				
				result.append(c).append(root).append("\n");
				
				if (root.getLeft() != null) {
					root = root.getLeft();
					isRight = false;
				}
				else {
					root = null;
				}
	
			}
			
			while (root != null) {
				
				stack.push(root);
				
				if (isRight) {
				
					cpref.push(rpref.peek() + ((root != this.root) ? ch_l : ""));
					lpref.push(rpref.peek() + ((root != this.root) ? ch_c : ""));
					rpref.push(rpref.peek() + ((root != this.root) ? "  " : ""));
					
				} else {
					
					rpref.push(l + ch_c);
					cpref.push(l + ch_r);
					lpref.push(l + "  ");
					
				}
				
				root = root.getRight();
				isRight = true;
				
			}
			
		}
		
		return result.toString();
	}
	
}
