<#ftl encoding="UTF-8"/>
<#import "mcrs/bodyScripts.ftl" as b>

<#import "mcrs/headScripts.ftl" as h>
<#import "mcrs/LoginCss.ftl" as c>

<html lang="eng">
<head>
    <title>Login Page</title>
    <meta charset="UTF-8">
    <!--Made with love by Mutiullah Samim -->

    <!--Bootsrap 4 CDN-->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">

    <!--Fontawesome CDN-->
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">


    <style>
        <@c.logCss></@c.logCss>
    </style>


    <@h.headScript></@h.headScript>


</head>
<body>


<div class="container">
    <div class="d-flex justify-content-center h-100">
        <div class="card">
            <div class="card-header">
                <h3>Sign In</h3>
                <div class="d-flex justify-content-end social_icon">
                    <span><i   class="fab fa-facebook-square"></i></span>
                    <span><i class="fab fa-google-plus-square"></i></span>
                    <span><i class="fab fa-twitter-square"></i></span>
                </div>
            </div>
            <div class="card-body">
                <form  action='someaction' method="POST" onsubmit="return false;">
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
                    <a >Forgot your password?</a>
                </div>
            </div>
        </div>
    </div>
</div>
<input id ='csrf-token' name="_csrf_token" value="${_csrf_token}" />
<@b.bodyScript></@b.bodyScript>

<script src="${reqObj.contextPath}/js/login.js"></script>
</body>
</html>
