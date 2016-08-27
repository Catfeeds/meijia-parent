<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ include file="../shared/taglib.jsp"%>
<html>
<head>

<!--common css for all pages-->
<%@ include file="../shared/importCss.jsp"%>

<!--css for this page-->

<link href="<c:url value='/assets/js/zTree/css/awesomeStyle/awesome.css'/>" rel="stylesheet">
<link href="<c:url value='/assets/js/amazeui.datatables/amazeui.datatables.min.css'/>"
	rel="stylesheet">

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
					<strong class="am-text-primary am-text-lg">悬赏职位列表</strong> / <small>内推悬赏职位信息一览</small>
				</div>
				<div class="am-btn-toolbar am-fr">
						<div class="am-btn-group am-btn-group-sm">
							<button type="button" id="btn-job-add" class="am-btn am-btn-warning am-radius">
								<span class="am-icon-plus"></span> 发布悬赏需求
							</button>							
						</div>
				</div>
			</div>
			<hr>
			<div class="am-g">
				<div class="am-u-sm-12">
					<form class="am-form">
						<table id="list-table" class="am-table am-table-bordered am-table-striped am-table-centered">
							<thead>
								<tr>
								    <th class="table-title">发布者</th>
									<th class="table-title">城市</th>
									<th class="table-title">赏金(元)</th>
									<th class="table-title">截止天数</th>
									<th class="table-title">截止日期</th>
									<th class="table-title">标题</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${jobHunterModel.list}" var="item">
									<tr>
										<td>${item.name}</td>
										<td>${item.cityName}</td>
										<td>${item.reward}</td>
										<td>${item.limitDayStr}</td>
										<td>${item.endTimeStr }</td>
										<td>${item.title}</td>
										<td>
											<button type="button" class="am-btn am-btn-success" 
											
											onclick="getJobDetail(${item.id})">查看详情</button>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<c:import url="../shared/paging.jsp">
							<c:param name="pageModelName" value="jobHunterModel" />
							<c:param name="urlAddress" value="/hr/hunter/job_publish_list" />
						</c:import>
					</form>
				</div>
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

	<script src="<c:url value='/assets/js/amazeui.datatables/amazeui.datatables.min.js'/>"></script>
	<script src="<c:url value='/assets/js/amazeui.datatables/dataTables.responsive.min.js'/>"></script>
	<script src="<c:url value='/assets/js/xcloud/hr/job-hunter-list.js'/>"></script>
</body>
</html>
