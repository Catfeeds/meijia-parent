<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ include file="../shared/taglib.jsp"%>
<%@ taglib prefix="timestampTag" uri="/WEB-INF/tags/timestamp.tld"%>
<%@ taglib prefix="cityNameTag" uri="/WEB-INF/tags/cityName.tld"%>
<%@ taglib prefix="sexTypeNameTag" uri="/WEB-INF/tags/sexTypeName.tld"%>
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
			<section class="panel"> <header class="panel-heading">
			<form:form modelAttribute="userSearchVoModel" action="applyList" method="GET">
                          <header class="panel-heading">
                          	<h4>数据搜索</h4>
                          		<div>
                          					用户昵称：<form:input path="name" />
                          					手机号：<form:input path="mobile"/>
									<input type="submit"  value="搜索"  >
								</div>   
                          </header>
                           </form:form>   
                          <hr style="width: 100%; color: black; height: 1px; background-color:black;" />
                          <header class="panel-heading">  
			秘书列表
<!-- 
			<div class="pull-right">
				<button onClick="btn_add('/sec/listForm?id=0')"
					class="btn btn-primary" type="button">
					<i class="icon-expand-alt"></i>新增
				</button>
			</div> -->
			</header>



			<table class="table table-striped table-advance table-hover">
				<thead>
					<tr>
						<th>姓名</th>
						<th>手机号</th>
						<th>昵称</th>
						<!-- <th>性别</th> -->
						<!-- <th>出生日期</th> -->
						<!-- <th>学历</th>
						<th>头像</th>
						<th>所在城市</th> -->
					
						<th>审批情况</th>
						<th>添加时间</th>
						<th>操作</th>

					</tr>
				</thead>
				<tbody>
					<c:forEach items="${contentModel.list}" var="item">
						<tr>
							<td>${ item.realName}</td>
							<td>${ item.mobile}</td>
							<td>${ item.name}</td>
							<%-- <c:if test="${ item.orderStatus < 3 }">
							            		<orderStatusTag:orderstatus orderStatus="${ item.orderStatus }"/>
							            	</c:if> --%>
		                   <%--  <td>
		                                <c:choose>
												<c:when test="${ item.sex  == 0}">
														男
												</c:when>
												<c:when test="${ item.sex  == 1}">
														女
												</c:when>
										</c:choose>	
		                                </td> --%>
							<%-- <td><fmt:formatDate value="${ item.birthDay}"
									pattern="yyyy-MM-dd" /></td> --%>
							<%-- <td>${ item.degreeId}"</td>
							<td><img src="${ item.headImg}" /></td>
							<td>${ item.provinceName}"</td> --%>
						<td>
		                                <c:choose>
												<c:when test="${item.isApproval  == 0}">
														审核未通过
												</c:when>
												<c:when test="${item.isApproval  == 1}">
														信息不符
												</c:when>
												<c:when test="${item.isApproval  == 2}">
														审核已通过
												</c:when>
												
										</c:choose>	
		                                </td>
							<td><timestampTag:timestamp patten="yyyy-MM-dd"
									t="${item.addTime * 1000}" /></td>
							<td>
								<button id="btn_update"
									onClick="btn_update('/sec/applyForm?id=${item.id}')"
									class="btn btn-primary btn-xs" title="查看">
									<i class=" icon-ambulance"></i>
								</button>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>


			</section>

			<c:import url="../shared/paging.jsp">
				<c:param name="pageModelName" value="contentModel" />
				<c:param name="urlAddress" value="/sec/list" />
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