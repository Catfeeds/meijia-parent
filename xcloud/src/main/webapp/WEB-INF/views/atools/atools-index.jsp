<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ include file="../shared/taglib.jsp"%>

<html>
<head>

<!--common css for all pages-->
<%@ include file="../shared/importCss.jsp"%>

<!--css for this page-->
<link rel="stylesheet" href="<c:url value='/css/fileinput.css'/>" type="text/css" />
<link href="<c:url value='/assets/js/zTree/css/awesomeStyle/awesome.css'/>"
	rel="stylesheet"
>
<link href="<c:url value='/assets/js/switch/amazeui.switch.css'/>" rel="stylesheet">	


</head>

<body>
	<!--header start-->
	<%@ include file="../shared/pageHeader.jsp"%>
	<!--header end-->

	<div class="am-cf admin-main">
		<!-- sidebar start -->
		<%@ include file="../atools/atools-menu.jsp"%>
		<!-- sidebar end -->

		<!-- content start -->
		<div class="admin-content">

			<div class="am-cf am-padding">
				<div class="am-fl am-cf">
					<strong class="am-text-primary am-text-lg">通讯录</strong> / <small>Contacts</small>
				</div>
			</div>
			<hr>
			<div class="am-g">
				<div class="am-u-sm-12">
					<form class="am-form">
						<table class="am-table">
							<thead>
								<tr>
									</th>
									<th class="table-name">应用</th>
									<th class="table-title">名称</th>
									<th class="table-title">状态</th>
									<th class="table-title">介绍</th>
									<th class="table-set">操作</th>
								</tr>
							</thead>
							<tbody>
							<c:forEach items="${contentModel.list}" var="item">
								<tr>
									
									<td><img src="${ item.logo }"/></td>
									<td>${item.name}</td>
									<td><c:choose>
									<c:when test="${item.status  == 0}">
														未启用
												</c:when>
									<c:when test="${item.status  == 1}">
														已启用
									</c:when>
							        </c:choose></td> 
									<td>${item.appDescribe}</td>
									<td>
									<button class="btn btn-info" onclick="btn_update('ordasasaser/order-scheduling?org_staff_id=22')">排班</button>
										<!-- <div class="am-btn-toolbar">
											<div class="am-btn-group am-btn-group-xs">
												<input id="alive" type="checkbox" checked data-on-color="success" >
											</div>
										</div> -->
										<!-- <div class="am-btn-toolbar">
											<div class="am-btn-group am-btn-group-xs">
												<button class="am-btn am-btn-default am-btn-xs am-text-secondary">
													<span class="am-icon-upload"></span> 启用
												</button>
												<button
													class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only"
												>
													<span class="am-icon-download"></span> 停用
												</button>
											</div>
										</div> -->
									</td>
								</tr>
								</c:forEach>
								<!-- <tr>
									<td><a href="#"><img
											src="https://static.dingtalk.com/media/lALOASopBszIzMg_200_200.png_450x10000q90.jpg"
											width="60" height="60"
										/></a></td>
									<td>饮用水管理</td>
									<td>已启用</td>
									<td class="am-hide-sm-only">企业可以通过手机移动考勤和实体考勤机相结合的方式，实现功能强大的企业考勤管理。</td>
									<td>
										<div class="am-btn-toolbar">
											<div class="am-btn-group am-btn-group-xs">
												<button class="am-btn am-btn-default am-btn-xs am-text-secondary">
													<span class="am-icon-upload"></span> 启用
												</button>
												<button
													class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only"
												>
													<span class="am-icon-download"></span> 停用
												</button>
											</div>
										</div>
									</td>
								</tr>
								<tr>
									<td><a href="#"><img
											src="https://static.dingtalk.com/media/lALOASoo-8zIzMg_200_200.png_450x10000q90.jpg"
											width="60" height="60"
										/></a></td>
									<td>绿植管理</td>
									<td>已停用</td>
									<td class="am-hide-sm-only">企业可以通过手机移动考勤和实体考勤机相结合的方式，实现功能强大的企业考勤管理。</td>
									<td>
										<div class="am-btn-toolbar">
											<div class="am-btn-group am-btn-group-xs">
												<input id="alive" type="checkbox" checked data-on-color="success" >
											</div>
										</div>
									</td>
								</tr>
								<tr>
									<td><a href="#"><img
											src="https://static.dingtalk.com/media/lALOASoo6MzIzMg_200_200.png_450x10000q90.jpg"
											width="60" height="60"
										/></a></td>
									<td>薪资管理</td>
									<td>即将上线</td>
									<td class="am-hide-sm-only">企业可以通过手机移动考勤和实体考勤机相结合的方式，实现功能强大的企业考勤管理。</td>
									<td>
										<div class="am-btn-toolbar">
											<div class="am-btn-group am-btn-group-xs">
												<button class="am-btn am-btn-default am-btn-xs am-text-secondary">
													<span class="am-icon-upload"></span> 启用
												</button>
												<button
													class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only"
												>
													<span class="am-icon-download"></span> 停用
												</button>
											</div>
										</div>
									</td>
								</tr> -->
							</tbody>
						</table>


					</form>
				</div>

			</div>
			<!-- content end -->

		</div>

		<a href="#" class="am-icon-btn am-icon-th-list am-show-sm-only admin-menu"
			data-am-offcanvas="{target: '#admin-offcanvas'}"
		></a>


		<!--footer start-->
		<%@ include file="../shared/pageFooter.jsp"%>
		<!--footer end-->

		<!-- js placed at the end of the document so the pages load faster -->
		<!--common script for all pages-->
		<%@ include file="../shared/importJs.jsp"%>

		<!--script for this page-->

		<script
			src="<c:url value='/assets/js/amazeui.datatables/amazeui.datatables.min.js'/>"
		></script>
		<script
			src="<c:url value='/assets/js/amazeui.datatables/dataTables.responsive.min.js'/>"
		></script>
		<script src="<c:url value='/assets/js/switch/amazeui.switch.min.js'/>"></script>
		<script src="<c:url value='/assets/js/xcloud/atools/index.js'/>"></script>
</body>
</html>
