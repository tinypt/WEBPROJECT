<%-- 
    Document   : Register
    Created on : Oct 31, 2018, 11:52:46 PM
    Author     : GT62VR
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
               <link href="https://fonts.googleapis.com/css?family=Prompt" rel="stylesheet">
        <style>
            body {
                font-family: 'Prompt', sans-serif;
            }
        </style>
        <title>Register Page</title>
    </head>
    <body>
        <jsp:include page="include/header.jsp"/>
        <h1>Register</h1>
        <form method="post" action="Register">
            username: <input type="text" name="username"> <br><br>
            password: <input type="password" name="password"><br><br>
            address: <textarea rows="4" cols="30" name="address"></textarea><br><br>
            name: <input type="text" name="name"><br><br>
            surname: <input type="text" name="surname"><br><br>
            telno: <input type="text" name="telno"><br><br>
            <input type="submit" value="submit">
        </form>
        <a href="montho.jsp">Back</a>
    </body>
</html>
