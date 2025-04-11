<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Admin Page</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body class="bg-light">
        <div class="container mt-5">
            <c:if test="${not empty msg}">
                <div class="alert alert-success">${msg}</div>
            </c:if>
                <a href="create.jsp">Create new account? </a>
            <h1 class="mb-4 text-primary">Hello, ${acc.getFullName()}</h1>

            <h2 class="mb-3">Danh sách tài khoản</h2>
            <table class="table table-bordered table-striped table-hover shadow">
                <thead class="table-dark">
                    <tr>
                        <th scope="col">ID</th>
                        <th scope="col">Username</th>
                        <th scope="col">Full Name</th>                        
                        <th scope="col">Phone</th>
                        <th scope="col">Email</th>
                        <th scope="col">Role</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="a" items="${list}">
                        <tr>
                            <td>${a.getId()}</td>
                            <td>${a.getUsername()}</td>
                            <td>${a.getFullName()}</td>                            
                            <td>${a.getPhone()}</td>
                            <td>${a.getEmail()}</td>
                            <td>${a.getRole()}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <!-- Bootstrap JS Bundle (Optional) -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
