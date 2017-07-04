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
<%-- <link rel="stylesheet" href="<c:url value='/css/fileinput.css'/>" type="text/css" /> --%>
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
					<strong class="am-text-primary am-text-lg">我的应用</strong> / <small>选择自己所需的应用功能</small>
				</div>
			</div>
			<hr>
			<div class="am-g">
				<div class="am-u-sm-1">
				</div>
				<div class="am-u-sm-11">
					<form class="am-form">
						<table class="am-table">
							<thead>
								<tr>
									<th class="table-name">应用</th>
									<th class="table-title">名称</th>
									<th class="table-title">介绍</th>
									<th class="table-set">操作</th>
								</tr>
							</thead>
							<tbody>
							<c:forEach items="${contentModel}" var="item">
								<tr>
									
									<td><img width="40" height="40" src="${ item.logo }"/></td>
									<td>${item.name}</td>
									
									<td>${item.appDescribe}</td>
									<td>
									<c:if test="${item.status == null && item.isDefault == 0 }">
									<%-- <button class="btn btn-info" onclick="btn_update('/atools/user_app_tools_oa?t_id=${item.tId}&status=1&user_id=${item.userId}')">添加</button>
									 --%><a href ="/xcloud/atools/user_app_tools_oa?t_id=${item.tId}&status=1&user_id=${item.userId}">添加</a>
									</c:if>
									<c:if test="${item.status == null && item.isDefault == 1 && item.isDel == 0}">
									<a href ="/xcloud/atools/user_app_tools_oa?t_id=${item.tId}&status=0&user_id=${item.userId}">取消</a>
									<%-- <button class="btn btn-info" onclick="btn_update('/atools/user_app_tools_oa?t_id=${item.tId}&status=0&user_id=${item.userId}')">取消</button> --%>
									</c:if>
									<c:if test="${item.status == null && item.isDefault == 1 && item.isDel == 1}">
									<!-- <button class="btn btn-info" onclick="btn_update()">已添加</button> -->
									已添加
									</c:if>
									<c:if test="${item.status ==0 }">
									<a href ="/xcloud/atools/user_app_tools_oa?t_id=${item.tId}&status=1&user_id=${item.userId}">添加</a>
									<%-- <button class="btn btn-info" onclick="btn_update('/atools/user_app_tools_oa?t_id=${item.tId}&status=1&user_id=${item.userId}')">添加</button> --%>
									</c:if>
									<c:if test="${item.status ==1 }">
									<%-- <button class="btn btn-info" onclick="btn_update('/atools/user_app_tools_oa?t_id=${item.tId}&status=0&user_id=${item.userId}')">取消</button>
									 --%>
									 <a href ="/xcloud/atools/user_app_tools_oa?t_id=${item.tId}&status=0&user_id=${item.userId}">取消</a>
									
									</c:if>
									</td>
								</tr>
								</c:forEach>
								
							</tbody>
						</table>
					</form>
				</div>

			</div>
			<!-- content end -->

		</div>
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
