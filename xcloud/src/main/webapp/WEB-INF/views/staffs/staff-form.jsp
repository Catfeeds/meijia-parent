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
		<%@ include file="../hr/hr-menu.jsp"%>
		<!-- sidebar end -->
		<!-- content start -->
		<div class="admin-content">
			<div class="am-cf am-padding">
				<div class="am-fl am-cf">
					<strong class="am-text-primary am-text-lg">人事档案</strong> / <small>员工信息详情</small>
				</div>
			</div>
			<hr>
			
			<div class="am-g">
				<form:form modelAttribute="contentModel" method="POST" id="staff-form" class="am-form am-form-horizontal"
					enctype="multipart/form-data">
					<form:hidden path="companyId" />
					<form:hidden path="id" />
					<form:hidden path="userId" />
					<div class="am-u-sm-12 am-u-md-4 am-u-md-push-8">
						<br> <section class="am-panel am-panel-default">
						<header class="am-panel-hd"> 员工头像或照片 </header>
						<div class="am-panel-bd"></div>
						<div class="am-panel-bd">
							<div class="am-form-group">
								<label class="am-u-sm-1 am-form-label"></label>
								<div class="am-u-sm-9" style="height: 140px;">
									<img id="preHeadImg" src="${contentModel.headImg }" width="140" height="140"
										<c:if test="${contentModel.headImg == '' }">
										  	 style="display:none"
										  </c:if> />
								</div>
							</div>
							<div class="am-form-group">
								<label for="user-phone" class="am-u-sm-1 am-form-label"></label>
								<div class="am-u-sm-9">
									<div class="am-form-group am-form-file">
										<div class="am-u-sm-8">
											<button type="button" id="btnUpload" name="btnUpload" class="am-btn am-btn-danger am-btn-sm">
												<i class="am-icon-cloud-upload"></i>
												选择要上传的图片
											</button>
											<input id="headImg" type="file" name="headImg" accept="image/*" onchange="showMyImage(this, 'preHeadImg')">
										</div>
									</div>
								</div>
							</div>
						</div>
						</section>
					</div>
					<div class="am-u-sm-12 am-u-md-8 am-u-md-pull-4">
						<div class="am-tabs am-margin " data-am-tabs>
							<ul class="am-tabs-nav am-nav am-nav-tabs">
								<li class="am-active">
									<a href="#staff-info">基本信息</a>
								</li>
								<li>
									<a href="#staff-job">职位信息</a>
								</li>
								<li>
									<a href="#staff-files">相关附件</a>
								</li>
							</ul>
							<div class="am-tabs-bd">
								<!-- --------------- 基本信息 ---------------------------------- -->
								<div class="am-tab-panel am-fade am-in am-active" id="staff-info">
									<div class="am-form-group">
										<label for="user-phone" class="am-u-sm-3 am-form-label">手机号码<font color="red">*</font>: </label>
										<div class="am-u-sm-9">
											<form:input path="mobile" class="am-form-field am-radius js-pattern-mobile" maxLength="11"
												data-validation-message="" placeholder="手机号" required="required" />
										</div>
									</div>
									<div class="am-form-group">
										<label for="user-phone" class="am-u-sm-3 am-form-label">姓名<font color="red">*</font>: </label>
										<div class="am-u-sm-9">
											<form:input path="realName" class="am-form-field am-radius" placeholder="" maxLength="64" required="required" />
										</div>
									</div>
									<div class="am-form-group">
										<label for="user-phone" class="am-u-sm-3 am-form-label">性别:</label>
										<div class="am-u-sm-9">
											<label class="am-radio-inline"> <form:radiobutton path="sex" value="男" />先生
											</label> <label class="am-radio-inline"> <form:radiobutton path="sex" value="女" />女士
											</label>
										</div>
									</div>
									<div class="am-form-group">
										<label for="user-phone" class="am-u-sm-3 am-form-label">身份证号: </label>
										<div class="am-u-sm-9">
											<form:input path="idCard" class="am-form-field am-radius" placeholder="" maxLength="18" />
										</div>
									</div>
									<div class="am-form-group">
										<label for="user-phone" class="am-u-sm-3 am-form-label">企业邮箱:</label>
										<div class="am-u-sm-9">
											<form:input path="companyEmail" class="am-form-field am-radius js-pattern-email" maxLength="64" />
											<small></small>
										</div>
									</div>
									<div class="am-form-group">
										<label for="user-phone" class="am-u-sm-3 am-form-label">毕业院校:</label>
										<div class="am-u-sm-9">
											<form:input path="school" class="am-form-field am-radius" maxLength="64" />
											<small></small>
										</div>
									</div>
									<div class="am-form-group">
										<label for="user-phone" class="am-u-sm-3 am-form-label">学历:</label>
										<div class="am-u-sm-9">
											<form:select path="degreeId">
												<form:option value="">请选择</form:option>
												<form:options items="${degreeTypes}" />
											</form:select>
											<small></small>
										</div>
									</div>
									<div class="am-form-group">
										<label for="user-phone" class="am-u-sm-3 am-form-label">银行卡号:</label>
										<div class="am-u-sm-9">
											<form:input path="bankCardNo" class="am-form-field am-radius" maxLength="64" />
											<small></small>
										</div>
									</div>
									<div class="am-form-group">
										<label for="user-phone" class="am-u-sm-3 am-form-label">开户行:</label>
										<div class="am-u-sm-9">
											<form:input path="bankName" class="am-form-field am-radius" maxLength="64" />
											<small></small>
										</div>
									</div>
								</div>
								<!----------------- 职位信息 ------------------------------------>
								<div class="am-tab-panel am-fade am-in " id="staff-job">
									<div class="am-form-group">
										<label for="user-email" class="am-u-sm-3 am-form-label">员工编号: </label>
										<div class="am-u-sm-9">
											<form:input path="jobNumber" class="am-form-field am-radius js-pattern-pinteger" maxLength="4"
												data-validation-message="" placeholder="" required="required"
												readOnly="${(contentModel.id > 0) ? 'readonly' : ''}" style="background-color: #fff;" />
										</div>
									</div>
									<div class="am-form-group">
										<label for="user-phone" class="am-u-sm-3 am-form-label">入职时间:</label>
										<div class="am-u-sm-9">
											<fmt:formatDate var='formattedJoinDate' value='${contentModel.joinDate}' type='both' pattern="yyyy-MM-dd" />
											<div class="am-input-group am-datepicker-date" data-am-datepicker="{format: 'yyyy-mm-dd', viewMode: 'years'}">
												<input type="text" id="joinDate" name="joinDate" value="${formattedJoinDate}" class="am-form-field"
													placeholder="" readonly required style="background-color: #fff;">
												<span class="am-input-group-btn am-datepicker-add-on">
													<button class="am-btn am-btn-default" type="button">
														<span class="am-icon-calendar"></span>
													</button>
												</span>
											</div>
											<small></small>
										</div>
									</div>
									<div class="am-form-group">
										<label for="user-phone" class="am-u-sm-3 am-form-label">转正时间:</label>
										<div class="am-u-sm-9">
											<fmt:formatDate var='formattedRegularDate' value='${contentModel.regularDate}' type='both'
												pattern="yyyy-MM-dd" />
											<div class="am-input-group am-datepicker-date" data-am-datepicker="{format: 'yyyy-mm-dd', viewMode: 'years'}">
												<input type="text" id="regularDate" name="regularDate" value="${formattedRegularDate}" class="am-form-field"
													placeholder="" readonly required style="background-color: #fff;">
												<span class="am-input-group-btn am-datepicker-add-on">
													<button class="am-btn am-btn-default" type="button">
														<span class="am-icon-calendar"></span>
													</button>
												</span>
											</div>
											<small></small>
										</div>
									</div>
									<div class="am-form-group">
										<label for="user-phone" class="am-u-sm-3 am-form-label">合同开始时间:</label>
										<div class="am-u-sm-9">
											<div class="am-input-group am-datepicker-date" data-am-datepicker="{format: 'yyyy-mm-dd', viewMode: 'years'}">
												<input type="text" id="contractBeginDate" name="contractBeginDate" value="${contentModel.contractBeginDate}"
													class="am-form-field" placeholder="" readonly required style="background-color: #fff;">
												<span class="am-input-group-btn am-datepicker-add-on">
													<button class="am-btn am-btn-default" type="button">
														<span class="am-icon-calendar"></span>
													</button>
												</span>
											</div>
											<small></small>
										</div>
									</div>
									<div class="am-form-group">
										<label class="am-u-sm-3 am-form-label">合同年限:</label>
										<div class="am-u-sm-9">
											<form:select path="contractLimit" class="am-form-field am-radius">
												<form:option value="6">6个月</form:option>
												<form:option value="12">1年</form:option>
												<form:option value="24">2年</form:option>
												<form:option value="36">3年</form:option>
												
											</form:select>
										</div>
										<div class="am-u-sm-3"></div>
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
								</div>
								<!----------------- 附件信息 ------------------------------------>
								<div class="am-tab-panel am-fade am-in am-g" id="staff-files">
									<div class="am-u-sm-4">
										<section class="am-panel am-panel-default">
										<div class="am-panel-bd"></div>
										<div class="am-panel-bd">
											<div class="am-form-group" style="">
												<img id="imgIdCardFrontPrew" src="${contentModel.idCardFront}" width="140" height="140"
													<c:if test="${contentModel.idCardFront == '' }">
												  	 style="display:none"
												  </c:if> />
											</div>
											<div class="am-form-group am-form-file">
												<div class="am-u-sm-5">
													<button type="button" id="btnUpload" name="btnUpload" class="am-btn am-btn-danger am-btn-sm">
														<i class="am-icon-cloud-upload"></i>
														身份证正面照片
													</button>
													<input id="imgIdCardFront" type="file" name="imgIdCardFront" accept="image/*"
														onchange="showMyImage(this, 'imgIdCardFrontPrew')">
												</div>
											</div>
										</div>
										</section>
									</div>
									<div class="am-u-sm-4">
										<section class="am-panel am-panel-default">
										<div class="am-panel-bd"></div>
										<div class="am-panel-bd">
											<div class="am-form-group" style="">
												<img id="imgIdCardBackPrew" src="${contentModel.idCardBack }" width="140" height="140"
													<c:if test="${contentModel.idCardBack == '' }">
												  	 style="display:none"
												  </c:if> />
											</div>
											<div class="am-form-group am-form-file">
												<div class="am-u-sm-5">
													<button type="button" id="btnUpload" name="btnUpload" class="am-btn am-btn-danger am-btn-sm">
														<i class="am-icon-cloud-upload"></i>
														身份证反面照片
													</button>
													<input id="imgIdCardBack" type="file" name="imgIdCardBack" accept="image/*"
														onchange="showMyImage(this, 'imgIdCardBackPrew')">
												</div>
											</div>
										</div>
										</section>
									</div>
									<div class="am-u-sm-4">
										<section class="am-panel am-panel-default">
										<div class="am-panel-bd"></div>
										<div class="am-panel-bd">
											<div class="am-form-group" style="">
												<img id="imgDegreePrew" src="${contentModel.imgDegree }" width="140" height="140"
													<c:if test="${contentModel.imgDegree == '' }">
												  	 style="display:none"
												  </c:if> />
											</div>
											<div class="am-form-group am-form-file">
												<div class="am-u-sm-5">
													<button type="button" id="btnUpload" name="btnUpload" class="am-btn am-btn-danger am-btn-sm">
														<i class="am-icon-cloud-upload"></i>
														毕业证照片
													</button>
													<input id="imgDegree" type="file" name="imgDegree" accept="image/*"
														onchange="showMyImage(this, 'imgDegreePrew')">
												</div>
											</div>
										</div>
										</section>
									</div>
								</div>
							</div>
						</div>
					</div>
			</div>
			<hr>
			<div class="am-form-group">
				<label for="user-phone" class="am-u-sm-3 am-form-label"></label>
				<div class="am-u-sm-9">
					<form:errors path="mobile" class="am-alert am-alert-danger center"></form:errors>
				</div>
			</div>
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
