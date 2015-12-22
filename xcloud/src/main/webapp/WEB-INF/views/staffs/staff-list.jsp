<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ include file="../shared/taglib.jsp"%>

<html>
<head>

<!--common css for all pages-->
<%@ include file="../shared/importCss.jsp"%>

<!--css for this page-->

<link rel="stylesheet"
	href="<c:url value='/assets/js/zTree/css/awesomeStyle/awesome.css'/>"
	type="text/css">
<style type="text/css">
</style>

</head>

<body>
	<!--header start-->
	<%@ include file="../shared/pageHeader.jsp"%>
	<!--header end-->

	<div class="am-cf admin-main">
		<!-- sidebar start -->
		<div class="admin-sidebar am-offcanvas" id="admin-offcanvas">

			<ul class="am-list admin-sidebar-list">
				<li>
				<%@ include file="../shared/dept-tree.jsp"%>
				</li>
			</ul>


		</div>
		<!-- sidebar end -->

		<!-- content start -->
		<div class="admin-content">
			<div class="am-cf am-padding">
				<div class="am-fl am-cf">
					<strong class="am-text-primary am-text-lg">通讯录</strong>
				</div>
			</div>

			<hr />

			<div class="am-u-md-11">
				<div class="am-panel am-panel-default">
					<div class="am-panel-hd am-cf"
						data-am-collapse="{target: '#collapse-panel-2'}">
						浏览器统计<span class="am-icon-chevron-down am-fr"></span>
					</div>
					<div id="collapse-panel-2" class="am-in">
						<table
							class="am-table am-table-bd am-table-bdrs am-table-striped am-table-hover">
							<tbody>
								<tr>
									<th class="am-text-center">#</th>
									<th>浏览器</th>
									<th>访问量</th>
								</tr>
								<tr>
									<td class="am-text-center"><img
										src="assets/i/examples/admin-chrome.png" alt=""></td>
									<td>Google Chrome</td>
									<td>3,005</td>
								</tr>
								<tr>
									<td class="am-text-center"><img
										src="assets/i/examples/admin-firefox.png" alt=""></td>
									<td>Mozilla Firefox</td>
									<td>2,505</td>
								</tr>
								<tr>
									<td class="am-text-center"><img
										src="assets/i/examples/admin-ie.png" alt=""></td>
									<td>Internet Explorer</td>
									<td>1,405</td>
								</tr>
								<tr>
									<td class="am-text-center"><img
										src="assets/i/examples/admin-opera.png" alt=""></td>
									<td>Opera</td>
									<td>4,005</td>
								</tr>
								<tr>
									<td class="am-text-center"><img
										src="assets/i/examples/admin-safari.png" alt=""></td>
									<td>Safari</td>
									<td>505</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>

		</div>
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
	<script type="text/javascript"
		src="<c:url value='/assets/js/zTree/js/jquery.ztree.core-3.5.js'/>"></script>
	<script type="text/javascript"
		src="<c:url value='/assets/js/zTree/js/jquery.ztree.excheck-3.5.js'/>"></script>
	<script type="text/javascript"
		src="<c:url value='/assets/js/zTree/js/jquery.ztree.exedit-3.5.js'/>"></script>
	<script type="text/javascript"
		src="<c:url value='/js/xcloud/common/dept-tree.js'/>"></script>
	<script type="text/javascript"
		src="<c:url value='/js/xcloud/staffs/staff-list.js'/>"></script>
	<%-- <script type="text/javascript" src="<c:url value='/assets/bootstrap-datepicker/js/bootstrap-datepicker.min.js'/>"></script>
	    <script type="text/javascript" src="<c:url value='/assets/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js'/>"></script> --%>
	<%-- <script src="<c:url value='/assets/jquery-validation/dist/jquery.validate.min.js'/>" type="text/javascript"></script>
	    <script src="<c:url value='/js/vendor/jquery.dataTables.min.js'/>" type="text/javascript"></script> --%>
</body>
</html>
