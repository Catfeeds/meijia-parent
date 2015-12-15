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
	<%@ include file="../shared/pageHeader.jsp"%>
	<!--header end-->

	<div class="am-g ztlb_nr">
		<div class="am-u-sm-12">
			<div class="ztlb_nr_blockc">
				<div class="ztlb_nr_blockc_l">
					<img src="img/kj.png" alt="">
				</div>
				<div class="ztlb_nr_blockc_r">
					<div class="ztlb_nr_blockc_r_info">
						<div class="ztlb_nr_blockc_r_info_l">
							<div class="ztlb_nr_blockc_r_info_num">37</div>
							<div>收录</div>
						</div>
						<div class="ztlb_nr_blockc_r_info_r">
							<div class="ztlb_nr_blockc_r_info_num">182</div>
							<div>人气</div>
						</div>
					</div>
					<a class="ztlb_nr_blockc_r_title">布兜宠物 Budoupet Store</a> <span class="ztlb_nr_blockc_r_nr">宠物们表达爱意的方式，秀恩爱、拍写真、或拥抱、或接吻，玩得不亦乐乎。其实爱你，只要待在一起就感觉很幸福吧....</span>
					<div class="ztlb_nr_blockc_r_user">
						参与用户 <img src="img/kj.png" alt=""><img src="img/kj.png" alt=""><img src="img/kj.png" alt=""><img
							src="img/kj.png" alt=""><img src="img/kj.png" alt=""><img src="img/kj.png" alt=""><img
							src="img/kj.png" alt="">
					</div>
				</div>
			</div>
		</div>
	</div>

	<!--footer start-->
	<%@ include file="../shared/pageFooter.jsp"%>
	<!--footer end-->



	<!-- js placed at the end of the document so the pages load faster -->
	<!--common script for all pages-->
	<%@ include file="../shared/importJs.jsp"%>

	<!--script for this page-->
	<script src="<c:url value='/js/xcloud/home/index.js'/>"></script>
</body>
</html>
