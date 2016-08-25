<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- sidebar start -->
<div class="admin-sidebar am-offcanvas" id="admin-offcanvas">
	<ul class="am-list admin-sidebar-list">
		
		
	
		
		<li class="admin-parent">
			<a class="am-cf " onclick="setMenuId('collapse-nav-xz-assets')">
				<span class="am-icon-tags"></span>
				资产管理
				<span class="am-icon-angle-right am-fr am-margin-right"></span>
			</a>
			<ul class="am-list am-collapse admin-sidebar-sub"  id="collapse-nav-xz-assets">
				<li>
					<a href="/xcloud/xz/assets/company_asset_list" class="am-cf">
						<span class="am-icon-pencil"></span>
						资产登记
					</a>
				</li>
				<li>
					<a href="/xcloud/xz/assets/use_asset_list">
						<span class="am-icon-reply"></span>
						领用与借用
					</a>
				</li>
				<li>
					<a href="/xcloud/xz/assets/company_asset">
						<span class="am-icon-archive"></span>
						库存管理
					</a>
				</li>
				
			</ul>
		</li>
		
		<li class="admin-parent">
			<a class="am-cf " onclick="setMenuId('collapse-nav-xz-meeting')">
				<span class="am-icon-university"></span>
				会议室管理
				<span class="am-icon-angle-right am-fr am-margin-right"></span>
			</a>
			<ul class="am-list am-collapse admin-sidebar-sub" id="collapse-nav-xz-meeting">
				<li>
					<a href="/xcloud/xz/meeting/list" class="am-cf">
						<span class="am-icon-calendar"></span>
						会议情况一览
					</a>
				</li>
				<li>
					<a href="/xcloud/xz/meeting/room-list/">
						<span class="am-icon-gear"></span>
						会议室设置
					</a>
				</li>
				
			</ul>
		</li>
		
		<li class="admin-parent">
			<a class="am-cf " onclick="setMenuId('collapse-nav-xz-express')">
				<span class="am-icon-truck"></span>
				快递管理
				<span class="am-icon-angle-right am-fr am-margin-right"></span>
			</a>
			<ul class="am-list am-collapse admin-sidebar-sub" id="collapse-nav-xz-express">
				<li>
					<a href="/xcloud/xz/express/express-form?id=0" class="am-cf">
						<span class="am-icon-pencil"></span>
						快递登记
					</a>
				</li>
				<li>
					<a href="/xcloud/xz/express/list" class="am-cf">
						<span class="am-icon-search-plus"></span>
						服务单查阅
					</a>
				</li>
				
			</ul>
		</li>
		<li class="admin-parent">
			<a class="am-cf" onclick="setMenuId('collapse-nav-xz-water')">
				<span class="am-icon-coffee"></span>
				茶点饮用水
				<span class="am-icon-angle-right am-fr am-margin-right"></span>
			</a>
			<ul class="am-list am-collapse admin-sidebar-sub" id="collapse-nav-xz-water">
				<li>
					<a href="/xcloud/xz/water/water-form" class="am-cf">
						<span class="am-icon-shopping-cart "></span>
						茶点饮用水订购
					</a>
				</li>
				<li>
					<a href="/xcloud/xz/water/list">
						<span class="am-icon-search-plus"></span>
						服务单查阅
					</a>
				</li>
				
			</ul>
		</li>
		<li class="admin-parent">
			<a class="am-cf" onclick="setMenuId('collapse-nav-xz-clean')">
				<span class="am-icon-bitbucket"></span>
				办公环境
				<span class="am-icon-angle-right am-fr am-margin-right"></span>
			</a>
			<ul class="am-list am-collapse admin-sidebar-sub" id="collapse-nav-xz-clean">
				<li>
					<a href="/xcloud/xz/clean/clean-form" class="am-cf">
						<span class="am-icon-shopping-cart"></span>
						保洁维修预约
					</a>
				</li>
				<li>
					<a href="/xcloud/xz/clean/list">
						<span class="am-icon-search-plus"></span>
						服务单查阅
					</a>
				</li>
				
			</ul>
		</li>
		<li class="admin-parent">
			<a class="am-cf" onclick="setMenuId('collapse-nav-xz-recycle')">
				<span class="am-icon-recycle"></span>
				废品回收
				<span class="am-icon-angle-right am-fr am-margin-right"></span>
			</a>
			<ul class="am-list am-collapse admin-sidebar-sub" id="collapse-nav-xz-recycle">
				<li>
					<a href="/xcloud/xz/recycle/recycle-form" class="am-cf">
						<span class="am-icon-shopping-cart"></span>
						回收服务预约
					</a>
				</li>
				<li>
					<a href="/xcloud/xz/recycle/list">
						<span class="am-icon-search-plus"></span>
						服务单查阅
					</a>
				</li>
				
			</ul>
		</li>
		
		<li><a href="/xcloud/xz/service"><span class="am-icon-binoculars"></span> 找服务商</a></li>
		
	</ul>
</div>
<!-- sidebar end -->
