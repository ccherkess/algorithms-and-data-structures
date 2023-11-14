package lists;

public class IndexOutOfListSizeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public IndexOutOfListSizeException(String message, int index, int size) {
		super(message + "! Т. к. индекс - " + index + " выходит за пределы списка размером - " + size + "!");
	}

}
