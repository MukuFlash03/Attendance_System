package model;

import java.util.List;
import java.util.ArrayList;
import java.util.Observable;

public class GraphDataSource extends Observable {
    private static volatile GraphDataSource INSTANCE;

    private final List<Student> studentRoster;
    private final List<Attendance> attendances;

    private String action = "";

    private GraphDataSource() {
        this.studentRoster = new ArrayList<Student>();
        this.attendances = new ArrayList<Attendance>();
    }

    public static GraphDataSource getInstance() {
        if (INSTANCE == null) {
            synchronized (GraphDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new GraphDataSource();
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

}
