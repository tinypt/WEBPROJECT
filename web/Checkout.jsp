<%-- 
    Document   : Checkout
    Created on : Nov 12, 2018, 2:06:32 PM
    Author     : Hong
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Checkout Page</title>

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
                background-color: #F7819F;
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
    <center>
        <jsp:include page="include/header.jsp"/>
        <c:if test="${update!=null}">
            <div class="alert alert-success" role="alert">
                ${update}
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>

        </c:if>
        <div class="container-fluid text-center p-4" style="background-color: #fafafa;">
            <h1>รายการการสั่งซื้อ</h1> 
        </div>
        <br>
        <div class="container">
            <div class="card pl-3 pt-3 pb-1 mb-3 text-left">
                <p><img src="image/box.png" class="mr-2" width="20"/>จัดส่งสินค้าฟรี</p>
            </div>
            <div class="row">
                <div class="col" style="text-align: left; padding-left: 80px; padding-right: 0;">
                    <div class="row">
                        
                   
                    <div class="col-5 text-center">


                        <c:choose>
                            <c:when test="${acc.picturename!=null}">
                                <img src="image-account/${acc.picturename}" alt="user" width="150px">
                            </c:when>
                            <c:otherwise>
                                <img src="image-account/user.png" alt="user" width="150px">
                            </c:otherwise>
                        </c:choose>
                    </div>  
                    <div class="col-7">
                        อีเมลล์: ${acc.email}<br>
                            ชื่อลูกค้า: ${acc.name} ${acc.surname} <br>
                            ที่อยู่ที่จัดส่ง: ${acc.address} <br>
                            เบอร์ติดต่อ: ${acc.telnumber}

                        <a href="#" data-toggle="modal" data-target="#myModal">
                            แก้ไข
                        </a>
                    </div> </div>
                    <!-- The Modal -->
                    <div class="modal" id="myModal">
                        <div class="modal-dialog" style="top: 150px;">
                            <div class="modal-content">

                                <!-- Modal Header -->
                                <div class="modal-header">
                                    <h4 class="modal-title">ข้อมูลลูกค้า</h4>
                                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                                </div>

                                <!-- Modal body -->
                                <form action="UpdateAccount" method="post" enctype="multipart/form-data">
                                    <div class="modal-body">
                                        <table>
                                            <tr>
                                                <td>ชื่อลูกค้า: </td>
                                                <td><input type="text" value="${acc.name} ${acc.surname}" name="name" onchange="Allowbtn()" required></td> 
                                            </tr> 
                                            <tr>
                                                <td>ที่อยู่ที่จัดส่ง: </td>
                                                <td>
                                                    <textarea rows="4" cols="20"  name="address" required onchange="Allowbtn()">${acc.address}</textarea>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>เบอร์ติดต่อ: </td>
                                                <td>
                                                    <input type="tel" name="telno"  value="${acc.telnumber}" pattern="[0-9]{10}" onchange="Allowbtn()" required>
                                                </td>
                                            </tr>
                                        </table>
                                        <br>
                                        <input type="hidden" name="username" value="${acc.username}">
                                        <input type="hidden" name="form" value="checkout">
                                    </div>

                                    <!-- Modal footer -->
                                    <div class="modal-footer">
                                        <input type="submit" id="subbtn" class="btn btn-primary" disabled>
                                        <!--<button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>-->
                                    </div>
                                </form>

                            </div>
                        </div>
                    </div>
                </div>
                <div class="col">
                </div>
            </div>
        </div>
        <br>
        <table id="example" class="table" style="width: 1000px;">
            <thead>
            <th>รายการ</th>
            <th>ชื่อขนม</th>
            <th>ราคาต่อหน่วย (บาท)</th>
            <th>จำนวน (ชิ้น)</th>
            <th>ราคารวม (บาท)</th>
            </thead>


            <c:forEach items="${cart.lineItems}" var="line" varStatus="vs">
                <tr >
                    <td>${vs.count}</td>
                    <td>${line.prod.productName}</td>
                    <td>${line.prod.productPrice}</td>
                    <td>${line.quantity} </td>
                    <td>${line.totalprice}</td>
                </tr>
            </c:forEach>
            <tr>
                <td colspan="2"></td>
                <td></td>
                <td>จำนวนสินค้ารวม ${cart.totalQuantityInCart} ชิ้น</td>
                <td>ราคาสินค้ารวม ${cart.totalPriceInCart} บาท</td>
            </tr>
        </table>
        <!--<a href="Cart.jsp">กลับ</a>-->
        <a href="Cart.jsp">
            <button type="button" class="btn" id="subbtn2">กลับ</button>
        </a>
        <a href="Checkout">
            <button  type="button" class="btn"  id="subbtn2">ชำระเงิน</button>
        </a>

        <!--<a href="Checkout" style="text-align: right;">ยืนยันการสั่งซื้อ</a>-->
    </center>


</body>
</html>
