<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.simi.oa.common.UrlHelper"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ include file="../shared/taglib.jsp"%>
<%@ taglib prefix="citySelectTag" uri="/WEB-INF/tags/citySelect.tld"%>
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
					method="GET" action="orderView" 
					enctype="multipart/form-data">

					<form:hidden path="id" />
					<div class="form-body">
						<div class="form-group required">
						
						
							<label class="col-md-2 control-label">姓名</label>
							<div class="col-md-5">
								<form:input path="name" class="form-control"
									maxLength="32" readonly="true"/>
								<form:errors path="name" class="field-has-error"></form:errors>
							</div>
						</div>
						<%-- <div class="form-group required">

							<label class="col-md-2 control-label">订单号</label>
							<div class="col-md-5">
								<form:input path="orderNo " class="form-control"
									maxLength="32" readonly="true"/>
								<form:errors path="orderNo " class="field-has-error"></form:errors>
							</div>
						</div> --%>
						<div class="form-group">

							<label class="col-md-2 control-label">下单时间</label>
							<div class="col-md-5">
								<form:input path="addTime" class="form-control"
									maxLength="32" readonly="true"/>
								<form:errors path="addTime" class="field-has-error"></form:errors>
							</div>
						</div>
						
						<div class="form-group">

							<label class="col-md-2 control-label">用户手机号</label>
							<div class="col-md-5">
								<form:input path="mobile" class="form-control"
									maxLength="32" readonly="true"/>
								<form:errors path="mobile" class="field-has-error"></form:errors>
							</div>
						</div>
						
						<div class="form-group">

							<label class="col-md-2 control-label">城市名称</label>
							<div class="col-md-5">
								<form:input path="cityName" class="form-control"
									maxLength="32" readonly="true"/>
								<form:errors path="cityName" class="field-has-error"></form:errors>
							</div>
						</div>
						<div class="form-group required">

							<label class="col-md-2 control-label">用户服务地址</label>
							<div class="col-md-5">								
								<form:input path="addrId" class="form-control"
									maxLength="32" readonly="true"/>
								<form:errors path="addrId" class="field-has-error"></form:errors>
							</div>
						</div>
						
						<div class="form-group">

							<label class="col-md-2 control-label">优惠券</label>
							<div class="col-md-5">
								<form:input path="cardPasswd" class="form-control"
									maxLength="32" readonly="true"/>
								<form:errors path="cardPasswd" class="field-has-error"></form:errors>
							</div>
						</div>
						
						<div class="form-group">

							<label class="col-md-2 control-label">订单状态</label>
							<div class="col-md-5">
								<form:input path="orderStatus" class="form-control"
									maxLength="32" readonly="true"/>
								<form:errors path="orderStatus" class="field-has-error"></form:errors>
							</div>
						</div>
						<div class="form-group">

							<label class="col-md-2 control-label">总金额</label>
							<div class="col-md-5">
								<form:input path="orderMoney" class="form-control"
									maxLength="32" readonly="true"/>
								<form:errors path="orderMoney" class="field-has-error"></form:errors>
							</div>
						</div>
						<div class="form-group">

							<label class="col-md-2 control-label">支付金额</label>
							<div class="col-md-5">
								<form:input path="orderPay" class="form-control"
									maxLength="32" readonly="true"/>
								<form:errors path="orderPay" class="field-has-error"></form:errors>
							</div>
						</div>
						<div class="form-group">

							<label class="col-md-2 control-label">支付方式</label>
							<div class="col-md-5">
								<form:input path="payType" class="form-control"
									maxLength="32" readonly="true"/>
								<form:errors path="payType" class="field-has-error"></form:errors>
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
	<script type="text/javascript"
		src="<c:url value='/assets/bootstrap-datepicker/js/bootstrap-datepicker.min.js'/>"></script>
	<script type="text/javascript"
		src="<c:url value='/assets/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js'/>"></script>
	<script
		src="<c:url value='/assets/jquery-validation/dist/jquery.validate.min.js'/>"
		type="text/javascript"></script>
	<script type="text/javascript"
		src="<c:url value='/assets/bootstrap-fileupload/fileinput.min.js'/>"></script>

	<script src="<c:url value='/js/simi/demo.js'/>"></script>

</body>
</html>
