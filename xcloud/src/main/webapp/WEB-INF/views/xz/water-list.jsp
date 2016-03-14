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
					<strong class="am-text-primary am-text-lg">送水列表</strong> / <small>drinking water</small>
				</div>
			</div>
			<hr>
			<div class="am-g">
				<div class="am-u-sm-12 am-u-md-6">
					<div class="am-btn-toolbar">
						<div class="am-btn-group am-btn-group-sm">
							<button type="button" id="btn-express-add" class="am-btn am-btn-default am-radius">
								<span class="am-icon-plus"></span> 一键下单
							</button>							
						</div>
					</div>
				</div>

			</div>
			<br>
				<div class="am-g">
				<div class="am-u-sm-12">
					<form class="am-form">
						<table id="list-table" class="am-table am-table-bordered am-table-striped">
							<thead>
								<tr>
									<!-- <th class="table-check"><input type="checkbox" name="select_all" id="select_all"
										value="1" /></th> -->
					<!-- <th>订单号</th>
					<th>下单时间</th>
					<th>用户手机号</th>
					<th>服务日期</th>
					<th>服务大类</th>
					<th>地址</th>
					<th>送水的数量</th>
					<th>订单状态</th>
					<th>订单总金额</th>
					<th>订单支付金额</th> -->
					
					
									<!-- <th class="table-name">订单号</th> -->
									<th class="table-date am-hide-sm-only">服务大类名称</th>
									<th class="table-set">送水的数量</th>
									<th class="table-id">地址</th>
									<th class="table-date am-hide-sm-only">订单状态</th>
									<th class="table-title">下单时间</th>
									<!-- <th class="table-type">用户手机号</th> -->
									<!-- <th class="table-author am-hide-sm-only">服务日期</th> -->
									<!-- <th class="table-id">订单总金额</th>
									<th class="table-set">订单支付金额</th> -->
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${contentModel.list}" var="item">
									<tr>
										<td>${item.serviceTypeName}</td>
										<td><a href="#" class="name-cell">${ item.serviceNum }</a></td>
										<td>${item.addrName}</td>
										<td class="am-hide-sm-only">${item.orderStatusName}</td>
										<td class="am-hide-sm-only">${item.addTimeStr}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<c:import url="../shared/paging.jsp">
							<c:param name="pageModelName" value="contentModel" />
							<c:param name="urlAddress" value="/xz/water/list" />
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
	<script src="<c:url value='/assets/js/xcloud/xz/express-list.js'/>"></script>
</body>
</html>
