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
							<label class="col-md-2 control-label">用户:</label>
							<div class="col-md-5">
								<select id="userId">
									<option value="">选择提问用户</option>
									<c:forEach items="${userList}" var="u">
										<option value="${u.id}">${u.name }</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group required">
							<label class="col-md-2 control-label">悬赏:</label>
							<div class="col-md-5">
								<select id="feedExtra">
									<option value="0">0</option>
									<option value="10">10</option>
									<option value="20">20</option>
									<option value="50">50</option>
									<option value="100">100</option>									
								</select>
							</div>
						</div>
						<div class="form-group required">
							<label class="col-md-2 control-label">问题:</label>
							<div class="col-md-5">
								<textarea id="title" rows=20 cols=80></textarea>
							</div>
						</div>
						
						<div class="form-actions fluid">
							<div class="col-md-offset-6 col-md-6">
								<button type="button" id="saveBtn" class="btn btn-success">保存</button>
							</div>
						</div>
						
				</form:form>
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
	<script src="<c:url value='/js/simi/feed/feedAddForm.js'/>" type="text/javascript"></script>
</body>
</html>
