<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="../shared/taglib.jsp"%>
<html>
<head>
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
					<strong class="am-text-primary am-text-lg">打卡设置</strong> / <small>check-in</small>
				</div>
				<div class="am-u-sm-12 am-u-md-3 am-fr">
					<div class="am-btn-toolbar am-fr">
						<div class="am-btn-group am-btn-group-sm ">
							<button type="button" id="btn-checkin-add" class="am-btn am-btn-warning am-radius">
								<span class="am-icon-plus"></span>
								添加打卡设置
							</button>
						</div>
					</div>
				</div>
			</div>
			<hr>
			<div class="am-g">
				<div class="am-u-sm-12">
					<form class="am-form">
						<table id="list-table" class="am-table am-table-bordered am-table-striped">
							<thead>
								<tr>
									<th class="table-title">出勤地点</th>
									<th class="table-title">范围</th>
									<th class="table-title">指定wifi</th>
									<th class="table-title">上下班时间</th>
									<th class="table-title">弹性时间</th>
									<th class="table-title">出勤部门</th>
									<th class="table-title">状态</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${contentModel}" var="item">
									<tr>
										<td>${item.addr}</td>
										<td>${item.distance}米</td>
										<td>${item.wifis}</td>
										<td>${item.startTime}-${item.endTime}</td>
										<td>${item.flexTime}分钟</td>
										<td>${item.deptNames}</td>
										<td>
											<c:if test="${item.status == 1 }">有效</c:if>
											<c:if test="${item.status == 0 }">无效</c:if>
										</td>
										<td>
											<div class="am-btn-toolbar">
												<div class="am-btn-group am-btn-group-xs">
													<a href="/xcloud/xz/checkin/netForm?id=${item.id }" class="am-icon-edit" title="编辑"></a>
														<a href="#" onClick="javascript:checkInNetDel(${item.id})" class="am-icon-remove" title="无效"></a>
												</div>
											</div>
										</td>
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
	<script src="<c:url value='/assets/js/xcloud/xz/checkin-net.js'/>"></script>
</body>
</html>
