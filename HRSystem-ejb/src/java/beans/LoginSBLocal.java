/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/SessionLocal.java to edit this template
 */
package beans;

import entities.Accounts;
import entities.Attendance;
import entities.Employees;
import jakarta.ejb.Local;
import java.util.List;

/**
 *
 * @author Luu Bao
 */
@Local
public interface LoginSBLocal {
     public Accounts checkLogin(String usn , String pwd );
    
    public List<Accounts> findAll();
    
    public void saveEmployee(Accounts newacc);
   
}
