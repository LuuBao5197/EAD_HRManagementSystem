/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;
import beans.LoginSBLocal;
import entities.*;
import jakarta.ejb.EJB;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Luu Bao
 */
public class LoginServlet extends HttpServlet {
    
    @EJB
    LoginSBLocal sb;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
         try (PrintWriter out = response.getWriter()) {

            String action = request.getParameter("action");
            if (null == action) {
                request.getRequestDispatcher("login.jsp").forward(request, response);
            } else {
                switch (action) {
                    case "Login":
                        String usn = request.getParameter("txtUsn");
                        String pwd = request.getParameter("txtPwd");
                        Accounts acc = sb.checkLogin(usn, pwd);
                        System.out.println("usn:" + usn);
                        System.out.println("pwd:" + pwd);
                        System.out.println("Account login:" + acc);
                        if (acc != null) {
                            if (acc.getRole().contains("Admin")) {
                                List<Accounts> list = sb.findAll();
                                request.getSession().setAttribute("list", list);
                                request.getSession().setAttribute("acc", acc);
                                request.getRequestDispatcher("AdminHome.jsp").forward(request, response);
                            } else if (acc.getRole().contains("Manager")) {
                                request.getSession().setAttribute("acc", acc);
                                request.getRequestDispatcher("ManagerHome.jsp").forward(request, response);
                            } else if (acc.getRole().contains("Employee")) {
                                
                                request.getSession().setAttribute("acc", acc);
                                List<LeaveRequests> leaveList = sb.FindAllLeaveRequests(acc.getUsername());
                                request.setAttribute("leaverequestlist", leaveList);
                                request.getRequestDispatcher("EmployeeHome.jsp").forward(request, response);
                            }
                        } else {
                            request.setAttribute("error", "This account is not exist. Try again");
                            request.getRequestDispatcher("login.jsp").forward(request, response);
                        }

                        break;

                    case "Create":
                        String username = request.getParameter("txtUsn");
                        String password = request.getParameter("txtPassword");
                        String fullname = request.getParameter("txtFullname");
                        String phone = request.getParameter("txtPhone");
                        String email = request.getParameter("txtEmail");
                        String role = request.getParameter("txtRole");

                        Accounts newAcc = new Accounts(username, fullname, phone, email, password, role);
                        sb.saveEmployee(newAcc);
                        List<Accounts> list = sb.findAll();
                        request.getSession().setAttribute("list", list);
                        request.setAttribute("msg", "Create new account successfully!");
                        request.getRequestDispatcher("AdminHome.jsp").forward(request, response);
                        break;
                    case "CreateLeaveRequest":
                        try {
                            String startDateStr = request.getParameter("startDate");
                            String endDateStr = request.getParameter("endDate");
                            String reason = request.getParameter("reason");

                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            Date startDate = sdf.parse(startDateStr);
                            Date endDate = sdf.parse(endDateStr);

                            // Lấy tài khoản đăng nhập
                            Accounts currentAcc = (Accounts) request.getSession().getAttribute("acc");

                            List<Employees> empList = currentAcc.getEmployeesList();
                            if (empList == null || empList.isEmpty()) {
                                request.setAttribute("error", "Không tìm thấy thông tin nhân viên.");
                                request.getRequestDispatcher("CreateLeaveRequest.jsp").forward(request, response);
                                return;
                            }

                            Employees employee = empList.get(0); // giả sử mỗi account chỉ có 1 employee

                            LeaveRequests newRequest = new LeaveRequests(startDate, endDate, reason, "Pending", employee);
                            sb.createLeaveRequest(newRequest);

                            request.setAttribute("msg", "Gửi đơn xin nghỉ thành công!");
                            request.getRequestDispatcher("success.html").forward(request, response);
                        } catch (Exception e) {
                            e.printStackTrace();
                            request.setAttribute("error", "Có lỗi xảy ra khi gửi đơn nghỉ: " + e.getMessage());
                            request.getRequestDispatcher("CreateLeaveRequest.jsp").forward(request, response);
                        }
                        break;
                        
                    default:
                        throw new AssertionError();
                }
            }

        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
