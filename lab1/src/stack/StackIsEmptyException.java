package stack;

public class StackIsEmptyException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public StackIsEmptyException(String message) {
		
		super(message + "! Т.к. стек пуст!");
		
	}

}
