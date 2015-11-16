<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.simi.oa.common.UrlHelper"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ include file="../shared/taglib.jsp"%>

<html>
<head>

<!--common css for all pages-->
<%@ include file="../shared/importCss.jsp"%>

<!--css for this page-->
<link rel="stylesheet" href="<c:url value='/assets/bootstrap3-dialog-master/dist/css/bootstrap-dialog.min.css'/>"
 type="text/css"/>
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
			优惠券-充值后赠送 </header>

			<hr
				style="width: 100%; color: black; height: 1px; background-color: black;" />

			<div class="panel-body">
				<form:form modelAttribute="dictCoupons" id="recharge-coupon-form"
					commandName="dictCoupons" action="rechargeCouponForm"
					class="form-horizontal" method="POST">
					<form:hidden path="id" />
					<div class="form-body">
						

						<div class="form-group">

							<!-- Text input-->
							<label class="col-md-2 control-label">优惠券金额</label>
							<div class="col-md-5">
								<form:input path="value" class="form-control"
									placeholder="优惠券金额" value="${dictCoupons.value}" maxSize="10" />
								<form:errors path="value" class="field-has-error"></form:errors>

							</div>
						</div>
						<div class="form-group">

							<!-- Text input-->
							<label class="col-md-2 control-label">订单满金额</label>
							<div class="col-md-5">
								<form:input path="maxValue" class="form-control"
									placeholder="订单满金额" value="${dictCoupons.maxValue}" maxSize="10" />
								<form:errors path="value" class="field-has-error"></form:errors>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">通用类型 </label>

							<div class="col-md-5">
								<form:radiobutton path="rangType" value="0" />
								通用
								<form:radiobutton path="rangType" value="1" />
								唯一
							</div>
						</div>
						<input type="hidden" id="serviceTypeSelectedId" value="${dictCoupons.serviceTypeId}" />
						<input type="hidden" id="servicePriceIdSelectedId" value="${dictCoupons.servicePriceId}" />
						<div class="form-group">
							<label class="col-md-2 control-label">服务类别*</label>
							<div class="col-md-5">
								<form:select path="serviceTypeId" class="form-control">
								<option value="">请选择服务类别</option>
								<form:options items="${partnerServiceType}" itemValue="id" itemLabel="name"/>
								</form:select>
							</div>
						</div>
						<div class="form-group" >
							<label class="col-md-2 control-label">服务小类:</label> 
							
							<div class="col-md-5" >
								 <select name="servicePriceId" path="servicePriceId" id="servicePriceId" class="form-control">
									<option value="0">全部</option>
								
								</select> 
								<form:errors path="servicePriceId" class="field-has-error"></form:errors>
							</div>
						</div>
						
						<div class="form-group">

							<!-- Text input-->
							<label class="col-md-2 control-label">描&nbsp;&nbsp;&nbsp;
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;述*</label>
							<div class="col-md-5">
								<form:input path="introduction" name="introduction" 
									class="form-control" placeholder="请输入描述" />
								<p class="help-block"></p>
							</div>
						</div>
						<div class="form-group">

							<!-- Textarea -->
							<label class="col-md-2 control-label">详&nbsp;细&nbsp;说&nbsp;明*</label>
							<div class="col-md-5">
								<div class="textarea">
								<%-- 	<textarea type="textarea" name="description"
										class="form-control">${dictCoupons.description}</textarea> --%>
									<form:textarea class="form-control" path="description" placeholder="一句话描述"/><br/>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 col-sm-2 control-label">日&nbsp;期&nbsp;范&nbsp;围</label>
							<div class="col-md-5">
								<form:select path="rangMonth" class="form-control">
									<form:options items="${selectDataSource}" />
								</form:select>
							</div>
						</div>
						<div class="form-group">

							<!-- Text input-->
							<label class="col-md-2 control-label">过&nbsp;期&nbsp;时&nbsp;间</label>
							<div class="col-md-5">
							From:
								<div class="input-group date">
									<fmt:formatDate var='formattedDate1' value='${dictCoupons.fromDate}' type='both'
										pattern="yyyy-MM-dd" />
										<input type="text" value="${formattedDate1}" 
										id="fromDate" name="fromDate" readonly class="form-control">
									<span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
								</div>
							To:
								<div class="input-group date">
									<fmt:formatDate var='formattedDate2' value='${dictCoupons.toDate}' type='both'
										pattern="yyyy-MM-dd" />
										<input type="text" value="${formattedDate2}" id="toDate"
										name="toDate" readonly class="form-control">
									<span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
								</div>
							</div>
						</div>
					</div>
					<div class="form-actions">
						<div class="row">
							<div class="col-md-4" align="right">
								<button class="btn btn-success" id="addCoupon_btn" type="button">
									保存</button>
							</div>
							<!-- Button -->
							<div class="col-md-8">
								<button class="btn btn-success" type="reset">重置</button>
							</div>

						</div>
					</div>
					<!-- </fieldset> -->
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

<!-- 日期处理js -->
	<script type="text/javascript" src="<c:url value='/assets/bootstrap-datepicker/js/bootstrap-datepicker.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/assets/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js'/>"></script>
	<!--script for this page-->
	<script src="<c:url value='/assets/jquery-validation/dist/jquery.validate.min.js'/>"
		type="text/javascript"></script>
	<!-- rechargeCoupon Form表单 js -->
	<script src="<c:url value='/js/simi/coupon/couponForm.js'/>"
		type="text/javascript"></script>
			<script type="text/javascript" src="<c:url value='
			/assets/bootstrap3-dialog-master/dist/js/bootstrap-dialog.min.js'/>"></script>
			<!-- 大类小类联动js -->
	<script type="text/javascript"  src="<c:url value='/js/simi/coupon/select-price-type.js'/>"	></script>	
	<script type="text/javascript" src="<c:url value='/js/simi/coupon/couponForm.js'/>"></script>
	<script type="text/javascript">
	
	$('#serviceTypeId').trigger('change');
		//$('#servicePriceId').trigger('change');
	//	$('#cityId').trigger('change');
	//	setTagButton();
	</script>
</body>
</html>
