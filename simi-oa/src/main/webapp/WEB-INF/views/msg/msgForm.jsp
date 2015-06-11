<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ include file="../shared/taglib.jsp"%>

<html>
  <head>
	
	<!--common css for all pages-->
	<%@ include file="../shared/importCss.jsp"%>
	
	<!--css for this page-->
	<link rel="stylesheet" href="<c:url value='/assets/bootstrap-wysihtml5/bootstrap3-wysihtml5.min.css'/>" type="text/css" />

  </head>

  <body>

  <section id="container" >
	  
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
                      <section class="panel">
                          <header class="panel-heading">
                         	消息管理
                          </header>
                          
                          <hr style="width: 100%; color: black; height: 1px; background-color:black;" />
                          
                          <div class="panel-body">
                              <form:form modelAttribute="msgModel"
								class="form-horizontal" method="POST" action="msgForm"
								id="msg-form">

								<form:hidden path="id" />
								<input type="hidden" name="type" id="type"/>
								<div class="form-body">

									<div class="form-group required">

										<label class="col-md-2 control-label">标题</label>
										<div class="col-md-5">
											<form:input path="title" class="form-control" placeholder="标题" id="title"
												maxLength="32" />
											<form:errors path="title" class="field-has-error"></form:errors>
										</div>
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

									<label class="col-md-2 control-label">全文</label>
									<div class="col-md-9">
									<form:textarea  class="textarea" id="some-textarea" path="content"  placeholder="全文"></form:textarea>
									<form:errors path="content" class="field-has-error"></form:errors>
									</div>
								</div>
								<div class="form-group required">

									<!-- Text input-->
									<label class="col-md-2 control-label">用户群</label>
									<div class="col-md-10">

										<div class="row">
											<div class="col-md-2" align="right">
												<label class="radio"> <input value="0" name="sendGroup" type="radio"> 全部用户</label>
											</div>
											<div class="col-md-2" align="right">
												<label class="radio"> <input value="1" name="sendGroup" type="radio"> 未下单用户</label>
											</div>
											<div class="col-md-2" align="left">
												<label class="radio"> <input checked="checked" value="2" name="sendGroup" type="radio"> 已下单用户</label>
											</div>
										</div>
                                     </div>
                                    </div>

								<div class="form-group required">

									<!-- Text input-->
									<label class="col-md-2 control-label">是否有效</label>
									<div class="col-md-10">

										<div class="row">
											<div class="col-md-2" align="right">
												<label class="radio">
												<input value="0" name="isEnable" type="radio"> 无效</label>
											</div>
											<div class="col-md-2" align="left">
												<label class="radio">
												 <input checked="checked" value="1" name="isEnable" type="radio"> 有效</label>
											</div>
										</div>
                                     </div>
                                    </div>
								<div class="form-group required">

									<!-- Text input-->
									<label class="col-md-2 control-label">是否发送</label>
									<div class="col-md-10">

										<div class="row">
											<div class="col-md-2" align="right">
												<label class="radio">
												<input value="0" name="isSended" type="radio"> 发送</label>
											</div>
											<div class="col-md-2" align="left">
												<label class="radio">
												 <input checked="checked" value="1" name="isSended" type="radio"> 不发送</label>
											</div>
										</div>
                                     </div>
                                    </div>
									<div class="form-actions">
										<div class="row">
											<div class="col-md-6">
												<div class="col-md-offset-7">
													<button type="submit"  id="editMsg_btn" class="btn btn-success">保存</button>
												</div>
											</div>
											<div class="col-md-6">
												<div class="col-md-offset-0">
													<button type="button"   id="previewMsg_btn" class="btn btn-success">预览</button>

												</div>
											</div>
										</div>
									</div>

							</form:form>
                          </div>
                      </section>
                  </div>
              </div>
              <!-- page end-->
          </section>
      </section>
      <!--main content end-->
      
      <!--footer start-->
      <%@ include file="../shared/pageFooter.jsp"%>
      <!--footer end-->
  </section>

    <!-- js placed at the end of the document so the pages load faster -->
    <!--common script for all pages-->
    <%@ include file="../shared/importJs.jsp"%>


    <!--script for this page-->	
	<%-- <script src="<c:url value='/js/simi/demo.js'/>"></script> --%>
	<script src="<c:url value='/assets/jquery-validation/dist/jquery.validate.min.js'/>" type="text/javascript"></script>

	<script src="<c:url value='/js/simi/msg/msgForm.js'/>" type="text/javascript"></script>

	<script charset="utf-8" src="<c:url value='/assets/kindeditor-4.1.10/kindeditor.js'/>"></script>
	<script charset="utf-8" src="<c:url value='/assets/kindeditor-4.1.10/lang/zh_CN.js'/>"></script>

  </body>
</html>
