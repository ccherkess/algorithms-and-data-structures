package tree;

import java.util.Stack;

/**
 * Свойства красно-черного дерева:
 * 1) Каждый узел либо красный, либо черный.
 * 2) (Корень черный.)
 * 3) Все листья NULL черные.
 * 4) Красный узел не должен иметь красных потомков.
 * 5) Все пути от узла к листьям ниже содержат одинаковое количество черных узлов.
 * 
 * @param <V>
 */
public class RedBlackTree<V> extends BinarySearchTree<V> {

private static class Node<V> implements abstractNode<V> {
		
		public V value;
		public boolean isRed;
		public Node<V> left;
		public Node<V> right;
		
		public Node(V value) {
			this.value = value;
			isRed = true;
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
			return String.format("%s %s", (isRed ? "R" : "B"), value.toString()); 
		}
		
	}

	public RedBlackTree(BinaryTree<V> tree) {
		super(tree);
	}

	private boolean isRed(Node<V> node) {
		return (node == null) ? false : node.isRed;
	}
	
	private void setColor(Node<V> node, boolean isRed) {
		if (node != null) node.isRed = isRed;
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
		
		return R;
	}
	
	/*
	 * Случай №1
	 * Дядя красный
	 * 
	 * Перекраска дяди и отца в черный, а деда в красный
	 */
	private void addCase1(Node<V> F, Node<V> U, Node<V> G) {
		setColor(F, false);
		setColor(U, false);
		setColor(G, true);
	}
	
	/*
	 * Случай №2
	 * Дядя черный и сын, отец, дед образуют прямую линию
	 * 
	 * Перекраска отца и деда, одинарный поворот
	 * Прямая линия влево (вправо), делаем правый (левый) поворот
	 */
	private Node<V> addCase2(Node<V> F, Node<V> U, Node<V> G) {
		
		setColor(F, !isRed(F));
		setColor(G, !isRed(G));
		
		if (G.left == F) return rotateRight(G);
		else return rotateLeft(G);
		
	}
	
	/*
	 * Случай №3
	 * Дядя черный и сын, отец, дед образуют угол
	 * 
	 * Перекраска деда и сына, двойной поворот
	 */
	private Node<V> addCase3(Node<V> S, Node<V> F, Node<V> U, Node<V> G) {
		
		setColor(S, !isRed(S));
		setColor(G, !isRed(G));
		
		if ((G.left == F)) {
			G.left = rotateLeft(F);
			return rotateRight(G);
		} else {
			G.right = rotateRight(F);
			return rotateLeft(G);
		}
		
	}
	
	private void addBalance(Stack<abstractNode<V>> stack) {
		
		Node<V> S = (Node<V>) stack.pop();
		
		while (stack.size() > 1) { 
			
			Node<V> F = (Node<V>) stack.pop(); //Отец
			Node<V> G = (Node<V>) stack.pop(); //Дед
			Node<V> U = (G.left == F) ? G.right : G.left; //Дядя
			
			if (isRed(U) == true) { //случай №1
				addCase1(F, U, G);
				S = G;
			}  
			else {
				
				//запоминаем прадеда
				Node<V> parentG = null;
				if (!stack.isEmpty()) parentG = (Node<V>) stack.pop();
				boolean isLeft = parentG != null ? parentG.left == G ? true : false : false;
				
				//случай №2
				if ((G.left == F && F.left == S) || (G.right == F && F.right == S)) G = addCase2(F, U,G);
				//случай №3
				else if ((G.left == F && F.right == S) || (G.right == F && F.left == S)) G = addCase3(S, F, U, G);
				
				//переписываем ссылку на деда
				if (parentG != null) {
					if (isLeft) parentG.left = G;
					else parentG.right = G;
				} else {
					root = G;
				}
				
				break;
			}
		}
		
	}
	
	@Override
	public void add(V value) {
		
		Node<V> S = new Node<>(value);
		
		if (root == null) root = S;
		else {
			
			Stack<abstractNode<V>> stack = super.findIter(value);
			
			Node<V> tmp = (Node<V>) stack.pop();
			
			if (tmp != null) return; //элемент уже есть в дереве
			else {
				
				/*
				 *Элемента нет в дереве, добавляем его 
				 */
				tmp = (Node<V>) stack.peek();
				if (compare(tmp.value, value) > 0) tmp.left = S;
				else tmp.right = S;
				
			}
			
			//балансировка
			stack.push(S);
			addBalance(stack);
		}
		
		//если корень оказался красным - перекрашиваем его в черный
		if (((Node<V>) root).isRed) setColor((Node<V>) root, false);
		size++;
	}
	
	/*
	 * Случай №1
	 * 
	 * Отец F узла N красный, остальные узлы черные.
	 * Перекраска B и F.
	 */
	private void removeCase1(Node<V> F, Node<V> B) {
		setColor(F, false);
		setColor(B, true);
	}
	
	/*
	 * Случай №2
	 * 
	 * Брат B узла N черный, а его правый сын CR красный.
	 * Поворот F вокруг B, перекраска CR и обмен цветами F и B
	 */
	private Node<V> removeCase2(Node<V> F, Node<V> B, Node<V> CR) {
		setColor(CR, !isRed(CR));
		
		boolean colorF = isRed(F);
		setColor(F, isRed(B));
		setColor(B, colorF);
		
		if (F.left == B) return rotateRight(F);
		else return rotateLeft(F);
		
	}
	
	/*
	 * Случай №3
	 * 
	 * Брат B узла N черный, его правый сын CR черный, а левый сын CL красный.
	 * Этот случай сводится к предыдущему, если повернуть левого сына CL относительно своего отца B так,
	 * чтобы поддерево B со своими сыновьями вытянулось в одну линию, и
	 * перекрасить CL и B.
	 */
	private Node<V> removeCase3(Node<V> F, Node<V> B, Node<V> CR, Node<V> CL) {
		setColor(CL, !isRed(CL));
		setColor(B, !isRed(B));
		
		if (F.left == B) B = F.left = rotateLeft(B);
		else B = F.right = rotateRight(B);
		
		return removeCase2(F, B, CR);
	}
	
	/*
	 * Случай №4
	 * 
	 * Брат B узла N красный.
	 * Поворот F вокруг B и перекраска F и B
	 */
	private Node<V> removeCase4(Node<V> F, Node<V> B) {
		setColor(F, !isRed(F));
		setColor(B, !isRed(B));
		
		if (F.left == B) return rotateRight(F);
		else return rotateLeft(F);
	}
	
	/*
	 * Случай №5
	 * 
	 * Все узлы комбинации черные.
	 * Перекраска B в красный цвет и продолжение процедуры наверх		
	 */
	private void removeCase5(Node<V> B) {
		setColor(B, true);
	}
	
	private Node<V> removeBalance(Stack<abstractNode<V>> stack, Node<V> root) {
		
		boolean flag = true;
		Node<V> N, F, B, CL, CR, G = null;
		boolean colorF, colorB, colorCL, colorCR;
		
		while (stack.size() > 1 && flag) {
			
			N = (Node<V>) stack.pop(); //сын, так как нигде не участвует, используется, как временная переменная
			F = (Node<V>) stack.pop(); //отец
			B = (F.left == N) ? F.right : F.left; //брат
			CL = (B == null) ? null : (F.right == B) ? B.left : B.right; //левый сын брата
			CR = (B == null) ? null : (F.right == B) ? B.right : B.left; //правый сын брата
			
			//цвета узлов
			colorF = isRed(F);
			colorB = isRed(B);
			colorCL = isRed(CL);
			colorCR = isRed(CR);
			
			//дед
			G = (!stack.isEmpty()) ? (Node<V>) stack.peek() : null;
			
			if (colorF == false && colorB == false && colorCL == false && colorCR == false) { //пятый случай
				
				removeCase5(B);
				stack.push(F);
			}
			else {
				
				if ( //первый случай
						colorF == true 
						&& colorB == false
						&& colorCL == false
						&& colorCR == false
					) {
					
					removeCase1(F, B);
					break;
					
				} else if (colorB == true) { //четвёртый случай
					
					/*
					 * Записываем отца и сына обратно в стэк,
					 * так как четвёртый случай сводит ситуацию к 1-3 случаям
					 */
			
					Node<V> tmp = removeCase4(F, B);
					
					if (G != null) {
						if (G.left == F) G.left = tmp;
						else G.right = tmp;
					} else root = tmp;
					
					stack.push(F);
					stack.push(N);
					continue;
					
				} else if (colorB == false && colorCR == true) { //второй случай
					
					N = removeCase2(F, B, CR);
					flag = false; //помечаем, что нужно будет выйти из цикла
					
				} else if (colorB == false && colorCR == false && colorCL == true) { //третий случай
					
					N = removeCase3(F, B, CR, CL);
					flag = false; //помечаем, что нужно будет выйти из цикла
					
				}
				
				/*
				 * Если дошли до этого места - было вращение.
				 * После вращения необходимо перезаписать ссылку отца у деда.
				 */
				if (G != null) {
					if (G.left == F) G.left = N;
					else G.right = N;
				}
				else {
					return N;
				}
				
			}
		}
		return root;
	}
	
	@Override
	public void remove(V value) {
		
		root = remove(value, (Node<V>) root);
		size--;
		
	}
	
	private Node<V> remove(V value, Node<V> root) {
		
		Stack<abstractNode<V>> stack = findIter(value);
		
		Node<V> tmp = (Node<V>) stack.pop();
		
		boolean color = isRed(tmp);
		
		Node<V> parent = stack.isEmpty() ? null : (Node<V>) stack.peek();
		
		if (tmp == null) return root; //элемент не найден
		
		if (tmp.left != null && tmp.right != null) {
			
			/*
			 * Ищем элемент, который встанет на место удаляемого
			 */
			Node<V> successor = (Node<V>) getMin(tmp.right);
			tmp.value = successor.value; //вставляем его на место удаляемого
			
			root = remove(successor.value, root); //удаляем его из дерева
			
			
		} else {
			
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
					
					stack.push(child);
					
				} 
				
				stack.push(null);
				if (!color) root = removeBalance(stack, root);
		}
		
		return root;
	}
	
}
