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
					<strong class="am-text-primary am-text-lg">会议设置</strong> / <small>meeting</small>
				</div>
			</div>

			<div class="am-tabs am-margin" data-am-tabs>
				<ul class="am-tabs-nav am-nav am-nav-tabs">
					<li class="am-active"><a href="#tab1">会议室</a></li>
					<li><a href="#tab2">会议类型</a></li>
				</ul>

				<div class="am-tabs-bd">
					<div class="am-tab-panel am-fade am-in am-active" id="tab1">
						<div class="am-g am-margin-top">
							<div class="am-u-sm-4 am-u-md-2 am-text-right">会议室名称</div>
							<div class="am-u-sm-8 am-u-md-10">
								<input type="input" id="meeting-name" name="meeting-name" value="" />
								<button type="button" class="am-btn am-btn-primary am-btn-xs">添加</button>
							</div>
						</div>

						<div class="am-g am-margin-top">
							<div class="am-u-sm-4 am-u-md-2 am-text-right">&nbsp;</div>
							<div class="am-u-sm-4 am-u-md-10">
							<table style="width:225px;" class="am-table am-table-bordered am-table-striped am-table-hover">
								<thead>
									<tr>
										<th>会议室名称</th>

									</tr>
								</thead>
								<tbody>
									<tr>
										<td>大会议室</td>

									</tr>
									<tr>
										<td>小会议室</td>

									</tr>
									<tr>
										<td>咖啡角</td>

									</tr>
									
								</tbody>
							</table>
							</div>
						</div>

					</div>

					<div class="am-tab-panel am-fade" id="tab2">
						<div class="am-g am-margin-top">
							<div class="am-u-sm-4 am-u-md-2 am-text-right">会议类型</div>
							<div class="am-u-sm-8 am-u-md-10">
								<input type="input" id="meeting-name" name="meeting-name" value="" />
								<button type="button" class="am-btn am-btn-primary am-btn-xs">添加</button>
							</div>
						</div>

						<div class="am-g am-margin-top">
							<div class="am-u-sm-4 am-u-md-2 am-text-right">&nbsp;</div>
							<div class="am-u-sm-4 am-u-md-10">
							<table style="width:225px;" class="am-table am-table-bordered am-table-striped am-table-hover">
								<thead>
									<tr>
										<th>会议类型</th>

									</tr>
								</thead>
								<tbody>
									<tr>
										<td>销售会议</td>

									</tr>
									<tr>
										<td>日程例会</td>

									</tr>
									<tr>
										<td>部门会议</td>

									</tr>
									
								</tbody>
							</table>
							</div>
						</div>

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

</body>
</html>
