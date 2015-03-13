package exceptions;

public class InvalidProjectDataException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1664036828995231183L;
	
	private String message;
	
	public InvalidProjectDataException(String message) {
		this.message = message;
	}
	
	public String getMessage(){
		return message;
	}
}