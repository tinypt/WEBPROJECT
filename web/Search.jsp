<%-- 
    Document   : Search
    Created on : Nov 1, 2018, 12:34:30 AM
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
        <title>Search Page</title>
    </head>
    <body>
        <jsp:include page="include/header.jsp"/>
        <div class="container mt-3">
            <h1>ค้นหา : ${nameSearch}</h1>
            <h2>พบรายการสินค้า ${qty} สินค้า</h2>
            <h5>${msg}</h5>
            <c:forEach items="${prod}" var="prod">
                <br>
                <img src="image/${prod.productId}.jpg" style="width: 300px"><br>
                product id = &nbsp; ${prod.productId} <br>
                product name = &nbsp; ${prod.productName} <br>
                product price = &nbsp; ${prod.productPrice} <br>
                product datail = &nbsp; ${prod.productDetail} <br>
                product type = &nbsp; ${prod.type} <br>
                -------------------------------------

            </c:forEach>
        </div>
    </body>
</html>
