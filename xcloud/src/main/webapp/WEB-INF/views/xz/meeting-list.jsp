<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ include file="../shared/taglib.jsp"%>

<html>
<head>

<!--common css for all pages-->
<%@ include file="../shared/importCss.jsp"%>

<!--css for this page-->

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
		<%@ include file="../xz/xz-menu.jsp"%>
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

	<!--footer start-->
	<%@ include file="../shared/pageFooter.jsp"%>
	<!--footer end-->

	<!-- js placed at the end of the document so the pages load faster -->
	<!--common script for all pages-->
	<%@ include file="../shared/importJs.jsp"%>

	<!--script for this page-->

	<script src="<c:url value='/assets/js/calendar/fullcalendar.min.js'/>"></script>
	<script src="<c:url value='/assets/js/calendar/lang/zh-cn.js'/>"></script>
	<script src="<c:url value='/assets/js/xcloud/xz/meeting-list.js'/>"></script>
</body>
</html>
