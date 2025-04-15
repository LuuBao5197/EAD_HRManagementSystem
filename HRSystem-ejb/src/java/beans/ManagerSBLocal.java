/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/SessionLocal.java to edit this template
 */
package beans;

import entities.LeaveRequests;
import jakarta.ejb.Local;
import java.util.List;

/**
 *
 * @author kyanh
 */
@Local
public interface ManagerSBLocal {

    public List<LeaveRequests> GetLeaveRequests();

    public LeaveRequests getRequestsDetail(int id);

    public void UpdateStatus(LeaveRequests editStatus);
}
