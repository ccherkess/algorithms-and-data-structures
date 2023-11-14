package sotring_station_algorithm;

public class NotEnoughArgumentsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public NotEnoughArgumentsException(int count) {
		
		super("Ошибка: не хватает " + count + " аргументов для выполнения операции.");
		
	}

}
