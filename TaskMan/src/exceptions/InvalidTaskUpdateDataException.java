package exceptions;

public class InvalidTaskUpdateDataException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7890706623980646421L;
	
	private String message;
	
	public InvalidTaskUpdateDataException(String message) {
		this.message = message;
	}
	
	public String getMessage(){
		return message;
	}
}
