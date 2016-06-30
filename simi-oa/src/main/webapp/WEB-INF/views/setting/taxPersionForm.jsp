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
			<section class="panel"> <header class="panel-heading">个人所得税率表 </header>

			<hr style="width: 100%; color: black; height: 1px; background-color: black;" />

			<div class="panel-body">
				
				<form:form modelAttribute="contentModel" action="form" 
					id="insuranceForm" class="form-horizontal" method="POST" >
					
					<input type="hidden" name="id" value="${id }"/>
					
					<div class="form-body">
						
						<div class="form-group required">
							<label class="col-md-2 control-label">级别*</label>
							<div class="col-md-5">
								<form:input path="level"  placeholder="form-control" maxlength="10" onkeyup="value=value.replace(/[^\d]/g,'')"/>
							</div>
						</div>
						
						<div class="form-group required">
							<label class="col-md-2 control-label">应纳税所得额(含税)*</label>
							<div class="col-md-5">
								超过
								<form:input path="taxMin" placeholder="" maxlength="10" size="6" onkeyup="value=value.replace(/[^\d]/g,'')"/>
								至
								<form:input path="taxMax" placeholder="" maxlength="10" size="6" onkeyup="value=value.replace(/[^\d]/g,'')"/>的部分
							</div>
						</div>
						
						<div class="form-group required">
							<label class="col-md-2 control-label">应纳税所得额(不含税)*</label>
							<div class="col-md-5">
							
								超过
								<form:input path="noTaxMin" placeholder="" maxlength="10" size="6" onkeyup="value=value.replace(/[^\d]/g,'')"/>
								
								至
								<form:input path="noTaxMax"  placeholder="" maxlength="10" size="6" onkeyup="value=value.replace(/[^\d]/g,'')"/>的部分
								
							</div>
						</div>
						
						<div class="form-group required">
							<label class="col-md-2 control-label">税率(%)*</label>
							<div class="col-md-5">
								<form:input path="taxRio"  placeholder="" maxlength="10" onkeyup="value=value.replace(/[^\d]/g,'')"/>%
							</div>
						</div>

						<div class="form-group required">
							<label class="col-md-2 control-label">速算扣除数*</label>
							<div class="col-md-5">
								<form:input path="taxSs"  placeholder="" maxlength="10" onkeyup="value=value.replace(/[^\d]/g,'')"/>
							</div>
						</div>
						
						
						
						<div class="form-actions fluid">
							<div class="col-md-offset-6 col-md-6" style="margin-left: 315px">
								<button type="button" id="insuranceFormBtn" class="btn btn-success">保存</button>
							</div>
						</div>
				</form:form>
			</div>
			</section>
		</div>
	</div>
	<!-- page end--> </section> </section> <!--main content end--> <!--footer start--> 
	<%@ include file="../shared/pageFooter.jsp"%>
	<!--footer end--> </section>

	<!-- js placed at the end of the document so the pages load faster -->
	<!--common script for all pages-->
	<%@ include file="../shared/importJs.jsp"%>

	<!--script for this page-->
	
	<!-- 表单校验 -->	
	<script type="text/javascript" src="<c:url value='/assets/jquery.validate.min.js' />"></script>
	
	<script type="text/javascript" src="<c:url value='/js/simi/setting/taxPersionForm.js' />"></script>
	

</body>
</html>
