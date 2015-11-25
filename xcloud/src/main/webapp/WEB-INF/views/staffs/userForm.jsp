<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ include file="../shared/taglib.jsp"%>


<html>
<head>

<!--common css for all pages-->
<%@ include file="../shared/importCss.jsp"%>

<!--css for this page-->

<link rel="stylesheet"
	href="<c:url value='/js/vendor/zTree/css/awesomeStyle/awesome.css'/>"
	type="text/css">
<style type="text/css">
.ztree li span.button.switch.level0 {
	visibility: hidden;
	width: 1px;
}

.ztree li ul.level0 {
	padding: 0;
	background: none;
}
</style>

</head>

<body data-offset="250" data-target=".h5a-sidebar" data-spy="scroll">
	<!--header start-->
	<%@ include file="../shared/pageHeader.jsp"%>
	<!--header end-->
	</br>
	<div class="h5a-header" id="content">
		<div class="container h5a-container gray">
			<div class="row">

				<div class="col-sm-2">
					<div class="box hidden-print" style="height: 500px;">
						<div class="title">
							<h4>
								<div style="margin-top: -10px;">
									<a href="#">部门</a>
								</div>

							</h4>
							<span class="pull-right boxes_right" style="margin-top: -35px;">
								<button class="btn btn-danger " type="button">修改</button>
							</span>

						</div>
						<ul id="treeDemo" class="ztree"></ul>

					</div>
				</div>
				<div class="col-sm-8">
					<div class="box hidden-print" style="height: 500px;">

						<!-- 	<div class="form-group required">

							<label class="col-md-2 control-label">姓名</label>
							<div class="col-md-5">
								<div>
									<input class="form-control" type="text"
										placeholder="Text input">
								</div>
							</div>
						</div> -->
						<div class="form-group">
							<label for="exampleInputEmail1">姓名</label> <input
								id="exampleInputEmail1" class="form-control" type="text"
								placeholder="姓名">
						</div>
						<div class="form-group">
							<label for="exampleInputPassword1">昵称</label> <input
								id="exampleInputPassword1" class="form-control" type="text"
								placeholder="昵称">
						</div>
						<div class="form-group">
							<label for="exampleInputPassword1">手机号</label> <input
								id="exampleInputPassword1" class="form-control" type="text"
								placeholder="手机号">
						</div>
						<div class="form-group">
							<label for="exampleInputPassword1">身份证号</label> <input
								id="exampleInputPassword1" class="form-control" type="text"
								placeholder="身份证号">
						</div>
						<div class="form-group">
							<label for="exampleInputPassword1">个人简介</label>
							<textarea class="form-control" rows="3"></textarea>
						</div>

						<button class="btn btn-default" type="submit">Submit</button>


					</div>
				</div>
			</div>
		</div>





		<!-- js placed at the end of the document so the pages load faster -->
		<!--common script for all pages-->
		<%@ include file="../shared/importJs.jsp"%>

		<!--script for this page-->
		<script type="text/javascript"
			src="<c:url value='/js/vendor/zTree/js/jquery.ztree.core-3.5.js'/>"></script>

		<script type="text/javascript"
			src="<c:url value='/js/xcloud/staffs/staff-list.js'/>"></script>
</body>
</html>
