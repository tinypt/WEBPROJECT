<%-- 
    Document   : Search
    Created on : Nov 1, 2018, 12:34:30 AM
    Author     : GT62VR
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search Page</title>
    </head>
    <body>
        <jsp:include page="include/header.jsp"/>
        <h1>Detail</h1>
        product id = &nbsp; ${prod.productId} <br>
        product name = &nbsp; ${prod.productName} <br>
        product price = &nbsp; ${prod.productPrice} <br>
        product datail = &nbsp; ${prod.productDetail} <br>
        product type = &nbsp; ${prod.type} 
    </body>
</html>
