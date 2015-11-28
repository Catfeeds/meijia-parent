<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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

				<input type="hidden" id="companyId" value="${companyId}"/>
				<input type="hidden" id="companyName" value="${companyName}"/>
				<input type="hidden" id="shortName" value="${shortName}"/>

				<div class="col-sm-2">
					<div class="box hidden-print" style="height: 500px;">
						<div class="title">
							<h4>
								<div style="margin-top: -10px;">
									<a href="#">组织架构</a>
								</div>

							</h4>
							<!-- <span class="pull-right boxes_right" style="margin-top: -35px;">
								<button class="btn btn-danger " type="button">修改</button>
							</span> -->
							<div class="title_right newadd fr prelative">
								<i class="add_icon"></i>
								<dl class="newadd_list company_message" style="display: none;">
									<dd>
										<a id="department_id" class="add_element"
											data-add_target="a.add_element" href="javascript:;">新增部门</a>
									</dd>
									<dd>
										<a href="/members/multi?to=add">添加员工</a>
									</dd>
									<dd>
										<a class="members_multi_add" href="javascript:void(0);">批量导入</a>
									</dd>
								</dl>
							</div>
						</div>
						<ul id="detpTree" class="ztree"></ul>
					</div>
				</div>

				<div class="col-sm-8">
					<div class="box hidden-print" style="height: 500px;">
						<div class="title">
							<h4>
								<div style="margin-top: -13px;">
									<a href="#">员工列表</a>
								</div>

							</h4>
							<span class="pull-right boxes_right" style="margin-top: -30px;">
								<%-- <button class="btn btn-danger " type="button" onClick="btn_add('order/orderView?orderNo=${item.orderNo }')">添加员工</button> --%>
								<a href="/xcloud/staff/userForm"><button
										class="btn btn-danger " type="button">添加员工</button></a>

							</span>

						</div>

						<!-- <dl class="num_list clearfix">
							<dd>
								<a href="#">
									<p class="ind_num ind_color_green">
										7<em>人</em>
									</p>
									<p class="ind_name">在职员工</p>
								</a>
							</dd>
							<dd>
								<p class="ind_num ind_color_blue">
									4<em>人</em>
								</p>
								<p class="ind_name">全职员工</p>
							</dd>
							<dd>
								<p class="ind_num ind_color_red">
									3<em>人</em>
								</p>
								<p class="ind_name">外勤员工</p>
							</dd>
						</dl> -->
						<!-- ///////////////////////////////////////// -->
						<div class="card-body">
							<div id="DataTables_Table_0_wrapper"
								class="dataTables_wrapper form-inline dt-bootstrap">
								<div class="top">
									<div id="DataTables_Table_0_filter" class="dataTables_filter">
										<label> Search: <input class="form-control input-sm"
											type="search" placeholder=""
											aria-controls="DataTables_Table_0">
										</label>
									</div>
									<div id="DataTables_Table_0_length" class="dataTables_length">
										<label> Show <select class="form-control input-sm"
											name="DataTables_Table_0_length"
											aria-controls="DataTables_Table_0">
												<option value="10">10</option>
												<option value="25">25</option>
												<option value="50">50</option>
												<option value="100">100</option>
										</select> entries
										</label>
									</div>
									<div class="clear"></div>
								</div>
								<table id="DataTables_Table_0"
									class="datatable table table-striped dataTable" width="100%"
									cellspacing="0" role="grid"
									aria-describedby="DataTables_Table_0_info" style="width: 100%;">
									<thead>
										<tr role="row">
											<th class="sorting_asc" tabindex="0"
												aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
												style="width: 178px;" aria-sort="ascending"
												aria-label="Name: activate to sort column descending">Name</th>
											<th class="sorting" tabindex="0"
												aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
												style="width: 273px;"
												aria-label="Position: activate to sort column ascending">Position</th>
											<th class="sorting" tabindex="0"
												aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
												style="width: 132px;"
												aria-label="Office: activate to sort column ascending">Office</th>
											<th class="sorting" tabindex="0"
												aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
												style="width: 79px;"
												aria-label="Age: activate to sort column ascending">Age</th>
											<th class="sorting" tabindex="0"
												aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
												style="width: 143px;"
												aria-label="Start date: activate to sort column ascending">Start
												date</th>
											<th class="sorting" tabindex="0"
												aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
												style="width: 106px;"
												aria-label="Salary: activate to sort column ascending">Salary</th>
										</tr>
									</thead>
									<tfoot>
										<tr>
											<th rowspan="1" colspan="1">Name</th>
											<th rowspan="1" colspan="1">Position</th>
											<th rowspan="1" colspan="1">Office</th>
											<th rowspan="1" colspan="1">Age</th>
											<th rowspan="1" colspan="1">Start date</th>
											<th rowspan="1" colspan="1">Salary</th>
										</tr>
									</tfoot>
									<tbody>
										<tr class="odd" role="row">
											<td class="sorting_1">Airi Satou</td>
											<td>Accountant</td>
											<td>Tokyo</td>
											<td>33</td>
											<td>2008/11/28</td>
											<td>$162,700</td>
										</tr>
										<tr class="even" role="row">
											<td class="sorting_1">Angelica Ramos</td>
											<td>Chief Executive Officer (CEO)</td>
											<td>London</td>
											<td>47</td>
											<td>2009/10/09</td>
											<td>$1,200,000</td>
										</tr>
										<tr class="odd" role="row">
											<td class="sorting_1">Ashton Cox</td>
											<td>Junior Technical Author</td>
											<td>San Francisco</td>
											<td>66</td>
											<td>2009/01/12</td>
											<td>$86,000</td>
										</tr>
										<!--<tr class="even" role="row">
									<td class="sorting_1">Bradley Greer</td>
									<td>Software Engineer</td>
									<td>London</td>
									<td>41</td>
									<td>2012/10/13</td>
									<td>$132,000</td>
								</tr>
							 	<tr class="odd" role="row">
									<td class="sorting_1">Brenden Wagner</td>
									<td>Software Engineer</td>
									<td>San Francisco</td>
									<td>28</td>
									<td>2011/06/07</td>
									<td>$206,850</td>
								</tr>
								<tr class="even" role="row">
									<td class="sorting_1">Brielle Williamson</td>
									<td>Integration Specialist</td>
									<td>New York</td>
									<td>61</td>
									<td>2012/12/02</td>
									<td>$372,000</td>
								</tr>
								<tr class="odd" role="row">
									<td class="sorting_1">Bruno Nash</td>
									<td>Software Engineer</td>
									<td>London</td>
									<td>38</td>
									<td>2011/05/03</td>
									<td>$163,500</td>
								</tr>
								<tr class="even" role="row">
									<td class="sorting_1">Caesar Vance</td>
									<td>Pre-Sales Support</td>
									<td>New York</td>
									<td>21</td>
									<td>2011/12/12</td>
									<td>$106,450</td>
								</tr>
								<tr class="odd" role="row">
									<td class="sorting_1">Cara Stevens</td>
									<td>Sales Assistant</td>
									<td>New York</td>
									<td>46</td>
									<td>2011/12/06</td>
									<td>$145,600</td>
								</tr>
								<tr class="even" role="row">
									<td class="sorting_1">Cedric Kelly</td>
									<td>Senior Javascript Developer</td>
									<td>Edinburgh</td>
									<td>22</td>
									<td>2012/03/29</td>
									<td>$433,060</td>
								</tr> -->
									</tbody>
								</table>
								<div class="bottom">
									<div id="DataTables_Table_0_info" class="dataTables_info"
										role="status" aria-live="polite">Showing 1 to 10 of 57
										entries</div>
									<div id="DataTables_Table_0_paginate"
										class="dataTables_paginate paging_simple_numbers">
										<ul class="pagination">
											<li id="DataTables_Table_0_previous"
												class="paginate_button previous disabled"
												aria-controls="DataTables_Table_0" tabindex="0"><a
												href="#">Previous</a></li>
											<li class="paginate_button active"
												aria-controls="DataTables_Table_0" tabindex="0"><a
												href="#">1</a></li>
											<li class="paginate_button "
												aria-controls="DataTables_Table_0" tabindex="0"><a
												href="#">2</a></li>
											<li class="paginate_button "
												aria-controls="DataTables_Table_0" tabindex="0"><a
												href="#">3</a></li>
											<li class="paginate_button "
												aria-controls="DataTables_Table_0" tabindex="0"><a
												href="#">4</a></li>
											<li class="paginate_button "
												aria-controls="DataTables_Table_0" tabindex="0"><a
												href="#">5</a></li>
											<li class="paginate_button "
												aria-controls="DataTables_Table_0" tabindex="0"><a
												href="#">6</a></li>
											<li id="DataTables_Table_0_next" class="paginate_button next"
												aria-controls="DataTables_Table_0" tabindex="0"><a
												href="#">Next</a></li>
										</ul>
									</div>
									<div class="clear"></div>
								</div>
							</div>
							<!-- /////////////////////////////////// -->
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
		<script type="text/javascript" src="<c:url value='/js/vendor/zTree/js/jquery.ztree.excheck-3.5.js'/>"></script>
		<script type="text/javascript" src="<c:url value='/js/vendor/zTree/js/jquery.ztree.exedit-3.5.js'/>"></script>
		<script type="text/javascript" src="<c:url value='/js/xcloud/common/dept-tree.js'/>"></script>
		<script type="text/javascript" src="<c:url value='/js/xcloud/staffs/staff-list.js'/>"></script>
</body>
</html>
