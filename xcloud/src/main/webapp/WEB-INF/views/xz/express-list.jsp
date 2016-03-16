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
		<%@ include file="../xz/xz-menu.jsp"%>
		<!-- sidebar end -->

		<!-- content start -->
		<div class="admin-content">

			<div class="am-cf am-padding">
				<div class="am-fl am-cf">
					<strong class="am-text-primary am-text-lg">快递记录</strong> / <small>Express</small>
				</div>
			</div>
			<hr>
			<div class="am-g">
				<div class="am-u-sm-12 am-u-md-6">
					<div class="am-btn-toolbar">
						<div class="am-btn-group am-btn-group-sm">
							<button type="button" id="btn-express-add" class="am-btn am-btn-default am-radius">
								<span class="am-icon-plus"></span> 新增快递单
							</button>
							
							<button type="button" id="btn-staff-export" class="am-btn am-btn-default">
								<span class="am-icon-table"></span> 导出快递单
							</button>
							
						</div>
					</div>
				</div>

				<div class="am-u-sm-12 am-u-md-3">
					<!-- <form class="am-form" id="search-form"> -->
					 <form:form modelAttribute="contentModel" action="list" method="GET">
					<div class="am-input-group am-input-group-sm">
						<input type="text" id="express_no" name="express_no" class="am-form-field" placeholder="快递单号" maxLength="18" size=18> <span
							class="am-input-group-btn">
							<button class="am-btn am-btn-default" type="submit">搜索</button>
						</span>
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
									
									<th class="table-date">时间</th>
									<th class="table-title">快递服务商</th>
									<th class="table-type">快递单号</th>
									<th class="table-date">类型</th>
									<!-- <th class="table-date">费用</th> -->
									<th class="table-date">是否送达</th>
									<th class="table-date">是否结算</th>
									<!-- <th class="table-set">操作</th> -->
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${contentModel.list}" var="item">
									<tr>
										<td>${item.addTimeStr}</td>
										<td>${item.expressId}</td>
										<td>${item.expressNo}</td>
										<td class="am-hide-sm-only">${item.expressTypeName}</td>
										<td class="am-hide-sm-only">${item.isDoneName}</td>
										<td class="am-hide-sm-only">${item.isCloseName}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						

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
	<script src="<c:url value='/assets/js/xcloud/xz/express-list.js'/>"></script>
</body>
</html>
