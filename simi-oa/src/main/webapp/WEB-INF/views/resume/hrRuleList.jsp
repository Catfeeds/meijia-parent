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
			<section class="panel"> <form:form modelAttribute="searchModel" action="hrRuleList" method="GET">
				<header class="panel-heading">
				<h4>数据搜索</h4>
				<div>
					简历来源：
					<form:select path="fromId">
						<option value="">全部</option>
						<form:options items="${hrFroms}" itemValue="fromId" itemLabel="name" />
					</form:select>
					匹配字段
					<form:select path="matchDictId">
						<option value="">全部</option>
						<form:options items="${hrRuleDicts}" itemValue="id" itemLabel="name" />
					</form:select>
					<input type="submit" value="搜索">
				</div>
				</header>
			</form:form> <header class="panel-heading"> 简历解析规则库

			<div class="pull-right">
				<button onClick="btn_add('/resume/hrRuleForm?id=0')" class="btn btn-primary" type="button">
					<i class="icon-expand-alt"></i>新增
				</button>
			</div>
			</header>

			<hr style="width: 100%; color: black; height: 1px; background-color: black;" />

			<table class="table table-striped table-advance table-hover">
				<thead>
					<tr>
						<th>简历来源</th>
						<th>匹配字段</th>
						<th>文件类型</th>
						<th>匹配类型</th>
						<th>样本</th>
						<th>匹配数</th>
						<th>创建时间</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${contentModel.list}" var="item">
						<tr>
							<td>${ item.fromName}</td>
							<td>${ item.matchDictName}</td>
							<td>${ item.fileType }</td>
							<td>${ item.matchType }</td>
							<td><a href="/resume/app/downloadRuleFile?ruleType=rule&id=${item.id }">下载</a></td>
							<td>${ item.totalMatch }</td>
							<td><timestampTag:timestamp patten="yyyy-MM-dd"
									t="${item.addTime * 1000}" /></td>
							<td>
								<button id="btn_update" onClick="btn_update('/resume/hrRuleForm?id=${item.id}')"
									class="btn btn-primary btn-xs" title="修改"
								>
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
				<c:param name="urlAddress" value="/resume/hrRuleList" />
			</c:import>
		</div>
	</div>
	<!-- page end--> </section> </section> <!--main content end--> <!--footer start--> 
	<%@ include file="../shared/pageFooter.jsp"%>
	<!--footer end--> </section>

	<!-- js placed at the end of the document so the pages load faster -->
	<!--common script for all pages-->
	<%@ include file="../shared/importJs.jsp"%>

</body>
</html>