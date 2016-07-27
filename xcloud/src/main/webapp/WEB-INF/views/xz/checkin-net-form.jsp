<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="../shared/taglib.jsp"%>
<html>
<head>
<!--common css for all pages-->
<%@ include file="../shared/importCss.jsp"%>
<!--css for this page-->
<link href="<c:url value='/assets/js/datetimepicker/amazeui.datetimepicker.css'/>" rel="stylesheet">
<link rel="stylesheet" href="<c:url value='/assets/js/chosen/amazeui.chosen.css'/> " />
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
					<strong class="am-text-primary am-text-lg">出勤地点</strong> / <small>出勤地理位置设定</small>
				</div>
			</div>
			<hr>
			<div class="am-g am-fl am-u-sm-8">
				<form:form modelAttribute="contentModel" method="POST" id="checkin-net-form" class="am-form am-form-horizontal">
					<input type="hidden" id="id" name="id" value="${id}" />
					<div class="am-form-group">
						<label for="user-phone" class="am-u-sm-3 am-form-label">出勤部门:</label>
						<div class="am-u-sm-9">
							<input type="hidden" id="selectedDeptIds" value="${contentModel.deptIds }" />
							<select id="deptIds" name="deptIds" data-placeholder="选择出勤部门" multiple class="am-form-field chosen-select " required>
								<option value="0">全体成员</option>
								<c:forEach items="${deptList}" var="d">
									<option value="${d.deptId }">${d.name }</option>
								</c:forEach>
							<select>
							<small></small>
						</div>
					</div>
					
					<div class="am-form-group">
						<label class="am-u-sm-3 am-form-label">出勤地点:<font color="red">*</font></label>
						<div class="am-u-sm-9">
							<form:hidden path="lat" />
							<form:hidden path="lng" />
							<form:hidden path="addr" />
							<form:hidden path="city" />
							<input type="text" id="suggestId" placeholder="${contentModel.addr}" value="${contentModel.addr}" class="am-form-field am-radius" minlength="1" />
							<div id="searchResultPanel" style="border: 1px solid #C0C0C0; width: 150px; height: auto; display: none;"></div>
							<br>
							<div style="width: 620px; height: 340px; border: 1px solid gray" id="containers"></div>
							<small></small>
						</div>
					</div>
					<div class="am-form-group am-form-inline">
						<label class="am-u-sm-3 am-form-label">有效范围(米):<font color="red">*</font></label>
						<div class="am-u-sm-9">
							<form:input path="distance" class="am-form-field am-radius js-pattern-pinteger" maxLength="4" size="5" required="required" />
							<small></small>
						</div>
					</div>
					<div class="am-form-group am-form-inline">
						<label class="am-u-sm-3 am-form-label">上班时间:<font color="red">*</font></label>
						<div class="am-u-sm-9">
							<div class="am-input-group date form_datetime form-datetime-lang" data-date="">
								<form:input path="startTime" class="am-form-field" size="5" readOnly="true"  />
								<span class="am-input-group-label add-on">
									<i class="icon-th am-icon-clock-o"></i>
								</span>
							</div>
						</div>
						<small></small>
					</div>
					<div class="am-form-group am-form-inline">
						<label class="am-u-sm-3 am-form-label">下班时间:<font color="red">*</font></label>
						<div class="am-u-sm-9">
							<div class="am-input-group date form_datetime form-datetime-lang" data-date="">
								<form:input path="endTime" class="am-form-field" size="5" readOnly="true" />
								<span class="am-input-group-label add-on">
									<i class="icon-th am-icon-clock-o"></i>
								</span>
							</div>
						</div>
						<small></small>
					</div>
					<div class="am-form-group">
						<label class="am-u-sm-3 am-form-label">弹性时间:<font color="red">*</font></label>
						<div class="am-u-sm-9">
							<form:input path="flexTime" class="am-form-field js-pattern-pinteger" size="5" maxLength="4" minlength="1"  />
							<small>比如规定上班时间为上午9:00，下班时间18:00，弹性时间为10分钟，则员工在9:10之前签到都不算迟到，在17:50之后签退都不算早退</small>
						</div>
					</div>
					<div class="am-form-group">
						<label class="am-u-sm-3 am-form-label">指定wifi:</label>
						<div class="am-u-sm-9">
							<form:input path="wifis" class="am-form-field" size="5" maxlength="255" />
							<small>输入wifi的名称，支持多个，用逗号','隔开</small>
						</div>
					</div>
					<hr>
					<div class="am-form-group">
						<div class="am-u-sm-9 am-u-sm-push-3">
							<button type="button" class="am-btn am-btn-danger" id="btn-save">保存</button>
							<button type="button" class="am-btn am-btn-success" id="btn-return">返回</button>
						</div>
					</div>
				</form:form>
			</div>
		</div>
		<!-- content end -->
	</div>
	<a href="#" class="am-icon-btn am-icon-th-list am-show-sm-only admin-menu"
		data-am-offcanvas="{target: '#admin-offcanvas'}"></a>
	<!--footer start-->
	<%@ include file="../shared/pageFooter.jsp"%>
	<!--footer end-->
	<!-- js placed at the end of the document so the pages load faster -->
	<!--common script for all pages-->
	<%@ include file="../shared/importJs.jsp"%>
	<!--script for this page-->
	<!-- 引入百度地图API,其中   申请的密钥   ak 和主机 ip绑定， -->
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=${ak}">
		
	</script>
	<script src="<c:url value='/assets/js/xcloud/common/baiduMap.js'/>"></script>
	<script src="<c:url value='/assets/js/xcloud/common/validate-methods.js'/>"></script>
	<script src="<c:url value='/assets/js/datetimepicker/amazeui.datetimepicker.min.js'/>"></script>
	<script src="<c:url value='/assets/js/datetimepicker/amazeui.datetimepicker.zh-CN.js'/>"></script>
	<script src="<c:url value='/assets/js/chosen/amazeui.chosen.min.js'/>"></script>
	<script src="<c:url value='/assets/js/xcloud/xz/checkin-net-form.js'/>"></script>
</body>
</html>
