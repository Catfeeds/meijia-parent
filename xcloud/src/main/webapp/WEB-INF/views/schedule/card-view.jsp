<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
%>
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
		<div class="admin-sidebar am-offcanvas" id="admin-offcanvas">
			<div class="am-offcanvas-bar admin-offcanvas-bar">
				<ul class="am-list admin-sidebar-list">
					<li><a href="/xcloud/schedule/list"><span class="am-icon-tags"></span>
							日程管理</a></li>
					<li class="admin-parent"><a class="am-cf"
						data-am-collapse="{target: '#collapse-nav'}"
					><span class="am-icon-file"></span> 工作卡片 <span
							class="am-icon-angle-right am-fr am-margin-right"
						></span></a>
						<ul class="am-list am-collapse admin-sidebar-sub am-in" id="collapse-nav">
							<li><a href="/xcloud/schedule/card-form?card_type=2"><span
									class="am-icon-th"
								></span> 通知公告</a></li>
							<li><a href="/xcloud/schedule/card-form?card_type=3" class="am-cf"><span
									class="am-icon-bell"
								></span> 事务提醒</a></li>
							<li><a href="/xcloud/schedule/card-form?card_type=1"><span
									class="am-icon-users"
								></span> 会议安排</a></li>
							<li><a href="/xcloud/schedule/card-form?card_type=4"><span
									class="am-icon-calendar"
								></span> 面试邀约</a></li>
							<!-- 							<li><a href="/xcloud/schedule/card-form?card_type=5"><span class="am-icon-plane"></span>
									差旅规划</a></li> -->
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
					<strong class="am-text-primary am-text-lg">${cardTypeName }</strong> / <small>Notice
						Alarm</small>
				</div>
			</div>

			<hr />

			<div class="am-g">
				<div class="am-u-sm-12 am-u-md-4 am-u-md-push-8">
					<section class="am-panel am-panel-default"> <header
						class="am-panel-hd"
					> <img src="<c:url value='/assets/img/a1.png'/>"
						class="am-img-thumbnail am-circle" width="35" height="35"
					> 云小秘提示您 </header>
					<div class="am-panel-bd">${cardTips }</div>
					</section>
				</div>

				<div class="am-u-sm-12 am-u-md-8 am-u-md-pull-4">
					<form modelAttribute="contentModel" method="POST" id="comment-form"
						class="am-form am-form-horizontal"
					>
						<input type="hidden" id="cardId" value="${cardId }" /> 
						<input type="hidden" id="userId" value="${userId }" />
						<input type="hidden" id="headImg" value="${headImg }" />
						<input type="hidden" id="name" value="${name }" />
						<div class="am-form-group">
							<label for="user-name" class="am-u-sm-3 am-form-label">${labelAttendStr }：</label>

							<label class="am-sm-9 am-form-label ">
								<c:forEach items="${attends}" var="item">
									${item.name},
								</c:forEach>
							</label>
						</div>

						<div class="am-form-group">
							<label for="user-email" class="am-u-sm-3 am-form-label">${labelTimeStr }：</label>
							<label class="am-sm-9 am-form-label">${serviceTimeStr}</label>
						</div>

						<c:if test="${cardType == 1}">
							<div class="am-form-group">
								<label for="user-intro" class="am-u-sm-3 am-form-label">会议地址：</label>
								<label class="am-sm-9 am-form-label">${serviceAddr}</label>
							</div>
						</c:if>

						<div class="am-form-group">
							<label for="user-intro" class="am-u-sm-3 am-form-label">${labelContentStr }：</label>
							<label class="am-sm-9 am-form-label">${serviceContent}</label>
						</div>
						<hr>
						
						<div class="am-comment">
							<img width="48" height="48" class="am-comment-avatar" alt="" src="${headImg }">
							
							<div class="am-comment-main">
								<header class="am-comment-hd">
								<div class="am-comment-meta">
									<span class="am-fr">
										<button type="button" class="am-btn am-btn-success am-btn-xs" id="btn-comment-submit">发布</button>
									</span>
									
								</div>
								</header>
								
								<div class="am-comment-bd">
									<textarea id="comment" placeholder="说点什么吧…" name="message" minLength=1 maxLength="140" class="am-form-field am-radius" requried></textarea>
									
								</div>
							</div>
							
						</div>
						
						<hr>

						<ul id="comment-list" class="am-comments-list am-comments-list-flip">

						</ul>
						<button type="button" id="btn-get-more"
							class="am-btn am-btn-primary am-btn-block" style="display: none"
						>
							<i class="am-icon-spinner am-icon-spin"></i> 加载更多
						</button>
						<hr>
						<div class="am-form-group">
							<div class="am-u-sm-9 am-u-sm-push-3">
								<button type="button" class="am-btn am-btn-success" id="btn-return">返回</button>
							</div>
						</div>
					
				</div>
			</div>
		</div>

		<div id="comment-list-part" style="display: none">
			<li class="am-comment">{imgTag}

				<div class="am-comment-main">
					<header class="am-comment-hd">
					<div class="am-comment-meta">
						<a class="am-comment-author" href="#link-to-user">{name}</a> 评论于
						<time title="" datetime="">{addTimeStr}</time>
					</div>
					</header>
					<div class="am-comment-bd">
						<p>{commentContent}</p>

					</div>
				</div>
			</li>
		</div>


		<!-- content end -->

	</div>

	<a href="#" class="am-icon-btn am-icon-th-list am-show-sm-only admin-menu"
		data-am-offcanvas="{target: '#admin-offcanvas'}"
	></a>


	<!--footer start-->
	<%@ include file="../shared/pageFooter.jsp"%>
	<!--footer end-->

	<!-- js placed at the end of the document so the pages load faster -->
	<!--common script for all pages-->
	<%@ include file="../shared/importJs.jsp"%>

	<!--script for this page-->
	<script src="<c:url value='/assets/js/xcloud/schedule/card-view.js'/>"></script>
</body>
</html>
