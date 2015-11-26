<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ include file="../shared/taglib.jsp"%>



<header class="navbar navbar-fixed-top">
<div class="container top-header">
	<div class="navbar-header">
		<button type="button" class="navbar-toggle" data-toggle="collapse"
			data-target=".navbar-collapse">
			<span class="icon icon-bar"></span> <span class="icon icon-bar"></span>
			<span class="icon icon-bar"></span>
		</button>
		<a class="navbar-brand" href="/"><img
			src="<c:url value='/img/logo_header.png'/>" alt="云行政"> <span
			class="text" style="color: #fff">云行政-智慧行政服务平台</span></a>
	</div>
</div>
</header>


<!--common css for all pages-->
<%@ include file="../shared/importCss.jsp"%>
<!--css for this page-->

<link rel="stylesheet"
	href="<c:url value='/js/vendor/zTree/css/awesomeStyle/awesome.css'/>"
	type="text/css">

<html>
<head>


<body class="members_index"><div class="wrapper clearfix"
	style="overflow: hidden; zoom: 1; min-width: 1200px;">

	<header class="b_shadow5"> 
	<section class="breadcrumb clearfix">
	<div class="crumbs">
		<a href="/xcloud/index">首页</a> <em>></em> <a href="/xcloud/staff/list">员工管理</a>
		<em>></em> <span>添加员工</span>
	</div>
	</section> </header>

	<div class="main">
		<div class="container clearfix">
			<div class="title clearfix" style="margin: 0 24px;">
				<h3 class="title_left">
					<a class="cur" href="javascript:void(0);">添加员工</a>
				</h3>
				<div class="title_right fr">
					<a class="btn btn_blue fr bradius12 members_multi_add"
						href="javascript:void(0);">批量导入</a>
				</div>
			</div>
			<form id="members-add"
				data-validator-option=" { stopOnError:false, timely:2 } "
				autocomplete="off" method="post" action="/members/multi">
				<input type="hidden" value="two" name="act"> <input
					type="hidden" value="one" name="source">
				<section id="members_add" class="content members_add">
				<ul class="madd_box" style="margin-right: 0px;">
					<li class="clearfix">
						<div class="madd_list">
							<div class="form_group clearfix">
								<label class="control_label "> 姓名： <em>*</em>
								</label>
								<div class="form_control">
									<input id="realname0" class="form_text " type="text"
										data-tip="请填写员工的真实姓名"
										data-rule="姓名:required;remote[/api/user/multiRealnameCheck, #id_card0];"
										placeholder="" value="" name="multi_data[0][realname]">
									<span class="msg-box" for="realname0"></span>
								</div>
							</div>
							<div class="form_group clearfix">
								<label class="control_label "> 身份证号： <em>*</em>
								</label>
								<div class="form_control">
									<input id="id_card0" class="form_text id_card_el" type="text"
										data-tip="请填写员工的身份证号"
										data-rule="身份证号:required; length[18];IDCard;remote[/api/user/multiIdCardCheck]"
										placeholder="" value="" data-sub="#realname0"
										name="multi_data[0][id_card]"> <span class="msg-box"
										for="id_card0"></span>
								</div>
							</div>
							<div class="form_group clearfix">
								<label class="control_label "> 手机号： <em>*</em>
								</label>
								<div class="form_control">
									<input id="phone0" class="form_text" type="text"
										data-tip="请填写员工的手机号" data-rule="手机号:required; mobile;"
										placeholder="" value="" name="multi_data[0][phone]"> <span
										class="msg-box" for="phone0"></span>
								</div>
							</div>
						</div>
						<div class="madd_list">
							<div class="form_group clearfix">
								<label class="control_label "> 员工类型 <em>*</em>
								</label>
								<div class="form_control">
									<div id="radio_employee_type" class="radio_employee_type"
										dis="" style="display: inline;">
										<label class="input_check radio_employee_type cur"
											data-value="1"> 全职 <em></em>
										</label> <label class="input_check radio_employee_type "
											data-value="2"> 兼职 <em></em>
										</label> <label class="input_check radio_employee_type "
											data-value="3"> 实习 <em></em>
										</label> <input class="input_employee_type" type="text"
											style="width: 1px; height: 1px; padding: 0px; font-size: 0; border: 0px; cursor: default;"
											value="1" data-rule="员工类型:required;"
											name="multi_data[0][employee_type]">
									</div>
								</div>
							</div>
							<div class="form_group clearfix">
								<label class="control_label "> 职位： <em>*</em>
								</label>
								<div class="form_control">
									<input id="position0" class="form_text" type="text"
										data-tip="请填写员工的职位" data-rule="职位:required;" placeholder=""
										value="" name="multi_data[0][position]"> <span
										class="msg-box" for="position0"></span>
								</div>
							</div>
							<div class="form_group clearfix">
								<label class="control_label ">邮箱：</label>
								<div class="form_control">
									<input id="email0" class="form_text" type="text"
										data-tip="请填写员工的邮箱" data-rule="邮箱:email" placeholder=""
										value="" name="multi_data[0][email]"> <span
										class="msg-box" for="email0"></span>
								</div>
							</div>
						</div>
						<div class="madd_list">
							<div class="form_group clearfix">
								<label class="control_label ">工号：</label>
								<div class="form_control">
									<input id="work_id0" class="form_text" type="text"
										placeholder="" value="" name="multi_data[0][work_id]">
								</div>
							</div>
							<div class="form_group clearfix">
								<label class="control_label ">入职时间：</label>
								<div class="form_control">
									<input id="access_date0" class="form_text date_select"
										type="text" placeholder="" value=""
										name="multi_data[0][access_date]">
								</div>
							</div>
							<div class="form_group clearfix">
								<label class="control_label ">转正时间：</label>
								<div class="form_control">
									<input id="trans_date0" class="form_text date_select"
										type="text" placeholder="" value=""
										name="multi_data[0][trans_date]">
								</div>
							</div>
						</div>
					</li>
				</ul>
				<input type="hidden" value="two" name="act"> <section
					class="btn_box multi_box">
				<button id="members-add-btn"
					class="btn btn_blue bradius4 save_new_item" name="" type="submit">提交</button>
				<a class="btn bradius4" href="/members/index">取消</a> </section> </section>
			</form>
		</div>
	</div>

	<!-- js placed at the end of the document so the pages load faster -->
	<!--common script for all pages-->
	<%@ include file="../shared/importJs.jsp"%>

	<!--script for this page-->
	<script type="text/javascript"
		src="<c:url value='/js/vendor/zTree/js/jquery.ztree.core-3.5.js'/>"></script>

	</body>
</html>
