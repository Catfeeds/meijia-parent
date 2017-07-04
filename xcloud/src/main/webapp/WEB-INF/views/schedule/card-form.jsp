<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="../shared/taglib.jsp"%>
<html>
<head>
<!--common css for all pages-->
<%@ include file="../shared/importCss.jsp"%>
<!--css for this page-->
<link href="<c:url value='/assets/js/amazeui.datatables/amazeui.datatables.min.css'/>" rel="stylesheet">
<link href="<c:url value='/assets/js/tagsinput/amazeui.tagsinput.css'/>" rel="stylesheet">
<link href="<c:url value='/assets/js/datetimepicker/amazeui.datetimepicker.css'/>" rel="stylesheet">
<link href="<c:url value='/assets/js/switch/amazeui.switch.css'/>" rel="stylesheet">
<link href="<c:url value='/assets/js/charfirst/style.css'/>" rel="stylesheet">
</head>
<body>
	<!--header start-->
	<%@ include file="../shared/pageHeader.jsp"%>
	<!--header end-->
	<div class="am-cf admin-main">
		<!-- sidebar start -->
		<%@ include file="../schedule/schedule-menu.jsp"%>
		<!-- sidebar end -->
		<!-- content start -->
		<div class="admin-content">
			<div class="am-cf am-padding">
				<div class="am-fl am-cf">
					<strong class="am-text-primary am-text-lg">${cardTypeName }</strong> / <small>设置详细信息</small>
				</div>
			</div>
			<hr />
			<div class="am-g">
				<div class="am-u-sm-12 am-u-md-4 am-u-md-push-8">
					<section class="am-panel am-panel-default"> <header class="am-panel-hd"> <img
						src="<c:url value='/assets/img/a1.png'/>" class="am-img-thumbnail am-circle" width="35" height="35"> 菠萝小秘提示您
					</header>
					<div class="am-panel-bd">${cardTips }</div>
					</section>
				</div>
				<div class="am-u-sm-12 am-u-md-8 am-u-md-pull-4">
					<form modelAttribute="contentModel" method="POST" id="card-form" class="am-form am-form-horizontal">
						<input type="hidden" id="cardId" value="0" />
						<input type="hidden" id="cardType" value="${cardType}" />
						<input type="hidden" id="userId" value="${userId}" />
						<div class="am-form-group">
							<label for="user-name" class="am-u-sm-3 am-form-label">${labelAttendStr }：</label>
							<div class="am-u-sm-9">
								<div class="am-input-group">
									<button type="button" class="am-btn am-btn-primary" data-am-modal="{target: '#chosen-popup'}">点击选择人员</button>
									<input type="hidden" id="selectUserIds" name="selectUserIds" value="" required class="am-form-field"
										minlength="1" />
									<input type="hidden" id="selectUserMobiles" name="selectUserMobiles" value="" />
									<input type="hidden" id="selectUserNamesHidden" name="selectUserNamesHidden" value="" />
									<small>选择需要发送与分享的好友</small>
								</div>
								<small id="selectUserNames"></small>
							</div>
						</div>
						<div class="am-form-group">
							<label for="user-email" class="am-u-sm-3 am-form-label">${labelTimeStr }：</label>
							<div class="am-u-sm-9">
								<div class="am-input-group date form_datetime form-datetime-lang" data-date="">
									<input type="text" id="serviceTime" name="serviceTime" size="16" value="" class="am-form-field" readonly
										minlength="1" maxlength="20">
									<span class="am-input-group-label add-on">
										<i class="icon-th am-icon-calendar"></i>
									</span>
								</div>
								<small>选择此卡片希望提醒的时间</small>
							</div>
						</div>
						<c:if test="${cardType == 1}">
							<div class="am-form-group">
								<label for="user-intro" class="am-u-sm-3 am-form-label">会议地址：</label>
								<div class="am-u-sm-9">
									<input type="text" id="serviceAddr" name="serviceAddr" class="" placeholder="输入会议地址" minlength="1"
										maxlength="32">
									<small></small>
								</div>
							</div>
						</c:if>
						<div class="am-form-group">
							<label for="user-intro" class="am-u-sm-3 am-form-label">${labelContentStr }：</label>
							<div class="am-u-sm-9">
								<textarea id="serviceContent" name="serviceContent" class="" rows="5" placeholder="输入具体内容" minlength="1"
									maxlength="250"></textarea>
								<small>250字以内展现你的信息...</small>
							</div>
						</div>
						<div class="am-form-group">
							<label for="user-email" class="am-u-sm-3 am-form-label">提醒设置：</label>
							<div class="am-u-sm-9">
								<select id="setRemind" data-am-selected="{btnSize: 'md', btnStyle: 'warning', dropUp: 1}">
									<option value="1">按时提醒</option>
									<option value="2">提前5分钟</option>
									<option value="3">提前15分钟</option>
									<option value="4">提前30分钟</option>
									<option value="5">提前1小时</option>
									<option value="6">提前2小时</option>
									<option value="7">提前6小时</option>
									<option value="8">提前1天</option>
									<option value="9">提前2天</option>
								</select>
								<small>设置此卡片提醒的方式</small>
							</div>
						</div>
						<div class="am-form-group">
							<label for="user-email" class="am-u-sm-3 am-form-label">立即告知相关人员：</label>
							<div class="am-u-sm-9">
								<input id="setNowSend" type="checkbox" data-on-color="success">
								<small></small>
							</div>
						</div>
						<hr>
						<div class="am-form-group">
							<div class="am-u-sm-9 am-u-sm-push-3">
								<button type="button" id="btn-card-submit" class="am-btn am-btn-primary">确定保存</button>
							</div>
						</div>
					</form>
				</div>
			</div>
			<!-- select popup start -->
			<div class="am-popup" id="chosen-popup" style="OVERFLOW-Y: auto; OVERFLOW-X: hidden;">
				<div class="am-popup-inner">
					<div class="am-popup-hd">
						<h4 class="am-popup-title">通讯录</h4>
						<span data-am-modal-close class="am-close am-close-alt">&times;</span>
						<div id="letter"></div>
						<div><input id="selected_users" type="text" value="" readonly data-role="tagsinput"  style="border: 0px;" /></div>
						<div class="sort_box">
							<c:forEach items="${staffList}" var="item">
								<div class="sort_list ">
									<div class="am-g">
										<div class="am-u-sm-3">
											<div class="num_logo"><img src="${item.headImg }" /></div>
										</div>
										<div class="am-u-sm-6">
											<div class="num_name">${item.name }</div>
										</div>
										<div class="am-u-sm-3 am-fl">
											<label class="am-checkbox"> 
												<input id="checkbox-${item.userId})" type="checkbox" value="${item.name }" onclick="setSelectUser(this, ${item.userId}, '${item.name }', '${item.mobile }')">
											</label>
										</div>
									</div>
								</div>
							</c:forEach>
						</div>
					</div>
					<div class="initials">
						<ul>
							<li>
								<img src="<c:url value='/assets/js/charfirst/img/068.png'/>" />
							</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
		<!-- select popup end -->
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
	<script src="<c:url value='/assets/js/amazeui.datatables/amazeui.datatables.min.js'/>"></script>
	<script src="<c:url value='/assets/js/amazeui.datatables/dataTables.responsive.min.js'/>"></script>
	<script src="<c:url value='/assets/js/tagsinput/amazeui.tagsinput.min.js'/>"></script>
	<script src="<c:url value='/assets/js/datetimepicker/amazeui.datetimepicker.min.js'/>"></script>
	<script src="<c:url value='/assets/js/datetimepicker/amazeui.datetimepicker.zh-CN.js'/>"></script>
	<script src="<c:url value='/assets/js/switch/amazeui.switch.min.js'/>"></script>
	<script src="<c:url value='/assets/js/charfirst/jquery.charfirst.pinyin.js'/>"></script>
	<script src="<c:url value='/assets/js/charfirst/sort.js'/>"></script>
	<script src="<c:url value='/assets/js/xcloud/schedule/card-form.js'/>"></script>
</body>
</html>
