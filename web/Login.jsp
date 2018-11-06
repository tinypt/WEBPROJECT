<%-- 
    Document   : Login
    Created on : Oct 31, 2018, 11:38:47 PM
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

        <title>Login Page</title>
    </head>
    <body style="background-color: #f2f2f2;">

        <jsp:include page="include/header.jsp"/>
        <div class="container">
            <div class="row text-center">
                <div class="col"></div>
                <div class="col">
                    <div class="card mt-5" style="width: 61rem; height: 30rem; box-shadow: 0 2px 2px 0 #b6b6b6;">
                        <div class="card-header bg-white text-left">
                            <h3>Login</h3>
                        </div>
                        <div class="col-5 p-5 m-4">
                           
                            <h5 class="text-left">ลงชื่อเข้าใช้</h5>

                            <form method="post" action="Login">
                                <div class="row">
                                <input type="text" class="form-control mb-3"  name="username" placeholder="Username*"><br>
                                <input type="password" class="form-control mb-3"  name="password" placeholder="Password*"><br>
                                </div>
                                <div class="row"> 
                                    <div class="col" >
                                        <a href="Register">Register?</a>
                                    </div>
                                    <div class="col"></div>
                                    <div class="col"></div>
                                    <div class="col">
                                        <input type="submit" value="submit" class="btn btn-primary">
                                    </div>  
                                </div>
                            </form>
                             <p class="text-warning p-2 ">  
                                ${activate} 
                                ${actcom} 
                                ${Logfail} 
                            </p>
                           
                        </div>
                            
                        <div class="col text-left p-5 ml-5">
                            <c:if test="${link!=null}">
                                <a href="${link}">Click this to activate</a> <br>
                            </c:if>
                        </div>
                        <div class="card-footer text-left" style="background: linear-gradient(white,#f2f2f2)">
                            <a href="montho.jsp" class="btn">Back</a> 

                        </div>
                    </div>
                </div>
                <div class="col"></div>
            </div>
        </div>
    </body>
</html>
