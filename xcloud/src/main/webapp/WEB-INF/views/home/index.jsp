<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="../shared/taglib.jsp"%>
<html>
<head>
<!--common css for all pages-->
<%@ include file="../shared/importCss.jsp"%>
</head>
<body>
	<!--header start-->
	<%@ include file="../shared/pageHeader.jsp"%>
	<!--header end-->
	<div class="am-cf admin-main">
		<!-- sidebar start -->
		<%@ include file="../home/home-menu.jsp"%>
		<!-- sidebar end -->
		<!-- content start -->
		<!-- content start -->
		<div class="admin-content">
			<div class="am-cf am-padding">
				<div class="am-fl am-cf"></div>
			</div>
			<!-- 需进一步确认
			<div class="am-g ">
				<div class="am-u-md-6">
					<div class="am-panel am-panel-secondary">
						<div class="am-panel-hd am-cf" data-am-collapse="{target: '#collapse-index-company'}">
							HI, ${sessionScope.accountAuth.name}, 美好的一天从这里开始了.
							<span class="am-icon-chevron-down am-fr"></span>
						</div>
						<div id="collapse-index-company" class="am-panel-bd am-in">
							&nbsp;&nbsp;你的企业未认证，申请企业认证，获取更多的权益.
							<div class="am-btn-toolbar am-fr">
								<div class="am-btn-group am-btn-group-sm ">
									<button type="button" id="btn-company-idn" class="am-btn am-btn-warning am-radius">
										<span class="am-icon-star"></span>
										申请企业认证
									</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div> 
			-->
			<div class="am-g">
                <div class="am-u-md-4">
					<div class="am-panel am-panel-default">
			            <div class="am-panel-hd am-cf" data-am-collapse="{target: '#collapse-panel-4'}"><i class="am-icon-bookmark-o am-primary"></i> 重要事务提醒<span class="am-fr am-text-sm"><a href="/xcloud/schedule/list"> 更多 ></a></span></div>
			            <div id="collapse-panel-4" class="am-panel-bd am-collapse am-in">
			              <ul class="am-list admin-content-task">
			                <li>
			                	<span id="getTodayDate" class="am-icon-clock-o"></span>
			                </li>
			                <li>
			                  <div class="admin-task-meta"> 提醒时间 2017年7月1日 14：30</div>
			                  <div class="admin-task-bd">
			                    通知技术部老张办理新的员工卡。
			                  </div>
			                  <div class="am-cf">
			                    <div class="am-btn-toolbar am-fl">
			                      <div class="am-btn-group am-btn-group-xs">
			                        <button type="button" class="am-btn am-btn-default"><span class="am-icon-check"></span></button>
			                        <button type="button" class="am-btn am-btn-default"><span class="am-icon-pencil"></span></button>
			                      </div>
			                    </div>
			                    <div class="am-fr">
			                      <button type="button" class="am-btn am-btn-default am-btn-xs">删除</button>
			                    </div>
			                  </div>
			                </li>
							<li>
			                  <div class="admin-task-meta"> 提醒时间 2017年7月1日 14：30</div>
			                  <div class="admin-task-bd">
			                    通知技术部老张办理新的员工卡。
			                  </div>
			                  <div class="am-cf">
			                    <div class="am-btn-toolbar am-fl">
			                      <div class="am-btn-group am-btn-group-xs">
			                        <button type="button" class="am-btn am-btn-default"><span class="am-icon-check"></span></button>
			                        <button type="button" class="am-btn am-btn-default"><span class="am-icon-pencil"></span></button>
			                      </div>
			                    </div>
			                    <div class="am-fr">
			                      <button type="button" class="am-btn am-btn-default am-btn-xs">删除</button>
			                    </div>
			                  </div>
			                </li>
			                <li>
			                  <div class="admin-task-meta"> 提醒时间 2017年7月2日 10：30</div>
			                  <div class="admin-task-bd">
			                    面试客服部新招聘的主管。
			                  </div>
			                  <div class="am-cf">
			                    <div class="am-btn-toolbar am-fl">
			                      <div class="am-btn-group am-btn-group-xs">
			                        <button type="button" class="am-btn am-btn-default"><span class="am-icon-check"></span></button>
			                        <button type="button" class="am-btn am-btn-default"><span class="am-icon-pencil"></span></button>
			                      </div>
			                    </div>
			                    <div class="am-fr">
			                      <button type="button" class="am-btn am-btn-default am-btn-xs">删除</button>
			                    </div>
			                  </div>
			                </li>
			                <li>
			                  <div class="admin-task-meta"> 提醒时间 2017年7月3日 16：30</div>
			                  <div class="admin-task-bd">
			                    与财务部核对考勤。
			                  </div>
			                  <div class="am-cf">
			                    <div class="am-btn-toolbar am-fl">
			                      <div class="am-btn-group am-btn-group-xs">
			                        <button type="button" class="am-btn am-btn-default"><span class="am-icon-check"></span></button>
			                        <button type="button" class="am-btn am-btn-default"><span class="am-icon-pencil"></span></button>
			                      </div>
			                    </div>
			                    <div class="am-fr">
			                      <button type="button" class="am-btn am-btn-default am-btn-xs">删除</button>
			                    </div>
			                  </div>
			                </li>
			                <li>
			                  <div class="admin-task-meta"> 提醒时间 2017年7月3日 16：30</div>
			                  <div class="admin-task-bd">
			                    明天一早再次与财务部核对考勤。
			                  </div>
			                  <div class="am-cf">
			                    <div class="am-btn-toolbar am-fl">
			                      <div class="am-btn-group am-btn-group-xs">
			                        <button type="button" class="am-btn am-btn-default"><span class="am-icon-check"></span></button>
			                        <button type="button" class="am-btn am-btn-default"><span class="am-icon-pencil"></span></button>
			                      </div>
			                    </div>
			                    <div class="am-fr">
			                      <button type="button" class="am-btn am-btn-default am-btn-xs">删除</button>
			                    </div>
			                  </div>
			                </li>
			              </ul>
			            </div>
			          </div>
				</div>
				<div class="am-u-md-8">
					<div class="am-slider am-slider-default" data-am-flexslider id="demo-slider-0">
						<ul class="am-slides">
							<li><img src="http://cdn.bolohr.com/wp-content/uploads/2015/08/index-banner-1-11-11.jpg" /></li>
							<li><img src="http://cdn.bolohr.com/wp-content/uploads/2015/08/index-banner-2-11-22.jpg" /></li>
						</ul>
					</div>
				</div>
				<div class="am-u-md-8">
					<div class="am-panel am-panel-default">
						<div class="am-panel-hd am-cf" data-am-collapse="{target: '#collapse-index-company'}">
							常用功能
							<span class="am-icon-chevron-down am-fr"></span>

						</div>
						<div id="collapse-index-company" class="am-panel-bd am-in">
							<ul class="am-avg-sm-1 am-avg-md-8 am-margin am-padding am-text-center admin-content-list ">
								<li>
									<a href="/xcloud/schedule/card-form?card_type=2" class="am-text-secondary">
										<img src="http://img.bolohr.com/733d857f736cdb7cefe39379df5634ea?p=0" width="50%">
										<br />通知公告<br />
									</a>
								</li>
								<li>
									<a href="/xcloud/schedule/card-form?card_type=3" class="am-text-secondary">
										<img src="http://img.bolohr.com/8befcfd517342750758dceb3893e705c?p=0" width="50%">
										<br />事务提醒<br />
									</a>
								</li>
								<li>
									<a href="/xcloud/schedule/card-form?card_type=4" class="am-text-secondary">
										<img src="http://img.bolohr.com/b7573e98e89ce8a59a3107fa66be2591?p=0" width="50%">
										<br />面试邀约<br />
									</a>
								</li>
								
								<li>
									<a href="/xcloud/staff/list" class="am-text-secondary">
										<img src="http://img.bolohr.com/ded312297decfd7d0bbe07650894e8d0?p=0" width="50%">
										<br />通讯录<br />
									</a>
								</li>
								
								<li>
									<a href="/xcloud/xz/assets/company_asset_list" class="am-text-secondary">
										<img src="http://img.bolohr.com/ff54c25acfe1c170e7c93e457483aa9a?p=0" width="50%">
										<br />资产管理<br />
									</a>
								</li>
								
								<li>
									<a href="/xcloud/atools/tools-tax" class="am-text-secondary">
										<img src="http://img.bolohr.com/15bd4e23a7882291d3ca9bd612011cd1?p=0" width="50%">
										<br />个税计算器<br />
									</a>
								</li>
								<li>
									<a href="/xcloud/atools/list" class="am-text-secondary">
										<img src="http://img.bolohr.com/437396cc0b49b04dc89a0552f7e90cae?p=0" width="50%">
										<br />更多功能<br />
									</a>
								</li>
							</ul>
						</div>
					</div>
				</div>
			</div>
			<div class="am-g">
				<div class="am-u-md-6">
					<div class="am-panel am-panel-default">
						<div class="am-panel-hd am-cf" data-am-collapse="{target: '#collapse-index-card'}">
							热点知识精选
							<span class="am-icon-arrow-right am-fr"><a href="http://bolohr.com" target="_blank"> 更多</a></span>
						</div>
						<div class="am-in" height="100%">
							<table id="news-index" class="am-table am-table-bd am-table-bdrs am-table-striped am-table-hover">
								<tbody>
									
									
								</tbody>
							</table>
						</div>
						
					</div>
				</div>
				<div class="am-u-md-6">
					<div class="am-panel am-panel-default">
		            <div class="am-panel-hd am-cf" data-am-collapse="{target: '#collapse-panel-3'}">大咖同行问答<span class="am-icon-arrow-right am-fr"><a href="http://bolohr.com" target="_blank"> 更多</a></span></div>
		            <div class="am-panel-bd am-collapse am-in am-cf" id="collapse-panel-3">
		              <ul class="am-comments-list admin-content-comment">
		                <li class="am-comment">
		                  <a href="#"><img src="http://s.amazeui.org/media/i/demos/bw-2014-06-19.jpg?imageView/1/w/96/h/96" alt="" class="am-comment-avatar" width="48" height="48"></a>
		                  <div class="am-comment-main">
		                    <header class="am-comment-hd">
		                      <div class="am-comment-meta"><a href="#" class="am-comment-author">某人</a> 发布于 <time>1天前</time></div>
		                      <div class="am-comment-actions">
		                        <button type="button" class="am-btn am-btn-primary am-btn-xs"> 我来回答 </button>
		                      </div>
		                    </header>
		                    <div class="am-comment-bd">
		                      <p><a href="#">北京最近石油工人好招聘吗？特别是在油田进行一些地质勘探，这样的好招聘吗？</a></p>
		                
		                    </div>
		                    <footer class="am-comment-footer">
		                      <div class="am-comment-actions">
		                        <i class="am-icon-comments-o"> 3人已回答</i><span > </span><i class="am-icon-eye"> 28人已查看</i>
		                      </div>
		                    </footer>
		                  </div>
		                </li>

		                <li class="am-comment">
		                  <a href="#"><img src="http://s.amazeui.org/media/i/demos/bw-2014-06-19.jpg?imageView/1/w/96/h/96" alt="" class="am-comment-avatar" width="48" height="48"></a>
		                  <div class="am-comment-main">
		                    <header class="am-comment-hd">
		                      <div class="am-comment-meta"><a href="#" class="am-comment-author">某人</a> 发布于 <time>1天前</time></div>
		                      <div class="am-comment-actions">
		                        <button type="button" class="am-btn am-btn-primary am-btn-xs"> 我来回答 </button>
		                      </div>
		                    </header>
		                    <div class="am-comment-bd">
		                      <p><a href="#">2017年度上海的社保基数调整结果如何？和去年相比，有哪些变化？</a></p>
		                
		                    </div>
		                    <footer class="am-comment-footer">
		                      <div class="am-comment-actions">
		                        <i class="am-icon-comments-o"> 3人已回答</i><span > </span><i class="am-icon-eye"> 28人已查看</i>
		                      </div>
		                    </footer>
		                  </div>
		                </li>
		                <br>
		          	  </ul>
		            </div>
		          </div>
				</div>

			</div>
			
			<div class="am-g">
				<div class="am-u-md-6">
					<div class="am-panel am-panel-default">
						<div class="am-panel-hd am-cf" data-am-collapse="{target: '#collapse-index-wp'}">
							薪资与福利
							<span class="am-icon-arrow-right am-fr"><a href="http://bolohr.com/tag/%E8%96%AA%E8%B5%84" target="_blank"> 更多</a></span>
						</div>
						<div class="am-in">
							<table id="news-64" class="am-table am-table-bd am-table-bdrs am-table-striped am-table-hover">
								<tbody>
									
								</tbody>
							</table>
						</div>
					</div>
				</div>
				
				<div class="am-u-md-6">
					<div class="am-panel am-panel-default">
						<div class="am-panel-hd am-cf" data-am-collapse="{target: '#collapse-index-wp'}">
							绩效与考核
							<span class="am-icon-arrow-right am-fr"><a href="http://bolohr.com/tag/%E7%BB%A9%E6%95%88" target="_blank"> 更多</a></span>
						</div>
						<div class="am-in">
							<table id="news-65" class="am-table am-table-bd am-table-bdrs am-table-striped am-table-hover">
								<tbody>
									
								</tbody>
							</table>
						</div>
						
					</div>
				</div>
			</div>
		</div>
		<!-- content end -->
	</div>
	<!--footer start-->
	<%@ include file="../shared/pageFooter.jsp"%>
	<!--footer end-->
	<!-- js placed at the end of the document so the pages load faster -->
	<!--common script for all pages-->
	<%@ include file="../shared/importJs.jsp"%>
	<!--script for this page-->
	<script src="<c:url value='/assets/js/xcloud/home/index.js'/>"></script>
	<script src="<c:url value='/assets/js/showtoday.js'/>"></script>
</body>
</html>
