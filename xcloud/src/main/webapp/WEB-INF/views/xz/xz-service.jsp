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
					<strong class="am-text-primary am-text-lg">找服务商</strong> / <small>对比挑选最佳的服务供应商</small>
				</div>
			</div>
			<hr>
				
			<div class="am-g">
				<div class="am-u-sm-12 am-u-md-4 am-u-md-push-8">
					<section class="am-panel am-panel-default"> <header class="am-panel-hd"> <img
						src="/xcloud/assets/img/a1.png" class="am-img-thumbnail am-circle" width="35" height="35"> 菠萝小秘提示您
					</header>
					<div class="am-panel-bd">使用“菠萝HR”手机APP，各类企业服务商一应俱全，一呼百应哦~~扫码下载试试吧</div>
					<div class="am-panel-bd"><center><img src="/xcloud/assets/img/erweima.png" width="200" height="200" /></center></div>
					</section>
				</div>
				<div class="am-u-sm-12 am-u-md-8 am-u-md-pull-4">
					<center><img src="/xcloud/assets/img/findservice.jpg" width="60%" /></center>
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

</body>
</html>
