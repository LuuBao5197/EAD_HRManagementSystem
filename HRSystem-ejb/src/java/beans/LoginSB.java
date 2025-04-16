/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/StatelessEjbClass.java to edit this template
 */
package beans;

import entities.Accounts;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import jakarta.persistence.Persistence;

import java.util.List;


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

    
}
