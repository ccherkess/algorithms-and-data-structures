package stack;

public class StackBaseIsNullPointerException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public StackBaseIsNullPointerException() {
		
		super("Невозвомно создать стэк! Т.к. ссылка на базу стэка указывает на null!");
		
	}

}
