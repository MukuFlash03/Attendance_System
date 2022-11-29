package model;

import java.util.List;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Blackboard extends Observable {
    private static volatile Blackboard INSTANCE;

    private final List<Student> studentRoster;
    private final List<Attendance> attendances;
    private Map<String, Integer> extraAttendees;

    private String action = "";

    private Blackboard() {
        this.studentRoster = new ArrayList<Student>();
        this.attendances = new ArrayList<Attendance>();
        this.extraAttendees = new HashMap<String, Integer>();
    }

    public static Blackboard getInstance() {
        if (INSTANCE == null) {
            synchronized (Blackboard.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Blackboard();
                }
            }
        }
        return INSTANCE;
    }

    public void addStudent(Student stud) {
        studentRoster.add(stud);
    }

    public void addAttendance(Attendance attend) {
        attendances.add(attend);
    }

    
    public void fileParsed() {
        this.setChanged();
        this.notifyObservers();
    }

    public boolean hasStudent(Student stud) {
        return studentRoster.contains(stud);
    }

    public int getStudentCount() {
        return studentRoster.size();
    }

    public List<Student> getStudents() {
        return studentRoster;
    }

    public List<Attendance> getAttendances() {
        return attendances;
    }

    public void clearRoster() {
        studentRoster.removeAll(studentRoster);
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getFormattedDate(Date date) {
        SimpleDateFormat dt1 = new SimpleDateFormat("MM/dd/yy");
        return dt1.format(date);
    }

    public int hasAsurite(String asurite) {
        int studIDFlag = -1;
        for (Student stud : studentRoster) {
            if (asurite.equals(stud.getAsurite())) {
                studIDFlag = studentRoster.indexOf(stud);
                return studIDFlag;
            }
        }
        return studIDFlag;
    }

    public void setExtras(Map<String, Integer> extraAttendees) {
        this.extraAttendees = extraAttendees;
    }

    public Map<String, Integer> getExtras() {
        return extraAttendees;
    }

    public LinkedHashMap<String, Integer> getAttendanceCount() {
        LinkedHashMap<String, Integer> attendCount = new LinkedHashMap<String, Integer>();

        for (Attendance attend : attendances) {
            attendCount.put(attend.getFormattedDate(), attend.getOrderedAttendanceCount());
        }

        return attendCount;
    }
}
