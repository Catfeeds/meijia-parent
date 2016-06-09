<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.simi.oa.common.UrlHelper"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ include file="../shared/taglib.jsp"%>
<html>
<head>

<!--common css for all pages-->
<%@ include file="../shared/importCss.jsp"%> 

<!--css for this page-->
<link rel="stylesheet" href="<c:url value='/assets/bootstrap-datetimepicker/css/datetimepicker.css'/>"
	type="text/css" />
</head>

<body>

	<section id="container"> 
	<!--header start--> 
	<%@ include file="../shared/pageHeader.jsp"%> 
	<!--header end-->

	<!--sidebar start--> 
	<%@ include file="../shared/sidebarMenu.jsp"%>
	<!--sidebar end--> 
	<!--main content start--> 
	<section id="main-content">
	<section class="wrapper"> 
	<!-- page start-->
	<div class="row">
		<div class="col-lg-12">
			<section class="panel"> <header class="panel-heading">
			消息管理 </header>

			<hr
				style="width: 100%; color: black; height: 1px; background-color: black;" />

			<div class="panel-body">
				<form:form modelAttribute="contentModel" class="form-horizontal"
					method="POST" action="saveMsgForm" id="msg-form">

					<form:hidden path="msgId" />
					<form:hidden path="isSend" />
					
					<input type="hidden" name="type" id="type" />
					
					 <div class="form-group required">
						<label class="col-md-2 control-label">标题</label>
						<div class="col-md-5">
							<form:input path="title" class="form-control" placeholder="标题"
								id="title" maxLength="32" />
							<form:errors path="title" class="field-has-error"></form:errors>
						</div>
					</div>
					
					<%--
					<div class="form-group required">
						<label class="col-md-2 control-label">摘要</label>
						<div class="col-md-5">
							<form:input path="summary" class="form-control" id="summary"
								placeholder="摘要" maxSize="10" />
							<form:errors path="summary" class="field-has-error"></form:errors>
						</div>
					</div> --%>

					<div class="form-group required">
						<label class="col-md-2 control-label">消息详细内容</label>
						<div class="col-md-9">
							<form:textarea class="textarea" path="content"  rows="4" cols="60"
								placeholder="消息内容,不超过120字" maxlength="120"></form:textarea>
						</div>
					</div>


					<div class="form-group required">
						<label class="col-md-2 control-label">跳转类型*</label>
						<div class="col-md-5">
							<form:select path="category" class="form-control">
								<option value="">请选择</option>
								<form:option value="h5">h5</form:option>
								<form:option value="app">app</form:option>
							</form:select>
						</div>
					</div>

					<div class="form-group required">
						<label class="col-md-2 control-label">跳转url</label>
						<div class="col-md-5">
							<form:input path="gotoUrl" class="form-control"
								placeholder="跳转url"  maxLength="512" />
							<form:errors path="gotoUrl" class="field-has-error"></form:errors>
						</div>
					</div>

					<div class="form-group required">

						<label class="col-md-2 control-label">动作标识</label>
						<div class="col-md-5">
							<form:input path="action" class="form-control" placeholder="动作标识,不超过32字"
								maxLength="32" />
							<form:errors path="action" class="field-has-error"></form:errors>
						</div>
					</div>
					<div class="form-group required">

						<label class="col-md-2 control-label">操作相关参数</label>
						<div class="col-md-5">
							<form:input path="params" class="form-control" placeholder="操作相关参数,不超过32字"
								maxLength="32" />
							<form:errors path="params" class="field-has-error"></form:errors>
						</div>
					</div>


					<div class="form-group required">
						<label class="col-md-2 control-label">用户类型*</label>
						<div class=col-md-5>
							<form:select path="userType" class="form-control">
								<form:option value="" label="请选择用户类型" />
								<form:option value="0" label="普通用户" />
								<form:option value="1" label="秘书" />
								<form:option value="2" label="服务商" />
								<form:option value="3" label="全部用户"/>
								<form:option value="4" label="测试用户"/>
							</form:select>
						</div>
						
						<div class="col-md-5" id="useTypeTip" style="display:none">
							<font color="red">只发送给最近一个月登录过的用户</font>
						</div>
					</div>

					
					<div class="form-group required">
						<label class="col-md-2 control-label">应用类型</label>
						<div class=col-md-5>
							
							<form:select path="appType" class="form-control">
								<form:option value="" label="请选择应用类型" />
								<form:option value="xcloud" label="xcloud"/>
								<form:option value="simi" label="simi"/>
								<form:option value="timechick" label="timechick"/>
							</form:select>
							
						</div>
					</div>

					<div class="form-group required">
						<label class="col-md-2 control-label">是否可用</label>
						<div class="col-md-10">
							<form:radiobutton path="isEnable" value="1" label="可用" />
							<form:radiobutton path="isEnable" value="0" label="不可用" />
						</div>
					</div>
					
					
					<div class="form-group required" id="sendWayDiv" >
						<label class="col-md-2 control-label"><font color="green">选择发送方式</font></label>
						<div class=col-md-5>
							<form:select path="sendWay" class="form-control">
								<form:option value="0" label="测试并立即发送"/>
								<form:option value="1" label="保存并立即发送"/>
								<form:option value="2" label="测试并定时发送"/>
								<form:option value="3" label="保存并定时发送"/>
							</form:select>
						</div>
						
						<div class="col-md-5" id="sendWayTestTip" style="display:none">
							<font color="red">只发送给运营部人员</font>
						</div>
						
						<!-- 默认是 保存并立即发送 -->	
						<div class="col-md-5" id="sendWaySaveTip" >
							<font color="red">发送给选择的用户类型</font>
						</div>
					</div>
					
					
					<div class="form-group required" style="display:none"  id="sendTimeDiv">
						<label class="col-md-2 control-label">发送时间</label>
						<div class="col-md-5">
							 <fmt:formatDate var='formattedDate' value='${sendTimeDate}' type='both'
											pattern="yyyy-MM-dd HH:mm" />  
							
								<input type="text" name="sendTime" id="sendTime"
											value="${formattedDate }" readonly class="form-control form_datetime">
						</div>
					</div> 
					
					
					
					
					
					
					<div class="form-actions">
						<div class="row">
							<div class="col-md-6">
								<div class="col-md-offset-7">
									<button type="button" id="editMsg_btn" class="btn btn-success">保存</button>
								</div>

							</div>
							
							<div class="col-md-5" id="sendStatusTip" style="display:none">
								<font color="red"></font>
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
	
	<script type="text/javascript"	src="<c:url value='/assets/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js'/>"></script>
	<script type="text/javascript"	src="<c:url value='/assets/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js'/>"></script>
	
	<script
		src="<c:url value='/assets/jquery-validation/dist/jquery.validate.min.js'/>"
		type="text/javascript"></script>
	<script type="text/javascript"
		src="<c:url value='/assets/bootstrap-fileupload/fileinput.min.js'/>"></script>

	<script src="<c:url value='/js/simi/msg/msgForm.js'/>"></script>

</body>
</html>
