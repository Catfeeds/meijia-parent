<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	import="com.simi.oa.common.UrlHelper"%>
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
		
			
		<header class="panel-heading"> 互助问答
		<div class="pull-right">
			<button onClick="btn_add('/feed/feedAdd')" class="btn btn-primary" type="button">
				<i class="icon-expand-alt"></i>
				新增
			</button>
		</div>
		</header>
		<hr style="width: 100%; color: black; height: 1px; background-color: black;" />
		<table class="table table-striped table-advance table-hover">
			<thead>
				<tr>
					<th>发布时间</th>
					<th>用户头像</th>
					<th>用户</th>
					<th>标题</th>
					<th>悬赏</th>
					<th>标签</th>
					<th>回答数</th>
					<th>状态</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${contentModel.list}" var="item">
					<tr>
						<td><timestampTag:timestamp patten="yyyy-MM-dd HH:mm" t="${item.addTime * 1000}" /></td>
						<td><img src="${item.headImg }" width="50" height="50" /></td>
						<td>${ item.name }</td>
						<td>问:${ item.title }</td>
						<td>${ item.feedExtra }</td>
						<td><c:forEach items="${item.feedTags}" var="tag">
								${tag.tagName }&nbsp;
							</c:forEach></td>
						<td>${ item.totalComment }</td>
						<td>${ item.statusName }</td>
						<td>
							<button id="btn_update" onClick="btn_update('/feed/feedForm?fid=${item.fid}')" class="btn btn-primary btn-xs"
								title="修改">
								<i class="icon-pencil"></i>
							</button>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</section> <c:import url="../shared/paging.jsp">
		<c:param name="pageModelName" value="contentModel" />
		<c:param name="urlAddress" value="/feed/list" />
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