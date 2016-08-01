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
					<br>
					<small>表格说明:</small>
					<br>
					<small>&nbsp;&nbsp;&nbsp;&nbsp;日勤--√;&nbsp;&nbsp;&nbsp;&nbsp;
						   迟到--迟;&nbsp;&nbsp;&nbsp;&nbsp;
						   早退--退;&nbsp;&nbsp;&nbsp;&nbsp;
						   旷工--旷
					</small>
					<br>
					<small>年休假--年;&nbsp;&nbsp;&nbsp;&nbsp;
						   事假--事;&nbsp;&nbsp;&nbsp;&nbsp;
						   病假--病;&nbsp;&nbsp;&nbsp;&nbsp;
						   婚假--婚;&nbsp;&nbsp;&nbsp;&nbsp;
						   产假--产;&nbsp;&nbsp;&nbsp;&nbsp;
						   丧假--丧;&nbsp;&nbsp;&nbsp;&nbsp;
						   其他--其;&nbsp;&nbsp;&nbsp;&nbsp;
						   周末--/;&nbsp;&nbsp;&nbsp;&nbsp;
						   节假日--/;&nbsp;&nbsp;&nbsp;&nbsp;
					</small>
				</div>
				
				<div class="am-u-sm-12 am-u-md-3 am-fr">
					<div class="am-btn-toolbar am-fr">
						<div class="am-btn-group am-btn-group-sm ">
							<button type="button" id="btn-excel" class="am-btn am-btn-success am-radius">
								<span class="am-icon-plus"></span> 导出excel
							</button>
						</div>
					</div>
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
			<br>
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
									<c:choose>
										<c:when test="${dataAm.status == '√' }">   
											<td><a href="/xcloud/xz/checkin/list?selectDay=${dataAm.cday}&userId=${checkin.userId}">${dataAm.status}</a></td>
  										</c:when>
  										<c:when test="${dataAm.leaveId > 0 }">   
											<td><a href="/xcloud/xz/leave/list?selectDay=${dataAm.cday}&userId=${checkin.userId}">${dataAm.status}</a></td>
  										</c:when>
										<c:otherwise>  
											<td><a href="/xcloud/xz/checkin/list?selectDay=${dataAm.cday}&userId=${checkin.userId}">
												<font color="red">${dataAm.status}</font>
											</a></td>
  										
  										</c:otherwise>
									</c:choose>
									
								</c:forEach>
							</tr>
							<tr>
								<td>下午</td>
								<c:forEach items="${checkin.dataPm}" var="dataPm">
									<c:choose>
										<c:when test="${dataPm.status == '√' }">   
											<td><a href="/xcloud/xz/checkin/list?selectDay=${dataPm.cday}&userId=${checkin.userId}">${dataPm.status}</a></td>
  										</c:when>
  										<c:when test="${dataPm.leaveId > 0 }">   
											<td><a href="/xcloud/xz/leave/list?selectDay=${dataPm.cday}&userId=${checkin.userId}">${dataPm.status}</a></td>
  										</c:when>
										<c:otherwise>  
											<td><a href="/xcloud/xz/checkin/list?selectDay=${dataPm.cday}&userId=${checkin.userId}">
												<font color="red">${dataPm.status}</font>
											</a></td>
  										
  										</c:otherwise>
									</c:choose>
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
