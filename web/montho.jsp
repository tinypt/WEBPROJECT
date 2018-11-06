<%-- 
    Document   : montho
    Created on : Oct 31, 2018, 11:19:06 PM
    Author     : GT62VR
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <title>มณโฑขนมหวาน</title>
    </head>
    <body>
        <jsp:include page="include/header.jsp"/>

        <br>
        <c:if test="${link!=null}">
            <a href="${link}">click to activate</a>
        </c:if>
        ${type}
        ${update}
    </body>
</html>
