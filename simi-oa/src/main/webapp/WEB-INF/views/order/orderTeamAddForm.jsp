<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	import="com.simi.oa.common.UrlHelper"
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ include file="../shared/taglib.jsp"%>
<%@ taglib prefix="citySelectTag" uri="/WEB-INF/tags/citySelect.tld"%>
<%@ taglib prefix="timestampTag" uri="/WEB-INF/tags/timestamp.tld"%>
<html>
<head>

<!--common css for all pages-->
<%@ include file="../shared/importCss.jsp"%>

<!--css for this page-->
<link rel="stylesheet" href="<c:url value='/css/fileinput.css'/>" type="text/css" />

<link href="<c:url value='/assets/bootstrap-datepicker/css/bootstrap-datepicker3.min.css'/>" rel="stylesheet"
	type="text/css"
/>
</head>

<body>

	<section id="container"> <!--header start--> <%@ include file="../shared/pageHeader.jsp"%>
	<!--header end--> <!--sidebar start--> <%@ include file="../shared/sidebarMenu.jsp"%> <!--sidebar end-->
	<!--main content start--> <section id="main-content"> <section class="wrapper"> <!-- page start-->
	<div class="row">
		<div class="col-lg-12">
			<section class="panel"> <header class="panel-heading">
			<h4>团建下单</h4>
			</header>

			<hr style="width: 100%; color: black; height: 1px; background-color: black;" />

			<div class="panel-body">
				<form:form modelAttribute="contentModel" class="form-horizontal" method="POST" action="saveOrderTeamAdd"
					id="order-team-add-form">
					<%-- <form:hidden path="orderId" /> --%>
					
					<div class="form-body">

						<div class="form-group">
							<label class="col-md-2 control-label">用户手机号</label>
							<div class="col-md-5">
								<form:input path="mobile" class="form-control" maxLength="32"/>
								<form:errors path="mobile" class="field-has-error"></form:errors>
							</div>
						</div>
					 <div class="form-group">
						<label class="col-md-2 control-label">活&nbsp;动&nbsp;时&nbsp;间</label>
							<div class="col-md-5">
								<div class="input-group date">
									<fmt:formatDate var='serviceDate1' value='${partners.serviceDate}' type='both'
										pattern="yyyy-MM-dd" />
										<input type="text" value="${serviceDate1}" 
										id="serviceDate" name="serviceDate" class="form-control">
									<span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
								</div>
								</div>
						</div>
						<div class="form-group required">
							<label class="col-md-2 control-label">参加人数</label>
							<div class="col-md-5">
								<form:input path="attendNum" class="form-control" maxLength="32"  
								/>
								<form:errors path="attendNum" class="field-has-error"></form:errors>
							</div>
						</div>
						<div class="form-group required">
							<label class="col-md-2 control-label">活动天数</label>
							<div class="col-md-5">
							<form:select path="serviceDays" name="serviceDays" id="serviceDays" class="form-control">
									<form:option value="0">不限</form:option>
									<form:option value="1">一天</form:option>
									<form:option value="2">两天</form:option>
									<form:option value="3">三天及三天以上</form:option>
								</form:select>
								</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">团建类型</label>
							<div class="col-md-5">
								<form:select path="teamType" name="teamType" id="teamType" class="form-control">
									<form:option value="0">不限</form:option>
									<form:option value="1">年会</form:option>
									<form:option value="2">拓展训练</form:option>
									<form:option value="3">聚会沙龙</form:option>
									<form:option value="4">度假休闲</form:option>
									<form:option value="5">其他</form:option>
								</form:select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">活动城市</label>
							<div class="col-md-5">
								<form:select path="cityId" name="cityId" id="cityId" class="form-control">
									<option value="">请选择活动所在城市</option>
									<form:options items="${dictCityVo}" itemValue="cityId" itemLabel="cityName" />
								</form:select>
							</div>
						</div>
						
						<div class="form-group required">

							<label class="col-md-2 control-label">联系人</label>
							<div class="col-md-5">
								<form:input path="linkMan" class="form-control" />
								<form:errors path="linkMan" class="field-has-error"></form:errors>
							</div>
						</div>
						<div class="form-group required">

							<label class="col-md-2 control-label">联系人电话</label>
							<div class="col-md-5">
								<form:input path="linkTel" class="form-control" maxLength="32" />
								<form:errors path="linkTel" class="field-has-error"></form:errors>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">备注</label>
							<div class="col-md-5">
								<form:textarea path="remarks" class="form-control" placeholder="请输入备注" />
							</div>
						</div>
						<div class="form-actions fluid">
							<div class="col-md-offset-4">
								<button class="btn btn-success" id="btn_submit" type="button">保存订单信息</button>
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
	<script type="text/javascript" src="<c:url value='/assets/bootstrap-datepicker/js/bootstrap-datepicker.min.js'/>"></script>
	<script type="text/javascript"
		src="<c:url value='/assets/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js'/>"
	></script>
	<script src="<c:url value='/assets/jquery-validation/dist/jquery.validate.min.js'/>" type="text/javascript"></script>
	<script type="text/javascript" src="<c:url value='/assets/bootstrap-fileupload/fileinput.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/simi/order/orderTeamAddForm.js'/>"></script>
	
	
	<script>
		$('#orderStatus').trigger('change');
		$('#partnerId').trigger('change');
	</script>
</body>
</html>
