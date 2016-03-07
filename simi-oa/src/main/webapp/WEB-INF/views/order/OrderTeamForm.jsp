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
			<h4>团建订单管理</h4>
			</header>

			<hr style="width: 100%; color: black; height: 1px; background-color: black;" />

			<div class="panel-body">
				<form:form modelAttribute="contentModel" class="form-horizontal" method="POST" action="saveOrderTeam"
					id="order-team-view-form"
				>

					<form:hidden path="orderId" />
					
					<div class="form-body">

						<div class="form-group required">

							<label class="col-md-2 control-label">订单号</label>
							<div class="col-md-5">
								<form:input path="orderNo" class="form-control" maxLength="32" readonly="true" />
								<form:errors path="orderNo" class="field-has-error"></form:errors>
							</div>
						</div>
						<div class="form-group">

							<label class="col-md-2 control-label">下单时间</label>
							<div class="col-md-5">

								<input class="form-control" value="${addTimeStr }" maxLength="32" readonly="true" />
								<form:hidden path="addTime" />
							</div>
						</div>

						<div class="form-group required">
							<label class="col-md-2 control-label">姓名</label>
							<div class="col-md-5">
								<form:input path="userName" class="form-control" maxLength="32" readonly="true" />
								<form:errors path="userName" class="field-has-error"></form:errors>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">用户手机号</label>
							<div class="col-md-5">
								<form:input path="mobile" class="form-control" maxLength="32" readonly="true" />
								<form:errors path="mobile" class="field-has-error"></form:errors>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">手机号归属地:</label>
							<div class="col-md-5">
								<input class="form-control" value="${user.provinceName}" maxLength="32" readonly="true" />

							</div>
						</div>
						<div class="form-group">

							<label class="col-md-2 control-label">总金额</label>
							<div class="col-md-5">
								<form:input path="orderMoney" class="form-control" maxLength="32" 
								 />
								<form:errors path="orderMoney" class="field-has-error"></form:errors>
							</div>
						</div>
						<div class="form-group">

							<label class="col-md-2 control-label">支付金额</label>
							<div class="col-md-5">
								<form:input path="orderPay" class="form-control" maxLength="32" 
								 />
								<form:errors path="orderPay" class="field-has-error"></form:errors>
							</div>
						</div>
						  <div class="form-group">
						<label class="col-md-2 control-label">服&nbsp;务&nbsp;时&nbsp;间</label>
							<div class="col-md-5">
								<div class="input-group date">
									<fmt:formatDate var='serviceDate1' value='${partners.serviceDate}' type='both'
										pattern="yyyy-MM-dd" />
										<input type="text" value="${serviceDate1}" 
										id="serviceDate" name="serviceDate" readonly class="form-control">
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
						<div class="form-group">
							<label class="col-md-2 control-label">订单状态</label>
							<div class="col-md-5">
								<form:select path="orderStatus" name="orderStatus" id="orderStatus" class="form-control">
									<form:option value="0">已关闭</form:option>
									<form:option value="1">待支付</form:option>
									<form:option value="2">已支付</form:option>
									<form:option value="3">处理中</form:option>
									<form:option value="7">待评价</form:option>
									<form:option value="9">已完成</form:option>
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
			<section class="panel"> <header class="panel-heading">
			<h4>服务商信息： 已支付订单才需要进行服务商分派</h4>
			</header>
			<hr style="width: 100%; color: black; height: 1px; background-color: black;" />
			<div class="form-body">
				<form:form modelAttribute="orderExtPartner" class="form-horizontal" method="POST" action="saveOrderTeamPartner"
					id="order-team-partner-form"
				>
					<form:hidden path="id" />
					<form:hidden path="orderId" />
					<form:hidden path="orderNo" />
					<div class="form-group">
						<label class="col-md-2 control-label">服务商:</label>
						<div class="col-md-5">
							<form:select path="partnerId" class="form-control">
								<option value="">请选择服务商</option>
								<form:options items="${partnerList}" itemValue="partnerId" itemLabel="companyName" />
							</form:select>
						</div>
					</div>
					<div class="form-group required">
						<label class="col-md-2 control-label">服务商订单号:</label>
						<div class="col-md-5">
							<form:input path="partnerOrderNo" class="form-control" maxLength="32" />
							<form:errors path="partnerOrderNo" class="field-has-error"></form:errors>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-2 control-label">服务商订单价格:</label>
						<div class="col-md-5">
							<form:input path="partnerOrderMoney" class="form-control" maxLength="32" />
							<form:errors path="partnerOrderMoney" class="field-has-error"></form:errors>
						</div>
					</div>

					<div class="form-group">
						<label class="col-md-2 control-label">备注</label>
						<div class="col-md-5">
							<form:textarea path="remarks" class="form-control" placeholder="请输入备注" />
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-md-2 control-label">处理状态</label>
						<div class="col-md-5">
							<select id="orderExtStatus" name="orderExtStatus"  class="form-control">
								<option value="0" <c:if test="${contentModel.orderExtStatus == 0 }">selected="true"</c:if>    >运营人员处理中</option>
								<option value="1" <c:if test="${contentModel.orderExtStatus == 1 }">selected="true"</c:if>>已转派服务商</option>
							
							</select>
						</div>
					</div>
					<c:if test="${contentModel.orderStatus >= 2 }">
						<div class="form-actions fluid">
							<div class="col-md-offset-4">
								<button class="btn btn-success" id="btn_submit_partner" type="button">保存服务商信息</button>
							</div>
						</div>
					</c:if>
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
	<script type="text/javascript" src="<c:url value='/js/simi/order/orderTeamForm.js'/>"></script>
	
	
	<script>
		$('#orderStatus').trigger('change');
		$('#partnerId').trigger('change');
	</script>
</body>
</html>
