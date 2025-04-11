<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Create Account</title>
        <style>
            form {
                width: 400px;
                margin: 50px auto;
                padding: 20px;
                border: 1px solid #ccc;
                border-radius: 8px;
            }
            input[type="text"], input[type="password"], input[type="email"] {
                width: 100%;
                padding: 8px;
                margin: 8px 0;
            }
            input[type="submit"] {
                padding: 10px 20px;
                background-color: #007bff;
                color: white;
                border: none;
                border-radius: 4px;
            }
        </style>
    </head>
    <body>
        <h2 style="text-align:center;">Create New Account</h2>
        <form action="LoginServlet" method="post">
            <label>Username:</label><br>
            <input type="text" name="txtUsn" required><br>

            <label>Password:</label><br>
            <input type="password" name="txtPassword" required><br>

            <label>Full Name:</label><br>
            <input type="text" name="txtFullname" required><br>

            <label>Email:</label><br>
            <input type="email" name="txtEmail" required><br>

            <label>Phone:</label><br>
            <input type="text" name="txtPhone" required><br>

            <label>Role:</label><br>
            <select name="txtRole" required style="margin: 8px 0; width: 100%; padding: 8px;">
                <option value="">SELECT ROLE</option>           
                <option value="Manager">Manager</option>           
                <option value="Employee">Employee</option>

            </select>

            <input type="submit" value="Create" name="action"/>
        </form>
    </body>
</html>
