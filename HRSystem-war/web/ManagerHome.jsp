<%-- 
    Document   : home
    Created on : Apr 9, 2025, 1:30:36 PM
    Author     : P52
--%>

<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <title>Manager Home - Leave Requests</title>
        <style>
            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 20px;
            }
            th, td {
                padding: 8px 15px;
                border: 1px solid #ccc;
                text-align: left;
            }
            th {
                background-color: #f2f2f2;
            }
        </style>
    </head>
    <body>
        <c:if test="${not empty message}">
            <div id="successMsg" class="alert alert-success text-center">${message}</div>

            <script>
                setTimeout(function () {
                    var msg = document.getElementById("successMsg");
                    if (msg) {
                        msg.style.display = "none";
                    }
                }, 3000); 
            </script>
        </c:if>

        <h2 class="text-center">Welcome, Manager!</h2>
        <h3 class="text-center">List of Leave Requests</h3>
        <form action="ManagerServlet" method="get">
            <table class="table table-hover">
                <tr>
                    <th class="text-center">#</th>
                    <th class="text-center">Start Date</th>
                    <th class="text-center">End Date</th>
                    <th class="text-center">Status</th>
                    <th class="text-center">Employee ID</th>
                    <th class="text-center">Rejected Reason</th>
                    <th class="text-center">Action</th>
                </tr>
                <c:forEach items="${lrList}" var="it">
                    <tr>
                        <td>${it.getId()}</td>
                        <td>
                            <fmt:formatDate value="${it.getStartDate()}" pattern="dd-MM-yyyy"/>
                        </td>
                        <td>
                            <fmt:formatDate value="${it.getEndDate()}" pattern="dd-MM-yyyy"/>
                        </td>
                        <td>${it.getStatus()}</td>
                        <td>${it.getEmployeeID()}</td>
                        <td>${it.getRejectedReason()}</td>
                        <td class="btn btn-gray"><a href="LoginServlet?action=DetailLeaveRequest&id=${it.id}">Details</a></td>
                    </tr>
                </c:forEach>
            </table>
        </form>
    </body>
</html>
