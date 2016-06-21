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
                             简历来源定义
                          </header>
                          
                          <hr style="width: 100%; color: black; height: 1px; background-color:black;" />
                          
                          <div class="panel-body">
                              <form:form modelAttribute="contentModel" class="form-horizontal"
								method="POST"  id="contentForm">

								<form:hidden path="fromId" />
								<div class="form-body">

									<div class="form-group required">

										<label class="col-md-2 control-label">名称</label>
										<div class="col-md-5">
											<form:input path="name" class="form-control" placeholder="名称"
												maxLength="32" />
											<form:errors path="name" class="field-has-error"></form:errors>
										</div>
									</div>

									
									<div class="form-group required">

										<label class="col-md-2 control-label">网址</label>
										<div class="col-md-5">
											<form:input path="webUrl" class="form-control"
												placeholder="主页/域名" maxLength="1024" />
											<form:errors path="webUrl" class="field-has-error"></form:errors>
										</div>
									</div>
									
									<div class="form-group required">
										<label class="col-md-2 control-label">搜索入口</label>
										<div class="col-md-5">
											<form:input path="searchUrl" class="form-control"
												placeholder="搜索的url" maxLength="1024" />
											<form:errors path="searchUrl" class="field-has-error"></form:errors>
										</div>
									</div>
									
									<div class="form-group required">
										<label class="col-md-2 control-label">匹配分页</label>
										<div class="col-md-5">
											<form:input path="matchPage" class="form-control"
												placeholder="匹配分页表达式" maxLength="255" />
											<form:errors path="matchPage" class="field-has-error"></form:errors>
										</div>
									</div>
									
									<div class="form-group required">
										<label class="col-md-2 control-label">匹配详情链接</label>
										<div class="col-md-5">
											<form:input path="matchLink" class="form-control"
												placeholder="匹配详情链接表达式" maxLength="255" />
											<form:errors path="matchLink" class="field-has-error"></form:errors>
										</div>
									</div>

									<div class="form-actions fluid">
										<div class="col-md-offset-6 col-md-6">
											<button type="submit" id="btn-save"
												class="btn btn-success">保存</button>

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
	<script src="<c:url value='/assets/jquery-validation/dist/jquery.validate.min.js'/>" type="text/javascript"></script>
	<script src="<c:url value='/js/simi/resume/dictFromForm.js'/>"type="text/javascript"></script>


  </body>
</html>
