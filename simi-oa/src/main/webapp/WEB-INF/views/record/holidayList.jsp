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
			<form:form modelAttribute="searchModel"  method="GET">
				<header class="panel-heading">
				<h4>数据搜索</h4>
				<div>
					
					应用类型
					<form:select path="year">
						<c:forEach items="${years}" var="item">
							<option value="${item.value }">${item.name }</option>
						</c:forEach>
						
						
					</form:select>

					<input type="submit" value="搜索">
				</div>
				</header>
			</form:form>
			
			<header class="panel-heading"> 法定节假日 </header>
			<hr style="width: 100%; color: black; height: 1px; background-color: black;" />
			<table class="table table-striped table-advance table-hover">
				<thead>
					<tr>
						<th>年度</th>
						<th>日期</th>
						<th>假日</th>
						<th>描述</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${contentModel.list}" var="item">
						<tr>
							<td>${ item.year}</td>
							<td><timestampTag:timestamp patten="yyyy-MM-dd" t="${item.hdate * 1000}" /></td>
							<td>${ item.name }</td>
							<td>${ item.hdesc }</td>
							
						</tr>
					</c:forEach>
				</tbody>
			</table>
			</section>
			<c:import url="../shared/paging.jsp">
				<c:param name="pageModelName" value="contentModel" />
				<c:param name="urlAddress" value="/record/HolidayList" />
			</c:import>
		</div>
	</div>
	<!-- page end--> </section> </section> <!--main content end--> <!--footer start--> <%@ include file="../shared/pageFooter.jsp"%>
	<!--footer end--> </section>
	<!-- js placed at the end of the document so the pages load faster -->
	<!--common script for all pages-->
	<%@ include file="../shared/importJs.jsp"%>
	<!--script for this page-->
	
</body>
</html>