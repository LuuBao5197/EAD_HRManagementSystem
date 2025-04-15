<%-- 
    Document   : home
    Created on : Apr 9, 2025, 1:30:36 PM
    Author     : P52
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Employee Page</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body class="bg-light">
        <div class="container mt-5">
                <a href="createleaverequest.jsp">Create new leave request? </a>
            <h1 class="mb-4 text-primary">Hello, ${acc.getFullName()}</h1>

            <h2 class="mb-3">Danh sách tài khoản</h2>
            <table class="table table-bordered table-striped table-hover shadow">
                <thead class="table-dark">
                    <tr>
                        <th scope="col">ID</th>
                        <th scope="col">StartDate</th>
                        <th scope="col">EndDate</th>                        
                        <th scope="col">getReason</th>
                        <th scope="col">Status</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="a" items="${leaverequestlist}">
                        <tr>
                            <td>${a.getId()}</td>
                            <td>${a.getStartDate()}</td>
                            <td>${a.getEndDate()}</td>                            
                            <td>${a.getReason()}</td>
                            <td>${a.getStatus()}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <!-- Bootstrap JS Bundle (Optional) -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
