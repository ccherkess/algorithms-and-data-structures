package queue;

public class QueueIsEmptyException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public QueueIsEmptyException(String message) {
		
		super(message + "! Т.к. очередь пуста!");
		
	}

}
