<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>内部错误</title>
		<link rel="icon" href="/static/assets/img/favicon.ico">
		<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
		<link rel="stylesheet" href="/static/assets/css/bootstrap.css" />
		<link rel="stylesheet" href="/static/assets/css/style.css" />
	</head>
	<body>
		<div class="loss-page2">
			<p>亲，服务器产生了内部错误，请稍后再试</p>
		</div>
		<script type="text/javascript" src="/static/assets/js/jquery-1.11.1-min.js"></script>
		<script type="text/javascript">
			$(document).ready(function() {
				$(".loss-page2").css("height", $(window).height() + "px");
			})
			$(window).resize(function() {
				$(".loss-page2").css("height", $(window).height() + "px");
			})
		</script>
	</body>

</html>