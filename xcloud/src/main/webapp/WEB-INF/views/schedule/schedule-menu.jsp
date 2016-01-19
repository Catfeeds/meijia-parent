<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
%>

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


