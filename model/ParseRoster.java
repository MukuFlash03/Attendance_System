package model;

import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;



public class ParseRoster {

    private Scanner myReader;

    private GraphDataSource blackboard;
    
    public ParseRoster() {
        this.blackboard = GraphDataSource.getInstance();
    }

    public void parseRosterFile(File file) {
        String data;
        String[] tokens;
        
		try{
            myReader = new Scanner(file);
			while (myReader.hasNextLine()) {
				data = myReader.nextLine();
				tokens = data.split(",");
                Student newStud = new Student(tokens);
                if (!blackboard.hasStudent(newStud))
                    blackboard.addStudent(new Student(tokens));
			}
            myReader.close();
            blackboard.fileParsed();
		} catch (FileNotFoundException e) {
			System.out.println("Error occurred in finding roster file: " + e);
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Error occurred in parsing roster data: " + e);
			e.printStackTrace();
		}
	}
}
