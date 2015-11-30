<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ include file="../shared/taglib.jsp"%>
<html>
<head>

<header class="navbar navbar-fixed-top">
<div class="container top-header">
	<div class="navbar-header">
		<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
			<span class="icon icon-bar"></span> <span class="icon icon-bar"></span> <span class="icon icon-bar"></span>
		</button>
		<a class="navbar-brand" href="/"><img src="<c:url value='/img/logo_header.png'/>" alt="云行政"> <span
			class="text" style="color: #fff">云行政-智慧行政服务平台</span></a>
	</div>
</div>
</header>


<!--common css for all pages-->
<%@ include file="../shared/importCss.jsp"%>
<!--css for this page-->

<link rel="stylesheet" href="<c:url value='/js/vendor/zTree/css/awesomeStyle/awesome.css'/>" type="text/css">





<div class="h5a-header" id="content">
	<div class="container h5a-container gray">
		<div class="row">

			<div class="row col-sm-12">
				<div class="box hidden-print" style="height: 200px;">
					<div class="title">
						<h4>
							<div style="margin-top: -13px;">
								<a href="#">员工信息</a>

							</div>

						</h4>

						<form class="form-horizontal">
							<div class="row col-sm-12">
								
	
								<div class="col-md-4">
									<div class="form-group">
											<label class="col-sm-2">姓名:</label>
											<div class="col-sm-8">
												<input type="text" class="h5a-input form-control input-sm" placeholder="用户姓名" id="form-field-1">
											</div>
									</div>
								</div>

								<div class="col-md-4">
									<div class="form-group">
											<label class="col-sm-2 control-label">手机号:</label>
											<div class="col-sm-8">
												<input type="text" class="h5a-input form-control input-sm" placeholder="手机号" id="form-field-1">
											</div>
									</div>
								</div>
								
								<div class="col-md-4">
									<div class="form-group">
											<label class="col-sm-2">员工类型:</label>
											<div class="col-sm-8">
												<input type="text" class="h5a-input form-control input-sm" placeholder="用户姓名" id="form-field-1">
											</div>
									</div>
								</div>
	
							</div>
							
							
							<div class="row col-sm-12">
								
	
								<div class="col-md-4">
									<div class="form-group">
											<label class="col-sm-2">工号:</label>
											<div class="col-sm-8">
												<input type="text" class="h5a-input form-control input-sm" placeholder="用户姓名" id="form-field-1">
											</div>
									</div>
								</div>

								<div class="col-md-4">
									<div class="form-group">
											<label class="col-sm-2 control-label">部门:</label>
											<div class="col-sm-8">
												<input type="text" class="h5a-input form-control input-sm" placeholder="手机号" id="form-field-1">
											</div>
									</div>
								</div>
								
								<div class="col-md-4">
									<div class="form-group">
											<label class="col-sm-2">职位:</label>
											<div class="col-sm-8">
												<input type="text" class="h5a-input form-control input-sm" placeholder="用户姓名" id="form-field-1">
											</div>
									</div>
								</div>
	
							</div>
							
							<div class="row col-sm-12">
								
	
								<div class="col-md-4">
									<div class="form-group">
											<label class="col-sm-2">公司座机:</label>
											<div class="col-sm-8">
												<input type="text" class="h5a-input form-control input-sm" placeholder="用户姓名" id="form-field-1">
											</div>
									</div>
								</div>
								
								<div class="col-md-4">
									<div class="form-group">
											<label class="col-sm-2">分机号:</label>
											<div class="col-sm-8">
												<input type="text" class="h5a-input form-control input-sm" placeholder="用户姓名" id="form-field-1">
											</div>
									</div>
								</div>
								
								<div class="col-md-4">
									
								</div>


								
	
							</div>
							
							<div class="row col-sm-12">
								
									<div class="col-md-4">
									
									</div>
									
									<div class="col-md-4">
									
								
										<div class="form-group">
	
													<a href="/xcloud/staff/userForm"><button class="btn btn-danger " type="button">提交</button></a>
	
										</div>
									
									</div>
									
									<div class="col-md-4">
									
									</div>

							</div>
					

					</form>


				</div>
			</div>

		</div>

	</div>
</div>

<!-- js placed at the end of the document so the pages load faster -->
<!--common script for all pages-->
<%@ include file="../shared/importJs.jsp"%>

<!--script for this page-->
<script type="text/javascript" src="<c:url value='/js/vendor/zTree/js/jquery.ztree.core-3.5.js'/>"></script>
</body>

</html>
