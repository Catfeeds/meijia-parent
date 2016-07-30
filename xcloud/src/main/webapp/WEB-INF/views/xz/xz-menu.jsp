<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- sidebar start -->
<div class="admin-sidebar am-offcanvas" id="admin-offcanvas">
	<ul class="am-list admin-sidebar-list">
		
		<li class="admin-parent">
			<a class="am-cf " onclick="setMenuId('collapse-nav-xz-checkin')">
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
					<a href="/xcloud/xz/checkin/stat" class="am-cf">
						<span class="am-icon-check"></span>
						考勤统计表
					</a>
				</li>
				<li>
					<a href="/xcloud/xz/checkin/net"
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
			</ul>
		</li>
	
		
		<li class="admin-parent">
			<a class="am-cf " onclick="setMenuId('collapse-nav-xz-assets')">
				<span class="am-icon-file"></span>
				资产管理
				<span class="am-icon-angle-right am-fr am-margin-right"></span>
			</a>
			<ul class="am-list am-collapse admin-sidebar-sub"  id="collapse-nav-xz-assets">
				<li>
					<a href="/xcloud/xz/assets/commpany_asset_list" class="am-cf">
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
					<a href="#">
						<span class="am-icon-archive"></span>
						库存管理
					</a>
				</li>
				<li>
					<a href="admin-404.html">
						<span class="am-icon-shopping-cart"></span>
						办公用品采购
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
						查询与结算
					</a>
				</li>
				<li>
					<a href="/xcloud/xz/express/service" class="am-cf">
						<span class="am-icon-star"></span>
						快递服务商
					</a>
				</li>
			</ul>
		</li>
		<li class="admin-parent">
			<a class="am-cf" onclick="setMenuId('collapse-nav-xz-water')">
				<span class="am-icon-coffee"></span>
				饮用水管理
				<span class="am-icon-angle-right am-fr am-margin-right"></span>
			</a>
			<ul class="am-list am-collapse admin-sidebar-sub" id="collapse-nav-xz-water">
				<li>
					<a href="/xcloud/xz/water/water-form" class="am-cf">
						<span class="am-icon-shopping-cart "></span>
						饮用水订购
					</a>
				</li>
				<li>
					<a href="/xcloud/xz/water/list">
						<span class="am-icon-search-plus"></span>
						查询与结算
					</a>
				</li>
				<li>
					<a href="/xcloud/xz/water/service">
						<span class="am-icon-star"></span>
						饮用水服务商
					</a>
				</li>
			</ul>
		</li>
		<li class="admin-parent">
			<a class="am-cf" onclick="setMenuId('collapse-nav-xz-clean')">
				<span class="am-icon-bitbucket"></span>
				企业保洁
				<span class="am-icon-angle-right am-fr am-margin-right"></span>
			</a>
			<ul class="am-list am-collapse admin-sidebar-sub" id="collapse-nav-xz-clean">
				<li>
					<a href="/xcloud/xz/clean/clean-form" class="am-cf">
						<span class="am-icon-phone"></span>
						保洁预约
					</a>
				</li>
				<li>
					<a href="/xcloud/xz/clean/list">
						<span class="am-icon-search-plus"></span>
						查询与结算
					</a>
				</li>
				<li>
					<a href="/xcloud/xz/clean/service">
						<span class="am-icon-star"></span>
						保洁服务商
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
						<span class="am-icon-phone"></span>
						回收预约
					</a>
				</li>
				<li>
					<a href="/xcloud/xz/recycle/list">
						<span class="am-icon-search-plus"></span>
						查询与结算
					</a>
				</li>
				<li>
					<a href="/xcloud/xz/recycle/service">
						<span class="am-icon-star"></span>
						废品回收服务商
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
						会议一览
					</a>
				</li>
				<li>
					<a href="/xcloud/xz/meeting/room-list/">
						<span class="am-icon-calendar"></span>
						会议室设置
					</a>
				</li>
				<li>
					<a href="/xcloud/xz/meeting/service">
						<span class="am-icon-star"></span>
						会展服务商
					</a>
				</li>
			</ul>
		</li>
	</ul>
</div>
<!-- sidebar end -->
