package model;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Objects;

public class Attendance {

    private final Date date;

    public Attendance(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public String getFormattedDate() {
        SimpleDateFormat dt1 = new SimpleDateFormat("MM/dd/yy");
        return dt1.format(date);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(date);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attendance attend = (Attendance) o;
        return date.equals(attend.date);
    }
}
