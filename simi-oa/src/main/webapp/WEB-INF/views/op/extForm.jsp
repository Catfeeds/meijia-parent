<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ include file="../shared/taglib.jsp"%>

<html>
<head>

<!--common css for all pages-->
<%@ include file="../shared/importCss.jsp"%>

<!--css for this page-->
<link  href="<c:url value='/css/fileinput.css'/>" type="text/css" rel="stylesheet" />
<link  href="<c:url value='/assets/jquery-multi-select/css/multi-select.css'/>" media="screen" type="text/css" rel="stylesheet"  />
</head>

<body>

	<section id="container"> <!--header start--> <%@ include file="../shared/pageHeader.jsp"%>
	<!--header end--> <!--sidebar start--> <%@ include file="../shared/sidebarMenu.jsp"%> <!--sidebar end-->

	<!--main content start--> <section id="main-content"> <section class="wrapper"> <!-- page start-->
	<div class="row">
		<div class="col-lg-12">
			<section class="panel"> <header class="panel-heading"> 推广渠道管理 </header>

			<hr style="width: 100%; color: black; height: 1px; background-color: black;" />

			<div class="panel-body">
				
				<form:form modelAttribute="contentModel"  class="form-horizontal" method="POST" id="ext-form">
					<form:hidden path="id" />
					<form:hidden path="settingType" />

					<div class="form-body">

						<div class="form-group required">

							<label class="col-md-2 control-label">渠道名称</label>
							<div class="col-md-5">
								<form:input path="name" class="form-control" placeholder="名称" maxLength="32" />
								<form:errors path="name" class="field-has-error"></form:errors>
							</div>
						</div>
						
						<div class="form-group required">

							<label class="col-md-2 control-label">推广提成</label>
							<div class="col-md-5">
								<form:input path="expIn" class="form-control" placeholder="每个用户提成多少，输入数字" maxLength="32" />
								<form:errors path="expIn" class="field-has-error"></form:errors>
							</div>
						</div>
						
						<div class="form-group required">
							<label class="col-md-2 control-label">备注</label>
							<div class="col-md-5">
								<form:textarea path="remarks" class="form-control" placeholder="请输入备注" />
							</div>
						</div>

						<div class="form-actions fluid">
							<div class="col-md-offset-6 col-md-6">
								<button type="button" id="extForm_btn" class="btn btn-success">保存</button>
							</div>
						</div>
				</form:form>
			</div>
			</section>
		</div>
	</div>
	<!-- page end--> </section> </section> <!--main content end--> <!--footer start--> <%@ include file="../shared/pageFooter.jsp"%>
	<!--footer end--> </section>

	<!-- js placed at the end of the document so the pages load faster -->
	<!--common script for all pages-->
	<%@ include file="../shared/importJs.jsp"%>

	<!--script for this page-->
	<script src="<c:url value='/js/simi/common/validate-methods.js'/>"></script>
	<script src="<c:url value='/assets/jquery-validation/dist/jquery.validate.min.js'/>" type="text/javascript"></script>
	<script src="<c:url value='/js/simi/op/extForm.js'/>" type="text/javascript"></script>


</body>
</html>
