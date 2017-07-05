<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- sidebar start -->
<div class="admin-sidebar am-offcanvas" id="admin-offcanvas">
	<ul class="am-list admin-sidebar-list">
		
		<li class="admin-parent">
			<a class="am-collapsed" onclick="setMenuId('collapse-nav-hr-entry')"> 
				<span class="am-icon-user"></span>&nbsp; 入职管理
				<span class="am-icon-angle-right am-fr am-margin-right"></span>
			</a>
			<ul class="am-list am-collapse admin-sidebar-sub" id="collapse-nav-hr-entry">
				<li>
					<a href="/xcloud/hr/offer-form" class="am-cf"> 
						<span class="am-icon-folder-open-o"></span>&nbsp; 发offer
					</a>
				</li>
				<li>
					<a href="/xcloud/hr/offer-list" class="am-cf"> 
						<span class="am-icon-user-plus"></span>&nbsp; offer管理
					</a>
				</li>
				<li>
					<a href="/xcloud/hr/staff-flow" class="am-cf"> 
						<span class="am-icon-puzzle-piece"></span>&nbsp; 办理入职
					</a>
				</li>
			</ul>
		</li>
		
		<li><a href="/xcloud/hr/salary-index"><span class="am-icon-binoculars"></span>&nbsp; 薪酬管理</a></li>
		<li><a href="/xcloud/hr/insu-index"><span class="am-icon-binoculars"></span>&nbsp; 社保管理</a></li>
		<li><a href="/xcloud/hr/hr-report"><span class="am-icon-binoculars"></span>&nbsp; 人事报表</a></li>
		
		<li class="admin-parent">
			<a class="am-collapsed" onclick="setMenuId('collapse-nav-hr-staff')"> 
				<span class="am-icon-user"></span>&nbsp; 员工管理
				<span class="am-icon-angle-right am-fr am-margin-right"></span>
			</a>
			<ul class="am-list am-collapse admin-sidebar-sub" id="collapse-nav-hr-staff">
				<li>
					<a href="/xcloud/staff/list" class="am-cf"> 
						<span class="am-icon-folder-open-o"></span>&nbsp; 员工列表
					</a>
				</li>
				<li>
					<a href="/xcloud/staff/staff-form?staff_id=0" class="am-cf"> 
						<span class="am-icon-user-plus"></span>&nbsp; 录入新员工
					</a>
				</li>
				<li>
					<a href="/xcloud/job/job_list" class="am-cf"> 
						<span class="am-icon-puzzle-piece"></span>&nbsp; 职位管理
					</a>
				</li>
				<li>
					<a href="/xcloud/staff/dept/dept_list" class="am-cf"> 
						<span class="am-icon-users"></span>&nbsp; 部门管理
					</a>
				</li>
				<li>
					<a href="/xcloud/company/company-form" class="am-cf"> 
						<span class="am-icon-file-text-o"></span>&nbsp; 公司信息
					</a>
				</li>
				
			</ul>
		</li>
		
		<li class="admin-parent">
			<a class="am-cf " onclick="setMenuId('collapse-nav-xz-checkin')">
				<span class="am-icon-street-view"></span>
				&nbsp; 考勤管理
				<span class="am-icon-angle-right am-fr am-margin-right"></span>
			</a>
			<ul class="am-list am-collapse admin-sidebar-sub"  id="collapse-nav-xz-checkin">
				<li>
					<a href="/xcloud/xz/checkin/stat-list" class="am-cf">
						<span class="am-icon-list-alt"></span>
						 &nbsp; 考勤明细表
					</a>
				</li>
				<li>
					<a href="/xcloud/xz/checkin/stat-total" class="am-cf">
						<span class="am-icon-bar-chart"></span>
						 &nbsp; 考勤统计表
					</a>
				</li>
				<li>
					<a href="/xcloud/xz/leave/list" class="am-cf">
						<span class="am-icon-twitch"></span>
						 &nbsp; 请假情况查阅
					</a>
				</li>
				<li>
					<a href="/xcloud/xz/checkin/list" class="am-cf">
						<span class="am-icon-table"></span>
						 &nbsp; 考勤日志查询
					</a>
				</li>
				<li>
					<a href="/xcloud/xz/checkin/net" class="am-cf">
						<span class="am-icon-gear"></span>
						 &nbsp; 考勤相关设置
					</a>
				</li>
			</ul>
		</li>
		
		<li class="admin-parent">
			<a class="am-collapsed" onclick="setMenuId('collapse-nav-hr-recruit')"> 
				<span class="am-icon-flag"></span>&nbsp; 招聘管理
				<span class="am-icon-angle-right am-fr am-margin-right"></span>
			</a>
			<ul class="am-list am-collapse admin-sidebar-sub" id="collapse-nav-hr-recruit">
				<li>
					<a href="/xcloud/hr/resume/resume_exchange_list" class="am-cf"> 
						<span class="am-icon-refresh"></span>&nbsp; 简历交换
					</a>
				</li>
				
				<li>
					<a href="/xcloud/hr/hunter/job_publish_list" class="am-cf"> 
						<span class="am-icon-compass"></span>&nbsp; 内推悬赏
					</a>
				</li>
			
			</ul>
		</li>
		
		
		<li><a href="/xcloud/hr/service"><span class="am-icon-binoculars"></span>&nbsp; 找服务商</a></li>
	</ul>
</div>
<!-- sidebar end -->
