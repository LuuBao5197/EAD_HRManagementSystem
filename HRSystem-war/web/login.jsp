<%-- 
    Document   : login
    Created on : Apr 2, 2025, 3:42:45 PM
    Author     : P52
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    
    <body class="d-flex align-items-center justify-content-center vh-100 bg-light">
        
        <div class="card p-4 shadow-lg w-25">
            <h2 class="text-center mb-4">Login</h2>
            
             <c:if test="${not empty error}">
                    <div class="alert alert-danger">${error}</div>
                </c:if>
            <form action="LoginServlet" method="post">
                <div class="mb-3">
                    <label class="form-label">Username</label>
                    <input type="text" name="txtUsn" class="form-control" placeholder="Enter username..." required />
                </div>
                 <div class="mb-3">
                    <label class="form-label">Password</label>
                    <input type="text" name="txtPwd" class="form-control" placeholder="Enter password..." required />
                </div>
                <div class="d-grid">
                    <input type="submit" class="btn btn-primary" name="action" value="Login">
                </div>
            </form>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>