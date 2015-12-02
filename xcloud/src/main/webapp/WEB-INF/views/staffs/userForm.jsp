<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ include file="../shared/taglib.jsp"%>
<html>
<head>

<header class="navbar navbar-fixed-top">
<div class="container top-header">
	<div class="navbar-header">
		<button type="button" class="navbar-toggle" data-toggle="collapse"
			data-target=".navbar-collapse">
			<span class="icon icon-bar"></span> <span class="icon icon-bar"></span>
			<span class="icon icon-bar"></span>
		</button>
		<a class="navbar-brand" href="/"><img
			src="<c:url value='/img/logo_header.png'/>" alt="云行政"> <span
			class="text" style="color: #fff">云行政-智慧行政服务平台</span></a>
	</div>
</div>
</header>


<!--common css for all pages-->
<%@ include file="../shared/importCss.jsp"%>
<!--css for this page-->

<link rel="stylesheet"
	href="<c:url value='/js/vendor/zTree/css/awesomeStyle/awesome.css'/>"
	type="text/css">





<div class="h5a-header" id="content">
	<div class="container h5a-container gray">
		<div class="row">

			<div class="row col-sm-12">
				<div class="box hidden-print" style="height: 200px;">
					<div class="title">
						<h4>
							<div style="margin-top: -13px;">
								<a href="#">员工信息</a>

							</div>

						</h4>

						<form:form class="form-horizontal" modelAttribute="contentModel"
							method="POST" action="postUserForm" id="user-form"
							enctype="multipart/form-data">
							
							<form:hidden path="companyId" />
							<form:hidden path="id" />
						<!-- 	<form id="user-form" class="form-horizontal"> -->
							<div class="row col-sm-12">
								<div class="col-md-4">
									<div class="form-group">
										<label class="col-sm-2">*姓名:</label>
										<div class="col-sm-8">
										    <form:input path="name" class="h5a-input form-control input-sm" placeholder="姓名"
												/>
											<form:errors path="name" class="field-has-error"></form:errors>
										</div>
									</div>
								</div>
								<div class="col-md-4">
									<div class="form-group">
										<label class="col-sm-2">*身份证号:</label>
										<div class="col-sm-8">
									
										 <form:input path="idCard" class="h5a-input form-control input-sm" placeholder="身份证号"
												 />
											<form:errors path="idCard" class="field-has-error"></form:errors>
										
										</div>
									</div>
								</div>
								<div class="col-md-4">
									<div class="form-group">
										<label class="col-sm-2 control-label">*企业邮箱:</label>
										<div class="col-sm-8">
									
											<form:input path="companyEmail" class="h5a-input form-control input-sm" placeholder="企业邮箱"/>
											<form:errors path="companyEmail" class="field-has-error"></form:errors>
										</div>
									</div>
								</div>
								<div class="col-md-4">
									<div class="form-group">
										<label class="col-sm-2">*公司名称:</label>
										<div class="col-sm-8">
											<!-- <input type="text" id="company_name" name="company_name"
												 class="h5a-input form-control input-sm"
												placeholder="公司名称"> -->
											<form:input path="companyName" class="h5a-input form-control input-sm" placeholder="公司名称"/>
											<form:errors path="companyName" class="field-has-error"></form:errors>
										</div>
									</div>
								</div>
								<div class="col-md-4">
									<div class="form-group">
										<label class="col-sm-2">*公司传真:</label>
										<div class="col-sm-8">
											<!-- <input type="text" id="company_fax" name="company_fax"
											class="h5a-input form-control input-sm"
												placeholder="公司传真"> -->
												<form:input path="companyFax" class="h5a-input form-control input-sm" placeholder="公司名称"/>
											<form:errors path="companyFax" class="field-has-error"></form:errors>
										</div>
									</div>
								</div>
								<div class="col-md-4">
									<div class="form-group">
										<label class="col-sm-2">*公司座机:</label>
										<div class="col-sm-8">
											<!-- <input type="text" id="tel" name="tel"
											class="h5a-input form-control input-sm"
												placeholder="公司座机"> -->
										<form:input path="tel" class="h5a-input form-control input-sm" placeholder="公司座机"/>
										<form:errors path="tel" class="field-has-error"></form:errors>
										
										</div>
									</div>
								</div>
								<div class="col-md-4">
									<div class="form-group">
										<label class="col-sm-2 control-label">*区域:</label>
										<div class="col-sm-8">
											<!-- <input type="text" id="city_id" name="city_id" 
												class="h5a-input form-control input-sm" placeholder="区域"> -->
											<form:input path="cityId" class="h5a-input form-control input-sm" placeholder="区域"/>
										<form:errors path="cityId" class="field-has-error"></form:errors>
											
										</div>
									</div>
								</div>
								<div class="col-md-4">
									<div class="form-group">
										<label class="col-sm-2">*分机号:</label>
										<div class="col-sm-8">
											<!-- <input type="text" id="tel_ext" name="tel_ext"
											class="h5a-input form-control input-sm"
												placeholder="分机号"> -->
												<form:input path="telExt" class="h5a-input form-control input-sm" placeholder="分机号"/>
										<form:errors path="telExt" class="field-has-error"></form:errors>
										
										</div>
									</div>
								</div>
								<div class="col-md-4">
									<div class="form-group">
										<label class="col-sm-2">*员工类型:</label>
										<div class="item-xx">
												<select id="staffType" name="staffType">
										<option value="">---请选择---</option>
										<option value="0" selected="selected">全职</option>
										<option value="1">兼职</option>
										<option value="2">实习</option>
									</select>
									 <%-- <form:select name="staffType" path="staffType" id="staffType" >
									<option value="">---请选择---</option>
										<option value="0">全职</option>
										<option value="1">兼职</option>
										<option value="2">实习</option>
								</form:select> 
								<form:errors path="staffType" class="field-has-error"></form:errors> --%>
										</div>
										
									</div>
								</div>

							</div>

							<div class="row col-sm-12">

								<div class="col-md-4">
									<div class="form-group">
										<label class="col-sm-2">*工号:</label>
										<div class="col-sm-8">
											<!-- <input type="text" id="job_number" name="job_number"
												class="h5a-input form-control input-sm"
												placeholder="工号"> -->
										<form:input path="jobNumber" class="h5a-input form-control input-sm" placeholder="工号"/>
										<form:errors path="jobNumber" class="field-has-error"></form:errors>
										
										</div>
									</div>
								</div>
                                <!--=================================================================================================>
								<!-- ================================================================================== -->




								<div class="col-md-4">
									<div class="form-group">
										<label class="col-sm-2 control-label">*部门:</label>
										<div class="col-sm-8">
											
										<form:input path="deptId" class="h5a-input form-control input-sm" placeholder="部门"/>
										<form:errors path="deptId" class="field-has-error"></form:errors>
										
										</div>
									</div>
								</div>

								<div class="col-md-4">
									<div class="form-group">
										<label class="col-sm-2">*职位:</label>
										<div class="col-sm-8">
										
												<form:input path="jobName" class="h5a-input form-control input-sm" placeholder="职位"/>
										<form:errors path="jobName" class="field-has-error"></form:errors>
										
										</div>
									</div>
								</div>
							</div>

							<div class="row col-sm-12">
								<div class="col-md-4"></div>
								<div class="col-md-4">

									<div class="form-group">

										<!-- <a href="/xcloud/staff/userForm"><button
												class="btn btn-danger " type="button">提交</button></a> -->
										<!--<button type="submit" id="secForm_btn" class="btn btn-success">保存</button> -->
										<!-- <button type="submit" id="userForm_btn" class="btn btn-danger">提交</button> -->
										<button id="userForm_btn" class="btn btn-danger">提交</button>
										
									</div>
								</div>
								<div class="col-md-4"></div>
							</div>

						</form:form>
<!-- </form> -->
					</div>
				</div>

			</div>

		</div>
	</div>

	<!-- js placed at the end of the document so the pages load faster -->
	<!--common script for all pages-->
	<%@ include file="../shared/importJs.jsp"%>

	<!--script for this page-->
	<script type="text/javascript"
		src="<c:url value='/js/vendor/zTree/js/jquery.ztree.core-3.5.js'/>"></script>
		<script src="<c:url value='/assets/jquery-validation/dist/jquery.validate.min.js'/>"
		type="text/javascript"></script>
		<script type="text/javascript"
		src="<c:url value='/js/xcloud/staffs/userForm.js'/>"></script>
	</body>
</html>
