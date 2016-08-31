<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	import="com.simi.oa.common.UrlHelper"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="../shared/taglib.jsp"%>
<html>
<head>
<!--common css for all pages-->
<%@ include file="../shared/importCss.jsp"%>
<!--css for this page-->
<link rel="stylesheet" href="<c:url value='/css/fileinput.css'/>" type="text/css" />
<link rel="stylesheet" href="<c:url value='/assets/data-tables/DT_bootstrap.css'/>" type="text/css" />
<link rel="stylesheet" href="<c:url value='/assets/aliplayer/index-min.css'/>" type="text/css" />
</head>
<body>
	<section id="container"> <!--header start--> <%@ include file="../shared/pageHeader.jsp"%>
	<!--header end--> <!--sidebar start--> <%@ include file="../shared/sidebarMenu.jsp"%> <!--sidebar end-->
	<!--main content start--> <section id="main-content"> <section class="wrapper"> <!-- page start-->
	<div class="row">
		<div class="col-lg-12">
			<section class="panel"> <header class="panel-heading"> 服务价格 </header>
			<hr style="width: 100%; color: black; height: 1px; background-color: black;" />
			<form:form modelAttribute="contentModel" enctype="multipart/form-data" class="form-horizontal" method="POST"
				id="partner-service-price-form">
				<form:hidden path="userId" />
				<form:hidden path="serviceTypeId" />
				<form:hidden path="partnerId" />
				<form:hidden path="servicePriceId" />
				<form:hidden path="orderType" />
				<form:hidden path="orderDuration" />
				<form:hidden path="isAddr" />
				<form:hidden path="contentDesc" />
				<div class="form-body">
					<div class="form-group">
						<label class="col-md-2 control-label">排序&nbsp;*</label>
						<div class="col-md-5">
							<form:input path="no" class="form-control" placeholder="排序" />
							<br />
							<form:errors path="no" class="field-has-error"></form:errors>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-md-2 control-label">频道&nbsp;*</label>
						<div class="col-md-5">
							<form:select path="extendId" class="form-control">
								<form:options items="${channelList}" itemValue="id" itemLabel="name"></form:options>
							</form:select>
						</div>
					</div>
					
					<c:if test="${contentModel.imgUrl != null && contentModel.imgUrl != '' }">
						<div class="form-group ">
							<label class="col-md-2 control-label">图片</label>
							<div class="col-md-5">
								<img src="${ contentModel.imgUrl }?p=0" width=300 heigh=300/>
							</div>
						</div>
					</c:if>
					<div class="form-group required">
						<label class="col-md-2 control-label">图片地址(640 × 370)</label>
						<div class="col-md-5">
							<input id="imgUrlFile" type="file" name="imgUrlFile" accept="image/*" data-show-upload="false">
							<form:errors path="imgUrl" class="field-has-error"></form:errors>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-2 control-label">标题&nbsp;*</label>
						<div class="col-md-5">
							<form:input path="name" class="form-control" placeholder="名称" />
							<br />
							<form:errors path="name" class="field-has-error"></form:errors>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-2 control-label"></label>
						<div class="col-md-5">
							<div id='J_prismPlayer' class='prism-player'></div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-2 control-label">视频地址&nbsp;*</label>
						<div class="col-md-5">
							<form:input path="videoUrl" class="form-control" placeholder="阿里云地址" />
							<br />
							<form:errors path="videoUrl" class="field-has-error"></form:errors>
						</div>
						<div class="col-md-2">
							<button type="button" id="prew-video-btn" class="btn btn-info">试播</button>
						</div>
					</div>
					
					<div class="form-group required">
							<label class="col-md-2 control-label">操作类别(视频页面弹窗)</label>
							<div class="col-md-5">
								<form:select path="category" class="form-control">
								    <option value="">无</option>
									<form:option value="h5">h5</form:option>
									<form:option value="app">app</form:option>
								</form:select>
							</div>
						</div>
						<div class="form-group required">

							<label class="col-md-2 control-label">动作标识</label>
							<div class="col-md-5">
								<form:input path="action" class="form-control" placeholder="动作标识"
									maxLength="32" />
								<form:errors path="action" class="field-has-error"></form:errors>
							</div>
						</div>
						<div class="form-group required">

							<label class="col-md-2 control-label">操作相关参数</label>
							<div class="col-md-5">
								<form:input path="params" class="form-control" placeholder="动作标识"
									maxLength="32" />
								<form:errors path="params" class="field-has-error"></form:errors>
							</div>
						</div>
						<div class="form-group required">

							<label class="col-md-2 control-label">跳转地址</label>
							<div class="col-md-5">
								<form:input path="gotoUrl" class="form-control" placeholder="跳转路径"/>
								<form:errors path="gotoUrl" class="field-has-error"></form:errors>
							</div>
						</div>
					
					
					<div class="form-group">
						<label class="col-md-2 control-label">原价&nbsp;*</label>
						<div class="col-md-5">
							<form:input path="price" class="form-control" placeholder="名称" />
							<br />
							<form:errors path="price" class="field-has-error"></form:errors>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-2 control-label">现价&nbsp;*</label>
						<div class="col-md-5">
							<form:input path="disPrice" class="form-control" placeholder="名称" />
							<br />
							<form:errors path="disPrice" class="field-has-error"></form:errors>
						</div>
					</div>
					<div class="form-group required">
						<label class="col-md-2 control-label">文章详情</label>
						<div class="col-md-9">
							<form:textarea class="textarea" id="contentStandard" path="contentStandard" placeholder="全文"></form:textarea>
							<form:errors path="contentStandard" class="field-has-error"></form:errors>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-2 control-label">标签&nbsp;*</label>
						<div class="col-md-5">
							<form:input path="tags" class="form-control" placeholder="名称" />
							<br />
							<form:errors path="tags" class="field-has-error"></form:errors>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-2 control-label">是否下架</label>
						<div class="col-md-5">
							<form:radiobutton path="isEnable" value="0" />
							下架
							<form:radiobutton path="isEnable" value="1" />
							上架
						</div>
					</div>
				</div>
				<div class="form-actions fluid">
					<div class="col-md-offset-4">
						<button type="button" id="btn_submit" class="btn btn-success">保存</button>
					</div>
				</div>
			</form:form> </section>
		</div>
	</div>
	<!-- page end--> </section> </section> <!--main content end--> <!--footer start--> <%@ include file="../shared/pageFooter.jsp"%>
	<!--footer end--> </section>
	<%@ include file="../shared/importJs.jsp"%>
	<!--script for this page-->
	<script src="<c:url value='/assets/aliplayer/prism-min.js'/>"></script>
	<script src="<c:url value='/assets/jquery-validation/dist/jquery.validate.min.js'/>"></script>
	<script src="<c:url value='/assets/bootstrap-fileupload/fileinput.min.js'/>" type="text/javascript"></script>
	<script charset="utf-8" src="<c:url value='/assets/kindeditor-4.1.10/kindeditor.js'/>"></script>
	<script charset="utf-8" src="<c:url value='/assets/kindeditor-4.1.10/lang/zh_CN.js'/>"></script>
	<script type="text/javascript">
		//富编辑器
		KindEditor.ready(function(K) {
			K.create("#contentStandard", {
				width : '500px',
				height : '500px',
				afterBlur : function() {
					this.sync();
				}//帮助KindEditor获得textarea的值
			});
			
		});
	</script>
	<script src="<c:url value='/js/simi/partner/partnerServiceVideoForm.js'/>" type="text/javascript"></script>
</body>
</html>