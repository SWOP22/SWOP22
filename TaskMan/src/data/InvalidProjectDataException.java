package data;

public class InvalidProjectDataException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1664036828995231183L;
	
	public String getMessage(){
		return "The information in the ProjectData object is not valid";
	}
}
