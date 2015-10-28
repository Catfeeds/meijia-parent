<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ include file="../shared/taglib.jsp"%>

<html>
  <head>
	
	<!--common css for all pages-->
	<%@ include file="../shared/importCss.jsp"%>
	
	<!--css for this page-->
	<link rel="stylesheet" href="<c:url value='/css/fileinput.css'/>"
	type="text/css" />

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
                             用户管理
                          </header>
                          
                          <hr style="width: 100%; color: black; height: 1px; background-color:black;" />
                          
                          <div class="panel-body">
                              <form:form modelAttribute="adModel" class="form-horizontal"
								method="POST" action="adForm" id="dict-form"
								enctype="multipart/form-data">

								<form:hidden path="id" />
								<div class="form-body">

									<div class="form-group required">

										<label class="col-md-2 control-label">序号</label>
										<div class="col-md-5">
											<form:input path="No" class="form-control" placeholder="序号"
												maxLength="32" />
											<form:errors path="No" class="field-has-error"></form:errors>
										</div>
									</div>

									<c:if test="${adModel.imgUrl != null && adModel.imgUrl != '' }">
										<div class="form-group ">

											<label class="col-md-2 control-label">图片</label>
											<div class="col-md-5">
												<img src="${ adModel.imgUrl }"/>
											</div>
										</div>
									</c:if>

									<div class="form-group required">

										<label class="col-md-2 control-label">图片地址</label>
										<div class="col-md-5">
											<input id="imgUrl" type="file" name="imgUrl" accept="image/*"
												data-show-upload="false">
											<form:errors path="imgUrl" class="field-has-error"></form:errors>
										</div>
									</div>
									<div class="form-group required">

										<label class="col-md-2 control-label">跳转地址</label>
										<div class="col-md-5">
											<form:input path="gotoUrl" class="form-control"
												placeholder="跳转地址" maxLength="255" />
											<form:errors path="gotoUrl" class="field-has-error"></form:errors>
										</div>
									</div>

									<div class="form-group required">

										<label class="col-md-2 control-label">广告类型</label>
										<div class="col-md-5">
											
										</div>
									</div>

									<div class="form-group required">

										<!-- Text input-->
										<label class="col-md-2 control-label">是否可用</label>
										<div class="col-md-10">

											<div class="row">
												<div class="col-md-2" align="right">
													<label class="radio"> <input value="0" name="enable"
														type="radio"> 不可用
													</label>
												</div>
												<div class="col-md-2" align="left">
													<label class="radio"> <input checked="checked"
														value="1" name="enable" type="radio"> 可用
													</label>
												</div>
											</div>

										</div>
									</div>

									<div class="form-actions fluid">
										<div class="col-md-offset-6 col-md-6">
											<button type="submit" id="adForm_btn"
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
    <script type="text/javascript"
		src="<c:url value='/assets/bootstrap-fileupload/fileinput.min.js'/>"></script>
	<script
		src="<c:url value='/assets/jquery-validation/dist/jquery.validate.min.js'/>"
		type="text/javascript"></script>
	<script src="<c:url value='/js/simi/dict/adForm.js'/>"
		type="text/javascript"></script>
	<script src="<c:url value='/js/simi/demo.js'/>"></script>

  </body>
</html>
