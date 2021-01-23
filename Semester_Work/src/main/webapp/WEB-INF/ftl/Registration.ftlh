<#ftl encoding="UTF-8"/>
<#import "mcrs/bodyScripts.ftl" as b>

<#import "mcrs/headScripts.ftl" as h>

<html>
<head>
    <@h.headScript></@h.headScript>
    <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
    <link href="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/css/bootstrap-combined.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/js/bootstrap.min.js"></script>



</head>
<body>
<form class="form-horizontal" action='register' method="POST" onsubmit="return false;">
    <fieldset>
        <div id="legend">
            <legend class="">Register</legend>
        </div>
        <div class="control-group">
            <!-- E-mail -->
            <label class="control-label" for="login">E-mail</label>
            <div class="controls">
                <input type="text" id="login" name="login" placeholder="" class="input-xlarge">
                <p class="help-block">Please provide your E-mail</p>
            </div>
        </div>

        <div class="control-group">
            <!-- Password-->
            <label class="control-label" for="password">Password</label>
            <div class="controls">
                <input type="password" id="password" name="password" placeholder="" class="input-xlarge">
                <p class="help-block">Password should be at least 4 characters</p>
            </div>
        </div>

        <div class="control-group">
            <!-- Password -->
            <label class="control-label"  for="confirm">Password (Confirm)</label>
            <div class="controls">
                <input type="password" id="confirm" name="confirm" placeholder="" class="input-xlarge">

                <p class="help-block">Please confirm password</p>
            </div>
        </div>
        <h5 id="x">

        </h5>

        <div class="control-group">
            <!-- Button -->
            <div class="controls">
                <button id="submit-btn" class="btn btn-success">Register</button>
            </div>
        </div>
    </fieldset>
</form>

<@b.bodyScript></@b.bodyScript>
<script src="${reqObj.contextPath}/js/registr.js"></script>


</body>
</html>