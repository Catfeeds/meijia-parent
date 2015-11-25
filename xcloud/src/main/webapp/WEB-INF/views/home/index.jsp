<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ include file="../shared/taglib.jsp"%>


<html>
<head>

<!--common css for all pages-->
<%@ include file="../shared/importCss.jsp"%>

<!--css for this page-->
<link href="<c:url value='/js/vendor/fullcalendar/fullcalendar.min.css'/>" rel="stylesheet" />
</head>

<body data-offset="250" data-target=".h5a-sidebar" data-spy="scroll">
	<!--header start-->
	<%@ include file="../shared/pageHeader.jsp"%>
	<!--header end-->
	</br>
	<div class="h5a-header" id="content">
		<div class="container h5a-container gray">
			<div class="row">

				<div class="col-sm-4">
					<div class="box hidden-print" style="height:180px;">
						 <div class="title">
								<h4 >
									<div style="margin-top:-13px;"><a href="#" >企业信息</a></div>
									
								</h4>
								<span class="pull-right boxes_right" style="margin-top:-30px;">
									<button class="btn btn-danger " type="button">修改</button>
								</span>
						
						 </div>

						<dl class="num_list clearfix">
							<dd>
								<a href="#">
									<p class="ind_num ind_color_green">
										7<em>人</em>
									</p>
									<p class="ind_name">全部员工</p>
								</a>
							</dd>
							<dd>
								<p class="ind_num ind_color_blue">
									4<em>人</em>
								</p>
								<p class="ind_name">全职员工</p>
							</dd>
							<dd>
								<p class="ind_num ind_color_red">
									3<em>人</em>
								</p>
								<p class="ind_name">兼职员工</p>
							</dd>
						</dl>
					</div>
				</div>
				
				<div class="col-sm-4">
					<div class="box hidden-print" style="height:180px;">
						 <div class="title">
								<h4 >
									<div style="margin-top:-13px;"><a href="#" >员工管理</a></div>
									
								</h4>
								<span class="pull-right boxes_right" style="margin-top:-30px;">
									<button class="btn btn-danger " type="button">添加员工</button>
								</span>
						
						 </div>

						<dl class="num_list clearfix">
							<dd>
								<a href="#">
									<p class="ind_num ind_color_green">
										7<em>人</em>
									</p>
									<p class="ind_name">在职员工</p>
								</a>
							</dd>
							<dd>
								<p class="ind_num ind_color_blue">
									4<em>人</em>
								</p>
								<p class="ind_name">全职员工</p>
							</dd>
							<dd>
								<p class="ind_num ind_color_red">
									3<em>人</em>
								</p>
								<p class="ind_name">外勤员工</p>
							</dd>
						</dl>
					</div>
				</div>
				
				<div class="col-sm-4">
					<div class="box hidden-print" style="height:180px;">
						 <div class="title">
								<h4 >
									<div style="margin-top:-13px;"><a href="#" >今日天气</a></div>
									
								</h4>
								<h4 class="pull-right" style="margin-top:-30px;">
									2015-11-24 10:55:00
								</h4>
						
						 </div>


						
					</div>
				</div>
				
				



			</div>
			
			<div class="row">
					<div class="col-md-9">
						<div class="box hidden-print" style="height:500px;">
							<div id="calendar"></div>
						</div>
					</div>
					<div class="col-md-3">
						<div class="box hidden-print" style="height:500px;">
							<div class="title">
								<h4 >
									<div style="margin-top:-13px;"><a href="#" >创建卡片</a></div>
									
								</h4>
								<span class="pull-right boxes_right" style="margin-top:-20px;">
									拖拽到日历中
								</span>
						
						 </div>
							<div id="external-events">
								
								<!-- <button class="btn btn-success" type="button">Success</button>
								
								
								<div class="btn btn-danger external-event-button" type="button">Danger111112345</div>
								<div class="btn btn-danger external-event red" data-class="red" type="button">Danger11111</div>
								 -->
								<!--  在xcloud的2159行设置长度 -->
								<div class="external-event red" data-class="red">备忘标签</div>
								<div class="external-event violet" data-class="violet">差旅规划</div>
								<div class="external-event gray" data-class="gray">会议安排</div>
								<div class="external-event blue" data-class="blue">事务提醒</div>
								<div class="external-event green" data-class="green">邀约通知</div>
								<div class="external-event orange" data-class="orange">秘书叫早</div>
								
							</div>
						</div>
						
					</div>
				</div>
		</div>
	</div>





	<!-- js placed at the end of the document so the pages load faster -->
	<!--common script for all pages-->
	<%@ include file="../shared/importJs.jsp"%>

	<!--script for this page-->
	<script  src="<c:url value='/js/vendor/moment.min.js'/>"></script>
	<script  src="<c:url value='/js/vendor/fullcalendar/fullcalendar.min.js'/>" ></script>
	<script src="<c:url value='/js/vendor//fullcalendar/lang/zh-cn.js'/>"></script>
	<script src="<c:url value='/js/xcloud/home/index.js'/>" ></script>
</body>
</html>
