<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
					<strong class="am-text-primary am-text-lg">资产管理</strong> / <small>资产入库登记</small>
				</div>
			</div>
			<hr>

			<div class="am-g">

				<div class="am-u-sm-12 am-u-md-4 am-u-md-push-8">
					<section class="am-panel am-panel-default"> <header
						class="am-panel-hd"> <img
						src="<c:url value='/assets/img/a1.png'/>"
						class="am-img-thumbnail am-circle" width="35" height="35">
					云小秘提示您 </header>
					<div class="am-panel-bd">可以用App扫码加入快递，快去试试吧</div>
					<div class="am-panel-bd"><img src="<c:url value='/assets/img/erweima.png'/>" width="250" height="250" /></div>
					</section>
				</div>

				<div class="am-u-sm-12 am-u-md-8 am-u-md-pull-4">
					<form:form modelAttribute="contentModel" method="POST" 
						id="commpany-asset-form" class="am-form am-form-horizontal"
						enctype="multipart/form-data" >
					 
					 <input type="hidden" id="userId" name="userId" value="${userId }" /> 
					 <input type="hidden" id="companyId" name="companyId" value="${companyId }">
						
						<div class="am-form-group" required>
							<label class="am-u-sm-3 am-form-label">资产类别:</label>
							<div class="am-u-sm-9">
								
								<form:select path="assetTypeId" class="am-form-field am-radius">
									<form:options items="${contentModel.xCompSettingList}" itemValue="id" itemLabel="name" />
								</form:select>
								
							</div>
						</div>
						
						
						<div class="am-form-group">
							<label  class="am-u-sm-3 am-form-label">资产名称:</label>
							<div class="am-u-sm-9">
								<form:input path="name"
									class="am-form-field am-radius" autocomplete="off"
									maxLength="32" required="required"/>
								<small>*必填项</small>		
							</div>
						</div>

						<div class="am-form-group">
							<label  class="am-u-sm-3 am-form-label">数量:</label>
							<div class="am-u-sm-9">
								<form:input path="total" 
									class="am-form-field am-radius js-pattern-pinteger"
									maxLength="32" required="required"/>
								<small>*必填项</small>	
							</div>
						</div>

					
						<div class="am-form-group">
							<label class="am-u-sm-3 am-form-label">单价:</label>
							<div class="am-u-sm-9">
								<form:input path="price" 
									class="am-form-field am-radiu js-pattern-pinteger"
									maxLength="32" required="required" />
								<small>*必填项</small>
							</div>
						</div>

						<div class="am-form-group">
							<label class="am-u-sm-3 am-form-label">规格(单位):</label>
							<div class="am-u-sm-9">
								<form:input path="unit"
									class="form-control"
									maxLength="32" required="required" />
								<small>*必填项</small>
							</div>
						</div>

						<div class="am-form-group">
							<label for="user-phone" class="am-u-sm-3 am-form-label">编号:</label>
							<div class="am-u-sm-9">
								<form:input path="seq" 
									class="am-form-field am-radius js-pattern-pinteger"
									maxLength="32" required="required" />
								<small>*必填项</small>
							</div>
						</div>

						<div class="am-form-group">
							<label class="am-u-sm-3 am-form-label">存放位置:</label>
							<div class="am-u-sm-9">
								<form:input path="place"
									class="form-control"
									maxLength="32" required="required" />
								<small>*必填项</small>
							</div>
						</div>

						<hr>
						<div class="am-form-group">
							<div class="am-u-sm-9 am-u-sm-push-3">
								<button type="button" class="am-btn am-btn-danger"
									id="btn-commpany-asset-form-submit">保存</button>
								<button type="button" class="am-btn am-btn-success"
									id="btn-return">返回</button>
							</div>
						</div>
						
					</form:form>
				</div>
			</div>
		</div>
		<!-- content end -->

	</div>

	<a href="#"
		class="am-icon-btn am-icon-th-list am-show-sm-only admin-menu"
		data-am-offcanvas="{target: '#admin-offcanvas'}"></a>

	<!--footer start-->
	<%@ include file="../shared/pageFooter.jsp"%>
	<!--footer end-->

	<!-- js placed at the end of the document so the pages load faster -->
	<!--common script for all pages-->
	<%@ include file="../shared/importJs.jsp"%>

	<!--script for this page-->
	<script src="<c:url value='/assets/js/xcloud/common/validate-methods.js'/>" ></script>
	<script src="<c:url value='/assets/js/xcloud/xz/commpany-asset-form.js'/>"></script>
</body>
</html>
