<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ include file="../shared/taglib.jsp"%>

<html>
<head>

<!--common css for all pages-->
<%@ include file="../shared/importCss.jsp"%>

<!--css for this page-->
<link href="<c:url value='/css/xcloud/login.css'/>" rel="stylesheet" />
<style>
div.center, p.center, img.center {
    text-align: left;
}
</style>
</head>

<body class="login-page">
	

	<div class="ch-container">
		<div class="row">

			<div class="row">
				<div class="col-md-12 login-header" style="text-align:center;">
					<h2>注册企业账号</h2>
				</div>
				<!--/span-->
			</div>
			<!--/row-->

			<div class="row">
				<div class="well col-md-5 center login-box">

					<form:form modelAttribute="contentModel" class="form-horizontal" method="post" id="register-form">
							<form:hidden path="companyId"/>
							<div class="form-group">
								<div class="col-md-12">
									<div class="col-md-9">
										<div class="input-group input-group-md">
											<span class="input-group-addon"><i class="glyphicon glyphicon-earphone red"></i></span>
											<form:input path="userName" class="form-control placeholder-no-fix" autocomplete="off" placeholder="手机号/邮箱" />
										</div>
									</div>
									<div class="col-md-3"></div>
								</div>
							</div>
							


							<div class="form-group">
								
								<div class="col-md-12">
									
										<div class="col-md-6">
											<div class="input-group input-group-md">
												<span class="input-group-addon"><i class="glyphicon glyphicon-chevron-right red"></i></span>
												<input type="text" id="imgToken" name="imgToken" placeholder="图片验证" class="form-control" maxLength="4">
											</div>
										</div>

										<div class="col-md-3"  style="vertical-align：Middle">
											<img id="kaptchaImage" width="80px" src="/xcloud/captcha" maxlength="4"  />
										</div>
										
									
								</div>
							</div>

							<div class="form-group">
								<div class="col-md-12">

										<div class="col-md-6">
											<div class="input-group input-group-md">
												<span class="input-group-addon"><i class="glyphicon glyphicon-circle-arrow-right red"></i></span>
				
												<input type="text" id="sendToken" name="sendToken" placeholder="验证码" class="form-control" style="width: 100%">
											</div>
										</div>
										
										<div class="col-md-3">
											<button class="btn btn-small btn-primary" id="btn_sms_token" type="button" >获取验证码
											</button>
										</div>

								</div>
							</div>
							
							<div class="form-group">
								<div class="col-md-12">
									<div class="col-md-9">
										<div class="input-group input-group-md">
											<span class="input-group-addon"><i class="glyphicon glyphicon-lock red"></i></span>
											<form:password path="password" class="form-control" placeholder="输入密码" maxLength="12"/>
										</div>
									</div>
									<div class="col-md-3"></div>
								</div>
							</div>
							
							<div class="form-group">
								<div class="col-md-12">
									<div class="col-md-9">
										<div class="input-group input-group-md">
											<span class="input-group-addon"><i class="glyphicon glyphicon-arrow-up red"></i></span>
											<input type="password" id ="confirmPassword" name="confirmPassword" class="form-control" placeholder="再次输入密码" maxLength="12">
										</div>
									</div>
									<div class="col-md-3"></div>
								</div>
							</div>
							
							<div class="form-group">
								<div class="col-md-12">
									<div class="col-md-9">
										<div class="input-group input-group-md">
											<span class="input-group-addon"><i class="glyphicon glyphicon-book red"></i></span>
											<form:input path="companyName" class="form-control placeholder-no-fix" autocomplete="off" placeholder="输入公司名称" maxLength="32" />
										</div>
									</div>
									<div class="col-md-3"></div>
								</div>
							</div>
							
							<div class="form-group">
								<div class="col-md-12">
									<div class="col-md-9">
										<div class="input-group input-group-md">
											<span class="input-group-addon"><i class="glyphicon glyphicon-align-justify red"></i></span>
											<form:input path="shortName" class="form-control placeholder-no-fix" autocomplete="off" placeholder="输入公司简称" maxLength="20" />
										</div>
									</div>
									<div class="col-md-3"></div>
								</div>
							</div>
							
							<div class="form-group">
								<div class="col-md-12">
									<div class="col-md-9">
										<label>注册即表示您同意</label></label><a href="#"><<用户使用协议>></a>
									</div>
									<div class="col-md-3"></div>
								</div>
							</div>
							
							
							<div class="form-group">
								<div class="col-md-12" style="text-align:center;">
									<div class="col-md-9" >
										<button class="btn btn-primary" type="submit">
												<i class="icon-icon-forward"></i> 立即注册
											</button>
									</div>
									<div class="col-md-3"></div>
								</div>
							</div>
							



					</form:form>
				</div>
				<!--/span-->
			</div>
			<!--/row-->
		</div>
		<!--/fluid-row-->

	</div>
	<!--/.fluid-container-->
	<!-- js placed at the end of the document so the pages load faster -->
	<!--common script for all pages-->
	<%@ include file="../shared/importJs.jsp"%>

	<!--script for this page-->
	<script src="<c:url value='/js/vendor/jquery.validate.min.js'/>" type="text/javascript"></script>
	<script src="<c:url value='/js/xcloud/common/validate-methods.js'/>" type="text/javascript"></script>
	<script src="<c:url value='/js/vendor/countdown.js'/>" type="text/javascript"></script>
	<script src="<c:url value='/js/xcloud/home/register.js'/>" type="text/javascript"></script>
	
	<script>
		$('#kaptchaImage').click(function () {//生成验证码    
		    $(this).hide().attr('src', '/xcloud/captcha?' + Math.floor(Math.random()*100) ).fadeIn();    
		});
	
	</script>
</body>
</html>
