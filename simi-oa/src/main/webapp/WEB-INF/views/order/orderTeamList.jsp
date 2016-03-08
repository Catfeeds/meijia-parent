<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.simi.oa.common.UrlHelper"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ include file="../shared/taglib.jsp"%>

<!-- taglib for this page -->
<%@ taglib prefix="orderFromTag" uri="/WEB-INF/tags/orderFromName.tld"%>
<%@ taglib prefix="timestampTag" uri="/WEB-INF/tags/timestamp.tld"%>
<%@ taglib prefix="orderStatusTag"
	uri="/WEB-INF/tags/orderStatusName.tld"%>
<%@ taglib prefix="payTypeNameTag" uri="/WEB-INF/tags/payTypeName.tld"%>
<%@ taglib prefix="orderServiceTimeTag"
	uri="/WEB-INF/tags/orderServiceTime.tld"%>
<%@ taglib prefix="serviceTypeTag"
	uri="/WEB-INF/tags/serviceTypeName.tld"%>
<%@ taglib prefix="serviceTypeSelectTag"
	uri="/WEB-INF/tags/serviceTypeSelect.tld"%>
<%@ taglib prefix="orderStatusSelectTag"
	uri="/WEB-INF/tags/orderSatusSelect.tld"%>
<%@ taglib prefix="orderFromSelectTag"
	uri="/WEB-INF/tags/orderFromSelect.tld"%>

<html>
<head>

<!--common css for all pages-->
<%@ include file="../shared/importCss.jsp"%>

<!--css for this page-->

</head>

<body>

	<section id="container"> <!--header start--> <%@ include
		file="../shared/pageHeader.jsp"%> <!--header end-->

	<!--sidebar start--> <%@ include file="../shared/sidebarMenu.jsp"%>
	<!--sidebar end--> <!--main content start--> <section id="main-content">
	<section class="wrapper"> <!-- page start-->

	<div class="row">
		<div class="col-lg-12">
			<section class="panel"> <form:form
				modelAttribute="oaOrderSearchVoModel" action="teamList" method="GET">
				<header class="panel-heading">
				<h4>数据搜索</h4>
				<div>
					订单状态：
					<form:select path="orderStatus">
						<option value="">请选择订单状态</option>
						<form:option value="0">已关闭</form:option>
						<form:option value="1">待支付</form:option>
						<form:option value="2">已支付</form:option>
						<form:option value="3">处理中</form:option>
						<form:option value="7">待评价</form:option>
						<form:option value="9">已完成</form:option>
					</form:select>

					<input type="submit" value="搜索">
				</div>
		</div>
		<div class="pull-right">
            <button onClick="btn_add('order/orderTeamAddForm?id=0')" class="btn btn-primary" type="button"><i class="icon-expand-alt"></i>新增</button>
         </div> 
		</header>
		</form:form>

		<header class="panel-heading">
		<h4>团建订单列表</h4>
		</header>
		<hr
			style="width: 100%; color: black; height: 1px; background-color: black;" />
		<table class="table table-striped table-advance table-hover">
			<thead>
				<tr>
					<th>订单号</th>
					<th>下单时间</th>
					<th>用户手机号</th>
					<th>服务日期</th>
					<th>服务大类</th>
					<th>所在城市</th>
					<th>参加人数</th>
					<th>活动天数</th>
					<th>订单状态</th>
					<th>订单总金额</th>
						<th>订单支付金额</th>
						<!--   <th>操作</th> -->
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${contentModel.list}" var="item">
						<tr>
							<td>${ item.orderNo }</td>
							<td><timestampTag:timestamp patten="yyyy-MM-dd HH:mm "
								t="${item.addTime * 1000}" /></td>
							<td>${ item.mobile }</td>
							<td><timestampTag:timestamp patten="yyyy-MM-dd"
								t="${item.serviceDate * 1000}" /></td>
							<td>${ item.serviceTypeName }</td>

							<td>${ item.cityName }</td>
							<td>${ item.attendNum }</td>
							<td>${ item.serviceDays }</td>
							<td><c:if test="${ item.orderStatus < 1 }">
							            		${ item.orderStatusName }
							            	</c:if> <c:if test="${ item.orderStatus == 1 }">
									<span style="color: red; font-weight: bold;">${ item.orderStatusName }</span>
								</c:if> <c:if test="${ item.orderStatus > 1 }">
									<span style="color: green; font-weight: bold;">${ item.orderStatusName }</span>
								</c:if></td>
							<td>${ item.orderMoney }</td>
							<td>${ item.orderPay }</td>
							<td><button id="btn_update"
								onClick="btn_update('order/orderTeamForm?orderNo=${item.orderNo }')"
								class="btn btn-primary btn-xs" title="订单详情">
								<i class=" icon-ambulance"></i>
							</button></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>


			</section>

			<c:import url="../shared/paging.jsp">
				<c:param name="pageModelName" value="contentModel" />
				<c:param name="urlAddress" value="/order/teamList" />
			</c:import>
		</div>
	</div>
	<!-- page end--> </section> </section> <!--main content end--> <!--footer start--> <%@ include
		file="../shared/pageFooter.jsp"%> <!--footer end-->
	</section>

	<!-- js placed at the end of the document so the pages load faster -->
	<!--common script for all pages-->
	<%@ include file="../shared/importJs.jsp"%>

	<!--script for this page-->
	<script src="<c:url value='/js/simi/order/orderTeamList.js'/>"></script>
</body>
</html>