package data;

public class InvalidTaskUpdateDataException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7890706623980646421L;
	
	public String getMessage(){
		return "The information in the TaskUpdateData object is not valid";
	}
}
