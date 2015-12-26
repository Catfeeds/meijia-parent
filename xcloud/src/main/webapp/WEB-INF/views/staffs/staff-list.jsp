<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ include file="../shared/taglib.jsp"%>

<html>
<head>

<!--common css for all pages-->
<%@ include file="../shared/importCss.jsp"%>

<!--css for this page-->

<link href="<c:url value='/assets/js/zTree/css/awesomeStyle/awesome.css'/>" rel="stylesheet">
<link href="<c:url value='/assets/js/amazeui.datatables/amazeui.datatables.min.css'/>" rel="stylesheet">

</head>

<body>
	<!--header start-->
	<%@ include file="../shared/pageHeader.jsp"%>
	<!--header end-->

	<div class="am-cf admin-main">
		<!-- sidebar start -->
		<div class="admin-sidebar am-offcanvas" id="admin-offcanvas">
			
			<ul class="am-list admin-sidebar-list">
				<li><%@ include file="../shared/dept-tree.jsp"%></li>
				<input type="hidden" id="dept_id" value="0"/>
			</ul>


		</div>
		<!-- sidebar end -->

		<!-- content start -->
		<div class="admin-content">
			<div class="am-cf am-padding">
				<div class="am-fl am-cf">
					<strong class="am-text-primary am-text-lg">通讯录</strong>
					
					
				</div>
				<div class="am-fr">
					<button type="button" class="am-btn am-btn-success">导入通讯录</button>
					<button type="button" class="am-btn am-btn-warning" id="btn-staff-add">添加员工</button>
					<button type="button" class="am-btn am-btn-danger" id="btn-change-dept">分配部门</button>
				</div>
			</div>

			<hr />

			<div class="am-u-md-11">
				<div class="doc-example">
					<div id="sorting-chinese_wrapper"
						class="dataTables_wrapper am-datatable am-form-inline dt-amazeui no-footer">
						<div class="am-g am-datatable-hd">
							<div class="am-u-sm-6">
								<div class="dataTables_length am-form-group am-datatable-length" id="sorting-chinese_length">
									<!-- <label>显示 <select name="sorting-chinese_length" aria-controls="sorting-chinese"
										class="am-form-select am-input-sm"><option value="10">10</option>
											<option value="25">25</option>
											<option value="50">50</option>
											<option value="100">100</option></select> 项结果
									</label> -->
									
								</div>
							</div>
							<div class="am-u-sm-6">
								
							</div>

						</div>
						<div class="am-g">
							<div class="am-u-sm-12">
								<table id="list-table"
									class="am-table am-table-bordered am-table-striped dataTable no-footer" role="grid"
									aria-describedby="sorting-chinese_info">
									<thead>
										<tr>
											<th width="10%"><input type="checkbox" name="select_all" id="select_all" value="1" /></th>
											<th width="5%">工号</th>
											<th width="15%">姓名</th>
											<th width="15%">手机</th>
											<th width="10%">职位</th>
											<th width="15%">部门</th>
											<th width="10%">类型</th>
											<th width="15%">操作</th>
										</tr>
									</thead>

								</table>
							</div>
						</div>

					</div>
				</div>
			</div>
			
			<div class="am-modal am-modal-confirm" tabindex="-1" id="change-dept-modal">
			
			<div class="am-modal-dialog">
				<div class="am-modal-hd">员工分配部门</div>
				
				
				
				
				
				
				<div class="am-modal-bd">
				
				
					<form class="am-form">
		          <div class="am-g am-margin-top">
		            <div class="am-u-sm-4">
		              已选择员工:
		            </div>
		           
		            <div class="am-u-sm-8 am-text-left">
		              <label id="select_staff_names"></label>
		                <input type="hidden" id="select_staff_ids" name="select_staff_ids" value=""/>
		            </div>
		            
		          </div>
		
		          <div class="am-g am-margin-top">
		            <div class="am-u-sm-4">
		              分配部门
		            </div>
		            <div class="am-u-sm-8">
		              <select id="dept-id-select">
					    		<c:forEach items="${deptList}" var="item">
					    			<option value="${ item.deptId}">${ item.name}</option>
					    		</c:forEach>
					    	</select>
		            </div>
		          </div>
		
		        </form>				
				
				
				
			      
			     
			     
			    </div>
				
				<div class="am-modal-footer">
					<span class="am-modal-btn" data-am-modal-cancel>取消</span> <span class="am-modal-btn"
						data-am-modal-confirm>确定</span>
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
	<script src="<c:url value='/assets/js/zTree/js/jquery.ztree.core-3.5.js'/>"></script>
	<script src="<c:url value='/assets/js/zTree/js/jquery.ztree.excheck-3.5.js'/>"></script>
	<script src="<c:url value='/assets/js/zTree/js/jquery.ztree.exedit-3.5.js'/>"></script>
	<script src="<c:url value='/assets/js/xcloud/common/dept-tree.js'/>"></script>
	<script src="<c:url value='/assets/js/amazeui.datatables/amazeui.datatables.min.js'/>"></script>
	<script src="<c:url value='/assets/js/amazeui.datatables/dataTables.responsive.min.js'/>"></script>
	<script src="<c:url value='/assets/js/xcloud/staffs/staff-list.js'/>"></script>
</body>
</html>
