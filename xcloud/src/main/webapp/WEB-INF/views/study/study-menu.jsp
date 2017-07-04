<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- sidebar start -->
<div class="admin-sidebar am-offcanvas" id="admin-offcanvas">
	<ul class="am-list admin-sidebar-list">
		
		<li class="admin-parent">
			<a class="am-collapsed" onclick="setMenuId('collapse-nav-hr-staff')"> 
				<span class="am-icon-user"></span>&nbsp; 学习天地
				<span class="am-icon-angle-right am-fr am-margin-right"></span>
			</a>
			<ul class="am-list am-collapse admin-sidebar-sub" id="collapse-nav-hr-staff">
				<li>
					<a href="/xcloud/staff/list" class="am-cf"> 
						<span class="am-icon-folder-open-o"></span>&nbsp; 精选文章
					</a>
				</li>
				<li>
					<a href="/xcloud/staff/staff-form?staff_id=0" class="am-cf"> 
						<span class="am-icon-user-plus"></span>&nbsp; 视听课程
					</a>
				</li>
				<li>
					<a href="/xcloud/staff/staff-form?staff_id=0" class="am-cf"> 
						<span class="am-icon-user-plus"></span>&nbsp; 资料下载
					</a>
				</li>
			</ul>
		</li>
		
		<li class="admin-parent">
			<a class="am-cf " onclick="setMenuId('collapse-nav-xz-checkin')">
				<span class="am-icon-street-view"></span>
				&nbsp; 问答互助
				<span class="am-icon-angle-right am-fr am-margin-right"></span>
			</a>
			<ul class="am-list am-collapse admin-sidebar-sub"  id="collapse-nav-xz-checkin">
				<li>
					<a href="/xcloud/xz/checkin/stat-list" class="am-cf">
						<span class="am-icon-list-alt"></span>
						 &nbsp; 提问
					</a>
				</li>
				<li>
					<a href="/xcloud/xz/checkin/stat-total" class="am-cf">
						<span class="am-icon-bar-chart"></span>
						 &nbsp; 查看问题
					</a>
				</li>
			</ul>
		</li>
		<li><a href="/xcloud/hr/service"><span class="am-icon-binoculars"></span>&nbsp; 发现</a></li>
	</ul>
</div>
<!-- sidebar end -->
