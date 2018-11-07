<%-- 
    Document   : Cart
    Created on : Nov 7, 2018, 11:25:35 PM
    Author     : GT62VR
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cart Page</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdn.datatables.net/1.10.19/css/dataTables.bootstrap.min.css">
        <script src="https://code.jquery.com/jquery-3.3.1.js"></script>
        <script src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
        <script src="https://cdn.datatables.net/1.10.19/js/dataTables.bootstrap.min.js"></script>

        <link href="https://fonts.googleapis.com/css?family=Prompt" rel="stylesheet">
        <style>
            body {
                font-family: 'Prompt', sans-serif;
            }
        </style>

    </head>
    <body>
        <jsp:include page="include/header.jsp"/>
            <br>
            <br>
            <br>
        <form action="UpdateIteminCart" method="post">
            <table id="example" class="table">
                <thead>
                <th>รายการ</th>
                <th>Image</th>
                <th>ชื่อขนม</th>
                <th>ราคาต่อหน่วย</th>
                <th>จำนวน</th>
                <th>ราคารวม</th>
                <!--<th>นำสินค้าออก</th>-->
                </thead>


                <c:forEach items="${cart.lineItems}" var="line" varStatus="vs">
                    <tr >
                        <td>${vs.count}</td>
                        <td><img src="image/${line.prod.productId}.jpg" width="120"></td>
                        <td>${line.prod.productName}</td>
                        <td>${line.prod.productPrice}</td>
                        <td>${line.quantity}</td>
                        <td>${line.totalprice}</td>
                        <!--                        <td>
                                                    <input type="checkbox" name="check" value="check">
                                                </td>-->
                    </tr>
                </c:forEach>


                <tr>
                    <td colspan="3"></td>
                    <td></td>
                    <td>${cart.totalQuantityInCart}</td>
                    <td>${cart.totalPriceInCart}</td>
                </tr>
            </table>
            <br>
        </form>
    </body>
</html>
