package sotring_station_algorithm;

public class ThereIsNoSuchOperatorException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ThereIsNoSuchOperatorException(String message) {
		
		super("В списке операторов отсутствует оператор: " + message + ".");
		
	}

}
