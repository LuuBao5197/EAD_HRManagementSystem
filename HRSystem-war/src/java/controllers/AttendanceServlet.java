package controllers;

import beans.LoginSBLocal;
import entities.Attendance;
import entities.Employees;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@WebServlet(name = "AttendanceServlet", urlPatterns = {"/AttendanceServlet"})
public class AttendanceServlet extends HttpServlet {

    @EJB
    private LoginSBLocal loginBean;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        
        // Kiểm tra đăng nhập
        Employees employee = (Employees) session.getAttribute("currentEmployee");
        if (employee == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            // Lấy dữ liệu chấm công hôm nay
            Attendance todayAttendance = getTodayAttendance(employee);
            request.setAttribute("todayAttendance", todayAttendance);
            
            // Lấy lịch sử chấm công
            List<Attendance> attendanceList = loginBean.getAttendanceHistory(employee);
            request.setAttribute("attendanceList", attendanceList);
            
            if (action != null) {
                switch (action) {
                    case "checkin":
                        loginBean.checkIn(employee);
                        request.setAttribute("message", "Check-in thành công!");
                        break;
                    case "checkout":
                        loginBean.checkOut(employee);
                        request.setAttribute("message", "Check-out thành công!");
                        break;
                }
            }
            
            request.getRequestDispatcher("attendance.jsp").forward(request, response);
            
        } catch (Exception ex) {
            request.setAttribute("error", ex.getMessage());
            request.getRequestDispatcher("attendance.jsp").forward(request, response);
        }
    }

    private Attendance getTodayAttendance(Employees employee) {
        try {
            Date now = new Date();
            return loginBean.getAttendanceHistory(employee).stream()
                    .filter(a -> isSameDay(a.getAttendanceDate(), now))
                    .findFirst()
                    .orElse(null);
        } catch (Exception e) {
            return null;
        }
    }
    
    private boolean isSameDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) return false;
        return date1.getYear() == date2.getYear() && 
               date1.getMonth() == date2.getMonth() && 
               date1.getDate() == date2.getDate();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}