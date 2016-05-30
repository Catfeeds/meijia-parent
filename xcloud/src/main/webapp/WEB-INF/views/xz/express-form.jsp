<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ include file="../shared/taglib.jsp"%>

<html>
<head>

<!--common css for all pages-->
<%@ include file="../shared/importCss.jsp"%>

<!--css for this page-->

</head>

<body>
	<!--header start-->
	<%@ include file="../shared/pageHeader.jsp"%>
	<!--header end-->

	<div class="am-cf admin-main">
		<!-- sidebar start -->
		<%@ include file="../xz/xz-menu.jsp"%>
		<!-- sidebar end -->

		<!-- content start -->
		<div class="admin-content">
			<div class="am-cf am-padding">
				<div class="am-fl am-cf">
					<strong class="am-text-primary am-text-lg">快递管理</strong> / <small>快递单</small>
				</div>
			</div>
			<hr>

			<div class="am-g">

				<div class="am-u-sm-12 am-u-md-4 am-u-md-push-8">
					<section class="am-panel am-panel-default"> <header
						class="am-panel-hd"> <img
						src="<c:url value='/assets/img/a1.png'/>"
						class="am-img-thumbnail am-circle" width="35" height="35">
					云小秘提示您 </header>
					<div class="am-panel-bd">可以用App扫码加入快递，快去试试吧</div>
					<div class="am-panel-bd"><img src="<c:url value='${qrCode }'/>" width="250" height="250" /></div>
					</section>
				</div>

				<div class="am-u-sm-12 am-u-md-8 am-u-md-pull-4">
					<form:form modelAttribute="contentModel" method="POST"
						id="express-form" class="am-form am-form-horizontal"
						enctype="multipart/form-data">
						<form:hidden path="id"/>
						<input type="hidden" id="userId" value="${userId }" />
						<input type="hidden" id="companyId" value="${companyId }"/>
						
						<div class="am-form-group">
							<label for="user-phone" class="am-u-sm-3 am-form-label">快递单号<font color="red">*</font>:</label>
							<div class="am-u-sm-9">
								<form:input path="expressNo"
									class="am-form-field am-radius js-pattern-pinteger"
									maxLength="32" required="required" />
								
							</div>
						</div>

						<div class="am-form-group">
							<label for="user-phone" class="am-u-sm-3 am-form-label">快递公司<font color="red">*</font>:</label>
							<div class="am-u-sm-9">
								<form:select path="expressId" class="am-form-field am-radius">
									<form:option value="">请选择快递公司</form:option>
									<form:options items="${expressList}" itemValue="expressId" itemLabel="name" />
								</form:select>
							</div>
						</div>
						
						<div class="am-form-group">
							<label class="am-u-sm-3 am-form-label">寄件人地址:</label>
							<div class="am-u-sm-9">
								<form:input path="fromAddr"
									class="form-control"
									maxLength="32"  />
							</div>
						</div>

						<div class="am-form-group">
							<label class="am-u-sm-3 am-form-label">寄件人姓名<font color="red">*</font>:</label>
							<div class="am-u-sm-9">
								<form:input path="fromName"
									class="form-control"
									maxLength="32" required="required" />
								
							</div>
						</div>

						<div class="am-form-group">
							<label for="user-phone" class="am-u-sm-3 am-form-label">寄件人联系方式<font color="red">*</font>:</label>
							<div class="am-u-sm-9">
								<form:input path="fromTel"
									class="am-form-field am-radius "
									maxLength="32" required="required" />
								
							</div>
						</div>

						<div class="am-form-group">
							<label class="am-u-sm-3 am-form-label">收件人地址<font color="red">*</font>:</label>
							<div class="am-u-sm-9">
								<form:input path="toAddr"
									class="form-control"
									maxLength="32" required="required" />
								
							</div>
						</div>
						<div class="am-form-group">
							<label class="am-u-sm-3 am-form-label">收件人姓名<font color="red">*</font>:</label>
							<div class="am-u-sm-9">
								<form:input path="toName"
									class="form-control"
									maxLength="32" required="required" />
								
							</div>
						</div>

						<div class="am-form-group">
							<label for="user-phone" class="am-u-sm-3 am-form-label">收件人联系方式<font color="red">*</font>:</label>
							<div class="am-u-sm-9">
								<form:input path="toTel"
									class="am-form-field am-radius "
									maxLength="32" required="required" />
								
							</div>
						</div>
						
						<div class="am-form-group">
							<label class="am-u-sm-3 am-form-label">发送或接收:</label>
								<div class="am-u-sm-9">
								<label class="am-radio-inline"> 
									<form:radiobutton path="expressType" value="0" />收件
								</label> 
								<label class="am-radio-inline"> 
									<form:radiobutton path="expressType" value="1" />寄件
								</label>
							</div>
							
						</div>
						
						
						<div id="payTypeDiv" class="am-form-group" style="display:none">
							<label for="user-phone" class="am-u-sm-3 am-form-label">付费方式:</label>
							<div class="am-u-sm-9">
								<label class="am-radio-inline"> 
									<form:radiobutton path="payType" value="0" />公费
								</label> 
								<label class="am-radio-inline"> 
									<form:radiobutton path="payType" value="1" />自费
								</label>	      
								    
							</div>
						</div>
						
						<div id="priceDiv" class="am-form-group" style="display:none">
							<label class="am-u-sm-3 am-form-label">价格:</label>
							<div class="am-u-sm-9">
								<form:input path="price" class="form-control js-pattern-price" maxLength="7" required="required" />
							</div>
						</div>
						
						
						<div id="isCloseDiv" class="am-form-group" style="display:none">
							<label for="user-phone" class="am-u-sm-3 am-form-label">是否结算:</label>
							<div class="am-u-sm-9">
								<label class="am-radio-inline"> 
									<form:radiobutton path="isClose" value="0" />未结算
								</label> 
								<label class="am-radio-inline"> 
									<form:radiobutton path="isClose" value="1" />已结算
								</label>    
							</div>
						</div>

						<div class="am-form-group">
							<label for="user-phone" class="am-u-sm-3 am-form-label">备注:</label>
							<div class="am-u-sm-9">
								<form:textarea path="remarks" class="form-control"
									placeholder="请输入备注" />
							</div>
						</div>

						<hr>
						<div class="am-form-group">
							<div class="am-u-sm-9 am-u-sm-push-3">
								<button type="button" class="am-btn am-btn-danger"
									id="btn-express-submit">保存</button>
								<button type="button" class="am-btn am-btn-success"
									id="btn-return">返回</button>
							</div>
						</div>
					</form:form>
				</div>
			</div>
		</div>
		<!-- content end -->

	</div>

	<a href="#"
		class="am-icon-btn am-icon-th-list am-show-sm-only admin-menu"
		data-am-offcanvas="{target: '#admin-offcanvas'}"></a>

	<!--footer start-->
	<%@ include file="../shared/pageFooter.jsp"%>
	<!--footer end-->

	<!-- js placed at the end of the document so the pages load faster -->
	<!--common script for all pages-->
	<%@ include file="../shared/importJs.jsp"%>

	<!--script for this page-->
	<script
		src="<c:url value='/assets/js/xcloud/common/validate-methods.js'/>"
		type="text/javascript"></script>
	<script src="<c:url value='/assets/js/xcloud/xz/express-form.js'/>"></script>
</body>
</html>
