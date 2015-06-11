<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<!-- BEGIN BODY -->
<body class="login" >
	<!-- BEGIN LOGIN -->
            <div class="col-md-2"></div>
            <div class="col-md-5">
		<!-- BEGIN REGISTRATION FORM -->
		<form:form modelAttribute="adModel" class="form-horizontal"
								method="POST" action="adForm" id="dict-form"
								enctype="multipart/form-data">
			<h3 align="center">新增用户</h3>

            <form:hidden path="id" />
            
			<div class="form-group">
				<label class="control-label visible-ie8 visible-ie9">序号</label>
				<div class="input-icon">
					<i class="icon-font"></i>
					<form:input path="No" class="form-control placeholder-no-fix" autocomplete="off" placeholder="序号"/><br/>
					<form:errors path="No" class="field-has-error"></form:errors>
				</div>
			</div>
			
			<c:if test="${adModel.imgUrl != null && adModel.imgUrl != '' }">
			<div class="form-group">
				<label class="control-label visible-ie8 visible-ie9">图片</label>
				<div class="input-icon">
					<i class="icon-font"></i>
					<img src="${ adModel.imgUrl }"/>
				</div>
			</div>
			</c:if>
			
			<div class="form-group">
				<label class="control-label visible-ie8 visible-ie9">图片地址</label>
				<div class="input-icon">
					<i class="icon-envelope"></i>
					<input id="imgUrl" type="file" name="imgUrl" accept="image/*" data-show-upload="false">
					<form:errors path="imgUrl" class="field-has-error"></form:errors>
				</div>
			</div>

			<div class="form-group">
				<label class="control-label visible-ie8 visible-ie9">跳转地址</label>
				<div class="input-icon">
					<i class="icon-user"></i>
					<form:input path="gotoUrl" class="form-control placeholder-no-fix" autocomplete="off" placeholder="跳转地址"/><br/>
					<form:errors path="gotoUrl" class="field-has-error"></form:errors>
				</div>
			</div>
			
			<div class="form-group">
				<label class="control-label visible-ie8 visible-ie9">服务类型</label>
				<div class="input-icon">
					<i class="icon-lock"></i>
					<ServiceTypeSelectTag:select />
				</div>
			</div>
			
			<div class="form-group">
				<label class="control-label visible-ie8 visible-ie9">是否可用</label>
				<div class="controls">
					<div class="input-icon">
						<i class="icon-ok"></i>
						<div class="row">
							<div class="col-md-2" align="right">
								<label class="radio"> <input value="0" name="enable"
									type="radio"> 不可用
								</label>
							</div>
							<div class="col-md-2" align="left">
								<label class="radio"> <input checked="checked" value="1"
									name="enable" type="radio"> 可用
								</label>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="form-actions">									
				<button type="submit" id="adForm_btn" class="btn btn-success">
				保存 <i class="m-icon-swapright m-icon-white"></i>
			</button>
			</div>			
			<div class="col-md-5"></div>
		</form:form>
		</div>
		<!-- END REGISTRATION FORM -->
		  	<script src="<c:url value='/js/simi/dict/adForm.js'/>"
		                      type="text/javascript"></script>
</body>
</html>