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

	<div class="am-g">
		<div class="am-u-lg-6 am-u-md-8 am-u-sm-centered">

			<form:form method="post" modelAttribute="contentModel" id="company-reg-form" class="am-form">
				<form:hidden path="companyId"/>
				<div class="am-g am-margin-top">
					<div class="am-u-sm-4 am-u-md-2 am-text-right">手机号：</div>
					<div class="am-u-sm-8 am-u-md-4">
						<form:input path="userName" class="js-pattern-mobile" autocomplete="off" placeholder="手机号" required="required" />
					</div>
					<div class="am-hide-sm-only am-u-md-6"></div>
				</div>
				
				<div class="am-g am-margin-top">
					<div class="am-u-sm-4 am-u-md-2 am-text-right">验证码：</div>
					<div class="am-u-sm-8 am-u-md-4">
						<input type="text" id="sms_token" name="sms_token" class="js-ajax-validate" placeholder="输入验证码" required />
					</div>
					<div class="am-hide-sm-only am-u-md-6">&nbsp;
						<button class="am-btn am-btn-default" id="btn_sms_token">
								<i class="am-icon-spinner am-icon-spin"></i> 获取验证码
						</button>
					</div>
				</div>

				<div class="am-g am-margin-top">
					<div class="am-u-sm-4 am-u-md-2 am-text-right">密码: </div>
					<div class="am-u-sm-8 am-u-md-4">
						<form:password path="password" autocomplete="off" placeholder="密码" required="required" />
					</div>
					<div class="am-hide-sm-only am-u-md-6"></div>
				</div>

				<div class="am-g am-margin-top">
					<div class="am-u-sm-4 am-u-md-2 am-text-right">确认密码：</div>
					<div class="am-u-sm-8 am-u-md-4">
						<input type="password" id="confirmPassword" placeholder="请与上面输入的值一致"
							data-equal-to="#password" required />
					</div>
					<div class="am-u-sm-12 am-u-md-6"></div>
				</div>

				<div class="am-g am-margin-top">
					<div class="am-u-sm-4 am-u-md-2 am-text-right">公司全称：</div>
					<div class="am-u-sm-8 am-u-md-4">
						<form:input path="companyName" autocomplete="off" placeholder="输入公司全称" maxLength="64" required="required" />
					</div>
					<div class="am-u-sm-12 am-u-md-6"></div>
				</div>
				
				<div class="am-g am-margin-top">
					<div class="am-u-sm-4 am-u-md-2 am-text-right">公司简称：</div>
					<div class="am-u-sm-8 am-u-md-4">
						<form:input path="shortName" autocomplete="off" placeholder="输入公司简称" maxLength="32" required="required" />
					</div>
					<div class="am-u-sm-12 am-u-md-6"></div>
				</div>
				
				<div class="am-g am-margin-top">
					<div class="am-u-sm-4 am-u-md-2 am-text-right"></div>
					<div class="am-u-sm-8 am-u-md-4">
						<div class="am-center"><form:errors path="userName" class="am-alert am-alert-danger center"></form:errors></div>
					</div>
					<div class="am-u-sm-12 am-u-md-6"></div>					
						
					
				</div>
				
				<div class="am-g am-margin-top">
					<div class="am-u-sm-4 am-u-md-2 am-text-right"></div>
					<div class="am-u-sm-8 am-u-md-4">
						<button class="am-btn am-btn-secondary" id="reg-submit" type="button">提交</button>
					</div>
					<div class="am-u-sm-12 am-u-md-6"></div>					
						
					
				</div>
				
				

			</form:form>





		</div>
	</div>

	<!-- js placed at the end of the document so the pages load faster -->
	<!--common script for all pages-->
	<%@ include file="../shared/importJs.jsp"%>

	<!--script for this page-->

	<script src="<c:url value='/assets/js/countdown.js'/>" type="text/javascript"></script>
	<script src="<c:url value='/assets/js/xcloud/common/validate-methods.js'/>" type="text/javascript"></script>
	<script src="<c:url value='/assets/js/xcloud/home/register.js'/>" type="text/javascript"></script>
</body>
</html>
