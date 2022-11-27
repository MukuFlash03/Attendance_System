package model;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Objects;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Vector;

public class Attendance {

    private final Date date;
    private Map<String, Integer> attendMap;
    private Map<String, int[]> attendanceMap;

    public Attendance(Date date) {
        this.date = date;
        this.attendMap =  new ConcurrentHashMap<>();
        this.attendanceMap = new ConcurrentHashMap<String, int[]>();
    }

    public Date getDate() {
        return date;
    }

    public String getFormattedDate() {
        SimpleDateFormat dt1 = new SimpleDateFormat("MM/dd/yy");
        return dt1.format(date);
    }

    public Map<String, Integer> getAttendance() {
        return attendMap;
    }

    public void setAttendance(Map<String, Integer> attendMap) {
        this.attendMap = attendMap;
    }

    public boolean hasStudentTime(String asurite) {
        return attendMap.containsKey(asurite);
    }

    public void addStudentTime(String asurite, int minutes) {
        attendMap.put(asurite, minutes);
    }

    public void mergeStudentTime(String asurite, int minutes) {
        int totalMinutes = attendMap.get(asurite) + minutes;
        attendMap.replace(asurite, totalMinutes);
    }

    public int getAttendanceCount() {
        return attendMap.size();
    }

    public Vector<String> getMinutes() {
        Vector<String> minutes = new Vector<String>();

        for (Integer minute : attendMap.values())
            minutes.add(minute.toString());
        return minutes;
    }

    public void printAttendance() {
        for (String asurite : attendMap.keySet())
            System.out.println("ASURITE: " + asurite + "\t" + "Minutes: " + attendMap.get(asurite));
    }

    public Map<String, int[]> getAttendance2() {
        return attendanceMap;
    }

    public void setAttendance2(Map<String, int[]> attendanceMap) {
        this.attendanceMap = attendanceMap;
    }

    public boolean hasStudentTime2(String asurite) {
        return attendanceMap.containsKey(asurite);
    }

    public void addStudentTime(int index, String asurite, int minutes) {
        int[] studInfo = new int[2];
        studInfo[0] = index;
        studInfo[1] = minutes;
        attendanceMap.put(asurite,studInfo);
    }

    public void mergeStudentTime(int index, String asurite, int minutes) {
        int totalMinutes = attendanceMap.get(asurite)[1] + minutes;

        int[] studInfo = new int[2];
        studInfo[0] = index;
        studInfo[1] = totalMinutes;
        attendanceMap.replace(asurite, studInfo);
    }

    public int getOrderedAttendanceCount() {
        return attendanceMap.size();
    }

    public Vector<Integer> getMinutes2() {
        Vector<Integer> minutes = new Vector<Integer>();

        for (int[] data : attendanceMap.values())
            minutes.add(data[1]);
        return minutes;
    }

    public void printAttendance2() {
        for (String asurite : attendanceMap.keySet())
            System.out.println("ASURITE: " + asurite + "\tIndex: " + attendanceMap.get(asurite)[0] + "\t" + "Minutes: " + attendanceMap.get(asurite)[1]);
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
