package tree;

interface abstractNode<V> {

	public V getValue();
	
	public void setValue(V value);
	
	public abstractNode<V> getLeft();
	
	public abstractNode<V> getRight();
	
	public void setLeft(abstractNode<V> node);
	
	public void setRight(abstractNode<V> node);
}
