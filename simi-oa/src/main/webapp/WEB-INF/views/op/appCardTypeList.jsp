<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ include file="../shared/taglib.jsp"%>
<%@ taglib prefix="timestampTag" uri="/WEB-INF/tags/timestamp.tld"%>
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
			<section class="panel"> <%-- <form:form modelAttribute="searchModel" action="ad_list" method="GET">
				<header class="panel-heading">
				<h4>数据搜索</h4>
				<div>
					标题：
					<form:input path="title" />
					频道：
					<form:select path="adType">
						<option value="">全部</option>
						<form:options items="${opChannels}" itemValue="channelId" itemLabel="name" />

					</form:select>

					<input type="submit" value="搜索">
				</div>
				</header>
			</form:form>  --%> <header class="panel-heading"> 卡片类型管理

			<div class="pull-right">
				<button onClick="btn_add('/op/appCardTypeForm?card_type_id=0')"
					class="btn btn-primary" type="button">
					<i class="icon-expand-alt"></i>新增
				</button>
			</div>
			</header>

			<hr
				style="width: 100%; color: black; height: 1px; background-color: black;" />


			<table class="table table-striped table-advance table-hover">
				<thead>
					<tr>
						<th>序号</th>
						<th>名称</th>
						<th>卡片图标</th>
						<th>卡片类型</th>
						<th>应用类型</th>
						<th>添加时间</th>

						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${contentModel.list}" var="item">
						<tr>
							<td>${ item.no}</td>
							<td>${ item.name}</td>
							<td><img src="${ item.cardIcon }?w=100&h=100" /></td>
							<td><c:choose>

									<c:when test="${item.cardType  == 0}">
														通用
												</c:when>
									<c:when test="${item.cardType  == 1}">
														会议安排
												</c:when>
									<c:when test="${item.cardType  == 2}">
														秘书叫早
												</c:when>
									<c:when test="${item.cardType  == 3}">
														事务提醒
												</c:when>
									<c:when test="${item.cardType  == 4}">
														邀约通知
												</c:when>
									<c:when test="${item.cardType  == 5}">
														形成规划
												</c:when>
								</c:choose></td>
							<td>${ item.appType}</td>
							
							<td><timestampTag:timestamp patten="yyyy-MM-dd"
									t="${item.addTime * 1000}" /></td>
							<td>
								<button id="btn_update"
									onClick="btn_update('/op/appCardTypeForm?card_type_id=${item.cardTypeId}')"
									class="btn btn-primary btn-xs" title="修改">
									<i class="icon-pencil"></i>
								</button>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>


			</section>

			<c:import url="../shared/paging.jsp">
				<c:param name="pageModelName" value="contentModel" />
				<c:param name="urlAddress" value="/op/appCardType_list" />
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
	<script src="<c:url value='/js/simi/account/list.js'/>"></script>

</body>
</html>