<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="entities.Accounts"%>
<%
    Accounts acc = (Accounts) session.getAttribute("acc");
%>
<html>
<head>
    <title>Create Leave Request</title>
    <style type="text/css">
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f9;
            color: #333;
            margin: 0;
            padding: 0;
        }

        .container {
            width: 50%;
            margin: 50px auto;
            padding: 30px;
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }

        h2 {
            text-align: center;
            color: #4CAF50;
        }

        form {
            display: flex;
            flex-direction: column;
        }

        label {
            font-size: 1.1em;
            margin-bottom: 5px;
            color: #333;
        }

        input[type="date"], textarea {
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 4px;
            font-size: 1em;
        }

        textarea {
            resize: vertical;
        }

        input[type="submit"] {
            background-color: #4CAF50;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            font-size: 1.1em;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        input[type="submit"]:hover {
            background-color: #45a049;
        }

        .form-group {
            margin-bottom: 20px;
        }

        .error-message {
            color: red;
            font-size: 0.9em;
            margin-top: -10px;
        }
    </style>
    <script type="text/javascript">
        // Function to validate the start date and end date
        function validateDates() {
            var today = new Date();
            var startDate = new Date(document.getElementsByName("startDate")[0].value);
            var endDate = new Date(document.getElementsByName("endDate")[0].value);
            
            // Check if start date is after today's date
            if (startDate <= today) {
                alert("Start date must be after today's date.");
                return false;
            }
            
            // Check if end date is after start date
            if (endDate <= startDate) {
                alert("End date must be after start date.");
                return false;
            }
            
            return true;
        }
    </script>
</head>
<body>
    <div class="container">
        <h2>Create Leave Request</h2>

        <form action="LoginServlet" method="post" onsubmit="return validateDates()">
            <input type="hidden" name="action" value="CreateLeaveRequest" />
            
            <div class="form-group">
                <label>Start Date:</label>
                <input type="date" name="startDate" required /><br/>
            </div>
            
            <div class="form-group">
                <label>End Date:</label>
                <input type="date" name="endDate" required /><br/>
            </div>
            
            <div class="form-group">
                <label>Reason:</label><br/>
                <textarea name="reason" rows="4" cols="30" required></textarea><br/>
            </div>
            
            <input type="submit" value="Submit Request" />
        </form>
    </div>
</body>
</html>
