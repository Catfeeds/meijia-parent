<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ include file="../shared/taglib.jsp"%>


<html>
<head>

<!--common css for all pages-->
<%@ include file="../shared/importCss.jsp"%>

<!--css for this page-->

<link rel="stylesheet" href="<c:url value='/js/vendor/zTree/css/awesomeStyle/awesome.css'/>" type="text/css">
<style type="text/css">
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

				<%@ include file="../shared/dept-tree.jsp"%>

				<div class="col-sm-10">
					<div class="box hidden-print" style="height: 500px;">
						<div class="title">
							<h4>
								<div style="margin-top: -13px;">
									<a href="#">员工列表</a>
									
								</div>
								
							</h4>
							
							<span class="pull-right boxes_right" style="margin-top: -30px;"> 
								<a href="/xcloud/staff/userForm"><button class="btn btn-danger " type="button">批量导入</button></a>
								<a href="/xcloud/staff/userForm"><button class="btn btn-info " type="button">添加员工</button></a>
								
							</span>

						</div>


						<!-- //////////////表格数据-start/////////////////////////// -->
						<table class="table table-striped table-advance table-hover">
							<thead>
								<tr>
									<th  >头像</th>
                               		<th  >姓名</th>
                               		<th  >手机号</th>
                               		<th  >员工类型</th>
                               		<th  >工号</th>
                               		<th  >部门</th>
                               		<th  >职位</th>
                               		<th  >操作</th>
								</tr>
							</thead>
							<tbody>
								
									<tr>
										<td>#</td>
										<td>张三</td>
										<td>13810002890</td>
										<td>全职</td>
										<td>0000013</td>
										<td>技术部</td>
			
										<td>高级工程师</td>
										
										<td>
											<button id="btn_update" onClick="#" class="btn btn-primary btn-xs" title="修改"><i class=" icon-edit"></i></button>
											<button id="btn_update" onClick="#" class="btn btn-primary btn-xs" title="删除"><i class=" icon-remove"></i></button>
											<button id="btn_update" onClick="#" class="btn btn-primary btn-xs" title="员工离职"><i class="  icon-share-alt"></i></button>
										</td>
									</tr>
									
									
								
							</tbody>
						</table>
						<!-- //////////////表格数据-end/////////////////////////// -->
					</div>



				</div>


			</div>
		</div>





		<!-- js placed at the end of the document so the pages load faster -->
		<!--common script for all pages-->
		<%@ include file="../shared/importJs.jsp"%>

		<!--script for this page-->
		<script type="text/javascript" src="<c:url value='/js/vendor/zTree/js/jquery.ztree.core-3.5.js'/>"></script>
		<script type="text/javascript" src="<c:url value='/js/vendor/zTree/js/jquery.ztree.excheck-3.5.js'/>"></script>
		<script type="text/javascript" src="<c:url value='/js/vendor/zTree/js/jquery.ztree.exedit-3.5.js'/>"></script>
		<script type="text/javascript" src="<c:url value='/js/xcloud/common/dept-tree.js'/>"></script>
		<script type="text/javascript" src="<c:url value='/js/xcloud/staffs/staff-list.js'/>"></script>
</body>
</html>
