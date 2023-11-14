package sotring_station_algorithm;

public class ErrorInExpressionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ErrorInExpressionException(String message) {
		
		super("Ошибка в выражении: " + message + ".");
		
	}

}
