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
        <br>
        <c:choose>
            <c:when test="${orders!=null}">
                <div>
                    <table id="example" class="table">
                        <thead>
                        <th>รายการสั่งซื้อ</th>
                        <th>วันที่การสั่งซื้อ</th>
                        <th>ราคารวม (บาท)</th>
                        <th>รายละเอียด</th>
                        </thead>

                        <c:forEach items="${orders}" var="order" varStatus="vs">
                            <tr >
                                <td>${vs.count}</td>
                                <td>${order.orderDate}</td>
                                <td>${order.totalprice}</td>
                                <td>
                                    <a href="GetOrderDetail?orderid=${order.orderId}">
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
                <h2 style="text-align: center;">ไม่มีประวัติการสั่งซื้อ</h2> 
            </c:otherwise>
        </c:choose>
    <center>

        <a href="montho.jsp">กลับ</a>
    </center>
</body>
</html>
