<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ include file="../shared/taglib.jsp"%>

<html>
<head>

<!--common css for all pages-->
<%@ include file="../shared/importCss.jsp"%>

<!--css for this page-->
<link rel="stylesheet"  href="<c:url value='/assets/js/chosen/amazeui.chosen.css'/> "/>
</head>

<body>
	<!--header start-->
	<%@ include file="../shared/pageHeader.jsp"%>
	<!--header end-->

	<div class="am-cf admin-main">
		<!-- sidebar start -->
		<%@ include file="../hr/hr-menu.jsp"%>
		<!-- sidebar end -->

		<!-- content start -->
		<div class="admin-content">
			<div class="am-cf am-padding">
				<div class="am-fl am-cf">
					<strong class="am-text-primary am-text-lg">职位管理</strong> / <small>职位详情描述</small>
				</div>
			</div>
			<hr>

			<div class="am-g">

				<div class="am-u-sm-12 am-u-md-4 am-u-md-push-8">
					
				</div>

				<div class="am-u-sm-12 am-u-md-8 am-u-md-pull-4">
					<form:form modelAttribute="jobVoModel" method="POST" id="job_form" 
						class="am-form am-form-horizontal" enctype="multipart/form-data">
						
						<form:hidden path="jobId"/>
						<form:hidden path="companyId"/>
						
						
						<div class="am-form-group">
							<label  class="am-u-sm-3 am-form-label">职位名称<font color="red">*</font>:</label>
							<div class="am-u-sm-9">
								<form:input path="jobName"
									class="am-form-field am-radius" autocomplete="off"
									maxLength="64" required="required" />
								<small>尽量具体描述，例如：手机软件开发工程师</small>
							</div>
						</div>
						
						<div class="am-form-group">
							<label  class="am-u-sm-3 am-form-label">选择部门:</label>
							<div class="am-u-sm-9">
								<form:select path="deptId"  
									 class="chosen-select am-form-field am-radius" required="required">
										 <c:forEach items="${jobVoModel.deptList }" var="items">
											<option value="${items.deptId }" 
											
												<c:if test="${jobVoModel.deptId eq items.deptId }">
													selected
												</c:if>	
											
											>${items.name }</option>	
									</c:forEach> 
								</form:select>
							</div>
						</div>	
						
						<%-- <div class="am-form-group">
							<label  class="am-u-sm-3 am-form-label">选择员工:</label>
							<div class="am-u-sm-9">
								<form:select path="userId"  
									 class="chosen-select am-form-field am-radius" required="required">
										 <c:forEach items="${jobVoModel.userList }" var="items">
											<option value="${items.id }" 
											
												<c:if test="${jobVoModel.userId eq items.id }">
													selected
												</c:if>	
											
											>${items.name }</option>	
									</c:forEach> 
								</form:select>
							</div>
						</div> --%>	
						
						<div class="am-form-group">
							<label  class="am-u-sm-3 am-form-label">规划人数<font color="red">*</font>:</label>
							<div class="am-u-sm-9">
								<form:input path="totalNum" 
									class="am-form-field am-radius js-pattern-pinteger"
									maxLength="4" required="required" />
								<small>公司对此职位的人力资源规划数量</small>	
							</div>
						</div>
						
						
						<div class="am-form-group">
							<label for="user-phone" class="am-u-sm-3 am-form-label">备	注:</label>
							<div class="am-u-sm-9">
								<form:textarea path="remarks" class="form-control" 
									maxlength="250" 
									placeholder="请输入备注,不超过250字" />
							</div>
						</div>	
					
						
						<hr>
						<div class="am-form-group">
							<div class="am-u-sm-9 am-u-sm-push-3">
								<button type="button" class="am-btn am-btn-danger" id="btn-team-submit">提交</button>
								<button type="button" class="am-btn am-btn-success" id="btn-return">返回</button>
							</div>
						</div>
					</form:form>
				</div>
			</div>
		</div>
		<!-- content end -->

	</div>

	<a href="#" class="am-icon-btn am-icon-th-list am-show-sm-only admin-menu"
		data-am-offcanvas="{target: '#admin-offcanvas'}"></a>

	<!--footer start-->
	<%@ include file="../shared/pageFooter.jsp"%>
	<!--footer end-->

	<%@ include file="../shared/importJs.jsp"%>

	<!--script for this page-->
	<script src="<c:url value='/assets/js/xcloud/common/validate-methods.js'/>" type="text/javascript"></script>
	<script src="<c:url value='/assets/js/chosen/amazeui.chosen.min.js'/>"></script>
	<script src="<c:url value='/assets/js/xcloud/staffs/job-form.js'/>"></script>
</body>
</html>
