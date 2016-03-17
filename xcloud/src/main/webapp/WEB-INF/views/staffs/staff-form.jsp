<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ include file="../shared/taglib.jsp"%>

<html>
<head>

<!--common css for all pages-->
<%@ include file="../shared/importCss.jsp"%>

<!--css for this page-->

</head>

<body>
	<!--header start-->
	<%@ include file="../shared/pageHeader.jsp"%>
	<!--header end-->

	<div class="am-cf admin-main">


		<!-- content start -->
		<div class="admin-content">
			<div class="am-cf am-padding">
				<div class="am-fl am-cf">
					<strong class="am-text-primary am-text-lg">通讯录</strong> / <small>员工管理</small>
				</div>
			</div>
			<hr>

			<div class="am-g">

				<div class="am-u-sm-12 am-u-md-4 am-u-md-push-8">
					<section class="am-panel am-panel-default"> <header class="am-panel-hd"> <img
						src="<c:url value='/assets/img/a1.png'/>" class="am-img-thumbnail am-circle" width="35" height="35">
					云小秘提示您 </header>
					<div class="am-panel-bd">可以扫码加入员工，快来试试吧</div>
					<div class="am-panel-bd"><img src="${xCompany.qrCode }" width="250" height="250" /></div>
					</section>
				</div>

				<div class="am-u-sm-12 am-u-md-8 am-u-md-pull-4">
					<form:form modelAttribute="contentModel" method="POST" id="staff-form"
						class="am-form am-form-horizontal" enctype="multipart/form-data">
						<form:hidden path="companyId" />
						<form:hidden path="id" />

						<div class="am-form-group">
							<label for="user-email" class="am-u-sm-3 am-form-label">员工编号:</label>
							<div class="am-u-sm-9">
								<form:input path="jobNumber" class="am-form-field am-radius js-pattern-pinteger"
									maxLength="4" data-validation-message="" placeholder="" required="required" 
									readOnly="${(contentModel.id > 0) ? 'readonly' : ''}"
									/>
								<small>*必填项，范围在 0001-9999之间</small>
							</div>
						</div>

						<div class="am-form-group">
							<label for="user-phone" class="am-u-sm-3 am-form-label">手机号码:</label>
							<div class="am-u-sm-9">
								<form:input path="mobile" class="am-form-field am-radius js-pattern-mobile" maxLength="11"
									data-validation-message="" placeholder="手机号" required="required" />
								<small>*必填项，不可重复</small>
							</div>
						</div>

						<div class="am-form-group">
							<label for="user-phone" class="am-u-sm-3 am-form-label">姓名:</label>
							<div class="am-u-sm-9">
								<form:input path="name" class="am-form-field am-radius" placeholder="" maxLength="64"
									required="required" />
								<small>*必填项</small>
							</div>
						</div>

						<div class="am-form-group">
							<label for="user-phone" class="am-u-sm-3 am-form-label">邮箱:</label>
							<div class="am-u-sm-9">
								<form:input path="companyEmail" class="am-form-field am-radius js-pattern-email"
									required="required" maxLength="64" />
								<small></small>
							</div>
						</div>

						<div class="am-form-group">
							<label for="user-phone" class="am-u-sm-3 am-form-label">入职时间:</label>
							<div class="am-u-sm-9">
								<fmt:formatDate var='formattedDate' value='${contentModel.joinDate}' type='both'
									pattern="yyyy-MM-dd" />
								<div class="am-input-group am-datepicker-date"
									data-am-datepicker="{format: 'yyyy-mm-dd', viewMode: 'years'}">
									<input type="text" id="joinDate" name="joinDate" value="${formattedDate}"
										class="am-form-field" placeholder="" readonly required> <span
										class="am-input-group-btn am-datepicker-add-on">
										<button class="am-btn am-btn-default" type="button">
											<span class="am-icon-calendar"></span>
										</button>
									</span>
								</div>
								<small></small>
							</div>
						</div>

						<div class="am-form-group">
							<label for="user-phone" class="am-u-sm-3 am-form-label">职位:</label>
							<div class="am-u-sm-9">
								<form:input path="jobName" class="am-form-field am-radius" placeholder="" maxLength="64" />
								<small></small>
							</div>
						</div>

						<div class="am-form-group">
							<label for="user-phone" class="am-u-sm-3 am-form-label">所在部门:</label>
							<div class="am-u-sm-9">
								<form:select path="deptId" class="am-form-field am-radius">
									<form:options items="${deptList}" itemValue="deptId" itemLabel="name" />
								</form:select>
								<small></small>
							</div>
						</div>

						<div class="am-form-group">
							<label for="user-phone" class="am-u-sm-3 am-form-label">员工类型:</label>
							<div class="am-u-sm-9">
								<form:select path="staffType" class="am-form-field am-radius">
									<form:option value="0">全职</form:option>
									<form:option value="1">兼职</form:option>
									<form:option value="2">实习</form:option>
								</form:select>
								<small></small>
							</div>
						</div>

						<div class="am-form-group">
							<label for="user-phone" class="am-u-sm-3 am-form-label"></label>
							<div class="am-u-sm-9">
								<form:errors path="mobile" class="am-alert am-alert-danger center"></form:errors>

							</div>
						</div>

						<hr>
						<div class="am-form-group">
							<div class="am-u-sm-9 am-u-sm-push-3">
								<button type="button" class="am-btn am-btn-danger" id="btn-staff-submit">保存</button>
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

	<!-- js placed at the end of the document so the pages load faster -->
	<!--common script for all pages-->
	<%@ include file="../shared/importJs.jsp"%>

	<!--script for this page-->
	<script src="<c:url value='/assets/js/xcloud/common/validate-methods.js'/>" type="text/javascript"></script>
	<script src="<c:url value='/assets/js/xcloud/staffs/staff-form.js'/>"></script>
</body>
</html>
