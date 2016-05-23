<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ include file="../shared/taglib.jsp"%>

<html>
<head>

<!--common css for all pages-->
<%@ include file="../shared/importCss.jsp"%>

<!--css for this page-->

</head>

<body>
	<!--header start-->
	<%@ include file="../shared/pageHeader.jsp"%>
	<!--header end-->

	<div class="am-cf admin-main">
		<!-- sidebar start -->
		<%@ include file="../hr/hr-menu.jsp"%>
		<!-- sidebar end -->

		<!-- content start -->
		<div class="admin-content">
			<div class="am-cf am-padding">
				<div class="am-fl am-cf">
					<strong class="am-text-primary am-text-lg">发布悬赏</strong> / <small>job</small>
				</div>
			</div>
			<hr>

			<div class="am-g">

				<div class="am-u-sm-12 am-u-md-4 am-u-md-push-8">
					<section class="am-panel am-panel-default"> <header class="am-panel-hd"> <img
						src="<c:url value='/assets/img/a1.png'/>" class="am-img-thumbnail am-circle" width="35" height="35">
					云小秘提示您 </header>
					<div class="am-panel-bd">可以一键扫码预约团建，快来试试吧</div>
					<div class="am-panel-bd"><img src="<c:url value='/assets/img/erweima.png'/>" width="250" height="250" /></div>
					</section>
				</div>

				<div class="am-u-sm-12 am-u-md-8 am-u-md-pull-4">
					<form:form modelAttribute="resumeExchangeVoModel" method="POST" id="job_hunter_form"
						class="am-form am-form-horizontal" enctype="multipart/form-data">
						
						<form:hidden path="id"/>
						
						<form:hidden path="userId"/>
						
						<div class="am-form-group">
							<label  class="am-u-sm-3 am-form-label">发布人:</label>
							<div class="am-u-sm-9">
								<form:input path="name" 
									class="am-form-field am-radius" readOnly="readOnly" />
							</div>
						</div>
						
													
						<div class="am-form-group">
							<label  class="am-u-sm-3 am-form-label">选择所在城市:</label>
							<div class="am-u-sm-9">
								<input type="hidden" value="${resumeExchangeVoModel.ipTransCity }">
								<form:select path="cityName" class="am-form-field am-radius">
									
									<!-- 新增时，默认选中 ip 所在城市 -->
									<c:if test="${resumeExchangeVoModel.id == 0 }">
										<c:forEach items="${resumeExchangeVoModel.citySelectMap }" var="items">
											<option value="${items.key }" 
											
											<c:if test="${resumeExchangeVoModel.ipTransCity eq items.key }">
												selected="true"
											</c:if>	
											
											>${items.value }</option>	
										</c:forEach>
									</c:if>
									
								    <!-- 修改时, 回显字段值 -->
									<c:if test="${resumeExchangeVoModel.id > 0 }">
										<form:options items="${resumeExchangeVoModel.citySelectMap}" />
									</c:if>
								</form:select>
							</div>
						</div>	
						
							
						<div class="am-form-group">
							<label  class="am-u-sm-3 am-form-label">标题:</label>
							<div class="am-u-sm-9">
								<form:input path="title"
									class="am-form-field am-radius" autocomplete="off"
									maxLength="64" required="required" />
								<small>*必填项</small>
							</div>
						</div>
						
						
						<div class="am-form-group">
							<label  class="am-u-sm-3 am-form-label">有效期:</label>
							<div class="am-u-sm-9">
								<form:select path="limitDay" class="am-form-field am-radius">
									<form:options items="${resumeExchangeVoModel.timeMap}" />
								</form:select>
							</div>
						</div>						
						
						
						
						<div class="am-form-group">
							<label  class="am-u-sm-3 am-form-label">需求内容:</label>
							<div class="am-u-sm-9">
								<form:textarea path="content" class="form-control am-form-field am-radius"  
									maxlength="1000" required="required"
									placeholder="请输入需求内容,不超过1000字" />
								<small>*必填项</small>		
							</div>
						</div>
						
						<div class="am-form-group">
							<label  class="am-u-sm-3 am-form-label">联系方式:</label>
							<div class="am-u-sm-9">
								<form:textarea path="link" class="form-control"
									 maxlength="200"	required="required"
									 placeholder="请输入联系方式,不超过200字" />
								<small>*必填项</small>		 
							</div>
						</div>
						
						
						<hr>
						<div class="am-form-group">
							<div class="am-u-sm-9 am-u-sm-push-3">
								<button type="button" class="am-btn am-btn-danger" id="btn-team-submit">提交</button>
								<button type="button" class="am-btn am-btn-success" id="btn-return">返回</button>
							</div>
						</div>
					</form:form>
				</div>
			</div>
		</div>
		<!-- content end -->

	</div>

	<a href="#" class="am-icon-btn am-icon-th-list am-show-sm-only admin-menu"
		data-am-offcanvas="{target: '#admin-offcanvas'}"></a>

	<!--footer start-->
	<%@ include file="../shared/pageFooter.jsp"%>
	<!--footer end-->

	<%@ include file="../shared/importJs.jsp"%>

	<!--script for this page-->
	<script src="<c:url value='/assets/js/xcloud/common/validate-methods.js'/>" type="text/javascript"></script>
	<script src="<c:url value='/assets/js/xcloud/hr/resume-exchange-form.js'/>"></script>
</body>
</html>
