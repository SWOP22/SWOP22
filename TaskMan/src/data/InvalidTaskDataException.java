package data;

public class InvalidTaskDataException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4339574469110119403L;
	
	public String getMessage(){
		return "The information in the TaskData object is not valid";
	}
}
