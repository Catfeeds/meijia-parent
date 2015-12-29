<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.simi.oa.common.UrlHelper"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="../shared/taglib.jsp"%>

<%@ taglib prefix="provinceSelectTag"
	uri="/WEB-INF/tags/provinceSelect.tld"%>

<html>
<head>
<!--common css for all pages-->
<%@ include file="../shared/importCss.jsp"%>

<!--css for this page-->
<link rel="stylesheet" href="<c:url value='/css/fileinput.css'/>"
	type="text/css" />
<%@ include file="../shared/importJs.jsp"%>
</head>

<body>

	<section id="container"> <!--header start--> <%@ include
		file="../shared/pageHeader.jsp"%> <!--header end-->
	<!--sidebar start--> <%@ include file="../shared/sidebarMenu.jsp"%>
	<!--sidebar end--> <!--main content start--> <section id="main-content">
	<section class="wrapper"> <!-- page start-->
	<div class="row">
		<div class="col-lg-12">
			<section class="panel">
			<div class="panel-body">
				<form:form modelAttribute="contentModel" class="form-horizontal"
					method="POST" enctype="multipart/form-data" id="partner-user-form">
					<div class="form-body">

						<form:hidden path="partnerId" />
						<form:hidden path="id" />
						<form:hidden path="userId" />

						<h4>服务人员基础信息</h4>
						<hr />
						<div class="form-group">
							<label class="col-md-2 control-label">公司名称&nbsp;*</label>
							<div class=col-md-5>${contentModel.companyName }</div>
						</div>
						<div class="form-group required">
							<!-- Text input-->
							<label class="col-md-2 control-label">姓名</label>
							<div class=col-md-5>
								<form:input path="name" class="form-control" placeholder="姓名"
									maxSize="10" />
							</div>
						</div>

						<div class="form-group required">
							<label class="col-md-2 control-label">手机号</label>
							<div class=col-md-5>
								<form:input path="mobile" class="form-control" placeholder="手机号"
									maxSize="11" />
							</div>
						</div>

						<c:if
							test="${contentModel.headImg != null && contentModel.headImg != '' }">
							<div class="form-group ">

								<label class="col-md-2 control-label">头像</label>
								<div class="col-md-5">
									<img src="${ contentModel.headImg }?w=300&h=300" />
								</div>
							</div>
						</c:if>

						<div class="form-group required">

							<label class="col-md-2 control-label">上传头像</label>
							<div class="col-md-5">
								<input id="imgUrlFile" type="file" name="imgUrlFile"
									accept="image/*" data-show-upload="false">

							</div>
						</div>


						<input type="hidden" id="citySelectedId"
							value="${contentModel.cityId}" /> <input type="hidden"
							id="regionSelectedId" value="${contentModel.regionId}" />
						<div class="form-group required">
							<label class="col-md-2 control-label">所在省份:</label>
							<div class="col-md-5">
								<provinceSelectTag:select
									selectId="${ contentModel.provinceId }" />
								<form:errors path="partnerId" class="field-has-error"></form:errors>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">所在城市:</label>

							<div class="col-md-5">
								<select name="cityId" path="cityId" id="cityId"
									class="form-control">
									<option value="0">全部</option>
								</select>
								<form:errors path="cityId" class="field-has-error"></form:errors>
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-2 control-label">所在区县:</label>

							<div class="col-md-5">
								<select name="regionId" path="regionId" id="regionId"
									class="form-control">
									<option value="0">全部</option>
								</select>
								<form:errors path="regionId" class="field-has-error"></form:errors>
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-2 control-label">服务类别*</label>
							<div class="col-md-5">
								<form:select path="serviceTypeId" class="form-control">
									<option value="">请选择服务类别</option>
									<form:options items="${partnerServiceType}" itemValue="id"
										itemLabel="name" />
								</form:select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">服务响应时间 </label>
							<div class="col-md-5">
								<select id="responseTime" name="responseTime"
									class="form-control">
									<option value="">请选择服务响应时间</option>
									<option value="0">半个小时内响应</option>
									<option value="1">一个小时内响应</option>
									<option value="2">2个小时内响应</option>
								</select> <span class="am-form-caret"></span>
							</div>
						</div>
						<div class="form-group required">
							<label class="col-md-2 control-label">权重类型</label>
							<div class="col-md-5">

								<div class="row">
									<div class="col-md-2" align="right">
										<label class="radio"> <form:radiobutton path="weightType"
												value="0" />默认
										</label>
									</div>
									<div class="col-md-2" >
										<label class="radio"> <form:radiobutton path="weightType"
												value="1" />推荐
										</label>
									</div>
									<div class="col-md-2" >
										<label class="radio"> <form:radiobutton path="weightType"
												value="2" />优惠
										</label>
									</div>
									<div class="col-md-2">
										<label class="radio"> <form:radiobutton path="weightType"
												value="3" />新人
										</label>
									</div>
								</div>
							</div>
						</div>
						
						<div class="form-group required">
							<!-- Text input-->
							<label class="col-md-2 control-label">权重排序序号</label>
							<div class=col-md-5>
								<form:input path="weightNo" class="form-control" placeholder="请输入权重排序序号 0 - 65535之间整数"
									maxSize="10" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">个人简介</label>
							<div class="col-md-5">
								<form:textarea path="introduction" class="form-control"
									placeholder="个人简介" />
							</div>
						</div>

						<div class="form-group required">
							<label class="col-md-2 control-label">标签</label>
							<div class="col-md-5" id="allTag">
								<c:forEach items="${tags }" var="tag">
									<input type="button" name="tagName" value="${tag.tagName }"
										id="${tag.tagId }" onclick="setTagButton()"
										class="btn btn-default">
								</c:forEach>
								<input type="hidden" name="tagIds" id="tagIds"
									value="${ tagIds }" />
							</div>
						</div>


						<div class="form-actions">
							<div class="row">
								<div class="col-md-4" align="right">
									<button class="btn btn-success" id="btn_submit" type="button">保存</button>
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

	</section> </section> <%@ include file="../shared/pageFooter.jsp"%>
	<!--footer end--> </section>

	<!-- js placed at the end of the document so the pages load faster -->
	<!--common script for all pages-->
	<!--script for this page-->

	<script type="text/javascript"
		src="<c:url value='/assets/jquery-validation/dist/jquery.validate.min.js'/>"></script>
	<script
		src="<c:url value='/assets/jquery-validation/dist/additional-methods.min.js'/>"
		type="text/javascript"></script>
	<script
		src="<c:url value='/assets/bootstrap-fileupload/fileinput.min.js'/>"
		type="text/javascript"></script>
	<script src="<c:url value='/js/simi/common/validate-methods.js'/>"
		type="text/javascript"></script>
	<script type="text/javascript"
		src="<c:url value='/js/simi/common/validate-ajax.js'/>"></script>
	<!-- 省市区联动js -->
	<script type="text/javascript"
		src="<c:url value='/js/simi/common/select-province-city-region.js'/>"></script>
	<script type="text/javascript"
		src="<c:url value='/js/simi/partner/partnerUserForm.js'/>"></script>
	<script type="text/javascript">
		$('#provinceId').trigger('change');
		$('#cityId').trigger('change');
		setTagButton();
	</script>

</body>
</html>