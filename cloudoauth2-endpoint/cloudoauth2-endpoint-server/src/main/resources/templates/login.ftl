<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>正方认证登录</title>
		<link rel="icon" href="/static/assets/img/favicon.ico">
		<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
		<link rel="stylesheet" href="/static/assets/css/bootstrap.css" />
		<link rel="stylesheet" href="/static/assets/css/style.css" />
		<script type="text/javascript" src="/static/assets/js/jquery-1.11.1-min.js" ></script>
	</head>
	<body>
		<form action='/login' method='POST'>
		<#if token?? >
			<input type="hidden" name="${token['parameterName']}" value="${token['token']}"/>
		</#if>
		<div class="login-page">
			<div class="login-form">
				<div class="logo-inner"><img src="/static/assets/img/logo.png"></div>
				<#if error?? >
					<p class="error">${error}</p>
				</#if>
				<div class="form-group">
					<div class="input-group">
						<div class="input-group-addon"><img src="/static/assets/img/user.png"></div>
						<input type="text" id="username" name='username' class="form-control user-input" placeholder="用户名">
					</div>
				</div>
				<div class="form-group">
					<div class="input-group">
						<div class="input-group-addon"><img src="/static/assets/img/pwd.png"></div>
						<input type="password" id="password" name='password' class="form-control pwd-input" placeholder="密码">
						<!--
						<img class="toggleIcon" src="/static/assets/img/close.png">
						-->
					</div>
				</div>
				<#if enableKaptcha == true>
				<div class="form-group">
					<div class="col-lg-6 col-xs-6 p-0"><input type="text" name="kaptcha" class="form-control qrcode-input" placeholder="验证码"></div>
					<div class="col-lg-3 col-xs-3">
						<img id="kaptchaImg" src="" style="cursor:pointer"  width="140px" height="40px" onclick="javascript:refreshCode();">
					</div>
					<div class="col-lg-3 col-xs-3 p-0">
						<!--
						<span class="vague">看不清？</span>
						-->
					</div>
				</div>
				<script type="text/javascript">
					function refreshCode(){
						$("#kaptchaImg").attr("src",'${request.contextPath}/kaptcha?time=' + new Date().getTime());
					}
					$(function(){
							refreshCode();
					});
				</script>
				</#if>
				<!--
				<div class="form-group">
					<p class="forget-pwd">
					<img src="/static/assets/img/forget-pwd.png">
					<a href="#">忘记密码</a>
					</p>
				</div>
				-->
				<div class="form-group login-btn-wrap">
					<button type="submit" class="btn btn-block btn-primary login-btn">登 录</button>
				</div>
			</div>
		</div>
		</form>
		<script type="text/javascript">
			$(function(){
					$("#username").focus()
			});
			$(".login-page .login-form .toggleIcon").click(function(){
				$(this).toggleClass("active");
				if($(this).hasClass("active")){
					$(this).attr("src","/static/assets/img/open.png");
					$(this).prev("input").attr("type","text");
				}else{
					$(this).attr("src","/static/assets/img/close.png");
					$(this).prev("input").attr("type","password");
				}
			});
		</script>
	</body>
</html>