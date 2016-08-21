<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="../shared/taglib.jsp"%>
<html>
<head>
<!--common css for all pages-->
<%@ include file="../shared/importCss.jsp"%>
<!--css for this page-->
<link href="<c:url value='/assets/js/zTree/css/awesomeStyle/awesome.css'/>" rel="stylesheet">
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
					<strong class="am-text-primary am-text-lg">职位管理</strong>
				</div>
				
				<div class="am-u-sm-12 am-u-md-3 am-fr">
					<div class="am-btn-toolbar am-fr">
						<div class="am-btn-group am-btn-group-sm ">
							<button type="button" id="btn-job-add" class="am-btn am-btn-warning am-radius">
								<span class="am-icon-plus"></span> 新增职位
							</button>
						</div>
					</div>
				</div>
			</div>
			
			<hr>
			<div class="am-g">
				<div class="am-u-sm-12">
					<form class="am-form">
						<table class="am-table am-table-bordered am-table-striped">
							<thead>
								<tr>
									<th class="table-title">职位名称</th>
									<th class="table-title">所属部门</th>
									<th class="table-title">规划人数</th>
									<th class="table-set">操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${jobListModel.list}" var="item">
										<tr>
											<td>${item.jobName}</td>
											<td>${item.deptName}</td>
											<td>${item.totalNum}</td> 
											<td><a href="#" onclick="getJobDetail(${item.jobId})" class="am-icon-search" title="查看"></a></td>
										</tr>
									</c:forEach>
							</tbody>
						</table>
						<c:import url="../shared/paging.jsp">
								<c:param name="pageModelName" value="jobListModel" />
								<c:param name="urlAddress" value="/job/job_list" />
						</c:import>
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
		<!-- js placed at the end of the document so the pages load faster -->
		<!--common script for all pages-->
		<%@ include file="../shared/importJs.jsp"%>
		<!--script for this page-->
		<script src="<c:url value='/assets/js/amazeui.datatables/amazeui.datatables.min.js'/>"></script>
		<script src="<c:url value='/assets/js/amazeui.datatables/dataTables.responsive.min.js'/>"></script>
		<script src="<c:url value='/assets/js/xcloud/staffs/job-list.js'/>"></script>
</body>
</html>
