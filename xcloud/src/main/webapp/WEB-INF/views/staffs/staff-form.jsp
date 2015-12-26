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
		<!-- sidebar start -->
		<div class="admin-sidebar am-offcanvas" id="admin-offcanvas">

			<ul class="am-list admin-sidebar-list">
				<div class="am-cf am-padding">
					<div class="am-fl am-cf">
						<strong class="am-text-primary am-text-lg">扫码加入</strong>
					</div>

				</div>



				<li><img src="${xCompany.qrCode }" width="250" height="250" /></li>
			</ul>


		</div>
		<!-- sidebar end -->

		<!-- content start -->
		<div class="admin-content">
			<div class="am-cf am-padding">
				<div class="am-fl am-cf">
					<strong class="am-text-primary am-text-lg">员工管理</strong>


				</div>
				<div class="am-fr"></div>
			</div>

			<hr />

			<div class="am-tabs am-margin" data-am-tabs>
				<ul class="am-tabs-nav am-nav am-nav-tabs">
					<li class="am-active"><a href="#tab1">基本信息</a></li>

				</ul>

				<div class="am-tabs-bd">

					<div class="am-tab-panel am-fade am-in am-active" id="tab1">

						<form:form modelAttribute="contentModel" method="POST" id="staff-form"
							class="am-form am-container am-padding-xl am-padding-bottom" enctype="multipart/form-data">
							<form:hidden path="companyId" />
							<form:hidden path="id" />
							<div class="am-g am-margin-top">
								<div class="am-u-sm-4 am-u-md-2 am-text-right">员工编号:</div>
								<div class="am-u-sm-8 am-u-md-4">${contentModel.jobNumber }</div>
								<div class="am-hide-sm-only am-u-md-6"></div>
							</div>

							<div class="am-g am-margin-top ">
								<div class="am-u-sm-4 am-u-md-2 am-text-right">手机号码:</div>
								<div class="am-u-sm-8 am-u-md-4 ">
									<form:input path="mobile" class="am-form-field am-radius js-pattern-mobile"
										data-validation-message="" placeholder="手机号" required="required" />
								</div>
								<div class="am-hide-sm-only am-u-md-6">*必填，不可重复</div>
							</div>

							<div class="am-g am-margin-top">
								<div class="am-u-sm-4 am-u-md-2 am-text-right">姓名:</div>
								<div class="am-u-sm-8 am-u-md-4">
									<form:input path="name" class="am-form-field am-radius" placeholder="" maxLength="64"
										required="required" />

								</div>
								<div class="am-hide-sm-only am-u-md-6">*必填</div>
							</div>

							<div class="am-g am-margin-top">
								<div class="am-u-sm-4 am-u-md-2 am-text-right">公司邮箱:</div>
								<div class="am-u-sm-8 am-u-md-4">
									<form:input path="companyEmail" class="am-form-field am-radius js-pattern-email" required="required"
										maxLength="64" />

								</div>
								<div class="am-u-sm-12 am-u-md-6">*必填，不可重复</div>
							</div>

							<div class="am-g am-margin-top">
								<div class="am-u-sm-4 am-u-md-2 am-text-right">何时加入公司:</div>
								<div class="am-u-sm-8 am-u-md-4">
									<fmt:formatDate var='formattedDate' value='${contentModel.joinDate}' type='both'
										pattern="yyyy-MM-dd" />
									<div class="am-input-group am-datepicker-date"
										data-am-datepicker="{format: 'yyyy-mm-dd', viewMode: 'years'}">
										<input type="text" id="joinDate" name="joinDate" value="${formattedDate}" class="am-form-field"
											placeholder="" readonly> <span class="am-input-group-btn am-datepicker-add-on">
											<button class="am-btn am-btn-default" type="button">
												<span class="am-icon-calendar"></span>
											</button>
										</span>
									</div>
								</div>
								<div class="am-u-sm-12 am-u-md-6"></div>
							</div>

							<div class="am-g am-margin-top">
								<div class="am-u-sm-4 am-u-md-2 am-text-right">职位:</div>
								<div class="am-u-sm-8 am-u-md-4">
									<form:input path="jobName" class="am-form-field am-radius" placeholder="" maxLength="64" />

								</div>
								<div class="am-u-sm-12 am-u-md-6"></div>
							</div>

							<div class="am-g am-margin-top">
								<div class="am-u-sm-4 am-u-md-2 am-text-right">所在部门:</div>
								<div class="am-u-sm-8 am-u-md-4">
									<form:select path="deptId" class="am-form-field am-radius">
										<form:options items="${deptList}" itemValue="deptId" itemLabel="name" />
									</form:select>

								</div>
								<div class="am-u-sm-12 am-u-md-6"></div>
							</div>

							<div class="am-g am-margin-top">
								<div class="am-u-sm-4 am-u-md-2 am-text-right">员工类型:</div>
								<div class="am-u-sm-8 am-u-md-4">
									<form:select path="staffType" class="am-form-field am-radius">
										<form:option value="0">全职</form:option>
										<form:option value="1">兼职</form:option>
										<form:option value="2">实习</form:option>
									</form:select>


								</div>
								<div class="am-u-sm-12 am-u-md-6"></div>
							</div>

							<form:errors path="mobile" class="am-alert am-alert-danger center"></form:errors>

				</div>
			</div>

			<div class="am-margin">
				<button type="button" class="am-btn am-btn-danger" id="btn-staff-submit">保存</button>
				<button type="button" class="am-btn am-btn-success" id="btn-return">返回</button>
			</div>
			</form:form>
		</div>

	</div>
	</div>
	</div>
	<!-- content end -->

	</div>

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
