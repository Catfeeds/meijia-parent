<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
                          
                          <hr style="width: 100%; color: black; height: 1px; background-color:black;" />
                          
                          <div class="panel-body">
                            <form:form modelAttribute="dictCoupons" id="coupon-form" commandName="dictCoupons"
					action="addCoupons" class="form-horizontal" method="POST" >
					<div class="form-body">
						<div class="form-group">
							<!-- Text input-->
							<label class="col-md-2 control-label">卡&nbsp;&nbsp;&nbsp;数&nbsp;&nbsp;&nbsp;量</label>
							<div class=col-md-5>
								<form:input path="id" class="form-control" placeholder="卡数量"
									maxSize="10" />
							</div>
						</div>

						<div class="form-group">


							<label class="col-md-2 control-label">优惠券类型 </label>

							<div class="col-md-5">

								<label class="radio"> <input checked="checked" value="0"
									name="couponType" type="radio">订单支付
								</label>
								 <label class="radio"> <input value="1" name="couponType"
									type="radio"> 充值卡充值
								</label>
								 <label class="radio"> <input value="2" name="couponType"
									type="radio">活动相关
								</label>
							</div>

						</div>
						<div class="form-group">

							<!-- Text input-->
							<label class="col-md-2 control-label">优惠券金额</label>
							<div class="col-md-5">
								<form:input path="value" class="form-control" placeholder="优惠券金额"
									maxSize="10" />
								<form:errors path="value" class="field-has-error"></form:errors>

							</div>
						</div>



						<div class="form-group">
							<label class="col-md-2 control-label">通&nbsp;用&nbsp;类&nbsp;型</label>
							<div class="col-md-5">
								<label class="radio"> <input checked="checked" value="0"
									name="rangType" type="radio"> 通用
								</label> <label class="radio"> <input value="1" name="rangType"
									type="radio"> 唯一
								</label>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">服&nbsp;务&nbsp;类&nbsp;型</label>
							<div class="controls">
									<td>
										<form:checkbox path="serviceType" value="0"/>全部
										<form:checkbox path="serviceType" value="1"/>做饭
										<form:checkbox path="serviceType" value="2"/>洗衣
										<form:checkbox path="serviceType" value="3"/>家电清洗
										<form:checkbox path="serviceType" value="4"/>保洁
										<form:checkbox path="serviceType" value="5"/>擦玻璃
										<form:checkbox path="serviceType" value="6"/>管道疏通
										<form:checkbox path="serviceType" value="7"/>新居开荒
										<form:errors path="serviceType" class="field-has-error"></form:errors>
									</td>
							</div>
						</div>

						<div class="form-group">

							<!-- Select Basic -->
							<label class="col-md-2 control-label">使&nbsp;用&nbsp;来&nbsp;源</label>
							<div class="col-md-5">
								<select name="rangFrom" class="form-control">
									<option value="0">APP</option>
									<option value="1">微网站</option>
									<option value="999">所用来源</option>
								</select>
							</div>

						</div>

						<div class="form-group">

							<!-- Text input-->
							<label class="col-md-2 control-label">描&nbsp;&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;述</label>
							<div class="col-md-5">
								<form:input path="introduction" class="form-control"
									placeholder="请输入描述" />
								<p class="help-block"></p>
							</div>
						</div>

						<div class="form-group">

							<!-- Textarea -->
							<label class="col-md-2 control-label">详&nbsp;细&nbsp;说&nbsp;明</label>
							<div class="col-md-5">
								<div class="textarea">
									<textarea type="textarea" name="description"
										class="form-control"> </textarea>
								</div>
							</div>
						</div>

						<div class="form-group">

							<!-- Text input-->
							<label class="col-md-2 control-label">过&nbsp;期&nbsp;时&nbsp;间</label>
							<div class="col-md-6" id="exptime">
								<input type="text" readonly="readonly" name="expTime"
									style="width: 450px"  />
								<%@ include file="date.jsp"%>
							</div>
						</div>
					</div>
					<div class="form-actions">
						<div class="row">
							<div class="col-md-4" align="right">
								<button  class="btn btn-success" id="addCoupon_btn" type="button">新增</button>
							</div>
							<!-- Button -->
							<div class="col-md-8">
								<button class="btn btn-success" type="reset">重置</button>
							</div>

						</div>
					</div>
					<!-- </fieldset> -->
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
	<script src="<c:url value='/js/simi/coupon/couponForm.js'/>" type="text/javascript"></script>
 	<script type="text/javascript">
 	$(function() {
 		addCouponFormValidate.handleAddCoupon();
 	});
 	</script>
  </body>
</html>
