<%-- 
    Document   : getdetail
    Created on : Nov 6, 2018, 9:26:22 PM
    Author     : Hong
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <title>${product.productName}</title>
        <link href="htDetailtps://fonts.googleapis.com/css?family=Prompt" rel="stylesheet">

        <style>
            .qty .count {
                color: #000;
                display: inline-block;
                vertical-align: top;
                font-size: 25px;
                font-weight: 700;
                line-height: 30px;
                    ;min-width: 35px;
                text-align: center;
            }
            .qty .plus {
                cursor: pointer;
                display: inline-block;
                vertical-align: top;
                color: white;
                width: 30px;
                height: 30px;
                font: 30px/1 Arial,sans-serif;
                text-align: center;
                border-radius: 50%;
            }
            .qty .minus {
                cursor: pointer;
                display: inline-block;
                vertical-align: top;
                color: white;
                width: 30px;
                height: 30px;
                font: 30px/1 Arial,sans-serif;
                text-align: center;
                border-radius: 50%;
                background-clip: padding-box;
            }
            div {
                text-align: center;
            }
            .minus:hover{
                background-color: #717fe0 !important;
            }
            .plus:hover{
                background-color: #717fe0 !important;
            }
            /*Prevent text selection*/
            span{
                -webkit-user-select: none;
                -moz-user-select: none;
                -ms-user-select: none;
            }
            input{  
                border: 0;
                width: 2%;
            }
            input::-webkit-outer-spin-button,
            input::-webkit-inner-spin-button {
                -webkit-appearance: none;
                margin: 0;
            }
            input:disabled{
                background-color:white;
            }

        </style>

        <script>
            function minusValue() {
                quan = document.getElementById("quantity").value;
                quan--;
                if (quan < 1) {
                    quan = 1;
                }
                document.getElementById("quantity").value = quan;
            }
            function plusValue() {
                quan = document.getElementById("quantity").value;
                quan++;
                if (quan > 20) {
                    quan = 20;
                }
                document.getElementById("quantity").value = quan;
            }
            function favourite() {
                x = document.getElementById("whiteFav");
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
        <c:if test="${addfav!=null}">
            <div class="alert alert-success" role="alert">
                ${addfav}
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>

        </c:if>
        <c:if test="${removefav!=null}">
            <div class="alert alert-warning" role="alert">
                ${removefav}
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>

        </c:if>
        <div class="container pt-2 pb-0 mb-0 text-left">
            <h3>${product.productName}</h3>
        </div>
        <hr/>
        <div class="container mt-5 mb-5">
            <div class="row">
                <div class="col-7 text-center">
                    <img src="image/${product.productId}.jpg" style="width: 600px">
                </div>
                <div class="col-4">



                    <h1>ชื่อขนม : ${product.productName}</h1>
                    <div class="text-left">
                        <h5>รายละเอียด :<h5/> 
                            <div class="card p-3 text-left mb-2 border-primary">
                                <p style="line-height:1.7;">${product.productDetail}</p>
                            </div>
                            <div class="card p-3 text-left mb-2 bg-light border-light">
                                <h4>฿ ${product.productPrice}</h4>


                                <form action="AddItemtoCart" method="post">
                                    <div class="qty mt-3 text-left">
                                        <button id="minus" type="button" class="btn btn-info" onclick="minusValue()">-</button>
                                        <input id="quantity" type="number" class="count" name="qty" value="1">
                                        <button id="plus" type="button" class="btn btn-info" onclick="plusValue()">+</button>

                                        <input type="hidden" name="product" value="${product.productId}">
                                        <input type="hidden" name="form" value="getdetail">
                                        <input type="submit" value="เพิ่มลงตะกร้า" class="btn btn-danger" style="width: 120px;">
                                        <c:choose>
                                            <c:when test="${fav!=null}">
                                                <a href="RemoveFav?productid=${product.productId}">
                                                    <img id = "whiteFav" src="extra-image/redfav.png" style="width: 35px;margin-right: 10px;" onclick="favourite()">
                                                </a>
                                            </c:when>
                                            <c:otherwise>
                                                <a href="Favourite?productid=${product.productId}">
                                                    <img id = "whiteFav" src="extra-image/whitefav.png" style="width: 35px;margin-right: 10px;" onclick="favourite()">
                                                </a>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </form>
                            </div>
                    </div>
                </div>
            </div> 
        </div>
    </div>
</body>
</html>
