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

        <link href="https://fonts.googleapis.com/css?family=Prompt" rel="stylesheet">
        <style>
            body {
                font-family: 'Prompt', sans-serif;
                font-size: 30px;
            }
            .table {
                font-size: 17px;
            }
            .btn {
                background-color: #86B404;
                color:white;
            }
        </style>
        <script>
            function Allowbtn() {
                document.getElementById("subbtn").disabled = false;
            }
        </script>
    </head>
    <body>
        <jsp:include page="include/header.jsp"/>
        <h1 style="text-align: center;">รายการสินค้า</h1>
        <c:choose>
            <c:when test="${cart.size>0}">
                <form action="UpdateIteminCart" method="post">
                    <div>
                        <table id="example" class="table">
                            <thead>
                            <th>รายการ</th>
                            <th>Image</th>
                            <th>ชื่อขนม</th>
                            <th>ราคาต่อหน่วย (บาท)</th>
                            <th>จำนวน (ชิ้น)</th>
                            <th>ราคารวม (บาท)</th>
                            <th>นำสินค้าออก</th>
                            </thead>


                            <c:forEach items="${cart.lineItems}" var="line" varStatus="vs">
                                <tr >
                                    <td>${vs.count}</td>
                                    <td><img src="image/${line.prod.productId}.jpg" width="200"></td>
                                    <td>${line.prod.productName}</td>
                                    <td>${line.prod.productPrice}</td>
                                    <td>
                                        <input type="number" min="0" max="20" style="width: 60px;"  name="${line.prod.productId}" value="${line.quantity}"/>
                                    </td>
                                    <td>${line.totalprice}</td>
                                    <td>
                                        <input id="checkme" type="checkbox" name="prod_id" value="${line.prod.productId}" onchange="Allowbtn()">
                                    </td>
                                </tr>
                            </c:forEach>


                            <tr>
                                <td colspan="3"></td>
                                <td></td>
                                <td>จำนวนสินค้ารวม ${cart.totalQuantityInCart} ชิ้น</td>
                                <td>ราคาสินค้ารวม ${cart.totalPriceInCart} บาท</td>
                            </tr>
                        </table>
                        <br>
                    </div> 
                    <center>
                        <a href="Checkout">
                            <input type="button" class="btn " value="Checkout" id="subbtn2" >
                        </a>
                        <input type="submit" class="btn " value="UpdateCart" id="subbtn" disabled="true">
                    </center> 
                </form>
            </c:when>
            <c:otherwise>
                <h2 style="text-align: center;">ไม่มีรายการสินค้าในตะกร้าของคุณ</h2>
            </c:otherwise>
        </c:choose>
    </body>
</html>
