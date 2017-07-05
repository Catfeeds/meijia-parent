<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- sidebar start -->
<div class="admin-sidebar am-offcanvas" id="admin-offcanvas">
	<ul class="am-list admin-sidebar-list">
		
		<li class="admin-parent">
			<a class="am-collapsed" onclick="setMenuId('collapse-nav-study')"> 
				<span class="am-icon-user"></span>&nbsp; 菠萝学院
				<span class="am-icon-angle-right am-fr am-margin-right"></span>
			</a>
			<ul class="am-list am-collapse admin-sidebar-sub" id="collapse-nav-study">
				<li>
					<a href="/xcloud/study/class-list" class="am-cf"> 
						<span class="am-icon-folder-open-o"></span>&nbsp; 视听课程
					</a>
				</li>
				<li>
					<a href="/xcloud/study/ask-list" class="am-cf"> 
						<span class="am-icon-user-plus"></span>&nbsp; 互助问答
					</a>
				</li>
				<li>
					<a href="http://bolohr.com/doc" class="am-cf" target="_blank"> 
						<span class="am-icon-puzzle-piece"></span>&nbsp; 常用文档下载
					</a>
				</li>
				
			</ul>
		</li>
		<li><a href="/xcloud/study/discover-list"><span class="am-icon-binoculars"></span>&nbsp; 发现</a></li>
	</ul>
</div>
<!-- sidebar end -->
