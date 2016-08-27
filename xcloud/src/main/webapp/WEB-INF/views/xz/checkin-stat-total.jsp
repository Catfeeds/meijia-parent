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
		<%@ include file="../hr/hr-menu.jsp"%>
		<!-- sidebar end -->
		<!-- content start -->
		<div class="admin-content">
			<div class="am-cf am-padding">
				<div class="am-fl am-cf">
					<strong class="am-text-primary am-text-lg">考勤统计表</strong> / <small>按月查阅员工考勤汇总情况</small>
					
				</div>
				
				<div class="am-u-sm-12 am-u-md-3 am-fr">
					<div class="am-btn-toolbar am-fr">
						<div class="am-btn-group am-btn-group-sm ">
							<button type="button" id="btn-excel" onclick="exportTotal()" class="am-btn am-btn-success am-radius">
								<span class="am-icon-plus"></span> 导出员工考勤统计excel
							</button>
						</div>
					</div>
				</div>
			</div>
			<hr>
			<div class="am-g">
				<div class="am-u-sm-12">
					<!-- <form class="am-form" id="search-form"> -->
					<form:form modelAttribute="searchModel" action="stat-total" method="GET" class="am-form-inline am-form-horizontal">选择时间：
						<div class="am-form-group ">
							<form:select path="cyear" class="am-form-field">
								<form:options items="${selectYears}" />
							</form:select>年
						</div>
						<div class="am-form-group">
							<form:select path="cmonth" class="am-form-field">
								<form:options items="${selectMonths}" />
							</form:select>月
						</div>
						<div class="am-form-group">
							<div class="am-input-group am-input-group-sm">
								<span class="am-input-group-btn">
									<button class="am-btn am-btn-default" type="submit">查看</button>
								</span>
							</div>
						</div>
					</form:form>
				</div>
			</div>
			<br>
			<div class="am-g">
				<div class="am-u-sm-12 am-scrollable-horizontal">
					<table class="am-table am-table-bordered am-table-compact am-table-centered">
						
						<tr class="am-primary">
							<th rowspan="3" class="am-text-middle">序号</th>
							<th rowspan="3" class="am-text-middle">姓名</th>
							<th rowspan="3" class="am-text-middle">部门</th>
							<th colspan="13" >出勤记录</th>
							<th rowspan="3" class="am-text-middle">未打卡(次)</th>
							<th rowspan="3" class="am-text-middle">是否满勤</th>
						</tr>
						
						<tr class="am-primary">
							<th rowspan="2" class="am-text-middle">实际上班(天)</th>
							<th rowspan="2" class="am-text-middle">休息日</th>
							<th rowspan="2" class="am-text-middle">加班(天)</th>
							<th colspan="7" >请休假(天)</th>
							<th rowspan="2" class="am-text-middle">迟到(次)</th>
							<th rowspan="2" class="am-text-middle">早退(次)</th>
							<th rowspan="2" class="am-text-middle">旷工(天)</th>
						</tr>
						<tr class="am-primary">
							<th >事假</th>
							<th >病假</th>
							<th >婚假</th>
							<th >产假</th>
							<th >丧假</th>
							<th >年假</th>
							<th >其他</th>
						</tr>
						
						<c:forEach items="${staffTotalChekins}" var="item">
							<tr>
								<td>${item.no}</td>
								<td>${item.name}</td>
								<td>${item.deptName}</td>
								<td>${item.totalCheckinDays}</td>
								<td>${item.totalRestDays}</td>
								<td>${item.totalOverTimeDays}</td>
								<td>${item.totalLeavelType0}</td>
								<td>${item.totalLeavelType1}</td>
								<td>${item.totalLeavelType2}</td>
								<td>${item.totalLeavelType3}</td>
								<td>${item.totalLeavelType4}</td>
								<td>${item.totalLeavelType5}</td>
								<td>${item.totalLeavelType6}</td>
								<td>${item.totalLate}</td>
								<td>${item.totalEarly}</td>
								<td>${item.totalAbsence}</td>
								<td>${item.totalNoCheckin}</td>
								<td>${item.isAllCheckin}</td>

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
	
	<script>
	 	function exportTotal() {
	 		var cyear = $("#cyear").val();
	 		var cmonth = $("#cmonth").val();
		    var url="/xcloud/xz/checkin/export-total?cyear="+cyear+"&cmonth="+cmonth;
		    window.open(url);
		}
	</script>
</body>
</html>
