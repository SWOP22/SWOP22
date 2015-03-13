package exceptions;

public class InvalidTaskDataException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4339574469110119403L;
	
	private String message;
	
	public InvalidTaskDataException(String message) {
		this.message = message;
	}
	
	public String getMessage(){
		return message;
	}
}
