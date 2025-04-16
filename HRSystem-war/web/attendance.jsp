<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Hệ thống chấm công</title>
    <style>
        .tab {
            overflow: hidden;
            border: 1px solid #ccc;
            background-color: #f1f1f1;
        }
        .tab button {
            background-color: inherit;
            float: left;
            border: none;
            outline: none;
            cursor: pointer;
            padding: 14px 16px;
            transition: 0.3s;
        }
        .tab button:hover {
            background-color: #ddd;
        }
        .tab button.active {
            background-color: #4CAF50;
            color: white;
        }
        .tabcontent {
            display: none;
            padding: 6px 12px;
            border: 1px solid #ccc;
            border-top: none;
        }
        .tabcontent.active {
            display: block;
        }
        table {
            width: 100%;
            border-collapse: collapse;
        }
        table, th, td {
            border: 1px solid #ddd;
        }
        th, td {
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
        .message {
            padding: 10px;
            margin: 10px 0;
            border-radius: 4px;
        }
        .success {
            background-color: #dff0d8;
            color: #3c763d;
        }
        .error {
            background-color: #f2dede;
            color: #a94442;
        }
    </style>
</head>
<body>
    <h1>Hệ thống chấm công</h1>
    
    <!-- Hiển thị thông báo -->
    <c:if test="${not empty message}">
        <div class="message success">${message}</div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="message error">${error}</div>
    </c:if>
    
    <!-- Tab navigation -->
    <div class="tab">
        <button class="tablinks active" onclick="openTab(event, 'checkinTab')">Chấm công</button>
        <button class="tablinks" onclick="openTab(event, 'historyTab')">Lịch sử</button>
    </div>
    
    <!-- Tab content - Chấm công -->
    <div id="checkinTab" class="tabcontent active">
        <h2>Chấm công hôm nay</h2>
        <p>Thời gian hiện tại: <span id="currentTime"></span></p>
        
        <div style="margin: 20px 0;">
            <form action="AttendanceServlet" method="post" style="display: inline-block;">
                <input type="hidden" name="action" value="checkin">
                <button type="submit" style="padding: 10px 20px; background-color: #4CAF50; color: white; border: none; cursor: pointer;">Check-in</button>
            </form>
            
            <form action="AttendanceServlet" method="post" style="display: inline-block; margin-left: 10px;">
                <input type="hidden" name="action" value="checkout">
                <button type="submit" style="padding: 10px 20px; background-color: #f44336; color: white; border: none; cursor: pointer;">Check-out</button>
            </form>
        </div>
        
        <div id="todayStatus">
            <h3>Trạng thái hôm nay</h3>
            <p>Check-in: ${todayAttendance != null ? todayAttendance.checkin : 'Chưa check-in'}</p>
            <p>Check-out: ${todayAttendance != null ? todayAttendance.checkOut : 'Chưa check-out'}</p>
        </div>
    </div>
    
    <!-- Tab content - Lịch sử -->
    <div id="historyTab" class="tabcontent">
        <h2>Lịch sử chấm công</h2>
        
        <table>
            <tr>
                <th>Ngày</th>
                <th>Check-in</th>
                <th>Check-out</th>
                <th>Thời gian làm việc</th>
            </tr>
            <c:forEach items="${attendanceList}" var="att">
                <tr>
                    <td>${att.attendanceDate}</td>
                    <td>${att.checkin}</td>
                    <td>${att.checkOut}</td>
                    <td>
                        <c:if test="${att.checkin != null && att.checkOut != null}">
                            
                            ${(att.checkOut.time - att.checkin.time) / (1000 * 60 * 60)} giờ
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
    
    <div style="margin-top: 20px;">
        <a href="login.jsp" style="text-decoration: none; color: #333;">Đăng xuất</a>
    </div>
    
    <script>

        function openTab(evt, tabName) {
            var i, tabcontent, tablinks;
            
            tabcontent = document.getElementsByClassName("tabcontent");
            for (i = 0; i < tabcontent.length; i++) {
                tabcontent[i].className = tabcontent[i].className.replace(" active", "");
            }
            
            tablinks = document.getElementsByClassName("tablinks");
            for (i = 0; i < tablinks.length; i++) {
                tablinks[i].className = tablinks[i].className.replace(" active", "");
            }
            
            document.getElementById(tabName).className += " active";
            evt.currentTarget.className += " active";
        }
        
     
        function updateCurrentTime() {
            var now = new Date();
            document.getElementById('currentTime').textContent = now.toLocaleString();
        }
        
        
        updateCurrentTime();
        setInterval(updateCurrentTime, 1000);
    </script>
</body>
</html>