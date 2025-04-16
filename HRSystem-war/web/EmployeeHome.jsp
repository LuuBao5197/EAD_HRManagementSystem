<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Employee Attendance</title>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>
    <h1>Welcome ${acc.fullname}</h1>
    
    <c:if test="${not empty message}">
        <p style="color: green">${message}</p>
    </c:if>
    
    <div class="attendance-actions">
        <form action="AttendanceServlet" method="POST">
            <input type="hidden" name="action" value="checkin">
            <button type="submit">Check In</button>
        </form>
        
        <form action="AttendanceServlet" method="POST">
            <input type="hidden" name="action" value="checkout">
            <button type="submit">Check Out</button>
        </form>
    </div>
    
    <h2>Attendance History</h2>
    <table>
        <thead>
            <tr>
                <th>Date</th>
                <th>Check In</th>
                <th>Check Out</th>
                <th>Working Hours</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${attendanceHistory}" var="attendance">
                <tr>
                    <td><fmt:formatDate value="${attendance.attendanceDate}" pattern="dd/MM/yyyy"/></td>
                    <td><fmt:formatDate value="${attendance.checkin}" pattern="HH:mm:ss"/></td>
                    <td>
                        <c:choose>
                            <c:when test="${not empty attendance.checkOut}">
                                <fmt:formatDate value="${attendance.checkOut}" pattern="HH:mm:ss"/>
                            </c:when>
                            <c:otherwise>
                                Not checked out
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <c:if test="${not empty attendance.checkOut}">
                            <!-- Tính toán số giờ làm việc -->
                            <c:set var="diffInMillis" value="${attendance.checkOut.time - attendance.checkin.time}"/>
                            <c:set var="diffInHours" value="${diffInMillis / (1000 * 60 * 60)}"/>
                            <fmt:formatNumber value="${diffInHours}" maxFractionDigits="2"/> hours
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>