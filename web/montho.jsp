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
    </head>
    <body>
        <jsp:include page="include/header.jsp"/>



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

    </body>
</html>
