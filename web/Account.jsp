<%-- 
    Document   : Account
    Created on : Nov 1, 2018, 11:03:07 PM
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
        <title>Account Page</title>
    </head>
    <body>
        <jsp:include page="include/header.jsp"/>
        <h1>Your Account</h1>
        <form action="UpdateAccount" method="post">
            <input type="hidden" name="username" value="${acc.username}">
            name: <input type="text" name="name" value="${acc.name}"><br><br>
            surname: <input type="text" name="surname" value="${acc.surname}"><br><br>
            address: <textarea rows="4" cols="30" name="address">${acc.address}</textarea><br><br>
            telno: <input type="text" name="telno" value="${acc.telnumber}"><br><br>
            <input type="submit" value="UPDATE Account">
        </form>
    </body>
</html>
