package model;

import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Observable;

public class GraphDataSource extends Observable {
    private static volatile GraphDataSource INSTANCE;

    private final List<Student> studentRoster;

    private GraphDataSource() {
        this.studentRoster = new ArrayList<Student>();
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

    public void clearRoster() {
        studentRoster.removeAll(studentRoster);
    }

}
