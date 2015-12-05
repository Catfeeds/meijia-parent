<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<header class="navbar navbar-fixed-top ">
	<div class="container top-header">
		<div class="navbar-header">

			<a class="navbar-brand" href="/">
				<img src="<c:url value='/img/logo_header.png'/>"  alt="云行政"> 
				<span class="text" style="color: #fff">云行政-智慧行政服务平台</span>
			</a>
		</div>
		
		<div class="navbar-collapse collapse">

			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown">
						<a href="#" style="color: #fff;">美家生活<b
						class="caret"></b>
						</a>
					<ul class="dropdown-menu">
						<li><a href="#"><i class="fa fa-edit"></i>公司信息</a></li>
						<li><a href="#"><i class="fa fa-signout"></i>登出</a></li>
					</ul>
				</li>
			</ul>
		</div>
	</div>

	<div class="worker">
		<div class="apps apps-hover ui-tab ui-tab-hover">
			<div class="nav_shadow">
				<div class="ui-tab-title apps-title ">
					<label class="ui-item ui-tab-item "><a href="/xcloud/index/"><div>
								<i class="fa fa-desktop"></i>工作台
							</div></a></label>
							 <label class="ui-item ui-tab-item"><a href="/xcloud/staff/list"><div><i class="fa fa-desktop"></i>通讯录
							</div></a></label>
							 
							 <label class="ui-item ui-tab-item"><div>日程安排</div></label>
					<!-- <label class=" ui-tab-item ui-item ui-hide"><a id ="tongxunlua1" href="/xcloud/staff/list"><div>通讯录</div></a></label> -->
				</div>
			</div>
		</div>
	</div>

	
</header>

