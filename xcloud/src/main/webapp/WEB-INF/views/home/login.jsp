<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../shared/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>

<!--common css for all pages-->
<%@ include file="../shared/importCss.jsp"%>

<!--css for this page-->
<style>
.header {
	text-align: center;
}

.header h1 {
	font-size: 200%;
	color: #333;
	margin-top: 30px;
}

.header p {
	font-size: 14px;
}
</style>
</head>

<body>
	<div class="header">
		<div class="am-g">
			<h1>云行政</h1>
			<p>
				 一句话描述云行政
			</p>
		</div>
		<hr />
	</div>
	<div class="am-g">
		<div class="am-u-lg-6 am-u-md-8 am-u-sm-centered">
			<h3>登录</h3>
			<hr>
			<div class="am-btn-group">
				<a href="#" class="am-btn am-btn-secondary am-btn-sm"><i class="am-icon-github am-icon-sm"></i> 立即注册</a> 
				<a href="#" class="am-btn am-btn-success am-btn-sm"><i class="am-icon-google-plus-square am-icon-sm"></i> 加入公司</a>
			</div>
			<br> <br>

			<form:form method="post" modelAttribute="contentModel" id="login-form" class="am-form" >
				<label for="email">用户名:</label> 
					<form:input path="username" class="js-pattern-mobile"  autocomplete="off" placeholder="用户名" required="required"/> 
					
				<br> 
				<label for="password">密码:</label> <form:password path="password"  autocomplete="off" placeholder="密码"  required="required"/> 
				<br> 

				<div class="am-center"><form:errors path="username" class="am-alert am-alert-danger center"></form:errors></div>
				<br />
				
				<div class="am-cf">
					<input type="button" id="login-btn" value="登 录" class="am-btn am-btn-primary am-btn-sm am-fl"> 
					<input type="button" value="忘记密码? " class="am-btn am-btn-default am-btn-sm am-fr">
				</div>
			</form:form>
			<hr>
			<p>© 2014 AllMobilize, Inc. Licensed under MIT license.</p>
		</div>
	</div>

	<!-- js placed at the end of the document so the pages load faster -->
	<!--common script for all pages-->
	<%@ include file="../shared/importJs.jsp"%>

	<!--script for this page-->
	<script src="<c:url value='/assets/js/xcloud/home/login.js'/>" type="text/javascript"></script>
</body>
</html>
