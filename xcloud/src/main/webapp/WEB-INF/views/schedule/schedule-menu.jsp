<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- sidebar start -->
<div class="admin-sidebar am-offcanvas" id="admin-offcanvas">

	<ul class="am-list admin-sidebar-list">
		<li><a href="/xcloud/schedule/list" onclick="setMenuId('collapse-nav-shcedule-list')"><span class="am-icon-tags"></span> </span> 我的日程</a></li>
		<li class="admin-parent"><a class="am-cf" onclick="setMenuId('collapse-nav-shcedule-card')"><span
				class="am-icon-bell-o"></span> 通知与提醒 <span class="am-icon-angle-right am-fr am-margin-right"></span></a>
			<ul class="am-list am-collapse admin-sidebar-sub am-in" id="collapse-nav-shcedule-card">
				<li><a href="/xcloud/schedule/card-form?card_type=2"><span class="am-icon-th"></span> 通知公告</a></li>
				<li><a href="/xcloud/schedule/card-form?card_type=3" class="am-cf"><span class="am-icon-bell"></span>
						事务提醒</a></li>
				<li><a href="/xcloud/schedule/card-form?card_type=1"><span class="am-icon-users"></span> 会议安排</a></li>
				<li><a href="/xcloud/schedule/card-form?card_type=4"><span class="am-icon-calendar"></span> 面试邀约</a></li>
				
			</ul></li>
		<li><a href="/xcloud/schedule/setting"><span class="am-icon-th"></span> 常用提醒设置</a></li>
	</ul>


</div>
<!-- sidebar end -->


