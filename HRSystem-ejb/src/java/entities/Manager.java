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
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Luu Bao
 */
@Entity
@Table(name = "Manager")
@NamedQueries({
    @NamedQuery(name = "Manager.findAll", query = "SELECT m FROM Manager m"),
    @NamedQuery(name = "Manager.findById", query = "SELECT m FROM Manager m WHERE m.id = :id")})
public class Manager implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @OneToMany(mappedBy = "approvalBy")
    private List<LeaveApprovals> leaveApprovalsList;
    @JoinColumn(name = "UserId", referencedColumnName = "Id")
    @ManyToOne
    private Accounts userId;

    public Manager() {
    }

    public Manager(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<LeaveApprovals> getLeaveApprovalsList() {
        return leaveApprovalsList;
    }

    public void setLeaveApprovalsList(List<LeaveApprovals> leaveApprovalsList) {
        this.leaveApprovalsList = leaveApprovalsList;
    }

    public Accounts getUserId() {
        return userId;
    }

    public void setUserId(Accounts userId) {
        this.userId = userId;
    }

}
