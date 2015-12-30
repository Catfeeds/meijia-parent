<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ include file="../shared/taglib.jsp"%>

<html>
<head>

<!--common css for all pages-->
<%@ include file="../shared/importCss.jsp"%>

<!--css for this page-->
<link href="<c:url value='/css/fileinput.css'/>" type="text/css"
	rel="stylesheet" />
<link
	href="<c:url value='/assets/jquery-multi-select/css/multi-select.css'/>"
	media="screen" type="text/css" rel="stylesheet" />
</head>

<body>

	<section id="container"> <!--header start--> <%@ include
		file="../shared/pageHeader.jsp"%> <!--header end-->
	<!--sidebar start--> <%@ include file="../shared/sidebarMenu.jsp"%>
	<!--sidebar end--> <!--main content start--> <section id="main-content">
	<section class="wrapper"> <!-- page start-->
	<div class="row">
		<div class="col-lg-12">
			<section class="panel"> <header class="panel-heading">
			用户管理 </header>

			<hr
				style="width: 100%; color: black; height: 1px; background-color: black;" />

			<div class="panel-body">

				<form:form modelAttribute="contentModel"
					enctype="multipart/form-data" class="form-horizontal" method="POST"
					id="appCardType-form">
					<form:hidden path="cardTypeId" />
					<form:hidden path="addTime" />
					
					<div class="form-body">

						<div class="form-group required">

							<label class="col-md-2 control-label">序号</label>
							<div class="col-md-5">
								<form:input path="No" class="form-control" placeholder="序号"
									maxLength="32" />
								<form:errors path="No" class="field-has-error"></form:errors>
							</div>
						</div>
						<%-- <div class="form-group required">

							<label class="col-md-2 control-label">时间</label>
							<div class="col-md-5">
								<form:input path="addTime" class="form-control" placeholder="序号"
									maxLength="32" />
								<form:errors path="addTime" class="field-has-error"></form:errors>
							</div>
						</div> --%>
						
						<div class="form-group required">

							<label class="col-md-2 control-label">名称</label>
							<div class="col-md-5">
								<form:input path="name" class="form-control" placeholder="名称"
									maxLength="32" />
								<form:errors path="name" class="field-has-error"></form:errors>
							</div>
						</div>

						<c:if
							test="${contentModel.cardIcon != null && contentModel.cardIcon != '' }">
							<div class="form-group ">

								<label class="col-md-2 control-label">图片</label>
								<div class="col-md-5">
									<img src="${ contentModel.cardIcon }" />
								</div>
							</div>
						</c:if>

						<input type="hidden" name="img_url_new" id="img_url_new"
							value="${ contentModel.cardIcon }" />
						<div class="form-group required">

							<label class="col-md-2 control-label">图片地址</label>
							<div class="col-md-5">
								<input id="cardIconFile" type="file" name="cardIconFile"
									accept="image/*" data-show-upload="false">
								<form:errors path="cardIcon" class="field-has-error"></form:errors>
							</div>
						</div>
						<div class="form-group required">
							<label class="col-md-2 control-label">卡片类型</label>
							<div class="col-md-5">
								<form:select path="cardType" class="form-control">
									<form:option value="0" selected="selected">通用</form:option>
									<form:option value="1">会议安排</form:option>
									<form:option value="2">通知公告</form:option>
									<form:option value="3">事务提醒</form:option>
									<form:option value="4">面试邀约</form:option>
									<form:option value="5">行程规划</form:option>
								</form:select>
							</div>
						</div>
						<div class="form-group required">

							<label class="col-md-2 control-label">应用类型</label>
							<div class="col-md-5">
								<form:select path="appType" class="form-control">
									<form:option value="xcloud" selected="selected">云行政</form:option>
									<form:option value="timechick">时光机</form:option>
									<form:option value="simi">私秘</form:option>
								</form:select>
							</div>
						</div>
						<div class="form-actions fluid">
							<div class="col-md-offset-6 col-md-6">
								<button type="button" id="adForm_btn" class="btn btn-success">保存</button>

							</div>
						</div>
				</form:form>
			</div>
			</section>
		</div>
	</div>
	<!-- page end--> </section> </section> <!--main content end--> <!--footer start--> <%@ include
		file="../shared/pageFooter.jsp"%> <!--footer end-->
	</section>

	<!-- js placed at the end of the document so the pages load faster -->
	<!--common script for all pages-->
	<%@ include file="../shared/importJs.jsp"%>

	<!--script for this page-->
	<script
		src="<c:url value='/assets/jquery-multi-select/js/jquery.multi-select.js'/>"
		type="text/javascript"></script>
	<script
		src="<c:url value='/assets/bootstrap-fileupload/fileinput.min.js'/>"
		type="text/javascript"></script>
	<script
		src="<c:url value='/assets/jquery-validation/dist/jquery.validate.min.js'/>"
		type="text/javascript"></script>
	<script src="<c:url value='/js/simi/op/appCardTypeForm.js'/>"
		type="text/javascript"></script>


</body>
</html>
