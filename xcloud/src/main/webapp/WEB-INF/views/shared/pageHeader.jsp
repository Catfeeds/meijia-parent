<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<header class="am-topbar admin-header">
	<div class="am-topbar-brand">
		<img src="http://app.bolohr.com/simi-h5/icon/web-logo.png" height="30px" width="30px"> | <a href="/xcloud/company/company-form" onclick="setTopMenuId('top-hr', 'collapse-nav-hr-staff')" 
				class="am-dropdown-toggle" data-am-dropdown-toggle href="javascript:;">
						${sessionScope.accountAuth.companyName}
				</a>
		<c:if test="${sessionScope.accountAuth.companyList.size() > 1 }">
			<li class="am-dropdown" data-am-dropdown>

				<a href="/xcloud/company/company-form" onclick="setTopMenuId('top-hr', 'collapse-nav-hr-staff')" 
				class="am-dropdown-toggle" data-am-dropdown-toggle href="javascript:;">
						
						<span class="am-icon-caret-down"></span>
				</a>
				
				<ul class="am-dropdown-content">
				<center>[切换您所在的公司]</center>
					<c:forEach items="${sessionScope.accountAuth.companyList}" var="item">
						<c:if test="${item.companyId != sessionScope.accountAuth.companyId}">

							<li>
								<a href="/xcloud/company/company-form?companyId=${item.companyId }" onclick="setTopMenuId('top-hr', 'collapse-nav-hr-staff')">
									<span class="am-icon-book"></span>
									${item.companyName }
								</a>
							</li>
						</c:if>
					</c:forEach>
				</ul>
			</li>
		</c:if>
		<c:if test="${sessionScope.accountAuth.companyList.size() == 1 }">
			<a href="/xcloud/company/company-form" onclick="setTopMenuId('top-hr', 'collapse-nav-hr-staff')">
				| ${sessionScope.accountAuth.companyName}
			</a>
		</c:if>
		<a href="javascript:;">
			<span class="am-badge am-badge-danger am-radius">个人免费版</span>
		</a>
	</div>
	<button class="am-topbar-btn am-topbar-toggle am-btn am-btn-sm am-btn-success am-show-sm-only"
		data-am-collapse="{target: '#topbar-collapse'}">
		<span class="am-sr-only">导航切换</span>
		<span class="am-icon-bars"></span>
	</button>
	<div class="am-collapse am-topbar-collapse" id="topbar-collapse">
		<ul id="top-ul" class="am-nav am-nav-pills am-topbar-nav am-topbar-right admin-header-list">
			<li class="am-nav" id="top-index">
				<a href="/xcloud/index" onclick="setTopMenuId('top-index', '')" title="首页">&nbsp; 首页 &nbsp;</a>
			</li>
			<li id="top-schedule">
				<a href="/xcloud/schedule/list" onclick="setTopMenuId('top-schedule', 'collapse-nav-atools')" title="效率">&nbsp; 效率 &nbsp;</a>
			</li>
			<li id="top-study">
				<a href="/xcloud/study/index" onclick="setTopMenuId('top-study', 'collapse-nav-study')" title="成长" target="_top">&nbsp; 成长 &nbsp;</a>
			</li>
			<li id="top-hr">
				<a href="/xcloud/staff/list" onclick="setTopMenuId('top-hr', 'collapse-nav-hr-entry')" title="人事" target="_top">&nbsp; 人事 &nbsp;</a>
			</li>
			<li id="top-xz">
				<a href="/xcloud/xz/assets/company_asset_list" onclick="setTopMenuId('top-xz', 'collapse-nav-xz-assets')" title="行政"
					 target="_top">&nbsp; 行政 &nbsp;</a>
			</li>
			<li id="top-atools">
				<a href="/xcloud/atools/list" onclick="setTopMenuId('top-atools', 'collapse-nav-atools')" title="应用" target="_top">&nbsp; 应用 &nbsp;</a>
			</li>
			<li class="am-dropdown" data-am-dropdown>
				<a class="am-dropdown-toggle" data-am-dropdown-toggle href="javascript:;">
					<img src="<c:url value='${sessionScope.accountAuth.headImg}'/>" class="tr-am-img-thumbnail" width="30px" height="30px" />
					<span class="am-icon-caret-down"></span>
				</a>
				<ul class="am-dropdown-content">
					<li>
						<a href="/xcloud/staff/staff-form?staff_id=${sessionScope.accountAuth.staffId}">
							<span class="am-icon-user"></span>
							我的资料
						</a>
					</li>
					<!-- <li><a href="#"><span class="am-icon-cog"></span> 设置</a></li> -->
					<li>
						<a href="/xcloud/logout">
							<span class="am-icon-power-off"></span>
							退出
						</a>
					</li>
				</ul>
			</li>
		</ul>
	</div>
</header>
