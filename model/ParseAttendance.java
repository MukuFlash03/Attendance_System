package model;

import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;



public class ParseAttendance {

    private Scanner myReader;

    private GraphDataSource blackboard;
    
    public ParseAttendance() {
        this.blackboard = GraphDataSource.getInstance();
    }

    public void parseAttendance(File[] files) {
        for (File file : files)
            parseAttendanceFile(file);
        blackboard.fileParsed();
    }

    public void parseAttendanceFile(File file) {
        String data;
        String[] tokens;

        String dateStr = file.getName().substring(0, 8);
        // System.out.println(dateStr);

        try {
            SimpleDateFormat dt1 = new SimpleDateFormat("yyyyMMdd");
            Date date = dt1.parse(dateStr);
            // System.out.println(dt1.format(date));
            blackboard.addAttendance(new Attendance(date));
        } catch(ParseException e) {
            e.printStackTrace();
        }
	}
}
