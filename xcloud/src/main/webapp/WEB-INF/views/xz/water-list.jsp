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
									<th class="table-date am-hide-sm-only">服务大类名称</th>
									<th class="table-title">商品名称</th>
									<th class="table-id">数量</th>
									<th class="table-title">地址</th>
									<th >订单状态</th>
									<th >是否签收</th>
									<th class="table-set">下单时间</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${contentModel.list}" var="item">
									<tr>
										<td>${item.serviceTypeName}</td>
										<td>${item.servicePriceName}</td>
										<td>${ item.serviceNum }桶</td>
										<td>${item.addrName}</td>
										<td class="am-hide-sm-only">${item.orderStatusName}</td>
										<td class="am-hide-sm-only">${item.orderExtStatusName}</td>
										<td class="am-hide-sm-only">${item.addTimeStr}</td>
										<c:if test="${item.orderExtStatus == 0 || item.orderExtStatus == 1 }">
										<td><button type="button" class="am-btn am-btn-success" id="qianshou_btn"
											onclick="waterSign(${item.userId},${item.orderId})">签&nbsp;&nbsp;&nbsp;&nbsp;收</button>
										</td>
										</c:if>
										<c:if test="${item.orderExtStatus == 2 }">
										<td><input type="button" class="am-btn am-btn-default" id="qianshou_btn" value="已签收"/>
										</td>
										</c:if>
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
	<script src="<c:url value='/assets/js/xcloud/xz/water-list.js'/>"></script>
</body>
</html>
