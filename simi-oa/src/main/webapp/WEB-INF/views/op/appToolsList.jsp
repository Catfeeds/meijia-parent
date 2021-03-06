<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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

	<section id="container"> <!--header start--> <%@ include file="../shared/pageHeader.jsp"%>
	<!--header end--> <!--sidebar start--> <%@ include file="../shared/sidebarMenu.jsp"%> <!--sidebar end-->

	<!--main content start--> <section id="main-content"> <section class="wrapper"> <!-- page start-->

	<div class="row">
		<div class="col-lg-12">
			<section class="panel"> 
			<%-- <form:form modelAttribute="searchModel" action="ad_list" method="GET">
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
			</form:form>  --%>
			<header class="panel-heading"> 应用管理

			<div class="pull-right">
				<button onClick="btn_add('/op/appToolsForm?t_id=0')" class="btn btn-primary" type="button">
					<i class="icon-expand-alt"></i>新增
				</button>
			</div>
			</header>

			<hr style="width: 100%; color: black; height: 1px; background-color: black;" />


			<table class="table table-striped table-advance table-hover">
				<thead>
					<tr>
						<th>序号</th>
						<th>名称</th>
						<th>图标</th>
						<th>显示类型</th>
						<th>动作标识</th>
						<th>相关参数</th>
						<th>默认显示</th>
						<th>可以删除</th>
						<th>上线</th>
						
						<th>二维码</th>
						<th>添加时间</th>

						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${contentModel.list}" var="item">
						<tr>
							<td>${ item.no}</td>
							<td>${ item.name}</td>
							<td><img src="${ item.logo }" /></td>
							<td>${ item.openType}</td>
							<td>${ item.action}</td>
							<td>${ item.params}</td>
							<td><c:choose>
									<c:when test="${item.isDefault  == 0}">
														否
												</c:when>
									<c:when test="${item.isDefault  == 1}">
														是
									</c:when>
							</c:choose></td> 
							<td><c:choose>
									<c:when test="${item.isDel  == 0}">
														不可以
												</c:when>
									<c:when test="${item.isDel  == 1}">
														可以
									</c:when>
							</c:choose></td> 
							<td><c:choose>
									<c:when test="${item.isOnline  == 0}">
														上线
												</c:when>
									<c:when test="${item.isOnline  == 1}">
														下线
									</c:when>
							</c:choose></td> 

							<td><img src="${ item.qrCode }" width="100" height="100" /></td>
							<%-- <td><c:choose>
									<c:when test="${item.enable  == 0}">
														不可用
												</c:when>
									<c:when test="${item.enable  == 1}">
														可用
												</c:when>
								</c:choose></td> --%>

							<td><timestampTag:timestamp patten="yyyy-MM-dd" t="${item.addTime * 1000}" /></td>


							<td>
								<button id="btn_update" onClick="btn_update('/op/appToolsForm?t_id=${item.tId}')" class="btn btn-primary btn-xs"
									title="修改">
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
				<c:param name="urlAddress" value="/op/appTools_list" />
			</c:import>
		</div>
	</div>
	<!-- page end--> </section> </section> <!--main content end--> <!--footer start--> <%@ include file="../shared/pageFooter.jsp"%>
	<!--footer end--> </section>

	<!-- js placed at the end of the document so the pages load faster -->
	<!--common script for all pages-->
	<%@ include file="../shared/importJs.jsp"%>

	<!--script for this page-->
	<script src="<c:url value='/js/simi/account/list.js'/>"></script>

</body>
</html>