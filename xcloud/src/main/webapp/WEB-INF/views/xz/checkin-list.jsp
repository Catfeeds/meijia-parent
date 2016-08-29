<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="../shared/taglib.jsp"%>
<html>
<head>
<title>考勤列表</title>
<!--common css for all pages-->
<%@ include file="../shared/importCss.jsp"%>
<!--css for this page-->
<link href="<c:url value='/assets/js/amazeui.datatables/amazeui.datatables.min.css'/>" rel="stylesheet">
<link href="<c:url value='/assets/js/datetimepicker/amazeui.datetimepicker.css'/>" rel="stylesheet">
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
					<strong class="am-text-primary am-text-lg">考勤日志查询</strong> / <small>按条件查询考勤日志情况</small>
				</div>
			</div>
			<hr>
			<div class="am-g">
				<div class="am-u-sm-12">
					<!-- <form class="am-form" id="search-form"> -->
					<form:form modelAttribute="searchModel" action="list" method="GET" class="am-form-inline am-form-horizontal">
						<div class="am-form-group ">时间：
							<div class="am-input-group date form_datetime form-datetime-lang"
									data-date="">
									<input type="text" id="selectDay" name="selectDay" size="16"  value="${searchModel.selectDay }" class="am-form-field" readonly minlength="1" maxlength="20">
									<span class="am-input-group-label add-on"><i class="icon-th am-icon-calendar"></i></span>
							</div>
						</div>
						<div class="am-form-group">
							<form:input path="mobile" placeholder="手机号" class="am-form-field"/>
						</div>
						<div class="am-form-group">
							<div class="am-input-group am-input-group-sm">
								<span class="am-input-group-btn">
									<button class="am-btn am-btn-default" type="submit">搜索</button>
								</span>
							</div>
						</div>
					</form:form>
				</div>
			</div>
			<br>
			<div class="am-g">
				<div class="am-u-sm-12">
					<form class="am-form">
						<table id="list-table" class="am-table am-table-bordered am-table-striped am-table-centered">
							<thead>
								<tr class="am-primary">
									<th class="table-title">时间</th>
									<th class="table-title">员工</th>
									<th class="table-title">手机号</th>
									<th class="table-title">签到位置</th>
									<th class="table-title">签到网络</th>
									<th class="table-title">匹配出勤地点</th>
									<th class="table-title">距离(米)</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${contentModel.list}" var="item">
									<tr>
										<td>${item.addTimeStr }</td>
										<td>${item.name}</td>
										<td>${item.mobile}</td>
										<td>${item.poiName }</td>
										<td>${item.checkinNet}</td>
										<td>${item.settingName }</td>
										<td>${item.poiDistance }</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<c:import url="../shared/paging.jsp">
							<c:param name="pageModelName" value="contentModel" />
							<c:param name="urlAddress" value="/xz/checkin/list" />
						</c:import>
					</form>
				</div>
			</div>
		</div>
	</div>
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
	<script src="<c:url value='/assets/js/amazeui.datatables/amazeui.datatables.min.js'/>"></script>
	<script src="<c:url value='/assets/js/amazeui.datatables/dataTables.responsive.min.js'/>"></script>
	<script src="<c:url value='/assets/js/datetimepicker/amazeui.datetimepicker.min.js'/>"></script>
	<script src="<c:url value='/assets/js/datetimepicker/amazeui.datetimepicker.zh-CN.js'/>"></script>
	<script src="<c:url value='/assets/js/xcloud/xz/checkin-list.js'/>"></script>
</body>
</html>
