package data;

import java.util.Date;

public class InvalidProjectDataException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1664036828995231183L;
	
	public String getMessage(){
		return "The information in the ProjectData object is not valid";
	}
	
	//Field Checks
	public void checkName(String name) throws InvalidProjectDataException {
		if(name.equals(""))
			throw new InvalidProjectDataException();
	}
	
	public void checkDescription(String description) throws InvalidProjectDataException {
		if(description.equals(""))
			throw new InvalidProjectDataException();
	}
	
	public void checkCreationTime(Date creationTime) throws InvalidProjectDataException {
		if(creationTime == null)
			throw new InvalidProjectDataException();
	}
	
	public void checkDueTime(Date dueTime) throws InvalidProjectDataException {
		if(dueTime == null)
			throw new InvalidProjectDataException();
	}
}
