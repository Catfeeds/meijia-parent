<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="../shared/taglib.jsp"%>
<html>
<head>
<!--common css for all pages-->
<%@ include file="../shared/importCss.jsp"%>
<!--css for this page-->
<link rel="stylesheet" href="<c:url value='/assets/js/chosen/amazeui.chosen.css'/> " />
</head>
<body>
	<!--header start-->
	<%@ include file="../shared/pageHeader.jsp"%>
	<!--header end-->
	<div class="am-cf admin-main">
		<!-- sidebar start -->
		<%@ include file="../schedule/schedule-menu.jsp"%>
		<!-- sidebar end -->
		<!-- content start -->
		<div class="admin-content">
			<div class="am-cf am-padding">
				<div class="am-fl am-cf">
					<strong class="am-text-primary am-text-lg">年终奖计算器</strong> / <small>2016版</small>
				</div>
			</div>
			<hr>
			<div class="am-g">
				<form id="taxForm" class="am-form am-form-horizontal" action="" method="POST">
					<input type="hidden" id="settingType" value="tax_year_award" />
					<div class="am-form-group">
						<label class="am-u-sm-4 am-form-label">年终奖金<font color="red">*</font></label>
						<div class="am-u-sm-8">
							<input id="money" name="money" maxLength="32" minlength="1" class="am-form-field am-radius js-pattern-price"
								required="required" type="text" value="" autocomplete="off" />
							<small></small>
						</div>
					</div>
					<hr>
					<div class="am-form-group">
						<div class="am-u-sm-9 am-u-sm-push-3">
							<button type="button" class="am-btn am-btn-danger" id="btn-do">计算</button>
							<button type="button" class="am-btn am-btn-success" id="btn-return">重置</button>
						</div>
					</div>
					<hr>
					<div class="am-form-group">
						<label class="am-u-sm-4 am-form-label">应缴税款</label>
						<div class="am-u-sm-8">
							<input id="taxNeed" name="taxNeed" maxLength="32" class="form-control" type="text" value="" />
							<small></small>
						</div>
					</div>
					<div class="am-form-group">
						<label for="user-phone" class="am-u-sm-4 am-form-label">税后收入</label>
						<div class="am-u-sm-8">
							<input id="taxedSalary" name="taxedSalary" maxLength="32" class="am-form-field am-radius" type="text" value="" />
							<small></small>
						</div>
					</div>
				</form>
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
	<script src="<c:url value='/assets/js/xcloud/common/common.js'/>"></script>
	<script src="<c:url value='/assets/js/xcloud/atools/tools-tax-year.js'/>"></script>
</body>
</html>
