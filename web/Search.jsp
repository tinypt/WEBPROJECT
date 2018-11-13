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
            table th {
                text-align: center;
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
            <c:if test="${qty>0}">
                <table id="example" class="table">
                    <thead>
                    <th>รายการ</th>
                    <th>ชื่อขนม</th>
                    <th>Image</th>
                    <th>ราคาต่อหน่วย (บาท)</th>
                    <th>รายละเอียด</th>
                    <th>ชนิด</th>
                    <th>ตระกร้า</th>
                    </thead>

                    <c:forEach items="${prod}" var="prod" varStatus="s">
                        <tr>
                            <td>${s.count}</td>
                            <td>${prod.productName}</td>
                            <td><img src="image/${prod.productId}.jpg" width="200"></td>
                            <td>${prod.productPrice}</td>
                            <td>${prod.productDetail}</td>
                            <td>${prod.type}</td>
                            <td>
                                <a href="AddItemtoCart?qty=1&product=${prod.productId}&form=search&name=${nameSearch}">
                                    <button type="button" class="btn btn-outline-warning">เพิ่มลงตะกร้า</button>
                                </a>
                            </td>



                        </tr>
                    </c:forEach>
                </table>
            </c:if>
        </div>
    </body>
</html>
