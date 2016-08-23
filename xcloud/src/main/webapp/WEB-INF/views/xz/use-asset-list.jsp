<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ include file="../shared/taglib.jsp"%>

<html>
<head>
<title>资产管理--领用与借用</title>

<!--common css for all pages-->
<%@ include file="../shared/importCss.jsp"%>
<!--css for this page-->
<link href="<c:url value='/assets/js/zTree/css/awesomeStyle/awesome.css'/>" rel="stylesheet">
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
					<strong class="am-text-primary am-text-lg">资产领用列表</strong>
				</div>
				
				<div class="am-u-sm-12 am-u-md-3 am-fr">
					<div class="am-btn-toolbar am-fr">
						<div class="am-btn-group am-btn-group-sm ">
							<button type="button" id="btn-use-asset-add" class="am-btn am-btn-warning am-radius">
								<span class="am-icon-plus"></span> 领用登记
							</button>
						</div>
					</div>
				</div>
			</div>
			<hr>

			<br>
			<div class="am-g">
				<div class="am-u-sm-12">
					<form class="am-form">
						<table id="list-table" class="am-table am-table-bordered am-table-striped">
							<thead>
								<tr>
									<th class="table-title">领用人名称</th>
									<th class="table-title">领用人手机号</th>
									<th class="table-title">物品名称/数量</th>
									<th class="table-title">用途</th>
									<th class="table-title">经手人姓名</th>
									<th class="table-title">经手人手机号</th>
									<th class="table-title">领用状态</th>
									<th class="table-date am-hide-sm-only">时间</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${contentModel.list}" var="item">
									<tr>
										<td>${item.name}</td>
										<td>${item.mobile}</td>
										<td>${item.assetNameAndNumStr }</td>
										<td>${item.purpose }</td>
										<td>${item.fromName }</td>
										<td>${item.fromMobile }</td>
										<td><c:if test="${item.status == 0 }">
												审批中	
											</c:if> <c:if test="${item.status == 1 }">
												已领用	
											</c:if></td>
										<td>${item.addTimeStr }</td>
										<td><a href="/xcloud/xz/assets/use_asset_form?id=${item.id }" class="am-icon-edit" title="编辑"></a></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<c:import url="../shared/paging.jsp">
							<c:param name="pageModelName" value="contentModel" />
							<c:param name="urlAddress" value="/xz/assets/use_asset_list" />
						</c:import>
					</form>
				</div>
			</div>
		</div>

	</div>

	</div>


	<!--footer start-->
	<%@ include file="../shared/pageFooter.jsp"%>
	<!--footer end-->

	<!-- js placed at the end of the document so the pages load faster -->
	<!--common script for all pages-->
	<%@ include file="../shared/importJs.jsp"%>

	<!--script for this page-->

	<script src="<c:url value='/assets/js/amazeui.datatables/amazeui.datatables.min.js'/>"></script>
	<script src="<c:url value='/assets/js/xcloud/xz/use-asset-list.js'/>"></script>
</body>
</html>
