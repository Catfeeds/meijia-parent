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

	<div class="am-cf admin-main">
		<!-- sidebar start -->
		<div class="admin-sidebar am-offcanvas" id="admin-offcanvas">

			<ul class="am-list admin-sidebar-list">
				<div class="am-cf am-padding">
					<div class="am-fl am-cf">
						<strong class="am-text-primary am-text-lg">扫码加入</strong>
					</div>

				</div>



				<li><img src="${xCompany.qrCode }" width="250" height="250" /></li>
			</ul>


		</div>
		<!-- sidebar end -->

		<!-- content start -->
		<div class="admin-content">
			<div class="am-cf am-padding">
				<div class="am-fl am-cf">
					<strong class="am-text-primary am-text-lg">员工批量导入</strong>


				</div>
				<div class="am-fr"></div>
			</div>

			<hr />

			<div class="am-tabs am-margin" data-am-tabs>

				<div class="am-tabs-bd">

					<div class="am-g am-margin-top">
						<div class="am-u-sm-2"></div>
						<div class="am-u-md-10">
							导入完成.
						</div>
					</div>

				</div>

				<div class="am-margin">
					
					<button type="button" class="am-btn am-btn-success" id="btn-return">返回</button>
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


</body>
</html>
