<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- sidebar start -->
<div class="admin-sidebar am-offcanvas" id="admin-offcanvas">

	<ul class="am-list admin-sidebar-list">
		<li class="admin-parent"><a class="am-cf am-collapsed" data-am-collapse="{target: '#collapse-nav-checkin'}">
				<span class="am-icon-file"></span> 考勤管理 <span class="am-icon-angle-right am-fr am-margin-right"></span>
		</a>
			<ul class="am-list am-collapse admin-sidebar-sub" id="collapse-nav-checkin">
				<li><a href="admin-user.html" class="am-cf"> <span class="am-icon-bell"></span> 统计报表 <span
						class="am-icon-star am-fr am-margin-right admin-icon-yellow"
					></span>
				</a></li>
				<li><a href="admin-404.html"> <span class="am-icon-plane"></span> 排班管理
				</a></li>
				<li><a href="admin-log.html"> <span class="am-icon-calendar"></span> 基础设置
				</a></li>
			</ul></li>
		<li class="admin-parent"><a class="am-cf am-collapsed" data-am-collapse="{target: '#collapse-nav-meeting'}">
				<span class="am-icon-file"></span> 会议管理 <span class="am-icon-angle-right am-fr am-margin-right"></span>
		</a>
			<ul class="am-list am-collapse admin-sidebar-sub" id="collapse-nav-meeting">
				<li><a href="/xcloud/xz/meeting/list" class="am-cf"> <span class="am-icon-bell"></span> 会议一览 <span
						class="am-icon-star am-fr am-margin-right admin-icon-yellow"
					></span>
				</a></li>
				<li><a href="/xcloud/xz/meeting/setting/"> <span class="am-icon-calendar"></span> 会议设置
				</a></li>
				<li><a href="/xcloud/xz/meeting/service"> <span class="am-icon-calendar"></span> 会展服务商
				</a></li>
			</ul></li>
		<li class="admin-parent"><a class="am-cf am-collapsed" data-am-collapse="{target: '#collapse-nav-express'}">

				<span class="am-icon-file"></span> 快递管理 <span class="am-icon-angle-right am-fr am-margin-right"></span>
		</a>
			<ul class="am-list am-collapse admin-sidebar-sub" id="collapse-nav-express">
				<li><a href="/xcloud/xz/express/express-form" class="am-cf"> <span class="am-icon-bell"></span> 查询与登记 <span
						class="am-icon-star am-fr am-margin-right admin-icon-yellow"
					></span>
				</a></li>
				<li><a href="/xcloud/xz/express/list" class="am-cf"> <span class="am-icon-plane"></span> 快递结算
				</a></li>
				<li><a href="/xcloud/xz/express/service" class="am-cf"> <span class="am-icon-calendar"></span> 快递服务商
				</a></li>
			</ul></li>
		<li class="admin-parent"><a class="am-collapsed" data-am-collapse="{target: '#collapse-nav-water'}"> <span
				class="am-icon-file"
			></span> 饮用水管理 <span class="am-icon-angle-right am-fr am-margin-right"></span>
		</a>
			<ul class="am-list am-collapse admin-sidebar-sub" id="collapse-nav-water">
				<li><a href="/xcloud/xz/water/water-form" class="am-cf"> <span class="am-icon-bell"></span> 送水预约 <span
						class="am-icon-star am-fr am-margin-right admin-icon-yellow"
					></span>
				</a></li>
				<li><a href="/xcloud/xz/water/list"> <span class="am-icon-plane"></span> 饮用水结算
				</a></li>
				<li><a href="/xcloud/xz/water/service"> <span class="am-icon-calendar"></span> 饮用水服务商
				</a></li>
			</ul></li>
			
			
		<li class="admin-parent"><a class="am-collapsed" data-am-collapse="{target: '#collapse-nav-clean'}"> <span
				class="am-icon-file"
			></span> 企业保洁 <span class="am-icon-angle-right am-fr am-margin-right"></span>
		</a>
			<ul class="am-list am-collapse admin-sidebar-sub" id="collapse-nav-clean">
				<li><a href="/xcloud/xz/clean/clean-form" class="am-cf"> <span class="am-icon-bell"></span> 保洁预约 <span
						class="am-icon-star am-fr am-margin-right admin-icon-yellow"
					></span>
				</a></li>
				<li><a href="/xcloud/xz/clean/list"> <span class="am-icon-plane"></span> 保洁结算
				</a></li>
				<li><a href="/xcloud/xz/clean/service"> <span class="am-icon-calendar"></span> 保洁服务商
				</a></li>
			</ul></li>
			
		<li class="admin-parent"><a class="am-collapsed" data-am-collapse="{target: '#collapse-nav-teamwork'}"> <span
				class="am-icon-file"
			></span> 企业团建 <span class="am-icon-angle-right am-fr am-margin-right"></span>
		</a>
			<ul class="am-list am-collapse admin-sidebar-sub" id="collapse-nav-teamwork">
				<li><a href="/xcloud/xz/teamwork/teamwork-form" class="am-cf"> <span class="am-icon-bell"></span> 团建预约 <span
						class="am-icon-star am-fr am-margin-right admin-icon-yellow"
					></span>
				</a></li>
				<li><a href="/xcloud/xz/teamwork/list"> <span class="am-icon-plane"></span> 团建结算
				</a></li>
				<li><a href="/xcloud/xz/teamwork/service"> <span class="am-icon-calendar"></span> 团建服务商
				</a></li>
			</ul></li>	
			
		<li class="admin-parent"><a class="am-collapsed" data-am-collapse="{target: '#collapse-nav-recycle'}"> <span
				class="am-icon-file"
			></span> 废品回收 <span class="am-icon-angle-right am-fr am-margin-right"></span>
		</a>
			<ul class="am-list am-collapse admin-sidebar-sub" id="collapse-nav-recycle">
				<li><a href="/xcloud/xz/recycle/recycle-form" class="am-cf"> <span class="am-icon-bell"></span> 回收预约 <span
						class="am-icon-star am-fr am-margin-right admin-icon-yellow"
					></span>
				</a></li>
				<li><a href="/xcloud/xz/recycle/list"> <span class="am-icon-plane"></span> 回收结算
				</a></li>
				<li><a href="/xcloud/xz/recycle/service"> <span class="am-icon-calendar"></span> 团建服务商
				</a></li>
			</ul></li>	
				
		<li class="admin-parent"><a class="am-cf am-collapsed" data-am-collapse="{target: '#collapse-nav-assets'}"> <span
				class="am-icon-file"
			></span> 资产管理 <span class="am-icon-angle-right am-fr am-margin-right"></span>
		</a>
			<ul class="am-list am-collapse admin-sidebar-sub" id="collapse-nav-assets">
				<li><a href="admin-user.html" class="am-cf"> <span class="am-icon-bell"></span> 资产一览 <span
						class="am-icon-star am-fr am-margin-right admin-icon-yellow"
					></span>
				</a></li>
				<li><a href="admin-404.html"> <span class="am-icon-plane"></span> 领用与借用
				</a></li>
				<li><a href="admin-log.html"> <span class="am-icon-calendar"></span> 库存管理
				</a></li>
				<li><a href="admin-404.html"> <span class="am-icon-plane"></span> 办公用品采购
				</a></li>
				<li><a href="admin-log.html"> <span class="am-icon-calendar"></span> 资产常用设置
				</a></li>
			</ul></li>

	</ul>



</div>
<!-- sidebar end -->
