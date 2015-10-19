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
		<h1>云行政</h1>

		
		<form class="form-horizontal" role="form" id="login-form">

			<div class="form-group">
				<div class="col-lg-12">
					<input type="text" id="mobile" name="mobile" placeholder="手机号" class="form-control">
				</div>

			</div>
			<div class="form-group">
				<div class="col-lg-12">
                <div class="row">
					<div class="col-md-7">
						<input type="text" id="sms_token" name="sms_token" placeholder="手机验证码" class="form-control" style="width: 100%">
					</div>
					
					<div class="col-md-3">
						<button class="btn btn-primary" id="btn_sms_token" type="button">
						<i class="icon-refresh"></i>
							获取验证码
						</button>
					</div>
				</div>
                </div>
			</div>
			
			<div class="form-group">
				<div class="col-lg-12">
					<button class="btn btn-danger" type="submit" style="width:120px">登录</button>
				</div>
			</div>
		</form>
		
		

		</section>
		
		

		<!-- Footer -->
		<footer id="footer">
		<ul class="copyright">
			<li>&copy; 美家生活科技有限公司</li>
			<li><a href="register">企业注册</a></li>
		</ul>
		</footer>

	</div>

	<!-- js placed at the end of the document so the pages load faster -->
	<!--common script for all pages-->
	<%@ include file="../shared/importJs.jsp"%>

	<!--script for this page-->
	<script src="<c:url value='/assets/jquery-validation/dist/jquery.validate.min.js'/>" type="text/javascript"></script>

	<script src="<c:url value='/js/xcloud/common/validate-methods.js'/>" type="text/javascript"></script>
	<script src="<c:url value='/js/countdown.js'/>" type="text/javascript"></script>
	<script src="<c:url value='/js/xcloud/home/login.js'/>" type="text/javascript"></script>
</body>
</html>
