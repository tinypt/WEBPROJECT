<%-- 
    Document   : resetpassword
    Created on : Nov 25, 2018, 1:43:47 PM
    Author     : GT62VR
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Reset Password Page</title>
    </head>
    <body style="background-color: #f2f2f2;">
        <jsp:include page="include/header.jsp"/>
        <br>
        <div class="container">
            <div class="card mt-5" style="width: 61rem; height: 30rem; box-shadow: 0 2px 2px 0 #b6b6b6;">
                <div class="card-header bg-white text-left">
                    <h3>Forget Password</h3>
                </div>
                <div class="col-5 p-5 m-4">
                    <div class="row">
                        <div class="panel panel-default">
                            <div class="panel-body">
                                <div class="text-center">
                                    <h3><i class="fa fa-lock fa-4x"></i></h3>
                                    <h5>กรอก Email ของคุณเพื่อเปลี่ยนรหัสผ่าน</h5>
                                    <div class="panel-body">
                                        <form id="register-form" role="form" autocomplete="off" class="form" method="post" action="Resetpassword">
                                            <div class="form-group">
                                                <div class="input-group">
                                                    <span class="input-group-addon"><i class="glyphicon glyphicon-envelope color-blue"></i></span>
                                                    <input id="email" name="email" placeholder="email address" class="form-control mb-3"  type="email">
                                                </div>
                                            </div>
                                            <div class="form-group text-right">
                                                <input name="recover-submit" class="btn btn-primary" value="Reset Password" type="submit">
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>

                <div class="col text-left p-5 ml-5">

                </div>
                <div class="card-footer text-left" style="background: linear-gradient(white,#f2f2f2)">
                    <a href="Login.jsp" class="btn">Back</a> 
                </div>
            </div>
        </div>
    </body>
</html>
