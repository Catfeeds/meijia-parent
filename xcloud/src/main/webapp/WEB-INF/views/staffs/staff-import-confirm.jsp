<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="../shared/taglib.jsp"%>
<html>
<head>
<!--common css for all pages-->
<%@ include file="../shared/importCss.jsp"%>
<!--css for this page-->
<link href="<c:url value='/assets/js/amazeui.datatables/amazeui.datatables.min.css'/>" rel="stylesheet">
</head>
<body>
	<!--header start-->
	<%@ include file="../shared/pageHeader.jsp"%>
	<!--header end-->
	<div class="am-cf admin-main">
		<!-- sidebar start -->
		<%@ include file="../staffs/staff-menu.jsp"%>
		<!-- sidebar end -->
		<!-- content start -->
		<div class="admin-content">
			<div class="am-cf am-padding">
				<div class="am-fl am-cf">
					<strong class="am-text-primary am-text-lg">通讯录</strong> / <small>批量导入</small>
				</div>
			</div>
			<hr>
			<div class="am-g">
				<form method="POST" id="staff-import-form" action="/xcloud/staff/staff-import-do"
					class="am-form am-container am-padding-xl am-padding-bottom">
					<input type="hidden" name="newFileName" value="${newFileName}"
						<div class="am-u-sm-12">
					<font color="red">导入确认:</font>
					本次导入，新增<font color="red">${totalNewCount}</font>条，更新<font color="red">${totalUpdateCount}</font>条.
					<c:if test="${tableDatas != ''}">
							以下为更新的列表:
					</c:if>
				</div>
						<c:if test="${tableDatas != ''}">
						<div class="am-u-sm-12" >
						<table id="confirm-table" class="am-table am-table-bordered dataTable no-footer">
							<thead>
								<tr>
									<th class="table-name" nowrap>姓名</th>
									<th class="table-author am-hide-sm-only">手机号</th>
									<th class="table-id" nowrap>工号</th>
									<th class="table-type" nowrap>职位</th>
									<th class="table-title">员工类型</th>
									<th class="table-title">身份证号</th>
									<th class="table-title">入职时间</th>
									<th class="table-date am-hide-sm-only">邮箱</th>
									<th class="table-id" nowrap>行数</th>
								</tr>
							</thead>
							

						</table>

				</c:if>
						<hr>
				<div class="am-u-sm-12">
					<button type="submit" class="am-btn am-btn-danger">确认导入</button>
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
	<script src="<c:url value='/assets/js/amazeui.datatables/amazeui.datatables.min.js'/>"></script>
	<script src="<c:url value='/assets/js/amazeui.datatables/dataTables.responsive.min.js'/>"></script>
	<script src="<c:url value='/assets/js/xcloud/staffs/staff-import.js'/>"></script>
	<c:if test="${tableDatas != ''}">
		<script>
			$(function() {
				
				var data = ${tableDatas};
				
				$('#confirm-table').dataTable({
					"lengthChange" : false,
					searching : false,
					ordering : false,
					data : data
				});
			});
		</script>
	</c:if>
</body>
</html>
