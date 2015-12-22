<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<header class="am-topbar am-topbar-fixed-top">
	<div class="am-g tr-login-topbar">
		<div class="am-container">
			<div class="am-fr am-text-xs am-margin-right-sm" id="loginPanel">
				欢迎来到云行政
			</div>
		</div>
	</div>
	<div class="am-g tr-topbar">
		<div class="am-container">
			<h1 class="am-topbar-brand">
				<a href="#"><img src="<c:url value='/assets/img/logo-sm.png'/>" alt="云行政"></a>
			</h1>
			<button
				class="am-topbar-btn am-topbar-toggle am-btn am-btn-sm am-btn-secondary am-show-sm-only am-collapsed"
				data-am-collapse="{target: '#tr-header-nav'}">
				<span class="am-sr-only">导航切换</span> <span class="am-icon-bars"></span>
			</button>
			<div class="am-collapse am-topbar-collapse" id="tr-header-nav">
				
				<ul class="am-nav am-topbar-right tr-nav">
					<li class="home am-active"><a href="/xcloud/main" title="首页">首页</a></li>
					<li class="products"><a href="/xcloud/staff/list" title="通讯录">通讯录</a></li>
					<li><a href="/xcloud/" title="工作日程" target="_top">工作日程</a></li>
					
					<li class="am-dropdown"><a class="am-dropdown-toggle" href="javascript:;">考勤管理</a>
						<ul class="am-dropdown-content am-text-center">
							<li><a href="#" target="_top">考勤设备</a></li>
							<li><a href="#" target="_top">考勤列表</a></li>
							<li><a href="#" target="_top">考勤统计</a></li>
						</ul>
					</li>
					
					<li class="am-dropdown"><a class="am-dropdown-toggle" href="javascript:;">办公用品</a>
						<ul class="am-dropdown-content am-text-center">
							<li><a href="#" target="_top">库存</a></li>
							<li><a href="#" target="_top">市场</a></li>
							<li><a href="#" target="_top">零食 & 下午茶</a></li>
						</ul>
					</li>
					
					<li><a href="/xcloud/" title="服务市场" target="_top">服务市场</a></li>
					
					<li><a href="/xcloud/" title="会议室" target="_top">会议室</a></li>
					
					<li><a href="/xcloud/" title="合同管理" target="_top">合同管理</a></li>
					
				</ul>
			</div>
		</div>
	</div>
</header>
