<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>客户端授权</title>
		<link rel="icon" href="/static/assets/img/favicon.ico">
		<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
		<link rel="stylesheet" href="/static/assets/css/bootstrap.css" />
		<link rel="stylesheet" href="/static/assets/css/style.css" />
	</head>
	<body>
		<form id="confirmationForm" name="confirmationForm" action="/oauth/authorize" method="post">
		<input name="user_oauth_approval" value="true" type="hidden" />
		<#if token?? >
			<input type="hidden" name="${token['parameterName']}" value="${token['token']}"/>
		</#if>
		<#list authorizationRequest.scope as sc>
			<input type="hidden" name="scope.${sc}" value="true"/>
		</#list>
		
		<div class="authorization-page">
			<div class="inner">
				<div class="header">
					<#if clientImgSrc ??>
						<img class="logo" src="${clientImgSrc}">
					<#else>
						<img class="logo" src="/static/assets/img/sq-logo.png">
					</#if>
					<img class="check" src="/static/assets/img/check.png">
					<img class="logo"  src="/static/assets/img/zf-logo.png">
					<p>客户端${authorizationRequest.clientId}请求授权</p>
				</div>
				<div class="content">
					<ul>
						<li>
							<div class="col-lg-2 col-xs-2 p-0">
								<#if clientImgSrc ??>
									<img src="${clientImgSrc}">
								<#else>
									<img src="/static/assets/img/sq.png">
								</#if>
							</div>
							<div class="col-lg-10 col-xs-10">
								<p class="lg-text">客户端 ${authorizationRequest.clientId} 请求授权</span></p>
								<p class="sm-text">该应用将获得以下信息</p>
							</div>
						</li>
						<li>
							<div class="col-lg-2 col-xs-2 p-0">
								<#if userImgSrc ??>
									<img src="${userImgSrc}">
								<#else>
									<img src="/static/assets/img/user-lg.png">
								</#if>
							</div>
							<div class="col-lg-9 col-xs-9">
								<p class="lg-text">用户个人信息</p>
								<ul class="sm-text">
									<#list exposeAttrNameList as name>
										<li>${name} (read-only)</li>
									</#list>
								</ul>
							</div>
							<#-- 
							<div class="col-lg-1 col-xs-1 p-0"><img class="arrow-down" src="/static/assets/img/arrow-down.png"></div>
							 -->
						</li>
					</ul>
					<div class="btn-wrap">
					<button type="submit" class="btn btn-block btn-primary login-btn">授权</button>
					<p>授权后会重定向到:${authorizationRequest.redirectUri}</p>
				</div>
				</div>
			</div>
		</div>
		</form>
		<script type="text/javascript" src="/static/assets/js/jquery-1.11.1-min.js" ></script>
		<script type="text/javascript">
			$(".authorization-page .content ul>li .arrow-down").click(function(){
				$(this).toggleClass("active");
				$(".hide-text").toggle();
			});
		</script>
	</body>
</html>
