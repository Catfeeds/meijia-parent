<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.simi.oa.common.UrlHelper"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ include file="../shared/taglib.jsp"%>
<%@ taglib prefix="citySelectTag" uri="/WEB-INF/tags/citySelect.tld"%>
<!-- 学历选择标签 -->
<%@ taglib prefix="degreeSelectTag" uri="/WEB-INF/tags/degreeTypeSelect.tld" %>
<html>
<head>

<!--common css for all pages-->
<%@ include file="../shared/importCss.jsp"%>

<!--css for this page-->
<link rel="stylesheet" href="<c:url value='/css/fileinput.css'/>"
	type="text/css" />

<link
	href="<c:url value='/assets/bootstrap-datepicker/css/bootstrap-datepicker3.min.css'/>"
	rel="stylesheet" type="text/css" />
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
				<form:form modelAttribute="contentModel" class="form-horizontal"
					method="POST" action="saveApplyListForm" id="apply-form"
					enctype="multipart/form-data">

					<form:hidden path="id" />
					<input type="hidden" name="tagIds" id="tagIds" value="${contentModel.tagIds}"/>
					<div class="form-body">

						<div class="form-group required">

							<label class="col-md-2 control-label">姓名*</label>
							<div class="col-md-5">
								<form:input path="realName" class="form-control"
									placeholder="员工姓名" maxLength="32" />
								<form:errors path="realName" class="field-has-error"></form:errors>
							</div>
						</div>
						<div class="form-group">

							<label class="col-md-2 control-label">昵称</label>
							<div class="col-md-5">
								<form:input path="name" class="form-control" placeholder="昵称"
									maxLength="32" />
								<form:errors path="name" class="field-has-error"></form:errors>
							</div>
						</div>
						<div class="form-group required">

							<label class="col-md-2 control-label">手机号</label>
							<div class="col-md-5">
								<form:input path="mobile" class="form-control" placeholder="手机号"
									maxLength="32"/>
								<form:errors path="mobile" class="field-has-error"></form:errors>
							</div>
						</div>
						<%-- <div class="form-group required">

							<label class="col-md-2 control-label">身份证号</label>
							<div class="col-md-5">
								<form:input path="idCard" class="form-control" placeholder="身份证号"
									maxLength="32"/>
								<form:errors path="idCard" class="field-has-error"></form:errors>
							</div>
						</div> --%>
						
						

			<%-- 			<div class="form-group">

							<label class="col-md-2 control-label">性别</label>
							<div class="col-md-5">
								<form:input path="sexName" class="form-control" maxLength="32"
									readonly="true" />
								<form:errors path="sexName" class="field-has-error"></form:errors>
							</div>
						</div> --%>
						
						<div class="form-group required">
							<label class="col-md-2 control-label">性别</label>
							<div class="col-md-5">
								<form:radiobutton path="sex" value="0" label=" 男" />
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<form:radiobutton path="sex" value="1" label=" 女" />
							</div>
						</div>
						<%-- <div class="form-group required">
							<label class="col-md-2 control-label">学历*</label>
							<div class="col-md-5">
								<degreeSelectTag:select degreeId="${ contentModel.edu}"/>
							</div>
						</div> --%>
						<div class="form-group required">
								<label class="col-md-2 control-label">标签</label>
								<div class="col-md-5" id="allTag" >
									<c:forEach items="${tagList }" var="tag">
										<input type="button" name="tagName" value="${tag.tagName }" id="${tag.tagId }" onclick="setTagButton()" class="btn btn-default">
									</c:forEach>

								</div> 
						</div> 
						
						<%-- <div class="form-group required">

							<label class="col-md-2 control-label">所在城市</label>
							<div class="col-md-5">
								<form:input path="provinceName" class="form-control"
									maxLength="32" readonly="true" />
								<form:errors path="provinceName" class="field-has-error"></form:errors>
							</div>
						</div> --%>
						<div class="form-group required">
										<label class="col-md-2 control-label">审批情况</label>
										<div class="col-md-5">
											 <form:radiobutton path="isApproval" value="0" label="审核未通过" />
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<form:radiobutton path="isApproval" value="1" label="信息不符" />
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<form:radiobutton path="isApproval" value="2" label="审批通过" />
										</div>
									</div>
						
						<c:if test="${contentModel.isApproval == 0 ||contentModel.isApproval == 1}">
							<div class="form-actions fluid">
								<div class="col-md-offset-6 col-md-6">
									<button type="submit" id="applyForm_btn"
										class="btn btn-success">审批</button>
								</div>

							</div>
						</c:if>

						<%-- <c:if
							test="${contentModel.headImg != null && scontentModel.headImg != '' }">
							<div class="form-group ">

								<label class="col-md-2 control-label">图片</label>
								<div class="col-md-5">
									<img src="${ secModel.headImg }" />
								</div>
							</div>
						</c:if> --%>

						<%--  <c:if
							test="${secModel.headImg != null && secModel.headImg != '' }">
							<div class="form-group ">

								<label class="col-md-2 control-label">图片</label>
								<div class="col-md-5">
								<form:input path="nickName" class="form-control"
									placeholder="昵称" maxLength="32" />
								<form:errors path="nickName" class="field-has-error"></form:errors>
								</div>
							</div>
						</c:if> --%>
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
	<script type="text/javascript"
		src="<c:url value='/assets/bootstrap-datepicker/js/bootstrap-datepicker.min.js'/>"></script>
	<script type="text/javascript"
		src="<c:url value='/assets/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js'/>"></script>
	<script
		src="<c:url value='/assets/jquery-validation/dist/jquery.validate.min.js'/>"
		type="text/javascript"></script>
	<script type="text/javascript"
		src="<c:url value='/assets/bootstrap-fileupload/fileinput.min.js'/>"></script>

	<script src="<c:url value='/js/simi/sec/applyListForm.js'/>"
		type="text/javascript"></script>

	<script src="<c:url value='/js/simi/demo.js'/>"></script>
<script type="text/javascript">
	
	//处理
	setTagButton();
</script>
</body>
</html>
