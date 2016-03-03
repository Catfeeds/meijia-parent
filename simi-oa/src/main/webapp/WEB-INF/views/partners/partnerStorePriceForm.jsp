<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.simi.oa.common.UrlHelper"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ include file="../shared/taglib.jsp"%>

<html>
<head>

<!--common css for all pages-->
<%@ include file="../shared/importCss.jsp"%>

<!--css for this page-->

<link rel="stylesheet" href="<c:url value='/css/fileinput.css'/>" type="text/css" />

<link rel="stylesheet" href="<c:url value='/assets/data-tables/DT_bootstrap.css'/>"
<%@ include file="../shared/importJs.jsp"%>

<script src="<c:url value='/js/jquery.treeLite.js?ver=10' /> " type="text/javascript"></script>
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
			服务价格 </header>

			<hr
				style="width: 100%; color: black; height: 1px; background-color: black;" />
 				<form:form modelAttribute="contentModel" 
 				enctype="multipart/form-data" class="form-horizontal" 
 				method="POST" id="partner-service-price-form">
 				
 						<form:hidden path="userId" />
 						<%-- <form:hidden path="parentId" />
 						<form:hidden path="servicePriceId" /> --%>
                        <div class="form-body">
                        
                        	<div class="form-group">
                              <label  class="col-md-2 control-label">排序&nbsp;*</label>
                              <div class="col-md-5">
                                 <form:input path="no" class="form-control" placeholder="排序"/><br/>
                                 <form:errors path="no" class="field-has-error"></form:errors>
                              </div>
                           </div>
                        
                           <div class="form-group">
                              <label  class="col-md-2 control-label">名称&nbsp;*</label>
                              <div class="col-md-5">
                                 <form:input path="name" class="form-control" placeholder="名称"/><br/>
                                 <form:errors path="name" class="field-has-error"></form:errors>
                              </div>
                           </div>
                           
                           <c:if test="${contentModel.imgUrl != null && contentModel.imgUrl != '' }">
								<div class="form-group ">
	
									<label class="col-md-2 control-label">图片</label>
									<div class="col-md-5">
										<img src="${ contentModel.imgUrl }?w=300&h=300"/>
									</div>
								</div>
							</c:if>

						<div class="form-group required">

							<label class="col-md-2 control-label">图片地址(尺寸为640 × 370)</label>
							<div class="col-md-5">
								<input id="imgUrlFile" type="file" name="imgUrlFile" accept="image/*"
									data-show-upload="false">
								<form:errors path="imgUrl" class="field-has-error"></form:errors>
							</div>
						</div>
                           
                           <div class="form-group">
                              <label  class="col-md-2 control-label">副标题&nbsp;*</label>
                              <div class="col-md-5">
                                 <form:input path="serviceTitle" class="form-control" placeholder="副标题"/><br/>
                                 <form:errors path="serviceTitle" class="field-has-error"></form:errors>
                              </div>
                           </div>
                           
                           <div class="form-group">
                              <label  class="col-md-2 control-label">原价&nbsp;*</label>
                              <div class="col-md-5">
                                 <form:input path="price" class="form-control" placeholder="名称"/><br/>
                                 <form:errors path="price" class="field-has-error"></form:errors>
                              </div>
                           </div>
                           
                           <div class="form-group">
                              <label  class="col-md-2 control-label">现价&nbsp;*</label>
                              <div class="col-md-5">
                                 <form:input path="disPrice" class="form-control" placeholder="名称"/><br/>
                                 <form:errors path="disPrice" class="field-has-error"></form:errors>
                              </div>
                           </div>
                           
                           <div class="form-group">
                              <label  class="col-md-2 control-label">订单类型*</label>
                              <div class="col-md-5">
                                 <form:select path="orderType" class="form-control">
									<form:option value="0" label="通用订单"/>  
									<form:option value="1" label="时段订单"/> 
								 </form:select>
                              </div>
                           </div>
                           
                           <div class="form-group" id="orderDurationSelectBox" style="display:none">
                              <label  class="col-md-2 control-label">订单时长*</label>
                              <div class="col-md-5">
                                 <form:select path="orderDuration" class="form-control" >
									<form:option value="1" label="一天"/>  
									<form:option value="2" label="一周"/> 
									<form:option value="3" label="一个月"/> 
									<form:option value="4" label="三个月"/> 
									<form:option value="5" label="六个月"/> 
									<form:option value="6" label="九个月"/> 
									<form:option value="7" label="一年"/> 
								 </form:select>
                              </div>
                           </div>
                           
                           <div class="form-group">
                              <label  class="col-md-2 control-label">是否需要用户地址</label>
                              <div class="col-md-5">
                                <form:radiobutton path="isAddr" value="0" />不需要
                                 <form:radiobutton path="isAddr" value="1" />需要
                              </div>
                           </div>
                           
                           <div class="form-group">
                              <label  class="col-md-2 control-label">是否下架</label>
                              <div class="col-md-5">
                                <form:radiobutton path="isEnable" value="0" />下架
                                 <form:radiobutton path="isEnable" value="1" />上架
                              </div>
                           </div>
                           
                           <div class="form-group required">
								<label class="col-md-2 control-label">服务标准</label>
								<div class="col-md-9">
								<form:textarea  class="textarea" id="contentStandard" path="contentStandard"  placeholder="全文"></form:textarea>
								<form:errors path="contentStandard" class="field-has-error"></form:errors>
								</div>
						   </div>

                           <div class="form-group required">
								<label class="col-md-2 control-label">服务说明</label>
								<div class="col-md-9">
								<form:textarea  class="textarea" id="contentDesc" path="contentDesc"  placeholder="全文"></form:textarea>
								<form:errors path="contentDesc" class="field-has-error"></form:errors>
								</div>
						   </div>
						   
						   
						   <div class="form-group required">
								<label class="col-md-2 control-label">服务流程</label>
								<div class="col-md-9">
								<form:textarea  class="textarea" id="contentFlow" path="contentFlow"  placeholder="全文"></form:textarea>
								<form:errors path="contentFlow" class="field-has-error"></form:errors>
								</div>
						   </div>
                           
                           <%-- <div class="form-group">
                              <label  class="col-md-2 control-label">隶属于&nbsp;*</label>
                              <div class="col-md-5">
                                 <div class="portlet" id="parentId">
					                  <div class="portlet-body">
				                     	   <c:import url = "../shared/treeSelector.jsp">
												<c:param name="propertyName" value="parentId"/>
												<c:param name="propertyValue" value="${contentModel.parentId}"/>
												<c:param name="treeDataSourceName" value="treeDataSource"/>
										   </c:import>
					                  </div>
					             </div>
                              </div>
                           </div> --%>
                        </div>
                        <div class="form-actions fluid">
                           <div class="col-md-offset-4">
                              <button type="button" id ="btn_submit" class="btn btn-success">保存</button>
                           	
                           	  <c:if test="${contentModel.id > 0 }">
                           	  <button type="button" id ="btn_preview" class="btn btn-success">预览</button>
                           	  </c:if>
                           </div>
                                                      
                        </div>
                     </form:form>
			</section>
		</div>
	</div>
	<!-- page end--> </section> </section> <!--main content end--> <!--footer start--> <%@ include
		file="../shared/pageFooter.jsp"%> <!--footer end-->
	</section>
	<!--script for this page-->
    <script src="<c:url value='/assets/jquery-validation/dist/jquery.validate.min.js'/>"></script>
	<script src="<c:url value='/assets/bootstrap-fileupload/fileinput.min.js'/>" type="text/javascript"></script>
  
	<script charset="utf-8" src="<c:url value='/assets/kindeditor-4.1.10/kindeditor.js'/>"></script>
	<script charset="utf-8" src="<c:url value='/assets/kindeditor-4.1.10/lang/zh_CN.js'/>"></script>
	<script type="text/javascript">
	//富编辑器
	KindEditor.ready(function(K) {
	  	 K.create("#contentStandard",{
	         	width:'300px',
	         	height:'300px',
	         	afterBlur: function(){this.sync();}//帮助KindEditor获得textarea的值
	  	 });
	  	 
	  	 K.create("#contentDesc",{
	         	width:'300px',
	         	height:'300px',
	         	afterBlur: function(){this.sync();}//帮助KindEditor获得textarea的值
	  	 });
	  	 
	  	 K.create("#contentFlow",{
	         	width:'300px',
	         	height:'300px',
	         	afterBlur: function(){this.sync();}//帮助KindEditor获得textarea的值
	  	 });	  	 
	});
	</script>	
	
	<script src="<c:url value='/js/simi/partner/partnerServicePriceForm.js'/>" type="text/javascript"></script>
	<script>
		$('#orderType').trigger('change');
	</script>
</body>
</html>