package maps;

public class HashMap<K, V> implements Map<K, V> {

	private static class Node <K, V> {
		
		K key;
		V value;
		int hashCode;
		Node<K, V> next;
		
		public Node(K key, V value, int hashcode, Node<K, V> next) {
			this.key = key;
			this.value = value;
			this.hashCode = hashcode;
			this.next = next;
		}
		
		@Override
		public String toString() {
			
			return String.format("{ %s = %s }", key, value);
			
		}
		
	}
	
	private Node<K, V>[] baskets;
	private int size;
	private int capacity;
	

	public HashMap() {
		
		capacity = 16;
		size = 0;
		
		changeBaskets(capacity);
		
	}
	
	public HashMap(int capacity) {
		
		this.capacity = capacity;
		size = 0;
		
		changeBaskets(capacity);
		
	}
	
	@SuppressWarnings("unchecked")
	private void changeBaskets(int capacity) {
		
		baskets = (Node<K, V>[]) new Node[capacity];
		
	}
	
	private int getPosition(int hashcode) {
		
		return hashcode & (capacity - 1);
		
	}
	
	private int hashCode(K key) {
		
		if (key == null) throw new NullPointerException("key is null");
		
		return key.hashCode();
		
	}
	
	private Node<K, V> findNode(K key, int hashCode, int position) {
		
		Node<K, V> tmp = baskets[position];
		
		while (tmp != null) {
			
			if (tmp.hashCode == hashCode && tmp.key.equals(key)) {
				
				return tmp;
				
			}
			
			tmp = tmp.next;
			
		}
		
		return null;
		
	}

	@Override
	public void put(K key, V value) {
		
		int hashCode = hashCode(key);
		int position = getPosition(hashCode);
		
		if (baskets[position] == null) {
			
			baskets[position] = new Node<K, V>(key, value, hashCode, null);
			
		} else {
			
			Node<K, V> tmp = findNode(key, hashCode, position);
			
			if (tmp != null) {
				tmp.value = value;
			} else {
				baskets[position] = new Node<K, V>(key, value, hashCode, baskets[position]);
			}
			
		}
		
		size++;
		
	}

	@Override
	public V get(K key) {
		
		int hashCode = hashCode(key);
		
		Node<K, V> tmp = findNode(key, hashCode, getPosition(hashCode));
		
		return (tmp == null) ? null : tmp.value;
		
	}

	@Override
	public void remove(K key) {
		
		int hashCode = hashCode(key);
		int position = getPosition(hashCode);
		
		Node<K, V> tmp = baskets[position];
		
		if (tmp != null && tmp.hashCode == hashCode && tmp.key.equals(key)) baskets[position] = tmp.next;
		
		while (tmp.next != null) {
			
			if (tmp.next.hashCode == hashCode && tmp.next.key.equals(key)) {
				
				tmp.next = tmp.next.next;
				break;
				
			}
			
			tmp = tmp.next;
			
		}
		
		size--;
		
	}

	@Override
	public boolean isEmpty() {
	
		return size == 0;
		
	}

	@Override
	public int size() {
		
		return size;
		
	}
	
	@Override
	public String toString() {
		
		StringBuilder result = new StringBuilder();
		
		result.append("(");
		
		for (int i = 0; i < capacity; i++) {
			
			Node<K,V> tmp = baskets[i];
			
			while(tmp != null) {
				
				result.append(tmp.toString());
				
				tmp = tmp.next;
				
			}
			
		}
		
		result.append(")");
		
		return result.toString();
		
	}
}


