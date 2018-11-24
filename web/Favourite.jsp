<%-- 
    Document   : Favourite
    Created on : Nov 15, 2018, 10:19:14 PM
    Author     : GT62VR
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Favourite Page</title>
        <link href="https://fonts.googleapis.com/css?family=Prompt" rel="stylesheet">
        <style>
            body {
                font-family: 'Prompt', sans-serif;
            }
        </style>
    </head>
    <body>
        <jsp:include page="include/header.jsp"/>
        <div class="container-fluid text-center p-4" style="background-color: #fafafa;">
            <h1>รายการโปรด</h1> 
        </div>
        <c:choose>
            <c:when test="${favlist!=null}">
                <div class="row m-3">
                    <c:forEach items="${favlist}" var="favlist">
                        <div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
                            <div class="card p-3 text-center box-part bg-light border-0 mb-3" style="width: 42rem; height: 42rem;" >
                                <h1 class="pt-5">${favlist.productId.productName}</h1>  
                                <div class="row pt-3 pb-4">
                                    <div class="col"></div>
                                    <div class="col">
                                        <a href="Getdetail?product=${favlist.productId.productId}" class="h4 text-primary">ดูเพิ่มเติม ></a>
                                    </div>
                                    <div class="col">
                                        <a href="AddItemtoCart?product=${favlist.productId.productId}&qty=1&form=productType" class="h4 text-primary">ซื้อเลย ></a>
                                    </div>
                                    <div class="col"></div>
                                </div>
                                <div class="row pt-5">
                                    <div class="col"></div>
                                    <div class="col"> <img src="image/${favlist.productId.productId}.jpg" style="width: 400px ;height: 300px;" /></div>
                                    <div class="col"></div>
                                </div>
                                <br/>

                            </div>
                        </div> 
                    </c:forEach>
                </div>
            </c:when>
            <c:otherwise>
                <p class="text-center mt-3">ยังไม่มีรายการโปรด</p> 
            </c:otherwise>
        </c:choose>
    </body>
</html>
