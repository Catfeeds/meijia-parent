<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ include file="../shared/taglib.jsp"%>

<html>
<head>

<!--common css for all pages-->
<%@ include file="../shared/importCss.jsp"%>

<!--css for this page-->

</head>

<body>
	<!--header start-->
	<%@ include file="../shared/pageHeader.jsp"%>
	<!--header end-->

	<div class="ch-container">
		<!--sidebar start-->
		<%@ include file="../shared/sidebarMenu.jsp"%>
		<!--sidebar end-->

		<div class=" row">
			<div class="col-md-3 col-sm-3 col-xs-6">
				<a data-toggle="tooltip" title="6 new members." class="well top-block" href="#"> <i
					class="glyphicon glyphicon-user blue"></i>

					<div>Total Members</div>
					<div>507</div> <span class="notification">6</span>
				</a>
			</div>

			<div class="col-md-3 col-sm-3 col-xs-6">
				<a data-toggle="tooltip" title="4 new pro members." class="well top-block" href="#"> <i
					class="glyphicon glyphicon-star green"></i>

					<div>Pro Members</div>
					<div>228</div> <span class="notification green">4</span>
				</a>
			</div>

			<div class="col-md-3 col-sm-3 col-xs-6">
				<a data-toggle="tooltip" title="$34 new sales." class="well top-block" href="#"> <i
					class="glyphicon glyphicon-shopping-cart yellow"></i>

					<div>Sales</div>
					<div>$13320</div> <span class="notification yellow">$34</span>
				</a>
			</div>

			<div class="col-md-3 col-sm-3 col-xs-6">
				<a data-toggle="tooltip" title="12 new messages." class="well top-block" href="#"> <i
					class="glyphicon glyphicon-envelope red"></i>

					<div>Messages</div>
					<div>25</div> <span class="notification red">12</span>
				</a>
			</div>
		</div>


	</div>


	<!-- js placed at the end of the document so the pages load faster -->
	<!--common script for all pages-->
	<%@ include file="../shared/importJs.jsp"%>

	<!--script for this page-->


</body>
</html>
