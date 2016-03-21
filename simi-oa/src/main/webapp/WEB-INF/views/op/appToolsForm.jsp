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
					id="app-form">
					<form:hidden path="tId" />
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

						<div class="form-group required">

							<label class="col-md-2 control-label">名称</label>
							<div class="col-md-5">
								<form:input path="name" class="form-control" placeholder="名称"
									maxLength="32" />
								<form:errors path="name" class="field-has-error"></form:errors>
							</div>
						</div>

						<c:if
							test="${contentModel.logo != null && contentModel.logo != '' }">
							<div class="form-group ">

								<label class="col-md-2 control-label">图片</label>
								<div class="col-md-4">
									<img src="${ contentModel.logo }" />
								</div>
								
								<label class="col-md-2 control-label">二维码</label>
								<div class="col-md-4">
									<img src="${ contentModel.qrCode }" weight="200" height="150" />
								</div>
							</div>
						</c:if>

						<input type="hidden" name="img_url_new" id="img_url_new"
							value="${ contentModel.logo }" />
						<div class="form-group required">

							<label class="col-md-2 control-label">图片地址</label>
							<div class="col-md-5">
								<input id="logoFile" type="file" name="logoFile"
									accept="image/*" data-show-upload="false">
								<form:errors path="logo" class="field-has-error"></form:errors>
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
						
						<div class="form-group required">
							<label class="col-md-2 control-label">菜单类型</label>
							<div class="col-md-5">
								<form:select path="menuType" class="form-control">
									<form:option value="t" selected="selected">应用与工具</form:option>
									<form:option value="d">成长与赚钱   </form:option>
									<form:option value="f">全部服务   </form:option>
									<form:option value="g">App自带  </form:option>
								</form:select>
							</div>
						</div>

						<div class="form-group required">
							<label class="col-md-2 control-label">跳转类型</label>
							<div class="col-md-5">
								<form:select path="openType" class="form-control">
								    <option value="">请选择</option>
									<form:option value="h5">h5</form:option>
									<form:option value="app">app</form:option>
								</form:select>
							</div>
						</div>

						<div class="form-group required">

							<label class="col-md-2 control-label">跳转地址</label>
							<div class="col-md-5">
								<form:input path="url" class="form-control" placeholder="跳转url"/>
								<form:errors path="url" class="field-has-error"></form:errors>
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
							<label class="col-md-2 control-label">默认显示</label>
							<div class="col-md-10">
								<div class="row">
									<div class="col-md-2" align="right">
										<label class="radio"> <form:radiobutton
												path="isDefault" value="1" />是
										</label>
									</div>
									<div class="col-md-2" align="left">
										<label class="radio"> <form:radiobutton
												path="isDefault" value="0" />否
										</label>
									</div>
								</div>
							</div>
						</div>
						<div class="form-group required">
							<label class="col-md-2 control-label">可以删除</label>
							<div class="col-md-10">
								<div class="row">
									<div class="col-md-2" align="right">
										<label class="radio"> <form:radiobutton
												path="isDel" value="0" />可删
										</label>
									</div>
									<div class="col-md-2" align="left">
										<label class="radio"> <form:radiobutton
												path="isDel" value="1" />不可删
										</label>
									</div>
								</div>
							</div>
						</div>
						<div class="form-group required">
							<label class="col-md-2 control-label">必须为服务商</label>
							<div class="col-md-10">
								<div class="row">
									<div class="col-md-2" align="right">
										<label class="radio"> <form:radiobutton
												path="isPartner" value="1" />是
										</label>
									</div>
									<div class="col-md-2" align="left">
										<label class="radio"> <form:radiobutton
												path="isPartner" value="0" />否
										</label>
									</div>
								</div>
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
						<div class="form-group required">

							<label class="col-md-2 control-label">应用提供者</label>
							<div class="col-md-5">
								<form:input path="appProvider" class="form-control" placeholder="应用提供者"
									/>
								<form:errors path="appProvider" class="field-has-error"></form:errors>
							</div>
						</div>

						<div class="form-group">
						<label class="col-md-2 control-label">应用描述</label>
						<div class="col-md-5">
								<div class="textarea">
									<form:textarea class="form-control" path="appDescribe" placeholder="应用描述"/><br/>
								</div>
							</div>
						</div>
						<div class="form-group required">

							<label class="col-md-2 control-label">不满足条件时跳转页面</label>
							<div class="col-md-5">
								<form:input path="authUrl" class="form-control" placeholder="不满足条件时跳转页面"
									/>
								<form:errors path="authUrl" class="field-has-error"></form:errors>
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
	<script src="<c:url value='/js/simi/op/appToolsForm.js'/>"
		type="text/javascript"></script>


</body>
</html>
