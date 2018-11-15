<%-- 
    Document   : montho
    Created on : Oct 31, 2018, 11:19:06 PM
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
            }
        </style>
        <title>มณโฑขนมหวาน</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>
        <style>
            /* Make the image fully responsive */
            .carousel-inner img {
                width: 100%;
                height: 100%;
            }
        </style>

    </head>
    <body>
        <jsp:include page="include/header.jsp"/>

        <div id="demo" class="carousel slide" data-ride="carousel">

            <!-- Indicators -->
            <ul class="carousel-indicators">
                <li data-target="#demo" data-slide-to="0" class="active"></li>
                <li data-target="#demo" data-slide-to="1"></li>
                <li data-target="#demo" data-slide-to="2"></li>
                <li data-target="#demo" data-slide-to="3"></li>
                <li data-target="#demo" data-slide-to="4"></li>
                <li data-target="#demo" data-slide-to="5"></li>
            </ul>

            <!-- The slideshow -->
            <div class="carousel-inner" style="height: 800px;">
                <div class="carousel-item active">
                    <img src="image/N.jpg"  width="1000" height="400">
                </div>
                <div class="carousel-item">
                    <img src="image/Q.jpg"  width="1000" height="400">
                    <div class="carousel-caption d-none d-md-block">
                        <h3># เรื่องขนมไทย ไว้ใจ มณโฑ</h3>
                    </div>
                </div>
                <div class="carousel-item">
                    <img src="image/P.jpg"width="1000" height="400">
                    <div class="carousel-caption d-none d-md-block">
                        <h3># เรื่องขนมไทย ไว้ใจ มณโฑ</h3>
                    </div>
                </div>
                <div class="carousel-item">
                    <img src="image/K.jpg"  width="1000" height="400">
                    <div class="carousel-caption d-none d-md-block">
                        <h3># เรื่องขนมไทย ไว้ใจ มณโฑ</h3>
                    </div>
                </div>
                <div class="carousel-item">
                    <img src="image/J.png"  width="1000" height="400">
                    <div class="carousel-caption d-none d-md-block">
                        <h3># เรื่องขนมไทย ไว้ใจ มณโฑ</h3>
                    </div>
                </div>
                <div class="carousel-item">
                    <img src="image/O.jpg" width="1000" height="400">
                    <div class="carousel-caption d-none d-md-block">
                        <h3># เรื่องขนมไทย ไว้ใจ มณโฑ</h3>
                    </div>
                </div>
            </div>

            <!-- Left and right controls -->
            <a class="carousel-control-prev" href="#demo" data-slide="prev">
                <span class="carousel-control-prev-icon"></span>
            </a>
            <a class="carousel-control-next" href="#demo" data-slide="next">
                <span class="carousel-control-next-icon"></span>
            </a>
        </div>


        <c:choose>
            <c:when test="${activate!=null}">
                <div class="alert alert-warning" role="alert">
                    ${activate}  
                    <c:if test="${link!=null}">
                        <a href="${link}">click to activate</a>
                    </c:if>
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </c:when>
            <c:otherwise>
                <c:if test="${link!=null}">
                    <div class="alert alert-warning" role="alert">

                        <a href="${link}">click to activate</a>

                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                </c:if>
            </c:otherwise>
        </c:choose>

        <c:if test="${update!=null}">
            <div class="alert alert-success" role="alert">

<<<<<<< HEAD
        ${type}
        ${update}
=======
                ${update}

                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
        </c:if>

        <c:if test="${type!=null}">
            <div class="alert alert-secondary" role="alert">

                ${type}

                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
        </c:if>

>>>>>>> b2c5c930138341d6dc2ec699c3d48b0e7dcb4d4e
    </body>
</html>
