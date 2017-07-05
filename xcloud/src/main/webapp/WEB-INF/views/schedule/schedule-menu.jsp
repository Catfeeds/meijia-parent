<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- sidebar start -->
<div class="admin-sidebar am-offcanvas" id="admin-offcanvas">
	<ul class="am-list admin-sidebar-list">
		<li class="admin-parent">
			<a class="am-collapsed" onclick="setMenuId('collapse-nav-atools')">
				<span class="am-icon-calculator"></span>
				&nbsp; 常用计算器
				<span class="am-icon-angle-right am-fr am-margin-right"></span>
			</a>
			<ul class="am-list am-collapse admin-sidebar-sub" id="collapse-nav-atools">
				<li>
					<a href="/xcloud/atools/tools-insu" class="am-cf">
						<span class="am-icon-tag"></span>
						&nbsp; 社保公积金计算器
					</a>
				</li>
				<li>
					<a href="/xcloud/atools/tools-tax" class="am-cf">
						<span class="am-icon-tag"></span>
						&nbsp; 个税计算器
					</a>
				</li>
				<li>
					<a href="/xcloud/atools/tools-year" class="am-cf">
						<span class="am-icon-tag"></span>
						&nbsp; 年终奖计算器
					</a>
				</li>
				<li>
					<a href="/xcloud/atools/tools-pay" class="am-cf">
						<span class="am-icon-tag"></span>
						&nbsp; 劳务报酬所得计算器
					</a>
				</li>
			</ul>
		</li>
		<li>
			<a href="/xcloud/schedule/list" onclick="setMenuId('collapse-nav-shcedule-list')">
				<span class="am-icon-calendar"></span>
				</span>
				&nbsp; 我的日程
			</a>
		</li>
		<li class="admin-parent">
			<a class="am-cf" onclick="setMenuId('collapse-nav-shcedule-card')">
				<span class="am-icon-bell"></span>
				&nbsp; 通知与提醒
				<span class="am-icon-angle-right am-fr am-margin-right"></span>
			</a>
			<ul class="am-list am-collapse admin-sidebar-sub am-in" id="collapse-nav-shcedule-card">
				<li>
					<a href="/xcloud/schedule/card-form?card_type=2">
						<span class="am-icon-bullhorn"></span>
						&nbsp; 通知公告
					</a>
				</li>
				<li>
					<a href="/xcloud/schedule/card-form?card_type=3" class="am-cf">
						<span class="am-icon-th"></span>
						&nbsp; 事务提醒
					</a>
				</li>
				<li>
					<a href="/xcloud/schedule/card-form?card_type=1">
						<span class="am-icon-file-audio-o"></span>
						&nbsp; 会议安排
					</a>
				</li>
				<li>
					<a href="/xcloud/schedule/card-form?card_type=4">
						<span class="am-icon-tty"></span>
						&nbsp; 面试邀约
					</a>
				</li>
			</ul>
		</li>
		<li>
			<a href="/xcloud/schedule/setting">
				<span class="am-icon-gear"></span>
				&nbsp; 常用提醒设置
			</a>
		</li>
	</ul>
</div>
<!-- sidebar end -->
