<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="../shared/taglib.jsp"%>
<html>
<head>
<title>员工考勤列表</title>
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
					<strong class="am-text-primary am-text-lg">员工考勤明细表</strong>
				</div>
			</div>
			<hr>
			<div class="am-g">
				<div class="am-u-sm-12">
					<!-- <form class="am-form" id="search-form"> -->
					<form:form modelAttribute="searchModel" action="stat-list" method="GET" class="am-form-inline am-form-horizontal">
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
							<div class="am-input-group am-input-group-sm">
								<span class="am-input-group-btn">
									<button class="am-btn am-btn-default" type="submit">搜索</button>
								</span>
							</div>
						</div>
					</form:form>
				</div>
			</div>
			<br> <br>
			<div class="am-g">
				<div class="am-u-sm-12 am-scrollable-horizontal">
					<table class="am-table am-table-bordered am-table-centered">
						、
						<tr>
							<td rowspan="2" class="am-text-middle">序号</td>
							<td rowspan="2" class="am-text-middle">姓名</td>
							<td>日期</td>
							<c:forEach items="${viewDays}" var="day">
								<td>${day}</td>
							</c:forEach>
						</tr>
						<tr>
							<td>星期</td>
							<c:forEach items="${weeks}" var="w">
								<td>${w}</td>
							</c:forEach>
						</tr>
						<c:forEach items="${staffChekins}" var="checkin">
							<tr>
								<td rowspan="2" class="am-text-middle">${checkin.no }</td>
								<td rowspan="2" class="am-text-middle">${checkin.name }</td>
								<td>上午</td>
								<c:forEach items="${checkin.dataAm}" var="dataAm">
									<c:if test="${dataAm == '迟' }">
										<td><font color="red">${dataAm}</font></td>
									</c:if>
									<c:if test="${dataAm != '迟' }">
										<td>${dataAm}</td>
									</c:if>
								</c:forEach>
							</tr>
							<tr>
								<td>下午</td>
								<c:forEach items="${checkin.dataPm}" var="dataPm">
									<c:if test="${dataAm == '退' }">
										<td><font color="red">${dataPm}</font></td>
									</c:if>
									<c:if test="${dataAm != '退' }">
										<td>${dataPm}</td>
									</c:if>
								</c:forEach>
							</tr>
						</c:forEach>
					</table>
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
