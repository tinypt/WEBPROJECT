<%-- 
    Document   : GetOrderDetail
    Created on : Nov 14, 2018, 12:57:31 PM
    Author     : GT62VR
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Order Detail Page</title>
    </head>
    <body>
        <jsp:include page="include/header.jsp"/>
        <c:choose>
            <c:when test="${orderdetail!=null}">
                <h1 style="text-align: center;">รายการการสั่งซื้อ</h1>
                <div>
                    <table id="example" class="table">
                        <thead>
                        <th>รายการ</th>
                        <th>ชื่อขนม</th>
                        <th>รูป</th>
                        <th>ราคาต่อหน่วย (บาท)</th>
                        <th>จำนวนสินค้า (ชิ้น)</th>
                        <th>ราคารวม (บาท)</th>
                        </thead>

                        <c:forEach items="${orderdetail}" var="orderdetail" varStatus="vs">
                            <tr >
                                <td>${vs.count}</td>
                                <td>${orderdetail.productId.productName}</td>
                                <td><img src="image/${orderdetail.productId.productId}.jpg" width="200"></td>
                                <td>${orderdetail.priceeach}</td>
                                <td>${orderdetail.quantity}</td>
                                <td>${orderdetail.quantity*orderdetail.priceeach}</td>
                            </tr>
                        </c:forEach>
                    </table>
                    <br>
                </div> 
            </c:when>
            <c:otherwise>
                <h2 style="text-align: center;">ไม่มีรายการการสั่งซื้อ</h2>
            </c:otherwise>
        </c:choose>
    <center>
        <a href="GetOrder.jsp">กลับ</a>
    </center>
</body>
</html>
