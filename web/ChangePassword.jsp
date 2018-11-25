<%-- 
    Document   : ChangePassword
    Created on : Nov 25, 2018, 4:07:12 PM
    Author     : GT62VR
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Change password Page</title>
        <script>
            function checkpass() {
                if (document.getElementById("p1").value == document.getElementById("p2").value) {
                    document.getElementById("subbtn").disabled = false;
                }else {
                    document.getElementById("subbtn").disabled = true;
                }
            }
        </script>
    </head>
    <body style="background-color: #f2f2f2;">
        <jsp:include page="include/header.jsp"/>
        <div class="container">
            <div class="row text-center">
                <div class="col"></div>
                <div class="col">
                    <div class="card mt-5" style="width: 61rem; height: 30rem; box-shadow: 0 2px 2px 0 #b6b6b6;">
                        <div class="card-header bg-white text-left">
                            <h3>Reset Password</h3>
                        </div>
                        <div class="col-5 p-5 m-4">

                            <h5 class="text-left">รีเซ็ตรหัสผ่าน</h5>

                            <form method="post" action="ChangePassword">
                                <div class="row">
                                    <input type="hidden" name="accid" value="${acc.accountId}">
                                    <input id="p1" type="password" class="form-control mb-3"  name="password" placeholder="New Password*" onchange="checkpass()"><br>
                                    <input id="p2" name="confirmpassword" type="password" class="form-control mb-3" onchange="checkpass()" placeholder="Confirm New Password*"><br>
                                </div>
                                <div class="row"> 
                                    <div class="col"></div>
                                    <div class="col"></div>
                                    <div class="col"></div>
                                    <div class="col"></div>
                                    <div class="col">
                                        <input id="subbtn" type="submit" value="submit" class="btn btn-primary" disabled="">
                                    </div>  
                                </div>
                            </form>

                        </div>

                        <div class="col text-left p-5 ml-5">

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
