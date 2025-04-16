///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/StatelessEjbClass.java to edit this template
// */
//package beans;
//
//import entities.Attendance;
//import entities.Employees;
//import jakarta.ejb.Stateless;
//import java.util.List;
//import jakarta.persistence.*;
//import java.util.Date;
///**
// *
// * @author quyla
// */
//@Stateless
//public class AttendanceSB implements AttendanceSBLocal {
//    EntityManagerFactory emf=Persistence.createEntityManagerFactory("HRSystem-ejbPU");
//    EntityManager em=emf.createEntityManager();
//    @Override
//    public void checkIn(Employees employee) {
//         
//        try {
//            em.getTransaction().begin();
//
//            Date now = new Date();
//
//            List<Attendance> todayRecords = em.createQuery(
//                "SELECT a FROM Attendance a WHERE a.employeeID = :employee AND FUNCTION('DATE', a.attendanceDate) = FUNCTION('DATE', :now)",
//                Attendance.class
//            )
//            .setParameter("employee", employee)
//            .setParameter("now", now)
//            .getResultList();
//
//            if (todayRecords.isEmpty()) {
//                Attendance attendance = new Attendance();
//                attendance.setEmployeeID(employee);
//                attendance.setAttendanceDate(now);
//                attendance.setCheckin(now);
//                em.persist(attendance);
//            } else {
//                throw new IllegalStateException("Already checked in today");
//            }
//
//            em.getTransaction().commit();
//        } catch (Exception e) {
//            if (em.getTransaction().isActive()) em.getTransaction().rollback();
//            throw e;
//        } finally {
//            em.close();
//        }
//    }
//
//    @Override
//    public void checkOut(Employees employee) {
//        try {
//            em.getTransaction().begin();
//
//            Date now = new Date();
//
//            List<Attendance> todayRecords = em.createQuery(
//                "SELECT a FROM Attendance a WHERE a.employeeID = :employee AND FUNCTION('DATE', a.attendanceDate) = FUNCTION('DATE', :now)",
//                Attendance.class
//            )
//            .setParameter("employee", employee)
//            .setParameter("now", now)
//            .getResultList();
//
//            if (!todayRecords.isEmpty()) {
//                Attendance attendance = todayRecords.get(0);
//                if (attendance.getCheckOut() == null) {
//                    attendance.setCheckOut(now);
//                    em.merge(attendance);
//                } else {
//                    throw new IllegalStateException("Already checked out today");
//                }
//            } else {
//                throw new IllegalStateException("No check-in record for today");
//            }
//
//            em.getTransaction().commit();
//        } catch (Exception e) {
//            if (em.getTransaction().isActive()) em.getTransaction().rollback();
//            throw e;
//        } finally {
//            em.close();
//        }
//    }
//
//    @Override
//    public List<Attendance> getAttendanceHistory(Employees employee) {
//        try {
//            return em.createQuery(
//                "SELECT a FROM Attendance a WHERE a.employeeID = :employee ORDER BY a.attendanceDate DESC",
//                Attendance.class
//            )
//            .setParameter("employee", employee)
//            .getResultList();
//        } finally {
//            em.close();
//        }
//    }
//}
//
//    
//
//    // Add business logic below. (Right-click in editor and choose
//    // "Insert Code > Add Business Method")
//
