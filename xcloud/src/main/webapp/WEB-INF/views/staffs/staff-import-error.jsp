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
						<div class="am-u-sm-2">导入错误</div>
						<div class="am-u-md-10"><font color="red">${errors}</font></div>
					</div>
				</div>

				<c:if test="${tableDatas != ''}">
					<div class="am-tabs-bd">
						<table id="error-table"
							class="am-table am-table-bordered dataTable no-footer" >
							<thead>
								<tr>
									
									<th width="5%" nowrap>姓名</th>
									<th width="5%" nowrap>手机</th>
									<th width="5%" nowrap>工号</th>
									<th width="10%" nowrap>职位</th>
									<th width="10%" nowrap>员工类型</th>
									<th width="10%" nowrap>身份证号</th>
									<th width="5%" nowrap>入职时间</th>
									<th width="10%">邮箱</th>
									<th width="5%" nowrap>行数</th>
									<th width="30%">错误信息</th>
								</tr>
							</thead>

						</table>

					</div>
				</c:if>
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

	<!--script for this page-->
	<script src="<c:url value='/assets/js/amazeui.datatables/amazeui.datatables.min.js'/>"></script>
	<script src="<c:url value='/assets/js/amazeui.datatables/dataTables.responsive.min.js'/>"></script>	
	<script src="<c:url value='/assets/js/xcloud/staffs/staff-import.js'/>"></script>
	
	<c:if test="${tableDatas != ''}">
	<script>
	$(function() {

		var data = ${tableDatas};

		$('#error-table').dataTable( {
			"lengthChange": false,
			searching: false,
		    ordering:  false,
		    data: data
		} );
	});
	</script>
	</c:if>
	
	
</body>
</html>
