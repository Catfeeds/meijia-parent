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
					<strong class="am-text-primary am-text-lg">会议室</strong> / <small>meeting</small>
				</div>
			</div>
			<hr>

			<div class="am-g">
				<form:form modelAttribute="contentModel" method="POST" id="room-form" class="am-form am-form-horizontal">
					<form:hidden path="id" />
					<div class="am-form-group">
						<label for="user-phone" class="am-u-sm-3 am-form-label">会议室名称:</label>
						<div class="am-u-sm-9">
							<form:input path="name" class="am-form-field am-radius" maxLength="32" required="required" />
							<small></small>
						</div>
					</div>

					<div class="am-form-group">
						<label for="user-phone" class="am-u-sm-3 am-form-label">所在位置/备注:</label>
						<div class="am-u-sm-9">
							<form:textarea path="settingJson" class="form-control" placeholder="" />
						</div>
					</div>

					<hr>
					<div class="am-form-group">
						<div class="am-u-sm-9 am-u-sm-push-3">
							<button type="button" class="am-btn am-btn-danger" id="btn-room-submit">提交</button>
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

	<script src="<c:url value='/assets/jquery-validation/dist/jquery.validate.min.js'/>" type="text/javascript"></script>

	<!--script for this page-->
	<script src="<c:url value='/assets/js/xcloud/common/validate-methods.js'/>" type="text/javascript"></script>
	<script src="<c:url value='/assets/js/xcloud/xz/meeting-room-form.js'/>"></script>
</body>
</html>
