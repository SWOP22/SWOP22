package exceptions;

public class InvalidTimeStampException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4746167579765821927L;

	public String getMessage(){
		return "The TimeStamp is not valid";
	}
}
