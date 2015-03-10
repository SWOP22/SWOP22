package mainPackage;

import java.text.*;
import java.util.Date;
import java.util.Scanner;

import project.ProjectHandler;

public class TestConsole {

	public static void main(String[] args) throws ParseException {
		Scanner input = new Scanner(System.in);
		
		System.out.println("Please give a command: ");
		
		switch(input.nextLine()) {
			case "createProject" :
				String name;
				String description;
				Date creationTime;
				Date dueTime;
				
				String dateString;
				DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				
				System.out.println("Please give a project name: ");
				name = input.nextLine();
				System.out.println("Please give a project description: ");
				description = input.nextLine();
				System.out.println("Please give a start date (DD/MM/YYYY): ");
				dateString = input.nextLine();
				creationTime = format.parse(dateString);
				System.out.println("Please give a due date (DD/MM/YYYY): ");
				dateString = input.nextLine();
				dueTime = format.parse(dateString);
				
				ProjectHandler.createProject(name, description, creationTime, dueTime);
				
				System.out.println("Add a new task ?");
				
				while(input.nextLine() == "Y") {
					System.out.println("");
				}
				break;
		}
	}

}
