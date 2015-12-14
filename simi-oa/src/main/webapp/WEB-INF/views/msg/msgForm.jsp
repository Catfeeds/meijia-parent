<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.simi.oa.common.UrlHelper"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ include file="../shared/taglib.jsp"%>
<%@ taglib prefix="citySelectTag" uri="/WEB-INF/tags/citySelect.tld"%>
<html>
<head>

<!--common css for all pages-->
<%@ include file="../shared/importCss.jsp"%>

<!--css for this page-->
<link rel="stylesheet" href="<c:url value='/css/fileinput.css'/>"
	type="text/css" />

<link
	href="<c:url value='/assets/bootstrap-datepicker/css/bootstrap-datepicker3.min.css'/>"
	rel="stylesheet" type="text/css" />
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
			消息管理 </header>

			<hr
				style="width: 100%; color: black; height: 1px; background-color: black;" />

			<div class="panel-body">
				<form:form modelAttribute="contentModel"
								class="form-horizontal" method="POST" action="saveMsgForm"
								id="msg-form">

								<form:hidden path="msgId" />
								<input type="hidden" name="type" id="type"/>
									<div class="form-group required">
										<label class="col-md-2 control-label">标题</label>
										<div class="col-md-5">
											<form:input path="title" class="form-control" placeholder="标题" id="title"
												maxLength="32" />
											<form:errors path="title" class="field-has-error"></form:errors>
										</div>
									</div>
								<div class="form-group required">
									<label class="col-md-2 control-label">摘要</label>
									<div class="col-md-5">
										<form:input path="summary" class="form-control" id="summary"
											placeholder="摘要" maxSize="10" />
										<form:errors path="summary" class="field-has-error"></form:errors>
									</div>
								</div>
								
								<div class="form-group required">
									<label class="col-md-2 control-label">消息详细内容</label>
									<div class="col-md-9">
									<form:textarea  class="textarea" id="some-textarea" path="content"  placeholder="全文"></form:textarea>
									<form:errors path="content" class="field-has-error"></form:errors>
									</div>
								</div>
									<div class="form-group required">
										<label class="col-md-2 control-label">跳转url</label>
										<div class="col-md-5">
											<form:input path="gotoUrl" class="form-control" placeholder="跳转url" id="gotoUrl"
												maxLength="32" />
											<form:errors path="gotoUrl" class="field-has-error"></form:errors>
										</div>
									</div>
								
									<div class="form-group required">
										<label class="col-md-2 control-label">用户类型</label>
										<div class=col-md-5>
												<form:select path="userType" class="form-control">
												<form:option value="" label="请选择用户类型"/>  
												<form:option value="0" label="普通用户"/>  
												<form:option value="1" label="秘书"/>  
												<form:option value="2" label="服务商"/>  
												</form:select>
										</div>
									</div>
								
									<div class="form-group required">
										<label class="col-md-2 control-label">发送时间</label>
										
										<div class="col-md-5">
										<!-- <div class="input-group date"> -->

									<%-- <fmt:formatDate var='formattedDate' value='${contentModel.sendTime}' type='both'
										pattern="yyyy-MM-dd" /> --%>
									<form:input path="isSend" class="form-control form_datetime" style="width:110px;" readonly="true"  />
									<%-- <input type="text" path="sendTime" id="sendTime" name="sendTime"
										value="${formattedDate}" readonly class="form-control"><span
										class="input-group-addon"><i
										class="glyphicon glyphicon-th"></i></span>
								     </div> --%>
										</div>
								</div>
									<div class="form-group required">
										<label class="col-md-2 control-label">应用类型</label>
										<div class=col-md-5>
												<form:select path="appType" class="form-control">
												<form:option value="" label="请选择应用类型"/>  
												<form:option value="0" label="jhj-am"/>  
												<form:option value="1" label="jhj-u"/>  
												</form:select>
										</div>
									</div>
								
								<div class="form-group required">
									<!-- Text input-->
									<label class="col-md-2 control-label">是否可用</label>
									<div class="col-md-10">
										<div class="row">
											<div class="col-md-2" align="right">
												<label class="radio">
												<input value="0" name="isEnable" type="radio">不可用</label>
											</div>
											<div class="col-md-2" align="left">
												<label class="radio">
												 <input checked="checked" value="1" name="isEnable" type="radio">可用</label>
											</div>
										</div>
                                     </div>
                                    </div>
									<div class="form-actions">
										<div class="row">
											<div class="col-md-6">
												<div class="col-md-offset-7">
													<button type="submit" id="editMsg_btn" class="btn btn-success">保存</button>
												</div>
											</div>
										
										</div>
									</div>
							</form:form>
							</div>
						</section>
		</div>
	</div>
	<!-- page end--> </section> </section> <!--main content end--> <!--footer start--> <%@ include
		file="../shared/pageFooter.jsp"%> <!--footer end-->
	</section>

	<!-- js placed at the end of the document so the pages load faster -->
	<!--common script for all pages-->
	<%@ include file="../shared/importJs.jsp"%>


	<!--script for this page-->
	<script type="text/javascript"
		src="<c:url value='/assets/bootstrap-datepicker/js/bootstrap-datepicker.min.js'/>"></script>
	<script type="text/javascript"
		src="<c:url value='/assets/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js'/>"></script>
	<script
		src="<c:url value='/assets/jquery-validation/dist/jquery.validate.min.js'/>"
		type="text/javascript"></script>
	<script type="text/javascript"
		src="<c:url value='/assets/bootstrap-fileupload/fileinput.min.js'/>"></script>

	<%-- <script src="<c:url value='/js/simi/sec/listForm.js'/>"
		type="text/javascript"></script> --%>

	<script src="<c:url value='/js/simi/demo.js'/>"></script>

</body>
</html>
