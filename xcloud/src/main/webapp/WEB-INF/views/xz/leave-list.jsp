<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="../shared/taglib.jsp"%>
<html>
<head>
<title>请假列表</title>
<!--common css for all pages-->
<%@ include file="../shared/importCss.jsp"%>
<!--css for this page-->
<link href="<c:url value='/assets/js/amazeui.datatables/amazeui.datatables.min.css'/>" rel="stylesheet">
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
					<strong class="am-text-primary am-text-lg">请假列表</strong>
				</div>
			</div>
			<hr>
			<div class="am-g">
				<div class="am-u-sm-12">
					<!-- <form class="am-form" id="search-form"> -->
					<form:form modelAttribute="searchModel" action="list" method="GET" class="am-form-inline am-form-horizontal">
						<div class="am-form-group ">
							<form:select path="cyear" class="am-form-field">
								<form:options items="${selectYears}" />
							</form:select>
						</div>
						<div class="am-form-group">
							<form:select path="cmonth" class="am-form-field">
								<form:options items="${selectMonths}" />
							</form:select>
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
						<table id="list-table" class="am-table am-table-bordered am-table-striped">
							<thead>
								<tr>
									<th class="table-title">申请时间</th>
									<th class="table-title">员工</th>
									<th class="table-title">手机号</th>
									<th class="table-title">请假时间</th>
									<th class="table-title">请假天数</th>
									<th class="table-title">请假类型</th>
									<th class="table-title">状态</th>
									<th class="table-title">备注</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${listVo}" var="item">
									<tr>
										<td>${item.addTimeStr }</td>
										<td>${item.name}</td>
										<td>${item.mobile}</td>
										<td>${item.startDate} 至 ${item.endDate}</td>
										<td>${item.totalDays }</td>
										<td>${item.leaveTypeName}</td>
										<td>${item.statusName }</td>
										<td>${item.remarks }</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<c:import url="../shared/paging.jsp">
							<c:param name="pageModelName" value="contentModel" />
							<c:param name="urlAddress" value="/xz/leave/list" />
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
</body>
</html>
