<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="../shared/taglib.jsp"%>
<html>
<head>
<!--common css for all pages-->
<%@ include file="../shared/importCss.jsp"%>
<link href="<c:url value='/assets/css/xcloud.css'/>" rel="stylesheet" />
<link href="<c:url value='/assets/js/drag/drag.css'/>" rel="stylesheet" />
</head>
<body>
	<!--header start-->
	<%@ include file="../shared/pageSampleHeader.jsp"%>
	<!--header end-->
	<div class="am-g tr-yunpan">
		<div class="tr-yunpan-bg-container am-container am-container-collapse">
			<div data-am-widget="slider" class="tr-yunpan-bg-slider am-hide-sm-only am-slider am-slider-a1 am-no-layout"
				data-am-slider="{animation:'slide',directionNav:false,pauseOnHover: false}">
				<div style="overflow: hidden; position: relative;" class="am-viewport">
					<ul style="width: 800%; transition-duration: 0s; transform: translate3d(-1000px, 0px, 0px);" class="am-slides">
						<li style="width: 1000px; float: left; display: block;" aria-hidden="true" class="clone">
							<img draggable="false" src="<c:url value='/assets/img/netdisk-slider-p2.png'/>">
						</li>
						<li class="am-active-slide" style="width: 1000px; float: left; display: block;">
							<img draggable="false" src="<c:url value='/assets/img/netdisk-slider-p1.png'/>">
						</li>
						<li class="" style="width: 1000px; float: left; display: block;">
							<img draggable="false" src="<c:url value='/assets/img/netdisk-slider-p2.png'/>">
						</li>
						<li aria-hidden="true" class="clone" style="width: 1000px; float: left; display: block;">
							<img draggable="false" src="<c:url value='/assets/img/netdisk-slider-p1.png'/>">
						</li>
					</ul>
				</div>
				<ol class="am-control-nav am-control-paging">
					<li>
						<a class="am-active">1</a>
						<i></i>
					</li>
					<li>
						<a class="">2</a>
						<i></i>
					</li>
				</ol>
			</div>
		</div>
		<div class="tr-yunpan-container am-container am-container-collapse am-vertical-align">
			<div class="am-vertical-align-middle am-g am-g-collapse" style="width: 90%; margin-left: 9%;">
				<div
					class="tr-yunpan-login-container am-container am-animation-slide-right am-animation-delay-1 am-u-md-5 am-u-md-push-8 am-u-sm-10 am-u-sm-push-1">
					<div class="am-tabs am-margin " data-am-tabs>
						<ul class="am-tabs-nav am-nav am-nav-tabs">
							<li class="am-active">
								<a id="tab-login-pass" href="#tab-login-pass"></a>
							</li>
							<li>
								<a id="tab-login-sms" href="#tag-login-sms"></a>
							</li>
						</ul>
						<div class="am-tabs-bd">
							<!----------------- 密码登录 ------------------------------------>
							<div class="am-tab-panel am-fade am-in am-active" id="tab-login-pass">
								<form:form method="post" modelAttribute="contentModel" id="login-form" 
									class="am-form am-padding-xl am-padding-bottom-sm">
									<div class="am-g am-container">
										<h2>登录管理平台</h2>
										<form:errors path="username" class="am-alert am-alert-danger center"></form:errors>
									</div>
									<div class="am-g am-padding-sm">
										<div class="am-form-group am-form-icon">
											<span class="am-icon-user"></span>
											<form:input path="username" class="js-pattern-mobile am-form-field am-radius" placeholder="手机号"
												data-validation-message="手机号" required="required" />
										</div>
										<div class="am-form-group am-form-icon">
											<span class="am-icon-lock"></span>
											<form:password path="password" class="am-form-field am-radius" autocomplete="off" minlength="6"
												placeholder="密码" data-validation-message="请输入密码" required="required" />
										</div>
										<div class="am-form-group">
											<div id="drag" style="width: 239px;"></div>
										</div>
										<button type="button" id="login-btn" class="am-btn am-btn-danger am-btn-block am-radius">登 录</button>
										<div class="am-form-group am-margin-top-sm am-text-sm">
											<div class="am-fl">
												<a href="#" onclick="javascript:loginSwitch('tab-login-sms')">
													<font color="red">短信登录</font>
												</a>
											</div>
											<div class="am-fr">
												<a href="javascript:void(0)"
													data-am-modal="{target: '#yunpan-forgotpassword-modal', width: 520, height: 360}">忘记密码?</a>
											</div>
										</div>
									</div>
								</form:form>
							</div>
							<!----------------- 短信登录 ------------------------------------>
							<div class="am-tab-panel am-fade am-in " id="tag-login-sms">
								<form:form method="post" modelAttribute="contentModel" id="login-form-sms" action="login-sms"
									class="am-form am-padding-xl am-padding-bottom-sm">
									<div class="am-g am-container">
										<h2>登录管理平台</h2>
										<form:errors path="password" class="am-alert am-alert-danger center"></form:errors>
									</div>
									<div class="am-g am-padding-sm">
										<div class="am-form-group am-form-icon">
											<span class="am-icon-user"></span>
											<input type="text" id="mobile" name="mobile" class="js-pattern-mobile am-form-field am-radius" placeholder="手机号"
												data-validation-message="手机号" required="required" />
										</div>
										<div class="am-form-group am-input-group">
											
											<input type="text" id="sms_token" name="sms_token"
												class="am-form-field js-pattern-sms_token js-ajax-validate" placeholder="短信验证码" required="required"
												data-validation-message="验证失败" />
											<span class="am-input-group-btn">
												<button id="btn_sms_token" class="am-btn am-btn-warning" type="button">
													<i class="am-icon-spinner am-icon-spin"></i>
													获取
												</button>
											</span>
										</div>
										
										<button type="button" id="login-btn-sms" class="am-btn am-btn-danger am-btn-block am-radius">登 录</button>
										<div class="am-form-group am-margin-top-sm am-text-sm">
											<div class="am-fl">
												<a href="#" onclick="javascript:loginSwitch('tab-login-pass')">
													<font color="red">密码登录</font>
												</a>
											</div>
											<div class="am-fr">
												<a href="javascript:void(0)"
													data-am-modal="{target: '#yunpan-forgotpassword-modal', width: 520, height: 360}">忘记密码?</a>
											</div>
										</div>
									</div>
								</form:form>
							</div>
						</div>
					</div>
					<div class="am-container am-g tr-yunpan-btn-block-container">
						<a href="/xcloud/register" class="am-btn am-btn-block am-btn-lg tr-yunpan-btn-block">立即注册</a>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="am-modal am-modal-no-btn" tabindex="-1" id="yunpan-forgotpassword-modal">
		<div class="am-modal-dialog">
			<div class="am-modal-hd">
				忘记密码-重设密码
				<a href="javascript:%20void(0)" class="am-close am-close-spin" data-am-modal-close="">×</a>
			</div>
			<div class="am-modal-bd">
				<div novalidate="novalidate" id="yunpan-forgotpassword-form" class="am-form am-padding">
					<div class="am-form-group am-input-group">
						<span class="am-input-group-label">
							<i class="am-icon am-icon-phone"></i>
						</span>
						<input value="" pattern="^1((3|5|8){1}\d{1}|70|77)\d{8}$" id="fp_phone" name="phone"
							class="am-form-field am-radius js-pattern-mobile" placeholder="手机号码" required="required" type="text">
					</div>
					<div class="am-form-group am-input-group">
						<span class="am-input-group-label">
							<i class="am-icon am-icon-suitcase"></i>
						</span>
						<input value="" pattern="^\d{4}$" id="fp_validate_code" name="valiate_code"
							class="am-form-field am-radius js-pattern-valitecode" placeholder="手机短信验证码" data-validation-message="验证失败"
							required="required" type="text">
						<span class="am-input-group-btn">
							<button id="getValidateCodeBtn" class="am-btn am-btn-default" type="button">获取验证码</button>
						</span>
					</div>
					<div class="am-form-group am-input-group">
						<span class="am-input-group-label">
							<i class="am-icon am-icon-lock"></i>
						</span>
						<input id="fp_new_password" name="new_password" class="am-form-field am-radius" placeholder="重新设置新密码" minlength="6"
							required="required" type="password">
					</div>
					<div class="am-form-group am-input-group">
						<span class="am-input-group-label">
							<i class="am-icon am-icon-lock"></i>
						</span>
						<input id="fp_confirm_password" name="confirm_password" class="am-form-field am-radius" placeholder="再次输入新密码"
							data-validation-message="请确认前后输入密码一致" required="required" data-equal-to="#fp_new_password" type="password">
					</div>
					<p>
						<button id="forgotPasswordBtn" type="button" class="am-btn am-btn-warning am-btn-block">提 交</button>
					</p>
				</div>
			</div>
		</div>
	</div>
	<!--footer start-->
	<%@ include file="../shared/pageFooter.jsp"%>
	<!--footer end-->
	<!-- js placed at the end of the document so the pages load faster -->
	<!--common script for all pages-->
	<%@ include file="../shared/importJs.jsp"%>
	<!--script for this page-->
	<script src="<c:url value='/assets/js/xcloud/common/validate-methods.js'/>" type="text/javascript"></script>
	<script src="<c:url value='/assets/js/countdown.js'/>" type="text/javascript"></script>
	<script src="<c:url value='/assets/js/drag/drag.js'/>" type="text/javascript"></script>
	<script src="<c:url value='/assets/js/xcloud/home/login.js'/>" type="text/javascript"></script>
	
	<script>
		loginSwitch('${contentModel.loginType}');
	</script>
</body>
</html>
