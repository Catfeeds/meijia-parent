<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	import="com.simi.oa.common.UrlHelper"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="../shared/taglib.jsp"%>
<!-- taglib for this page -->
<html>
<head>
<!--common css for all pages-->
<%@ include file="../shared/importCss.jsp"%>
<!--css for this page-->
</head>
<body>
	<section id="container"> <!--header start--> <%@ include file="../shared/pageHeader.jsp"%>
	<!--header end--> <!--sidebar start--> <%@ include file="../shared/sidebarMenu.jsp"%> <!--sidebar end-->
	<!--main content start--> <section id="main-content"> <section class="wrapper"> <!-- page start-->
	<div class="row">
		<div class="col-lg-12">
			<section class="panel"> 
				<header class="panel-heading">
				<h4>数据搜索</h4>
				<div>
					<form:form modelAttribute="searchModel"  method="GET">
					手机号：
					<form:input path="mobile" />
					
					订单状态：
					<form:select path="orderStatus">
						<option value="">全部</option>
						<form:option value="0">兑换中</form:option>
						<form:option value="1">兑换成功</form:option>
					</form:select>
					
					订单号：
					<form:input path="orderNo" />
					
					兑吧订单号：
					<form:input path="orderNum" />
					<input type="submit" class = "btn btn-success" value="搜索">
				</div>
		</div>
		</header>
		</form:form>
		<header class="panel-heading">
		<h4>兑换列表</h4>
		</header>
		<hr style="width: 100%; color: black; height: 1px; background-color: black;" />
		<table class="table table-striped table-advance table-hover">
			<thead>
				<tr>
					<th width="15%">时间</th>
					<th width="10%">手机号</th>
					<th width="15%">状态</th>
					<th width="10%">使用积分</th>
					<th width="10%">兑换信息</th>
					<th width="10%">市场价值</th>
					<th width="10%">扣除开发者费用</th>
					<th width="10%">订单号</th>
					<th width="10%">兑吧订单号</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${contentModel.list}" var="item">
					<tr>
						<td>${ item.addTimeStr }</td>
						<td>${ item.mobile }</td>
						<td>${ item.orderStatusStr }</td>
						<td>${ item.credits }</td>
						<td>${ item.description }</td>
						<td>${ item.faceprice }元</td>
						<td>${ item.actualprice }元</td>
						<td>${ item.orderNo }</td>
						<td>${ item.ordernum }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</section> <c:import url="../shared/paging.jsp">
		<c:param name="pageModelName" value="contentModel" />
		<c:param name="urlAddress" value="/order/scoreList" />
	</c:import>
	</div>
	</div>
	<!-- page end--> </section> </section>
	<!--main content end-->
	<!--footer start-->
	<%@ include file="../shared/pageFooter.jsp"%>
	<!--footer end-->
	</section>
	<!-- js placed at the end of the document so the pages load faster -->
	<!--common script for all pages-->
	<%@ include file="../shared/importJs.jsp"%>
	<!--script for this page-->
	<script src="<c:url value='/js/simi/order/orderList.js'/>"></script>
</body>
</html>