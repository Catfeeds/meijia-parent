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
			<section class="panel"> <header class="panel-heading"> 互助问答 </header>
			<hr style="width: 100%; color: black; height: 1px; background-color: black;" />
			<div class="panel-body">
				<form:form modelAttribute="contentModel" class="form-horizontal" method="POST" id="feed-form">
					<div class="form-body">
						<div class="form-group required">
							<label class="col-md-2 control-label">时间:</label>
							<div class="col-md-5">
								<label class="control-label"> <timestampTag:timestamp patten="yyyy-MM-dd HH:mm"
										t="${contentModel.addTime * 1000}" />
								</label>
							</div>
						</div>
						<div class="form-group required">
							<label class="col-md-2 control-label">用户头像:</label>
							<div class="col-md-5">
								<img src="${contentModel.headImg }" width="50" height="50" />
							</div>
						</div>
						<div class="form-group required">
							<label class="col-md-2 control-label">用户:</label>
							<div class="col-md-5">
								<label class="control-label"> ${contentModel.name } </label>
							</div>
						</div>
						<div class="form-group required">
							<label class="col-md-2 control-label">问:</label>
							<div class="col-md-5">${contentModel.title }</div>
						</div>
						<div class="form-group required">
							<label class="col-md-2 control-label">悬赏:</label>
							<div class="col-md-5">${contentModel.feedExtra }</div>
						</div>
						<div class="form-group required">
							<label class="col-md-2 control-label">标签:</label>
							<div class="col-md-5">
								<label class="control-label"> <c:forEach items="${contentModel.feedTags}" var="tag">
									${tag.tagName }&nbsp;
								</c:forEach>
								</label>
							</div>
						</div>
				</form:form>
			</div>
			</section>
			<hr />
			<section class="panel"> <header class="panel-heading"> 答案列表 </header>
			<hr style="width: 100%; color: black; height: 1px; background-color: black;" />
			<div class="panel-body">
				<div class="timeline-messages">
					<c:forEach items="${commentList}" var="item">
					<!-- Comment -->
					<div class="msg-time-chat">
						<a href="#" class="message-img">
							<img class="avatar" src="${item.headImg}" alt="" width="50" height="50">
						</a>
						<div class="message-body msg-in">
							<span class="arrow"></span>
							<div class="text">
								<p class="attribution">
									<a href="#">${item.name }</a>
									<timestampTag:timestamp patten="yyyy-MM-dd HH:mm" t="${item.addTime * 1000}" />
								</p>
								<p>${item.comment }</p>
								
								<c:if test="${item.status == 1 }">
									<p><font color="red>已采纳</font></p>
								</c:if>
								
							</div>
						</div>
					</div>
					<!-- /comment -->
					</c:forEach>
				</div>
				<hr>
				<form id="commentForm" class="form-horizontal">
					<input type="hidden" id="fid" name="fid" value="${contentModel.fid}"/>
					<div class="form-group">
						<div class="col-md-5">
							<select id="userId">
								<option value="">选择回答用户</option>
								<c:forEach items="${userList}" var="u">
									<option value="${u.id}">${u.name }</option>
								</c:forEach>
							</select>
						</div>
					</div>
					
					<div class="form-group">
						<div class="col-md-5">
						<textarea id="comment" rows=10 cols=20  class="form-control"></textarea>
						</div>
					</div>
					
					<div class="form-group">
						<div class="col-md-5">
							<a class="btn btn-danger" onclick="feedComment()">回答</a>
							
							<a class="btn btn-danger" href="/simi-oa/feed/list">返回</a>
						</div>
					</div>
				</form>
			</div>
			</section>
		</div>
	</div>
	<!-- page end--> </section> </section> <!--main content end--> <!--footer start--> <%@ include file="../shared/pageFooter.jsp"%>
	<!--footer end--> </section>
	<!-- js placed at the end of the document so the pages load faster -->
	<!--common script for all pages-->
	<%@ include file="../shared/importJs.jsp"%>
	<!--script for this page-->
	<script src="<c:url value='/js/simi/feed/feedForm.js'/>" type="text/javascript"></script>
</body>
</html>
