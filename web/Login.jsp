<%-- 
    Document   : Login
    Created on : Oct 31, 2018, 11:38:47 PM
    Author     : GT62VR
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://fonts.googleapis.com/css?family=Chakra+Petch" rel="stylesheet">
        <style>
            body {
                font-family: 'Chakra Petch', sans-serif;
            }
        </style>
        <title>Login Page</title>
    </head>
    <body>
        <jsp:include page="include/header.jsp"/>
        <h1>Login</h1>
        ${activate} 
        ${actcom} 
        ${Logfail} 
        <c:if test="${link!=null}">
            <a href="${link}">Click this to activate</a> <br>
        </c:if>
        <form method="post" action="Login">
            username: <input type="text" name="username"><br>
            password: <input type="password" name="password"><br>
            <input type="submit" value="submit">
        </form>
        <br>
        <a href="Register">Register ?</a> <br>
        <a href="montho.jsp">Back</a>
    </body>
</html>
