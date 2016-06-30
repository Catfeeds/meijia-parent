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
			<section class="panel"> <header class="panel-heading">社保公积金缴纳基数 </header>

			<hr style="width: 100%; color: black; height: 1px; background-color: black;" />

			<div class="panel-body">
				
				<form:form modelAttribute="contentModel" action="form" 
					id="insuranceForm" class="form-horizontal" method="POST" >
					
					<input type="hidden" name="id" value="${id }"/>
					
					<div class="form-body">
						<div class="form-group required">
							<label class="col-md-2 control-label">选择城市*</label>
							<div class="col-md-5">
								<form:select class="form-control" path="cityId">
											<option value="">请选择城市</option>
											<form:option value="2" label="北京"/>  
											<form:option value="74" label="上海"/>  
											<form:option value="198" label="广州"/>
											<form:option value="200" label="深圳"/>
								</form:select>
							</div>
						</div>
						
						<div class="form-group required">
							<label class="col-md-2 control-label">最低社保缴纳基数*</label>
							<div class="col-md-5">
								<form:input path="shebaoMin" class="form-control" placeholder="" maxlength="10"/>
							</div>
						</div>
						
						<div class="form-group required">
							<label class="col-md-2 control-label">最高社保缴纳基数*</label>
							<div class="col-md-5">
								<form:input path="shebaoMax" class="form-control" placeholder="" maxlength="10"/>
							</div>
						</div>
						<hr>
						<div class="form-group required">
							<label class="col-md-2 control-label">最低公积金缴纳基数*</label>
							<div class="col-md-5">
								<form:input path="gjjMin" class="form-control" placeholder="" maxlength="10"/>
							</div>
						</div>
						
						<div class="form-group required">
							<label class="col-md-2 control-label">最高公积金缴纳基数*</label>
							<div class="col-md-5">
								<form:input path="gjjMax" class="form-control" placeholder="" maxlength="10"/>
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
	
	<!-- 用作 城市地区联动效果 -->
	<script type="text/javascript" src="<c:url value='/js/simi/xcloud/insuranceBaseForm.js' />"></script>
	

</body>
</html>
