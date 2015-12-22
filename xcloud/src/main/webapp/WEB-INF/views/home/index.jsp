<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ include file="../shared/taglib.jsp"%>

<html>
<head>

<!--common css for all pages-->
<%@ include file="../shared/importCss.jsp"%>

</head>

<body>
	<!--header start-->
	<%@ include file="../shared/pageSampleHeader.jsp"%>
	<!--header end-->

	<div class="tr-slider am-g">
		<div class="am-slider am-slider-default" id="slider1">
			<div class="am-hide-sm-only am-g  tr-slider-title am-text-center">
				<li style="display: none;">
					<h1 class="am-margin-top-sm">数据即财富·让云成为制造财富的引擎</h1>
					<p class="am-text-sm">构建云金融信息统一处理平台&nbsp;&nbsp;&nbsp;&nbsp;构建云金融信息安全平台&nbsp;&nbsp;&nbsp;&nbsp;构建云金融一站式财富管理平台</p>
				</li>
				<li style="display: list-item; opacity: 1;">
					<h1 class="am-margin-top-sm">云,企业数据汇集及存储平台</h1>
					<p class="am-text-sm">数据极速可达</p>
				</li>
			</div>

			<div style="overflow: hidden; position: relative;" class="am-viewport">
				<ul style="width: 800%; transition-duration: 0s; transform: translate3d(-2530px, 0px, 0px);" class="am-slides">
					<li style="width: 1265px; float: left; display: block;" aria-hidden="true" class="clone"><img
						draggable="false" src="<c:url value='/assets/img/slider-2.jpg'/>"></li>
					<li class="" style="width: 1265px; float: left; display: block;"><img draggable="false" src="<c:url value='/assets/img/slider-1.jpg'/>">
					</li>
					<li class="am-active-slide" style="width: 1265px; float: left; display: block;"><img draggable="false"
						src="<c:url value='/assets/img/slider-2.jpg'/>"></li>
					<li aria-hidden="true" class="clone" style="width: 1265px; float: left; display: block;"><img
						draggable="false" src="<c:url value='/assets/img/slider-1.jpg'/>"></li>
				</ul>
			</div>
			<ol class="am-control-nav am-control-paging">
				<li><a class="">1</a><i></i></li>
				<li><a class="am-active">2</a><i></i></li>
			</ol>
			<ul class="am-direction-nav">
				<li class="am-nav-prev"><a class="am-prev" href="#"> </a></li>
				<li class="am-nav-next"><a class="am-next" href="#"> </a></li>
			</ul>
		</div>
	</div>

	<div class="tr-main-container" id="home">
		<div class="am-container">
			<ul class="am-avg-md-4 am-avg-sm-1">
				<li class="am-padding-sm am-text-center">
					<p>
						<i class="am-icon-paper-plane am-icon-xl"></i>
					</p>
					<h2>极速调度</h2>
					<p class="am-text-sm">在泰然云，所有计算、存储、网络资源都是秒级响应。如果初始资源不够用，可以弹性扩展伸缩。不需要的资源可以随时销毁。随需应变，无需等待。</p>
				</li>
				<li class="am-padding-sm am-text-center">
					<p>
						<i class="am-icon-cloud am-icon-xl"></i>
					</p>
					<h2>云环境</h2>
					<p class="am-text-sm">通过 SDN 实现的虚拟路由器和交换机，您可以快速搭建属于自己的云环境，并提供 100% 的网络隔离，确保安全。</p>
				</li>
				<li class="am-padding-sm am-text-center">
					<p>
						<i class="am-icon-lock am-icon-xl"></i>
					</p>
					<h2>数据安全</h2>
					<p class="am-text-sm">私有网络提供100%二层隔离，在这个环境里，你的内部数据是非常安全的，黑客无法嗅探或者截获到你的数据。多重实时副本和备份可以保障即使在物理硬件彻底损坏时，数据也不会丢失，并且可以很快恢复业务。</p>
				</li>
				<li class="am-padding-sm am-text-center">
					<p>
						<i class="am-icon-database am-icon-xl"></i>
					</p>
					<h2>出众的磁盘性能</h2>
					<p class="am-text-sm">泰然云为您提供性能优异的块存储设备，可以达到或接近服务器物理硬盘的性能，完全能满足各主流数据库对磁盘的要求。同时，泰然云使用多重实时副本来保障数据安全，且至少包含一份异地副本，极大提高容灾能力。</p>
				</li>
			</ul>
			<span id="transmark"></span>
		</div>
		<div class="tr-slider am-g tr-slider2">
			<div class="tr-slider2-top am-text-center">
				<img src="<c:url value='/assets/img/slider2-top.png'/>" alt="云盘">
			</div>
			<div data-am-widget="slider" class="am-slider am-slider-a1 am-no-layout"
				data-am-slider="{animation:'fade',directionNav:false}">
				<ul class="am-slides">
					<li class=""
						style="width: 100%; float: left; margin-right: -100%; position: relative; opacity: 0; display: block; z-index: 1;">
						<img draggable="false" src="<c:url value='/assets/img/slider2-1.jpg'/>" alt="云化你的生活">
					</li>
					<li class="am-active-slide"
						style="width: 100%; float: left; margin-right: -100%; position: relative; opacity: 1; display: block; z-index: 2;">
						<img draggable="false" src="<c:url value='/assets/img/slider2-2.jpg'/>" alt="随时随地安全可控">
					</li>
				</ul>
				<ol class="am-control-nav am-control-paging">
					<li><a class="">1</a><i></i></li>
					<li><a class="am-active">2</a><i></i></li>
				</ol>
			</div>
		</div>
		<div class="tr-slider2-bottom am-g">
			<img src="<c:url value='/assets/img/slider2-bottom.png'/>" alt="云化你的生活">
		</div>
	</div>



	<!--footer start-->
	<%@ include file="../shared/pageFooter.jsp"%>
	<!--footer end-->



	<!-- js placed at the end of the document so the pages load faster -->
	<!--common script for all pages-->
	<%@ include file="../shared/importJs.jsp"%>

	<!--script for this page-->
	<script src="<c:url value='/assets/js/xcloud/home/index.js'/>"></script>
</body>
</html>
