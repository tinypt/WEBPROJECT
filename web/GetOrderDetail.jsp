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
        <div class="container mb-5 pt-4">
            <div class="card p-3">
                <h3 class="pt-2">รายการการสั่งซื้อ</h3>
                <hr/>
        <c:choose>
            <c:when test="${orderdetail!=null}">
                <c:forEach items="${orderdetail}" var="orderdetail" varStatus="vs">
                <div class="row" style="height: 180px;">
                    <div class="col-3">
                        <img src="image/${orderdetail.productId.productId}.jpg" width="200">
                    </div>
                         <div class="col-9">
                                        <div class="row">
                                            <div class="col-4">
                                                <h5>${orderdetail.productId.productName}</h5>
                                            </div>
                                            <div class="col-3">
                                                <p style="font-size: 14px;">฿ ${orderdetail.priceeach}</p>
                                                
                                            </div>
                                            <div class="col-2">
                                                <p style="font-size: 14px;">${orderdetail.quantity} ชิ้น</p>
                                            </div>
                                            <div class="col-3 text-right">
                                                <h6>฿ ${orderdetail.quantity*orderdetail.priceeach}</h6>
                                            </div>
                                        </div>
                                            <hr/>
                         </div>
                                            </div>
                        </c:forEach>
                   
                            <div class="container-fluid text-right mt-3">
                                 <hr/>
                                 <h5>จำนวนสินค้ารวม ${quantityall} ชิ้น</h5>
                                <h3>ราคาสินค้ารวม ฿ ${order.totalprice}</h3>
                            </div>
                    
            </c:when>
            <c:otherwise>
                <div class="container-fluid text-center p-4" style="background-color: #fafafa;">
                    <h1>ไม่มีรายการการสั่งซื้อ</h1> 
                </div>
            </c:otherwise>
        </c:choose>
            </div>
        </div>
    <center class="mb-5">
        <a href="GetOrder.jsp" >กลับ ></a>
    </center>
</body>
</html>
