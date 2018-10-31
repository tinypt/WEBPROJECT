<%-- 
    Document   : Login
    Created on : Oct 31, 2018, 11:38:47 PM
    Author     : GT62VR
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
    </head>
    <body>
        <jsp:include page="include/header.jsp"/>
        <h1>Login</h1>
        ${actcom}
        ${Logfail}
        <form method="post" action="Login">
            username: <input type="text" name="username"><br>
            password: <input type="text" name="password"><br>
            <input type="submit" value="submit">
        </form>
        <br>
        <a href="Register">Register ?</a>
    </body>
</html>
