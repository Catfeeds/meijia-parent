<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- sidebar start -->
<div class="admin-sidebar am-offcanvas" id="admin-offcanvas">
	<div class="am-panel am-panel-default admin-sidebar-panel" style="border-color: #fff;" align="center">
		<br>
		<img src="<c:url value='${sessionScope.accountAuth.headImg}'/>" class="tr-am-img-thumbnail" width="60px" height="60px" />
		<br><strong>${sessionScope.accountAuth.name}</strong>
		<p style="font-size:14px;">金币:2000 &nbsp;&nbsp; 经验值:1500<br><a href="/simi-h5/show/sign-today.html?user_id=">【每日签到】</a></p>
        
    </div>
	<ul class="am-list admin-sidebar-list">
		<li></li>
		<li><a href="/xcloud/index"><span class="am-icon-tachometer"></span>&nbsp; 首页</a></li>
		<li><a href="/xcloud/schedule/list"><span class="am-icon-calendar"></span>&nbsp; 效率</a></li>
		<li><a href="/xcloud/study/list"><span class="am-icon-book"></span>&nbsp; 成长</a></li>
		<li><a href="/xcloud/staff/list"><span class="am-icon-users"></span>&nbsp; 人事</a></li>
		<li><a href="/xcloud/xz/assets/company_asset_list"><span class="am-icon-tags"></span>&nbsp; 行政</a></li>
		<li><a href="/xcloud/atools/list"><span class="am-icon-cubes"></span>&nbsp; 应用</a></li>
	</ul>
</div>
<!-- sidebar end -->
