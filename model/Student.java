package model;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Objects;

public class Student {
    // private final String name;
    private final String id;
    private final String firstName;
    private final String lastName;
    private final String asurite;


    // public Student(String id, String firstName, String lastName, String asurite) {
        public Student(String[] data) {
        this.id = data[0];
        this.firstName = data[1];
        this.lastName = data[2];
        this.asurite = data[3];
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAsurite() {
        return asurite;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student stud = (Student) o;
        return id.equals(stud.id);
    }
}
