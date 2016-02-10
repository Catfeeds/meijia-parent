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
					<li class="am-active"><a href="#meeting-room">会议室</a></li>
					<li><a href="#meeting-type" id="href-tab2">会议类型</a></li>
				</ul>

				<div class="am-tabs-bd">
					<div class="am-tab-panel am-fade am-in am-active" id="meeting-room">
						<div class="am-g am-margin-top">
							<div class="am-u-sm-4 am-u-md-2 am-text-right">会议室名称</div>
							<div class="am-u-sm-8 am-u-md-10">
								<form id="meeting-room-form" class="am-form am-form-horizontal"
									method="POST"
								>
									<div class="am-form-group am-input-group ">
										<input type="hidden" id="settingType" name="settingType"
											value="meeting-room"
										/> <input type="input" id="meeting-room-name" name="name" value=""
											class="am-form-field am-radius" maxLength="32" required="required"
											style="width: 50%"
										/> &nbsp;
										<button type="button" onclick="addSetting('meeting-room-form')"
											class="am-btn am-btn-primary"
										>添加</button>
									</div>
								</form>
							</div>
						</div>

						<div class="am-g am-margin-top">
							<div class="am-u-sm-4 am-u-md-2 am-text-right">&nbsp;</div>
							<div class="am-u-sm-4 am-u-md-10">

								<c:if test="${meetingRooms != ''}">

									<table style="width: 225px;"
										class="am-table am-table-bordered am-table-striped am-table-hover"
									>
										<thead>
											<tr>
												<th>会议室名称</th>
												<th>操作</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${meetingRooms}" var="item">
												<tr>
													<td>${item.name }</td>
													<td>
														<div class="am-btn-toolbar">
															<div class="am-btn-group am-btn-group-xs">
																删除

															</div>
														</div>
													</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</c:if>
							</div>
						</div>

					</div>

					<div class="am-tab-panel am-fade" id="meeting-type">
						<div class="am-g am-margin-top">
							<div class="am-u-sm-4 am-u-md-2 am-text-right">会议类型</div>
							<div class="am-u-sm-8 am-u-md-10">
								<form id="meeting-type-form" class="am-form am-form-horizontal"
									method="POST"
								>
									<div class="am-form-group am-input-group ">
										<input type="hidden" id="settingType" name="settingType"
											value="meeting-type"
										/> <input type="input" id="meeting-type-name" name="name" value=""
											class="am-form-field am-radius" maxLength="32"
											required="required" style="width: 50%"
										/> &nbsp; 
										<button type="button" onclick="addSetting('meeting-type-form')"
											class="am-btn am-btn-primary"
										>添加</button>
									</div>
							</div>
						</div>

						<div class="am-g am-margin-top">
							<div class="am-u-sm-4 am-u-md-2 am-text-right">&nbsp;</div>
							<div class="am-u-sm-4 am-u-md-10">
								<c:if test="${meettingTypes != ''}">
									<table style="width: 225px;"
										class="am-table am-table-bordered am-table-striped am-table-hover"
									>
										<thead>
											<tr>
												<th>会议类型</th>

											</tr>
										</thead>
										<tbody>
											<c:forEach items="${meettingTypes}" var="item">
												<tr>
													<td>${item.name }</td>

												</tr>
											</c:forEach>

										</tbody>
									</table>
								</c:if>
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
	<script src="<c:url value='/assets/js/xcloud/xz/meeting-setting.js'/>"></script>
</body>
</html>
