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
        <style>
            /* Make the image fully responsive */
            .carousel-inner img {
                width: 100%;
                height: 100%;
            }

            /*loader*/
            .loader{
                width: 150px;
                height: 150px;
                margin: 40px auto;
                transform: rotate(-45deg);
                font-size: 0;
                line-height: 0;
                animation: rotate-loader 5s infinite;
                padding: 25px;
                border: 1px solid #cf303d;
            }
            .loader .loader-inner{
                position: relative;
                display: inline-block;
                width: 50%;
                height: 50%;
            }
            .loader .loading{
                position: absolute;
                background: #cf303d;
            }
            .loader .one{
                width: 100%;
                bottom: 0;
                height: 0;
                animation: loading-one 1s infinite;
            }
            .loader .two{
                width: 0;
                height: 100%;
                left: 0;
                animation: loading-two 1s infinite;
                animation-delay: 0.25s;
            }
            .loader .three{
                width: 0;
                height: 100%;
                right: 0;
                animation: loading-two 1s infinite;
                animation-delay: 0.75s;
            }
            .loader .four{
                width: 100%;
                top: 0;
                height: 0;
                animation: loading-one 1s infinite;
                animation-delay: 0.5s;
            }
            @keyframes loading-one {
                0% {
                    height: 0;
                    opacity: 1;
                }
                12.5% {
                    height: 100%;
                    opacity: 1;
                }
                50% {
                    opacity: 1;
                }
                100% {
                    height: 100%;
                    opacity: 0;
                }
            }
            @keyframes loading-two {
                0% {
                    width: 0;
                    opacity: 1;
                }
                12.5% {
                    width: 100%;
                    opacity: 1;
                }
                50% {
                    opacity: 1;
                }
                100% {
                    width: 100%;
                    opacity: 0;
                }
            }
            @keyframes rotate-loader {
                0% {
                    transform: rotate(-45deg);
                }
                20% {
                    transform: rotate(-45deg);
                }
                25% {
                    transform: rotate(-135deg);
                }
                45% {
                    transform: rotate(-135deg);
                }
                50% {
                    transform: rotate(-225deg);
                }
                70% {
                    transform: rotate(-225deg);
                }
                75% {
                    transform: rotate(-315deg);
                }
                95% {
                    transform: rotate(-315deg);
                }
                100% {
                    transform: rotate(-405deg);
                }
            }
        </style>
        <script>
            window.onload = function () {
                document.getElementById("loading").style.display = "none";
            };
        </script>
    </head>
    <body>
        <jsp:include page="include/header.jsp"/>
        <c:if test="${upcom!=null}">
            <div class="alert alert-success" role="alert" style="margin-bottom: 0px;">
                ${upcom}
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
        </c:if>
        <c:if test="${update!=null}">
            <div class="alert alert-success" role="alert" style="margin-bottom: 0px;">
                ${update}
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
        </c:if>
        <c:choose>
            <c:when test="${activate!=null}">
                <div class="alert alert-warning" role="alert" style="margin-bottom: 0px;">
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
                <c:if test="${actinmail!=null}">
                    <div class="alert alert-warning" role="alert" style="margin-bottom: 0px;">
                        ${actinmail}
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                </c:if>
            </c:otherwise>
        </c:choose>
        <c:if test="${type!=null}">
            <div class="alert alert-secondary" role="alert" style="margin-bottom: 0px;">
                ${type}
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
        </c:if>
        <c:if test="${buycom!=null}">
            <div class="alert alert-success" role="alert" style="margin-bottom: 0px;">
                ${buycom}
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>

        </c:if>
        <div class="container" id="loading">
            <div class="row">
                <div class="col-md-12">
                    <div class="loader">
                        <div class="loader-inner">
                            <div class="loading one"></div>
                        </div>
                        <div class="loader-inner">
                            <div class="loading two"></div>
                        </div>
                        <div class="loader-inner">
                            <div class="loading three"></div>
                        </div>
                        <div class="loader-inner">
                            <div class="loading four"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div id="demo" class="carousel slide" data-ride="carousel" id="page">

            <!--Indicators--> 
            <ul class="carousel-indicators">
                <li data-target="#demo" data-slide-to="0" class="active"></li>
                <li data-target="#demo" data-slide-to="1"></li>
                <li data-target="#demo" data-slide-to="2"></li>
                <li data-target="#demo" data-slide-to="3"></li>
                <li data-target="#demo" data-slide-to="4"></li>
                <li data-target="#demo" data-slide-to="5"></li>
            </ul>

            <!--The slideshow--> 
            <div class="carousel-inner" style="height: 800px;">
                <div class="carousel-item active">
                    <img src="image/N.jpg"  width="1000" height="400">
                </div>
                <div class="carousel-item">
                    <a href="Getproduct?type=1"> <img src="image/Q.jpg"  width="1000" height="400"></a>
                    <div class="carousel-caption d-none d-md-block">
                        <h3># เรื่องขนมไทย ไว้ใจ มณโฑ</h3>
                    </div>
                </div>
                <div class="carousel-item">
                    <a href="Getproduct?type=2"><img src="image/P.jpg"width="1000" height="400"></a>
                    <div class="carousel-caption d-none d-md-block">
                        <h3># เรื่องขนมไทย ไว้ใจ มณโฑ</h3>
                    </div>
                </div>
                <div class="carousel-item">
                    <a href="Getproduct?type=3"><img src="image/K.jpg"  width="1000" height="400"></a>
                    <div class="carousel-caption d-none d-md-block">
                        <h3># เรื่องขนมไทย ไว้ใจ มณโฑ</h3>
                    </div>
                </div>
                <div class="carousel-item">
                    <a href="Getproduct?type=4"> <img src="image/J.png"  width="1000" height="400"></a>
                    <div class="carousel-caption d-none d-md-block">
                        <h3># เรื่องขนมไทย ไว้ใจ มณโฑ</h3>
                    </div>
                </div>
                <div class="carousel-item">
                    <a href="Getproduct?type=5"><img src="image/O.jpg" width="1000" height="400"></a>
                    <div class="carousel-caption d-none d-md-block">
                        <h3># เรื่องขนมไทย ไว้ใจ มณโฑ</h3>
                    </div>
                </div>
            </div>

            <!--Left and right controls--> 
            <a class="carousel-control-prev" href="#demo" data-slide="prev">
                <span class="carousel-control-prev-icon"></span>
            </a>
            <a class="carousel-control-next" href="#demo" data-slide="next">
                <span class="carousel-control-next-icon"></span>
            </a>
        </div>

    </body>
</html>
