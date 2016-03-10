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


		<!-- content start -->
		<div class="admin-content">
			<div class="am-cf am-padding">
				<div class="am-fl am-cf">
					<strong class="am-text-primary am-text-lg">通讯录</strong> / <small>批量导入</small>
				</div>
			</div>
			<hr>

			<div class="am-g">

				<div class="am-u-sm-12 am-u-md-4 am-u-md-push-8">
					<section class="am-panel am-panel-default"> <header class="am-panel-hd"> <img
						src="<c:url value='/assets/img/a1.png'/>" class="am-img-thumbnail am-circle" width="35"
						height="35"> 云小秘提示您 </header>
					<div class="am-panel-bd">可以扫码加入员工，快来试试吧</div>
					<div class="am-panel-bd">
						<img src="${xCompany.qrCode }" width="250" height="250" />
					</div>
					</section>
				</div>

				<div class="am-u-sm-12 am-u-md-8 am-u-md-pull-4">
					<form method="POST" id="staff-import-form" class="am-form am-form-horizontal"
						enctype="multipart/form-data">

						<div class="am-form-group">
							
							<div class="am-u-sm-9">
								步骤1:请先点此 <a href="/xcloud/attach/批量导入员工模板文件.xlsx">下载Excel模版</a> 并按模板填写员工信息
							</div>
						</div>

						<div class="am-form-group">
							
							<div class="am-u-sm-9">步骤1:请将填写好的Excel文档上传</div>
						</div>

						<div class="am-form-group">
							
							<div class="am-u-sm-9">
								<div class="am-form-group am-form-file">
									<button type="button" class="am-btn am-btn-danger am-btn-sm">
										<i class="am-icon-cloud-upload"></i> 选择要上传的文件
									</button>
									<input id="staff-file" name="staff-file" type="file"
										accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" required />
								</div>
								<div id="file-list"></div>
							</div>
						</div>



						<hr>
						<div class="am-form-group">
							<div class="am-u-sm-9 am-u-sm-push-3">
								<button type="submit" class="am-btn am-btn-danger">确定</button>
								<button type="button" class="am-btn am-btn-success" id="btn-return">返回</button>
							</div>
						</div>
					</form>
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
