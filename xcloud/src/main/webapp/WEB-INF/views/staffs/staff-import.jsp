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
						<div class="am-u-sm-2">步骤1:</div>
						<div class="am-u-md-10">
							请先点此<a href="">下载Excel模版</a>并按模板填写员工信息
						</div>
					</div>

					<div class="am-g am-margin-top ">
						<div class="am-u-sm-2">步骤2:</div>
						<div class="am-u-md-10">请将填写好的Excel文档上传</div>
					</div>

					<div class="am-g am-margin-top ">
						<div class="am-u-sm-2"></div>
						<div class="am-u-md-10">
							<div class="am-form-group am-form-file">
								<button type="button" class="am-btn am-btn-danger am-btn-sm">
									<i class="am-icon-cloud-upload"></i> 选择要上传的文件
								</button>
								<input id="doc-form-file" type="file" multiple>
							</div>
							<div id="file-list"></div>
						</div>
					</div>
					<br />

				</div>

				<div class="am-margin">
					<button type="button" class="am-btn am-btn-danger" id="btn-import-submit">确定</button>
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

	<!--script for this page-->
	<script src="<c:url value='/assets/js/xcloud/staffs/staff-import.js'/>"></script>
</body>
</html>
