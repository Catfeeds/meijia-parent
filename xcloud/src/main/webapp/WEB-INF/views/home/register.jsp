<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ include file="../shared/taglib.jsp"%>

<html>
<head>

<!--common css for all pages-->
<%@ include file="../shared/importCss.jsp"%>

<!--css for this page-->
<link href="<c:url value='/assets/bootstrap-select/css/bootstrap-select.min.css'/>" rel="stylesheet" type="text/css"/>

</head>

  <body>

  <section id="container" class="">
      
      
      <!--main content start-->
      <section id="main-content">
          <section class="wrapper site-min-height">
              <!-- page start-->
              <div class="row">
                  <div class="col-lg-8">
                      <section class="panel">
                          <header class="panel-heading">
                              企业注册
                          </header>
                          <div class="panel-body">
                              <div class="stepy-tab">
                                  <ul id="default-titles" class="stepy-titles clearfix">
                                      <li id="default-title-0" class="current-step">
                                          <div>1. 填写手机号</div>
                                      </li>
                                      <li id="default-title-1" class="">
                                          <div>2. 完善资料</div>
                                      </li>
                                      <li id="default-title-2" class="">
                                          <div>3. 注册完成</div>
                                      </li>
                                  </ul>
                              </div>
                              
                              <form class="form-horizontal" id="register-form">
                              	  
                                  <fieldset title="1. 验证信息" class="step" id="default-step-0">
                                      <legend> </legend>
                                      <div class="form-group">
                                          <label class="col-lg-2 control-label">手机号</label>
                                          <div class="col-lg-7">
                                              <input type="text" id="mobile" name="mobile" class="form-control" placeholder="手机号" maxLength="11">
                                          </div>
                                      </div>
                                      <div class="form-group">
                                          <label class="col-lg-2 control-label">图形验证码</label>
                                          <div class="col-lg-7">
                                              	<div class="row">
													<div class="col-md-4">
														<input type="text" id="img_token" name="img_token" placeholder="验证码" class="form-control" maxLength="4" >
													</div>
													
													<div class="col-md-4">
														<img id="kaptchaImage" src="/xcloud/captcha" maxlength="4" onclick="changeCaptcha()" />  
													</div>
													<div class="col-md-3">
														<button class="btn btn-primary" id="btn_sms_token" type="button">
														<i class="icon-refresh"></i>
															获取验证码
														</button>
													</div>
												</div>
                                          </div>
                                      </div>
                                      <div class="form-group">
                                          <label class="col-lg-2 control-label">手机验证码</label>
                                          <div class="col-lg-7">
                                              <input type="text" id="sms_token" name="sms_token" placeholder="手机验证码" class="form-control" style="width: 100%">
                                          </div>
                                      </div>

                                  </fieldset>
                                  
                                  
                                  <fieldset title="2. 完善资料" class="step" id="default-step-1" >
                                      <legend> </legend>
                                      <div class="form-group">
                                          <label class="col-lg-2 control-label">公司名称</label>
                                          <div class="col-lg-7">
                                              <input type="text" id="company_name" name="company_name" maxLength="64" class="form-control" placeholder="">
                                          </div>
                                      </div>
                                      
                                      <div class="form-group">
                                          <label class="col-lg-2 control-label">公司规模</label>
                                          <div class="col-lg-7">
                                              <select id="company_size" class="selectpicker" data-style="btn-danger">
											    <option value="1">20人以下</option>
											    <option value="2">20-99人</option>
											    <option value="3">100-499人</option>
											    <option value="4">500-999人</option>
											    <option value="5">1000-9999人</option>
											    <option value="6">10000人</option>
											  </select>
                                          </div>
                                      </div>
                                      
                                      <div class="form-group">
                                          <label class="col-lg-2 control-label">所在行业</label>
                                          <div class="col-lg-7">
                                               <select id="company_trade" class="selectpicker" data-style="btn-danger">
                                               		
                                               </select>
                                          </div>
                                      </div>
                                      
                                      <div class="form-group">
                                          <label class="col-lg-2 control-label">地理位置</label>
                                          <div class="col-lg-7">
                                          	   <div id="l-map"></div>
                                               <div id="r-result"><input type="text" id="addr_name" name="addr_name" size="20" maxLength="128" class="form-control" /></div>
											   <div id="searchResultPanel" style="border:1px solid #C0C0C0;width:150px;height:auto; display:none;"></div>
                                          	   <input type="hidden" id="longitude" name="longitude" value=""/>
											   <input type="hidden" id="latitude" name="latitude" value=""/>
                                          </div>
                                      </div>
                                      
                                      <div class="form-group">
                                          <label class="col-lg-2 control-label">详细地址</label>
                                          <div class="col-lg-7">
                                              <input type="text" id="addr" name="addr" class="form-control" maxLength="128" placeholder="">
                                          </div>
                                      </div>
                                      
									  <div class="form-group">
                                          <label class="col-lg-2 control-label">管理员账户</label>
                                          <div class="col-lg-7">
                                              <input type="text" id="user_name" name="user_name" class="form-control" readonly="readonly">
                                          </div>
                                      </div>
                                      
                                      <div class="form-group">
                                          <label class="col-lg-2 control-label">管理员密码</label>
                                          <div class="col-lg-7">
                                              <input type="password" id ="password" name="password" class="form-control" placeholder="" maxLength="12">
                                          </div>
                                      </div>
                                      
                                      <div class="form-group">
                                          <label class="col-lg-2 control-label">再次输入密码</label>
                                          <div class="col-lg-7">
                                              <input type="password" id ="confirm_password" name="confirm_password" class="form-control" placeholder="" maxLength="12">
                                          </div>
                                      </div>                                      

                                  </fieldset>
                                  <fieldset title="3. 注册完成" class="step" id="default-step-2" >
                                      <legend> </legend>
                                      <div class="form-group">
                                          <label class="col-lg-2 control-label">公司名称</label>
                                          <div class="col-lg-7">
                                              <p class="form-control-static" id="company_name_label"></p>
                                          </div>
                                      </div>
                                      <div class="form-group">
                                          <label class="col-lg-2 control-label">公司规模</label>
                                          <div class="col-lg-7">
                                              <p class="form-control-static" id="company_size_label"></p>
                                          </div>
                                      </div>
                                      <div class="form-group">
                                          <label class="col-lg-2 control-label">所在行业</label>
                                          <div class="col-lg-7">
                                              <p class="form-control-static" id="company_trade_label"></p>
                                          </div>
                                      </div>
                                      <div class="form-group">
                                          <label class="col-lg-2 control-label">详细地址</label>
                                          <div class="col-lg-7">
                                              <p class="form-control-static" id="addr_label"></p>
                                          </div>
                                      </div>
                                      <div class="form-group">
                                          <label class="col-lg-2 control-label">管理员账户</label>
                                          <div class="col-lg-7">
                                              <p class="form-control-static" id="user_name_label"></p>
                                          </div>
                                      </div>
                                      
                                  </fieldset>
                                  <input type="submit" class="finish btn btn-danger" value="完成注册"/>
                              </form>
                          </div>
                      </section>
                  </div>
              </div>
              <!-- page end-->
          </section>
      </section>
      <!--main content end-->
      <!--footer start-->
      <footer class="site-footer">
          <div class="text-center">
             &copy; 美家生活科技有限公司
              <a href="#" class="go-top">
                  <i class="icon-angle-up"></i>
              </a>
          </div>
      </footer>
      <!--footer end-->
  </section>
	
    <!-- js placed at the end of the document so the pages load faster -->
    <%@ include file="../shared/importJs.jsp"%>
	
	<!--script for this page-->
	
	<script src="<c:url value='/js/jquery.dcjqaccordion.2.7.min.js'/>" type="text/javascript"></script>
	<script src="<c:url value='/js/jquery.scrollTo.min.js'/>" type="text/javascript"></script>
	<script src="<c:url value='/js/jquery.nicescroll.js'/>" type="text/javascript"></script>
	<script src="<c:url value='/js/respond.min.js'/>" type="text/javascript"></script>
	<script src="<c:url value='/js/jquery.stepy.js'/>" type="text/javascript"></script>
	<script src="<c:url value='/assets/jquery-validation/dist/jquery.validate.min.js'/>" type="text/javascript"></script>
	<script src="<c:url value='/js/countdown.js'/>" type="text/javascript"></script>
	<script>
	
	//step wizard
	$('#register-form').stepy({
	  backLabel: '上一步',
	  block: true,
	  nextLabel: '下一步',
	  titleClick: true,
	  titleTarget: '.stepy-tab',
	  validate : true,
	  next: function(index) {

	  	  if (index == 2) {
	  		  if (!validateStep2()) {
	  			return false;
	  		  }
	  		  
	  	  }
	  	  
	  	  if (index == 3) {
	  		  if (!validateStep3()) {
	  			return false;
	  		  }
	  	  }
	  }
	});	
	
	</script>

	
	<script src="<c:url value='/assets/bootstrap-select/js/bootstrap-select.min.js'/>" type="text/javascript"></script>
	
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=2sshjv8D4AOoOzozoutVb6WT"></script>
	
	<script src="<c:url value='/js/xcloud/common/validate-methods.js'/>" type="text/javascript"></script>
	
	<script src="<c:url value='/js/xcloud/home/register.js'/>" type="text/javascript"></script>
	<script>
	
	// 百度地图API功能
	function G(id) {
		return document.getElementById(id);
	}

	var map = new BMap.Map("l-map");
	map.centerAndZoom("北京",12);                   // 初始化地图,设置城市和地图级别。

	var ac = new BMap.Autocomplete(    //建立一个自动完成的对象
		{"input" : "addr_name"
		,"location" : map
	});

	ac.addEventListener("onhighlight", function(e) {  //鼠标放在下拉列表上的事件
	var str = "";
		var _value = e.fromitem.value;
		var value = "";
		if (e.fromitem.index > -1) {
			value = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
		}    
		str = "FromItem<br />index = " + e.fromitem.index + "<br />value = " + value;
		
		value = "";
		if (e.toitem.index > -1) {
			_value = e.toitem.value;
			value = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
		}    
		str += "<br />ToItem<br />index = " + e.toitem.index + "<br />value = " + value;
		G("searchResultPanel").innerHTML = str;
	});

	var myValue;
	ac.addEventListener("onconfirm", function(e) {    //鼠标点击下拉列表后的事件
	var _value = e.item.value;
		myValue = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
		G("searchResultPanel").innerHTML ="onconfirm<br />index = " + e.item.index + "<br />myValue = " + myValue;
		
		setPlace();
	});

	function setPlace(){
		console.log("setPlace");
		map.clearOverlays();    //清除地图上所有覆盖物
		function myFun(){
			var pp = local.getResults().getPoi(0).point;    //获取第一个智能搜索的结果
/* 			map.centerAndZoom(pp, 18);
			map.addOverlay(new BMap.Marker(pp));    //添加标注 */
			console.log(pp.lng);
			console.log(pp.lat);
			$("#longitude").val(pp.lng);
			$("#latitude").val(pp.lat);
			

		}
		var local = new BMap.LocalSearch(map, { //智能搜索
		  onSearchComplete: myFun
		});
		local.search(myValue);
	}	
	
	
	</script>


  </body>
</html>
