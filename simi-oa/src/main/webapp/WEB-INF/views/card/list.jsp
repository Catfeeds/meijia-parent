<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	import="com.simi.oa.common.UrlHelper"
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ include file="../shared/taglib.jsp"%>

<!-- taglib for this page -->
<%@ taglib prefix="timestampTag" uri="/WEB-INF/tags/timestamp.tld"%>


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
			<section class="panel"> <form:form modelAttribute="searchModel" method="GET">
				<header class="panel-heading">
				<h4>数据搜索</h4>
				<div>
					卡片类型：
					<form:select path="cardType">
						<option value="">请选择卡片类型</option>
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
		</header>
		</form:form>

		<header class="panel-heading">
		<h4>卡片列表</h4>
		</header>
		<hr style="width: 100%; color: black; height: 1px; background-color: black;" />
		<table class="table table-striped table-advance table-hover">
			<thead>
				<tr>
					<th>用户</th>
					<th>卡片时间</th>
					<th>卡片类型</th>
					<th>卡片标题</th>
					<th>卡片内容</th>
					<th>卡片状态</th>
					<th>赞个数</th>
					<th>评论数</th>
					<th>创建时间</th>

				</tr>
			</thead>
			<tbody>
				<c:forEach items="${contentModel.list}" var="item">
					<tr>
						<td>${ item.userName }</td>
						<td><timestampTag:timestamp patten="MM-dd HH:mm" t="${item.serviceTime * 1000}" /></td>
						<td>${ item.cardTypeName }</td>
						<td>${ item.title }</td>
						<td>${ item.serviceContent }</td>
						<td><c:if test="${ item.status < 1 }">取消</c:if> 
							<c:if test="${ item.status == 1 }">处理中</c:if> 
							<c:if test="${ item.status == 2 }">秘书处理中</c:if> 
							<c:if test="${ item.status == 3 }">已完成</c:if> 
						</td>
						<td>${ item.totalZan }</td>
						<td>${ item.totalComment }</td>
						<td><timestampTag:timestamp patten="MM-dd HH:mm" t="${item.addTime * 1000}" /></td>			
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</section> <c:import url="../shared/paging.jsp">
		<c:param name="pageModelName" value="contentModel" />
		<c:param name="urlAddress" value="/card/list" />
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

</body>
</html>