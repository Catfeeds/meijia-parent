<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
%>

<!-- sidebar start -->
<div class="admin-sidebar am-offcanvas" id="admin-offcanvas">
	<div class="am-offcanvas-bar admin-offcanvas-bar">
		<ul class="am-list admin-sidebar-list">
			<li class="admin-parent">
				<a class="am-cf"
				data-am-collapse="{target: '#collapse-nav-checkin'}"
			>
					<span class="am-icon-file"></span>
					考勤管理
					<span
					class="am-icon-angle-right am-fr am-margin-right"
				></span>
				</a>
				<ul class="am-list am-collapse admin-sidebar-sub am-in" id="collapse-nav-checkin">
					<li>
						<a href="admin-user.html" class="am-cf">
							<span
							class="am-icon-bell"
						></span>
							统计报表
							<span class="am-icon-star am-fr am-margin-right admin-icon-yellow"></span>
						</a>
					</li>
					<li>
						<a href="admin-404.html">
							<span class="am-icon-plane"></span>
							排班管理
						</a>
					</li>
					<li>
						<a href="admin-log.html">
							<span class="am-icon-calendar"></span>
							基础设置
						</a>
					</li>
				</ul>
			</li>
			<li class="admin-parent">
				<a class="am-cf"
				data-am-collapse="{target: '#collapse-nav-meeting'}"
			>
					<span class="am-icon-file"></span>
					会议管理
					<span
					class="am-icon-angle-right am-fr am-margin-right"
				></span>
				</a>
				<ul class="am-list am-collapse admin-sidebar-sub am-in" id="collapse-nav-meeting">
					<li>
						<a href="/xcloud/xz/meeting/list" class="am-cf">
							<span
							class="am-icon-bell"
						></span>
							会议一览
							<span class="am-icon-star am-fr am-margin-right admin-icon-yellow"></span>
						</a>
					</li>
					<li>
						<a href="/xcloud/xz/meeting/setting/">
							<span class="am-icon-calendar"></span>
							会议设置
						</a>
					</li>
					<li>
						<a href="/xcloud/xz/meeting/service">
							<span class="am-icon-calendar"></span>
							会展服务商
						</a>
					</li>
				</ul>
			</li>
			<li class="admin-parent">
				<a class="am-cf"
				data-am-collapse="{target: '#collapse-nav-express'}"
			>
					
					<span class="am-icon-file"></span>
					快递管理
					<span
					class="am-icon-angle-right am-fr am-margin-right"
				></span>
				</a>
				<ul class="am-list am-collapse admin-sidebar-sub am-in" id="collapse-nav-express">
					<li>
						<a href="/xcloud/xz/express/list" class="am-cf">
							<span
							class="am-icon-bell"
						></span>
							查询与登记
							<span class="am-icon-star am-fr am-margin-right admin-icon-yellow"></span>
						</a>
					</li>
					<li>
						<a href="/xcloud/xz/express/close" class="am-cf">
							<span class="am-icon-plane"></span>
							快递结算
						</a>
					</li>
					<li>
						<a href="/xcloud/xz/express/service" class="am-cf">
							<span class="am-icon-calendar"></span>
							快递服务商
						</a>
					</li>
				</ul>
			</li>
			<li class="admin-parent">
				<a class="am-cf"
				data-am-collapse="{target: '#collapse-nav-water'}"
			>
					<span class="am-icon-file"></span>
					饮用水管理
					<span
					class="am-icon-angle-right am-fr am-margin-right"
				></span>
				</a>
				<ul class="am-list am-collapse admin-sidebar-sub am-in" id="collapse-nav-water">
					<li>
						<a href="/xcloud/xz/water/water-form" class="am-cf">
							<span
							class="am-icon-bell"
						></span>
							一键送水
							<span class="am-icon-star am-fr am-margin-right admin-icon-yellow"></span>
						</a>
					</li>
					<li>
						<a href="/xcloud/xz/water/list">
							<span class="am-icon-plane"></span>
							饮用水结算
						</a>
					</li>
					<li>
						<a href="/xcloud/xz/water/service">
							<span class="am-icon-calendar"></span>
							饮用水服务商
						</a>
					</li>
				</ul>
			</li>
			<li class="admin-parent">
				<a class="am-cf"
				data-am-collapse="{target: '#collapse-nav-assets'}"
				>
					<span class="am-icon-file"></span>
					资产管理
					<span
					class="am-icon-angle-right am-fr am-margin-right"
				></span>
				</a>
				<ul class="am-list am-collapse admin-sidebar-sub am-in" id="collapse-nav-assets">
					<li>
						<a href="admin-user.html" class="am-cf">
							<span
							class="am-icon-bell"
						></span>
							资产一览
							<span class="am-icon-star am-fr am-margin-right admin-icon-yellow"></span>
						</a>
					</li>
					<li>
						<a href="admin-404.html">
							<span class="am-icon-plane"></span>
							领用与借用
						</a>
					</li>
					<li>
						<a href="admin-log.html">
							<span class="am-icon-calendar"></span>
							库存管理
						</a>
					</li>
					<li>
						<a href="admin-404.html">
							<span class="am-icon-plane"></span>
							办公用品采购
						</a>
					</li>
					<li>
						<a href="admin-log.html">
							<span class="am-icon-calendar"></span>
							资产常用设置
						</a>
					</li>
				</ul>
			</li>

		</ul>

		
	</div>
</div>
<!-- sidebar end -->
