/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Luu Bao
 */
@Entity
@Table(name = "LeaveRequests")
@NamedQueries({
    @NamedQuery(name = "LeaveRequests.findAll", query = "SELECT l FROM LeaveRequests l"),
    @NamedQuery(name = "LeaveRequests.findById", query = "SELECT l FROM LeaveRequests l WHERE l.id = :id"),
    @NamedQuery(name = "LeaveRequests.findByStartDate", query = "SELECT l FROM LeaveRequests l WHERE l.startDate = :startDate"),
    @NamedQuery(name = "LeaveRequests.findByEndDate", query = "SELECT l FROM LeaveRequests l WHERE l.endDate = :endDate"),
    @NamedQuery(name = "LeaveRequests.findByReason", query = "SELECT l FROM LeaveRequests l WHERE l.reason = :reason"),
    @NamedQuery(name = "LeaveRequests.findByStatus", query = "SELECT l FROM LeaveRequests l WHERE l.status = :status")})
public class LeaveRequests implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Column(name = "StartDate")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Column(name = "EndDate")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @Column(name = "Reason")
    private String reason;
    @Column(name = "Status")
    private String status;
    @Column(name = "RejectedReason")
    private String rejectedReason;
    @OneToMany(mappedBy = "leaveRequestId")
    private List<LeaveApprovals> leaveApprovalsList;
    @JoinColumn(name = "EmployeeID", referencedColumnName = "Id")
    @ManyToOne
    private Employees employeeID;

    public LeaveRequests(Integer id, String status, String rejectedReason) {
        this.id = id;
        this.status = status;
        this.rejectedReason = rejectedReason;
    }

    public LeaveRequests() {
    }

    public LeaveRequests(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<LeaveApprovals> getLeaveApprovalsList() {
        return leaveApprovalsList;
    }

    public void setLeaveApprovalsList(List<LeaveApprovals> leaveApprovalsList) {
        this.leaveApprovalsList = leaveApprovalsList;
    }

    public Employees getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(Employees employeeID) {
        this.employeeID = employeeID;
    }

    public String getRejectedReason() {
        return rejectedReason;
    }

    public void setRejectedReason(String rejectedReason) {
        this.rejectedReason = rejectedReason;
    }
}
