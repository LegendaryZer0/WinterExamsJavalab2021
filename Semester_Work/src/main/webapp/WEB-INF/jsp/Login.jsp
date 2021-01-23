<%--
  Created by IntelliJ IDEA.
  User: xsolla
  Date: 19.10.2020
  Time: 0:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="mainTag" tagdir="/WEB-INF/tags" %>
<html lang="en">
<head>
    <title>Login Page</title>
    <!--Made with love by Mutiullah Samim -->
    <mainTag:headScripts/>
    <!--Bootsrap 4 CDN-->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">

    <!--Fontawesome CDN-->
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">


    <style>
        <mainTag:LoginCss/>
    </style>



    <mainTag:bootstraptema/>

</head>
<body>
<div class="container">
    <div class="d-flex justify-content-center h-100">
        <div class="card">
            <div class="card-header">
                <h3>Sign In</h3>
                <div class="d-flex justify-content-end social_icon">
                    <span><i href="${pageContext.request.contextPath}/assets/comingSoonPage/index.html"  class="fab fa-facebook-square"></i></span>
                    <span><i href="${pageContext.request.contextPath}/assets/comingSoonPage/index.html" class="fab fa-google-plus-square"></i></span>
                    <span><i href="${pageContext.request.contextPath}/assets/comingSoonPage/index.html" class="fab fa-twitter-square"></i></span>
                </div>
            </div>
            <div class="card-body">
                <form  action='register' method="POST" onsubmit="return false;">
                    <div  class="input-group form-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text"><i class="fas fa-user"></i></span>
                        </div>
                        <input type="text" class="form-control" placeholder="email" name="login" id="login">

                    </div>
                    <div class="input-group form-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text"><i class="fas fa-key"></i></span>
                        </div>

                        <input type="password" class="form-control" placeholder="password" name="password" id="password">
                    </div>
                    <div class="row">
                        <a id ="x"></a>
                    </div>
                    <div class="row align-items-center remember">
                        <input type="checkbox" id="chex" name="chex" value="true">Remember Me
                    </div>
                    <div class="form-group">
                        <input id="submit" type="submit" value="Login" class="btn float-right login_btn">
                    </div>
                </form>
            </div>
            <div class="card-footer">
                <div class="d-flex justify-content-center links" style="color: red">
                    Don't have an account?<a href="registration">Sign Up</a>
                </div>
                <div class="d-flex justify-content-center">
                    <a href="${pageContext.request.contextPath}/assets/comingSoonPage/index.html">Forgot your password?</a>
                </div>
            </div>
        </div>
    </div>
</div>
<mainTag:bodyScripts/>
<script src="${pageContext.request.contextPath}/js/login.js"></script>
</body>
</html>
