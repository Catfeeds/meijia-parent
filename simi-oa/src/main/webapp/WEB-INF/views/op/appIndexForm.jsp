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
					id="appIndex-form">
					<form:hidden path="id" />
					<form:hidden path="addTime" />
					
					<div class="form-body">

						<div class="form-group required">

							<label class="col-md-2 control-label">序号</label>
							<div class="col-md-5">
								<form:input path="serialNo" class="form-control" placeholder="序号"
									maxLength="32" />
								<form:errors path="serialNo" class="field-has-error"></form:errors>
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

							<label class="col-md-2 control-label">标题</label>
							<div class="col-md-5">
								<form:input path="title" class="form-control" placeholder="标题"
									maxLength="32" />
								<form:errors path="title" class="field-has-error"></form:errors>
							</div>
						</div>

						<c:if test="${contentModel.iconUrl != null && contentModel.iconUrl != '' }">
							<div class="form-group ">

								<label class="col-md-2 control-label">图片</label>
								<div class="col-md-5">
									<img src="${ contentModel.iconUrl }" />
								</div>
							</div>
						</c:if>

						<input type="hidden" name="img_url_new" id="img_url_new"
							value="${ contentModel.iconUrl }" />
						<div class="form-group required">

							<label class="col-md-2 control-label">图片地址</label>
							<div class="col-md-5">
								<input id="cardIconFile" type="file" name="cardIconFile"
									accept="image/*" data-show-upload="false">
								<form:errors path="iconUrl" class="field-has-error"></form:errors>
							</div>
						</div>
						<div class="form-group required">

							<label class="col-md-2 control-label">应用类型</label>
							<div class="col-md-5">
								<form:select path="appType" class="form-control">
									<form:option value="xcloud" selected="selected">菠萝人事</form:option>
									<form:option value="timechick">时光机</form:option>
									<form:option value="simi">私秘</form:option>
								</form:select>
							</div>
						</div>
						<div class="form-group required">
							<label class="col-md-2 control-label">操作类别</label>
							<div class="col-md-5">
								<form:select path="category" class="form-control">
								    <option value="">请选择</option>
									<form:option value="h5">h5</form:option>
									<form:option value="app">app</form:option>
								</form:select>
							</div>
						</div>
						<div class="form-group required">

							<label class="col-md-2 control-label">动作标识</label>
							<div class="col-md-5">
								<form:input path="action" class="form-control" placeholder="动作标识"
									maxLength="32" />
								<form:errors path="action" class="field-has-error"></form:errors>
							</div>
						</div>
						<div class="form-group required">

							<label class="col-md-2 control-label">操作相关参数</label>
							<div class="col-md-5">
								<form:input path="params" class="form-control" placeholder="动作标识"
									maxLength="32" />
								<form:errors path="params" class="field-has-error"></form:errors>
							</div>
						</div>
						<div class="form-group required">

							<label class="col-md-2 control-label">跳转地址</label>
							<div class="col-md-5">
								<form:input path="gotoUrl" class="form-control" placeholder="跳转路径"/>
								<form:errors path="gotoUrl" class="field-has-error"></form:errors>
							</div>
						</div>
						<div class="form-group required">
							<label class="col-md-2 control-label">是否上线</label>
							<div class="col-md-10">
								<div class="row">
									<div class="col-md-2" align="right">
										<label class="radio"> <form:radiobutton
												path="isOnline" value="0" />上线
										</label>
									</div>
									<div class="col-md-2" align="left">
										<label class="radio"> <form:radiobutton
												path="isOnline" value="1" />下线
										</label>
									</div>
								</div>
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
	<script src="<c:url value='/js/simi/op/appIndexForm.js'/>"
		type="text/javascript"></script>


</body>
</html>
