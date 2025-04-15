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
import jakarta.persistence.Table;
import java.io.Serializable;

/**
 *
 * @author Luu Bao
 */
@Entity
@Table(name = "leave_approvals")
@NamedQueries({
    @NamedQuery(name = "LeaveApprovals.findAll", query = "SELECT l FROM LeaveApprovals l"),
    @NamedQuery(name = "LeaveApprovals.findById", query = "SELECT l FROM LeaveApprovals l WHERE l.id = :id"),
    @NamedQuery(name = "LeaveApprovals.findByResult", query = "SELECT l FROM LeaveApprovals l WHERE l.result = :result")})
public class LeaveApprovals implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "result")
    private String result;
    @JoinColumn(name = "LeaveRequestId", referencedColumnName = "Id")
    @ManyToOne
    private LeaveRequests leaveRequestId;
    @JoinColumn(name = "ApprovalBy", referencedColumnName = "Id")
    @ManyToOne
    private Manager approvalBy;

    public LeaveApprovals() {
    }

    public LeaveApprovals(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public LeaveRequests getLeaveRequestId() {
        return leaveRequestId;
    }

    public void setLeaveRequestId(LeaveRequests leaveRequestId) {
        this.leaveRequestId = leaveRequestId;
    }

    public Manager getApprovalBy() {
        return approvalBy;
    }

    public void setApprovalBy(Manager approvalBy) {
        this.approvalBy = approvalBy;
    }

}
