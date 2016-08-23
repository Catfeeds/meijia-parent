<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="../shared/taglib.jsp"%>
<html>
<head>
<!--common css for all pages-->
<%@ include file="../shared/importCss.jsp"%>
<!--css for this page-->
</head>
<body>
	<!--header start-->
	<%@ include file="../shared/pageHeader.jsp"%>
	<!--header end-->
	<div class="am-cf admin-main">
		<!-- sidebar start -->
		<%@ include file="../hr/hr-menu.jsp"%>
		<!-- sidebar end -->
		<!-- content start -->
		<div class="admin-content">
			<div class="am-cf am-padding">
				<div class="am-fl am-cf">
					<strong class="am-text-primary am-text-lg">公司信息</strong> / <small>companyName</small>
				</div>
			</div>
			<hr>
			<div class="am-u-sm-12 am-u-md-4 am-u-md-push-8">
				<section class="am-panel am-panel-default"> <header class="am-panel-hd"> 公司二维码 </header>
				<div class="am-panel-bd"></div>
				<div class="am-panel-bd">
					<img src="${contentModel.qrCode }" width="250" height="250" />
				</div>
				</section>
			</div>
			<div class="am-u-sm-12 am-u-md-8 am-u-md-pull-4">
				<form:form modelAttribute="contentModel" method="POST" id="company-form" class="am-form am-form-horizontal">
					<form:hidden path="companyId" />
					<div class="am-form-group">
						<label for="user-phone" class="am-u-sm-3 am-form-label">公司名称:<font color="red">*</font></label>
						<div class="am-u-sm-9">
							${contentModel.companyName } <small></small>
						</div>
					</div>
					<div class="am-form-group">
						<label for="user-phone" class="am-u-sm-3 am-form-label">公司邀请码:<font color="red">*</font></label>
						<div class="am-u-sm-9">
							${contentModel.invitationCode } <small></small>
						</div>
					</div>
					<div class="am-form-group am-form-inline">
						<label class="am-u-sm-3 am-form-label">公司简称:<font color="red">*</font></label>
						<div class="am-u-sm-9">
							<form:input path="shortName" class="am-form-field am-radius " maxLength="32" required="required" />
							<small></small>
						</div>
					</div>
					<div class="am-form-group am-form-inline">
						<label class="am-u-sm-3 am-form-label">公司规模:</label>
						<div class="am-u-sm-9">
							<form:select path="companySize" class="am-form-field am-radius">
								<form:option value="0">未知</form:option>
								<form:option value="1">49人及以下</form:option>
								<form:option value="2">50~99人</form:option>
								<form:option value="3">100~499人</form:option>
								<form:option value="4">500~999人</form:option>
								<form:option value="5">1000人以上</form:option>
							</form:select>
							<small></small>
						</div>
					</div>
					<div class="am-form-group">
						<label class="am-u-sm-3 am-form-label">公司地址:</label>
						<div class="am-u-sm-9">
							<form:hidden path="lat" />
							<form:hidden path="lng" />
							<form:hidden path="addr" />
							<input type="hidden" id="city" value="">
							<input type="text" id="suggestId" placeholder="${contentModel.addr}" value="${contentModel.addr}"
								class="am-form-field am-radius" />
							<div id="searchResultPanel" style="border: 1px solid #C0C0C0; width: 150px; height: auto; display: none;"></div>
							<br>
							<div style="width: 620px; height: 340px; border: 1px solid gray" id="containers"></div>
							<small></small>
						</div>
					</div>
					<hr>
					<div class="am-form-group">
						<div class="am-u-sm-9 am-u-sm-push-3">
							<button type="button" class="am-btn am-btn-danger" id="btn-save">保存</button>
							<button type="button" class="am-btn am-btn-success" id="btn-return">返回</button>
						</div>
					</div>
				</form:form>
			</div>
		</div>
		<!-- content end -->
	</div>
	<a href="#" class="am-icon-btn am-icon-th-list am-show-sm-only admin-menu"
		data-am-offcanvas="{target: '#admin-offcanvas'}"></a>
	<!--footer start-->
	<%@ include file="../shared/pageFooter.jsp"%>
	<!--footer end-->
	<!-- js placed at the end of the document so the pages load faster -->
	<!--common script for all pages-->
	<%@ include file="../shared/importJs.jsp"%>
	<!--script for this page-->
	<!-- 引入百度地图API,其中   申请的密钥   ak 和主机 ip绑定， -->
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=${ak}">
		
	</script>
	<script src="<c:url value='/assets/js/xcloud/common/baiduMap.js'/>"></script>
	<script src="<c:url value='/assets/js/xcloud/common/validate-methods.js'/>"></script>
	<script src="<c:url value='/assets/js/xcloud/hr/company-form.js'/>"></script>
</body>
</html>
