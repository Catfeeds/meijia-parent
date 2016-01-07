<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ include file="../shared/taglib.jsp"%>

<html>
<head>

<!--common css for all pages-->
<%@ include file="../shared/importCss.jsp"%>

<!--css for this page-->

<link href="<c:url value='/assets/js/zTree/css/awesomeStyle/awesome.css'/>" rel="stylesheet">
<link href="<c:url value='/assets/js/calendar/lib/cupertino/jquery-ui.min.css'/>" rel="stylesheet">
<link href="<c:url value='/assets/js/calendar/fullcalendar.min.css'/>" rel="stylesheet">
<link href="<c:url value='/assets/js/calendar/fullcalendar.print.css'/>" media='print'>
</head>

<body>
	<!--header start-->
	<%@ include file="../shared/pageHeader.jsp"%>
	<!--header end-->

	<div class="am-cf admin-main">
		<!-- sidebar start -->
		<div class="admin-sidebar am-offcanvas" id="admin-offcanvas">
			<div class="am-offcanvas-bar admin-offcanvas-bar">
				<ul class="am-list admin-sidebar-list">
					<li><a href="/xcloud/schedule/list"><span class="am-icon-tags"></span> 日程管理</a></li>
					<li class="admin-parent"><a class="am-cf" data-am-collapse="{target: '#collapse-nav'}"><span
							class="am-icon-file"></span> 工作卡片 <span class="am-icon-angle-right am-fr am-margin-right"></span></a>
						<ul class="am-list am-collapse admin-sidebar-sub am-in" id="collapse-nav">
							<li><a href="/xcloud/schedule/card-form?card_type=2"><span class="am-icon-th"></span>
									通知公告<span class="am-badge am-badge-warning am-margin-right am-fr">24</span></a></li>
							<li><a href="/xcloud/schedule/card-form?card_type=3" class="am-cf"><span
									class="am-icon-bell"></span> 事务提醒<span
									class="am-icon-star am-fr am-margin-right admin-icon-yellow"></span></a></li>
							<li><a href="/xcloud/schedule/card-form?card_type=1"><span class="am-icon-users"></span>
									会议安排</a></li>
							<li><a href="/xcloud/schedule/card-form?card_type=4"><span class="am-icon-calendar"></span>
									面试邀约</a></li>
							<li><a href="/xcloud/schedule/card-form?card_type=5"><span class="am-icon-plane"></span>
									差旅规划</a></li>
						</ul></li>
					<!-- <li><a href="admin-table.html"><span class="am-icon-tags"></span> 卡片商店</a></li> -->

				</ul>

				<div class="am-panel am-panel-default admin-sidebar-panel">
					<div class="am-panel-bd">
						<p>
							<span class="am-icon-bookmark"></span> 最新公告
						</p>
						<p>新年将至，公司年会将在月球举办，点击查看详情。</p>
					</div>
				</div>
			</div>
		</div>
		<!-- sidebar end -->

		<!-- content start -->
		<div class="admin-content">
			<div class="am-cf am-padding">
				<!-- 日历 -->
				<div id='calendar'></div>
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

	<script src="<c:url value='/assets/js/calendar/fullcalendar.min.js'/>"></script>
	<script src="<c:url value='/assets/js/calendar/lang/zh-cn.js'/>"></script>
	<script src="<c:url value='/assets/js/xcloud/schedule/list.js'/>"></script>
</body>
</html>
