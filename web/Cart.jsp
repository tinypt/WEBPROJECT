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
                background-color: #fafafa;
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
        <c:if test="${additem!=null}">
            <div class="alert alert-success" role="alert">
                ${additem}
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>

        </c:if>
        <c:if test="${edititem!=null}">
            <div class="alert alert-success" role="alert">
                ${edititem}
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
        </c:if>
        <div class="container mb-5 pt-4">
            
            <div class="card pl-3 pt-3 pb-1 mb-3">
                <p><img src="image/box.png" class="mr-2" width="20"/>จัดส่งสินค้าฟรี</p>
            </div>
            
            <div class="card p-3">
                <h3 class="pt-2">รายการสินค้าของคุณ</h3>
                <hr/>
                <c:choose>
                    <c:when test="${cart.size>0}">
                        <form action="UpdateIteminCart" method="post">
                            <c:forEach items="${cart.lineItems}" var="line" varStatus="vs">
                                <div class="row" style="height: 180px;">
                                    <div class="col-3"><img src="image/${line.prod.productId}.jpg" width="200"></div>
                                    <div class="col-9">
                                        <div class="row">
                                            <div class="col-4">
                                                <a href="Getdetail?product=${line.prod.productId}"><h5>${line.prod.productName}</h5></a>
                                            </div>
                                            <div class="col-3">
                                                <p style="font-size: 14px;">฿ ${line.prod.productPrice}</p>
                                            </div>
                                            <div class="col-2">
                                                <input id="abc" onchange="Allowbtn()" type="number" min="0" max="20" style="width: 60px;"  name="${line.prod.productId}" value="${line.quantity}"/>
                                            </div>
                                            <div class="col-3 text-right">
                                                <h6>฿ ${line.totalprice}</h6>
                                            </div>
                                        </div>
                                        <hr/>
                                        <div class="text-right pr-2">
                                            <span class="text-primary">นำสินค้าออก </span><input id="checkme" type="checkbox" name="deleteproduct" value="${line.prod.productId}" onchange="Allowbtn()">
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                            <div class=" text-right">
                                <input type="submit" class="btn btn-danger" value="แก้ไขตะกร้า" id="subbtn" disabled="true">
                                <hr/>
                                <h3>ราคารวม ฿ ${cart.totalPriceInCart}</h3>
                                <h4>สินค้ารวม ${cart.totalQuantityInCart} ชิ้น</h4>
                                <a href="Confirm">
                                    <input type="button" class="btn btn-primary" value="ต่อไป" id="subbtn2">
                                </a>

                            </div>
                        </form>
                    </c:when>
                    <c:otherwise>
                        <p class="text-center mt-3">ไม่มีรายการสินค้าในตะกร้าของคุณ</p> 
                    </c:otherwise>
                </c:choose>
            </div>
        </div>

    </body>
</html>
