<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ include file="../shared/taglib.jsp"%>

<html>
<head>

<!--common css for all pages-->
<%@ include file="../shared/importCss.jsp"%>

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
					<div class="form-body">
						<div class="form-group required">
							<label class="col-md-2 control-label">手机号</label>
							<div class="col-md-5">
								<form:input path="mobile" class="form-control" readonly="true" />
							</div>
						</div>
						
						<div class="form-group required">
							<label class="col-md-2 control-label">手机号归属地</label>
							<div class="col-md-5">
								<form:input path="provinceName" class="form-control" readonly="true" />
							</div>
						</div>
						
						<div class="form-group required">
							<label class="col-md-2 control-label">用户姓名</label>
							<div class="col-md-5">
								<form:input path="name" class="form-control" readonly="true" />
							</div>
						</div>
						
						<div class="form-group required">
							<label class="col-md-2 control-label">推广渠道</label>
							<div class="col-md-5">
								<select path="op_ext_id" class="form-control">
									<option value="">选择推广渠道</option>
									<c:forEach items="${opExtList}" var="item">
										<option value="${item.id }">${item.name }</option>
									</c:forEach>
								</select>
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
	<script src="<c:url value='/js/simi/user/userForm.js'/>" type="text/javascript"></script>


</body>
</html>
