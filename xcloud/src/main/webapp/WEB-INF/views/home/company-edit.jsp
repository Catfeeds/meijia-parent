<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="../shared/taglib.jsp"%>
<html>
<head>
<!--common css for all pages-->
<%@ include file="../shared/importCss.jsp"%>
<link href="<c:url value='/assets/css/xcloud.css'/>" rel="stylesheet" />
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
			<div class="am-vertical-align-middle am-g am-g-collapse" style="width: 100%;">
				<div class="tr-yunpan-register-container am-container am-animation-scale-up am-u-md-8 am-u-md-push-2 am-u-sm-12">
					<form:form method="post" modelAttribute="contentModel" id="company-edit-form"
						class="am-form am-container am-padding-xl am-padding-bottom">
						<form:hidden path="companyId" />
						<div class="am-g am-container">
							<h2>编辑公司信息</h2>
							
						</div>
						<div class="am-g am-padding-sm">
							<div class="am-form-group am-input-group">
								<span class="am-input-group-label">
									<i class="am-icon am-icon-male"></i>
								</span>
								<form:input path="companyName" class="am-form-field am-radius" placeholder="输入公司全称" maxLength="64"
									required="required" />
							</div>
							<div class="am-form-group am-input-group">
								<span class="am-input-group-label">
									<i class="am-icon am-icon-male"></i>
								</span>
								<form:input path="shortName" class="am-form-field am-radius" placeholder="输入公司简称" maxLength="32"
									required="required" />
							</div>
							<div class="am-form-group am-input-group">
								<span class="am-input-group-label">
									<i class="am-icon am-icon-male"></i>
								</span>
								<form:input path="addr" class="am-form-field am-radius" placeholder="输入详细地址" maxLength="32" />
							</div>
							<div class="am-form-group am-input-group">
								<span class="am-input-group-label">
									<i class="am-icon am-icon-male"></i>
								</span>
								<form:select path="companySize" class="am-form-field am-radius">
									<%--   <form:option value="" selected = "selected">请选择公司规模</form:option> --%>
									<form:option value="0">未知</form:option>
									<form:option value="1">1~10人</form:option>
									<form:option value="2">11~20人</form:option>
									<form:option value="3">20~50人</form:option>
									<form:option value="4">50~100人</form:option>
									<form:option value="5">100人以上</form:option>
								</form:select>
								<small></small>
							</div>
							<div class="am-form-group am-input-group">
								<span class="am-input-group-label">
									<i class="am-icon am-icon-male"></i>
								</span>
								<form:input path="linkMan" class="am-form-field am-radius" placeholder="输入联系人" maxLength="32" />
							</div>
							<div class="am-form-group am-input-group">
								<span class="am-input-group-label">
									<i class="am-icon am-icon-male"></i>
								</span>
								<form:input path="email" class="am-form-field am-radius" placeholder="输入邮箱" maxLength="32" />
							</div>
							<button type="button" class="am-btn am-btn-success am-btn-block am-radius" id="btn-edit-submit">保存</button>
						</div>
					</form:form>
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
	<script src="<c:url value='/assets/js/countdown.js'/>" type="text/javascript"></script>
	<script src="<c:url value='/assets/js/xcloud/common/validate-methods.js'/>" type="text/javascript"></script>
	<script src="<c:url value='/assets/js/xcloud/home/company-edit.js'/>" type="text/javascript"></script>
</body>
</html>
