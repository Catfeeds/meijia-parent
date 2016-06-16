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
		<%@ include file="../staffs/staff-menu.jsp"%>
		<!-- sidebar end -->

		<!-- content start -->
		<div class="admin-content">
			<div class="am-cf am-padding">
				<div class="am-fl am-cf">
					<strong class="am-text-primary am-text-lg">五险一金计算器</strong> / <small>insurance</small>
				</div>
			</div>
			<hr>

			<div class="am-g">

				<div class="am-u-sm-12 am-u-md-4 am-u-md-push-8">
					<section class="am-panel am-panel-default"> <header class="am-panel-hd"> <img
						src="<c:url value='/assets/img/a1.png'/>" class="am-img-thumbnail am-circle" width="35" height="35">
					云小秘提示您 </header>
					<div class="am-panel-bd">可以一键扫码预约团建，快来试试吧</div>
					<div class="am-panel-bd"><img src="<c:url value='/assets/img/erweima.png'/>" width="250" height="250" /></div>
					</section>
				</div>

				<div class="am-u-sm-12 am-u-md-8 am-u-md-pull-4">
					<form:form modelAttribute="" method="POST"  id="insuranceForm"
						class="am-form am-form-horizontal" enctype="multipart/form-data">
						
						
						<div class="am-form-group">
							<label class="am-u-sm-3 am-form-label">选择城市*</label>
							<div class="am-u-sm-9">
								<select class="chosen-select am-form-field am-radius" id="cityId" 
										required="required">
										<option value="2" label="北京"/>  
										<option value="74" label="上海"/>  
										<option value="198" label="广州"/>
										<option value="200" label="深圳"/>
								</select>
							</div>
						</div>
						
						<div class="am-form-group">
							<label class="am-u-sm-3 am-form-label">选择区县*</label>
							<div class="am-u-sm-9">
								<select class="chosen-select am-form-field am-radius" id="regionId"
										required="required">
								</select>
							</div>
						</div>
						
						
						<div class="am-form-group">
							<label class="am-u-sm-3 am-form-label">社保公积金基数:</label>
							<div class="am-u-sm-9">
								<input id="socialInsurance" 
									class="am-form-field am-radius js-pattern-pinteger"
									maxLength="5" minlength="4" required="required" />
								<small>*必填项,最低1890/最高19000</small>	
							</div>
						</div>
						
						<div class="am-form-group">
							<label  class="am-u-sm-3 am-form-label">公积金缴纳基数<:</label>
							<div class="am-u-sm-9">
								<input id="fundPrice" 
									class="am-form-field am-radius js-pattern-pinteger"
									maxLength="5" minlength="4" required="required" />
								<small>*必填项,最低1290/最高14000</small>	
							</div>
						</div>
						
						<div class="am-form-group">
							<div class="am-u-sm-9 am-u-sm-push-3">
								<button type="button" class="am-btn am-btn-danger" id="btn-calculate">计算</button>
								<button type="reset" class="am-btn am-btn-success" id="resetForm">重置</button>
							</div>
						</div>
						
						<div class="am-g">
							<div class="am-u-sm-12">
								<table id="list-table" class="am-table am-table-bordered am-table-striped">
									<thead>
										<tr>
											<th class="table-id">保险项</th>
											<th class="table-name">个人部分</th>
											<th class="table-name">单位部分</th>
										</tr>
									</thead>
									<tbody id="displayTBody">
										
										
									</tbody>
								  </table>
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
	<!-- 城市/区县联动 -->
	<script src="<c:url value='/assets/js/xcloud/common/get-region-by-city.js'/>"></script>
	
	<script src="<c:url value='/assets/js/xcloud/atools/insurance-form.js'/>"></script>
	
</body>
</html>
