<%-- 
    Document   : getdetail
    Created on : Nov 6, 2018, 9:26:22 PM
    Author     : Hong
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <title>Detail Page</title>
        <link href="https://fonts.googleapis.com/css?family=Prompt" rel="stylesheet">

        <style>
            .qty .count {
                color: #000;
                display: inline-block;
                vertical-align: top;
                font-size: 25px;
                font-weight: 700;
                line-height: 30px;
                padding: 0 2px
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
        </script>

    </head>
    <body>
        <jsp:include page="include/header.jsp"/>
    <center> <br><br>
        <h1>ชื่อขนม : ${product.productName}</h1><br>
        <img src="image/${product.productId}.jpg" style="width: 600px"><br><br>
        <div>
            รายละเอียด : ${product.productDetail} <br>
            ราคา : ${product.productPrice} บาท 
            <form action="AddItemtoCart" method="post">
                <div class="qty mt-5">
                    <button id="minus" type="button" class="btn btn-default" onclick="minusValue()">-</button>
                    <input id="quantity" type="number" class="count" name="qty" value="1">
                    <button id="plus" type="button" class="btn btn-info" onclick="plusValue()">+</button>

                    <input type="hidden" name="product" value="${product.productId}">
                    <input type="submit" value="เพิ่มสินค้าลงตะกร้า" class="btn btn-danger" style="width: 150px;">
                </div>
            </form>
            <br><br>
        </div> 
    </div>
</center>
</body>
</html>
