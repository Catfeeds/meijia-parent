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
		<%@ include file="../atools/atools-menu.jsp"%>
		<!-- sidebar end -->

		<!-- content start -->
		<div class="admin-content">
			<div class="am-cf am-padding">
				<div class="am-fl am-cf">
					<strong class="am-text-primary am-text-lg">应用需求悬赏</strong> / <small>Application Service</small>
				</div>
			</div>
			<hr>
				
			<div class="am-g">
				<div class="am-u-sm-12 am-u-md-4 am-u-md-push-8">
					<section class="am-panel am-panel-default"> <header class="am-panel-hd"> <img
						src="/xcloud/assets/img/a1.png" class="am-img-thumbnail am-circle" width="35" height="35"> 云小秘提示您
					</header>
					<div class="am-panel-bd">不论是“菠萝HR”手机APP，还是人事行政管理平台，欢迎吐槽哦~~扫码下载试试吧</div>
					<div class="am-panel-bd"><center><img src="/xcloud/assets/img/erweima.png" width="200" height="200" /></center></div>
					</section>
				</div>
				<div class="am-u-sm-12 am-u-md-8 am-u-md-pull-4">
					专注于“人事行政”是我们产品与服务的核心理念，希望“菠萝HR”手机APP和人事行政管理平台，在为您和企业提供帮助的同时，能听到您的反馈和建议。我们热切希望得到大家的点评与吐槽，若有好的意见建议，或应用需求的提议，我们都将表示感谢。对于最终采纳的应用功能建议，且开发上线的相关功能，我们将给予重谢！<br><br><br>
					产品经理邮箱：pm@bolohr.com，欢迎各路大侠指点！<br>
					您还可关注我们的微信、微博，搜索“菠萝HR”即可，产品经理在此有礼了~~
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
