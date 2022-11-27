package model;

import java.io.File;
import java.util.Scanner;

import view.TablePane;

import java.io.FileNotFoundException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;


public class ParseAttendance {

    private Scanner myReader;

    private GraphDataSource blackboard;
    private TablePane tableData;

    private Map<String, Integer> extraAttendees;

    public ParseAttendance() {
        this.blackboard = GraphDataSource.getInstance();
        this.tableData = TablePane.getInstance();
        this.extraAttendees = new HashMap<String, Integer>();
    }

    public void parseAttendance(File[] files) {


        String dateStr = "", dateTable = "";
        SimpleDateFormat dt1;
        Date date;

        for (File file : files) {
            try {
                dateStr = file.getName().substring(0, 8);
                dt1 = new SimpleDateFormat("yyyyMMdd");
                date = dt1.parse(dateStr);
                dateTable = blackboard.getFormattedDate(date);
                if (!tableData.hasColumn(dateTable))
                    parseAttendanceFile(file, date);
            } catch(ParseException e) {
                e.printStackTrace();
            }
        }
        blackboard.fileParsed();
    }

    public void parseAttendanceFile(File file, Date date) {
        String data, asurite;
        String[] tokens;
        int minutes, studIDFlag;

        Attendance attend = new Attendance(date);

        try {
            myReader = new Scanner(file);
			while (myReader.hasNextLine()) {
				data = myReader.nextLine();
				tokens = data.split(",");
                asurite = tokens[0].trim();
                minutes = Integer.parseInt(tokens[1].trim());
                studIDFlag = blackboard.hasAsurite(asurite);
                System.out.println(asurite + "\t" + studIDFlag);
                // if (blackboard.hasAsurite(asurite)) {
                if (studIDFlag != -1) {
                    if (!attend.hasStudentTime(asurite)) {
                        System.out.println("L74 " + asurite + "\t" + studIDFlag);
                        attend.addStudentTime(asurite, minutes);
                        // attend.printAttendance();
                    }
                    // else if (attend.hasStudentTime(asurite)) {
                    else {
                        System.out.println("L78 " + asurite + "\t" + studIDFlag);
                        attend.mergeStudentTime(asurite, minutes);
                        attend.printAttendance();
                    }
                    // if (!attend.hasStudentTime(studIDFlag)) {
                    if (!attend.hasStudentTime2(asurite)) {
                        System.out.println("L82 " + asurite + "\t" + studIDFlag);
                        attend.addStudentTime(studIDFlag, asurite, minutes);
                        attend.printAttendance2();
                    }
                    // else if (attend.hasStudentTime(studIDFlag)) {
                    else {
                        System.out.println("L86 " + asurite + "\t" + studIDFlag);
                        attend.mergeStudentTime(studIDFlag, asurite, minutes);
                        attend.printAttendance2();
                    }
                }
                else
                    extraAttendees.put(asurite, minutes);
            }
            blackboard.addAttendance(attend);
            blackboard.setExtras(extraAttendees);
            myReader.close();
            attend.printAttendance();
            attend.printAttendance2();
		} catch (FileNotFoundException e) {
			System.out.println("Error occurred in finding attendance file(s): " + e);
			e.printStackTrace();
        }
	}
}
