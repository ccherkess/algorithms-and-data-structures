package queue;

public class QueueBaseIsNullPointerException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public QueueBaseIsNullPointerException() {
		
		super("Невозвомно создать очередь! Т.к. ссылка на базу стэка указывает на null!");
		
	}

}
