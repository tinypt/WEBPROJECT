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
                color: #333;
            }
            table th {
                text-align: center;
            }
        </style>
        <title>Search Page</title>
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

        <div class="container p-4 pb-0 mb-0">
            <h2>ค้นหา : ${nameSearch}</h2>
            <p>พบรายการสินค้า ${qty} ผลลัพธ์</p>
            <p>${msg}</p> 
        </div><hr/>
        <div class="container p-4">
            
           <c:if test="${qty>0}"> 
               <c:forEach items="${prod}" var="prod" varStatus="s">
                   <div class="row" style="height: 200px;">
                <div class="col-3"><img src="image/${prod.productId}.jpg" width="200"></div>
                <div class="col-7 text-left">
                     <a href="Getdetail?product=${prod.productId}" class="h4 text-primary"><h4>${prod.productName}</h4></a>
                    <p>${prod.productDetail}</p>
                    <p>ประเภท ${prod.type} | ราคา ${prod.productPrice} บาท </p>
                </div>
                <div class="col-2">
                    <a href="AddItemtoCart?qty=1&product=${prod.productId}&form=search&name=${nameSearch}">
                            <button type="button" class="btn btn-outline-primary btn-sm mt-5">เพิ่มลงตะกร้า</button>
                        </a>
                </div>
            </div></c:forEach>
           </c:if>
           
        </div>
    </body>
</html>
