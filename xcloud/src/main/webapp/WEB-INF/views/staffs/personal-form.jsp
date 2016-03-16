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
		<%@ include file="../xz/xz-menu.jsp"%>
		<!-- sidebar end -->

		<!-- content start -->
		<div class="admin-content">
			<div class="am-cf am-padding">
				<div class="am-fl am-cf">
					<strong class="am-text-primary am-text-lg">个人资料</strong> / <small>drinking water</small>
				</div>
			</div>
			<hr>

			<div class="am-g">

				<div class="am-u-sm-12 am-u-md-4 am-u-md-push-8">
					<section class="am-panel am-panel-default"> <header class="am-panel-hd"> <img
						src="<c:url value='/assets/img/a1.png'/>" class="am-img-thumbnail am-circle" width="35" height="35">
					云小秘提示您 </header>
					<div class="am-panel-bd">可以一键扫码叫水，快来试试吧</div>
					<div class="am-panel-bd"><img src="${xCompany.qrCode }" width="250" height="250" /></div>
					</section>
				</div>

				<div class="am-u-sm-12 am-u-md-8 am-u-md-pull-4">
					<form:form modelAttribute="contentModel" method="POST" id="personal-form"
						class="am-form am-form-horizontal" enctype="multipart/form-data">
                        <input type="hidden" id="userId" value="${userId }" />
                        
						<div class="am-form-group" required>
							<label for="user-phone" class="am-u-sm-3 am-form-label">手机号:</label>
							<div class="am-u-sm-9">
								<form:input path="mobile" class="am-form-field am-radius js-pattern-pinteger" maxLength="32"/>
								<small></small>
							</div>
						</div>
						<div class="am-form-group" required>
							<label for="user-phone" class="am-u-sm-3 am-form-label">头像:</label>
							<div class="am-u-sm-9">
						<input type="hidden" name="img_url_new" id="img_url_new" />
							<div class="am-form-group am-form-file">
								<button type="button" id="btnUpload" name="btnUpload"
									class="am-btn am-btn-danger am-btn-sm">
									<i class="am-icon-cloud-upload"></i> 选择要上传的图片
								</button>
								<input id="file" type="file" name="file" accept="image/*">
							</div>
						</div>
						</div>
						<div class="am-form-group" required>
							<label for="user-phone" class="am-u-sm-3 am-form-label">昵称:</label>
							<div class="am-u-sm-9">
								<form:input path="name" class="am-form-field am-radius" maxLength="32" required="required"/>
								<small></small>
							</div>
						</div>
						<div class="am-form-group">
							<label for="user-phone" class="am-u-sm-3 am-form-label">性别:</label>
							<div class="am-u-sm-9">
							<label class="am-radio-inline">
							<input type="radio"  value="0" name="sex"> 先生
							 </label>
							 <label class="am-radio-inline">
							<input type="radio" value="1" name="sex" checked> 女士
							 </label>
							</div>
						</div>
						<hr>
						<div class="am-form-group">
							<div class="am-u-sm-9 am-u-sm-push-3">
								<button type="button" class="am-btn am-btn-danger" id="btn-personal-submit">提交</button>
								<button type="button" class="am-btn am-btn-success" id="btn-return">返回</button>
							</div>
						</div>
					</form:form>
				</div>
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

	<script src="<c:url value='/assets/jquery-validation/dist/jquery.validate.min.js'/>" type="text/javascript"></script>
	
	<script src="<c:url value='/assets/js/ajaxfileupload.js'/>" type="text/javascript"></script>
	<!--script for this page-->
	<script src="<c:url value='/assets/js/xcloud/common/validate-methods.js'/>" type="text/javascript"></script>
	<script src="<c:url value='/assets/js/xcloud/staffs/personal-form.js'/>"></script>
</body>
</html>
