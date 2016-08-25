<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- sidebar start -->
<div class="admin-sidebar am-offcanvas" id="admin-offcanvas">
	<ul class="am-list admin-sidebar-list">
		
		<li class="admin-parent">
			<a class="am-collapsed" onclick="setMenuId('collapse-nav-hr-staff')"> 
				<span class="am-icon-user"></span> 员工管理
				<span class="am-icon-angle-right am-fr am-margin-right"></span>
			</a>
			<ul class="am-list am-collapse admin-sidebar-sub" id="collapse-nav-hr-staff">
				<li>
					<a href="/xcloud/staff/list" class="am-cf"> 
						<span class="am-icon-folder-open-o"></span> 人事档案
					</a>
				</li>
				
				<li>
					<a href="/xcloud/job/job_list" class="am-cf"> 
						<span class="am-icon-puzzle-piece"></span> 职位管理
					</a>
				</li>
				<li>
					<a href="/xcloud/staff/dept/dept_list" class="am-cf"> 
						<span class="am-icon-users"></span> 部门管理
					</a>
				</li>
				<li>
					<a href="/xcloud/company/company-form" class="am-cf"> 
						<span class="am-icon-file-text-o"></span> 公司信息
					</a>
				</li>
				
			</ul>
		</li>
		
		<li class="admin-parent">
			<a class="am-cf " onclick="setMenuId('collapse-nav-hr-checkin')">
				<span class="am-icon-file"></span>
				考勤管理
				<span class="am-icon-angle-right am-fr am-margin-right"></span>
			</a>
			<ul class="am-list am-collapse admin-sidebar-sub"  id="collapse-nav-xz-checkin">
				<li>
					<a href="/xcloud/xz/checkin/stat-list" class="am-cf">
						<span class="am-icon-check"></span>
						 考勤明细表
					</a>
				</li>
				<li>
					<a href="/xcloud/xz/checkin/stat-total" class="am-cf">
						<span class="am-icon-bars"></span>
						 考勤统计表
					</a>
				</li>
				<li>
					<a href="/xcloud/xz/checkin/net" class="am-cf">
						<span class="am-icon-map-marker"></span>
						 出勤配置
					</a>
				</li>
				<li>
					<a href="/xcloud/xz/checkin/list" class="am-cf">
						<span class="am-icon-table"></span>
						 考勤日志列表
					</a>
				</li>
				<li>
					<a href="/xcloud/xz/leave/list" class="am-cf">
						<span class="am-icon-pause"></span>
						 请假列表
					</a>
				</li>
			</ul>
		</li>
		
		<li class="admin-parent">
			<a class="am-collapsed" onclick="setMenuId('collapse-nav-hr-recruit')"> 
				<span class="am-icon-flag"></span> 招聘管理
				<span class="am-icon-angle-right am-fr am-margin-right"></span>
			</a>
			<ul class="am-list am-collapse admin-sidebar-sub" id="collapse-nav-hr-recruit">
				<li>
					<a href="/xcloud/hr/resume/resume_exchange_list" class="am-cf"> 
						<span class="am-icon-exchange"></span> 简历交换大厅
					</a>
				</li>
				
				<li>
					<a href="/xcloud/hr/hunter/job_publish_list" class="am-cf"> 
						<span class="am-icon-dot-circle-o"></span> 内推悬赏大厅
					</a>
				</li>
			
				<!-- <li>
					<a href="#" class="am-cf"> 
						<span class="am-icon-forumbee"></span> 发布职位
					</a>
				</li>
				<li>
					<a href="#"> 
						<span class="am-icon-list"></span> 职位一览
					</a>
				</li>
				
				<li>
					<a href="/xcloud/hr/hunter/job_publish_list" class="am-cf"> 
						<span class="am-icon-dot-circle-o"></span> 内推悬赏大厅
					</a>
				</li> -->
			</ul>
		</li>
		
		<!-- <li class="admin-parent">
			<a class="am-collapsed" onclick="setMenuId('collapse-nav-hr-resume')"> 
				<span class="am-icon-th-list"></span> 企业人才库
				<span class="am-icon-angle-right am-fr am-margin-right"></span>
			</a>
			<ul class="am-list am-collapse admin-sidebar-sub" id="collapse-nav-hr-resume">
				<li>
					<a href="#" class="am-cf"> 
						<span class="am-icon-cloud-upload"></span> 导入简历
					</a>
				</li>
				<li>
					<a href="/xcloud/hr/resume/resume_exchange_list" class="am-cf"> 
						<span class="am-icon-exchange"></span> 简历交换大厅
					</a>
				</li>
				<li>
					<a href="#" class="am-cf"> 
						<span class="am-icon-rss"></span> 人才信息订阅
					</a>
				</li>
			</ul>
		</li> -->
		<li><a href="/xcloud/hr/service"><span class="am-icon-th"></span> 找服务商</a></li>
	</ul>
</div>
<!-- sidebar end -->
