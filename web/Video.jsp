<%-- 
    Document   : Video.jsp
    Created on : Nov 23, 2018, 9:45:04 AM
    Author     : tinypt
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Video</title>
    </head>
    <body>
        <jsp:include page="include/header.jsp"/>
        <div class="container-fluid text-center p-4" style="background-color: #fafafa;">
            <h1>วีดีโอ</h1> 
        </div>
        <c:choose>
            <c:when test="${video!=null}">
                <div class="row m-3">
                    <c:forEach items="${video}" var="map">
                        <div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
                            <div class="card p-3 text-center box-part bg-light border-0 mb-3" style="width: 42rem; height: 42rem;" >
                                <p class="text-danger">ดูได้ถึง ${map.value}</p>
                                <a href="Getdetail?product=${map.key.productId}" class="h4 text-primary"><h1>${map.key.productName}</h1></a>
                                <div class="row pt-5">
                                    <div class="col"></div>
                                    <div class="col"> 
                                        <iframe width="420" height="345" src="${map.key.productVideo}">
                                        </iframe>
                                    </div>
                                    <div class="col"></div>
                                </div>
                                <br/>

                            </div>
                        </div> 
                    </c:forEach>
                </div>
            </c:when>                                                                                                                                                                                                                                                                                                                                              
            <c:otherwise>
                <p class="text-center mt-3">ไม่มีวีดีโอ</p> 
            </c:otherwise>
        </c:choose>

    </body>
</html>
