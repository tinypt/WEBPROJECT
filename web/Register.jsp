<%-- 
    Document   : Register
    Created on : Oct 31, 2018, 11:52:46 PM
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
        <title>Register Page</title>
    </head>
    <body>
        <jsp:include page="include/header.jsp"/>
        <c:if test="${error!=null}">
            <div class="alert alert-danger" role="alert">
                ${error}
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
        </c:if>
        <div class="container mb-5">
            <div class="row text-center">
                <div class="col"></div>
                <div class="col">
                    <div class="card mt-5" style="width: 61rem; height: 60rem; box-shadow: 0 2px 2px 0 #b6b6b6;">
                        <div class="card-header bg-white text-left">
                            <h3>Register</h3>
                        </div>
                        <div class="col-6 p-5 m-4 card-body">
                            <div class="row">
                                <h5 class="text-left pb-2">สมัครสมาชิก</h5>
                            </div>

                            <form method="post" action="Register" autocomplete="off">
                                <div class="row">
                                    Username: <input type="text"  name="username" class="form-control mb-2" placeholder="Username*" required> <br>
                                    email: <input type="email"  name="email" class="form-control mb-2" placeholder="example@mail.com*" required> <br>
                                    Password: <input type="password" name="password" class="form-control mb-2 " placeholder="Password*" required><br>
                                    Name: <input type="text" name="name" class="form-control mb-2 " placeholder="Name*" required><br>
                                    Surname: <input type="text" name="surname" class="form-control mb-2 " placeholder="Surname*" required><br>
                                    Address: <textarea rows="4" cols="30" class="form-control mb-2 " name="address" placeholder="Address*" required></textarea><br>
                                    Telno: <input type="tel" name="telno" class="form-control mb-2 " placeholder="08xxxxxxxx*" pattern="[0-9]{10}" required><br>
                                </div>
                                <div class="row"> 
                                    <div class="col"></div>
                                    <div class="col"></div>
                                    <div class="col"></div>
                                    <div class="col">
                                        <input type="submit" value="submit" class="btn btn-primary">
                                    </div>  
                                </div>
                            </form>

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
