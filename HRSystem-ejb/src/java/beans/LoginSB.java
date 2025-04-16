/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/StatelessEjbClass.java to edit this template
 */
package beans;

import entities.Accounts;
import entities.Attendance;
import entities.Employees;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import java.util.Date;
import java.util.List;
import jakarta.annotation.PreDestroy;

@Stateless
public class LoginSB implements LoginSBLocal {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("HRSystem-ejbPU");
    EntityManager em = emf.createEntityManager();

    @Override
    public Accounts checkLogin(String usn, String pwd) {
        Accounts acc = null;
        String query = "SELECT a FROM Accounts a WHERE a.username = :username and a.passwordHash = :passwordHash";
        try {
            em.getTransaction().begin();
            acc = em.createQuery(query, Accounts.class)
                    .setParameter("username", usn)
                    .setParameter("passwordHash", pwd)
                    .getSingleResult();
            em.getTransaction().commit();
            if (acc != null) {
                return acc;
            } else {
                return null;
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void saveEmployee(Accounts newacc) {
        persists(newacc);
    }

    private void persists(Object o) {
        try {
            em.getTransaction().begin();
            em.persist(o);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
    }

    @Override
    public List<Accounts> findAll() {
        String query = "SELECT * FROM Accounts WHERE Role NOT IN ('Admin')";

        return em.createNativeQuery(query, Accounts.class).getResultList();
    }

    @PreDestroy
    public void cleanup() {
        if (em != null && em.isOpen()) {
            em.close();
        }
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }

    private EntityManager getEntityManager() {
        if (em == null || !em.isOpen()) {
            em = emf.createEntityManager();
        }
        return em;
    }

    @Override
    public void checkIn(Employees employee) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            Date now = new Date();

            // Sửa lại câu query đúng chuẩn JPQL
            Long count = em.createQuery(
                    "SELECT COUNT(a) FROM Attendance a WHERE a.employeeID = :employee "
                    + "AND CAST(a.attendanceDate AS date) = CAST(:now AS date) "
                    + "AND a.checkin IS NOT NULL",
                    Long.class)
                    .setParameter("employee", employee)
                    .setParameter("now", now)
                    .getSingleResult();

            if (count > 0) {
                throw new IllegalStateException("Bạn đã check-in hôm nay rồi");
            }

            // Tạo bản ghi mới
            Attendance attendance = new Attendance();
            attendance.setEmployeeID(employee);
            attendance.setAttendanceDate(now);
            attendance.setCheckin(now);
            em.persist(attendance);

            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Lỗi khi check-in: " + e.getMessage(), e);
        }
    }

    @Override
    public void checkOut(Employees employee) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            Date now = new Date();

            // Sửa lại câu query phù hợp với SQL Server
            Attendance attendance = em.createQuery(
                    "SELECT a FROM Attendance a WHERE a.employeeID = :employee "
                    + "AND CAST(a.attendanceDate AS date) = CAST(:now AS date) "
                    + "AND a.checkin IS NOT NULL AND a.checkOut IS NULL",
                    Attendance.class)
                    .setParameter("employee", employee)
                    .setParameter("now", now)
                    .getSingleResult();

            if (attendance == null) {
                throw new IllegalStateException("Bạn chưa check-in hôm nay hoặc đã check-out rồi");
            }

            attendance.setCheckOut(now);
            em.merge(attendance);

            tx.commit();
        } catch (NoResultException e) {
            throw new IllegalStateException("Không tìm thấy bản ghi check-in hôm nay");
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Lỗi khi check-out: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Attendance> getAttendanceHistory(Employees employee) {

        try {
            return em.createQuery(
                    "SELECT a FROM Attendance a WHERE a.employeeID = :employee "
                    + "ORDER BY a.attendanceDate DESC",
                    Attendance.class)
                    .setParameter("employee", employee)
                    .getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi lấy lịch sử chấm công: " + e.getMessage(), e);
        }
    }

    @Override
    public Attendance getTodayAttendance(Employees employee) {

        try {
            Date now = new Date();
            return em.createQuery(
                    "SELECT a FROM Attendance a WHERE a.employeeID = :employee "
                    + "AND CAST(a.attendanceDate AS date) = CAST(:now AS date)",
                    Attendance.class)
                    .setParameter("employee", employee)
                    .setParameter("now", now)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi lấy thông tin chấm công hôm nay: " + e.getMessage(), e);
        }
    }
}
