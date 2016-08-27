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
		<%@ include file="../xz/xz-menu.jsp"%>
		<!-- sidebar end -->

		<!-- content start -->
		<div class="admin-content">

			<div class="am-cf am-padding">
				<div class="am-fl am-cf">
					<strong class="am-text-primary am-text-lg">快递登记列表</strong> / <small>快递登记信息一览</small>
				</div>

				<div class="am-u-sm-12 am-u-md-3 am-fr">
					<div class="am-btn-toolbar am-fr">
						<div class="am-btn-group am-btn-group-sm ">
							<button type="button" id="btn-express-add" class="am-btn am-btn-warning am-radius">
								<span class="am-icon-plus"></span> 新增快递单
							</button>
						</div>
					</div>
				</div>
			</div>
			<hr>

			<div class="am-g">
				<div class="am-u-sm-12">
					<form:form modelAttribute="searchModel" class="am-form-inline am-form-horizontal" action="list" method="GET">
						从<div class="am-form-group">
							<input type="hidden" id="userId" name="userId" value="${userId}"/>
							

							<div class="am-input-group am-datepicker-date" data-am-datepicker="{format: 'yyyy-mm-dd', viewMode: 'day'}">
								<input type="text" id="startDate" name="startDate" value="${searchModel.startDate}" class="am-form-field"
									readonly size=15> <span class="am-input-group-btn am-datepicker-add-on">
									<button class="am-btn am-btn-default" type="button">
										<span class="am-icon-calendar"></span>
									</button>
								</span>
							</div>


						</div>

						<div class="am-form-group">
							<label class="am-form-label am-fl">至&nbsp;</label>


							<div class="am-input-group am-datepicker-date" data-am-datepicker="{format: 'yyyy-mm-dd', viewMode: 'day'}">
								<input type="text" id="endDate" name="endDate" value="${searchModel.endDate}" class="am-form-field" readonly
									size=15> <span class="am-input-group-btn am-datepicker-add-on">
									<button class="am-btn am-btn-default" type="button">
										<span class="am-icon-calendar"></span>
									</button>
								</span>
							</div>


						</div>

						<div class="am-form-group ">
							

							<form:select path="expressId" class="am-form-field">
								<form:option value="">全部快递公司</form:option>
								<form:options items="${expressList}" itemValue="expressId" itemLabel="name" />
							</form:select>
						</div>

						<div class="am-form-group">

							<form:select path="isClose" class="am-form-field">
								<form:option value="">是否结算</form:option>
								<form:option value="0">未结算</form:option>
								<form:option value="1">已结算</form:option>
							</form:select>
						</div>
						
						<div class="am-form-group">

							<form:select path="expressType" class="am-form-field">
								<form:option value="">收发件</form:option>
								<form:option value="1">寄件</form:option>
								<form:option value="0">收件</form:option>
							</form:select>
						</div>
						
<%-- 						<div class="am-form-group">

							<form:select path="payType" class="am-form-field">
								<form:option value="">付费方式</form:option>
								<form:option value="0">公费</form:option>
								<form:option value="1">自费</form:option>
							</form:select>
						</div>
						 --%>
						<div class="am-form-group">
							<div class="am-input-group am-input-group-sm">

								<form:input path="expressNo" class="am-form-field am-radius " placeholder="快递单号" maxLength="18" />
								<span class="am-input-group-btn">
									<button class="am-btn am-btn-default" type="submit">搜索</button>
								</span>
							</div>
						</div>

					</form:form>
				</div>
			</div>
			<br>
			<div class="am-g">
				<div class="am-u-sm-12">
					<form class="am-form">
						<table id="list-table" class="am-table am-table-bordered am-table-striped">
							<thead>
								<tr class="am-primary">
									
									<th class="table-date">时间</th>
									<th class="table-title">快递服务商</th>
									<th class="table-type">快递单号</th>
									<th class="table-date">类型</th>
									<th class="table-date">付费方式</th>
									<th class="table-date">是否结算</th>
									<th class="table-set">操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${contentModel.list}" var="item">
									<tr>
										
										<td>${item.addTimeStr}</td>
										<td>${item.expressName}</td>
										<td>${item.expressNo}</td>

										<td class="am-hide-sm-only">${item.expressTypeName}</td>
										<td class="am-hide-sm-only">${item.payTypeName}</td>
										<td class="am-hide-sm-only"><c:if test="${item.expressType == 0}">
												_
											</c:if> <c:if test="${item.expressType == 1}">
												${item.isCloseName}
											</c:if></td>

										<td>
											<div class="am-btn-toolbar">
												<div class="am-btn-group am-btn-group-xs">
													<a href="/xcloud/xz/express/express-form?id=${item.id }" class="am-icon-edit" title="编辑"></a> <a
														href="http://www.kuaidi100.com/chaxun?com=${item.ecode}&nu=${item.expressNo}" class="am-icon-truck"
														title="物流追踪" target="_blank"></a>
												</div>
											</div>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>

						<div class="am-u-sm-12">
							<div class="am-btn-toolbar">
								<div class="am-btn-group am-btn-group-sm">


									<button type="button" id="btn-express-close-all" class="am-btn am-btn-danger">
										<span class="am-icon-shopping-cart"></span> 一键结算
									</button>
									
									<button type="button" id="btn-express-export" class="am-btn am-btn-success">
										<span class="am-icon-table"></span> 导出快递单
									</button>

								</div>
							</div>
						</div>


						<c:import url="../shared/paging.jsp">
							<c:param name="pageModelName" value="contentModel" />
							<c:param name="urlAddress" value="/express/list" />
						</c:import>

					</form>
				</div>

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
	<script src="<c:url value='/assets/js/xcloud/xz/express-list.js'/>"></script>
</body>
</html>
