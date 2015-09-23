<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ include file="../shared/taglib.jsp"%>

<html>
<head>

<!--common css for all pages-->
<%@ include file="../shared/importCss.jsp"%>

<!--css for this page-->
<link href="<c:url value='/css/login.css'/>" rel="stylesheet" type="text/css">
</head>

<body>

	<!-- Wrapper -->
	<div id="wrapper">

		<!-- Main -->
		<section id="main"> <header> <span class="avatar"><img src="img/avatar.jpg" alt="" /></span>
		<h1>企业登录</h1>

		
		<form class="form-horizontal" role="form">
			<div class="form-group">
				<div class="col-lg-12">
					<input type="text" placeholder="用户名/手机号" class="form-control">
				</div>

			</div>
			<div class="form-group">
				<div class="col-lg-12">
					<input type="password" placeholder="密码" class="form-control">
				</div>
			</div>
			<div class="form-group">
				<div class="col-lg-12">
					<button class="btn btn-danger" type="button" style="width:120px">登录</button>
				</div>
			</div>
		</form>
		
		

		</section>
		
		

		<!-- Footer -->
		<footer id="footer">
		<ul class="copyright">
			<li>&copy; 美家生活科技有限公司</li>
			<li><a href="#">企业注册</a></li>
		</ul>
		</footer>

	</div>

	<!-- js placed at the end of the document so the pages load faster -->
	<!--common script for all pages-->
	<%@ include file="../shared/importJs.jsp"%>

	<!--script for this page-->


</body>
</html>
