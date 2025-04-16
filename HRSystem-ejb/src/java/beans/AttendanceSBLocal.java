package beans;

import entities.Attendance;
import entities.Employees;
import java.util.Date;
import java.util.List;

public interface AttendanceSBLocal {
   void checkIn(Employees employee);
    void checkOut(Employees employee);
    List<Attendance> getAttendanceHistory(Employees employee);
    Attendance getTodayAttendance(Employees employee);
}
