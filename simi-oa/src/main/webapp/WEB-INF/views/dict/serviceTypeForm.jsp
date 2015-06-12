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
                             用户管理
                          </header>
                          
                   <!--        <hr style="width: 100%; color: black; height: 1px; background-color:black;" /> -->
                          
                          <div class="panel-body">
                              	<form:form modelAttribute="serviceTypeModel"
								class="form-horizontal" method="POST" action="serviceTypeForm"
								id="dict-form">

								<form:hidden path="id" />
								<div class="form-body">

									<div class="form-group required">

										<label class="col-md-2 control-label">名&nbsp;&nbsp;&nbsp;&nbsp;称</label>
										<div class="col-md-5">
											<form:input path="name" class="form-control" placeholder="名称"
												maxLength="32" />
											<form:errors path="name" class="field-has-error"></form:errors>
										</div>
									</div>
								</div>

								<div class="form-group required">

									<label class="col-md-2 control-label">关键词</label>
									<div class="col-md-5">
										<form:input path="keyword" class="form-control"
											placeholder="关键词" maxSize="10" />
										<form:errors path="keyword" class="field-has-error"></form:errors>
									</div>
								</div>

								<div class="form-group required">

									<label class="col-md-2 control-label">提示语</label>
									<div class="col-md-5">
										<form:input path="tips" class="form-control" placeholder="提示语"
											maxSize="10" />
										<form:errors path="tips" class="field-has-error"></form:errors>
									</div>
								</div>

								<div class="form-group required">

									<!-- Text input-->
									<label class="col-md-2 control-label">单&nbsp;&nbsp;&nbsp;&nbsp;价</label>
									<div class="col-md-5">
										<form:input path="price" class="form-control" placeholder="单价"
											maxSize="10" />
										<form:errors path="price" class="field-has-error"></form:errors>

									</div>
								</div>

								<div class="form-group required">

									<!-- Text input-->
									<label class="col-md-2 control-label">折&nbsp;&nbsp;&nbsp;&nbsp;扣</label>
									<div class="col-md-5">
										<form:input path="disPrice" class="form-control"
											placeholder="折扣" maxSize="10" />
										<form:errors path="disPrice" class="field-has-error"></form:errors>

									</div>
								</div>
								<div class="form-group">

									<!-- Text input-->
									<label class="col-md-2 control-label">服务类型介绍</label>
									<div class="col-md-5">
										<form:input path="descUrl" class="form-control"
											placeholder="备注说明" maxSize="10" />
										<form:errors path="descUrl" class="field-has-error"></form:errors>

									</div>
								</div>

								<div class="form-group required">

									<!-- Text input-->
									<label class="col-md-2 control-label">是否可用</label>
									<div class="col-md-10">

										<div class="row">
											<div class="col-md-2" align="right">
												<label class="radio"> <input value="0" name="enable" type="radio"> 不可用</label>
											</div>
											<div class="col-md-2" align="left">
												<label class="radio"> <input checked="checked" value="1" name="enable" type="radio"> 可用</label>
											</div>
										</div>
                                     </div>
                                    </div>


										<div class="form-actions fluid">
											<div class="col-md-offset-6 col-md-6">
												<button type="button" id="editServiceType_btn"
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
     	<script
		src="<c:url value='/assets/jquery-validation/dist/jquery.validate.min.js'/>"
		type="text/javascript"></script>
     <script src="<c:url value='/js/simi/dict/serviceTypeForm.js'/>"
		type="text/javascript"></script> 
	<script src="<c:url value='/js/simi/demo.js'/>"></script>

  </body>
</html>
