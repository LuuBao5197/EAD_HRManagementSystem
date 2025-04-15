/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/StatelessEjbClass.java to edit this template
 */
package beans;

import entities.LeaveRequests;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

/**
 *
 * @author kyanh
 */
@Stateless
public class ManagerSB implements ManagerSBLocal {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("HRSystem-ejbPU");
    EntityManager em = emf.createEntityManager();

    @Override
    public List<LeaveRequests> GetLeaveRequests() {
        return em.createNamedQuery("LeaveRequests.findAll", LeaveRequests.class).getResultList();
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public LeaveRequests getRequestsDetail(int id) {
        return em.find(LeaveRequests.class, id);
    }

    @Override
    public void UpdateStatus(LeaveRequests editStatus) {
        LeaveRequests existing = em.find(LeaveRequests.class, editStatus.getId());
        try {
            if (existing != null) {
                em.getTransaction().begin();
                existing.setStatus(editStatus.getStatus());

                if ("R".equalsIgnoreCase(editStatus.getStatus())) {
                    System.out.println("Lý do từ chối: " + editStatus.getRejectedReason());
                    existing.setRejectedReason(editStatus.getRejectedReason());
                } else {
                    existing.setRejectedReason(null);
                }

                em.merge(existing);
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }
}
