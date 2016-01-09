<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ include file="../shared/taglib.jsp"%>

<html>
<head>

<!--common css for all pages-->
<%@ include file="../shared/importCss.jsp"%>

<!--css for this page-->

<link href="<c:url value='/assets/js/chosen/amazeui.chosen.css'/>" rel="stylesheet">

</head>

<body>
	<!--header start-->
	<%@ include file="../shared/pageHeader.jsp"%>
	<!--header end-->

	<div class="am-cf admin-main">
		<!-- sidebar start -->
		<div class="admin-sidebar am-offcanvas" id="admin-offcanvas">
			<div class="am-offcanvas-bar admin-offcanvas-bar">
				<ul class="am-list admin-sidebar-list">
					<li><a href="/xcloud/schedule/list"><span class="am-icon-tags"></span> 日程管理</a></li>
					<li class="admin-parent"><a class="am-cf" data-am-collapse="{target: '#collapse-nav'}"><span
							class="am-icon-file"></span> 工作卡片 <span class="am-icon-angle-right am-fr am-margin-right"></span></a>
						<ul class="am-list am-collapse admin-sidebar-sub am-in" id="collapse-nav">
							<li><a href="/xcloud/schedule/card-form?card_type=2"><span class="am-icon-th"></span>
									通知公告<span class="am-badge am-badge-warning am-margin-right am-fr">24</span></a></li>
							<li><a href="/xcloud/schedule/card-form?card_type=3" class="am-cf"><span
									class="am-icon-bell"></span> 事务提醒<span
									class="am-icon-star am-fr am-margin-right admin-icon-yellow"></span></a></li>
							<li><a href="/xcloud/schedule/card-form?card_type=1"><span class="am-icon-users"></span>
									会议安排</a></li>
							<li><a href="/xcloud/schedule/card-form?card_type=4"><span class="am-icon-calendar"></span>
									面试邀约</a></li>
							<li><a href="/xcloud/schedule/card-form?card_type=5"><span class="am-icon-plane"></span>
									差旅规划</a></li>
						</ul></li>
					<!-- <li><a href="admin-table.html"><span class="am-icon-tags"></span> 卡片商店</a></li> -->

				</ul>

				<div class="am-panel am-panel-default admin-sidebar-panel">
					<div class="am-panel-bd">
						<p>
							<span class="am-icon-bookmark"></span> 最新公告
						</p>
						<p>新年将至，公司年会将在月球举办，点击查看详情。</p>
					</div>
				</div>
			</div>
		</div>
		<!-- sidebar end -->

		<!-- content start -->
		<div class="admin-content">
			<div class="am-cf am-padding">
				<div class="am-fl am-cf">
					<strong class="am-text-primary am-text-lg">${cardTypeName }</strong> / <small>Notice Alarm</small>
				</div>
			</div>

			<hr />

			<div class="am-g">

				<div class="am-u-sm-12 am-u-md-4 am-u-md-push-8">
					<section class="am-panel am-panel-default"> <header class="am-panel-hd"> <img
						src="<c:url value='/assets/img/a1.png'/>" class="am-img-thumbnail am-circle" width="35" height="35">
					云小秘提示您 </header>
					<div class="am-panel-bd">${cardTips }</div>
					</section>
				</div>

				<div class="am-u-sm-12 am-u-md-8 am-u-md-pull-4">
					<form:form modelAttribute="contentModel" method="POST" id="card-form"
						class="am-form am-form-horizontal">
						<form:hidden path="cardId" />
						<div class="am-form-group">
							<label for="user-name" class="am-u-sm-3 am-form-label">${labelAttendStr }：</label>

							<div class="am-u-sm-9">
								<button type="button" class="am-btn am-btn-primary" data-am-modal="{target: '#chosen-popup'}">
									点击选择人员</button>

								<div class="am-popup" id="chosen-popup">
									<div class="am-popup-inner">
										<div class="am-popup-hd">
											<h4 class="am-popup-title">选择人员</h4>
											<span data-am-modal-close class="am-close">&times;</span>
										</div>
										<div class="am-popup-bd">建议放一个选人的列表，类似大类效果</div>
									</div>
								</div>

								<small>选择需要发送与分享的好友</small>

							</div>
						</div>

						<div class="am-form-group">
							<label for="user-email" class="am-u-sm-3 am-form-label">${labelTimeStr }：</label>
							<div class="am-u-sm-9">
								<div class="am-input-group date form_datetime-5 form-datetime-lang"
									data-date="2015-02-14 14:45">
									<input size="16" type="text" value="2015-02-14 14:45" class="am-form-field" readonly>
									<span class="am-input-group-label add-on"><i class="icon-remove am-icon-close"></i></span>
									<span class="am-input-group-label add-on"><i class="icon-th am-icon-calendar"></i></span>
								</div>
								<small>选择此卡片希望提醒的时间</small>
							</div>
						</div>

						<div class="am-form-group">
							<label for="user-intro" class="am-u-sm-3 am-form-label">${labelContentStr }：</label>
							<div class="am-u-sm-9">
								<textarea class="" rows="5" id="user-intro" placeholder="输入具体需要提醒的内容"></textarea>
								<small>250字以内展现你的信息...</small>
							</div>
						</div>

						<div class="am-form-group">
							<label for="user-email" class="am-u-sm-3 am-form-label">提醒设置：</label>
							<div class="am-u-sm-9">
								<select data-am-selected="{btnSize: 'md'}">
									<option value="option1">按时提醒</option>
									<option value="option2">提前10分钟</option>
									<option value="option3">提前30分钟</option>
									<option value="option3">提前30分钟</option>
									<option value="option3">不提醒</option>
								</select> <small>设置此卡片提醒的方式</small>
							</div>
						</div>
						<hr>
						<div class="am-form-group">
							<div class="am-u-sm-9 am-u-sm-push-3">
								<button type="button" class="am-btn am-btn-primary">保存修改</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		<!-- content end -->

	</div>

	<a href="#" class="am-icon-btn am-icon-th-list am-show-sm-only admin-menu"
		data-am-offcanvas="{target: '#admin-offcanvas'}"></a>


	<!--footer start-->
	<%@ include file="../shared/pageFooter.jsp"%>
	<!--footer end-->

	<!-- js placed at the end of the document so the pages load faster -->
	<!--common script for all pages-->
	<%@ include file="../shared/importJs.jsp"%>

	<!--script for this page-->

	<script src="<c:url value='/assets/js/chosen/amazeui.chosen.min.js'/>"></script>
	<script src="<c:url value='/assets/js/xcloud/schedule/card-form.js'/>"></script>
</body>
</html>
