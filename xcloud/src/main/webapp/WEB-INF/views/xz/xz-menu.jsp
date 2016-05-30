<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- sidebar start -->
<div class="admin-sidebar am-offcanvas" id="admin-offcanvas">

	<ul class="am-list admin-sidebar-list">
		
		<li class="admin-parent">
			<a class="am-collapsed" onclick="setMenuId('collapse-nav-xz-excel')" > 
				<span class="am-icon-flag"></span> 行政报表
				<span class="am-icon-angle-right am-fr am-margin-right"></span>
			</a>
			<ul class="am-list am-collapse admin-sidebar-sub" id="collapse-nav-xz-excel">
				<li>
					<a href="#" class="am-cf"> 
						<span class="am-icon-bell"></span> 月报表
					</a>
				</li>
				<li>
					<a href="#"> 
						<span class="am-icon-search-plus"></span> 报表配置
					</a>
				</li>
				
			</ul>
		</li>

		<li class="admin-parent"><a class="am-cf am-collapsed" onclick="setMenuId('collapse-nav-xz-express')" >

				<span class="am-icon-truck"></span> 快递管理 <span class="am-icon-angle-right am-fr am-margin-right"></span>
		</a>
			<ul class="am-list am-collapse admin-sidebar-sub" id="collapse-nav-xz-express">
				<li><a href="/xcloud/xz/express/express-form?id=0" class="am-cf"> <span class="am-icon-bell"></span> 快递登记
				</a></li>
				<li><a href="/xcloud/xz/express/list" class="am-cf"> <span class="am-icon-search-plus"></span> 查询与结算
				</a></li>
				<li><a href="/xcloud/xz/express/service" class="am-cf"> <span class="am-icon-star"></span> 快递服务商
				</a></li>
			</ul></li>
		<li class="admin-parent"><a class="am-collapsed" onclick="setMenuId('collapse-nav-xz-water')" > <span
				class="am-icon-coffee"
			></span> 饮用水管理 <span class="am-icon-angle-right am-fr am-margin-right"></span>
		</a>
			<ul class="am-list am-collapse admin-sidebar-sub" id="collapse-nav-xz-water">
				<li><a href="/xcloud/xz/water/water-form" class="am-cf"> <span class="am-icon-bell"></span> 饮用水订购
				</a></li>
				<li><a href="/xcloud/xz/water/list"> <span class="am-icon-search-plus"></span> 查询与结算
				</a></li>
				<li><a href="/xcloud/xz/water/service"> <span class="am-icon-star"></span> 饮用水服务商
				</a></li>
			</ul></li>


		<li class="admin-parent"><a class="am-collapsed" onclick="setMenuId('collapse-nav-xz-clean')"> <span
				class="am-icon-bitbucket"
			></span> 企业保洁 <span class="am-icon-angle-right am-fr am-margin-right"></span>
		</a>
			<ul class="am-list am-collapse admin-sidebar-sub" id="collapse-nav-xz-clean">
				<li><a href="/xcloud/xz/clean/clean-form" class="am-cf"> <span class="am-icon-bell"></span> 保洁预约
				</a></li>
				<li><a href="/xcloud/xz/clean/list"> <span class="am-icon-search-plus"></span> 查询与结算
				</a></li>
				<li><a href="/xcloud/xz/clean/service"> <span class="am-icon-star"></span> 保洁服务商
				</a></li>
			</ul></li>

		
		<li class="admin-parent"><a class="am-collapsed" onclick="setMenuId('collapse-nav-xz-recycle')"> <span
				class="am-icon-recycle"
			></span> 废品回收 <span class="am-icon-angle-right am-fr am-margin-right"></span>
		</a>
			<ul class="am-list am-collapse admin-sidebar-sub" id="collapse-nav-xz-recycle">
				<li><a href="/xcloud/xz/recycle/recycle-form" class="am-cf"> <span class="am-icon-bell"></span> 回收预约
				</a></li>
				<li><a href="/xcloud/xz/recycle/list"> <span class="am-icon-search-plus"></span> 查询与结算
				</a></li>
				<li><a href="/xcloud/xz/recycle/service"> <span class="am-icon-star"></span> 废品回收服务商
				</a></li>
			</ul></li>

		<li class="admin-parent"><a class="am-cf am-collapsed" onclick="setMenuId('collapse-nav-xz-meeting')" >
				<span class="am-icon-university"></span> 会议室管理 <span class="am-icon-angle-right am-fr am-margin-right"></span>
		</a>
			<ul class="am-list am-collapse admin-sidebar-sub" id="collapse-nav-xz-meeting">
				<li><a href="/xcloud/xz/meeting/list" class="am-cf"> <span class="am-icon-bell"></span> 会议一览 <!-- <span
						class="am-icon-star am-fr am-margin-right admin-icon-yellow"
					></span> -->
				</a></li>
				<li><a href="/xcloud/xz/meeting/setting/"> <span class="am-icon-calendar"></span> 会议设置
				</a></li>
				<li><a href="/xcloud/xz/meeting/service"> <span class="am-icon-star"></span> 会展服务商
				</a></li>
			</ul></li>

		<li class="admin-parent"><a class="am-cf am-collapsed" onclick="setMenuId('collapse-nav-xz-assets')" > <span
				class="am-icon-file"
			></span> 资产管理 <span class="am-icon-angle-right am-fr am-margin-right"></span>
		</a>
			<ul class="am-list am-collapse admin-sidebar-sub" id="collapse-nav-xz-assets">
				<li><a href="admin-user.html" class="am-cf"> <span class="am-icon-bell"></span> 资产一览 <span
						class="am-icon-star am-fr am-margin-right admin-icon-yellow"
					></span>
				</a></li>
				<li><a href="/xcloud/xz/assets/use_asset_list"> <span class="am-icon-plane"></span> 领用与借用
				</a></li>
				<li><a href="/xcloud/xz/assets/commpany_asset_list"> <span class="am-icon-calendar"></span> 库存管理
				</a></li>
				<li><a href="admin-404.html"> <span class="am-icon-plane"></span> 办公用品采购
				</a></li>
				<li><a href="admin-log.html"> <span class="am-icon-calendar"></span> 资产常用设置
				</a></li>
			</ul></li>

	</ul>



</div>
<!-- sidebar end -->
