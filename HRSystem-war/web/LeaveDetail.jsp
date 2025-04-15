<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Leave Requests Detail</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container mt-5">
            <h2>Leave Requests Detail</h2>
            <table class="table table-bordered mt-3">
                <tr>
                    <th>ID</th>
                    <td>${leaveRequest.id}</td>
                </tr>
                <tr>
                    <th>Start Date</th>
                    <td><fmt:formatDate value="${leaveRequest.startDate}" pattern="dd-MM-yyyy" /></td>
                </tr>
                <tr>
                    <th>End Date</th>
                    <td><fmt:formatDate value="${leaveRequest.endDate}" pattern="dd-MM-yyyy" /></td>
                </tr>
                <tr>
                    <th>Reason</th>
                    <td>${leaveRequest.reason}</td>
                </tr>
                <tr>
                    <th>Status</th>
                    <td>${leaveRequest.status}</td>
                </tr>
                <tr>
                    <th>Employee ID</th>
                    <td>${leaveRequest.employeeID.id}</td>
                </tr>
                <c:if test="${not empty leaveRequest.rejectedReason}">
                    <tr>
                        <th>Rejected Reason</th>
                        <td>${leaveRequest.rejectedReason}</td>
                    </tr>
                </c:if>
            </table>
            <a href="ManagerHome.jsp" class="btn btn-secondary">Back to Home</a>
        </div>
        <form action="LoginServlet" method="post" onsubmit="return validateForm()">
            <input type="hidden" name="action" value="UpdateStatus" />
            <input type="hidden" name="id" value="${leaveRequest.id}" />

            <div class="d-flex justify-content-center gap-3">
                <button type="submit" name="status" value="A" class="btn btn-success px-4">Approve</button>
                <button type="button" class="btn btn-danger px-4" onclick="showRejectReason()">Reject</button>
            </div>

            <div id="rejectBox" style="display: none;" class="mt-4 text-center">
                <label for="reason" class="form-label">Rejected Reason: </label>
                <textarea name="rejectedReason" id="reason" class="form-control mx-auto"></textarea>
                <button type="submit" name="status" value="R" class="btn btn-success mt-3 px-4">Confirm</button>
            </div>
        </form>

        <script>
            let isRejecting = false;

            function showRejectReason() {
                document.getElementById("rejectBox").style.display = "block";
                isRejecting = true;
            }

            function validateForm() {
                if (isRejecting) {
                    const reason = document.getElementById("reason").value.trim();
                    if (reason === "") {
                        alert("Vui lòng nhập lý do từ chối.");
                        return false;
                    }
                }
                return true;
            }
        </script>

    </body>
</html>
