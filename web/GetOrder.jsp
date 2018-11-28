<%-- 
    Document   : GetOrder
    Created on : Nov 14, 2018, 12:44:18 PM
    Author     : GT62VR
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Getorder Page</title>

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
    </head>
    <body>
        <jsp:include page="include/header.jsp"/>
        <div class="container-fluid text-center p-4" style="background-color: #fafafa;">
            <h1>ประวัติการสั่งซื้อ</h1> 
        </div>
        <c:choose>
            <c:when test="${orders!=null}">
                <div class="container mt-3">
                    <table id="example" class="table table-dark">
                        <thead>
                        <th>#</th>
                            <th>เลขที่สั่งซื้อ</th>
                            <th>วันที่สั่งซื้อ</th>
                            <th></th>
                        </thead>

                        <c:forEach items="${orders}" var="order" varStatus="vs">
                            <tr class="bg-light text-dark">
                                <td>${vs.count}</td>
                                <td>${order.orderId}</td>
                                <td>${order.orderDate}</td>
                                
                                <td class="text-right pr-5">
                                    <a href="GetOrderDetail?orderid=${order.orderId}" class="btn btn-success">
                                        ดูรายละเอียด
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                    <br>
                </div> 
            </c:when>
            <c:otherwise>
                <p class="text-center mt-3">ยังไม่มีประวัติการสั่งซื้อ</p> 
            </c:otherwise>
        </c:choose>
    <center class="mb-5">

        <a href="montho.jsp">ไปเลือกซื้อสินค้ากับเรา ></a>
    </center>
</body>
</html>
