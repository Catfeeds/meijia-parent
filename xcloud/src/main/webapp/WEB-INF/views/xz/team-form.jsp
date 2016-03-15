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
		<%@ include file="../xz/xz-menu.jsp"%>
		<!-- sidebar end -->

		<!-- content start -->
		<div class="admin-content">
			<div class="am-cf am-padding">
				<div class="am-fl am-cf">
					<strong class="am-text-primary am-text-lg">送水</strong> / <small>drinking water</small>
				</div>
			</div>
			<hr>

			<div class="am-g">

				<div class="am-u-sm-12 am-u-md-4 am-u-md-push-8">
					<section class="am-panel am-panel-default"> <header class="am-panel-hd"> <img
						src="<c:url value='/assets/img/a1.png'/>" class="am-img-thumbnail am-circle" width="35" height="35">
					云小秘提示您 </header>
					<div class="am-panel-bd">可以一键扫码叫水，快来试试吧</div>
					<div class="am-panel-bd"><img src="${xCompany.qrCode }" width="250" height="250" /></div>
					</section>
				</div>

				<div class="am-u-sm-12 am-u-md-8 am-u-md-pull-4">
					<form:form modelAttribute="contentModel" method="POST" id="team-form"
						class="am-form am-form-horizontal" enctype="multipart/form-data">
                        <input type="hidden" id="userId" value="${userId }" />
						
						    <div class="am-form-group">
							<label for="user-phone" class="am-u-sm-3 am-form-label">清洁类型:</label>
							<div class="am-u-sm-9">
						      <select id="teamType">
						        <option value="0">定期保洁</option>
						        <option value="1">深度养护</option>
						        <option value="2">维修清理</option>
						        <option value="3">其他</option>
						      </select>
						      <span class="am-form-caret"></span>
						    </div>
						</div>
						<div class="am-form-group">
							<label for="user-phone" class="am-u-sm-3 am-form-label">服务地址:</label>
							<div class="am-u-sm-9">
								<form:select path="cityId" class="am-form-field am-radius">
									<form:options items="${dictCityVo}" itemValue="cityId" itemLabel="cityName" />
								</form:select>
								<small></small>
							</div>
						</div>
						
						<div class="am-form-group">
							<label for="user-phone" class="am-u-sm-3 am-form-label">联系人:</label>
							<div class="am-u-sm-9">
								<form:input path="linkMan" class="am-form-field am-radius" maxLength="32" />
								<small></small>
							</div>
						</div>
						
						<div class="am-form-group" required>
							<label for="user-phone" class="am-u-sm-3 am-form-label">联系电话:</label>
							<div class="am-u-sm-9">
								<form:input path="linkTel" class="am-form-field am-radius js-pattern-pinteger" maxLength="32"
								required="required" />
								<small></small>
							</div>
						</div>
						<div class="am-form-group">
							<label for="user-phone" class="am-u-sm-3 am-form-label">备注:</label>
							<div class="am-u-sm-9">
								<form:textarea path="remarks" class="form-control" placeholder="请输入备注" />
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

	<!-- js placed at the end of the document so the pages load faster -->
	<!--common script for all pages-->
	<%@ include file="../shared/importJs.jsp"%>

	<script src="<c:url value='/assets/jquery-validation/dist/jquery.validate.min.js'/>" type="text/javascript"></script>
	
	<!--script for this page-->
	<script src="<c:url value='/assets/js/xcloud/common/validate-methods.js'/>" type="text/javascript"></script>
	<script src="<c:url value='/assets/js/xcloud/xz/team-form.js'/>"></script>
</body>
</html>
