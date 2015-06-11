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
		<form:form modelAttribute="secModel" class="form-horizontal"
				method="POST" action="listForm" id="sec-form"
				enctype="multipart/form-data">

		<form:hidden path="id" />
			<h3 align="center">新增用户</h3>
			<div class="form-group">
				<label class="control-label visible-ie8 visible-ie9">姓名</label>
				<div class="input-icon">
					<i class="icon-font"></i>
					<form:input path="name" class="form-control placeholder-no-fix" autocomplete="off" placeholder="姓名"/><br/>
					<form:errors path="name" class="field-has-error"></form:errors>
				</div>
			</div>
			<div class="form-group">
				<!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
				<label class="control-label visible-ie8 visible-ie9">手机号</label>
				<div class="input-icon">
					<i class="icon-envelope"></i>
					<form:input path="mobile" class="form-control placeholder-no-fix" autocomplete="off" placeholder="手机号"/><br/>
					<form:errors path="mobile" class="field-has-error"></form:errors>
				</div>
			</div>
			<div class="form-group">
				<label class="control-label visible-ie8 visible-ie9">昵称</label>
				<div class="input-icon">
					<i class="icon-font"></i>
					<form:input path="nickName" class="form-control placeholder-no-fix" autocomplete="off" placeholder="昵称"/><br/>
					<form:errors path="nickName" class="field-has-error"></form:errors>
				</div>
			</div>
			<div class="form-group">
				<label class="control-label visible-ie8 visible-ie9">出生日期</label>
				<div class="input-icon">
					<i class="icon-envelope"></i>
					<form:input path="birthDay" class="form-control placeholder-no-fix" autocomplete="off" placeholder="出生日期"/><br/>
					<form:errors path="birthDay" class="field-has-error"></form:errors>
				</div>
			</div>

			<div class="form-group">
				<label class="control-label visible-ie8 visible-ie9">头像</label>
				<div class="input-icon">
					<i class="icon-user"></i>
					<form:input id="headImg" name="username" class="form-control placeholder-no-fix" autocomplete="off"accept="image/*"
												data-show-upload="false"/><br/>
					<form:errors path="headImg" class="field-has-error"></form:errors>
				</div>
			</div>
			
			<div class="form-group">
				<label class="control-label visible-ie8 visible-ie9">状态</label>
				<div class="input-icon">
					<i class="icon-envelope"></i> 
					<label class="radio inline">
					 <input type="radio" name="status" value="0" checked>注册中
					</label> 
					
					<label class="radio inline">
					 <input type="radio" name="status" value="1">审核中
					</label> 
					
					<label class="radio inline"> 
					<input type="radio" name="status" value="2">审核通过
					</label>
					
				</div>
			</div>
			                                
			
			<div class="form-actions">
				<button type="submit" id="secForm_btn" class="btn btn-info pull-right">
				保存 <i class="m-icon-swapright m-icon-white"></i>
				</button>
			</div>			
			<div class="col-md-5"></div>
		</form:form>
		</div>
		<!-- END REGISTRATION FORM -->
   <script src="<c:url value='/js/onecare/sec/listForm.js'/>"
		type="text/javascript"></script>
</body>
</html>