<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ include file="../shared/taglib.jsp"%>

<html>
<head>

<!--common css for all pages-->
<%@ include file="../shared/importCss.jsp"%>

<!--css for this page-->

<link href="<c:url value='/assets/js/zTree/css/awesomeStyle/awesome.css'/>" rel="stylesheet">
<link href="<c:url value='/assets/js/amazeui.datatables/amazeui.datatables.min.css'/>"
	rel="stylesheet">

</head>

<body>
	<!--header start-->
	<%@ include file="../shared/pageHeader.jsp"%>
	<!--header end-->

	<div class="am-cf admin-main">
		<!-- sidebar start -->
		<div class="admin-sidebar am-offcanvas" id="admin-offcanvas">
			<div class="am-offcanvas-bar admin-offcanvas-bar">
				<ul class="am-list admin-sidebar-list">
					<li><a href="/staff/list"><span class="am-icon-book"></span> 通讯录</a></li>
					<li><a href="/staff/dept"><span class="am-icon-sitemap"></span> 组织架构</a></li>

				</ul>

				<div class="am-panel am-panel-default admin-sidebar-panel">
					<div class="am-panel-bd">
						<p>
							<span class="am-icon-bookmark"></span> 最新公告
						</p>
						<p>新年将至，公司年会将在月球举办，点击查看详情。</p>
					</div>
				</div>
			</div>
		</div>
		<!-- sidebar end -->

		<!-- content start -->
		<div class="admin-content">

			<div class="am-cf am-padding">
				<div class="am-fl am-cf">
					<strong class="am-text-primary am-text-lg">通讯录</strong> / <small>Contacts</small>
				</div>
			</div>
			<hr>
			<div class="am-g">
				<div class="am-u-sm-12 am-u-md-6">
					<div class="am-btn-toolbar">
						<div class="am-btn-group am-btn-group-sm">
							<button type="button" id="btn-staff-add" class="am-btn am-btn-default am-radius">
								<span class="am-icon-plus"></span> 新增员工
							</button>
							<button type="button" class="am-btn am-btn-default">
								<span class="am-icon-save"></span> 批量导入
							</button>
							<button type="button" class="am-btn am-btn-default">
								<span class="am-icon-table"></span> 导出通讯录
							</button>
							<button type="button" id="btn-change-dept" class="am-btn am-btn-default am-radius">
								<span class="am-icon-sitemap"></span> 分配部门
							</button>
						</div>
					</div>
				</div>

				<div class="am-u-sm-12 am-u-md-3">
					<div class="am-input-group am-input-group-sm">
						<input type="text" class="am-form-field" placeholder="姓名/身份证/手机号"> <span
							class="am-input-group-btn">
							<button class="am-btn am-btn-default" type="button">搜索员工</button>
						</span>
					</div>
				</div>
			</div>
			<br>
			<div class="am-g">
				<div class="am-u-sm-12">
					<form class="am-form">
						<table id="list-table" class="am-table am-table-bordered am-table-striped">
							<thead>
								<tr>
									<th class="table-check"><input type="checkbox" name="select_all" id="select_all"
										value="1" /></th>
									<th class="table-name">姓名</th>
									<th class="table-title">部门</th>
									<th class="table-type">职位</th>
									<th class="table-author am-hide-sm-only">手机号</th>
									<th class="table-date am-hide-sm-only">邮箱</th>
									<th class="table-id">工号</th>
									<th class="table-set">操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${contentModel.list}" var="item">
									<tr>
										<td><input type="checkbox" class="select-cell" value="${item.id}"></td>
										<td><a href="#" class="name-cell">${ item.name }</a></td>
										<td>${item.deptName}</td>
										<td class="am-hide-sm-only">${item.jobName}</td>
										<td class="am-hide-sm-only">${item.mobile}</td>
										<td>${item.companyEmail}</td>
										<td>${item.jobNumber}</td>
										<td>
											<div class="am-btn-toolbar">
												<div class="am-btn-group am-btn-group-xs">
													<button onClick="staffEdit(${item.id})" class="am-btn am-btn-default am-btn-xs am-text-secondary">
														<span class="am-icon-pencil-square-o"></span> 编辑
													</button>
													<button onClick="javascript:staffDel(${item.id})" class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only">
														<span class="am-icon-trash-o"></span> 删除
													</button>
												</div>
											</div>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<c:import url="../shared/paging.jsp">
							<c:param name="pageModelName" value="contentModel" />
							<c:param name="urlAddress" value="/staff/list" />
						</c:import>

					</form>
				</div>

			</div>
		</div>

		<div class="am-modal am-modal-confirm" tabindex="-1" id="change-dept-modal">

			<div class="am-modal-dialog">
				<div class="am-modal-hd">员工分配部门</div>

				<div class="am-modal-bd">

					<form class="am-form">
						<input type="hidden" id="companyId" value="${companyId }"/>
						<div class="am-g am-margin-top">
							<div class="am-u-sm-4">已选择员工:</div>

							<div class="am-u-sm-8 am-text-left">
								<label id="select_staff_names"></label> <input type="hidden" id="select_staff_ids"
									name="select_staff_ids" value="" />
							</div>

						</div>

						<div class="am-g am-margin-top">
							<div class="am-u-sm-4">分配部门</div>
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

	<script src="<c:url value='/assets/js/amazeui.datatables/amazeui.datatables.min.js'/>"></script>
	<script src="<c:url value='/assets/js/amazeui.datatables/dataTables.responsive.min.js'/>"></script>
	<script src="<c:url value='/assets/js/xcloud/staffs/staff-list.js'/>"></script>
</body>
</html>
