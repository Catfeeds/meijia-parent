<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<header class="am-topbar admin-header">
	<div class="am-topbar-brand">
		<img src="https://gtms02.alicdn.com/tps/i2/TB1qU_xGFXXXXczaXXXTyVt9VXX-128-128.png" height="25"
			width="25"><a href="#"></i> | 北京美家生活科技有限公司 </a><a href="javascript:;"> <span
			class="am-badge am-badge-danger am-radius">未认证</span></a>
	</div>

	<button class="am-topbar-btn am-topbar-toggle am-btn am-btn-sm am-btn-success am-show-sm-only"
		data-am-collapse="{target: '#topbar-collapse'}">
		<span class="am-sr-only">导航切换</span> <span class="am-icon-bars"></span>
	</button>

	<div class="am-collapse am-topbar-collapse" id="topbar-collapse">

		<ul class="am-nav am-nav-pills am-topbar-nav am-topbar-right admin-header-list">
			<li class="am-nav"><a href="/xcloud/index" title="首页">首页</a></li>
			<li><a href="/xcloud/staff/list" title="通讯录">日程</a></li>
			<li><a href="/xcloud/staff/list" title="工作日程" target="_top">通讯录</a></li>
			<li><a href="http://123.57.173.36/xcloud/" title="工作日程" target="_top">行政</a></li>
			<li><a href="http://123.57.173.36/xcloud/" title="合同管理" target="_top">人事</a></li>
			<li><a href="http://123.57.173.36/xcloud/" title="合同管理" target="_top">应用中心</a></li>
			<li><a href="http://123.57.173.36/xcloud/" title="工作日程" target="_top">服务大厅</a></li>
			<li><a href="http://123.57.173.36/xcloud/" title="工作日程" target="_top">办公超市</a></li>
			<!--
      	<li><a href="javascript:;"><span class="am-icon-bell"></span> 消息 <span class="am-badge am-badge-warning">5</span></a></li>
      	-->
			<li class="am-dropdown" data-am-dropdown><a class="am-dropdown-toggle"
				data-am-dropdown-toggle href="javascript:;"> <img src="<c:url value='/assets/img/a1.png'/>"
					class="am-img-thumbnail am-circle" width="30" height="30"> 莎拉波娃 <span
					class="am-icon-caret-down"></span>
			</a>
				<ul class="am-dropdown-content">
					<li><a href="#"><span class="am-icon-user"></span> 资料</a></li>
					<li><a href="#"><span class="am-icon-cog"></span> 设置</a></li>
					<li><a href="#"><span class="am-icon-power-off"></span> 退出</a></li>
				</ul></li>
		</ul>
	</div>
</header>
