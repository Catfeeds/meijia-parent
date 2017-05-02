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
		<div class="col-lg-12">
			<section class="panel"> <header class="panel-heading">
			<h4>数据搜索</h4>
			<form:form modelAttribute="searchModel" action="list" method="GET">
				<div>
					标题：
					<form:input path="title" />
					精选：
					<form:select path="featured">
						<option value="">全部</option>
						<form:option value="0" label="否" />
						<form:option value="1" label="是" />
					</form:select>
					状态：
					<form:select path="status">
						<option value="">全部</option>
						<form:option value="0" label="进行中" />
						<form:option value="1" label="已完结" />
						<form:option value="1" label="关闭" />
					</form:select>
					<input type="submit" value="搜索">
				</div></header> </form:form> <header class="panel-heading"> 互助问答
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
						<th width="5%">精选</th>
						<th width="13%">发布时间</th>
						<th width="10%">用户头像</th>
						<th width="5%">用户</th>
						<th>标题</th>
						<th width="5%">悬赏</th>
						<th width="5%">标签</th>
						<th width="6%">回答数</th>
						<th width="6%">状态</th>
						<th width="10%">操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${contentModel.list}" var="item">
						<tr>
							<td><c:if test="${item.featured == 1 }">精</c:if></td>
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
								<button id="btn_star" onClick="btn_star(${item.fid}, ${item.featured})" class="btn btn-primary btn-xs"
									title="精选">
									<i class="icon-star"></i>
								</button>
								<button id="btn_feed_del" onClick="btn_feed_del(${item.fid})" class="btn btn-primary btn-xs" title="删除">
									<i class="icon-remove"></i>
								</button>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			</section>
			<c:import url="../shared/paging.jsp">
				<c:param name="pageModelName" value="contentModel" />
				<c:param name="urlAddress" value="/feed/list" />
			</c:import>
		</div>
	</div>
	<!-- page end--> </section> </section> <!--main content end--> <!--footer start--> <%@ include file="../shared/pageFooter.jsp"%>
	<!--footer end--> </section>
	<!-- js placed at the end of the document so the pages load faster -->
	<!--common script for all pages-->
	<%@ include file="../shared/importJs.jsp"%>
	<!--script for this page-->
	<script src="<c:url value='/js/simi/feed/feedList.js'/>"></script>
</body>
</html>