/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "Employees")
@NamedQueries({
    @NamedQuery(name = "Employees.findAll", query = "SELECT e FROM Employees e"),
    @NamedQuery(name = "Employees.findById", query = "SELECT e FROM Employees e WHERE e.id = :id"),
    @NamedQuery(name = "Employees.findByPosition", query = "SELECT e FROM Employees e WHERE e.position = :position"),
    @NamedQuery(name = "Employees.findByHireDate", query = "SELECT e FROM Employees e WHERE e.hireDate = :hireDate"),
    @NamedQuery(name = "Employees.findByIdentityCard", query = "SELECT e FROM Employees e WHERE e.identityCard = :identityCard")})
public class Employees implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Id")
    private String id;
    @Column(name = "Position")
    private String position;
    @Basic(optional = false)
    @Column(name = "HireDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date hireDate;
    @Column(name = "IdentityCard")
    private String identityCard;
    @OneToMany(mappedBy = "employeeID")
    private List<Attendance> attendanceList;
    @JoinColumn(name = "UserId", referencedColumnName = "Id")
    @ManyToOne
    private Accounts userId;
    @JoinColumn(name = "DepartmentId", referencedColumnName = "Id")
    @ManyToOne
    private Department departmentId;
    @OneToMany(mappedBy = "employeeID")
    private List<LeaveRequests> leaveRequestsList;

    public Employees() {
    }

    public Employees(String id) {
        this.id = id;
    }

    public Employees(String id, Date hireDate) {
        this.id = id;
        this.hireDate = hireDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public List<Attendance> getAttendanceList() {
        return attendanceList;
    }

    public void setAttendanceList(List<Attendance> attendanceList) {
        this.attendanceList = attendanceList;
    }

    public Accounts getUserId() {
        return userId;
    }

    public void setUserId(Accounts userId) {
        this.userId = userId;
    }

    public Department getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Department departmentId) {
        this.departmentId = departmentId;
    }

    public List<LeaveRequests> getLeaveRequestsList() {
        return leaveRequestsList;
    }

    public void setLeaveRequestsList(List<LeaveRequests> leaveRequestsList) {
        this.leaveRequestsList = leaveRequestsList;
    }


}
