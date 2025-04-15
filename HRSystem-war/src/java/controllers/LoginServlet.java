/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import beans.LoginSBLocal;
import beans.ManagerSBLocal;
import entities.Accounts;
import entities.LeaveApprovals;
import entities.LeaveRequests;
import jakarta.ejb.EJB;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author Luu Bao
 */
public class LoginServlet extends HttpServlet {

    @EJB
    LoginSBLocal sb;
    @EJB
    ManagerSBLocal lqsb;

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("HRSystem-ejbPU");
    EntityManager em = emf.createEntityManager();

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
                                request.getSession().setAttribute("lrList", lqsb.GetLeaveRequests());
                                request.getRequestDispatcher("ManagerHome.jsp").forward(request, response);
                            } else if (acc.getRole().contains("Employee")) {
                                request.getSession().setAttribute("acc", acc);
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
                    case "DetailLeaveRequest":
                        int id = Integer.parseInt(request.getParameter("id"));
                        LeaveRequests leaveRequest = em.find(LeaveRequests.class, id);

                        request.setAttribute("leaveRequest", leaveRequest);
                        request.getRequestDispatcher("LeaveDetail.jsp").forward(request, response);
                        break;
                    case "UpdateStatus":
                        try {
                            int reqId = Integer.parseInt(request.getParameter("id"));
                            String status = request.getParameter("status");
                            String reason = request.getParameter("rejectedReason"); // có thể null nếu không từ chối

                            em.getTransaction().begin();

                            LeaveRequests reqToUpdate = em.find(LeaveRequests.class, reqId);

                            if (reqToUpdate != null) {
                                reqToUpdate.setStatus(status);

                                // Nếu từ chối thì cập nhật lý do, ngược lại xóa lý do cũ
                                if ("R".equalsIgnoreCase(status)) {
                                    reqToUpdate.setRejectedReason(reason);
                                } else {
                                    reqToUpdate.setRejectedReason(null);
                                }

                                em.merge(reqToUpdate); // không bắt buộc nếu entity managed
                            }

                            em.getTransaction().commit();

                            // Lấy lại danh sách mới để hiển thị lên ManagerHome.jsp
                            List<LeaveRequests> updatedList = em.createNamedQuery("LeaveRequests.findAll", LeaveRequests.class).getResultList();
                            request.setAttribute("lrList", updatedList);
                            request.setAttribute("message", "Update Successfully!");
                            request.getRequestDispatcher("ManagerHome.jsp").forward(request, response);

                        } catch (Exception e) {
                            em.getTransaction().rollback();
                            e.printStackTrace();
                            request.setAttribute("message", "Update failed: " + e.getMessage());
                            request.getRequestDispatcher("ManagerHome.jsp").forward(request, response);
                        }
                        break;

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
